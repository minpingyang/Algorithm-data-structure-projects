package gui;

import gameComponent.Menu;
import gameComponent.Piece;
import gameComponent.Player;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

public class View extends JComponent {

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
	public static boolean isGreenTurn;
	private boolean doesMove;
	private boolean doesRotate;
	private JFrame frame;
	private DegreePanLeft degreePanLeft;

    private DegreePanRight degreePanRight;
	private CardLayout cardLayout1;
    private CardLayout cardLayout2,cardLayout3;
	private JPanel panelConLeft;
	private JPanel panelConRight,panelConBoard;
	private boolean isRotate;
	private Stack<Board> undoStack;
	private Stack<Character> nameStack;
	private Stack<String> degreeStack;
	private boolean hasReaction;
    private boolean doesClPieBoard=false;
	private RotationPanel rotationPanel;
    private Menu menuF;
    private JButton menuBtn;
	public  CardLayout getCardLayout3(){
		return cardLayout3;
	}
	public JPanel getPanelConBoard(){return panelConBoard;}
	public JPanel getPanelConRight(){
	    return panelConRight;
    }
    public boolean getDoesCliPieBoard(){
	    return doesClPieBoard;
    }
    public void setDoesClPieBoard(boolean b){
        doesClPieBoard=b;
    }
    public RotationPanel getRotationPanel(){
        return rotationPanel;
    }
	private DegPanLeView degPanLeView;
	private LeftCreationView leftCreationView;
	private RightCreationView rightCreationView;
	private DegPanRiView degPanRiView;
    public LeftCreationView getLeftCreationView(){return leftCreationView;}
    public DegPanLeView getDegPanLeView(){return degPanLeView;}
    private Animation animation;
    public Animation getAnimation(){return animation;}
    public DegPanRiView getDegPanRiView(){return degPanRiView;}
    public RightCreationView getRightCreationView(){return  rightCreationView;}

