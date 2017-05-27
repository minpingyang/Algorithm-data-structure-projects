package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Value;
/**
 * This is a wrapper class for a list of rows, which is essentially all values of the table. In
 * addition, it wraps in a parent pointer to the table it belongs to.
 * 
 * @author minping
 *
 */
public class MyRowList implements List<List<Value>>{
	private ArrayList<MyRow> Listrow;
    private MyTable table;
    
    /**
     * Constructor.
     * 
     * @param table
     * 				  --- the pointer to the table it belongs to.
     */
    public MyRowList(MyTable table) {
    	// null check
        if (table == null) {
            throw new InvalidOperation("The given table cannot be null.");
        }

        this.Listrow = new ArrayList<>();
        this.table = table;
	}
    /**
     * A getter method to get the pointer to the table it belongs to.
     * 
     * @return --- the pointer to the table it belongs to.
     */
    public MyTable getTable() {
        return this.table;
    }
    
    @Override
    public boolean add(List<Value> element) {
        MyRow myRow = new MyRow(table, element);
        // check whether it's safe to add
        MyDatabase.checkSafeToAddRow(this, element);
        // if there is a reference value, need to update two maps in MyDatabase.
        MyDatabase.updateReference(table, myRow);
        return Listrow.add(myRow);
    }
    @Override
    public void add(int i, List<Value> element) {
        MyRow addedRow = new MyRow(table, element);
        // check whether it's safe to add
        MyDatabase.checkSafeToAddRow(this, element);
        // if there is a reference value, need to update two maps in MyDatabase.
        MyDatabase.updateReference(table, addedRow);
        Listrow.add(i, addedRow);
    }
   
    
    
    
    
    
}
