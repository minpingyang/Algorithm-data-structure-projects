package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Controller implements MouseListener,KeyListener {
//    public static boolean canClick= true;
	private List<Point> leftPoint,degreeLeftPoint;
	private List<Piece> leftPieces,degreeLeftPieces;

	private List<Point> rightPoint,degreeRightPoint;
	private List<Piece> rightPieces,degreeRightPieces;

	private Point[][] boardPoint= new Point[10][10];
	private int selectIndex;

	private View view;
	private JFrame jFrame;
	private int hasClicked = 0;
	private Piece previousSe = null;

	private DegreePanLeft degreePanLeft;
	private DegreePanRight degreePanRight;
    private int selectRange=Piece.SIZE_PIECE/4;
	private JPanel panelConLeft,panelConRight;
	private CardLayout cardLayout1,cardLayout2;

	private Board board;

    public Controller(View view) {

		leftPoint = view.getLeftCreation().getPiecesPoint();
		leftPieces = view.getGreenPlayer().getPieces();
		rightPoint = view.getRightCreation().getPiecesPoint();
		rightPieces = view.getYellowPlayer().getPieces();
		jFrame = view.getJFrame();
		this.view = view;

		panelConLeft=view.getPanelConLeft();
		panelConRight=view.getPanelConRight();

		cardLayout1 =view.getCardLayout1();
		cardLayout2= view.getCardLayout2();

		degreePanLeft =view.getDegreePanLeft();
		degreePanRight=view.getDegreePanRight();

		degreeLeftPieces=degreePanLeft.getDiffDegreePiece();
		degreeLeftPoint=degreePanLeft.getPiecesPoint();

		degreeRightPieces=degreePanRight.getDiffDegreePiece();
		degreeRightPoint=degreePanRight.getPiecesPoint();
		board= view.getBoard();
		boardPoint = board.getPiecePoint();
	}

	

	public boolean checkTwoPoint(Point clickP, Point pointP) {

		double rightMost = pointP.getX() + Piece.SIZE_PIECE;
		double leftMost = pointP.getX();
		double topMost = pointP.getY();
		double bottomMost = pointP.getY() + Piece.SIZE_PIECE;
		boolean b = clickP.getX() <= rightMost && clickP.getX() >= leftMost && clickP.getY() <= bottomMost
				&& clickP.getY() >= topMost;

		return b;
	}
	//for move
    public int checkPoitMove(Point clickP,Point pointP){
        double cX = clickP.getX();
        double cY = clickP.getY();
        double rightMostOut = pointP.getX() + Piece.SIZE_PIECE;
        double leftMostOut = pointP.getX();
        double topMostOut = pointP.getY();
        double bottomMostOut = pointP.getY() + Piece.SIZE_PIECE;
        double rightMostIn = rightMostOut-selectRange;
        double leftMostIn= leftMostOut+selectRange;
        double topMostIn= topMostOut+selectRange;
        double bottomMostIn = bottomMostOut-selectRange;
        boolean bLeft = cX>leftMostOut&&cX<leftMostIn&& cY>topMostIn&&cY<bottomMostIn;
        boolean bRight =cX<rightMostOut&&cX>rightMostIn&& cY>topMostIn&&cY<bottomMostIn;
        boolean bUp = cX>leftMostIn&&cX<rightMostIn&&cY>topMostOut&&cY<topMostIn;
        boolean bDown= cX>leftMostIn&&cX<rightMostIn&&cY>bottomMostIn&&cY<bottomMostOut;
        if(bUp) return 1;
        if(bRight) return 2;
        if(bDown) return 3;
        if(bLeft) return 4;

        return 0;

    }

    public void clickToMove(Point[][] points, Point p){
        boolean clickOn =doesClickOne(points,p,true);
        if(!clickOn){
            System.out.println("have not clicked right position");
            return;
        }
        Piece selectPiece=board.getPieceBoard()[board.getRowB()][board.getColB()];
        int dir = board.getMoveDir();
        String direction="1";
        if(dir==1) direction="up";
        if(dir==2) direction="right";
        if(dir==3) direction="down";
        if(dir==4) direction= "left";
        if(!direction.equals("1")){
            char piecesName = board.getPiecesBoard()[board.getRowB()][board.getColB()].getName();
            String command = "move "+piecesName+" "+direction;
            System.out.println("command: "+command);
            try {
                view.inputCommand(command);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

    }
    //for board
    public void selectHelper(Point[][] points,Point p){
        boolean clickOn= doesClickOne(points,p,false);
        if(!clickOn){
            System.out.println("have not clicked right position");
            return;
        }
        Piece selectPiece = board.getPieceBoard()[board.getRowB()][board.getColB()];
        clickHelper(selectPiece,false,false,true);


    }
	public boolean doesClickOne(Point clickP, List<Point> piecePoints) {

		for (Point piecePoint : piecePoints) {
			if (!checkTwoPoint(clickP, piecePoint)) {
				continue;
			} else {

				selectIndex = piecePoints.indexOf(piecePoint);
                return true;
			}
		}

		return false;
	}
    public boolean doesClickOne(Point[][] points,Point p,boolean isMove){
	    for(int row=0;row<10;row++){
	        for(int col=0;col<10;col++){
	            if(!isMove&&points[row][col]!=null&&checkTwoPoint(p,points[row][col])){
                    board.setRowB(row);
                    board.setColB(col);

                    return true;
                }
                if(isMove&&points[row][col]!=null&&(checkPoitMove(p,points[row][col])!=0)){
                    System.out.println("Click Diretion "+checkPoitMove(p,points[row][col]));
                    board.setRowB(row);
                    board.setColB(col);
                    board.setMoveDir(checkPoitMove(p,points[row][col]));
                    return true;
                }
            }
        }
        return false;

    }

	@Override
	public void mouseClicked(MouseEvent e) {
	}


    public  void clickHelper(Piece selectPiece,boolean isDegreePanel,boolean isLeft,boolean isBoard){
        // s->0->hig s->1->hig
        selectPiece.setIsHighLight(true);

        hasClicked++;// has->1 ->2->1
        if ((hasClicked == 2 || hasClicked == 1) && previousSe != null) {
            // 0->hidd
            previousSe.setIsHighLight(false);
        }


        if (hasClicked == 1) {//
            // pre->0
            previousSe = selectPiece; // 0 : 0
            jFrame.repaint();
        }
        if (hasClicked == 2) {
            // pr->1
            previousSe = selectPiece;
            hasClicked = 0;
            jFrame.repaint();
        }
        view.setDoesClPieBoard(isBoard);   // TODO ALREADY MOVED CANNOT BE SELECTED
        if(!isBoard) {selectOrientation(selectPiece,isDegreePanel,isLeft);}

        if(isDegreePanel){
            String degree = Integer.toString(selectIndex+1);
            char name= selectPiece.getName();
            String command = "create "+name+" "+degree;
            try {
                view.inputCommand(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }




    //for left/right
	public void selectHelper(List<Point> points, List<Piece> pieces,Point p,boolean isDegreePanel,boolean isLeft) throws InterruptedException{
	
		boolean clickOn = doesClickOne(p, points);
	
		if (!clickOn) {
			System.out.println("have not clicked right position");
			return;
		}
        Piece selectPiece=pieces.get(selectIndex);
        clickHelper(selectPiece,isDegreePanel,isLeft,false);
	}


	public void selectOrientation(Piece selectPiece,boolean isDegreePanel,boolean isLeft){

        if(!isDegreePanel&&isLeft){
            System.out.println("selectPiece: "+selectPiece.getName());
            view.getDegreePanLeft().setSelectPiece(selectPiece);
            cardLayout1.show(view.getPanelConLeft(),"2");
        }else if(isDegreePanel&&isLeft){
            cardLayout1.show(panelConLeft,"1");
        }else if(!isDegreePanel&&!isLeft){

            degreePanRight.setSelectPiece(selectPiece);
            cardLayout2.show(panelConRight,"4");
//            panelConRight.getClass().getName();
        }else if(isDegreePanel&&!isLeft){
            cardLayout2.show(panelConRight,"3");
        }

    }

	@Override
	public void mousePressed(MouseEvent e) {
//	    if(!canClick) return;
		Point p = e.getPoint();
		
		
		if (p != null) {
			if(e.getSource() instanceof LeftCreation){
				try {
					selectHelper(leftPoint, leftPieces, p,false,true);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() instanceof RightCreation){
				try {
					selectHelper(rightPoint, rightPieces, p,false,false);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() instanceof DegreePanLeft){
                try {
                    selectHelper(degreeLeftPoint,degreeLeftPieces,p,true,true);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }else if(e.getSource() instanceof DegreePanRight){
                try {
                    selectHelper(degreeRightPoint,degreeRightPieces,p,true,false);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }else if(e.getSource() instanceof Board){
                    if(!view.getDoesCliPieBoard()){
                        System.out.println("click to chose");
                        selectHelper(boardPoint,p);
                    }else{
                        System.out.println("click to move");
                        clickToMove(boardPoint,p);
                    }


            }
		}

	}
	public void clickMove(){

    }

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       int key = e.getKeyCode();
       if(!view.getDoesCliPieBoard()) {
           return;
       }
       String dir="1";

       if(key==KeyEvent.VK_RIGHT||key==KeyEvent.VK_KP_RIGHT) dir="right";
       if(key==KeyEvent.VK_LEFT||key== KeyEvent.VK_KP_LEFT) dir="left";
       if(key==KeyEvent.VK_UP||key==KeyEvent.VK_KP_UP) dir ="up";
       if(key==KeyEvent.VK_DOWN|| key==KeyEvent.VK_KP_DOWN) dir= "down";





       if(!dir.equals("1")){

           char piecesName = board.getPiecesBoard()[board.getRowB()][board.getColB()].getName();
           String command = "move "+piecesName+" "+dir;
           System.out.println("command: "+command);
           try {
               view.inputCommand(command);
           } catch (InterruptedException e1) {
               e1.printStackTrace();
           }
       }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
