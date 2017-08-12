package ass1.swen22;

public class RightPieces {
	private int rows = 6;
	private int cols = 4;
	private int top = 10;
	private int left = 13;
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

	
//	@Override
//	public void paint(Graphics g){
//		g.setColor(Color.PINK);
//		g.fillRect(0, 0, getWidth(), getHeight());
//		List<Piece> temp = yellowPlayer.getPieces();
//		int i=0;
//		int outline = 10;
//		for(int row = 0; row<6;row++){
//			for(int col=0;col<4;col++){
//				temp.get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+row*(Piece.SIZE_PIECE+outline),row,col);
//			}
//		}
//	}
//	protected void paintComponent(Graphics g){
//		super.paintComponent(g);
//	}
//	
//	@Override
//	public Dimension getPreferredSize(){
//		//width -> (5)*50
//		//height-> (10+10)*50
//		return new Dimension(5*(Piece.SIZE_PIECE),9*(Piece.SIZE_PIECE));
//	}
}
