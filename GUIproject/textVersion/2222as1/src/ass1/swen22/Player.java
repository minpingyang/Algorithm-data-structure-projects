package ass1.swen22;

import java.util.ArrayList;
import java.util.List;

import ass1.swen22.Piece.Type;
/**
 * This class representd the greenPlayer and yellowPlayer
 * Basically, this class is used to generate 24 pieces for each player, at the beginning
 * @author minpingyang
 * **/
public class Player {
	private Piece.Type cardType;
	private List<Piece> pieces;
	/**
	 * Constructor
	 * This is used to initialise  the list of piece player holds to a empty arraylist.
	 * 
	 * */
	public Player(Piece.Type cardType) {
		this.cardType = cardType;
		pieces = new ArrayList<Piece>();
	}

	/**
	 * This method is uesd for Junit test
	 * */
	public void setWeaponToPiece(char name, char top,char right,char bot,char left){
		if(!pieces.isEmpty()){
			for(Piece piece: pieces){
				if(piece.getName()==name){
					piece.setFourWeapon(top, right, bot, left);
				}
			}
		}
	}
	/**
	 * This method is used to check if random generated pieces is duplicate with the piece in the player hand
	 * @param unsurePiece check the piece if is duplicates
	 * @return indicate if the piece is duplicate with the pieces of the list
	 * */
	public boolean isDuplicate(Piece unsurePiece){
		for(Piece piece: pieces){
			if(unsurePiece.equals(piece)){
				return true;//is duplicates
			}
		}
		return false;
	}
	
	public List<Piece> getPieces(){
		return pieces;
	}
	public void setPieces(List<Piece> temp){
		pieces=temp;
	}
	/**
	 * This method is used to randomly genereated 24 pieces
	 * @param type -indicate what type piece the player want to be generated 
	 * 
	 * */
	public void addDiffPiece(Type type){
		Piece unsurePiece = new Piece(type) ; //
		char[] equipmentList = new char[4];
		char[][] temp = new char[3][3];
		 boolean empty=false;
		do{
			int randomWeap=(int)(3*Math.random()); //0 1 2 
			for(int i=0;i<4;i++){
				randomWeap=(int)(3*Math.random()); //0 1 2 
				if(randomWeap==0){
					equipmentList[i]= ' ';
				}else if(randomWeap==1){
					equipmentList[i]= '#';
				}else if(randomWeap==2){				
						equipmentList[i]= '|';
				}
			}
		  temp[0][1]= equipmentList[0];
		  temp[1][0]= equipmentList[1];
		  temp[1][2]= equipmentList[2];
		  temp[2][1]= equipmentList[3];
		   empty = equipmentList[0]==' '&&equipmentList[1]==' '&&equipmentList[2]==' '&&equipmentList[3]==' ';
		  unsurePiece.setEquipment(temp);
		}while(isDuplicate(unsurePiece)||empty);
		if(type==Piece.Type.GreenPiece){
			temp[1][1]=(char)('a'+pieces.size());
		}else if(type==Piece.Type.YellowPiece){
			temp[1][1]=(char)('A'+pieces.size());
		}
		temp[0][0]=' ';
		temp[0][2]=' ';
		temp[2][0]=' ';
		temp[2][2]=' ';
		unsurePiece.setEquipment(temp);
		unsurePiece.setName(temp[1][1]);
		

		pieces.add(unsurePiece);
	}
}
