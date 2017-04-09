package robotwar.core;

import java.util.ArrayList;

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
	public GuardBot(String name,  int xPosition, int yPosition, int strength) {
		super(name, xPosition,yPosition,strength);
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
		// First, look to see if there is anything to fire at.
		ArrayList<Robot> robotsInSight = findRobotsInSight(battle, 10);
		
		if(!robotsInSight.isEmpty()) {
			// shoot a robot then!
			Robot target = robotsInSight.get(0);			
			battle.actions.add(new Shoot(this,target,1));			
		}
		
		// Now, move robot
		int radius = 5;
		
		int dy = getyPosition() - startYPosition;
		int newXPosition = getxPosition();		
		int newYPosition = getyPosition();
		
		// This implements a simple alternating walk pattern.
		if(getxPosition() < startXPosition) {
			if(dy < radius && getyPosition() < battle.arenaHeight) {
				newYPosition = getyPosition() + 1;
			} else {
				newXPosition = getxPosition() + 1;
			}
		} else {
			if(dy > -radius && getyPosition() >= 0) {
				newYPosition = getyPosition() - 1;
			} else {
				newXPosition = getxPosition() - 1;
			}				
		}
		battle.actions.add(new Move(newXPosition,newYPosition,this));
		
	}
}