	public View() {



			undoStack = new Stack<Board>();
			isGreenTurn = true;
			nameStack=new Stack<Character>();
			degreeStack=new Stack<String>();

			this.setFocusable(true);

			frame = new JFrame("Sword and Shield");
			frame.setFocusable(true);
			menuF =new Menu(this);

			panelConLeft = new JPanel();
			cardLayout1 = new CardLayout();
			panelConLeft.setLayout(cardLayout1);
			degreePanLeft = new DegreePanLeft();

			 degPanLeView= new DegPanLeView(degreePanLeft);



			panelConRight=new JPanel();
			cardLayout2=new CardLayout();
			panelConRight.setLayout(cardLayout2);
			degreePanRight=new DegreePanRight();

			degPanRiView =new DegPanRiView(degreePanRight);



			board = new Board();
			BoardView boardView =new BoardView(board);

			panelConBoard=new JPanel();
			cardLayout3=new CardLayout();
			panelConBoard.setLayout(cardLayout3);
			rotationPanel=new RotationPanel(board);//TODO

			RotationPanView rotationPanView =new RotationPanView(rotationPanel);


			greenPlayer = new Player(Piece.Type.GreenPiece);
			yellowPlayer = new Player(Piece.Type.YellowPiece);




			leftCreation = new LeftCreation(greenPlayer);
			rightCreation = new RightCreation(yellowPlayer);

			leftCreationView= new LeftCreationView(leftCreation);


			rightCreationView =new RightCreationView(rightCreation);
			leftCreationView.addMouseListener(new Controller(this));
			rightCreationView.addMouseListener(new Controller(this));

		animation=new Animation(this);
		leftCreationView =animation.getPane1();
		degPanLeView=animation.getPane2();
		rightCreationView =animation.getPane3();
		degPanRiView = animation.getPane4();

			panelConLeft.add(leftCreationView, "1");
			panelConLeft.add(degPanLeView, "2");
			cardLayout1.show(panelConLeft, "1");

			panelConRight.add(rightCreationView,"3");
			panelConRight.add(degPanRiView,"4");
			cardLayout2.show(panelConRight,"3");


			panelConBoard.add(boardView,"5");
			panelConBoard.add(rotationPanView,"6");
			cardLayout3.show(panelConBoard,"5");


	//        frame.addKeyListener(new KeyController());
			frame.addMouseListener(new Controller(this));
			frame.addKeyListener(new Controller(this));
			//board.addKeyListener(new Controller(this));

			boardView.addKeyListener(new Controller(this));

			boardView.addMouseListener(new Controller(this));
			rotationPanView.addMouseListener(new Controller(this));
			degPanRiView.addMouseListener(new Controller(this));
			degPanLeView.addMouseListener(new Controller(this));
			leftCreationView.addMouseListener(new Controller(this));
			rightCreationView.addMouseListener(new Controller(this));
			leftCreationView.setFocusable(true);
			rightCreationView.setFocusable(true);

			leftCemetery = new LeftCemetery(greenPlayer);

			rightCemetery = new RightCemetery(yellowPlayer);
			LeftCemView leftCemView =new LeftCemView(leftCemetery);
			RightCemView rightCemView =new RightCemView(rightCemetery);


			menuBtn = new JButton("Menu");
			menuBtn.setFocusable(false);
			undo = new JButton("Undo");
			undo.setFocusable(false);
			pass = new JButton("Pass");
			pass.setFocusable(false);
			surrender = new JButton("Surrender");
			surrender.setFocusable(false);
			jtb = new JToolBar();
			jtb.add(undo);
			jtb.add(pass);
			jtb.add(surrender);
			jtb.add(menuBtn);
			jtb.setFocusable(false);

			jtb.setPreferredSize(new Dimension(getWidth(),40));
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			frame.add(jtb, BorderLayout.NORTH);
			frame.add(panelConBoard);
			addKeyListener(new Controller(this));
	//        addKeyListener(new KeyController());
			frame.setVisible(false);


			final JSplitPane hSplitRigt = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			final JSplitPane wholeBoard = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			final JSplitPane vSplitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			final JSplitPane vSplitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			JLabel leJLabel = new JLabel("11");
			JLabel rightLable = new JLabel("22");
			// vSplitLeft.setTopComponent(leftCreation);
			vSplitLeft.setTopComponent(panelConLeft);
			// vSplitLeft.setBottomComponent(leftCemetery);
			vSplitLeft.setBottomComponent(leJLabel);
			vSplitLeft.setResizeWeight(0.6);
			vSplitRight.setTopComponent(panelConRight);
			// vSplitRight.setBottomComponent(rightCemetery);
			vSplitRight.setBottomComponent(rightLable);
			vSplitRight.setResizeWeight(0.4);
			vSplitLeft.setContinuousLayout(true);
			vSplitLeft.setOneTouchExpandable(true);
			vSplitRight.setContinuousLayout(true);
			vSplitRight.setOneTouchExpandable(true);

			hSplitRigt.setLeftComponent(panelConBoard);
			hSplitRigt.setRightComponent(vSplitRight);
			hSplitRigt.setDividerLocation(1);
			hSplitRigt.setContinuousLayout(true);
			hSplitRigt.setOneTouchExpandable(true);

			wholeBoard.setLeftComponent(vSplitLeft);
			wholeBoard.setRightComponent(hSplitRigt);
			wholeBoard.setContinuousLayout(true);
			wholeBoard.setOneTouchExpandable(true);
			hSplitRigt.setDividerLocation(50);
			wholeBoard.addKeyListener(new Controller(this));
	//        wholeBoard.addKeyListener(new KeyController());
			frame.add(wholeBoard, BorderLayout.CENTER);
			frame.pack();

			buttonFuc();

	}


