package ass1.swen22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Stack;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

public class Game {
	private Board board;
	private LeftPieces leftPieces;
	private RightPieces rightPieces;
	public static boolean isGreenTurn;
	private Player greenPlayer,yellowPlayer;
	private String info = "Welcome";
	private Stack<Board> undoStack;

	public Game() {
		undoStack=new Stack<Board>();
		isGreenTurn=true;
		greenPlayer=new Player(Piece.Type.GreenPiece);
		yellowPlayer=new Player(Piece.Type.YellowPiece);
		board = new Board();
		
		leftPieces = new LeftPieces(greenPlayer);
		rightPieces = new RightPieces(yellowPlayer);
		
	}
	public Board getBoard(){
		return board;
	}
	public void switchTurn(){
		isGreenTurn=!isGreenTurn;
		undoStack.clear();
		board.setHasCreate(false);
		Piece[][] temp = board.getPieceBoard();
		for(int row=0;row<10;row++){
			for(int col=0;col<10;col++){
				if(temp[row][col].getType()!=Piece.Type.GridPiece){
					temp[row][col].setHasMove(false);
					temp[row][col].setHasRotate(false);
					board.setPieceBoard(temp);
				}
			}
		}
		
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
	public boolean move(String dir,char pieceName){
		return board.movePiece(pieceName, dir,false);
	}
	public boolean rotate(String degree,char pieceName){
		return board.rotatePiece(pieceName, degree);
	}
	public Player getGreenPlayer(){
		return greenPlayer;
	}
	public boolean create(String degree,char pieceName){
		boolean doesCreate=false;
		if(isGreenTurn){//green piece
			List<Piece> gPieces = greenPlayer.getPieces();
			for(Piece piece:gPieces){
				
				if(piece.getName()==pieceName){
					int index=gPieces.indexOf(piece);
					gPieces.set(index, new Piece(Piece.Type.EmptyPiece));
					greenPlayer.setPieces(gPieces);
					piece.rotate(degree);
				doesCreate=	board.createPiece(piece);
				}
			}
			
		}else{
			//System.out.println("yellow turn");
			List<Piece> pieces = yellowPlayer.getPieces();
			for(Piece piece:pieces){
				
				if(piece.getName()==pieceName){
					int index=pieces.indexOf(piece);
					pieces.set(index, new Piece(Piece.Type.EmptyPiece));
					yellowPlayer.setPieces(pieces);
					piece.rotate(degree);
				doesCreate=	board.createPiece(piece);
				}
			}
		}
		return doesCreate;
	}
	public void undo(){
	 board=undoStack.pop();
	}
	public Board deepCloneBoard(Board board){
		 Board temp =new Board();
		 temp.setHasCreate(board.getHasCreate());
		 for(int row=0;row<10;row++){
			 for(int col=0;col<10;col++){
				 temp.piecesBoard[row][col]=board.piecesBoard[row][col];
				 
			 }
		 }
		 return temp;
		 //undoStack.add(temp);
		
	}
	//if valid command
	public boolean excute(String command) throws InterruptedException{
		boolean doActionSuccess=false;
		boolean doesAct=false;
		Board temp = deepCloneBoard(board);
		String[] line = command.split(" ");
		if(line.length==1){
			info=line[0];
			if(info.equals("pass")){
			switchTurn();
			doActionSuccess=true;
			}else if(info.equals("undo")){
				undo();
				doActionSuccess=true;
			}
		}else if(line.length==3){
			info= line[0]+"  "+line[1]+"  "+line[2];
			if(line[0].equals("create")){
				char pieceName=line[1].charAt(0);
				String degree = line[2];
				doActionSuccess=create(degree,pieceName);
				doesAct=doActionSuccess;
				undoStack.add(temp);
			}else if(line[0].equals("move")){
				char pieceName=line[1].charAt(0);
				String dir = line[2];
				doActionSuccess=move(dir,pieceName);
				doesAct=doActionSuccess;
				undoStack.add(temp);
			}else if(line[0].equals("rotate")){
				//System.out.println("11111");
				char pieceName=line[1].charAt(0);
				String degree = line[2];
				doActionSuccess=rotate(degree, pieceName);
				doesAct=doActionSuccess;
				undoStack.add(temp);
			}
			
		}
		if(doesAct){
			boolean hasReaction=board.checkReaction();
			if(hasReaction){
				System.out.println("REACTION EXCUTED!!");
			}
		}
		 System.out.println("stack size"+undoStack.size());
		//leftPieces.setInfo(info);
		return doActionSuccess;
	}
	public void printOutBoard(){
	board.printBoard();
	}
	public static void main(String[] args) throws InterruptedException  {
		BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
		
		Game game = new Game();
		game.printOutBoard();
		
		try {
			bReader=new BufferedReader(new InputStreamReader(System.in));
			while(true){
				
				System.out.println("Enter a valid command:");
				String input = bReader.readLine();
				if("q".equals(input)){
					System.out.println("Exit!");
					System.exit(0);
				}
				boolean isValid = game.isValidCommand(input);
				if(isValid){
					boolean doesExcute=game.excute(input);
					
					System.out.print("Command does follow the formate");
					if(!doesExcute){
						System.out.println(", but doesnt follow the rule of game");
					}else{
						System.out.println();
						game.printOutBoard();
						System.out.println();
					}
					
				}else if(!isValid){
					System.out.println("illegal Command ");
				}				

	
			}
		} catch (IOException e) {
		 e.printStackTrace();
		}
	}
	
}
