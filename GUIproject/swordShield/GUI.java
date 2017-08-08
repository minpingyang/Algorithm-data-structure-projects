package swen222.swordShield;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GUI extends JFrame{

	 private JPanel board;
	 private JPanel leftPieces;
	 private JPanel rightPieces;
	 private JTextArea textOutputArea;
	 private JTextField textInputArea;
	 private static final int TEXT_OUTPUT_ROWS = 5;
	 private static final int TEXT_INPUT_COLS = 35;
	 private Player greenPlayer,yellowPlayer;
	
	    
	/**
	 * The following fields cache various icons so we don't need to load them
	 * everytime.
	 */
	//basic background
	    
	public GUI(){
		super("Sword and Shield");

		greenPlayer=new Player(Piece.Type.GreenPiece);
		yellowPlayer=new Player(Piece.Type.YellowPiece);
		
		board = new Board();
		leftPieces = new LeftPieces(greenPlayer);
		rightPieces = new RightPieces(yellowPlayer);
		setLayout(new BorderLayout()); // use border layout
		
		add(leftPieces,BorderLayout.WEST);
		add(board,BorderLayout.CENTER);
		add(rightPieces,BorderLayout.EAST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); //pack components tightly together
		setResizable(false); //prevent us from being resizeable
		setVisible(true);  // make sure we are visible
		
		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textInputArea = new JTextField(TEXT_INPUT_COLS);
        textInputArea.setMaximumSize(new Dimension(0, 25));
        textInputArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onInput();
				
			}

			
        });
	}
	
	private void onInput() {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		new GUI();
	}



}
