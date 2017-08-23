package model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by minpingyang on 23/08/17.
 */
public class RotationPanel extends JPanel {
    private Piece selectPiece;
    private Piece[][] piecesBoard;
    private Board board;
    private Piece[][] backgroudBoard;

    public RotationPanel(Board board){
        this.board=board;
        this.piecesBoard=board.getPiecesBoard();
        this.backgroudBoard=board.getBoard();

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


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fake the background
//        g.setColor(getBackground());
//        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setSelectPiece(Piece selectPiece) {
        this.selectPiece = selectPiece;
    }
}
