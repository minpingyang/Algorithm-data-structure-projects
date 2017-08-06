package swen222.swordShield;

import java.awt.Color;
import java.awt.Graphics;





public class Piece{
	public static final int SIZE_PIECE = 40;//may be used by other classes
	private Type type;
	
	public enum Type {
		GreenPiece,
		YellowPiece,
		GrayGrid,
		WhiteGrid,
		LeftFace,		
		RightFace,
		OutBoard,
		LeftCreation,
		RightCreation
	};
	
	public Piece(Type type) {
		this.type =type;		
	}
	
	public void drawPiece(Graphics g,int x,int y){
	    
		if(this.type == Type.RightCreation){
			g.setColor(Color.ORANGE);
	    	g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
		}else if(this.type == Type.LeftCreation){
	    	g.setColor(Color.GREEN);
	    	g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
	    }else if(this.type == Type.OutBoard){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
		}else if(this.type == Type.GrayGrid){
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
		}else if (this.type == Type.LeftFace){
			g.setColor(Color.gray);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			g.setColor(Color.white);
			g.fillOval(x-1, y-1, SIZE_PIECE-1, SIZE_PIECE-1);
			g.setColor(Color.black);
			g.fillRect(x, y, SIZE_PIECE/3, SIZE_PIECE/3);
			int temp = SIZE_PIECE/5;
			g.fillRect(x+4*temp, y+4*temp, temp, temp);
			
			
		}else if(this.type == Type.RightFace){
			g.setColor(Color.gray);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			g.setColor(Color.white);
			g.fillOval(x-1, y-1, SIZE_PIECE-1, SIZE_PIECE-1);
			g.setColor(Color.black);
			g.fillRect(x, y, SIZE_PIECE/3, SIZE_PIECE/3);
			int temp = SIZE_PIECE/5;
			g.fillRect(x+4*temp, y+4*temp, temp, temp);
			
		}else if (this.type== Type.WhiteGrid){
			g.setColor(Color.WHITE);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			
			
		}else if(this.type == Type.GreenPiece){
			g.setColor(Color.BLACK);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			g.setColor(Color.GREEN);
			g.fillOval(x, y,SIZE_PIECE, SIZE_PIECE);
		}else if (this.type == Type.YellowPiece){
			g.setColor(Color.BLACK);
			g.fillRect(x, y, SIZE_PIECE, SIZE_PIECE);
			g.setColor(Color.yellow);
			g.fillOval(x, y,SIZE_PIECE, SIZE_PIECE);
		}
		
	}
	public void drawRanSwordShield(Graphics g){
		if(this.type == Type.GreenPiece){
			
		}else if(this.type == Type.YellowPiece){
			
		}
	}
	
}
	
	

