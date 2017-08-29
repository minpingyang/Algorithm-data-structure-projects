package gameComponent;

import resources.ImageResource;

import java.awt.*;
import java.util.HashMap;

public class Piece {
	public static int SIZE_PIECE = 70;// may be used by other classes
	private Type type;
	private char[][] equipment;
	private char name;// first row represents: second row represents
	private char topWeapon, rightWeapon, botWeapon, leftWeapon;
	private boolean hasRotate, hasMove;
	private int weaponWidth = SIZE_PIECE / 8;
	private boolean isMoving=false;
	private int selectWidth = weaponWidth / 2;
	private int panelX, panelY;
	private boolean isHighLight = false;
	private int rotatePSize = SIZE_PIECE+10;
	private int rotateWeaponWidth=rotatePSize/8;
	private int roTimes= 20;
	public void decreaseRoTimes(){
		roTimes--;
	}
	public int getRoTimes(){return roTimes;}
	public void setIsHighLight(boolean b) {
		isHighLight = b;
	}
	private int movingStep = 70;
	public void decreaseMovingStep(){
		movingStep-=14;
	}
	public int getMovingStep(){return movingStep;}
	public void setMovingStep(int temp){movingStep=temp;}
	public int getPanelX() {
		return panelX;
	}
	private boolean isReact=false;
	public void setIsReact(boolean b){ isReact=b;}
	public boolean getIsReact(){return isReact;}
	public int getPanelY() {
		return panelY;
	}
	public void setIsMoving(boolean b){isMoving=b;}
	public boolean getIsMoving(){return isMoving;}
	public enum Type {
		GreenPiece, YellowPiece, GrayGrid, WhiteGrid, LeftFace, RightFace, OutBoard, LeftCreation, RightCreation, EmptyPiece, NonePiece

	};

	private HashMap<String, Piece> neighbourPiece = new HashMap<String, Piece>();

	/**
	 * 
	 * **/
	public HashMap<String, Piece> getNeighbourPiece() {
		return neighbourPiece;
	}

