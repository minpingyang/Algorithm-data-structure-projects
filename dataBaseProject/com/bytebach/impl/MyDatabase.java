package com.bytebach.impl;

import java.util.*;

import com.bytebach.model.*;
import com.bytebach.model.Field.Type;


/**
 * This is an implementation class of <I>Database</I> interface
 * <br>
 * Some Helper static methods are contained in this class, though preferably these methods could be
 * put in <I>Database</I> interface as default methods.
 * 
 * @author Minping
 *
 */
public class MyDatabase implements Database {

    private Set<MyTable> tableSet;

    /*
     * Two maps to keep track of al6l reference values. The map, tableToRefRow, uses table name
     * (String) as keys and each key has a set of Rows that are referencing to this table. The other
     * map, rowToRefRow, uses referenced row as key and each key has a set of Rows that are
     * referencing to this row.
     */
    private static Map<String, Set<MyRow>> tableToMyRow = new HashMap<>();
    private static Map<List<Value>, Set<MyRow>> rowToMyRow = new HashMap<>();

    /**
     * Constructor
     */
    public MyDatabase() {
        tableSet = new HashSet<>();
    }

    @Override
    public Collection<? extends Table> tables() {
        return tableSet;
    }

    @Override
    public Table table(String name) {
        for (MyTable table : tableSet) {
            if (table.name().equals(name)) {
                return table;
            }
        }
        return null;
    }

    @Override
    public void createTable(String name, List<Field> fields) {
        // if the name already existed
        if (table(name) != null) {
            throw new InvalidOperation("Table \"" + name + "\" already exists, Try another name.");
        }
        // initialise hashMap
        tableToMyRow.put(name, new HashSet<>());

        tableSet.add(new MyTable(name, fields, this));
    }

   

