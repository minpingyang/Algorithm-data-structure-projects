package gui;

import gameComponent.Piece;
import model.RotationPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 25/08/17.
 */
public class RotationPanView extends JComponent implements Observer{
    private RotationPanel rotationPanel;
    public RotationPanView(RotationPanel rotationPanel){
        this.rotationPanel =rotationPanel;
        this.rotationPanel.addObserver(this);
        this.setFocusable(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.3f));

        g2d.setColor(Color.PINK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        for(int row = 0; row<10;row++){
            for(int col=0;col<10;col++){
                rotationPanel.getBackgroudBoard()[row][col].drawPiece(g2d,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);
            }
        }
        for(int row = 0; row<10;row++){
            for(int col=0;col<10;col++){
                rotationPanel.getPiecesBoard()[row][col].drawPiece(g2d,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);

            }
        }
        //super.paint(g2d);
        rotationPanel.getSelectPiece().setIsHighLight(false);

        ArrayList<Integer> roCol= rotationPanel.getBoard().findPieceCoord(rotationPanel.getSelectPiece().getName());
        int row = roCol.get(0);
        int col = roCol.get(1);
        rotationPanel.getSelectPiece().drawRotatePiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,rotationPanel.getBoard().getRowB(),rotationPanel.getBoard().getColB());
//        selectPoint = new Point(col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE);

    }




    public Dimension getPreferredSize() {return new Dimension(10*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));}
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
