package gui;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.DocumentListener;

import model.Board;
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
	
	private Frame gui;
	private int hasClicked = 0;
	Piece previousSe = null;

	public Controller(LeftCreation leftCreation, RightCreation rightCreation, Player greenPlayer, Player yellowPlayer,
			Frame gui) {

		leftPoint = leftCreation.getPiecesPoint();
		leftPieces = greenPlayer.getPieces();
		rightPoint = rightCreation.getPiecesPoint();
		rightPieces = yellowPlayer.getPieces();
		this.gui = gui;
	}

	public void chooseOrientation() {

	}

	public boolean checkTwoPoint(Point clickP, Point pointP) {
		System.out.println("22222");

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
				System.out.println("33333");
				selectIndex = piecePoints.indexOf(piecePoint);
				System.out.println("**clicked***********");
				return true;
			}
		}

		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	
	public void selectHelper(List<Point> points, List<Piece> pieces,Point p){
		System.out.println("1111");
		boolean clickOn = doesClickOne(p, points);
	
		if (!clickOn) {
			System.out.println("have not clicked right position");
			return;
		}
		selectPiece=pieces.get(selectIndex);

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
			System.out.println("click2: " + hasClicked);
			gui.repaint();
		}
		if (hasClicked == 2) {
			// pr->1
			previousSe = selectPiece;
			hasClicked = 0;
			System.out.println("click3: " + hasClicked);
			gui.repaint();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		
		
		if (p != null) {
			if(e.getSource() instanceof LeftCreation){
				selectHelper(leftPoint, leftPieces, p);
			}else if(e.getSource() instanceof RightCreation){
				selectHelper(rightPoint, rightPieces, p);
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
