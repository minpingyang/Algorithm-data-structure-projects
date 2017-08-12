package ass1.swen22;

public class RightPieces {
	private int rows = 6;
	private int cols = 4;

	private Player yellowPlayer;
	
	public RightPieces(Player player) {
		yellowPlayer = player;

		//initialise all pieces
		for(int row =0;row<rows;row++){
			for(int col = 0;col<cols;col++){
				yellowPlayer.addDiffPiece(Piece.Type.YellowPiece);
			}
		}
	}


}
