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
    
    
    
    
    
}
