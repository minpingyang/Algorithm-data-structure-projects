package Tests;

import static org.junit.Assert.*;

import org.ietf.jgss.GSSManager;
import org.junit.*;

import ass1.swen22.Board;
import ass1.swen22.Game;
import ass1.swen22.Piece;

import ass1.swen22.Player;

public class Tests {
	// test1 pass command and excute method
	@Test
	public void testPass() throws InterruptedException {
		Game game = new Game();
		try {
			boolean beforePass = Game.isGreenTurn;
			boolean isExcute = game.excute("pass");
			boolean afterPass = Game.isGreenTurn;
			assertEquals(isExcute, true);
			assertEquals(!beforePass, afterPass);
		} catch (NullPointerException e) {
			e.getMessage();
		}

	}

	// test2 test isValid method
	@Test
	public void testValid() {
		Game game = new Game();
		try {
			boolean undo = game.isValidCommand("undo");
			boolean move1 = game.isValidCommand("move a up");
			boolean move3 = game.isValidCommand("move x right");
			boolean create1 = game.isValidCommand("create a 1");

			boolean rotate2 = game.isValidCommand("rotate A 1");
			boolean move2 = game.isValidCommand("move c 1");
			boolean create3 = game.isValidCommand("create B 1");
			boolean undo2 = game.isValidCommand("12121");
			boolean create2 = game.isValidCommand("create a 0");
			boolean rotate1 = game.isValidCommand("rotate a up");
			assertEquals(undo, true);
			assertEquals(undo2, false);
			assertEquals(move1, true);
			assertEquals(move2, false);
			assertEquals(move3, true);
			assertEquals(create1, true);
			assertEquals(rotate2, false);
			assertEquals(create3, false);
			assertEquals(create2, false);
			assertEquals(rotate1, false);

		} catch (NullPointerException e) {
			e.getMessage();
		}

	}
	//the test is used to test DeepClone method
	@SuppressWarnings("deprecation")
	@Test
	public void testDeepClone() {
		Board b = new Board();
		Game game = new Game();
		Board copyBoard = game.deepCloneBoard(b);
		assertEquals(copyBoard.getPieceBoard(), b.getPieceBoard());
	}
	//the test is used to test swithcTurn method
	@Test
	public void testSwitchTurn() throws InterruptedException {
		Game game = new Game();
		Board board = game.getBoard();

		game.excute("create a 1");
		game.excute("move a right");
		assertEquals(game.getUndoStack().isEmpty(), false);
		assertEquals(board.getHasCreate(), true);
		game.excute("pass");
		assertEquals(game.getUndoStack().isEmpty(), true);
		assertEquals(board.getHasCreate(), false);

	}
	//the test is used to test Rotate method
	@Test
	public void testRotate() throws InterruptedException {
		Game game = new Game();
		Board board = game.getBoard();
		game.create("1", 'a');
		// boolean b1=game.rotate("2", 'a');
		boolean b1 = game.excute("rotate a 2");
		boolean b2 = board.rotatePiece('a', "2",true);
		Piece rotateP = board.findPieceOnBoard('a');
		assertEquals(true, b1);
		assertEquals(true, rotateP.getHasRotate()); // not allow rotate twice in
													// one turn
		assertEquals(false, b2);//
	}
	//the test is used to the yellowPlayer create a piece 
	@Test
	public void testYellowCreate() throws InterruptedException {
		Game game = new Game();
		game.excute("pass");

		assertEquals(true, game.excute("create A 1"));
	}
	//the test is used to test undo method
	@SuppressWarnings("deprecation")
	@Test
	public void testUndo() throws InterruptedException {
		Game game = new Game();
		Board board1 = game.getBoard();
		game.excute("create a 1");
		assertEquals(board1.getPieceBoard()[2][2].getType(), Piece.Type.GreenPiece);
		game.excute("undo");
		Board board = game.getBoard();
		assertEquals(board.getPieceBoard()[2][2].getType(), Piece.Type.RightCreation);

	}

