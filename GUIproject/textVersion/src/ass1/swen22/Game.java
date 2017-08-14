package ass1.swen22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Stack;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;
/***
 This is Game class is the main class of the game
 user input the command in the main class
 There are all of the instance of all classes of the project
 @author minpingyang 
 **/
public class Game {
	private Board board; //the game board
	private LeftPieces leftPieces; //left user has pieces
	private RightPieces rightPieces; 
	public static boolean isGreenTurn; // the boolean is true, which means now its greenPlayer turn, otherwise its yellowPlayer turn
	private Player greenPlayer, yellowPlayer;
	private String info = "Welcome";
	private Stack<Board> undoStack; // the stack is used to store all of the board states within one turn
	private boolean hasReaction;// the boolean is used to indicate if there is reaction happen after a user command inputed
	private Stack<Character> nameStack;
	private Stack<String> degreeStack;
	private boolean isRotate;
	

	public boolean getHasReaction() {
		return hasReaction;
	}
	/***
	 * The constructor of the game class
	 * initially, both players will generate 24 random pieces belonging to them own.
	 * set the current turn is greenPlayer turn
	 * leftpieces->> belong to green player
	 * rightpieces--> belong to yellow player 
	 * **/
	public Game() {
		undoStack = new Stack<Board>();
		isGreenTurn = true;
		greenPlayer = new Player(Piece.Type.GreenPiece); //every players has their own type 24 random pieces 
		yellowPlayer = new Player(Piece.Type.YellowPiece);
		board = new Board();
		nameStack=new Stack<Character>();
		degreeStack=new Stack<String>();
		leftPieces = new LeftPieces(greenPlayer);  // here, randomly generate 24 pieces which stored in greenPlayer
		rightPieces = new RightPieces(yellowPlayer);

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
		undoStack.clear();
		nameStack.clear();
		degreeStack.clear();
		board.setHasCreate(false);
		Piece[][] temp = board.getPieceBoard();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				if (temp[row][col].getType() != Piece.Type.GridPiece) {
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
	 * @param --command  --- the user inputer command
	 * @return --boolean ----- indicate if the command is legal.
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
	 * @param -dir --->  move direction --> up/down/right/left
	 * @param -pieceName --->> the name of piece which will be moved in given direction
	 * @return boolean --->> true ->indicate move the piece successfully
	 * 
	 * **/
	public boolean move(String dir, char pieceName) {
		return board.movePiece(pieceName, dir, false);
	}
	/**
	 * 
	 * @param -degree-> the degree of the piece will be rotated ---> 1/2/3/4
	 * @param pieceName - > the name of piece which will be rotated in given degreee
	 * @return boolean --->> true ->indicate rotate the piece successfully
	 * **/
	public boolean rotate(String degree, char pieceName,boolean nonUndo) {
		return board.rotatePiece(pieceName, degree,nonUndo);
	}
	/**
	 * 
	 * @param -degree-> the degree of the piece will be created ---> 1/2/3/4
	 * @param pieceName - > the name of piece which will be created in given degreee
	 * @return boolean --->> true ->indicate create the piece successfully
	 * **/
	public boolean create(String degree, char pieceName) {
		boolean doesCreate = false;
		if (isGreenTurn) {// green piece
			List<Piece> gPieces = greenPlayer.getPieces();//get the list of pieces the particular player has
			for (Piece piece : gPieces) {

				if (piece.getName() == pieceName) {
					int index = gPieces.indexOf(piece);
					gPieces.set(index, new Piece(Piece.Type.EmptyPiece));
					greenPlayer.setPieces(gPieces);
					piece.rotate(degree);
					doesCreate = board.createPiece(piece);// create the piece in the board instance
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
					doesCreate = board.createPiece(piece);
				}
			}
		}
		return doesCreate;
	}
	/**
	 * assign the last baord to current board, the current board state changed back to previous board
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
		System.out.println("deepclone last board ");
		Board temp = new Board();
		temp.setHasCreate(board.getHasCreate());
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				temp.piecesBoard[row][col] = board.piecesBoard[row][col];
				if (board.piecesBoard[row][col].getType() == Piece.Type.YellowPiece
						|| board.piecesBoard[row][col].getType() == Piece.Type.GreenPiece) {
					temp.piecesBoard[row][col].deepClone(board.piecesBoard[row][col]);
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
	 * @param   --command  the grammar correct command is passed, which may be excuted in this method 
	 * @return ---boolean -->  return a boolean value indicate if excutes the command successfully or not
	 * 
	 * **/
	public boolean excute(String command) throws InterruptedException {
		boolean doActionSuccess = false;
		boolean doesAct = false; // the boolean is used to determine if should check interaction between pieces
		Board temp = deepCloneBoard(board);
		
		String[] line = command.split(" ");
		if (line.length == 1) {
			info = line[0];
			if (info.equals("pass")) {
				switchTurn();
				doActionSuccess = true;
				isRotate=false;
			} else if (info.equals("undo")) {
				if(!undoStack.isEmpty()){
					undo();
					doActionSuccess = true;
					isRotate=false;
				}
				
				
			}
		} else if (line.length == 3) {
			
			info = line[0] + "  " + line[1] + "  " + line[2];
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
		// System.out.println("stack size"+undoStack.size());
		// leftPieces.setInfo(info);
		return doActionSuccess;
	}
	/**
	 * This method is used to print out the board
	 * **/
	public void printOutBoard() {
		board.printBoard();
	}
	/***
	 * The main method is used for input and output the game.
	 * user input a command, the systerm will response if it is a valid command or not. Besides, the system also will indicate
	 * if the command does follow the rule of the game
	 *
	 *
	 *input ->  q   to quit  the game immediately
	 * **/
	public static void main(String[] args) throws InterruptedException {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

		Game game = new Game();
		game.printOutBoard();

		try {
			bReader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {

				System.out.println("Enter a valid command:");
				String input = bReader.readLine();
				if ("q".equals(input)) {
					System.out.println("Exit!");
					System.exit(0);
				}
				boolean isValid = game.isValidCommand(input);
				if (isValid) {
					boolean doesExcute = game.excute(input);

					System.out.print("Command does follow the formate");
					if (!doesExcute) {
						System.out.println(", but doesnt follow the rule of game");
					} else {
						System.out.println();

						game.printOutBoard();

						System.out.println();
					}

				} else if (!isValid) {
					System.out.println("illegal Command ");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
