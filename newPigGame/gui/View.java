package gui;
import java.awt.*; import java.util.*; import javax.swing.*;
import model.GrassLand;
import model.GrassLand.Monster;
import model.GrassLand.Pig;
import resources.ImgResources;

public class View extends JComponent implements Observer {
  private static final long serialVersionUID = 1L;
  GrassLand myModel;
  public static float d;
  public View(GrassLand m) {
    myModel = m;
    myModel.addObserver(this);
    this.addKeyListener(new Controller(m));
    this.setFocusable(true);
    JFrame f = new JFrame("Wheat run, use (w,a,s,d) to play");
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.add(this);
    f.pack();
    f.setVisible(true);
  }
  public void paintComponent(Graphics _g) {
    super.paintComponent(_g);
    Graphics2D g = (Graphics2D) _g;
    int minD = Math.min(getHeight(), getWidth());
    float d = minD / 20f;
    float step = minD / myModel.maxSize;
    g.setColor(Color.GREEN.darker());
    g.fillRect(0, 0, minD, minD);
    drawWheat(g, d, step);
    drawPigs(g, d, step);
    drawMonster(g, d, step);
  }
  private void drawMonster(Graphics2D g, float d, float step){
	 
	
      
      	
      //System.out.println("size: "+myModel.getPigs().size());
     Monster monster = myModel.getMonster();
      float px = monster.getX() * step;
      float py = monster.getY() * step;
     // System.out.println("px: "+px +"py: "+py);
      
      
      //System.out.println("imageMonster: "+ImgResources.MONSTER.img);
      g.drawImage(ImgResources.MONSTER.img, (int) (px - d), (int) (py - d),
          (int) (d * 2), (int) (d * 2), null);
      
	  
  }
  private void drawPigs(Graphics2D g, float d, float step) {
	  int i =0;
	  for (Pig p : myModel.getPigs()) {
	
      
      	
      //System.out.println("size: "+myModel.getPigs().size());
    	
      float px = p.getX() * step;
      float py = p.getY() * step;
      //System.out.println("px: "+px +"py: "+py);
      
      
      g.setColor(Color.red);
      g.drawRect((int)(px-d), (int)(py-d), (int)(d*2), (int)(d*2));
     // System.out.println("imagePig: "+ImgResources.PIG.img);
      g.drawImage(ImgResources.PIG.img, (int) (px - d), (int) (py - d),
          (int) (d * 2), (int) (d * 2), null);
    }
  }
  private void drawWheat(Graphics2D g, float d, float step) {
    float wx = myModel.getWheat().getX() * step;
    float wy = myModel.getWheat().getY() * step;
    g.setColor(Color.red);
   
    g.drawRect((int)(wx-d), (int)(wy-d), (int)(d*2), (int)(d*2));
    g.drawImage(ImgResources.WHEAT.img, (int) (wx - d), (int) (wy - d),
        (int) (d * 2), (int) (d * 2), null);
  }
  public Dimension getPreferredSize() {return new Dimension(600, 600);}
  public void update(Observable arg0, Object arg1) {repaint();}
}