package com.bytebach.impl;

import java.util.List;

import com.bytebach.model.Database;
import com.bytebach.model.Field;
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
    
    public MyTable(String name, List<Field> fields, MyDatabase database) {
		// TODO Auto-generated constructor stub
	}
    
    
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Field> fields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Value>> rows() {
		// TODO Auto-generated method stub
		return null;
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
