package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Controller implements MouseListener {

	private List<Point> leftPoint,degreeLeftPoint;
	private List<Piece> leftPieces,degreeLeftPieces;

	private List<Point> rightPoint,degreeRightPoint;
	private List<Piece> rightPieces,degreeRightPieces;

	private Point[][] boardPoint= new Point[10][10];
	private int selectIndex,rowB,colB;

	private View view;
	private JFrame jFrame;
	private int hasClicked = 0;
	private Piece previousSe = null;

	private DegreePanLeft degreePanLeft;
	private DegreePanRight degreePanRight;

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

	public boolean doesClickOne(Point clickP, List<Point> piecePoints) {

		for (Point piecePoint : piecePoints) {
			if (!checkTwoPoint(clickP, piecePoint)) {
				continue;
			} else {

				selectIndex = piecePoints.indexOf(piecePoint);
                System.out.println("selectIndex: "+selectIndex);
                return true;
			}
		}

		return false;
	}
    public boolean doesClickOne(Point[][] points,Point p){
	    for(int row=0;row<10;row++){
	        for(int col=0;col<10;col++){
	            if(points[row][col]!=null&&checkTwoPoint(p,points[row][col])){
                    rowB= row;
                    colB = col;
//                    System.out.println("find the clicked piece on the board");
//                    System.out.println("rowB: "+rowB+" colB"+colB);
                    return true;
                }
            }
        }
        return false;

    }

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void selectHelper(Point[][] points,Point p){
		boolean clickOn= doesClickOne(points,p);
		if(!clickOn){
            System.out.println("have not clicked right position");
            return;
        }
        Piece selectPiece = board.getPieceBoard()[rowB][colB];
		clickHelper(selectPiece,false,false,true);


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

        if(!isBoard) selectOrientation(selectPiece,isDegreePanel,isLeft);
        if(isDegreePanel){
            String degree = Integer.toString(selectIndex+1);
            System.out.println("degree"+degree);
            char name= selectPiece.getName();
            String command = "create "+name+" "+degree;
            try {
                view.inputCommand(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
	
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
            degreePanLeft.setSelectPiece(selectPiece);
            cardLayout1.show(panelConLeft,"2");
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
                System.out.println("click the board panel");
                selectHelper(boardPoint,p);
            }
		}

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

}
