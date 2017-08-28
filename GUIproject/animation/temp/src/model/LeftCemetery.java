package model;

import gameComponent.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class LeftCemetery extends Observable {

	private List<Piece> leftDeadPieces = new ArrayList<>();
	public void addPieces(Piece temp){
		leftDeadPieces.add(temp);
		setChanged();
		notifyObservers();
	}



	public List<Piece> getLeftDeadPiece(){

		return leftDeadPieces;
	}


}
