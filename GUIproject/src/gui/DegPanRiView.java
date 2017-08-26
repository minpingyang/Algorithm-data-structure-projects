package gui;

import gameComponent.Piece;
import model.DegreePanRight;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 25/08/17.
 */
public class DegPanRiView extends JComponent implements Observer {
    private int top = 10;
    private int left = 13;
    private DegreePanRight degreePanRight;
    public DegPanRiView(DegreePanRight degreePanRight){
        this.degreePanRight=degreePanRight;
        this.degreePanRight.addObserver(this);
        this.setFocusable(true);
    }
    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(degreePanRight.getSelectPiece()!=null){

            int outline = 13;

            for(int col=0;col<4;col++){
                degreePanRight.getDiffDegreePiece().get(col).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top,0,col);

                degreePanRight.getPiecesPoint().add(new Point(left+col*(Piece.SIZE_PIECE+outline),top));
            }

        }
    }

    @Override
    public Dimension getPreferredSize(){
        //width -> (5)*50
        //height-> (10+10)*50
        return new Dimension(5*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));
    }


    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
