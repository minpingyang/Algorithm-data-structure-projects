package swen222.swordShield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import swen222.swordShield.Piece.Type;

public class Board extends JPanel {
	Piece[][] board;
	private int rows = 10;
	private int cols = 10;
	Piece[][]piecesBoard;
	
	public Board() {
		board = new Piece[rows][cols];
		piecesBoard = new Piece[rows][cols];
		//initialise all pieces,fill all gray and while rectangle
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				piecesBoard[row][col] =new Piece(Piece.Type.NonePiece);
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
	public Piece[][] getBoard(){
		return board;
	}
	public void createPiece(Piece temp){
		if(temp.getType()==Piece.Type.GreenPiece){
			piecesBoard[2][2]=temp;
		}else if(temp.getType()==Piece.Type.YellowPiece){
			piecesBoard[7][7]=temp;
		}
		
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(int row = 0; row<10;row++){
			for(int col=0;col<10;col++){
				board[row][col].drawPiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
			}
		}
		for(int row = 0; row<10;row++){
			for(int col=0;col<10;col++){
				piecesBoard[row][col].drawPiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
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
