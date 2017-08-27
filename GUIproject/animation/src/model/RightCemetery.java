package model;

import gameComponent.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class RightCemetery extends Observable{

	private List<Piece> rightDeadPieces = new ArrayList<>();
	public void addPieces(Piece temp){
		rightDeadPieces.add(temp);
		setChanged();
		notifyObservers();
	}



	public List<Piece> getRightDeadPieces(){
		return rightDeadPieces;
	}

}
