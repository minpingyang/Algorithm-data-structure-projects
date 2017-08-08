package swen222.swordShield;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import swen222.swordShield.Piece.Type;

public class LeftPieces extends JPanel {

	private int rows = 6;
	private int cols = 4;
	private int top = 10;
	private int left = 12;
	private Player greenPlayer;
	
	
	public LeftPieces(Player player) {
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
		List<Piece> temp = greenPlayer.getPieces();
		//System.out.println("size: "+temp.size());
		int i =0;
		int outline = 13;
		for(int row = 0; row<6;row++){
			for(int col=0;col<4;col++){
				System.out.printf("\n left: %c ,",temp.get(i).getName());
				temp.get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+ row*(Piece.SIZE_PIECE+outline),row,col);
				
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
