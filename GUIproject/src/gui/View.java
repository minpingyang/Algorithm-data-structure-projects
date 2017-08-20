package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.datatransfer.FlavorMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import model.Board;
import model.LeftCemetery;
import model.LeftCreation;
import model.Piece;
import model.Player;
import model.RightCemetery;
import model.RightCreation;



public class View extends JComponent implements Observer, Runnable{
	private JButton startGame;
	private JButton exitGame;
	private JButton info;
	private JButton undo;
	private JButton pass;
	private JButton surrender;
	private Board board;
	private LeftCreation leftCreation;
	private RightCreation rightCreation;
	private LeftCemetery leftCemetery;
	private RightCemetery rightCemetery;
	private Player greenPlayer,yellowPlayer;
	private JToolBar jtb;
	public View(){
		
		this.setFocusable(true);
		JFrame frame= new JFrame("Sword and Shield");
		JPanel menu = new JPanel();
		greenPlayer=new Player(Piece.Type.GreenPiece);
		yellowPlayer=new Player(Piece.Type.YellowPiece);
		board = new Board();
		leftCreation = new LeftCreation(greenPlayer);
		rightCreation = new RightCreation(yellowPlayer);
		leftCreation.addMouseListener(new Controller(leftCreation,rightCreation,greenPlayer,yellowPlayer,frame));
		rightCreation.addMouseListener(new Controller(leftCreation,rightCreation,greenPlayer,yellowPlayer,frame));
		//addMouseListener(new Controller(leftCreation,rightCreation,greenPlayer,yellowPlayer));
		leftCemetery=new LeftCemetery(greenPlayer);
		rightCemetery=new RightCemetery(yellowPlayer);
		menu.setLayout(new FlowLayout());
		startGame= new JButton("Begin new game");
		exitGame = new JButton("Quit");
		info =new JButton("Info");
		menu.add(startGame);
		menu.add(exitGame);
		menu.add(info);
		undo=new JButton("Undo");
		pass =new JButton("Pass");
		surrender =new JButton("Surrender");
		jtb=new JToolBar();
		jtb.add(undo);
		jtb.add(pass);
		jtb.add(surrender);
		jtb.add(menu);
		jtb.setBackground(Color.PINK);
		menu.setBackground(Color.pink);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(jtb,BorderLayout.NORTH);
	
		frame.setVisible(true);
		
		//frame.addMouseListener(new Controller(leftCreation,rightCreation,greenPlayer,yellowPlayer));
		final JSplitPane hSplitRigt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		final JSplitPane wholeBoard = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		final JSplitPane vSplitLeft =new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		final JSplitPane vSplitRight=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JLabel leJLabel =new JLabel("11");
		JLabel rightLable = new JLabel("22");
		vSplitLeft.setTopComponent(leftCreation);
//		vSplitLeft.setBottomComponent(leftCemetery);
		vSplitLeft.setBottomComponent(leJLabel);
		vSplitLeft.setResizeWeight(0.6);
		vSplitRight.setTopComponent(rightCreation);
//		vSplitRight.setBottomComponent(rightCemetery);
		vSplitRight.setBottomComponent(rightLable);
		vSplitRight.setResizeWeight(0.4);
		vSplitLeft.setContinuousLayout(true);
		vSplitLeft.setOneTouchExpandable(true);
		vSplitRight.setContinuousLayout(true);
		vSplitRight.setOneTouchExpandable(true);
		
		hSplitRigt.setLeftComponent(board);
		hSplitRigt.setRightComponent(vSplitRight);
		hSplitRigt.setDividerLocation(1);
		hSplitRigt.setContinuousLayout(true);
		hSplitRigt.setOneTouchExpandable(true);
		
		wholeBoard.setLeftComponent(vSplitLeft);
		wholeBoard.setRightComponent(hSplitRigt);
		wholeBoard.setContinuousLayout(true);
		wholeBoard.setOneTouchExpandable(true);
		hSplitRigt.setDividerLocation(50);
		frame.add(wholeBoard,BorderLayout.CENTER);
		
		frame.pack();
		//frame.setResizable(false);
		
		
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
