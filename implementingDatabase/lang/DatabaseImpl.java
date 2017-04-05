package swen221.lab4.lang;


import java.util.ArrayList;

public class DatabaseImpl implements Database {
	ColumnType[] schema;
	int keyField;
	ArrayList<Object[]> rows;
	public DatabaseImpl(ColumnType[] schema, int keyField, ArrayList<Object[]>rows) {
		this.schema = schema;
		this.keyField =keyField;
		this.rows = rows;
	}
	@Override
	public ColumnType[] getSchema() {
		// TODO Auto-generated method stub
		return schema;
	}

	@Override
	public int getKeyField() {
		// TODO Auto-generated method stub
		return keyField;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public void addRow(Object... data) throws InvalidRowException, DuplicateKeyException {
		// TODO Auto-generated method stub
		//invalidRowException has two cases:
		//check columns
		if(data.length != schema.length){
			throw new InvalidRowException();
		}
		//second case is that check if the type of data is instance of Integer/String
		Object[] tempRow = new Object[schema.length];
		for (int i = 0; i < data.length; i++) {
			if(schema[i].getType().isInstance(data[i])){
				tempRow[i] = data [i];
			}else{
				throw new InvalidRowException();
			}
		}
		//check if key is duplicate
		for(Object[] r:rows){
			if(r[keyField].equals(data[keyField])){
				throw new DuplicateKeyException();
			}
		}
		rows.add(tempRow);
		
	}

	@Override
	public Object[] getRow(Object key) throws InvalidKeyException {
		// TODO Auto-generated method stub
		for (Object[] row : rows) {
			if(row[keyField].equals(key)){
				return row;
			}
		}
		//if above doesn't  return, then throw Exception
		throw new InvalidKeyException();
	}

	@Override
	public Object[] getRow(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if(index < 0 || index >= rows.size()){
			throw new IndexOutOfBoundsException();
		}
		return rows.get(index);
	}

}
