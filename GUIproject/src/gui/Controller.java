package gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import model.LeftCreation;
import model.Piece;
import model.RightCreation;

public class Controller implements MouseListener {
	private LeftCreation leftCreation;
	private RightCreation rightCreation;
	private List<Point> leftPoint;
	private List<Piece> leftPieces;
	private Piece selectPiece;
	private List<Point> rightPoint;
	private int selectIndex;
	public Controller(LeftCreation leftCreation, RightCreation rightCreation) {
		this.leftCreation=leftCreation;
		this.rightCreation=rightCreation;
		leftPoint=leftCreation.getPiecesPoint();
		leftPieces=leftCreation.getPieces();
//		rightCreation=
	}
	public void chooseOrientation(){
		
	}
	public boolean checkTwoPoint(Point clickP,Point pointP){
		
		int pointPX = (int)pointP.getX();
		int pointPY = (int)pointP.getY();
		Rectangle pieceRe = new Rectangle(pointPX, pointPY, Piece.SIZE_PIECE, Piece.SIZE_PIECE);
		return pieceRe.contains(clickP);
		

	}
	public boolean doesClickOne(Point clickP, List<Point> pieces){
		for(Point piecePoint:pieces){
			if(checkTwoPoint(clickP, piecePoint)){
				selectIndex=pieces.indexOf(piecePoint);
				return true;
			}	
		}
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p=e.getPoint();
		if(p!=null){
			boolean clickLeft=doesClickOne(p, leftPoint);
			if(clickLeft){
			  selectPiece=leftPieces.get(selectIndex);
			  
			}
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
