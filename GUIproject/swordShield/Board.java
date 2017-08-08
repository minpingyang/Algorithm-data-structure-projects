package swen222.swordShield;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Board extends JPanel {
	Piece[][] board;
	private int rows = 10;
	private int cols = 10;
	private int left = 0*Piece.SIZE_PIECE;
	
	public Board() {
		board = new Piece[rows][cols];
		//initialise all pieces,fill all gray and while rectangle
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
			    if((row<2&&col<2)||(row>7&&col>7)){
			    	board[row][col] = new Piece(Piece.Type.OutBoard);
			    }else if((row%2==0 && col%2==0)||(row%2!=0 && col%2!=0)){
					board[row][col] = new Piece(Piece.Type.GrayGrid);
				}else{
					board[row][col] = new Piece(Piece.Type.WhiteGrid);
				}
			}
		}
		board[1][1] =new Piece(Piece.Type.LeftFace);
		board[8][8] =new Piece(Piece.Type.RightFace);
		board[2][2] = new Piece(Piece.Type.LeftCreation);
		board[7][7] = new Piece(Piece.Type.RightCreation);
		
	}
	
	@Override
	public void paint(Graphics g){
		for(int row = 0; row<10;row++){
			for(int col=0;col<10;col++){
				board[row][col].drawPiece(g,left+col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
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
		return new Dimension(10*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));
	}
}
