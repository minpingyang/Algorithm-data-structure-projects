package gui;

import model.Board;
import gameComponent.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by minpingyang on 24/08/17.
 */
public class BoardView extends JComponent implements Observer {
    private Board board;
    public BoardView(Board board){
        this.board=board;
        this.board.addObserver(this);
        this.setFocusable(true);
    }
    @Override
    public void paintComponent(Graphics _g){
        super.paintComponent(_g);
        Graphics2D g = (Graphics2D)_g;
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());
        for(int row = 0; row<10;row++){
            for(int col=0;col<10;col++){
                board.getBoard()[row][col].drawPiece(g,col* Piece.SIZE_PIECE,row*Piece.SIZE_PIECE);
            }
        }
        for(int row = 0; row<10;row++){
            for(int col=0;col<10;col++){

                //board.getPieceBoard()[row][col].drawPiece(g,col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE,row,col);

                if(board.getPieceBoard()[row][col].getType()== Piece.Type.GreenPiece||board.getPieceBoard()[row][col].getType()== Piece.Type.YellowPiece){
                    if(!board.getPieceBoard()[row][col].getIsMoving()){
                        board.getPiecePoint()[row][col]=new Point(col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE);
//                        System.out.println("Now UP  piece is : "+board.getPieceBoard()[row-1][col].getName());
//                        System.out.println("Now CREATION piece is : "+board.getPieceBoard()[row][col].getName());
                    }

                    board.getPieceBoard()[row][col].drawPiece(g,board.getPiecePoint()[row][col].x,board.getPiecePoint()[row][col].y);

                }
            }
        }
        if(board.getPiecePoint()[2][2]!=null){
//            System.out.println("changing y: "+board.getPiecePoint()[2][2].y);
        }





        String s=" ";
        if(board.getIsleftTurn()){
            g.setColor(Color.green);
            s="Now It is Left player turn!";}
        else{
            g.setColor(Color.yellow);
            s="Now It is Right player turn!";
        }
        Font font0 =new Font("arial",Font.BOLD,40);
        g.setFont(font0);
        g.drawString(s,Piece.SIZE_PIECE,11*Piece.SIZE_PIECE);

    }
    public Dimension getPreferredSize() {return new Dimension(10*(Piece.SIZE_PIECE),11*(Piece.SIZE_PIECE));}
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
