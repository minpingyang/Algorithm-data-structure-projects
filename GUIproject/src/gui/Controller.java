package gui;

import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.LeftCreation;
import model.Piece;
import model.Player;
import model.RightCreation;

public class Controller implements MouseListener {

	private List<Point> leftPoint;
	private List<Piece> leftPieces;
	private Piece selectPiece;
	private List<Point> rightPoint;
	private List<Piece> rightPieces;
	private int selectIndex;
	private View view;
	private Frame gui;
	private int hasClicked = 0;
	private Piece previousSe = null;
	private char creatPiName;
	private String creatDegree;
	private JPanel panelConLeft;
	private CardLayout cardLayout;

	public Controller(View view) {

		leftPoint = view.getLeftCreation().getPiecesPoint();
		leftPieces = view.getGreenPlayer().getPieces();
		rightPoint = view.getRightCreation().getPiecesPoint();
		rightPieces = view.getYellowPlayer().getPieces();
		gui = view.getJFrame();
		this.view = view;
		panelConLeft=view.getPanelConLeft();
		cardLayout=view.getCardLayout();
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
				return true;
			}
		}

		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	public void chooseOrientation() throws InterruptedException {
		
		String[] options = new String[] {"0", "90", "180", "270"};
	    int response = JOptionPane.showOptionDialog(null, "choose the degree", "Orientation Chooser",
	        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
	        null, options, options[0]);
	    switch (response) {
		case 0:
			creatDegree="1";
			break;
		case 1:
			creatDegree="2";
			break;
		case 2:
			creatDegree="3";
			break;
		case 3:
			creatDegree="4";
			break;	
		default:
			break;
		}
	    String command = "create "+creatPiName+" "+creatDegree;
	    view.inputCommand(command);
	
	}
	
	public void selectHelper(List<Point> points, List<Piece> pieces,Point p) throws InterruptedException{
	
		boolean clickOn = doesClickOne(p, points);
	
		if (!clickOn) {
			System.out.println("have not clicked right position");
			return;
		}
		selectPiece=pieces.get(selectIndex);
		creatPiName=selectPiece.getName();
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
			gui.repaint();
		}
		if (hasClicked == 2) {
			// pr->1
			previousSe = selectPiece;
			hasClicked = 0;
			gui.repaint();
		}
		chooseOrientation();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		
		if (p != null) {
			if(e.getSource() instanceof LeftCreation){
				try {
					selectHelper(leftPoint, leftPieces, p);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(e.getSource() instanceof RightCreation){
				try {
					selectHelper(rightPoint, rightPieces, p);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
