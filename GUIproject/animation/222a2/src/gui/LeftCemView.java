package gui;

import gameComponent.Piece;
import model.LeftCemetery;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 25/08/17.
 */
public class LeftCemView extends JComponent implements Observer {
    private LeftCemetery leftCemetery;
    private int top = 10;
    private int left = 12;
    public LeftCemView(LeftCemetery leftCemetery){
        this.leftCemetery=leftCemetery;
        this.leftCemetery.addObserver(this);
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics _g){
        super.paintComponent(_g);
        Graphics2D g = (Graphics2D)_g;
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());

        System.out.println("PAINT size:  "+leftCemetery.getLeftDeadPiece().size());
        int i=0;
        int outline = 10;
        for(int row = 0; row<6;row++){
            for(int col=0;col<4;col++){
//                System.out.println("leftCemetry size:  "+leftCemetery.getLeftDeadPiece().size());
                if(i<leftCemetery.getLeftDeadPiece().size()){

                    leftCemetery.getLeftDeadPiece().get(i++).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top+row*(Piece.SIZE_PIECE+outline));
                }

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
