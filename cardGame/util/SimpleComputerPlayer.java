package swen221.cardgame.cards.util;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import swen221.cardgame.cards.core.Card;
import swen221.cardgame.cards.core.Card.Suit;
import swen221.cardgame.cards.core.Hand;
import swen221.cardgame.cards.core.Player;
import swen221.cardgame.cards.core.Trick;
import swen221.cardgame.cards.core.TrickyCard;

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
		Card highestCard =getHighest(trick.getCardsPlayed(), lead, trumpSuit);
		SortedSet<Card> cardsMatchingLead = hand.matches(lead);
		SortedSet<Card> cardsMatchingTrump = hand.matches(trumpSuit);
		Card card_play;
		if (!cardsMatchingLead.isEmpty()) {
			// get all cards that match lead and are higher than the highest
            // card ever played
			SortedSet<Card> avaliableCardSet = getHigherCardSet(cardsMatchingLead, highestCard, lead,
                    trumpSuit);
			if (avaliableCardSet.isEmpty()) {
				card_play = getLowest(cardsMatchingLead,lead,trumpSuit);
			}else {
				card_play = avaliableCardSet.last();
				card_play = leastSelect(card_play, avaliableCardSet, lead);
			}
		}else if (!cardsMatchingTrump.isEmpty()){
			// get all cards that match trump and are higher than the highest
            // card ever played
			SortedSet<Card> avaliableCardSet = getHigherCardSet(cardsMatchingTrump, highestCard, lead,
                    trumpSuit);
			if (avaliableCardSet.isEmpty()) {
				card_play = getLowest(hand.getCards(), lead, trumpSuit);
			}else {
				card_play = avaliableCardSet.last();
				card_play = leastSelect(card_play, avaliableCardSet, trumpSuit);
				
			}
		}else {
			card_play = getLowest(hand.getCards(), lead, trumpSuit);
		}
		
		return card_play;
	}
	private Card getLowest(Collection<Card> cardsMatchingLead, Suit lead, Suit trumpSuit) {
		if (cardsMatchingLead.isEmpty()) {
			return null;
		}
		SortedSet<TrickyCard> trickyCardSet =new TreeSet<>();
		for (Card card : cardsMatchingLead) {
			trickyCardSet.add(new TrickyCard(card, lead, trumpSuit));
		}
		Card lowest = trickyCardSet.first().getCard();
		return lowest;
	}
	private SortedSet<Card> getHigherCardSet(SortedSet<Card> cardsMatchingLead, Card highestCard, Suit lead,
			Suit trumpSuit) {
		if (cardsMatchingLead.isEmpty()) {
			return null;
		}
		TrickyCard wrappedTrickCard = new TrickyCard(highestCard, lead, trumpSuit);
		SortedSet<TrickyCard> trickyCardSet = new TreeSet<>();
		//wrap and compare
		for (Card card : cardsMatchingLead) {
			TrickyCard trickyCard = new TrickyCard(card, lead, trumpSuit);
			if (trickyCard.compareTo(wrappedTrickCard) > 0) {
				trickyCardSet.add(trickyCard);
			}
		}
		//unwrap and return
		SortedSet<Card> higherCardSet = new TreeSet<>();
		for(TrickyCard trickyCard : trickyCardSet){
			higherCardSet.add(trickyCard.getCard());
		}
		
		return higherCardSet;
	}
	
	private Card leastSelect(Card card_play, SortedSet<Card> cardsMatchingSuit, Suit suit) {
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
     * @see swen221.cardgame.cards.core
     *      #compareTo(TrickyCard)
     */
	private Card leadAI(Hand hand, Suit trumpSuit) {
		SortedSet<Card> cards = hand.getCards();
		Card card_play = getHighest(cards, null,trumpSuit);
		SortedSet<Card> cardsMatchingSuit = hand.matches(card_play.suit());
		card_play = leastSelect(card_play,cardsMatchingSuit,card_play.suit());
		return card_play;
	}
	
	private Card getHighest(Collection<Card> cards, Suit lead, Suit trumpSuit) {
		//check if is empty
		if (cards.isEmpty()) {
			return null;
		}
		SortedSet<TrickyCard> trickyCards = new TreeSet<>();
		for (Card card : cards) {
			trickyCards.add(new TrickyCard(card, lead, trumpSuit));
		}
		Card lowest =trickyCards.first().getCard();
		return lowest;
		
	}	
}
