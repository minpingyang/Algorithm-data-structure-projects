package com.bytebach.impl;

import java.util.*;

import com.bytebach.model.*;
import com.bytebach.model.Field.Type;


/**
* This is an implementation class of <I>database</I> interface.<br>
* <br>
* Some Helper static methods are contained in this class, though preferably these methods could be
* put in <I>Database</I> interface as default methods. Because <I>Database</I> interface could not be modified
* , these methods end up here.
* 
* @author minping
*
*/

    
public class MyDatabase implements Database {

    private Set<MyTable> tables;

    /*
     * Two maps to keep track of al6l reference values. The map, tableToRefRow, uses table name
     * (String) as keys and each key has a set of Rows that are referencing to this table. The other
     * map, rowToRefRow, uses referenced row as key and each key has a set of Rows that are
     * referencing to this row.
     */
    private static Map<String, Set<MyRow>> tableToRefRow = new HashMap<>();
    private static Map<List<Value>, Set<MyRow>> rowToRefRow = new HashMap<>();

    /**
     * Constructor
     */
    public MyDatabase() {
        tables = new HashSet<>();
    }
    
  	// One of the key challenges in this assignment is to provide you're
  	// own implementations of the List interface which can intercept the various
  	// operations (e.g. add, set, remove, etc) and check whether they violate
  	// the constraints and/or update the database appropriately (e.g. for the
  	// cascading delete).
  	//
  	// HINT: to get started, don't bother providing your own implementations of
  	// List as discussed above! 
    //Instead, implement MyDatabase and MyTabe using conventional Collections.
    //When you have that working, and the web system
  	// is doing something sensible, then consider how you're going to get those
  	// unit test to past. 
    @Override
    public Collection<? extends Table> tables() {
        return tables;
    }
    @Override
    public Table table(String name) {
        for (MyTable table : tables) {
            if (table.name().equals(name)) {
                return table;
            }
        }
        return null;
    }
    @Override
    public void createTable(String name, List<Field> fields) {
        // if the name is already used
        if (table(name) != null) {
            throw new InvalidOperation("Table \"" + name + "\" already exists, Try another name.");
        }

        // initialise the map for later use.
        tableToRefRow.put(name, new HashSet<>());
        tables.add(new MyTable(name, fields, this));
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
	public static boolean checkTypeMatch(Field field, Value value) {
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
        } else if (value instanceof StringValue) {
            /*
             * Here we assume if this field is set to TEXTAREA, then it doens't matter whether or
             * not the value contains new line character. But if the type is set to TEXT, then it
             * cannot contain new line character.
             */
            String str = ((StringValue) value).value();
            if (str.contains("\n") && type == Type.TEXT) {
                return false;
            }
            return true;
        }

        return false;
	}
	 /**
     * This method updates two maps that records all reference values. More specifically, it check
     * the validity of the given reference value, and add the row it belongs to into two maps
     * properly if it is a valid reference.
     * 
     * @param table
     *            --- the table that this reference value is added into
     * @param reference
     *            --- the given reference value
     * @param referencingRow
     *            --- the row that the reference value belongs to
     */
	public static void updateReference(MyTable table, ReferenceValue element, MyRow myRow) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * This method does a series of checks to ensure that adding the given row doesn't violate the
     * database constraints. If it does, an InvalidOperation is thrown.
     * 
     * @param myRowList
     *            --- The list of rows (table) that need to add the given row in.
     * @param element
     *            --- The given row
     * @throws InvalidOperation
     *             --- if it's not safe to add.
     */
	public static void checkSafeToAddRow(MyRowList myRowList, List<Value> element) throws InvalidOperation {
		// the added row must have fewer or same amount of values than schema
        List<Field> schema = myRowList.getTable().fields();
        if (element.size() > schema.size()) {
            throw new InvalidOperation("Cannot add a row with more fields than schema.");
        }

        // the added row cannot have empty key field.
        List<Integer> keyColumns = myRowList.getTable().getKeyColumns();
        for (Integer index : keyColumns) {
            if (element.size() < index + 1 || element.get(index) == null) {
                throw new InvalidOperation("the added row cannot have empty key field.");
            }
        }

        // the added row must correspond to the types determined in schema
        for (int i = 0; i < element.size(); i++) {
            Value value = element.get(i);
            Field field = schema.get(i);

            if (!MyDatabase.checkTypeMatch(field, value)) {
                throw new InvalidOperation(
                        "The added row must correspond to the types determined in schema.");
            }
        }

        // if there are reference values in this row, check if they are good values.
        List<ReferenceValue> refValues = referenceValues(element);
        Database database = myRowList.getTable().getDatabase();
        for (ReferenceValue value : refValues) {
            MyDatabase.checkValidReference(database, value);
        }

        // the added row must have unique key value.
        for (List<Value> row : myRowList) {
            boolean sameKeys = true;
            for (Integer index : keyColumns) {
                Value existingValue = row.get(index);
                Value value = element.get(index);
                if (!value.equals(existingValue)) {
                    sameKeys = false;
                }
            }
            if (sameKeys) {
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
	private static void checkValidReference(Database database, ReferenceValue reference) {
		 MyTable referencedTable = null;

	        // The referenced table must exist
	        Collection<? extends Table> tables = database.tables();
	        boolean noSuchTable = true;
	        for (Table table : tables) {
	            if (table.name().equals(reference.table())) {
	                noSuchTable = false;
	                referencedTable = (MyTable) table; // It's safe to cast
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
	            if (!MyDatabase.checkTypeMatch(fields.get(keyColumns.get(i)), keys[i])) {
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
	            if (found) { // found one
	                return;
	            }
	        }

	        // no match
	        throw new InvalidOperation("Found no match to this reference value.");
		
	}
	/**
     * This helper method collects all reference values in the given row.
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
    
    
    
    
    
}
