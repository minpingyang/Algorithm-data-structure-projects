package com.bytebach.impl;

import java.util.ArrayList;
import java.util.List;

import com.bytebach.model.Database;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

/**
 * This is an implementation class for a Table in a database.
 * 
 * @author minping
 *
 */
public class MyTable implements Table {
	private final String name;
    private final MyFieldList fields;
    private MyDatabase dataBasePointer;
    private MyRowList rows;

    // a helper field to record the indices of key fields
    private List<Integer> keyColumns;
    
    public MyTable(String name, List<Field> fields, MyDatabase dataBase) {
    	// null check
        if (fields == null) {
            throw new InvalidOperation("The given fields cannot be null.");
        }
        if (dataBase == null) {
            throw new InvalidOperation("The given dataBase cannot be null.");
        }
        if (name == null || name.trim().equals("")) {
            throw new InvalidOperation("The given name cannot be empty.");
        }

        this.name = name;
        this.fields = new MyFieldList(fields, this);
        dataBasePointer = dataBase;
        rows = new MyRowList(this);

        // let this table knows its key field(s).
        keyColumns = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isKey()) {
                keyColumns.add(new Integer(i));
            }
        }
        
	}
     
    @Override
    public String name() {
        return name;
    }

    @Override
    public List<Field> fields() {
        return fields;
    }

    @Override
    public List<Value> row(Value... keys) {

        // if the number of given keys doesn't match, error
        if (keys.length != keyColumns.size()) {
            throw new InvalidOperation("The number of given keys doesn't match current table.");
        }

        // if the types of given keys don't match, error again
        for (int i = 0; i < keyColumns.size(); i++) {
            if (!MyDatabase.checkTypeMatch(fields.get(keyColumns.get(i)), keys[i])) {
                throw new InvalidOperation("One of given keys has a wrong type.");
            }
        }

        // go through every row, try to find a match
        for (int j = 0; j < rows.size(); j++) {
            List<Value> row = rows.get(j);
            boolean found = true;
            for (int i = 0; i < keyColumns.size(); i++) {
                if (!row.get(keyColumns.get(i)).equals(keys[i])) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return row;
            }
        }

        // no luck
        throw new InvalidOperation("Found no match");
    }

	@Override
	public List<Value> row(Value... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Value... keys) {
		// TODO Auto-generated method stub
		
	}


	public List<Integer> getKeyColumns() {
		// TODO Auto-generated method stub
		return null;
	}


	
	 /**
     * A getter method to return the pointer to the database that it belongs to
     * 
     * @return --- the pointer to the database that it belongs to
     */
    public MyDatabase getDatabase() {
        return this.dataBasePointer;
    }

}
