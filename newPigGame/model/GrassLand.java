package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;
import java.util.function.Predicate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import gui.View;
import model.GrassLand.Item;
import model.GrassLand.Monster;
import model.GrassLand.Pig;

public class GrassLand extends Observable {
  public static Random r = new Random();
  private int d=11;
  boolean overLop;
  public final float maxSize = 100;
  private Wheat w = new Wheat(50, 50);
  private Monster monster = new Monster(90, 30);
  private List<Pig> pigs = new ArrayList<>();
  { pigs.add(new Pig(0, r.nextInt((int) maxSize))); }

  public List<Pig> getPigs() { return new ArrayList<>(pigs); }

  public Wheat getWheat() { return w; }

  public void moveWheat(int x, int y) {
    if (x > 1 || x < -1 || y > 1 || y < -1)
      throw new Error("Invalid Command");
    w.x =inMargin(w.x+x * w.speed);
    w.y =inMargin(w.y+y * w.speed);
    ping();
  }
  
  private float inMargin(float val) {
    return Math.max(0, Math.min(val,maxSize));
  }
  public Monster getMonster(){
	  return monster;
  }
  public void ping() {
    
	  
	  
	if (r.nextInt(10) == 0) { 
		int curY =r.nextInt((int) maxSize);
		
		if(Math.abs(pigs.get(pigs.size()-1).getY()-curY)<=d)
			return;
		Pig currentPig = new Pig(0, curY);
		boolean overLop1= false;
		
			Rectangle pRect = new Rectangle((int)currentPig .getX(), (int)currentPig .getY(), d, d);
	    	
			for(Pig p1:pigs){
				if(currentPig !=p1){
	    			Rectangle p1Rect= new Rectangle((int)p1.getX(), (int)p1.getY(), d,d);
	    			if(p1Rect.intersects(pRect)){
	    				overLop1= true;
	    				break;
	    			}
	    		}
				
			}
			
				
		
		
		if(!overLop1){
			
			pigs.add(currentPig);
    	}
	}
	Rectangle wheatRect= new Rectangle((int)w.getX(), (int)w.getY(), d*2, d*2);
	Rectangle monsterRect= new Rectangle((int)monster.getX(), (int)monster.getY(), d*2, d*2);
    for (Pig p : pigs) { 
    	Rectangle pRect = new Rectangle((int)p.getX(), (int)p.getY(), d, d);
    	
    	overLop= false;
    	if(pRect.intersects(wheatRect)){
    		int r =  JOptionPane.showConfirmDialog(new JLabel("Game Over"),
					("Game Over"));	
			  System.exit(0);
    	}
    	if(monsterRect.intersects(pRect)){
    		p.moveAway();
    	}
    	
    		if(pRect.intersects(wheatRect)){
        		int r =  JOptionPane.showConfirmDialog(new JLabel("Game Over"),
    					("Game Over"));	
    			  System.exit(0);
        	}
        	for(Pig p1:pigs){
        		if(p!=p1){
        			Rectangle p1Rect= new Rectangle((int)p1.getX(), (int)p1.getY(), d,d);
        			if(p1Rect.intersects(pRect)){
        				overLop= true;
        				break;
        			}
        		}
        	}
        	
        		if(!overLop){
        			p.ping();
        		}
        		monster.ping(p);
    	
    	
    }
    setChanged();
    notifyObservers();
  }

  class Item {//inner class: know about GrassLand
    public Item(float x, float y) { this.x = x; this.y = y; }
    float x; public float getX() { return x; }//no setter
    float y; public float getY() { return y; }
  }

  public class Pig extends Item {
    public Pig(float x, float y) { 
    	super(x, y);
    	
    }
    public final float speed = 0.5f;
    public void ping() {
     
    	 if (x > w.x) { x -= 3*speed; }
         if (x < w.x) { x += 3*speed; }
         if (y > w.y) { y -= 3*speed; }
         if (y < w.y) { y += 3*speed; }
     
    	
      
    }
    public void moveAway(){
    	x = 1000;
    	y= 1000;
    }
   
    public void drawBoundingBox(Graphics2D g){
    	  float leftMost = this.getX();
 		  float rightMost = this.getX();
 		  float topMost = this.getY();
 		  float botMost = this.getY();
 		  int leftInt = (int) leftMost;
 		  int rightInt = (int)rightMost;
 		  int topInt = (int)topMost;
 		  int botInt = (int)botMost;
 		  g.setColor(Color.red);
 		  g.drawRect(leftInt, topInt, 60, 60);
 		  
    }
    public boolean notOverLap(Pig o){
    	  if(o instanceof Pig){
    		  Pig other = (Pig) o;
    		  Rectangle pigRec = new Rectangle((int)(this.getX()), (int)(this.getY()), 60, 60);
    		  Rectangle pigRecOt =new Rectangle((int)(other.getX()), (int)(other.getY()), 60, 60);
    		  if(!pigRec.intersects(pigRecOt)){
    			  return true;
    		  }
    		  
    	  }
    	  return false;
      }
  }
  public class Monster extends Item {
		public Monster(float x, float y) { super(x, y); }
		public final float speed = 0.2f;

		public void ping(Pig p) {
			 if (x > p.getX()) { x -= 2.5*speed; }
	         if (x < p.getX()) { x += 2.5*speed; }
	         if (y > p.getY()) { y -= 2.5*speed; }
	         if (y < p.getY()) { y += 2.5*speed; }
		}
	}

  public class Wheat extends Item {
    public Wheat(float x, float y) { super(x, y); }
    public final float speed = 2;
  }
}