	@Test
	public void testPrintOutBoard() {
		Game game = new Game();
		game.printOutBoard();
		Board board = game.getBoard();
		Piece[][] pieceBoard = board.getPieceBoard();

		assertEquals(pieceBoard[2][2].getType(), pieceBoard[7][7].getType());
	}
	//the test is used to test Reaction
	//when self has shield, neighbour has sword, its neighbour piece in its bottom
	@Test
	public void testReaction1() throws InterruptedException {
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', '|', ' ', ' ', ' ');
		greenPlayer.setWeaponToPiece('b', ' ', ' ', '#', ' ');
		game.excute("create a 1");
		assertEquals(false, game.getHasReaction());
		game.excute("move a down");
		char a = game.getBoard().findPieceOnBoard('a').getTopWeapon();
		assertEquals(a, '|');
		game.excute("pass");
		game.excute("pass");
		game.excute("create b 1");
		assertEquals(true, game.getHasReaction());
		char b = game.getBoard().findPieceOnBoard('b').getBottomWeapon();
		assertEquals(b, '#');

	}
	//the test is used to test Reaction
	//when neighbou has shield, self has sword, its neighbour piece in its bottom
	@Test
	public void testReaction2() throws InterruptedException {
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('b', '|', ' ', ' ', ' ');
		greenPlayer.setWeaponToPiece('a', ' ', ' ', '#', ' ');
		game.excute("create b 1");
		assertEquals(false, game.getHasReaction());
		game.excute("move b down");
		char b = game.getBoard().findPieceOnBoard('b').getTopWeapon();
		assertEquals(b, '|');
		game.excute("pass");
		game.excute("pass");
		game.excute("create a 1");
		assertEquals(true, game.getHasReaction());
		char a = game.getBoard().findPieceOnBoard('a').getBottomWeapon();
		assertEquals(a, '#');
	}
	//the test is used to test Reaction
	//when  neighbou has sword self has sword, its neighbour piece in its right
	@Test
	public void testReaction3() throws InterruptedException {
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', ' ', ' ', ' ', '|');
		greenPlayer.setWeaponToPiece('b', ' ', '|', ' ', ' ');
		game.excute("create a 1");
		assertEquals(false, game.getHasReaction());
		game.excute("move a right");
		char a = game.getBoard().findPieceOnBoard('a').getLeftWeapon();
		assertEquals(a, '|');
		game.excute("pass");
		game.excute("pass");
		game.excute("create b 1");
		assertEquals(true, game.getHasReaction());
		// a and b both should be null-->> dead
		assertEquals(game.getBoard().findPieceOnBoard('a'), game.getBoard().findPieceOnBoard('b'));
	}

