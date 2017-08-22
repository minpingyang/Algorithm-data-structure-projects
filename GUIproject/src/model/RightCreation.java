package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class RightCreation extends JPanel{

	private int rows = 6;
	private int cols = 4;
	private int top = 10;
	private int left = 13;
	private Player yellowPlayer;
	private List<Piece> pieces;
	private List<Point> piecesPoint;
	public List<Piece> getPieces(){
		return pieces;
	}
	public List<Point> getPiecesPoint(){
		return piecesPoint;
	}

	public RightCreation(Player player) {
		yellowPlayer = player;
		pieces = new ArrayList<Piece>();
		piecesPoint=new ArrayList<Point>();
		//initialise all pieces
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				yellowPlayer.addDiffPiece(Piece.Type.YellowPiece);
			}
		}
	}

	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, getWidth(), getHeight());
		 pieces = yellowPlayer.getPieces();
		int i=0;
		int outline = 10;
		for(int row = 0; row<6;row++){
			for(int col=0;col<4;col++){
				pieces.get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+row*(Piece.SIZE_PIECE+outline),row,col);
				piecesPoint.add(new Point(left+col*(Piece.SIZE_PIECE+outline), top+ row*(Piece.SIZE_PIECE+outline)));
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
		return new Dimension(3*(Piece.SIZE_PIECE),9*(Piece.SIZE_PIECE));
	}
}
