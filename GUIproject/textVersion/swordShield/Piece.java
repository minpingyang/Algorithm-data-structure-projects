package ass1.swen22;


import java.util.HashMap;

public class Piece{
	public static final int SIZE_PIECE = 70;//may be used by other classes
	private Type type;
	private char [][] equipment;
	private char name;//first row represents:  second row represents
	private char topWeapon,rightWeapon,botWeapon,leftWeapon;
	private boolean hasRotate, hasMove;

	private HashMap<String, Piece> neighbourPiece=new HashMap<String,Piece>();
	
	public HashMap<String, Piece> getNeighbourPiece(){
		return neighbourPiece;
	}
	public void setNeighbourPiece(HashMap<String, Piece> temp){
		neighbourPiece=temp;
	}
	public char getTopWeapon(){
		return topWeapon;
	}
	public char getRightWeapon(){
		return rightWeapon;
	}
	public char getLeftWeapon(){
		return leftWeapon;
	
	}
	public char getBottomWeapon(){
		return botWeapon;
	}
	
	public void addNeighbourPiece(String dirction, Piece p){
		neighbourPiece.put(dirction, p);
	}
	public enum Type {
		GreenPiece,
		YellowPiece,
		GridPiece,	
		LeftFace,		
		RightFace,
		OutBoard,//forbidden are
		RightCreation,
		EmptyPiece,
		NonePiece
		
	};
	public void fillNeighbour(int row, int col,Board board){
		
	}
	public boolean getHasRotate(){
		return hasRotate;
	}
	public boolean getHasMove(){
		return hasMove;
	}
	public void setHasRotate(boolean b){
		hasRotate=b;
	}
	public void setHasMove(boolean b){
		hasMove = b;
	}
	
	
	public Type getType(){
		return this.type;
	}
	public char[][] getEquipment(){
		return equipment;
	}
	public void printWeapon(){
		System.out.println("top: "+topWeapon+"  right:  "+rightWeapon+"  bot"+botWeapon+"   "+leftWeapon);
	}
	public void setFourWeapon(char top,char right, char bot,char left){
	 topWeapon = top;
	 rightWeapon=right;
	 botWeapon= bot;
	 leftWeapon = left;
	 equipment[0][1]=topWeapon;
	 equipment[1][2]=rightWeapon; 
	 equipment[2][1]=botWeapon;
	 equipment[1][0]=leftWeapon;
	}
	//1->0 2->90 3->180 4->270
	public void rotate(String degree){
		if(degree.equals("1")){
			return;
		}else if(degree.equals("2")){
			setFourWeapon(leftWeapon, topWeapon, rightWeapon, botWeapon);
		}else if(degree.equals("3")){
			setFourWeapon(botWeapon, leftWeapon, topWeapon, rightWeapon);
		}else if(degree.equals("4")){
			setFourWeapon(rightWeapon, botWeapon, leftWeapon, topWeapon);
		}
		
	}
	public void setEquipment(char[][] temp){
		 equipment=temp;
		 setFourWeapon(temp[0][1], temp[1][2], temp[2][1], temp[1][0]);
	}
	
	public Piece(Type type) {
		
		this.type =type;
		equipment= new char[3][3];
		name=' ';
	}
	public char getName(){return name;}
	public void setName(char s){name=s;}
	public boolean equals(Object o){
		if(o instanceof Piece){
			Piece other = (Piece) o;
			//compare character
			if(this.equipment[0][1]==other.equipment[0][1] && this.equipment[1][0]==other.equipment[1][0] &&this.equipment[1][2]==other.equipment[1][2]&&this.equipment[2][1]==other.equipment[2][1]){
				return true;
			}
		}
		return false;
	}
	
	public void fillPieceBoardHelper(char c){
		for(int row=0;row<3;row++){
			for(int col=0;col<3;col++){
				this.equipment[row][col]=c;
			}
		}
	}
	
