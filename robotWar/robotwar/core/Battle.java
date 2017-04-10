package robotwar.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a robot battle and implemnents the logic for taking turns in the
 * battle.
 * 
 * @author David J. Pearce
 * 
 */
public class Battle {



	/**
	 * The list of robots in the arena, including those which are deceased.
	 */
	private List<Robot> robots = new ArrayList<>();
	
	/**
	 * The list of actions applied the current turn. 
	 */
	private List<Action> actions = new ArrayList<Action>();
	
	public int arenaWidth;
	public int arenaHeight;

	/**
	 * Construct a battle with given dimensions and number of robots.
	 *
	 * @param width Width of battle arena
	 * @param height Height of battle arena
	 * //@param numRobots Number of robots to fight
	 */
	public Battle(int width, int height) {
		arenaWidth = width;
		arenaHeight = height;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public void setRobots(List<Robot> robots) {
		this.robots = robots;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}


	
	/**
	 * The purpose of this method is to allow each of the robots to make a move.
	 */
	public void takeTurn() {
		actions.clear();
		for(Robot r : robots) {
			if(!r.isDead) {
				r.takeTurn(this);
			}
		}
		for(Action action : actions) {
			action.apply(this);
		}
	}
	
	public void log(String msg) {
		System.out.println("LOG: " + msg);
	}

}
