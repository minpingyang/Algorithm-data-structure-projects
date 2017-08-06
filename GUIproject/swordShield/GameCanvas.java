package swen222.swordShield;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameCanvas extends JPanel{
	JPanel board;
	JPanel leftPieces;
	JPanel rightPieces;
	
	//constructor
	// intialize the board with none pieces
	
	
	public GameCanvas() {
		board = new Board();
		leftPieces = new LeftPieces();
		rightPieces = new RightPieces();
	}
	@Override
	public void paint(Graphics g){
		board.paint(g);
		leftPieces.paint(g);
		rightPieces.paint(g);
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	
	
	@Override
	public Dimension getPreferredSize(){
		//width -> (5+10+5)*50
		//height-> (10+10)*50
		return new Dimension(1000,1000);
	}
	
	
}
