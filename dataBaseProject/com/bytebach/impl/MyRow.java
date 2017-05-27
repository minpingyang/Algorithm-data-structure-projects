package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Value;
/**
 * This is a wrapper class for a list of values, which is essentially a row in a table. In addition,
 * it wraps in a parent pointer to the table it belongs to.
 * 
 * @author minping
 *
 */
public class MyRow implements List<Value> {
	 private ArrayList<Value> listValues;
	 private MyTable table;
	 
	public MyRow(MyTable table, List<Value> listValues) {
		// null check
        if (listValues == null) {
            throw new InvalidOperation("Cannot construct a row from null.");
        }
        if (table == null) {
            throw new InvalidOperation("The given table cannot be null.");
        }

        this.listValues = new ArrayList<>(listValues);
        this.table = table;
	}
	
	/**
     * A getter method to get the pointer to the table it belongs to.
     * 
     * @return --- a pointer to the table it belongs to.
     */
    public MyTable table() {
        return table;
    }

    @Override
    public Value set(int index, Value element) {
        // cannot set a null
        if (element == null) {
            throw new InvalidOperation("Cannot set a null.");
        }
        // boundary check
        if (index >= listValues.size() || index < 0) {
            throw new InvalidOperation("Index out of boundary.");
        }
        // Cannot set a key field
        if (table.fields().get(index).isKey()) {
            throw new InvalidOperation("Cannot modify a key value.");
        }
        // element must correspond to the type determined in schema
        Field field = table.fields().get(index);
        if (!MyDatabase.checkTypeMatch(field, element)) {
            throw new InvalidOperation("Cannot set a value with incorrect type.");
        }
        // if it's a reference, need to check its validity, and to update two HashMaps in MyDatabase.
        if (element instanceof ReferenceValue) {
            MyDatabase.updateReference(table, (ReferenceValue) element, this);
        }

        return listValues.set(index, element);
    }
	
    @Override
    public int size() {
        return listValues.size();
    }

    @Override
    public boolean isEmpty() {
        return listValues.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return listValues.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return listValues.containsAll(c);
    }

    @Override
    public Value get(int index) {
        return listValues.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return listValues.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return listValues.lastIndexOf(o);
    }

    @Override
    public Object[] toArray() {
        return listValues.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return listValues.toArray(a);
    }

    @Override
    public Iterator<Value> iterator() {
        return listValues.iterator();
    }

    @Override
    public ListIterator<Value> listIterator() {
        return listValues.listIterator();
    }

    @Override
    public ListIterator<Value> listIterator(int index) {
        return listValues.listIterator(index);
    }

    @Override
    public List<Value> subList(int fromIndex, int toIndex) {
        return listValues.subList(fromIndex, toIndex);
    }

    /*
     *These methods should not be implemented in this project
     */

    @Override
    public boolean add(Value e) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public void add(int index, Value element) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean addAll(Collection<? extends Value> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean addAll(int index, Collection<? extends Value> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public Value remove(int index) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean remove(Object o) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public void clear() {
        throw new InvalidOperation("Unsupported Operation");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new InvalidOperation("Unsupported Operation");
    }

}
