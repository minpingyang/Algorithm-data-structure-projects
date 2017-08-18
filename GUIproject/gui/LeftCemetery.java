package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class LeftCemetery extends JPanel {
	private int rows = 6;
	private int cols = 4;
	private int top = 10;
	private int left = 12;
	private Player greenPlayer;
	private int widthDis = 4 * (Piece.SIZE_PIECE) + 40;
	private int heightDis = 3 * Piece.SIZE_PIECE - 10;
	private int topDis = 80 + 6 * Piece.SIZE_PIECE;


	public LeftCemetery(Player player) {
		greenPlayer = player;
		// initialise all pieces
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				greenPlayer.addDiffPiece(Piece.Type.GreenPiece);

			}
		}
		
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.cyan);
		g.fillRect(0, 0, getWidth(), getHeight());
		List<Piece> temp = greenPlayer.getPieces();
		//System.out.println("size: "+temp.size());
		int i =0;
		int outline = 13;
		for(int row = 0; row<6;row++){
			for(int col=0;col<4;col++){
				//System.out.printf("\n left: %c ,",temp.get(i).getName());
				temp.get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+ row*(Piece.SIZE_PIECE+outline),row,col);
				
			}
		}
//		g.setColor(Color.cyan);
//		g.fillRect(left, topDis, widthDis, heightDis);
//		g.setColor(Color.red);
//		g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
//		g.drawString(info, left, topDis+30);
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
