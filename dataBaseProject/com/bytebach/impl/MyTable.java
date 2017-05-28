package com.bytebach.impl;

import java.util.ArrayList;
import java.util.List;

import com.bytebach.model.Database;
import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

/**
 * This is an implementation class for a <I>Table</I> interface.
 * 
 * @author Minping
 *
 */
public class MyTable implements Table {

    private final String name;
    private final MyFieldList fields;
    private MyDatabase dataBasePointer;
    private MyRowList rows;
    private List<Integer> keyCols;

    /**
     * constructor 
     * 
     * @param name
     *            --- the name of the table
     * @param fields
     *            --- the schema
     * @param dataBase
     *            --- the pointer pointing to the database 
     */
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
        // let this table knows its key fields
        keyCols = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).isKey()) {
                keyCols.add(new Integer(i));
            }
        }
    }

   
    @Override
    public List<Value> row(Value... keys) {

        // if the number of given keys doesn't match, error
        if (keys.length != keyCols.size()) {
            throw new InvalidOperation("The number of given keys doesn't match current table.");
        }

        // if the types of given keys don't match, error again
        for (int i = 0; i < keyCols.size(); i++) {
            if (!MyDatabase.isSameType(fields.get(keyCols.get(i)), keys[i])) {
                throw new InvalidOperation("One of given keys has a wrong type.");
            }
        }

        // go through every row, try to find a match
        for (int j = 0; j < rows.size(); j++) {
            List<Value> row = rows.get(j);
            boolean found = true;
            for (int i = 0; i < keyCols.size(); i++) {
                if (!row.get(keyCols.get(i)).equals(keys[i])) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return row;
            }
        }
        throw new InvalidOperation("Found no match");
    }

    @Override
    public void delete(Value... keys) {

        // if the number of given keys doesn't match
        if (keys.length != keyCols.size()) {
            throw new InvalidOperation("The number of given keys doesn't match current table.");
        }

        // if the types of given keys don't match
        for (int i = 0; i < keyCols.size(); i++) {
            if (!MyDatabase.isSameType(fields.get(keyCols.get(i)), keys[i])) {
                throw new InvalidOperation("One of given keys has a wrong type.");
            }
        }

        // go through every row, try to find a match
        int index = 0;
        boolean found = true;
        for (int j = 0; j < rows.size(); j++) {
            List<Value> row = rows.get(j);
            found = true;
            for (int i = 0; i < keyCols.size(); i++) {
                if (!row.get(keyCols.get(i)).equals(keys[i])) {
                    found = false;
                    break;
                }
            }
            if (found) {
                index = j;
                break;
            }
        }

        if (found) {
            rows.remove(index);
            return;
        }
        throw new InvalidOperation("Found no match");
    }

    /**
     * A getter method to return the indices of key fields
     * 
     * @return --- a list of integer that indicates the indices of key fields.
     */
    public List<Integer> getKeyColumns() {
        return this.keyCols;
    }

    /**
     * A getter method to return the pointer to the database that it belongs to
     * 
     * @return --- the pointer to the database that it belongs to
     */
    public MyDatabase getDatabase() {
        return this.dataBasePointer;
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
    public List<List<Value>> rows() {
        return rows;
    }

}
