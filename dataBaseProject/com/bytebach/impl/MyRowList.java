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
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator<List<Value>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean add(List<Value> e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection<? extends List<Value>> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection<? extends List<Value>> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Value> get(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Value> set(int index, List<Value> element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void add(int index, List<Value> element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Value> remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListIterator<List<Value>> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<List<Value>> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<List<Value>> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
