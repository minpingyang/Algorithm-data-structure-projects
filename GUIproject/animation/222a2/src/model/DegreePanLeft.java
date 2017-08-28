package model;

import gameComponent.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DegreePanLeft extends Observable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Piece selectPiece;
	private List<Point> piecesPoint=new ArrayList<Point>();
	private List<Piece> diffDegreePiece=new ArrayList<Piece>();
	public List<Point> getPiecesPoint(){
		return piecesPoint;
	}
	public Piece getSelectPiece(){
		return selectPiece;
	}
	public void setSelectPiece(Piece temp){
		List<Piece> late= new ArrayList<Piece>();
	    selectPiece=temp;
		Piece selectPiece1= selectPiece.getRotatePiece("1", selectPiece);
		Piece selectPiece2= selectPiece.getRotatePiece("2", selectPiece);
		Piece selectPiece3= selectPiece.getRotatePiece("3", selectPiece);
		Piece selectPiece4= selectPiece.getRotatePiece("4", selectPiece);

		System.out.println("name: "+selectPiece1.getName());

		selectPiece1.printWeapon();
		selectPiece2.printWeapon();
		selectPiece3.printWeapon();
		selectPiece4.printWeapon();
        diffDegreePiece.clear();
        diffDegreePiece.add(selectPiece1);
        diffDegreePiece.add(selectPiece2);
		diffDegreePiece.add(selectPiece3);
		diffDegreePiece.add(selectPiece4);
		setChanged();
		notifyObservers();

	}
	public List<Piece> getDiffDegreePiece(){
		return diffDegreePiece;
	}


	

}