	public void fillPieceBoard(){
		if(this.type==Piece.Type.RightFace){
			this.fillPieceBoardHelper('@');
		}else if(this.type==Piece.Type.LeftFace){
			this.fillPieceBoardHelper('@');
		}else if (this.type==Piece.Type.GridPiece){
			this.fillPieceBoardHelper('*');
		}else if (this.type==Piece.Type.OutBoard){
			this.fillPieceBoardHelper('X');
		}else if (this.type==Piece.Type.RightCreation){
			this.fillPieceBoardHelper(' ');
		}else if (this.type==Piece.Type.RightCreation){
			this.fillPieceBoardHelper(' ');
		}
	}
	//index - 0-3  -0-3
	public void drawWeaponToPiece(int inRow){
	
		for(int col=0;col<3;col++){
			if(inRow==1&&equipment[inRow][col]=='|'){
				System.out.printf("%-2c",'-');
			}else{
				System.out.printf("%-2c",this.equipment[inRow][col]);
			}
			
		}
		
	}
//	public void drawWeaponHelper(Graphics g,char c,int x,int y,boolean col,int offset){
//		
//		int i=offset==0?0:1;
//		
//		if(col){
//			switch (c) {
//			case '#'://shield horizontal
//				g.fillRect(x, y+offset-weaponWidth*i, SIZE_PIECE, weaponWidth); // horizontal shield
//				break;
//			case '|'://sword vertical
//				g.fillRect(x+SIZE_PIECE/2, y+offset/2, weaponWidth,SIZE_PIECE/2); // vertical sword
//				break;
//			default:
//				break;
//			}
//		}else{
//			switch (c) {
//			case '#'://shield vertical
//				g.fillRect(x+offset-weaponWidth*i, y, weaponWidth,SIZE_PIECE); //  shield vertical
//				break;
//			case '|'://sword horizontal
//				g.fillRect(x+offset/2, y+SIZE_PIECE/2, SIZE_PIECE/2,weaponWidth); //  sword 
//				break;
//			default:
//				break;
//			}
//		}
//	}
//	
//	
//	public void drawWeapon(Graphics g,int x, int y){
//		if(this.type==Type.GreenPiece){
//			g.setColor(Color.red);
//		}else if(this.type==Type.YellowPiece){
//			g.setColor(new Color(178, 0, 255));
//		}
//		char top = this.equipment[0][1];
//		char left = this.equipment[1][0];
//		char bottom =this.equipment[2][1];
//		char right = this.equipment[1][2];
//		drawWeaponHelper(g, top, x, y,true,0);
//		drawWeaponHelper(g, bottom, x, y, true, SIZE_PIECE);
//		drawWeaponHelper(g, left, x, y, false, 0);
//		drawWeaponHelper(g, right, x, y, false, SIZE_PIECE);
//		
//	}
//	
//	public void drawPiece(Graphics g,int x,int y,int row,int col){
//	    
//		if(this.type == Type.RightCreation){
//			g.setColor(Color.ORANGE);
//	    	g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//		}else if(this.type == Type.LeftCreation){
//	    	g.setColor(Color.GREEN);
//	    	g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//	    }else if(this.type == Type.OutBoard){
//			g.setColor(Color.LIGHT_GRAY);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//		}else if(this.type == Type.GrayGrid){
//			g.setColor(Color.DARK_GRAY);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//		}else if (this.type == Type.LeftFace){
//			g.setColor(Color.gray);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//			g.setColor(Color.white);
//			g.fillOval(x-1, y-1, SIZE_PIECE-1, SIZE_PIECE-1);
//			g.setColor(Color.black);
//			g.fillRect(x, y, SIZE_PIECE/3, SIZE_PIECE/3);
//			int temp = SIZE_PIECE/5;
//			g.fillRect(x+4*temp, y+4*temp, temp, temp);
//			
//			
//		}else if(this.type == Type.RightFace){
//			g.setColor(Color.gray);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//			g.setColor(Color.white);
//			g.fillOval(x-1, y-1, SIZE_PIECE-1, SIZE_PIECE-1);
//			g.setColor(Color.black);
//			g.fillRect(x, y, SIZE_PIECE/3, SIZE_PIECE/3);
//			int temp = SIZE_PIECE/5;
//			g.fillRect(x+4*temp, y+4*temp, temp, temp);
//			
//		}else if (this.type== Type.WhiteGrid){
//			g.setColor(Color.WHITE);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//			
//			
//		}else if(this.type == Type.GreenPiece){
//		
//			g.setColor(Color.BLACK);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//			g.setColor(Color.GREEN);
//			g.fillOval(x, y,SIZE_PIECE, SIZE_PIECE);
//			drawWeapon(g, x, y);
//			
//		}else if (this.type == Type.YellowPiece){
//		
//			g.setColor(Color.BLACK);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//			g.setColor(Color.yellow);
//			g.fillOval(x, y,SIZE_PIECE, SIZE_PIECE);
//			drawWeapon(g, x, y);
//		}else if (this.type == Type.EmptyPiece) {
//			g.setColor(Color.pink);
//			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
//		}
//		
//	}
	
	
}