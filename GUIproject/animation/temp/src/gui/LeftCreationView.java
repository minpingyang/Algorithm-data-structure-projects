package gui;

import gameComponent.Piece;
import model.LeftCreation;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 25/08/17.
 */
public class LeftCreationView extends JComponent implements Observer{

    private int top = 10;
    private int left = 12;
    private LeftCreation leftCreation;
    private float alpha;

    public LeftCreationView(LeftCreation leftCreation){
        setOpaque(false);
        this.leftCreation= leftCreation;
        this.leftCreation.addObserver(this);
        this.setFocusable(true);
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d= (Graphics2D) g;
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2d.setColor(Color.PINK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        leftCreation.setPieces(leftCreation.getGreenPlayer().getPieces());
        //System.out.println("size: "+temp.size());
        int i =0;
        int outline = 13;
        for(int row = 0; row<6;row++){
            for(int col=0;col<4;col++){
                //System.out.printf("\n left: %c ,",temp.get(i).getName());

                leftCreation.getPieces().get(i++).drawPiece(g2d,left+col*(Piece.SIZE_PIECE+outline),top+ row*(Piece.SIZE_PIECE+outline));

                leftCreation.getPiecesPoint().add(new Point(left+col*(Piece.SIZE_PIECE+outline), top+ row*(Piece.SIZE_PIECE+outline)));
            }
        }


    }
    public void setAlpha(float value) {
        if (alpha != value) {
            this.alpha = Math.min(1f, Math.max(0, value));
            repaint();
        }
    }

    public float getAlpha() {
        return alpha;
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
