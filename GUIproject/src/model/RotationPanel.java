package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by minpingyang on 23/08/17.
 */
public class RotationPanel extends JPanel {
    private Piece selectPiece;
    private Piece[][] piecesBoard;
    private Board board;
    private Piece[][] backgroudBoard;
    private Point selectPoint;
    public Point getSelectPoint(){
        return selectPoint;
    }

    public RotationPanel(Board board){
        this.board=board;
        this.piecesBoard=board.getPiecesBoard();
        this.backgroudBoard=board.getBoard();

    }
    public void setSelectPoint(){
        ArrayList<Integer> roCol= board.findPieceCoord(selectPiece.getName());
        int row = roCol.get(0);
        int col = roCol.get(1);
        selectPoint = new Point(col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE);
    }
    @Override
    public void paint(Graphics g){
        System.out.println("paint gray board");
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.3f));

        g2d.setColor(Color.PINK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        for(int row = 0; row<10;row++){
            for(int col=0;col<10;col++){
                backgroudBoard[row][col].drawPiece(g2d,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
            }
        }
        for(int row = 0; row<10;row++){
            for(int col=0;col<10;col++){
                piecesBoard[row][col].drawPiece(g2d,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);

            }
        }
        //super.paint(g2d);
        selectPiece.setIsHighLight(false);

        ArrayList<Integer> roCol= board.findPieceCoord(selectPiece.getName());
        int row = roCol.get(0);
        int col = roCol.get(1);
        selectPiece.drawRotatePiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,board.getRowB(),board.getColB());
//        selectPoint = new Point(col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE);


    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        int rotateSize= Piece.SIZE_PIECE+10;
        System.out.println("paint component");
        selectPiece.drawPiece(g,board.getColB()*rotateSize,board.getRowB()*rotateSize,board.getRowB(),board.getColB());
        // Fake the background
//        g.setColor(getBackground());
//        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setSelectPiece(Piece selectPiece) {
        this.selectPiece = selectPiece;
    }
}
