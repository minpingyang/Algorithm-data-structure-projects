package swen221.cardgame.cards.core;
/**
 * This class represents a card in a trick-taking card game. 
 * Its purpose is only to compare between each other by the rule of game
 * It also has two flags determinating whether its suit is lead suit or trump suit.
 *  
 * @author Fred
 * ***/

public class TrickyCard implements Comparable<TrickyCard>{
	
	// card is used to be compared
	private Card card;
	//flag is used to check if its suit is either lead or trump suit
	private boolean isLead =false;
	private boolean isTrump =false;
	/**
	 * Constructor of ComparableCard class
	 *
	 *@param card  -------card is used to be compared
	 *@param lead  -------- enum value of suit of lead
	 *			  Note:  the lead suit is unknown, when the player is
     *            opening the trick, null is passed.
     *            In this case, the lead suit doesn't matter.
     *            
	 *@param trump ---------enum value of suit of trump
	 *
	 *
	 * */
	public TrickyCard(Card card,Card.Suit lead, Card.Suit trump) {
		this.card = card;
		//assign either true or false to two flags
		//two flags defalut value are both false;
		if (this.card.suit() == lead) {
			this.isLead = true;
		}
		if(this.card.suit() == trump){
			this.isTrump = true;
		}
		
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
	public Card getCard(){
		return card;
	}
	
}
