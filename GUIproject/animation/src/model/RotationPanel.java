package model;

import gameComponent.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by minpingyang on 23/08/17.
 */
public class RotationPanel extends Observable {
    private Piece selectPiece;
    private Piece[][] piecesBoard;
    private Board board;
    private Piece[][] backgroudBoard;
    private Point selectPoint;
    private int rotateTime;
    public Point getSelectPoint(){
        return selectPoint;
    }

    public Piece[][] getBackgroudBoard(){return backgroudBoard;}
    public Piece[][] getPiecesBoard(){return piecesBoard;}
    public Piece getSelectPiece(){return selectPiece;}
    public Board getBoard(){return board;}

    public RotationPanel(Board board){
        this.board=board;
        this.piecesBoard=board.getPiecesBoard();
        this.backgroudBoard=board.getBoard();
        rotateTime=0;
        setChanged();
        notifyObservers();

    }
    public void setSelectPoint(){
        ArrayList<Integer> roCol= board.findPieceCoord(selectPiece.getName());
        int row = roCol.get(0);
        int col = roCol.get(1);
        selectPoint = new Point(col*Piece.SIZE_PIECE,row*Piece.SIZE_PIECE);
        setChanged();
        notifyObservers();
    }
    public int getRotateTime(){return rotateTime;}
    public void rotatePiece(String degree,Piece selectPiece){
        if (degree.equals("2")) {
            rotateTime++;
            System.out.println("rotate Time: "+rotateTime);
            selectPiece.setFourWeapon(selectPiece.getLeftWeapon(), selectPiece.getTopWeapon(), selectPiece.getRightWeapon(),selectPiece.getBottomWeapon());
        }
        setChanged();
        notifyObservers();
    }

    public void setSelectPiece(Piece selectPiece) {
        this.selectPiece = selectPiece;
        setChanged();
        notifyObservers();
    }
}
