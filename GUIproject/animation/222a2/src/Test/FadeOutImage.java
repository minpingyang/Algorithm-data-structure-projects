package Test;
import resources.ImageResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class FadeOutImage extends JPanel implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 Image myImage = ImageResource.ME1.img;


  Timer timer = new Timer(100, this);

  private float alpha = 1f;

  public FadeOutImage() {
    timer.start();
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    g2d.drawImage(myImage, 10, 10, null);
  }

  public void actionPerformed(ActionEvent e) {
    alpha += -0.01f;
    if (alpha <= 0) {
      alpha = 0;
      timer.stop();
    }
    repaint();
  }
  public static void main(String[] args) {
    JFrame frame = new JFrame("Fade out");
    frame.add(new FadeOutImage());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 250);
    frame.setVisible(true);
  }

}
