package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DegreePanLeft extends JPanel {
	/**
	 * 
	 */
	private int top = 10;
	private int left = 12;
	private static final long serialVersionUID = 1L;
	private Piece selectPiece;
	private List<Point> piecesPoint=new ArrayList<Point>();
	private List<Piece> diffDegreePiece=new ArrayList<Piece>();
	public List<Point> getPiecesPoint(){
		return piecesPoint;
	}

	public void setSelectPiece(Piece temp){
		List<Piece> late= new ArrayList<Piece>();
	    selectPiece=temp;
		Piece selectPiece1= selectPiece.getRotatePiece("1", selectPiece);
		Piece selectPiece2= selectPiece.getRotatePiece("2", selectPiece);
		Piece selectPiece3= selectPiece.getRotatePiece("3", selectPiece);
		Piece selectPiece4= selectPiece.getRotatePiece("4", selectPiece);

//		System.out.println("name: "+selectPiece1.getName());
//
////		selectPiece1.printWeapon();
////		selectPiece2.printWeapon();
////		selectPiece3.printWeapon();
////		selectPiece4.printWeapon();
        diffDegreePiece.clear();
        diffDegreePiece.add(selectPiece1);
        diffDegreePiece.add(selectPiece2);
		diffDegreePiece.add(selectPiece3);
		diffDegreePiece.add(selectPiece4);

	}
	public List<Piece> getDiffDegreePiece(){
		return diffDegreePiece;
	}


	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(selectPiece!=null){
			
			int outline = 13;

				for(int col=0;col<4;col++){
					diffDegreePiece.get(col).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top,0,col);
//                    System.out.println("draw name: "+diffDegreePiece.get(col).getName());
                    diffDegreePiece.get(col).printWeapon();
					piecesPoint.add(new Point(left+col*(Piece.SIZE_PIECE+outline),top));
				}
		
		}
	
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	@Override
	public Dimension getPreferredSize(){
		//width -> (5)*50
		//height-> (10+10)*50
		return new Dimension(5*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));
	}
}
