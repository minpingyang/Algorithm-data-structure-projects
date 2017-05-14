package swen221.cardgame.cards.core;

import java.io.Serializable;

/**
 * Represent a card in a card game.
 *
 * @author David J. Pearce
 */
public class Card implements Comparable<Card>, Serializable {

	private static final long serialVersionUID = 1L;
	

	/**
     * Represents a card suit.
     *
     * @author David J. Pearce
     *
     */
    public enum Suit {
    		HEARTS,
		CLUBS,
		DIAMONDS,
		SPADES;
    }

    /**
     * Represents the different card "numbers".
     *
     * @author David J. Pearce
     *
     */
    public enum Rank {
    		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		JACK,
		QUEEN,
		KING,
		ACE;  
    	}

    // =======================================================
    // Card stuff
    // =======================================================

    private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
    private Rank rank; // 2 <= number <= 14 (ACE)

    /**
     * Construct a card in the given suit, with a given number
     *
     * @param suit
     *            --- between 0 (HEARTS) and 3 (SPADES)
     * @param number
     *            --- between 2 and 14 (ACE)
     */
    public Card(Suit suit, Rank number) {
        this.suit = suit;
        this.rank = number;
    }

    /**
     * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
     *
     * @return --- the suit of this card;
     */
    public Suit suit() {
        return suit;
    }

    /**
     * Get the number of this card, between 2 and 14 (ACE).
     *
     * @return --- the rank of this card
     */
    public Rank rank() {
        return rank;
    }

    private static String[] suits = { "Hearts", "Clubs", "Diamonds", "Spades" };
    private static String[] ranks = { "2 of ", "3 of ", "4 of ", "5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ",
            "Jack of ", "Queen of ", "King of ", "Ace of " };

   
    /**
	 * Override the hash code to suit the project
	 **/
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }
    /*
	 * Override equals method to suit the project
	 * **/
    @Override
    public boolean equals(Object obj) {
    		Card otherCard = (Card) obj;
        if (this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass() || rank != otherCard.rank
    			|| suit != otherCard.suit){
    			return false;
    		}
      //Except last if-condition, then return true;	
        return true;
    }
    
    /**
	 * The method is used to compare two cards
	 * compare both card by their rank and suit ordinal
	 * @param otherCard ------ the card is compared to
	 * @return   0------- two cards are equal
	 * 			 1------- Value of this card is greater than the value of other card
	 * 			-1--------Value of this card is smaller than the value of other card
	 * ***/
    @Override
    public int compareTo(Card otherCard) {
    	// value of both cards
    			int valueThisCard = this.rank.ordinal() + 100*this.suit.ordinal();
    			int valueOtherCard = otherCard.rank.ordinal() + 100*otherCard.suit.ordinal();
    			int compareNumber = valueThisCard - valueOtherCard;
    			if(compareNumber > 0){
    				return 1;
    			}else if (compareNumber < 0) {
    				return -1;
    			}else{
    				return 0;
    			}
    }
    
    @Override
    public String toString() {
        return ranks[rank.ordinal()] + suits[suit.ordinal()];
    }
}