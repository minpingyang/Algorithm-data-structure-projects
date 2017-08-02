import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
public class LanderCanvas extends JPanel {
	private int y = 1;
	private int x = 0;
	private double count = 0;
	private int fuel = 100;
	private int[] groundXS = { 0,30,40,100,140,160,180,200,220,230,300,310,330,350,
			360,400,410,435,460,465,500,545,560,575,580,600,600,0
	};
	private int[] groundYS = {500,450,480,510,350,400,395,480,490,480,480,520,515,525,
			515,550,400,350,360,400,410,480,455,465,480,500,600,600
	};
	private int[] landerXS = {11,13,27,29,30,26,37,40,40,30,30,33,24,
			21,24,16,19,16,7,0,0,10,10,3,14,10
	};
	private int[] landerYS = {5,0,0,5,20,20,35,35,40,40,35,35,20,
			20,25,25,20,20,35,35,40,40,35,35,20,20
	};
	public LanderCanvas() {
		Polygon ground = new Polygon(this.groundXS,this.groundYS, this.groundXS.length);
		Rectangle flat = new Rectangle(230,480,70,20);
		this.fuel = 100;
	
		new Timer(100, (e) -> {
			count+=0.1;
			
			y= y+(int)count;
			if(ground.contains(x+40, y+40) && !flat.contains(x+40,y+40)) {
				int r = JOptionPane.showConfirmDialog(this, new JLabel("you lose"),
					"Warning!",JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);	
				  System.exit(0);
			  
			}
			else if(flat.contains(x+40, y+40)) {
				int r = JOptionPane.showConfirmDialog(this, new JLabel("you win"),
					"WIN!",JOptionPane.CLOSED_OPTION,
					JOptionPane.WARNING_MESSAGE);
					 System.exit(0);
				
			}

			this.repaint();
		} ).start();
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);

	}
	public void IncrementX() {
		assert (x <= 600);
		this.x = x + 5;
	}
	public void decrementX() {
		assert(x>=0);
		this.x = x - 5;
	}
	public void IncrementY() {
		
		
		if(this.fuel >= 5 ) {
			
			this.fuel = this.fuel - 5;
			this.y = y - 8;
			}

	}

	@Override
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,getWidth(), getHeight());
		Polygon ground = new Polygon(this.groundXS,this.groundYS, this.groundXS.length);
		g.setColor(Color.GRAY);
		g.fillPolygon(ground);
		g.translate(x,y);
		Polygon lander = new Polygon(this.landerXS, this.landerYS, this.landerXS.length);
		g.setColor(Color.LIGHT_GRAY);
		g.fillPolygon(lander);
		g.drawString("Fuel " + this.fuel, 100, 0);
		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(600,600);
	}

}

