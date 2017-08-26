package model;


import gameComponent.Piece;
import gameComponent.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


@SuppressWarnings("serial")
public class LeftCreation extends Observable implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rows = 6;
	private int cols = 4;
	private float alpha =1f;
	private Timer timer= new Timer(100,this);
	private Player greenPlayer;
	private List<Piece> pieces;
	private List<Point> piecesPoint; //the coordinator of Point is the piece left-top location
	public List<Piece> getPieces(){
		return pieces;
	}

	public void startTimer(){
		timer.start();
	}

	public Player getGreenPlayer(){
		return greenPlayer;
	}
	public void setPieces(List<Piece> temp){
		pieces=temp;
		setChanged();
		notifyObservers();
	}
	public List<Point> getPiecesPoint(){
		return piecesPoint;
	}

	public LeftCreation(Player player) {

		pieces=new ArrayList<Piece>();
		piecesPoint=new ArrayList<Point>();
		greenPlayer=player;
		
		//initialise all pieces
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				greenPlayer.addDiffPiece(Piece.Type.GreenPiece);

			}
		}
		setChanged();
		notifyObservers();

	}
	public float getAlpha(){return alpha;}

	@Override
	public void actionPerformed(ActionEvent e) {
		alpha += -0.03f;
		if (alpha <= 0) {
			alpha = 0;
			timer.stop();
		}
		setChanged();
		notifyObservers();
	}
}