package com.bytebach.impl;

import java.util.*;

import com.bytebach.model.*;


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

	@Override
	public Collection<? extends Table> tables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table table(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createTable(String name, List<Field> fields) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTable(String name) {
		// TODO Auto-generated method stub
		
	}
	// This is where you'll probably want to start. You'll need to provide an
	// implementation of Table as well.
	//
	// One of the key challenges in this assignment is to provide you're
	// own implementations of the List interface which can intercept the various
	// operations (e.g. add, set, remove, etc) and check whether they violate
	// the constraints and/or update the database appropriately (e.g. for the
	// cascading delete).
	//
	// HINT: to get started, don't bother providing your own implementations of
	// List as discussed above! Instead, implement MyDatabase and MyTabe using
	// conventional Collections. When you have that working, and the web system
	// is doing something sensible, then consider how you're going to get those
	// unit test to past. 

	public static boolean checkTypeMatch(Field field, Value element) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void updateReference(MyTable table, ReferenceValue element, MyRow myRow) {
		// TODO Auto-generated method stub
		
	}

	public static void checkSafeToAddRow(MyRowList myRowList, List<Value> element) {
		// TODO Auto-generated method stub
		
	}

	public static void updateReference(MyTable table, MyRow myRow) {
		// TODO Auto-generated method stub
		
	}
}
