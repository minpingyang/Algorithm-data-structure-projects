package gui;

import gameComponent.Piece;
import model.DegreePanLeft;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 25/08/17.
 */
public class DegPanLeView extends JComponent implements Observer {
    private int top = 10;
    private int left = 12;
    private DegreePanLeft degreePanLeft;
    private float alpha;
    public DegPanLeView(DegreePanLeft degreePanLeft){
        this.degreePanLeft=degreePanLeft;
        this.degreePanLeft.addObserver(this);
        this.setFocusable(true);
        setOpaque(false);
    }
    public void paintComponent(Graphics _g){
        super.paintComponent(_g);

        Graphics2D g = (Graphics2D)_g;


        g.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(degreePanLeft.getSelectPiece()!=null){

            int outline = 13;

            for(int col=0;col<4;col++){
                degreePanLeft.getDiffDegreePiece().get(col).drawPiece(g,left+col*(Piece.SIZE_PIECE+outline),top,0,col);
//                    System.out.println("draw name: "+diffDegreePiece.get(col).getName());
//                    diffDegreePiece.get(col).printWeapon();
                degreePanLeft.getPiecesPoint().add(new Point(left+col*(Piece.SIZE_PIECE+outline),top));
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
    public void update(Observable o, Object arg) {repaint();}
}
