package swen222.swordShield;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RightPieces extends JPanel{
	Piece[][] RightPieces;
	private int rows = 6;
	private int cols = 4;

	private int top = 10;
	private int left = 13;
	
	public RightPieces() {
		RightPieces = new Piece[rows][cols];
		//initialise all pieces
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				RightPieces[row][col] = new Piece(Piece.Type.YellowPiece);
			}
		}
	}
	
	@Override
	public void paint(Graphics g){
		int outline = 10;
		for(int row = 0; row<6;row++){
			for(int col=0;col<4;col++){
				RightPieces[row][col].drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+row*(Piece.SIZE_PIECE+outline));
			}
		}
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	@Override
	public Dimension getPreferredSize(){
		//width -> (5)*50
		//height-> (10+10)*50
		return new Dimension(5*(Piece.SIZE_PIECE),9*(Piece.SIZE_PIECE));
	}
}