	//the test is used to test Reaction
	//when neighbou has sword self has nothing, its neighbour piece in its left
	@Test
	public void testReaction4() throws InterruptedException {
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', ' ', ' ', ' ', ' ');
		greenPlayer.setWeaponToPiece('b', ' ', ' ', ' ', '|');
		game.excute("create a 1");
		assertEquals(false, game.getHasReaction());
		game.excute("move a left");
		char a = game.getBoard().findPieceOnBoard('a').getRightWeapon();
		assertEquals(a, ' ');
		game.excute("pass");
		game.excute("pass");
		game.excute("create b 1");
		assertEquals(true, game.getHasReaction());
		// a and b both should be null-->> dead
		assertEquals(game.getBoard().findPieceOnBoard('a'), null);
	}
	//the test is used to test Reaction
	//when neighbou has sword self has nothing, its neighbour piece in its top
	@Test
	public void testReaction5() throws InterruptedException {
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', ' ', ' ', '|', ' ');
		greenPlayer.setWeaponToPiece('b', ' ', ' ', ' ', ' ');
		game.excute("create a 1");
		assertEquals(false, game.getHasReaction());
		game.excute("move a up");
		char a = game.getBoard().findPieceOnBoard('a').getBottomWeapon();
		assertEquals(a, '|');
		game.excute("pass");
		game.excute("pass");
		game.excute("create b 1");
		assertEquals(true, game.getHasReaction());
		// a and b both should be null-->> dead
		assertEquals(game.getBoard().findPieceOnBoard('b'), null);
	}
	//the test is used to test Reaction
	//when neighbou has shield self has sword its neighbour piece in its top
	@Test
	public void testReaction6() throws InterruptedException {
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', ' ', ' ', '#', ' ');
		greenPlayer.setWeaponToPiece('b', '|', ' ', ' ', ' ');
		game.excute("create a 1");
		assertEquals(false, game.getHasReaction());
		game.excute("move a up");
		char a = game.getBoard().findPieceOnBoard('a').getBottomWeapon();
		assertEquals(a, '#');
		game.excute("pass");
		game.excute("pass");
		game.excute("create b 1");
		assertEquals(true, game.getHasReaction());
		// a and b both should be null-->> dead
		assertEquals(game.getBoard().getPieceBoard()[2][2].getType(), Piece.Type.RightCreation);
	}
	//the test is used to test pushBack method
	@Test
	public void testPushBack1() {
		Board board =new Board();
		String dir = board.pushBackDir("left", true);
		assertEquals(dir, "right");
	}
	//the test is used to test pushBack method
	@Test
	public void testPushBack2() {
		Board board =new Board();
		String dir = board.pushBackDir("right", true);
		assertEquals(dir, "left");
	}
	//the test is used to test pushBack method
	@Test
	public void testPushBack3() {
		Board board =new Board();
		String dir = board.pushBackDir("bottom", true);
		assertEquals(dir, "up");
	}
	//the test is used to test pushBack method
	@Test
	public void testPushBack4() {
		Board board =new Board();
		String dir = board.pushBackDir("left", false);
		assertEquals(dir, "left");
	}
	//the test is used to test pushBack method
	@Test
	public void testPushBack5() {
		Board board =new Board();
		String dir = board.pushBackDir("right", false);
		assertEquals(dir, "right");
	}
	//the test is used to test pushBack method
	@Test
	public void testPushBack6() {
		Board board =new Board();
		String dir = board.pushBackDir("top",false);
		assertEquals(dir, "up");
	}
	//the test is used to test rotate 4 degree
	@Test
	public void testRotate2() throws InterruptedException{
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', '#', ' ', ' ', ' ');
		game.excute("create a 1");
		char a1 = game.getBoard().findPieceOnBoard('a').getTopWeapon();
	
		assertEquals(true, game.excute("rotate a 4"));
		char a2 = game.getBoard().findPieceOnBoard('a').getLeftWeapon();
		assertEquals(a1, '#');
		assertEquals(a2, '#');
	
	}
	//the test is used to test rotate 3 degree
	@Test
	public void testRotate3() throws InterruptedException{
		Game game = new Game();
		Player greenPlayer = game.getPlayer("green");
		greenPlayer.setWeaponToPiece('a', '#', ' ', ' ', ' ');
		game.excute("create a 1");
		char a1 = game.getBoard().findPieceOnBoard('a').getTopWeapon();
	
		assertEquals(true, game.excute("rotate a 3"));
		char a2 = game.getBoard().findPieceOnBoard('a').getBottomWeapon();
		assertEquals(a1, '#');
		assertEquals(a2, '#');
		
	}
	//the test is used to test rotate 2
	@Test
	public void TestUndoRotate() throws InterruptedException{
		Game game = new Game();
		Player yellowPlayer = game.getPlayer("yellow");
		yellowPlayer.setWeaponToPiece('B', '#', ' ', ' ', ' ');
		game.excute("pass");
		game.excute("create B 1");
		game.excute("rotate B 2");
		char B1= game.getBoard().findPieceOnBoard('B').getRightWeapon();
		assertEquals(B1,'#');
		game.excute("undo");
		char B2= game.getBoard().findPieceOnBoard('B').getTopWeapon();
		assertEquals(B2,'#');

	}
	//the test is used to test rotate 3
	@Test
	public void TestUndoRotate2() throws InterruptedException{
		Game game = new Game();
		Player yellowPlayer = game.getPlayer("yellow");
		yellowPlayer.setWeaponToPiece('B', '#', ' ', ' ', ' ');
		game.excute("pass");
		game.excute("create B 1");
		game.excute("rotate B 3");
		char B1= game.getBoard().findPieceOnBoard('B').getBottomWeapon();
		assertEquals(B1,'#');
		game.excute("undo");
		char B2= game.getBoard().findPieceOnBoard('B').getTopWeapon();
		assertEquals(B2,'#');
	}
	//the test is used to test rotate 4
	@Test
	public void TestUndoRotate3() throws InterruptedException{
		Game game = new Game();
		Player yellowPlayer = game.getPlayer("yellow");
		yellowPlayer.setWeaponToPiece('B', '#', ' ', ' ', ' ');
		game.excute("pass");
		game.excute("create B 1");
		game.excute("rotate B 4");
		char B1= game.getBoard().findPieceOnBoard('B').getLeftWeapon();
		assertEquals(B1,'#');
		game.excute("undo");
		char B2= game.getBoard().findPieceOnBoard('B').getTopWeapon();
		assertEquals(B2,'#');
	}
	//the test is used to test rotate 1
	@Test
	public void TestUndoRotate4() throws InterruptedException{
		Game game = new Game();
		Player yellowPlayer = game.getPlayer("yellow");
		yellowPlayer.setWeaponToPiece('B', '#', ' ', ' ', ' ');
		game.excute("pass");
		game.excute("create B 2");
		game.excute("rotate B 1");
		char B1= game.getBoard().findPieceOnBoard('B').getRightWeapon();
		assertEquals(B1,'#');
		game.excute("undo");
		char B2= game.getBoard().findPieceOnBoard('B').getRightWeapon();
		assertEquals(B2,'#');
	}
	//the test is used to test  right loose
	@Test
	public void TestWin() throws InterruptedException{
		Game game = new Game();
		Player yellowPlayer = game.getPlayer("yellow");
		yellowPlayer.setWeaponToPiece('B', ' ', ' ', '|', ' ');
		game.excute("pass");
		game.excute("create B 1");
		game.excute("move B right");
		char B1= game.getBoard().findPieceOnBoard('B').getBottomWeapon();
		assertEquals(B1,'|');
		
		assertEquals(game.doesWin(),2);
	
	}
	//the test is used to test left loose
	@Test
	public void TestWin2() throws InterruptedException{
		Game game = new Game();
		Player yellowPlayer = game.getPlayer("green");
		yellowPlayer.setWeaponToPiece('a', '|', ' ', ' ', ' ');
		
		game.excute("create a 1");
		game.excute("move a left");
		char B1= game.getBoard().findPieceOnBoard('a').getTopWeapon();
		assertEquals(B1,'|');
	
		assertEquals(game.doesWin(),1);
	}
	
}
