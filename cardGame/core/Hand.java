package swen221.cardgame.cards.core;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a hand of cards held by a player. As the current round proceeds,
 * the number of cards in the hand will decrease. When the round is over, new
 * cards will be delt and added to this hand.
 * 
 * @author David J. Pearce
 * 
 */
public class Hand implements Serializable, Iterable<Card> {
	private static final long serialVersionUID = 1L;
    private SortedSet<Card> cards = new TreeSet<Card>();

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    /**
     * Check with a given card is contained in this hand, or not.
     * 
     * @param card
     * @return 
     */
    public boolean contains(Card card) {
        return cards.contains(card);
    }

    /**
     * 	Additional method
     * Return all cards in this hand which has the given suit.
     * @param suit
     * @return ---SortedSet type.
     */
    public SortedSet<Card> matches(Card.Suit suit) {
        TreeSet<Card> r = new TreeSet<Card>();
        if (suit == null) {
            return r;
        }
        for (Card c : cards) {
            if (c.suit() == suit) {
                r.add(c);
            }
        }
        return r;
    }
    /**
     * Additional method added by minping
     * Get all the cards held in this hand
     * 
     * @return --- all the cards held in this hand
     */
    public SortedSet<Card> getCardSet() {
        return this.cards;
    }

    /**
     * Add a card to the hand.
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Remove a card from the hand.
     */
    public void remove(Card card) {
        cards.remove(card);
    }

    /**
     * Get number of cards in this hand.
     * 
     * @return 
     */
    public int size() {
        return cards.size();
    }

   
    /**
     * Remove all cards from this hand.
     */
    public void clear() {
        cards.clear();
    }
  

}
