package model;

import gameComponent.Piece;
import gameComponent.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class LeftCemetery extends Observable {
	private int rows = 6;
	private int cols = 4;
	private Player greenPlayer;
	private List<Piece> pieces;
	private List<Point> piecesPoint;
	public void setPieces(List<Piece> temp){
		pieces=temp;
		setChanged();
		notifyObservers();
	}
	public List<Piece> getPieces(){
		return pieces;
	}
	public List<Point> getPiecesPoint(){return piecesPoint;}
	private int widthDis = 4 * (Piece.SIZE_PIECE) + 40;
	private int heightDis = 3 * Piece.SIZE_PIECE - 10;
	private int topDis = 80 + 6 * Piece.SIZE_PIECE;
	public Player getGreenPlayer(){
		return greenPlayer;
	}

	public LeftCemetery(Player player) {
		greenPlayer = player;
		pieces=new ArrayList<Piece>();
		// initialise all pieces
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				greenPlayer.addDiffPiece(Piece.Type.GreenPiece);


			}
		}
		setChanged();
		notifyObservers();
		
	}

}
