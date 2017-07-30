package swen222.swordShield;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements WindowListener{
	
	/**
	 * The following fields cache various icons so we don't need to load them
	 * everytime.
	 */
	//basic background
	private static ImageIcon graySq = makeImageIco("gray.png");
	private static ImageIcon whiteSq = makeImageIco("white.png");
	private static ImageIcon blackSq = makeImageIco("black.png");
	private static ImageIcon greenCreation= makeImageIco("green.png");
	private static ImageIcon yellowCreation = makeImageIco("yellow.png");	
	private static ImageIcon leftFace = makeImageIco("leftFace.png");
	private static ImageIcon rightFace =makeImageIco("rightFace.png");
	//all green pieces
	private static ImageIcon greenB_LR = makeImageIco("greenB_LR.png");	
	private static ImageIcon greenB_LRT = makeImageIco("greenB_LRT.png");
	private static ImageIcon greenB_Non = makeImageIco("greenB_Non.png");
	private static ImageIcon greenB_R = makeImageIco("greenB_R.png");
	private static ImageIcon greenBR_L = makeImageIco("greenBR_L.png");
	private static ImageIcon greenBR_LT = makeImageIco("greenBR_LT.png");
	private static ImageIcon greenBT_LR = makeImageIco("greenBT_LR.png");
	private static ImageIcon greenBTLR_Non = makeImageIco("greenBTLR_Non.png");
	private static ImageIcon greenL_T = makeImageIco("greenL_T.png");
	private static ImageIcon greenLBR_Non = makeImageIco("greenLBR_Non.png");
	private static ImageIcon greenLR_T = makeImageIco("greenLR_T.png");
	private static ImageIcon greenLRB_T = makeImageIco("greenLRB_T.png");
	private static ImageIcon greenNon_LB = makeImageIco("greenNon_LB.png");
	private static ImageIcon greenNon_LR = makeImageIco("greenNon_LR.png");
	private static ImageIcon greenNon_LTB = makeImageIco("greenNon_LTB.png");
	private static ImageIcon greenNon_T = makeImageIco("greenNon_T.png");
	private static ImageIcon greenNon_TLRB = makeImageIco("greenB_TLRB.png");
	private static ImageIcon greenNothing = makeImageIco("greenNothing.png");
	private static ImageIcon greenR_L = makeImageIco("greenR_L.png");
	private static ImageIcon greenR_LB = makeImageIco("greenR_LB.png");
	private static ImageIcon greenR_LT = makeImageIco("greenR_LT.png");
	private static ImageIcon greenTB_Non = makeImageIco("greenTB_Non.png");
	private static ImageIcon greenTR_L = makeImageIco("greenTR_L.png");
	private static ImageIcon greenTR_Non = makeImageIco("greenTR_Non.png");
	//all yellow pieces
	private static ImageIcon yellowB_T = makeImageIco("yellowB_T.png");
	private static ImageIcon yellowL_LB = makeImageIco("yellowL_LB.png");
	private static ImageIcon yellowL_Non = makeImageIco("yellowL_Non.png");
	private static ImageIcon yellowL_T = makeImageIco("yellowL_T.png");
	private static ImageIcon yellowL_TB = makeImageIco("yellowL_TB.png");
	private static ImageIcon yellowL_TBR = makeImageIco("yellowL_TBR.png");
	private static ImageIcon yellowL_TR = makeImageIco("yellowL_TR.png");
	private static ImageIcon yellowLR_B = makeImageIco("yellowLR_B.png");
	private static ImageIcon yellowLR_Non = makeImageIco("yellowLR_Non.png");
	private static ImageIcon yellowLT_Non = makeImageIco("yellowLT_Non.png");
	private static ImageIcon yellowNon_B = makeImageIco("yellowNon_B.png");
	private static ImageIcon yellowNon_LR= makeImageIco("yellowNon_LR.png");
	private static ImageIcon yellowNon_LT = makeImageIco("yellowNon_LTpng");
	private static ImageIcon yellowNon_TLBR = makeImageIco("yellowNon_TLBR.png");
	private static ImageIcon yellowNothing = makeImageIco("yellowNothing.png");
	private static ImageIcon yellowT_L = makeImageIco("yellowT_L.png");
	private static ImageIcon yellowTB_LR = makeImageIco("yellowTB_LR.png");
	private static ImageIcon yellowTL_B = makeImageIco("yellowTL_B.png");
	private static ImageIcon yellowTL_R = makeImageIco("yellowTL_R.png");
	private static ImageIcon yellowTL_RB = makeImageIco("yellowTL_RB.png");
	private static ImageIcon yellowTLB_Non = makeImageIco("yellowTLB_Non .png");
	private static ImageIcon yellowTLB_R = makeImageIco("yellowTLB_R.png");
	private static ImageIcon yellowTLRB_B = makeImageIco("yellowTLRB_B.png");
	
	private SwordShieldGame game;
	private JPanel outMostPanel;
	private JPanel boardPanel;
	private JPanel greenPlayerPanel;
	private JPanel yellowPlayerPanel;
	private JPanel displayAction;
	private JLabel [][] board;
	
	public GUI(){
		super("The Game of SwordShield!");
		game = new SwordShieldGame();
		
	}
	
	/**
	 * Helper method for loading image icons.
	 * @param filename
	 * @return
	 */
	private static ImageIcon makeImageIco(String filename) {		
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = SwordShieldGame.class.getResource(filename);

		ImageIcon icon = null;
		if (imageURL != null) {
			icon = new ImageIcon(imageURL);
		}
		return icon;
	}
	

	
	

	
	
	@Override
	public void windowClosing(WindowEvent e) {
		// Ask the user to confirm they wanted to do this
				int r = JOptionPane.showConfirmDialog(this, new JLabel(
				"Exit Game?"), "Confirm Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (r == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
		
	}

	

	@Override
	public void windowOpened(WindowEvent e) {	
	}
	@Override
	public void windowClosed(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {	
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowDeactivated(WindowEvent e) {	
	}
	
	
	public static void main(String[] args) {
		new GUI();

	}


}
