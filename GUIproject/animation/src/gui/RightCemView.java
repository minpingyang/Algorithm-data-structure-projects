package gui;

import gameComponent.Piece;
import model.RightCemetery;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 25/08/17.
 */
public class RightCemView extends JComponent implements Observer {
    private int top = 10;
    private int left = 13;
    private RightCemetery rightCemetery;
    public RightCemView(RightCemetery rightCemetery){
        this.rightCemetery = rightCemetery;
        this.rightCemetery.addObserver(this);
        this.setFocusable(true);
    }

   @Override
   public void paintComponent(Graphics _g){
       super.paintComponent(_g);
       Graphics2D g = (Graphics2D)_g;
       g.setColor(Color.PINK);
       g.fillRect(0, 0, getWidth(), getHeight());
       rightCemetery.setPieces(rightCemetery.getYellowPlayer().getPieces());
       int i=0;
       int outline = 10;
       for(int row = 0; row<6;row++){
           for(int col=0;col<4;col++){
               rightCemetery.getPieces().get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+row*(Piece.SIZE_PIECE+outline),row,col);
               rightCemetery.getPiecesPoint().add(new Point(left+col*(Piece.SIZE_PIECE+outline),top+row*(Piece.SIZE_PIECE+outline)));
           }
       }

   }

    @Override
    public void update(Observable o, Object arg) {
      repaint();
    }
}