    /**
     * This method check whether the given field matches the given value.
     * 
     * @param field
     *            --- the given field determined in schema
     * @param value
     *            --- the given value to be checked
     * @return --- true if they match, or false if not.
     */
    public static boolean isSameType(Field field, Value value) {

        // special case, if any of these two arguments is null, they are not match (even both null).
        if (field == null || value == null) {
            return false;
        }

        Type type = field.type();

        if (value instanceof BooleanValue && type == Type.BOOLEAN) {
            return true;
        } else if (value instanceof IntegerValue && type == Type.INTEGER) {
            return true;
        } else if (value instanceof ReferenceValue && type == Type.REFERENCE) {
            return true;
           
        }// check textArea and text  
        else if (value instanceof StringValue) {  
        	//if the type is set to TEXT, then it cannot contain new line character.          
            String str = ((StringValue) value).value();
            if (str.contains("\n") && type == Type.TEXT) {
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * This method does a series of checks to ensure that adding the given row doesn't violate the
     * database constraints. If it does, an InvalidOperation is thrown.
     * 
     * @param rowList
     *            --- The list of rows that need to add the given row in.
     * @param element
     *            --- The row
     * @throws InvalidOperation
     *        
     */
    public static void addRow(MyRowList rowList, List<Value> element) throws InvalidOperation {

        // the added row must have fewer or same amount of values than schema
        List<Field> schema = rowList.getTable().fields();
        if (element.size() > schema.size()) {
            throw new InvalidOperation("Cannot add a row with more fields than schema.");
        }
        // the added row cannot have empty key field.
        List<Integer> keyColumns = rowList.getTable().getKeyColumns();
        for (Integer index : keyColumns) {
            if (element.size() < index + 1 || element.get(index) == null) {
                throw new InvalidOperation("the added row cannot have empty key field.");
            }
        }
        // the added row must correspond to the types determined in schema
        for (int i = 0; i < element.size(); i++) {
            Value value = element.get(i);
            Field field = schema.get(i);
            if (!MyDatabase.isSameType(field, value)) {
                throw new InvalidOperation(
                        "The added row must correspond to the types determined in schema.");
            }
        }
        // if there are reference values in this row, check if they are good values.
        List<ReferenceValue> refValues = referenceValues(element);
        Database database = rowList.getTable().getDatabase();
        for (ReferenceValue value : refValues) {
            MyDatabase.checkReference(database, value);
        }
        // the added row must have unique key value.
        for (List<Value> row : rowList) {
            boolean isSameKey = true;
            for (Integer index : keyColumns) {
                Value existingValue = row.get(index);
                Value value = element.get(index);
                if (!value.equals(existingValue)) {
                    isSameKey = false;
                }
            }
            if (isSameKey) {
                throw new InvalidOperation("The given row does not have unique key field.");
            }
        }
    }

    /**
     * This method checks whether a given reference value is valid in the given database.
     * 
     * @param database
     * @param reference
     */
    private static void checkReference(Database database, ReferenceValue reference) {
        MyTable referencedTable = null;
        // The referenced table must exist
        Collection<? extends Table> tables = database.tables();
        boolean noSuchTable = true;
        for (Table table : tables) {
            if (table.name().equals(reference.table())) {
                noSuchTable = false;
                referencedTable = (MyTable) table; 
                break;
            }
        }
        if (noSuchTable) {
            throw new InvalidOperation("The referenced table doesn't exist.");
        }
        
        // The referenced table must have exact same schema as specified in this reference
        List<Integer> keyColumns = referencedTable.getKeyColumns();
        Value[] keys = reference.keys();

        if (keyColumns.size() != keys.length) { // check size of keys
            throw new InvalidOperation("Keys in this reference doesn't match the referenced table.");
        }

        List<Field> fields = referencedTable.fields();
        for (int i = 0; i < keys.length; i++) { // check type of keys
            if (!MyDatabase.isSameType(fields.get(keyColumns.get(i)), keys[i])) {
                throw new InvalidOperation("Keys in this reference doesn't match the referenced table.");
            }
        }

        // The referenced table must have such a row that matches keys specified in this reference
        List<List<Value>> rows = referencedTable.rows();
        for (List<Value> row : rows) {
            boolean found = true;
            for (int i = 0; i < keyColumns.size(); i++) {
                if (!row.get(keyColumns.get(i)).equals(keys[i])) {
                    found = false;
                    break;
                }
            }
            //found such a row that matches keys
            if (found) { 
                return;
            }
        }
        throw new InvalidOperation("Found no match to this reference value.");
    }
    @Override
    public void deleteTable(String tableName) {
        Table table = table(tableName);

        if (table == null) {
            throw new InvalidOperation("No such table");
        }
        tableSet.remove(table);
        
        // cascade delete all reference values pointing to this table
        Set<MyRow> referRows = tableToMyRow.get(tableName);
        if (referRows != null) {
            for (MyRow row : referRows) {
                row.table().rows().remove(row);
            }
            referRows.clear();
        }
    }

    /**
     * A getter method to get hashMap
     * 
     * @return --- a hash map uses table-name as keys and 
     * 		   --- each key has a set of my Rows 
     *         		that are referencing to this table.
     */
    public Map<String, Set<MyRow>> getTableToMyRow() {
        return tableToMyRow;
    }

    /**
     * A getter method to get hashMap
     * 
     * @return --- a hash map uses referenced-row as key and 
     * 		   --- each key has a set of Rows 
     * 			   that are referencing to this row.
     */
    public Map<List<Value>, Set<MyRow>> getRowToMyRow() {
        return rowToMyRow;
    }
    /**
     * This method collects all reference values in the given row.
     * 
     * @param element
     *            --- the row that need to collect reference values from
     * @return --- all reference value as a List
     */
    private static List<ReferenceValue> referenceValues(List<Value> element) {
        List<ReferenceValue> list = new ArrayList<>();
        for (Value value : element) {
            if (value instanceof ReferenceValue) {
                list.add((ReferenceValue) value);
            }
        }
        return list;
    }

    /**
     * This method is used to update two hash maps. 
     * it checks the given row, and check the validity of any reference value it runs across, 
     * Meantime, add it into two maps properly if it is a valid reference.
     * 
     * @param table
     *            --- the table that this referencing row is added into
     * @param referRow
     *            --- the given row which contains reference value(s)
     */
    public static void setReference(MyTable table, MyRow referRow) {
        MyDatabase databasePointer = table.getDatabase();
        List<ReferenceValue> refs = MyDatabase.referenceValues(referRow);
        if (refs.isEmpty()) {
            return;
        }

        for (ReferenceValue ref : refs) {
            // check the validity of this reference value
            MyDatabase.checkReference(databasePointer, ref);
            // update  (Table -> refer rows) hashMap
            Set<MyRow> referRows1 = tableToMyRow.get(ref.table());
            referRows1.add(referRow); // it's not null for sure.
            // update (Referenced rows -> refer rows) hashMap
            List<Value> referencedRow = databasePointer.table(ref.table()).row(ref.keys());
            Set<MyRow> referRows2 = rowToMyRow.get(referencedRow);
            if (referRows2 == null) { 
                referRows2 = new HashSet<>();
                referRows2.add(referRow);
                rowToMyRow.put(referencedRow, referRows2);
            } else {
                referRows2.add(referRow);
            }
        }
    }

    /**
     * 
     * This method is used to update  two hash maps. 
     * it examines the given row, and check the validity of any reference value it runs across, 
     * Meantime, add row into two maps properly if it is a valid reference.
     * 
     * @param table
     *            --- the table that this reference value is added into
     * @param reference
     *            --- the given reference value
     * @param referRow
     *            --- the row that the reference refer to
     */
    public static void setReference(MyTable table, ReferenceValue reference, MyRow referRow) {
        MyDatabase databasePointer = table.getDatabase();
        MyDatabase.checkReference(databasePointer, reference);
        // update  (Table -> referencing rows) hashMap
        Set<MyRow> referRows1 = tableToMyRow.get(reference.table());
        referRows1.add(referRow); // it's not null for sure.
        // update (Referenced rows -> referencing rows) hashMap
        List<Value> referencedRow = databasePointer.table(reference.table()).row(reference.keys());
        Set<MyRow> referRows2 = rowToMyRow.get(referencedRow);
        if (referRows2 == null) {
            referRows2 = new HashSet<>();
            referRows2.add(referRow);
            rowToMyRow.put(referencedRow, referRows2);
        } else {
            referRows2.add(referRow);
        }
    }
}
