package teamresistance.tickettoride.TTR;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Deck is a class that represents any type of deck, and implements methods appropriate to decks
 * External Citation
 * Date 03/16
 * Source: SlapJack class example
 * Solution: The Deck class in SlapJack from class is very similar to the
 * functionality we wanted for our cards.
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */
public class Deck implements Serializable {
    private static final long serialVersionUID = 778245564192016L;
    /** Says whether or not the card has been selected */
    private boolean highlight;
    /** Name of the deck. IE: destinationDeck or trainCardDeck or playerHandDeck */
    private String deckName;
    /** the arraylist of train cards acting as a deck */
    private ArrayList<Card> cards;

    /*
     * Constructor for a Deck
     * @deckName
     * @cards
     */
    public Deck(String deckName, Card[] newCards) {
        cards = new ArrayList<Card>();
        for (int i = 0; i < newCards.length; i++){
            cards.add(newCards[i]);
        }
        highlight = false;
    }

    /*
     * Creates a new Deck based on a already created one
     * @orig
     */
    public Deck(Deck orig) {
        cards = new ArrayList<Card>();
        for (int i =0; i < orig.getCards().size(); i++){
            cards.add(orig.getCards().get(i));
            cards.get(i).setHighlight(orig.getCards().get(i).getHighlight());
        }
        highlight = orig.getHighlight();
    }

    /**
     * Creates empty deck
     */
    public Deck(String deckName) {
        cards = new ArrayList<Card>();
        deckName = deckName;
        highlight = false;
    }

    /**
     * Shuffles the deck
     * Acquired from SlapJack game
     */
    public void shuffle() {
        synchronized (this.cards) {
            int size = this.cards.size();
            for (int i = size; i > 1; i--) {
                int spot = (int) (i * Math.random());
                Card temp = this.cards.get(spot);
                this.cards.set(spot, this.cards.get(i - 1));
                this.cards.set(i - 1, temp);
            }
        }
    }

    /**
     * Moves the top cards from one Deck to another
     * @param targetDeck
     * @param sourceDeck
     */
    public void moveTopCardTo(Deck targetDeck, Deck sourceDeck) {
        // will hold the card
        Card c = null;
        // size of the first deck
        int size;
        // indivisibly check the deck for empty, and remove the card, to
        // avoid a race condition
        synchronized(this.cards) {
            size = sourceDeck.size();
            if (size > 0) {
                c = sourceDeck.getCards().get(size-1);
                sourceDeck.getCards().remove(cards.size()-1);
            }
        }
        // if the original size was non-zero, add the card to the top of the
        // target deck
        if (size > 0) {
            targetDeck.add(c);
        }

    }

    /**
     * Moves the top cards from one Deck to another
     * @param targetDeck
     * @param sourceDeck
     */
    public void moveCardTo(Deck targetDeck, Deck sourceDeck, int position) {
        synchronized (this.cards) {
            if (!sourceDeck.getCards().isEmpty()) {
                Card temp = sourceDeck.getCards().get(position);
                targetDeck.add(temp);
                sourceDeck.getCards().remove(position);
            }
        }
    }

    /**
     * Moves all cards from one Deck to another
     * @param targetDeck
     * @param sourceDeck
     */
    public void moveAllCardsTo(Deck targetDeck, Deck sourceDeck) {
        int i = 0;
        while(!sourceDeck.getCards().isEmpty()){
            if(i < sourceDeck.getCards().size()) {
                Card c = sourceDeck.getCards().get(i);
                sourceDeck.getCards().remove(i);
                targetDeck.getCards().add(c);
            }
        }
    }

    /**
     * Adds a card
     * @param c-new card
     */
    public void add(Card c) {
        cards.add(c);
    }

    /**
     * Returns the size of cards ArrayList
     * @return
     */
    public int size() {
         return cards.size();
    }

    /**
     * Removes top card
     * @return
     */
    public Card removeTopCard() {
        return null;
    }

    /**
     * Shows top card of a deck
     * @return
     */
    public Card peekAtTopCard() {
        return cards.get(this.size()-1);
    }

    /**
     * Returns cards ArrayList
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setHighlight(boolean value) {
        highlight = value;
    }

    public boolean getHighlight() {
        return highlight;
    }
}
