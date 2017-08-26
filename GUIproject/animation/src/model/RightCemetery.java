package model;

import gameComponent.Piece;
import gameComponent.Player;

import java.awt.*;
import java.util.List;
import java.util.Observable;


public class RightCemetery extends Observable{

	private int rows = 6;
	private int cols = 4;

	private Player yellowPlayer;
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
	public Player getYellowPlayer(){
		return  yellowPlayer;
	}
	public RightCemetery(Player player) {
		yellowPlayer = player;

		//initialise all pieces
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				yellowPlayer.addDiffPiece(Piece.Type.YellowPiece);

			}
		}
		setChanged();
		notifyObservers();
	}


}
