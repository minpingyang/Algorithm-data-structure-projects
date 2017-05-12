package swen221.cardgame.cards.core;


/**
 * This class represents a card in a trick-taking card game. Its purpose is only
 * to compare between each other in a strategy making process. A card in a
 * trick-taking game has its rank and suit. It also has two flags to indicate
 * whether its suit is lead suit, and whether its suit is trump suit.
 * 
 * @author Minping
 *
 */
public class TrickyCard implements Comparable<TrickyCard> {

	// card is used to be compared
	private Card card;
	//flag is used to check if its suit is either lead or trump suit
	private boolean isLead;
	private boolean isTrump; //default boolean value is false

    /**
     * Construct a ComparableCard object with a card, the suit of lead, and the
     * suit of trump.
     * 
     * @param card
     *            --- the card that is used to compare
     * @param leadSuit
     *            --- the suit of lead. Note that in the case when the player is
     *            opening the trick, the lead suit is unknown, null is passed
     *            here indicating that the lead suit doesn't matter.
     * @param trumpSuit
     *            --- the suit of trump
     */
    public TrickyCard(Card card, Card.Suit leadSuit, Card.Suit trumpSuit) {
        this.card = card;

      //assign either true or false to two flags
      //two flags defalut value are both false;
      if (this.card.suit() == leadSuit) {
      	this.isLead = true;
      }
      if(this.card.suit() == trumpSuit){
      	this.isTrump = true;
      }
      		
    }

    /**
     * Return the card.
     * 
     * @return --- the card in this object.
     */
    public Card getCard() {
        return card;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((card == null) ? 0 : card.hashCode());
        result = prime * result + (isLead ? 1231 : 1237);
        result = prime * result + (isTrump ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrickyCard other = (TrickyCard) obj;
        if (card == null) {
            if (other.card != null)
                return false;
        } else if (!card.equals(other.card))
            return false;
        if (isLead != other.isLead)
            return false;
        if (isTrump != other.isTrump)
            return false;
        return true;
    }

    /**
     * Override compareTo method
     * Here whether or not the card is a trump comes 
     * as the top priority to compare. 
     * Then the second priority is a lead-suit.
     * 
     * @param otherCard ------ the card is compared to
	 * @return   0------- two cards are equal
	 * 			 1------- Value of this card is greater than the value of other card
	 * 			-1--------Value of this card is smaller than the value of other card
	 * ***/
    @Override
	public int compareTo(TrickyCard otherCard) {
		int compareRank = 0;  // the number of compare their rank
		//check Trump first
		if(this.isTrump && !otherCard.isTrump){
			return 1;
		}else if (!this.isTrump && otherCard.isTrump) {
			return -1;
		  // if both cards have trump
		}else if (this.isTrump && otherCard.isTrump) {
			// then compare their rank
			compareRank = this.card.rank().ordinal() - otherCard.card.rank().ordinal();
			if (compareRank > 0) {
				return 1;
			}else if (compareRank < 0) {
				return -1;
			}else {
				return 0;
			}
		}
		//if neither cards is not trump , then check the lead card
		else {
			//then do similar check as Trump card
			if(this.isLead && !otherCard.isLead ){
				return 1;
			}else if (!this.isLead && otherCard.isLead) {
				return -1;
			}else{
				//two case: one is both of cards has lead card
				//second one is neither of cards has lead card
				compareRank = this.card.rank().ordinal() - otherCard.card.rank().ordinal();
				if (compareRank > 0) {
					return 1;
				}else if (compareRank < 0) {
					return -1;
				}else {
					//same rank, then use compareTo method of card class
					if (this.card.compareTo(otherCard.card) > 0) {
						return 1;
					} else if (this.card.compareTo(otherCard.card) < 0) {
                        return -1;
                    } else {
                        return 0; 
                    }
				}
			
			}
		}
		
	}

}

