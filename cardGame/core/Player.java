package swen221.cardgame.cards.core;

import java.io.Serializable;

/**
 * Represents a player on the board, which can be either a computer player or a
 * human. Every player has a direction (North, East, South, West) and a hand of
 * cards which they are currently playing.
 * 
 * @author David J. Pearce
 * 
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
     * Construct a player in the given direction
     * 
     * @param direction --- the direction where the player is seated
     */
    public Player(Direction direction) {
        this.direction = direction;
        this.hand = new Hand();
    }
    /**
     * Represents one of the four position on the table (North, East, South and
     * West).
     * 
     * @author David J. Pearce
     * 
     */
    public enum Direction {
        NORTH, 
        EAST, 
        SOUTH, 
        WEST;

        /**
         * Returns the next direction to play after this one (i.e. following a
         * clockwise rotation).
         * 
         * @return 
         */
        public Direction next() {
        	if (this.equals(NORTH)) return EAST;
			if (this.equals(EAST)) return SOUTH;
			if (this.equals(SOUTH)) return WEST;
			return NORTH;
        }

        /**
         * Additonal method
         * Returns the previous direction to play before this one (i.e.
         * following a anti-clockwise rotation).
         * 
         * @return --- the previous direction
         */
        public Direction previous() {
            if (this.equals(NORTH))
                return WEST;
            if (this.equals(EAST))
                return NORTH;
            if (this.equals(SOUTH))
                return EAST;
            return SOUTH;
        }
    }

    private final Direction direction;
    private Hand hand;

    

    /**
     * Get the position in which this player is sitting.
     * 
     * @return --- the position in which this player is sitting.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the current hand of this player.
     * 
     * @return 
     */
    public Hand getHand() {
        return hand;
    }
}
