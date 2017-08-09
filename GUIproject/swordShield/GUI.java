package swen222.swordShield;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.border.Border;

public class GUI extends JFrame{

	 private JPanel board;
	 private JPanel leftPieces;
	 private JPanel rightPieces;
	 private boolean isGreenTurn;
	 private Player greenPlayer,yellowPlayer;
	 private String info;
	
	    
	/**
	 * The following fields cache various icons so we don't need to load them
	 * everytime.
	 */
	//basic background
	    
	public GUI(){
		
		super("Sword and Shield");
		isGreenTurn=true;
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
		

	}
	public void switchTurn(){
		isGreenTurn=!isGreenTurn;
	}
	public boolean isValidCommand(String command){
		

		String singleAct="undo|pass";
		String greenName="[a-x]";
		String yellowName="[A-X]";
		String degree = "[1-4]";
		String direction ="up|down|left|right";
		String[] line = command.split(" ");
		
		boolean firstChecker  = line.length==1&& line[0].matches(singleAct);		
		boolean secondChecker1= line.length==3&& line[0].equals("move")&&line[2].matches(direction);
		boolean secondChecker2 =line.length==3&& line[0].equals("rotate")&&line[2].matches(degree);
		boolean secondChecker3 = line.length==3&& line[0].equals("create")&&line[2].matches(degree);
		boolean thirdChecker1 = line.length==3&& isGreenTurn&&line[1].matches(greenName);
		boolean thirdChecker2 = line.length==3&& !isGreenTurn&&line[1].matches(yellowName);

		boolean fourthChecker1 =  (secondChecker1||secondChecker2||secondChecker3)&&(thirdChecker1^thirdChecker2);
		return firstChecker||fourthChecker1;		
		
	}
	public void excute(String command){
		if(command.equals("remove")){
			List<Piece> temp = greenPlayer.getPieces();
			temp.remove(1);
			temp.add(new Piece(Piece.Type.GreenPiece));
			greenPlayer.setPieces(temp);
		}
	}

	public static void main(String[] args) {
		BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
		
		GUI gui = new GUI();
		try {
			bReader=new BufferedReader(new InputStreamReader(System.in));
			while(true){
				
				System.out.println("Enter a valid command:");
				String input = bReader.readLine();
				if("q".equals(input)){
					System.out.println("Exit!");
					System.exit(0);
				}
				boolean isValid = gui.isValidCommand(input);
				if(isValid){
					System.out.println("success");
					
//					gui.excute(input);
//					gui.repaint();
				}else{
					System.out.println("fail");
				}				

	
			}
		} catch (IOException e) {
		 e.printStackTrace();
		}
	}

	

}
