package ass1.swen22;

public class LeftPieces {
/**
 * This class is used to represent the all pieces the greenPlayer has 
 * **/
	private int rows = 6; // the coordinator is used for graphical version of the game
	private int cols = 4;

	private Player greenPlayer;
 
	public LeftPieces(Player player) {
		greenPlayer=player;
		
		//initialise all pieces
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				greenPlayer.addDiffPiece(Piece.Type.GreenPiece);
				
			}
		}
		
	}

	

}
