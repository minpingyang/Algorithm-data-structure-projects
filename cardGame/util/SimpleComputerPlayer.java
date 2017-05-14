package swen221.cardgame.cards.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
	 * The method is implemented by Minping
	 * */
    @Override
    public Card getNextCard(Trick trick) {

        Hand hand = this.player.getHand();
        Player.Direction leadDirection = trick.getLeadPlayer();
        Card.Suit trumpSuit = trick.getTrumps();

        Card card_play;
        // AI is starting this trick
        if (leadDirection == this.player.getDirection()) {
            card_play = beiginningTactic(hand, trumpSuit);
        } 
        // AI is at the last position to finish this trick.
        // it is known if it can win or lose.
        else if (this.player.getDirection() == leadDirection.previous()) {
            Card.Suit leadSuit = trick.getCardPlayed(leadDirection).suit();
            card_play = terminateTactic(hand, leadSuit, trumpSuit, trick);
        } 
        // AI is neither leading nor terminating this trick.
        else {
            Card.Suit leadSuit = trick.getCardPlayed(leadDirection).suit();
            card_play = generalTactic(hand, leadSuit, trumpSuit, trick);
        }

        return card_play;
    }

    /**
     * This method represents a tactic used by the PC player, while opening a
     * trick. In this tactic, it doesn't have a lead to follow, it generally
     * plays the highest <I>TrickyCard</I> in its hand.
     * 
     * if the AI player might hold the highest card or highest a few cards, 
     * then he will deliberately re-select the least needed card to play.
     * @param hand --- the hand in where the computer player has cards
     * @param trumpSuit  --- the trump suit of this trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.cardgame.cards.core.TrickyCard
     *      #compareTo(TrickyCard)
     */
    private Card beiginningTactic(Hand hand, Suit trumpSuit) {
        SortedSet<Card> playableCardSet = hand.getCardSet();
        Card card_Play = getHighest(playableCardSet, null, trumpSuit);
        SortedSet<Card> cardsWithSelectedSuit = hand.matches(card_Play.suit());
        card_Play = deliberatelySelect(card_Play, cardsWithSelectedSuit, card_Play.suit());

        return card_Play;
    }

    /**
     * This method represents a tactic used by the computer player, when it is closing a
     * trick.  
     * In this tactic, Surely, PC does know whether it can win or lose this trick. 
     * if it deliberately plays the least needed card to win, then PC can win.
     * if it selects the lowest sorted <I>TrickyCard</I> to play, then it must lose,
     * 
     * @param hand
     *            --- the hand of cards that the computer player has
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @param trick
     *            --- the current trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.cardgame.cards.core.TrickyCard
     *      #compareTo(TrickyCard)
     */
    private Card terminateTactic(Hand hand, Suit leadSuit, Suit trumpSuit, Trick trick) {
    	  	Card card_Play;
    		// the highest card already played in this trick
        Card highestCard = getHighest(trick.getCardsPlayed(), leadSuit, trumpSuit);
        // a set of cards with same lead suit
        SortedSet<Card> cardsWithLead = hand.matches(leadSuit);
        // a set of cards with same trump suit
        SortedSet<Card> cardsWithTrump = hand.matches(trumpSuit);
      
        if (!cardsWithLead.isEmpty()) {
            // get all cards that follow lead and are higher than the highest
            // played card
            SortedSet<Card> playableCardSet = getHigherCardSet(cardsWithLead, highestCard, leadSuit,
                    trumpSuit);
            if (playableCardSet.isEmpty()) {
                // if none, then cannot win, play the lowest card which can follow the lead suit
                card_Play = getLowest(cardsWithLead, leadSuit, trumpSuit);
            } else {
                // if there is card that can win, then play the lowest card
                card_Play = playableCardSet.first();
            }
        } else if (!cardsWithTrump.isEmpty()) {
            // get all cards that match trump and are higher than the highest played card 
        	
            SortedSet<Card> playableCards = getHigherCardSet(cardsWithTrump, highestCard, leadSuit,
                    trumpSuit);
            if (playableCards.isEmpty()) {
                // if none, then cannot win, play the lowest in hand
                card_Play = getLowest(hand.getCardSet(), leadSuit, trumpSuit);
            } else {
                // if there is card that can win, play the lowest
                card_Play = playableCards.first();
            }
        } else {
            // no card can follow the lead without trump card, definitely lose.
            // then, play the lowest card 
            card_Play = getLowest(hand.getCardSet(), leadSuit, trumpSuit);
        }

        return card_Play;
    }

    /**
     * This method represents a tactic used by the computer player, when it is neither
     * opening nor closing a trick. 
     * In this case, the tactic can be from offensive to defensive. 
     * Here we assume that AI is doing offensively Similarly like
     * in the terminate tactic method, 
     * in the case that it must lose, it select the lowest sorted <I>TrickyCard</I> to play. But in the case it might still have
     * chance to win, it plays the highest <I>TrickyCard</I>.
     * 
     * @param hand
     *            --- the hand of cards that the computer player has
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @param trick
     *            --- the current trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.cardgame.cards.core.TrickyCard
     *      #compareTo(TrickyCard)
     */
    private Card generalTactic(Hand hand, Suit leadSuit, Suit trumpSuit, Trick trick) {
    		Card card_Play;
    		// the highest card already played in this trick
        Card highestCardPlayed = getHighest(trick.getCardsPlayed(), leadSuit, trumpSuit);
        // a set of cards with same lead suit
        SortedSet<Card> cardsWithLead = hand.matches(leadSuit);
        // a set of cards with same trump suit
        SortedSet<Card> cardsWithTrump = hand.matches(trumpSuit);
        
        if (!cardsWithLead.isEmpty()) {
            // get all cards that follow lead and are higher than the highest played card 
            SortedSet<Card> playableCardSet = getHigherCardSet(cardsWithLead, highestCardPlayed, leadSuit,
                    trumpSuit);
            if (playableCardSet.isEmpty()) {
                // if none, then cannot win, then play the lowest card that can follow the lead suit
                card_Play = getLowest(cardsWithLead, leadSuit, trumpSuit);
            } else {
                // if there is a card that can win,then play the highest
                card_Play = playableCardSet.last();
                // deliberately re-select the card
                card_Play = deliberatelySelect(card_Play, playableCardSet, leadSuit);
            }
        } else if (!cardsWithTrump.isEmpty()) {
            // get all cards that match trump and are higher than the highest played card
            SortedSet<Card> playableCardSet = getHigherCardSet(cardsWithTrump, highestCardPlayed, leadSuit,
                    trumpSuit);
            if (playableCardSet.isEmpty()) {
                // if none, then cannot win, play the lowest in hand
                card_Play = getLowest(hand.getCardSet(), leadSuit, trumpSuit);
            } else {
                // if there is card(s) that can win, play the highest
                card_Play = playableCardSet.last();
                // conservatively re-select the card
                card_Play = deliberatelySelect(card_Play, playableCardSet, trumpSuit);
            }
        } else {
            // no card to follow the lead, no trump card, definitely lose.
            // Then, play the lowest card
            card_Play = getLowest(hand.getCardSet(), leadSuit, trumpSuit);
        }
        return card_Play;
    }

    /**
     * This method is used to take a collection of Card objects in, 
     * sort them by the <I>TrickyCard</I> sorting criteria,
     * then return the highest card.
     * 
     * @param cards
     *            --- a collection of cards to be choose from
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the highest ranked card according to <I>TrickyCard</I>
     *         sorting criteria
     * 
     * @see swen221.cardgame.cards.core.TrickyCard
     *      #compareTo(TrickyCard)
     */
    private Card getHighest(Collection<Card> cards, Suit leadSuit, Suit trumpSuit) {
        // Should never get in here.
        if (cards.isEmpty()) {
            return null;
        }
        SortedSet<TrickyCard> trickyCardSet = new TreeSet<>();
        for (Card card : cards) {
            trickyCardSet.add(new TrickyCard(card, leadSuit, trumpSuit));
        }
        Card highestCard = trickyCardSet.last().getCard();
        return highestCard;
    }

    /**
     * This method takes a collection of Card objects in, sort them according to
     * the <I>TrickyCard</I> sorting criteria, 
     * then returns the lowest card.
     * 
     * @param cards
     *            --- a collection of cards to be choose from
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the lowest ranked card according to <I>TrickyCard</I>
     *         sorting criteria
     * 
     * @see swen221.cardgame.cards.core.TrickyCard
     *      #compareTo(TrickyCard)
     */
    private Card getLowest(Collection<Card> cards, Suit leadSuit, Suit trumpSuit) {
        // for security
        if (cards.isEmpty()) {
            return null;
        }

        SortedSet<TrickyCard> trickyCardSet = new TreeSet<>();
        for (Card card : cards) {
            trickyCardSet.add(new TrickyCard(card, leadSuit, trumpSuit));
        }
        Card lowestCard = trickyCardSet.first().getCard();
        return lowestCard;
    }

    /**
     * This method takes a collection of Card objects in, sort them according to
     * the <I>TrickyCard</I> sorting criteria, and returns a sorted set of
     * Card objects that are higher than the given card according to the sorting
     * criteria in <I>TrickyCard</I>.
     * 
     * @param cards
     *            --- a collection of cards to be choose from
     * @param toBeCompared
     *            --- the card to be compared
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the lowest ranked card according to <I>TrickyCard/I>
     *         sorting criteria
     * 
     * @see swen221.cardgame.cards.core.TrickyCard
     *      #compareTo(TrickyCard)
     */
    private SortedSet<Card> getHigherCardSet(SortedSet<Card> cards, Card toBeCompared, Suit leadSuit,
            Suit trumpSuit) {
    		// for security
        if (cards.isEmpty()) {
            return null;
        }

        TrickyCard newCard = new TrickyCard(toBeCompared, leadSuit, trumpSuit);
        SortedSet<TrickyCard> TrickyCardSet= new TreeSet<>();

        // wrap and compare
        for (Card card : cards) {
            TrickyCard trickyCard = new TrickyCard(card, leadSuit, trumpSuit);
            if (trickyCard.compareTo(newCard ) > 0) {
                TrickyCardSet.add(trickyCard);
            }
        }

        // unwrap and return
        SortedSet<Card> higherCardSet = new TreeSet<>();
        for (TrickyCard tc : TrickyCardSet) {
            higherCardSet.add(tc.getCard());
        }

        return higherCardSet;
    }

    /**
     * This method is used to treat a special case, when the player holds the highest card
     * or highest several cards in one suit,
     * then he should deliberately select the least needed card to play. <br>
     * <br>
     * For instance, if player holds both Heart A, Heart K and Heart Q, then other player
     * cannot play A or K to win him if he plays Q. In this case, Q is the
     * rational choice.<br>
     * <br>
     * Notice: This helper method requires that the card in the first argument
     * <I>card</I>, and cards in the second argument <I>playableCards</I>, must
     * be of same suit, and this suit must be consistent with the third argument
     * <I>suit</I>. If any of these requirements is not met, an IllegalArgument
     * exception will be thrown.
     * 
     * @param card--- the card which is needed to be checked
     * @param playableCardSetSet
     *            --- a set of cards that the player can re-pick if the
     *            card indeed fit in this special case
     * @param suit  --- the given suit.
     * @return --- the same card if the hand of cards does not fit into this
     *         special case, or another card if it does. The re-picked card is
     *         guaranteed to win the trick as well if the given card can win.
     */
    private Card deliberatelySelect(Card card, SortedSet<Card> playableCardSetSet, Suit suit) {

        //check for security, throw all illegal exception
        if (card.suit() != suit) { 
            throw new IllegalArgumentException("Suit of card should be the given suit.");
        }

        for (Card c : playableCardSetSet) {
            if (c.suit() != card.suit()) {
                throw new IllegalArgumentException("The card of playableCardSets should match given suit.");
            }
        }

        if (!card.equals(playableCardSetSet.last())) { 
            throw new IllegalArgumentException("this card should be the highest card of playableCardSet.");
        }

        List<Card> cardsList = new ArrayList<>(playableCardSetSet);
        Collections.sort(cardsList); 
        
        int lastIndex = cardsList.size() - 1;
        int ordinal = 12;
        Card selectedCard = card;
        
        // a boolean value used for checking if the last index is decreased.
        boolean lastIndexDecreased = false;  
        while (selectedCard.rank().ordinal() == ordinal) {
            ordinal--;
            lastIndex--;
            lastIndexDecreased = true;
            if (lastIndex < 0) {
                break;
            }
            selectedCard = cardsList.get(lastIndex);
        }

        if (lastIndexDecreased) {
            selectedCard = cardsList.get(lastIndex + 1);
        }

        return selectedCard;
    }

}
