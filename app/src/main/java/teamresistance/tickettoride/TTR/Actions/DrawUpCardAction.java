package teamresistance.tickettoride.TTR.Actions;

import java.io.Serializable;

import teamresistance.tickettoride.Game.GamePlayer;
import teamresistance.tickettoride.Game.actionMsg.GameAction;

/**
 *  Draws a card from a face up deck
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class DrawUpCardAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 388970564192016L;
    private int pos;

    /**
     * Draws a card from a face up deck
     * @param player
     * @param pos
     */
    public DrawUpCardAction(GamePlayer player, int pos) {
        super(player);
        this.pos = pos;
    }

    public int getPos(){
        return pos;
    }
}
