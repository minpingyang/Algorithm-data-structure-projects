package model;

import gameComponent.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by minpingyang on 21/08/17.
 */
public class DegreePanRight extends Observable {
    /**
     *
     */


    private static final long serialVersionUID = 1L;
    private Piece selectPiece;
    private List<Point> piecesPoint=new ArrayList<Point>();
    private List<Piece> diffDegreePiece=new ArrayList<Piece>();
    public List<Point> getPiecesPoint(){
        return piecesPoint;
    }
    public  Piece getSelectPiece(){
        return selectPiece;
    }
    public void setSelectPiece(Piece temp){
        selectPiece=temp;
        Piece selectPiece1= selectPiece.getRotatePiece("1", selectPiece);
        Piece selectPiece2= selectPiece.getRotatePiece("2", selectPiece);
        Piece selectPiece3= selectPiece.getRotatePiece("3", selectPiece);
        Piece selectPiece4= selectPiece.getRotatePiece("4", selectPiece);

        diffDegreePiece.clear();
        diffDegreePiece.add(selectPiece1);
        diffDegreePiece.add(selectPiece2);
        diffDegreePiece.add(selectPiece3);
        diffDegreePiece.add(selectPiece4);
        setChanged();
        notifyObservers();
    }
    public List<Piece> getDiffDegreePiece(){
        return diffDegreePiece;
    }





}
