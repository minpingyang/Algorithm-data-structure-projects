package robotwar.core;

import robotwar.ui.BattleCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * The GuardBot marks out its starting location and hangs around there
 * protecting it.
 * 
 * @author David J. Pearce
 * 
 */
public class GuardBot extends Robot {
	public int guardArea;
	public int startXPosition;
	public int startYPosition;

	/**
	 * Construct a gaurd bot with a given name, starting position, strength.
	 * 
	 * @param name
	 * @param xPosition
	 * @param yPosition
	 * @param strength
	 */
	public GuardBot(String name, int xPosition, int yPosition, int strength) {
		super(name, xPosition, yPosition, strength);
		startXPosition = xPosition;
		startYPosition = yPosition;
	}

	/**
	 * Move the robot according to a simple strategy which sees the robot
	 * circling (and protecting) its originating position. If another robot is
	 * sighted then it is attacked immediately.
	 */
	@Override
	public void takeTurn(Battle battle) {

		common(battle,1);
		// Now, move robot
		int radius = 5;
		int dy = getyPosition() - startYPosition;
		int newXPos = getxPosition();
		int newYPos = getyPosition();

		// This implements a simple alternating walk pattern.
		if (getxPosition() < startXPosition) {
			if (dy < radius && getyPosition() < battle.arenaHeight) {
				newYPos = getyPosition() + 1;
			} else {
				newXPos = getxPosition() + 1;
			}
		} else {
			if (dy > -radius && getyPosition() >= 0) {
				newYPos = getyPosition() - 1;
			} else {
				newXPos = getxPosition() - 1;
			}
		}
//		battle.actions.add(new Move(newXPos, newYPos, this));

		if(newXPos< 0 || newXPos>battle.arenaWidth || newYPos<0 || newYPos > battle.arenaHeight-1){
			return;
		}else if(!isDead){
			battle.getActions().add(new Move(newXPos,newYPos,this));
		}

	}

	@Override
	public Image getRobotImage() {

		java.net.URL imageURL = BattleCanvas.class.getResource("images/Robot4.png");
		try {
			Image img = ImageIO.read(imageURL);

			return img;

		} catch (IOException e) {
			throw new RuntimeException("Unable to load Guard robot image " );
		}

	}


}
