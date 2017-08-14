package ass1.swen22;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ass1.swen22.Piece.Type;

public class Board {
	Piece[][] board;
	private int rows = 10;
	private int cols = 10;
	Piece[][]piecesBoard;
	private Piece actPiece;
	private int pCol = 0, pRow = 0;
	private boolean hasCreate;
	public void setHasCreate(boolean b){
		hasCreate=b;
	}
	public boolean getHasCreate(){
		return hasCreate;
	}
	public Board() {
	
		piecesBoard = new Piece[rows][cols];
		// initialise all pieces,fill all gray and while rectangle
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				//piecesBoard[row][col] = new Piece(Piece.Type.NonePiece);
				if ((row < 2 && col < 2) || (row > 7 && col > 7)) {
					piecesBoard[row][col] = new Piece(Piece.Type.OutBoard);
			
				} else {
					piecesBoard[row][col] = new Piece(Piece.Type.GridPiece);
					
				}
			}
		}
		piecesBoard[1][1] = new Piece(Piece.Type.LeftFace);
		piecesBoard[8][8] = new Piece(Piece.Type.RightFace);
		piecesBoard[2][2] = new Piece(Piece.Type.RightCreation);
		piecesBoard[7][7] = new Piece(Piece.Type.RightCreation);


	}

//	public Piece[][] getBoard() {
//		return board;
//	}
	//0->both dead     1->neighbour move back     2->self dead  3->self move back  4->neighbourd dead
	public int weaponCompare(char neighbourWeap,char selfWeap){
		if(neighbourWeap=='|'&&selfWeap=='|'){
			return 0;
		}else if(neighbourWeap=='|'&&selfWeap=='#'){
			return 1;
		}else if(neighbourWeap=='|'&&selfWeap==' '){
			return 2;
		}else if(selfWeap=='|'&&neighbourWeap=='#'){
			return 3;
		}else if(selfWeap=='|'&&neighbourWeap==' '){
			return 4;
		}
		return 5;
	}
	
	
	public int checkReactionHelper(String direction,Piece neighbour,Piece self){
		int act=5;
		switch (direction) {
		case "left":
			act=weaponCompare(neighbour.getRightWeapon(), self.getLeftWeapon());
			break;
		case "right":
			act=weaponCompare(neighbour.getLeftWeapon(),self.getRightWeapon());
			break;
		case "top":
			act=weaponCompare(neighbour.getBottomWeapon(), self.getTopWeapon());
			break;
		case "bottom":
			act=weaponCompare(neighbour.getTopWeapon(), self.getBottomWeapon());
			break;
		default:
			break;
		}
		return act;
	}
	
	public boolean checkReaction() throws InterruptedException{
		int xRow=0,xCol=0,act=5,keySize=0;
		ArrayList<Integer> selfCoord=new ArrayList<>();
		if(actPiece!=null){
			System.out.println("name:"+actPiece.getName());
			selfCoord=findPieceCoord(actPiece.getName());
		if(selfCoord.size()==2){
			xRow=selfCoord.get(0);
			xCol=selfCoord.get(1);
			if(xCol>0){
				Piece leftPiece= piecesBoard[xRow][xCol-1];
				//System.out.println("0000leftpiece: "+leftPiece.getName());
				boolean b1 =(leftPiece.getType()==Type.GreenPiece||leftPiece.getType()==Type.YellowPiece)&&(leftPiece.getRightWeapon()=='|'||actPiece.getLeftWeapon()=='|');
				if(b1){	
					//System.out.println("1111leftpiece: "+leftPiece.getName());
					piecesBoard[xRow][xCol].addNeighbourPiece("left", leftPiece);
				}	
			} 
			if(xCol<9){
				Piece rightPiece=piecesBoard[xRow][xCol+1];
				//System.out.println("000rightPiece: "+rightPiece.getName()+"leftwep:"+rightPiece.getLeftWeapon()+"  "+actPiece+"rightwP"+actPiece.getRightWeapon());
				
				boolean b2 = (rightPiece.getType()==Type.GreenPiece||rightPiece.getType()==Type.YellowPiece)&&(rightPiece.getLeftWeapon()=='|'||actPiece.getRightWeapon()=='|');
				if(b2){
					//System.out.println("1111rightPiece: "+rightPiece.getName());
					piecesBoard[xRow][xCol].addNeighbourPiece("right", rightPiece);
				}
			}
			if(xRow<9){
				Piece botPiece=piecesBoard[xRow+1][xCol];
				//System.out.println("00000botPiece: "+botPiece.getName()+" topWP "+botPiece.getTopWeapon()+" "+actPiece+" botmWp "+actPiece.getBottomWeapon());
				boolean b3 = (botPiece.getType()==Type.GreenPiece||botPiece.getType()==Type.YellowPiece)&&(botPiece.getTopWeapon()=='|'||actPiece.getBottomWeapon()=='|');
				if(b3){
					//System.out.println("1111botPiece: "+botPiece.getName());
					piecesBoard[xRow][xCol].addNeighbourPiece("bottom", botPiece);
				}
			}
			if(xRow>0){
				Piece topPiece = piecesBoard[xRow-1][xCol];
				
				boolean b4= (topPiece.getType()==Type.GreenPiece||topPiece.getType()==Type.YellowPiece)&&(topPiece.getBottomWeapon()=='|'||actPiece.getTopWeapon()=='|');
				if(b4){
					//System.out.println("1111topPiece: "+topPiece.getName());
					piecesBoard[xRow][xCol].addNeighbourPiece("top", topPiece);
				}
			}
		}
			
			if(!piecesBoard[xRow][xCol].getNeighbourPiece().isEmpty()){
			Piece tempPiece = 	piecesBoard[xRow][xCol];
			System.out.println("There is a interaction happen!!");
			printBoard();
			Thread.sleep(1000);
			
				Set<String> keys =tempPiece.getNeighbourPiece().keySet();
				keySize=keys.size();
				for(String key:keys){
					//0->both dead     1->neighbour move back     2->self dead  3->self move back  4->neighbourd dead
					Piece neighP=tempPiece.getNeighbourPiece().get(key);
					act=checkReactionHelper(key,neighP,tempPiece);
					ArrayList<Integer> neighCoord= findPieceCoord(neighP.getName());
					int Nrow= neighCoord.get(0);
					int Ncol=neighCoord.get(1);
					switch (act) {
					case 0:
						piecesBoard[Nrow][Ncol]=removeHelper(Nrow,Ncol);
						piecesBoard[xRow][xCol]=removeHelper(xRow, xCol);
						System.out.println(piecesBoard[Nrow][Ncol].getName()+" ADN "+piecesBoard[xRow][xCol].getName()+" are dead");
						break;
					case 1:
						String dir1=pushBackDir(key, false);
						movePiece(neighP.getName(), dir1, true);
						System.out.println("111111"+neighP.getName()+" was pushed "+dir1);
						break;
					case 2:
						piecesBoard[xRow][xCol]=removeHelper(xRow, xCol);
						System.out.println("222222"+piecesBoard[xRow][xCol].getName()+" dead");
						break;
					case 3:
						String dir2=pushBackDir(key, true);
						movePiece(tempPiece.getName(), dir2, true);
						System.out.println("3333333"+tempPiece.getName()+" was pushed "+dir2);
						break;
					case 4:
						piecesBoard[Nrow][Ncol]=removeHelper(Nrow, Ncol);
						System.out.println("4444444"+piecesBoard[Nrow][Ncol].getName()+" dead");
						break;
					default:
						break;
					}
				}
			}
	
		}
		
		if(keySize>0){
			
			piecesBoard[xRow][xCol].setNeighbourPiece(new HashMap<String,Piece>());
			return true;
		}
		return false;
	}
	
	public String pushBackDir(String key,boolean isSelf){
		String dir="";
		if(isSelf){
			switch (key) {
			case "left":
				dir="right";
				break;
			case "right":
				dir="left";
				break;
			case "top":
				dir="down";
				break;
			case "bottom":
				dir="up";
				break;			
			default:
				break;
			}
		}else{
			switch (key) {
			case "left":
				dir="left";
				break;
			case "right":
				dir ="right";
				break;
			case "top":
				dir="up";
				break;
			case "bottom":
				dir="down";
				break;			
			default:
				break;
			}
		}
		return dir;
		
	
	}
	
	public Piece[][] getPieceBoard() {
		return piecesBoard;
	}

	public void setPieceBoard(Piece[][] temp) {
		piecesBoard = temp;
		for(int row=0;row<10;row++){
			for(int col=0;col<10;col++){
				piecesBoard[row][col].fillPieceBoard();
			}
		}
	}
	public boolean isEmptyCreationArea(){
		if(Game.isGreenTurn){
			return piecesBoard[2][2].getType()==Piece.Type.RightCreation;
		}
		
		return piecesBoard[7][7].getType()==Piece.Type.RightCreation;
	}
	public boolean createPiece(Piece temp) {
		if(hasCreate||!isEmptyCreationArea()){
			return false;
		}
		if (temp.getType() == Piece.Type.GreenPiece) {
			actPiece=piecesBoard[2][2] = temp;
		} else if (temp.getType() == Piece.Type.YellowPiece) {
			actPiece=piecesBoard[7][7] = temp;
		}
		setHasCreate(true);
		return true;
	}

	public boolean rotatePiece(char pieceName, String degree) {
		Piece temp = findPieceOnBoard(pieceName);
		//System.out.println("222222222222");
		if (temp == null || temp.getHasRotate() || temp.getHasMove()) {

			return false;
		}
		//System.out.println("111111111");
		temp.printWeapon();
		temp.rotate(degree);
		temp.printWeapon();
		temp.setHasRotate(true);
	    piecesBoard[pRow][pCol] = temp;
	    actPiece = temp;
		return true;
	}
	public ArrayList<Integer> findPieceCoord(char pieceName){
		ArrayList<Integer> rowCol=new ArrayList<Integer>();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if ((row < 2 && col < 2) || (row > 7 && col > 7))
					continue;
				if (pieceName == piecesBoard[row][col].getName()) {
					rowCol.add(row);
					rowCol.add(col);
					break;
				}
			}
		}
	
	
		
		return rowCol;
	}
	public Piece findPieceOnBoard(char pieceName) {
		Piece temp = null;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if ((row < 2 && col < 2) || (row > 7 && col > 7))
					continue;
				if (pieceName == piecesBoard[row][col].getName()) {
					temp = piecesBoard[row][col];
					pCol = col;
					pRow = row;
				}
			}
		}
		return temp;
	}

	// return true-->allow move
	public boolean moveHelper(int row, int col) {
		boolean outborder = row > 9 || col > 9 || row < 0 || col < 0;// true
																		// ->out
																		// of
																		// border
		boolean inForbiddenArea = (row < 2 && col < 2) || (row > 7 && col > 7);// true->in
																				// forbidden
		return !(outborder || inForbiddenArea);
	}
	public Piece removeHelper(int row,int col){
		if((row == 2 && col == 2) || (row== 7 && col== 7)){
			
			return new Piece(Piece.Type.RightCreation);
		}
		return new Piece(Piece.Type.GridPiece);
	}
	// return a boolean indicates if move the piece successfully
	public boolean movePiece(char pieceName, String dir,boolean isPushed) {
		Piece temp = findPieceOnBoard(pieceName);
		if (temp == null || temp.getHasMove() || temp.getHasRotate()) {
			return false;
		}
		if (dir.equals("up")) {
			if (!(moveHelper(pRow - 1, pCol))) {
				return false;
			} else {
				piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE	
				pRow = pRow - 1;
				piecesBoard[pRow][pCol] = temp; // add
			}
		} else if (dir.equals("down")) {
			if (!(moveHelper(pRow + 1, pCol))) {
				return false;
			} else {
				
				piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE	
				pRow = pRow + 1;
				piecesBoard[pRow][pCol] = temp; // add
			}
		} else if (dir.equals("left")) {
			if (!(moveHelper(pRow, pCol - 1))) {
				return false;
			} else {
				
				piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE	
				pCol = pCol - 1;
				piecesBoard[pRow][pCol] = temp; // add
			}
		} else if (dir.equals("right")) {
			if (!(moveHelper(pRow, pCol + 1))) {
				return false;
			} else {
				piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE	
				pCol = pCol + 1;
				piecesBoard[pRow][pCol] = temp; // add
			}
		}
		if(!isPushed){
			temp.setHasMove(true);
		}
		actPiece=temp;
		return true;
	}

	public void printBoard() {
		
		for(int row=0;row<10;row++){
			for(int col=0;col<10;col++){
				piecesBoard[row][col].fillPieceBoard();
			}
		}
		
		for (int row = 0; row < 10; row++) { 
			for (int inRow = 0; inRow < 3; inRow++) {
				for (int col = 0; col < 10; col++) {
					piecesBoard[row][col].drawWeaponToPiece(inRow);
					//piecesBoard[row][col].fillPieceBoard();
				}
				System.out.println();
			}
		}
	}
	// @Override
	// public void paint(Graphics g){
	// g.setColor(Color.PINK);
	// g.fillRect(0, 0, getWidth(), getHeight());
	// for(int row = 0; row<10;row++){
	// for(int col=0;col<10;col++){
	// board[row][col].drawPiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
	// }
	// }
	// for(int row = 0; row<10;row++){
	// for(int col=0;col<10;col++){
	// piecesBoard[row][col].drawPiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
	// }
	// }
	// }
	// protected void paintComponent(Graphics g){
	// super.paintComponent(g);
	// }
	//
	//
	//
	// @Override
	// public Dimension getPreferredSize(){
	// //width -> (5)*50
	// //height-> (10+10)*50
	// return new Dimension(10*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));
	// }
}