	/**
	 * This method is used to deep clone the piece, copy all weapon and
	 * equipment to current piece
	 * 
	 * @param other
	 *            -> the piece want to be deep cloned
	 * 
	 **/
	public void deepClone(Piece other) {
		if (other instanceof Piece) {
			Piece otPiece = (Piece) other;
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					this.equipment[row][col] = otPiece.equipment[row][col];

				}
			}
			this.type = otPiece.type;
			this.name = otPiece.name;
			this.setFourWeapon(otPiece.topWeapon, otPiece.rightWeapon, otPiece.botWeapon, otPiece.leftWeapon);
			this.hasRotate = otPiece.hasRotate;// set the two boolean value as
												// well
			this.hasMove = otPiece.hasMove;

		}
	}

	public void setNeighbourPiece(HashMap<String, Piece> temp) {
		neighbourPiece = temp;
	}

	public char getTopWeapon() {
		return topWeapon;
	}

	public char getRightWeapon() {
		return rightWeapon;
	}

	public char getLeftWeapon() {
		return leftWeapon;

	}

	public char getBottomWeapon() {
		return botWeapon;
	}

	public void addNeighbourPiece(String dirction, Piece p) {
		neighbourPiece.put(dirction, p);
	}

	/**
	 * a enum value which was used indicate the different type of piece
	 **/

	public boolean getHasRotate() {
		return hasRotate;
	}

	public boolean getHasMove() {
		return hasMove;
	}

	public void setHasRotate(boolean b) {
		hasRotate = b;
	}

	public void setHasMove(boolean b) {
		hasMove = b;
	}

	public Type getType() {
		return this.type;
	}

	public char[][] getEquipment() {
		return equipment;
	}

	/**
	 * This method is used to test
	 **/
	public void printWeapon() {
		System.out.println("top: " + topWeapon + "  right:" + rightWeapon + "  bot " + botWeapon + "   left " + leftWeapon);
	}

	/**
	 * This method is used to set Four direction weapons
	 **/
	public void setFourWeapon(char top, char right, char bot, char left) {
		topWeapon = top;
		rightWeapon = right;
		botWeapon = bot;
		leftWeapon = left;
		equipment[0][1] = topWeapon;
		equipment[1][2] = rightWeapon;
		equipment[2][1] = botWeapon;
		equipment[1][0] = leftWeapon;
	}

	/**
	 * 1->0 2->90 3->180 4->270 This method is used to rotate the piece by
	 * change the piece equipment 2D, according to the particular degree
	 * 
	 * @param degree-
	 *            degree want to be rotated
	 **/
	public void rotate(String degree) {
		if (degree.equals("1")) {
			return;
		} else if (degree.equals("2")) {
			setFourWeapon(leftWeapon, topWeapon, rightWeapon, botWeapon);
		} else if (degree.equals("3")) {
			setFourWeapon(botWeapon, leftWeapon, topWeapon, rightWeapon);
		} else if (degree.equals("4")) {
			setFourWeapon(rightWeapon, botWeapon, leftWeapon, topWeapon);
		}

	}

	public void setEquipment(char[][] temp) {
		equipment = temp;
		setFourWeapon(temp[0][1], temp[1][2], temp[2][1], temp[1][0]);
	}

	/**
	 * Constructor of this class initialise name to space euqipment to a empty
	 * 3*3 2D array
	 * 
	 * @param type
	 *            - the type of Piece
	 */
	public Piece(Type type) {

		this.type = type;
		equipment = new char[3][3];
		name = ' ';
	}

	public char getName() {
		return name;
	}

	public void setName(char s) {
		name = s;
	}

	/**
	 * This method is used to avoid duplicate piece generated when the player
	 * generate 24 pieces
	 **/
	public boolean equals(Object o) {
		if (o instanceof Piece) {
			Piece other = (Piece) o;
			// compare character
			if (this.equipment[0][1] == other.equipment[0][1] && this.equipment[1][0] == other.equipment[1][0]
					&& this.equipment[1][2] == other.equipment[1][2] && this.equipment[2][1] == other.equipment[2][1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This is a helper method is used to fill character to the equipment of the
	 * piece
	 * 
	 * @param c
	 *            the particlar character want to be filled
	 */
	public void fillPieceBoardHelper(char c) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				this.equipment[row][col] = c;
			}
		}
	}

	/**
	 * This method is used to fill the equipment, according by their piece type
	 ***/
	public void fillPieceBoard() {
		if (this.type == Piece.Type.RightFace) {
			this.fillPieceBoardHelper('@');
		} else if (this.type == Piece.Type.LeftFace) {
			this.fillPieceBoardHelper('@');
		} else if (this.type == Piece.Type.GrayGrid||this.type == Piece.Type.WhiteGrid) {
			this.fillPieceBoardHelper('*');
		} else if (this.type == Piece.Type.OutBoard) {
			this.fillPieceBoardHelper('X');
		} else if (this.type == Piece.Type.RightCreation) {
			this.fillPieceBoardHelper(' ');
		} else if (this.type == Piece.Type.LeftCreation) {
			this.fillPieceBoardHelper(' ');
		}
	}







	/**********/

	public Piece getRotatePiece(String degree, Piece ot) {
		Piece temp = new Piece(ot.getType());
		temp.setName(this.getName());
		if (degree.equals("2")) {
			temp.setFourWeapon(ot.getLeftWeapon(), ot.getTopWeapon(), ot.getRightWeapon(), ot.getBottomWeapon());
		} else if (degree.equals("3")) {
			temp.setFourWeapon(ot.getBottomWeapon(), ot.getLeftWeapon(), ot.getTopWeapon(), ot.getRightWeapon());
		} else if (degree.equals("4")) {
			temp.setFourWeapon(ot.getRightWeapon(), ot.getBottomWeapon(), ot.getLeftWeapon(), ot.getTopWeapon());
		} else if (degree.equals("1")) {
			temp.setFourWeapon(ot.getTopWeapon(), ot.getRightWeapon(), ot.getBottomWeapon(), ot.getLeftWeapon());
		}

		return temp;
	}

	private void setType(Type type1) {
	}

	public void drawWeaponHelper(Graphics g, char c, int x, int y, boolean col, int offset) {

		int i = offset == 0 ? 0 : 1;

		if (col) {
			switch (c) {
			case '#':// shield horizontal
				g.fillRect(x, y + offset - weaponWidth * i, SIZE_PIECE, weaponWidth); // horizontal
																						// shield
				break;
			case '|':// sword vertical
				g.fillRect(x + SIZE_PIECE / 2, y + offset / 2, weaponWidth, SIZE_PIECE / 2); // vertical
																								// sword
				break;
			default:
				break;
			}
		} else {
			switch (c) {
			case '#':// shield vertical
				g.fillRect(x + offset - weaponWidth * i, y, weaponWidth, SIZE_PIECE); // shield
																						// vertical
				break;
			case '|':// sword horizontal
				g.fillRect(x + offset / 2, y + SIZE_PIECE / 2, SIZE_PIECE / 2, weaponWidth); // sword
				break;
			default:
				break;
			}
		}
	}
	public void drawRotateWeaponHelper(Graphics g, char c, int x, int y, boolean col, int offset) {

		int i = offset == 0 ? 0 : 1;

		if (col) {
			switch (c) {
				case '#':// shield horizontal
					g.fillRect(x, y + offset - rotateWeaponWidth * i, rotatePSize, rotateWeaponWidth); // horizontal
					// shield
					break;
				case '|':// sword vertical
					g.fillRect(x + rotatePSize / 2, y + offset / 2, rotateWeaponWidth , rotatePSize / 2); // vertical
					// sword
					break;
				default:
					break;
			}
		} else {
			switch (c) {
				case '#':// shield vertical
					g.fillRect(x + offset - rotateWeaponWidth * i, y, rotateWeaponWidth, rotatePSize); // shield
					// vertical
					break;
				case '|':// sword horizontal
					g.fillRect(x + offset / 2, y + rotatePSize / 2, rotatePSize / 2, rotateWeaponWidth); // sword
					break;
				default:
					break;
			}
		}
	}


	public void drawWeapon(Graphics g, int x, int y) {
		if (this.type == Type.GreenPiece) {
			g.setColor(Color.red);
		} else if (this.type == Type.YellowPiece) {
			g.setColor(new Color(178, 0, 255));
		}
		char top = this.equipment[0][1];
		char left = this.equipment[1][0];
		char bottom = this.equipment[2][1];
		char right = this.equipment[1][2];
		drawWeaponHelper(g, top, x, y, true, 0);
		drawWeaponHelper(g, bottom, x, y, true, SIZE_PIECE);
		drawWeaponHelper(g, left, x, y, false, 0);
		drawWeaponHelper(g, right, x, y, false, SIZE_PIECE);

	}
	public void drawRotateWeapon(Graphics g, int x, int y) {
		if (this.type == Type.GreenPiece) {
			g.setColor(Color.red);
		} else if (this.type == Type.YellowPiece) {
			g.setColor(new Color(178, 0, 255));
		}
		char top = this.equipment[0][1];
		char left = this.equipment[1][0];
		char bottom = this.equipment[2][1];
		char right = this.equipment[1][2];
		drawRotateWeaponHelper(g, top, x, y, true, 0);
		drawRotateWeaponHelper(g, bottom, x, y, true, rotatePSize);
		drawRotateWeaponHelper(g, left, x, y, false, 0);
		drawRotateWeaponHelper(g, right, x, y, false, rotatePSize);

	}
	public void markReact(Graphics g){
		g.setColor(new Color(10, 34, 211));
		g.fillOval(panelX - selectWidth*2, panelY - selectWidth*2, SIZE_PIECE + selectWidth*4 ,
				SIZE_PIECE + selectWidth * 4);
	}
	public void highLightSelect(Graphics g) {
		g.setColor(new Color(0, 255, 239));
		g.fillRect(panelX - selectWidth, panelY - selectWidth, SIZE_PIECE + selectWidth * 2,
				SIZE_PIECE + selectWidth * 2);
	}

	public void drawRotatePiece(Graphics g, int x, int y, int row, int col){

		if (this.type == Type.GreenPiece) {
			if (isHighLight) {
				highLightSelect(g);
			}
			if(isReact){
				markReact(g);
			}
			g.setColor(Color.BLACK);
			g.fillRect(x, y, rotatePSize, rotatePSize);
			g.setColor(Color.GREEN);
			g.fillOval(x, y, rotatePSize, rotatePSize);
			drawRotateWeapon(g, x, y);
			panelX = x;
			panelY = y;
		} else if (this.type == Type.YellowPiece) {
			if (isHighLight) {
				highLightSelect(g);
			}
			if(isReact){
				markReact(g);
			}
			g.setColor(Color.BLACK);
			g.fillRect(x, y, rotatePSize, rotatePSize);
			g.setColor(Color.yellow);
			g.fillOval(x, y, rotatePSize, rotatePSize);
			drawRotateWeapon(g, x, y);
			panelX = x;
			panelY = y;
		}

	}

	public void drawPiece(Graphics2D graphics2D, int x, int y) {

		if (this.type == Type.RightCreation) {
			graphics2D.setColor(Color.ORANGE);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);

		} else if (this.type == Type.LeftCreation) {
			graphics2D.setColor(Color.GREEN);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
		} else if (this.type == Type.OutBoard) {
			graphics2D.setColor(Color.LIGHT_GRAY);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
		} else if (this.type == Type.GrayGrid) {
			graphics2D.setColor(Color.DARK_GRAY);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
		} else if (this.type == Type.LeftFace) {
			graphics2D.drawImage(ImageResource.RIGHT.img,x,y,SIZE_PIECE,SIZE_PIECE,null);

		} else if (this.type == Type.RightFace) {

			graphics2D.drawImage(ImageResource.LEFT.img,x,y,SIZE_PIECE,SIZE_PIECE,null);



		} else if (this.type == Type.WhiteGrid) {
			graphics2D.setColor(Color.WHITE);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);

		} else if (this.type == Type.GreenPiece) {
			if (isHighLight) {
				highLightSelect(graphics2D);
			}
			if(isReact){
				markReact(graphics2D);
			}
			graphics2D.setColor(Color.BLACK);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			graphics2D.setColor(Color.GREEN);
			graphics2D.fillOval(x, y, SIZE_PIECE, SIZE_PIECE);
			drawWeapon(graphics2D, x, y);
			panelX = x;
			panelY = y;
		} else if (this.type == Type.YellowPiece) {
			if (isHighLight) {
				highLightSelect(graphics2D);
			}
			if(isReact){
				markReact(graphics2D);
			}
			graphics2D.setColor(Color.BLACK);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			graphics2D.setColor(Color.yellow);
			graphics2D.fillOval(x, y, SIZE_PIECE, SIZE_PIECE);
			drawWeapon(graphics2D, x, y);
			panelX = x;
			panelY = y;
		} else if (this.type == Type.EmptyPiece) {
			graphics2D.setColor(Color.pink);
			graphics2D.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
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
