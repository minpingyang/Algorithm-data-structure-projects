package model;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;



@SuppressWarnings("serial")
public class LeftCreation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rows = 6;
	private int cols = 4;
	private int top = 10;
	private int left = 12;
	private Player greenPlayer;
	private List<Piece> pieces;
	private List<Point> piecesPoint; //the coordinator of Point is the piece left-top location
	public List<Piece> getPieces(){
		return pieces;
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

	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, getWidth(), getHeight());
		pieces = greenPlayer.getPieces();
		//System.out.println("size: "+temp.size());
		int i =0;
		int outline = 13;
		for(int row = 0; row<6;row++){
			for(int col=0;col<4;col++){
				//System.out.printf("\n left: %c ,",temp.get(i).getName());
				pieces.get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+ row*(Piece.SIZE_PIECE+outline),row,col);
				
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
		return new Dimension(5*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));
	}
}