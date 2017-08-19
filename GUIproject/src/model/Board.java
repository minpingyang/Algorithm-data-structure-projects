package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;



public class Board extends JPanel {
	Piece[][] board;
	private int rows = 10;
	private int cols = 10;
	private int left =0;
	private int top =0;
	Piece[][]piecesBoard;
	private int pCol=0,pRow=0;
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
	public Piece[][] getPieceBoard(){
		return piecesBoard;
	}
	public void setPieceBoard(Piece[][] temp){
		piecesBoard= temp;
	}
	public void createPiece(Piece temp){
		if(temp.getType()==Piece.Type.GreenPiece){
			piecesBoard[2][2]=temp;
		}else if(temp.getType()==Piece.Type.YellowPiece){
			piecesBoard[7][7]=temp;
		}
		
	}
	
	public boolean rotatePiece(char pieceName,String degree){
		Piece temp =findPieceOnBoard(pieceName);
		System.out.println("222222222222");
		if(temp==null||temp.getHasRotate()||temp.getHasMove()){
			
			return false;
		}
		System.out.println("111111111");
		temp.printWeapon();
		temp.rotate(degree);
		temp.printWeapon();
		temp.setHasRotate(true);
		piecesBoard[pRow][pCol]= temp;
		return true;
	}
	
	public Piece findPieceOnBoard(char pieceName){
		Piece temp =null;
		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				if((row<2&&col<2)||(row>7&&col>7))
					continue;
				if(pieceName==piecesBoard[row][col].getName()){
					temp=piecesBoard[row][col];
					pCol= col;
					pRow=row;
				}
			}
		}
		return temp;
	}

	//return true-->allow move 
	public boolean moveHelper(int row,int col){
		boolean outborder= row>9||col>9||row<0||col<0;//true ->out of border
		boolean inForbiddenArea= (row<2&&col<2)||(row>7&&col>7);// true->in forbidden
		return !(outborder||inForbiddenArea);
	}
	//return a boolean indicates if move the piece successfully
	public boolean movePiece(char pieceName,String dir){
		Piece temp =findPieceOnBoard(pieceName);
		if(temp==null||temp.getHasMove()||temp.getHasRotate()){
			return false;
		}
		if(dir.equals("up")){
			if(!(moveHelper(pRow-1, pCol))){
				return false;
			}else{
				temp.setHashMove(true);
				piecesBoard[pRow][pCol]=new Piece(Piece.Type.NonePiece); // REMOVE 
				pRow = pRow-1;
				piecesBoard[pRow][pCol] =temp; //add 
			}
		}else if(dir.equals("down")){
			if(!(moveHelper(pRow+1, pCol))){
				return false;
			}else{
				temp.setHashMove(true);
				piecesBoard[pRow][pCol]=new Piece(Piece.Type.NonePiece); // REMOVE 
				pRow = pRow+1;
				piecesBoard[pRow][pCol] =temp; //add 
			}
		}else if(dir.equals("left")){
			if(!(moveHelper(pRow, pCol-1))){
				return false;
			}else{
				temp.setHashMove(true);
				piecesBoard[pRow][pCol]=new Piece(Piece.Type.NonePiece); // REMOVE 
				pCol = pCol-1;
				piecesBoard[pRow][pCol] =temp; //add 
			}
		}else if(dir.equals("right")){
			if(!(moveHelper(pRow, pCol+1))){
				return false;
			}else{
				temp.setHashMove(true);
				piecesBoard[pRow][pCol]=new Piece(Piece.Type.NonePiece); // REMOVE 
				pCol = pCol+1;
				piecesBoard[pRow][pCol] =temp; //add 
			}
		}
		
		return true;
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(int row = 0; row<10;row++){
			for(int col=0;col<10;col++){
				board[row][col].drawPiece(g,left+col*Piece.SIZE_PIECE,top+row*Piece.SIZE_PIECE,row,col);
			}
		}
		for(int row = 0; row<10;row++){
			for(int col=0;col<10;col++){
				piecesBoard[row][col].drawPiece(g,left+col*Piece.SIZE_PIECE,top+row*Piece.SIZE_PIECE,row,col);
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
		return new Dimension(8*(Piece.SIZE_PIECE),10*(Piece.SIZE_PIECE));
	}
}