	public void buttonFuc() {
		menuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

			    frame.setVisible(false);
                menuF.setVisible(true);

			}
		});

		undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    excute("undo");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
		pass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    inputCommand("pass");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
		surrender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String[] options = new String[] {"Back to menu"};
                String message = " ";
                if(isGreenTurn) {message="Right Player WIN";}
                else{message ="Left Player WIN";}
                int response = JOptionPane.showOptionDialog(null, message, "Game Over",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                if(response==0){
                    frame.setVisible(false);
                    menuF.setVisible(true);
                }

            }
        });

	}

	public Player getPlayer(String color) {
		if (color.equals("green")) {
			return greenPlayer;
		}
		return yellowPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public Stack<Board> getUndoStack() {
		return undoStack;
	}
	/**
	 * this method is actived, when user input "pass" command
	 * firstly, clear the "undostack".  because, undo is only allowed within one turn
	 * set "hasCreate" to false.-> allow the player to create a new piece in a new turn
	 * set "hasRotate" to false -> allow the player to rotate anypice once in a new turn
	 * **/
	public void switchTurn() {
		isGreenTurn = !isGreenTurn;
		yellowPlayer.setHasCreate(false);
		greenPlayer.setHasCreate(false);
		undoStack.clear();
		nameStack.clear();
		degreeStack.clear();
		board.setHasCreate(false);
		Piece[][] temp = board.getPieceBoard();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (temp[row][col].getType() != Piece.Type.NonePiece) {
					temp[row][col].setHasMove(false);
					temp[row][col].setHasRotate(false);
					board.setPieceBoard(temp);
				}
			}
		}

	}
	/**
	 * This method is ued to check if the input command is legally in the game, but the method did not check if the command will work under the game rule
	 * for example, if the creation grid is not empty, the create does allow. but the method will return true,which means the command does folllow the grammar of the game
	 * @param command  --- the user inputer command
	 * @return boolean ----- indicate if the command is legal.
	 *
	 * **/

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
	/**
	 *
	 * @param dir ---  move direction --> up/down/right/left
	 * @param pieceName --- the name of piece which will be moved in given direction
	 * @return boolean --->> true ->indicate move the piece successfully
	 * @throws InterruptedException
	 *
	 * **/
	public boolean move(String dir, char pieceName) throws InterruptedException {
		if(board.checkNeighbour(pieceName, dir)){
			while(!board.getMoveQueue().isEmpty()){
				char neighbourName = board.getMoveQueue().poll();
				board.movePiece(neighbourName, dir, true);
			}
		}
		return board.movePiece(pieceName, dir, false);
	}
	/**
	 *
	 * @param degree- the degree of the piece will be rotated --- 1/2/3/4
	 * @param pieceName -  the name of piece which will be rotated in given degreee
	 * @return boolean --- true ->indicate rotate the piece successfully
	 * **/
	public boolean rotate(String degree, char pieceName,boolean nonUndo) {
		return board.rotatePiece(pieceName, degree,nonUndo);
	}
	/**
	 *
	 * @param degree-- the degree of the piece will be created --- 1/2/3/4
	 * @param pieceName - > the name of piece which will be created in given degreee
	 * @return boolean --- true ->indicate create the piece successfully
	 * **/
	public boolean create(String degree, char pieceName) {
		System.out.println("degree"+degree+"    name:"+pieceName);
		boolean doesCreate = false;
		if (isGreenTurn) {// green piece
			List<Piece> gPieces = greenPlayer.getPieces();//get the list of pieces the particular player has
			for (Piece piece : gPieces) {

				if (piece.getName() == pieceName) {
					int index = gPieces.indexOf(piece);


                    if(!greenPlayer.getHasCreate()){
                        gPieces.set(index, new Piece(Piece.Type.EmptyPiece));
                        greenPlayer.setPieces(gPieces);
                        piece.rotate(degree);
                        doesCreate = board.createPiece(piece);// create the piece in the board instance
                    }


                    if(doesCreate) {
                        piece.setIsHighLight(false);
                        greenPlayer.setHasCreate(true);
                    }
                }
			}
		} else {
			// System.out.println("yellow turn");
			List<Piece> pieces = yellowPlayer.getPieces();
			for (Piece piece : pieces) {

				if (piece.getName() == pieceName) {
                    if(!yellowPlayer.getHasCreate()){
                        int index = pieces.indexOf(piece);
                        pieces.set(index, new Piece(Piece.Type.EmptyPiece));
                        yellowPlayer.setPieces(pieces);
                        piece.rotate(degree);
                        doesCreate = board.createPiece(piece);
                    }

                    if(doesCreate) {
                        piece.setIsHighLight(false);
                        yellowPlayer.setHasCreate(true);
                    }

				}
			}
		}

		return doesCreate;
	}
	/**
	 * assign the last baord to current board, the current board state changed back to previous board
	 * For undo rotate, I just rotate the piece in a opposite direction.
	 * */
	public void undo() {
		if(isRotate&&!degreeStack.isEmpty()&&!nameStack.isEmpty()){

			String degree =degreeStack.pop();
			char pieceName =nameStack.pop();
			System.out.println("name: "+pieceName+" degree"+degree);
			if(degree.equals("2")){

				rotate("4", pieceName,false);
			}else if(degree.equals("3")){

				rotate("3", pieceName,false);
			}else if(degree.equals("4")){

				rotate("2", pieceName,false);
			}else if(degree.equals("1")){

				rotate("1", pieceName,false);
			}
		}
		board= deepCloneBoard(undoStack.pop());


	}

	/**
	 *
	 * @param board - > the board want to be deep cloned
	 * @return Board--> return the board has already deep cloned from the passing board
	 * **/
	public Board deepCloneBoard(Board board) {
		//System.out.println("deepclone last board ");
		Board temp = new Board();
		temp.setHasCreate(board.getHasCreate());
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				temp.getPiecesBoard()[row][col]=board.getPiecesBoard()[row][col];
				if (board.getPiecesBoard()[row][col].getType() == Piece.Type.YellowPiece
						|| board.getPiecesBoard()[row][col].getType() == Piece.Type.GreenPiece) {
					temp.getPiecesBoard()[row][col].deepClone(board.getPiecesBoard()[row][col]);
//					System.out.println("temp undo !!!!weapon");
//					temp.piecesBoard[row][col].printWeapon();
//					System.out.println("temp undo !!!!weapon");
				}

			}
		}
		return temp;
		// undoStack.add(temp);

	}

	/**
	 * This method is used after the command has be confirmed without grammar error,
	 * then pass the commad in this method to excute the command
	 *
	 * @param   command  the grammar correct command is passed, which may be excuted in this method
	 * @return   return a boolean value indicate if excutes the command successfully or not
	 *
	 * **/
	public boolean excute(String command) throws InterruptedException {
		boolean doActionSuccess = false;
		boolean doesAct = false; // the boolean is used to determine if should check interaction between pieces
		Board temp = deepCloneBoard(board);

		String[] line = command.split(" ");
		if (line.length == 1) {
			if (line[0].equals("pass")) {
				switchTurn();
				doActionSuccess = true;
				isRotate=false;
			} else if (line[0].equals("undo")) {
				if(!undoStack.isEmpty()){
					undo();
					doActionSuccess = true;
					isRotate=false;
				}


			}
		} else if (line.length == 3) {

			if (line[0].equals("create")) {
				char pieceName = line[1].charAt(0);
				String degree = line[2];
				boolean doesCreate = create(degree, pieceName);
				doActionSuccess = doesCreate;
				doesAct = doActionSuccess; //
				if(doesCreate){ //only if create successfully
					undoStack.add(temp);
					isRotate=false;
				}

			} else if (line[0].equals("move")) {
				char pieceName = line[1].charAt(0);
				String dir = line[2];

				boolean doesMove = move(dir, pieceName);
				doActionSuccess = doesMove;
				doesAct = doActionSuccess;
				if(doesMove){
				    board.findPieceOnBoard(pieceName).setIsHighLight(false);
					undoStack.add(temp);
					isRotate=false;
				}

			} else if (line[0].equals("rotate")) {
				// System.out.println("11111");
				char pieceName = line[1].charAt(0);
				String degree = line[2];
				boolean doesRotate=rotate(degree, pieceName,true);
				doActionSuccess = doesRotate;
				doesAct = doActionSuccess;

				if(doesRotate){
					isRotate=true;
					degreeStack.add(degree);
					nameStack.add(pieceName);
					undoStack.add(temp);
				}

			}

		}
		//only when create/move/rotate command happened, the check if there is a intereation between the piece with its neighbours
		if (doesAct) {
			hasReaction = board.checkReaction();
			if (hasReaction) {
				System.out.println("REACTION EXCUTED!!");
			}
		}

//		 System.out.println("stack size"+undoStack.size());
		// leftPieces.setInfo(info);
		return doActionSuccess;
	}
	public int doesWin(){
		int result =board.doesWin();
		if(result==1){
			System.out.println("Left Player Loose!");
		}else if(result==2){
			System.out.println("Right Player Loose!");
		}


		return 0;
	}

	public void inputCommand(String input) throws InterruptedException {


		boolean isValid = isValidCommand(input);
		System.out.println("input command :  "+input );
		if (isValid) {
			boolean doesExcute = excute(input);

			System.out.print("Command does follow the formate");
			if (!doesExcute) {
				System.out.println(", but doesnt follow the rule of game");
			} else {
				System.out.println();

				frame.repaint();

				System.out.println();
			}
			int isGameStop = doesWin();
			if (isGameStop != 0) {
				System.out.println("Game Over");

			}

		} else if (!isValid) {
			System.out.println("illegal Command ");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}



	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}

	/**
	 * @return the leftCreation
	 */
	public LeftCreation getLeftCreation() {
		return leftCreation;
	}

	/**
	 * @param leftCreation
	 *            the leftCreation to set
	 */
	public void setLeftCreation(LeftCreation leftCreation) {
		this.leftCreation = leftCreation;
	}

	/**
	 * @return the rightCreation
	 */
	public RightCreation getRightCreation() {
		return rightCreation;
	}

	/**
	 * @param rightCreation
	 *            the rightCreation to set
	 */
	public void setRightCreation(RightCreation rightCreation) {
		this.rightCreation = rightCreation;
	}


	public Player getGreenPlayer() {
		return greenPlayer;
	}


	public void setGreenPlayer(Player greenPlayer) {
		this.greenPlayer = greenPlayer;
	}


	public Player getYellowPlayer() {
		return yellowPlayer;
	}


	public void setYellowPlayer(Player yellowPlayer) {
		this.yellowPlayer = yellowPlayer;
	}


	public JFrame getJFrame() {
		return frame;
	}


	public void setJFrame(JFrame frame) {
		this.frame = frame;
	}

	public DegreePanLeft getDegreePanLeft() {
		return degreePanLeft;
	}

	public void setDegreePanLeft(DegreePanLeft degreePanLeft) {
		this.degreePanLeft = degreePanLeft;
	}

	public CardLayout getCardLayout1() {
		return cardLayout1;
	}

	public void setCardLayout1(CardLayout cardLayout1) {
		this.cardLayout1 = cardLayout1;
	}

	public JPanel getPanelConLeft() {
		return panelConLeft;
	}

	public void setPanelConLeft(JPanel panelConLeft) {
		this.panelConLeft = panelConLeft;
	}
    public CardLayout getCardLayout2() {
        return cardLayout2;
    }
    public DegreePanRight getDegreePanRight() {
        return degreePanRight;
    }
}
