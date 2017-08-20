package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.datatransfer.FlavorMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
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

public class View extends JComponent implements Observer {
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
	private Player greenPlayer, yellowPlayer;
	private JToolBar jtb;
	private boolean isGreenTurn;
	private boolean doesMove;
	private boolean doesRotate;

	public View() {

		this.setFocusable(true);
		JFrame frame = new JFrame("Sword and Shield");
		JPanel menu = new JPanel();
		greenPlayer = new Player(Piece.Type.GreenPiece);
		yellowPlayer = new Player(Piece.Type.YellowPiece);
		board = new Board();
		leftCreation = new LeftCreation(greenPlayer);
		rightCreation = new RightCreation(yellowPlayer);
		leftCreation
				.addMouseListener(new Controller(leftCreation, rightCreation, greenPlayer, yellowPlayer, frame, this));
		rightCreation
				.addMouseListener(new Controller(leftCreation, rightCreation, greenPlayer, yellowPlayer, frame, this));
		// addMouseListener(new
		// Controller(leftCreation,rightCreation,greenPlayer,yellowPlayer));
		leftCemetery = new LeftCemetery(greenPlayer);
		rightCemetery = new RightCemetery(yellowPlayer);
		menu.setLayout(new FlowLayout());
		startGame = new JButton("Begin new game");
		exitGame = new JButton("Quit");
		info = new JButton("Info");
		menu.add(startGame);
		menu.add(exitGame);
		menu.add(info);
		undo = new JButton("Undo");
		pass = new JButton("Pass");
		surrender = new JButton("Surrender");
		jtb = new JToolBar();
		jtb.add(undo);
		jtb.add(pass);
		jtb.add(surrender);
		jtb.add(menu);
		jtb.setBackground(Color.PINK);
		menu.setBackground(Color.pink);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(jtb, BorderLayout.NORTH);

		frame.setVisible(true);
		JPanel panelContainter = new JPanel();
		
		
		// frame.addMouseListener(new
		// Controller(leftCreation,rightCreation,greenPlayer,yellowPlayer));
		final JSplitPane hSplitRigt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		final JSplitPane wholeBoard = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		final JSplitPane vSplitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		final JSplitPane vSplitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JLabel leJLabel = new JLabel("11");
		JLabel rightLable = new JLabel("22");
		vSplitLeft.setTopComponent(leftCreation);
		// vSplitLeft.setBottomComponent(leftCemetery);
		vSplitLeft.setBottomComponent(leJLabel);
		vSplitLeft.setResizeWeight(0.6);
		vSplitRight.setTopComponent(rightCreation);
		// vSplitRight.setBottomComponent(rightCemetery);
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
		frame.add(wholeBoard, BorderLayout.CENTER);
		
		frame.pack();

		buttonFuc();

	}

	public void buttonFuc() {
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

	public void switchTurn() {
		isGreenTurn = !isGreenTurn;
		Piece[][] temp = board.getPieceBoard();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (temp[row][col].getType() != Piece.Type.NonePiece) {
					temp[row][col].setHashMove(false);
					temp[row][col].setHasRotate(false);
					board.setPieceBoard(temp);
				}
			}
		}

	}

	public boolean isValidCommand(String command) {

		String singleAct = "undo|pass";
		String greenName = "[a-x]";
		String yellowName = "[A-X]";
		String degree = "[1-4]";
		String direction = "up|down|left|right";
		String[] line = command.split(" ");

		boolean firstChecker = line.length == 1 && line[0].matches(singleAct);
		boolean secondChecker1 = line.length == 3 && line[0].equals("move") && line[2].matches(direction);
		boolean secondChecker2 = line.length == 3 && line[0].equals("rotate") && line[2].matches(degree);
		boolean secondChecker3 = line.length == 3 && line[0].equals("create") && line[2].matches(degree);
		boolean thirdChecker1 = line.length == 3 && isGreenTurn && line[1].matches(greenName);
		boolean thirdChecker2 = line.length == 3 && !isGreenTurn && line[1].matches(yellowName);

		boolean fourthChecker1 = (secondChecker1 || secondChecker2 || secondChecker3)
				&& (thirdChecker1 ^ thirdChecker2);
		return firstChecker || fourthChecker1;

	}

	public boolean move(String dir, char pieceName) {
		return board.movePiece(pieceName, dir);
	}

	public boolean rotate(String degree, char pieceName) {
		return board.rotatePiece(pieceName, degree);
	}

	public void create(String degree, char pieceName) {

		if (isGreenTurn) {// green piece
			List<Piece> gPieces = greenPlayer.getPieces();
			for (Piece piece : gPieces) {

				if (piece.getName() == pieceName) {
					int index = gPieces.indexOf(piece);
					gPieces.set(index, new Piece(Piece.Type.EmptyPiece));
					greenPlayer.setPieces(gPieces);
					piece.rotate(degree);
					board.createPiece(piece);
				}
			}

		} else {
			// System.out.println("yellow turn");
			List<Piece> pieces = yellowPlayer.getPieces();
			for (Piece piece : pieces) {

				if (piece.getName() == pieceName) {
					int index = pieces.indexOf(piece);
					pieces.set(index, new Piece(Piece.Type.EmptyPiece));
					yellowPlayer.setPieces(pieces);
					piece.rotate(degree);
					board.createPiece(piece);
				}
			}
		}
	}

	// if valid command
	public void excute(String command) {
		String[] line = command.split(" ");
		if (line.length == 1) {

			if (line[0].equals("pass")) {
				switchTurn();
			}
		} else if (line.length == 3) {

			if (line[0].equals("create")) {
				char pieceName = line[1].charAt(0);
				String degree = line[2];
				create(degree, pieceName);
			} else if (line[0].equals("move")) {
				char pieceName = line[1].charAt(0);
				String dir = line[2];
				doesMove = move(dir, pieceName);
			} else if (line[0].equals("rotate")) {
				// System.out.println("11111");
				char pieceName = line[1].charAt(0);
				String degree = line[2];
				doesRotate = rotate(degree, pieceName);
			}

		}
	

	}

	public void inputCommand(String input) {
	
		System.out.println("Enter a valid command:");

		boolean isValid = isValidCommand(input);
		if (isValid) {
			System.out.println("success");

			excute(input);
			repaint();
		} else {
			System.out.println("fail");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	

}
