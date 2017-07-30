package swen222.swordShield;

import java.util.ArrayList;
import java.util.Random;

public class SwordShieldGame {
	private GridSquare[][] board;
	private ArrayList<Piece> greenPieces;
	private ArrayList<Piece> yellowPieces;
	private int width;
	private int height;
	/**
	 * 
	 * construcotr 
	 * @param width  --- for the board
	 * @param height
	 * */
	
	public SwordShieldGame(int width, int height) {
		board= new GridSquare[width][height];
		greenPieces = new ArrayList<Piece>();
		yellowPieces = new ArrayList<Piece>();
		this.width = width;
		this.height= height;
	}
	public boolean isOver(){
		return didGreenWin()||didYellowWin();
	}
	
	private boolean didGreenWin() {
		// TODO 
		//left face is hit by sword only green?
		return false;
	}
	private boolean didYellowWin() {
		// TODO Auto-generated method stub
		///right face is hit by sword only green?
		return false;
	}
	public void createRandomPiece(Random randomBound){
		//To start with,
		addRandomPiece(new Piece(),true,randomBound);
	}
	private void addRandomPiece(Piece piece, boolean b, Random randomBound) {
		// TODO Auto-generated method stub
		
	}
}
