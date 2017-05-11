package swen221.cardgame.cards.util;

import java.util.SortedSet;

import swen221.cardgame.cards.core.Card;
import swen221.cardgame.cards.core.Card.Suit;
import swen221.cardgame.cards.core.Hand;
import swen221.cardgame.cards.core.Player;
import swen221.cardgame.cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}
	/**
	 * The method is used to get next card in passed trick 
	 * */
	@Override
	public Card getNextCard(Trick trick) {		
    Hand hand = this.player.getHand();
    Player.Direction leadDirection = trick.getLeadPlayer();
    Card.Suit trumpSuit = trick.getTrumps();
    Card card_play;
    
    if (leadDirection == this.player.getDirection()) {
//    	AI is leading this trick
     card_play = leadAI(hand,trumpSuit);
	}else if (this.player.getDirection() == leadDirection.previous()) {
        // then AI is at the last position to finish this trick.
		Card.Suit lead = trick.getCardPlayed(leadDirection).suit();
	 card_play = finishAI(hand,lead,trumpSuit,trick);
	}else {
		//AI is neither leading nor ending this trick
		Card.Suit lead = trick.getCardPlayed(leadDirection).suit();
		card_play = generalAI(hand, lead, trumpSuit, trick);
	}
    return card_play;
		
	}
	private Card generalAI(Hand hand, Suit lead, Suit trumpSuit, Trick trick) {
		// TODO Auto-generated method stub
		return null;
	}
	private Card finishAI(Hand hand, Suit lead, Suit trumpSuit, Trick trick) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * This is the strategy used by the computer player when it is opening a
     * trick. In this case, it doesn't have a lead to follow, i.e. it generally
     * plays the highest <I>ComparableCard</I> in its hand.
     * 
     * @param hand
     *            --- the hand of cards that the computer player has
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
	private Card leadAI(Hand hand, Suit trumpSuit) {
		SortedSet<Card> cards = hand.getCards();
		Card card_play = getHighest(cards, null,trumpSuit);
		SortedSet<Card> 
		return null;
	}
	private Card getHighest(SortedSet<Card> cards, Object object, Suit trumpSuit) {
		// TODO Auto-generated method stub
		return null;
	}	
}
