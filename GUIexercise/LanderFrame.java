import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.KeyEventPostProcessor;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class LanderFrame extends JFrame implements KeyListener{
	private LanderCanvas canvas;

	public LanderFrame() {
		super("Moon Lander");
		addKeyListener(this);
		canvas = new LanderCanvas(); // create canvas
		setLayout(new BorderLayout()); // use border layout
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); //pack components tightly together
		setResizable(false); //prevent us from being resizeable
		setVisible(true);  // make sure we are visible
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code==KeyEvent.VK_RIGHT||code == KeyEvent.VK_KP_RIGHT){
			canvas.IncrementX();;
		}else if(code==KeyEvent.VK_LEFT||code==KeyEvent.VK_KP_LEFT){
			canvas.decrementX();
		}else if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
			this.canvas.IncrementY();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	 public static void main(String[] args) {
			new LanderFrame();
		 }
}
