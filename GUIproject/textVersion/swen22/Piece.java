package ass1.swen22;
import java.util.HashMap;
/**
 * The class is represented all of pieces of the game
 * This includes different type of pieces
 * @author minpingyang
 * **/
public class Piece{
	//public static final int SIZE_PIECE = 70;//may be used by other classes
	private Type type;
	public char [][] equipment;
	private char name;//first row represents:  second row represents
	private char topWeapon,rightWeapon,botWeapon,leftWeapon;
	private boolean hasRotate, hasMove;

	private HashMap<String, Piece> neighbourPiece=new HashMap<String,Piece>();
	
	/**
	 * 
	 * **/
	public HashMap<String, Piece> getNeighbourPiece(){
		return neighbourPiece;
	}
	/**
	 * This method is used to deep clone the piece, copy all weapon and equipment to current piece
	 * @param other -> the piece want to be deep cloned
	 * 
	 * **/
	public void deepClone(Piece other){
		if(other instanceof Piece){
			Piece otPiece = (Piece) other;
			for(int row=0; row<3;row++){
				for(int col=0;col<3;col++){
					this.equipment[row][col]=otPiece.equipment[row][col];
				}
			}
			this.type = otPiece.type;
			this.name = otPiece.name;
			this.setFourWeapon(otPiece.topWeapon, otPiece.rightWeapon, otPiece.botWeapon, otPiece.leftWeapon);
			this.hasRotate=otPiece.hasRotate;//set the two boolean value as well
			this.hasMove=otPiece.hasMove;
			
		}
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
	/**
	 * a enum value which was used indicate the different type of piece
	 * **/
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
	/**
	 * This method is used to test 
	 * **/
	public void printWeapon(){
		System.out.println("top: "+topWeapon+"  right:  "+rightWeapon+"  bot"+botWeapon+"   "+leftWeapon);
	}
	/**
	 * This method is used to set Four direction weapons
	 * **/
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
	
	/**
	 * 1->0 2->90 3->180 4->270
	 * This method is used to rotate the piece by change the piece equipment 2D, according to the particular degree
	 * @param degree- degree want to be rotated 
	 * **/
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
	/**
	 * Constructor of this class 
	 * initialise name to space
	 * euqipment to a empty 3*3 2D array
	 * @param type - the type of Piece
	 * */
	public Piece(Type type) {
		
		this.type =type;
		equipment= new char[3][3];
		name=' ';
	}
	
	public char getName(){return name;}
	public void setName(char s){name=s;}
	
	/**
	 * This method is used to avoid duplicate piece generated when the player generate 24 pieces
	 * **/
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
	
	/**
	 * This is a helper method is used to fill character to the equipment of the piece
	 * @param c the particlar character want to be filled  
	 * */
	public void fillPieceBoardHelper(char c){
		for(int row=0;row<3;row++){
			for(int col=0;col<3;col++){
				this.equipment[row][col]=c;
			}
		}
	}
	/**
	 *This method is used to fill the equipment, according by their piece type
	 ***/
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
		}
	}

	//index - 0-3  -0-3
	/**
	 * This method is used to print out the piece in a correct position, accroding to their index of their 2D array equipment
	 *
	 * **/
	public void drawWeaponToPiece(int inRow){
	
		for(int col=0;col<3;col++){
			if(inRow==1&&equipment[inRow][col]=='|'){
				System.out.printf("%-2c",'-');
			}else{
				System.out.printf("%-2c",this.equipment[inRow][col]);
			}
			
		}
		
	}

	
	
}