package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;



public class View extends JComponent implements Observer{
	private JButton startGame;
	private JButton exitGame;
	private JButton info;
	private Board board;
	private LeftPieces leftPieces;
	private RightPieces rightPieces;
	private Player greenPlayer,yellowPlayer;
	
	public View(){
		this.setFocusable(true);
		JFrame frame= new JFrame("Sword and Shield");
		JPanel menu = new JPanel();
		greenPlayer=new Player(Piece.Type.GreenPiece);
		yellowPlayer=new Player(Piece.Type.YellowPiece);
		board = new Board();
		leftPieces = new LeftPieces(greenPlayer);
		rightPieces = new RightPieces(yellowPlayer);
		menu.setLayout(new FlowLayout());
		startGame= new JButton("Begin new game");
		exitGame = new JButton("Quit");
		info =new JButton("Info");
		menu.add(startGame);
		menu.add(exitGame);
		menu.add(info);
		menu.setBackground(Color.pink);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.CENTER);
		frame.add(leftPieces,BorderLayout.WEST);
		frame.add(board,BorderLayout.CENTER);
		frame.add(rightPieces,BorderLayout.EAST);
		frame.add(menu,BorderLayout.NORTH);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		buttonFuc();
		
	}
	public void buttonFuc(){
		startGame.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
	           new View();
	  
	           }
	       });
		exitGame.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
		           	System.exit(0);
	  
		           }
		       });
		info.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent ev) {
		           	System.out.println("infomation>.....");
		  
		           }
		       });
	}
	
    @Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
	}
   
	@Override
	public void update(Observable o, Object arg) {repaint();}
	public Dimension getPreferredSize(){
		return new Dimension(600,600);
	}
}
