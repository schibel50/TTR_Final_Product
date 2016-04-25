package teamresistance.tickettoride.TTR.Actions;

import java.io.Serializable;

import teamresistance.tickettoride.Game.GamePlayer;
import teamresistance.tickettoride.Game.actionMsg.GameAction;
import teamresistance.tickettoride.TTR.Deck;

/**
 * Confirms the GameAction of the player who made the selection
 *
 *@author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class ConfirmSelectionAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 388780064192016L;
    private Deck sendDeck = null;
    private Deck removeDeck = null;
    private String trainColor = null;
    private int useRainbow = 0;

    // Default constructor
    public ConfirmSelectionAction(GamePlayer player){
        super(player);
    }

    //Constructor for selected destination cards
    public ConfirmSelectionAction(GamePlayer player, Deck sendDeck, Deck removeDeck) {
        super(player);
        this.sendDeck = sendDeck;
        this.removeDeck = removeDeck; //used to remove all destination cards when
                                      //selecting destination cards
    }

    //Constructor for selected rainbow cards
    public ConfirmSelectionAction(GamePlayer player, int useRainbow){
        super(player);
        this.useRainbow = useRainbow;
    }

    //Constructor for choosing cards and how many (if any) rainbow cards
    public ConfirmSelectionAction(GamePlayer player, String trainColor, int useRainbow){
        super(player);
        this.trainColor = trainColor;
        this.useRainbow = useRainbow;
    }

    public Deck getSendDeck(){return this.sendDeck;}

    public Deck getRemoveDeck(){return this.removeDeck;}

    public String getTrainColor(){return this.trainColor;}

    public int getUseRainbow(){return this.useRainbow;}
}
