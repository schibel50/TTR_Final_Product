package teamresistance.tickettoride.TTR.Actions;

import java.io.Serializable;

import teamresistance.tickettoride.Game.GamePlayer;
import teamresistance.tickettoride.Game.actionMsg.GameAction;

/**
 *  TrackPlaceAction places a track
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */
public class TrackPlaceAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 3882400014192016L;

    private String trackColor;
    private int index;

    /*
     * Initializing a new action to change the mode
     * @player
     */
    public TrackPlaceAction(GamePlayer player, String trackColor, int index) {
        super(player);
        this.trackColor = trackColor;
        this.index = index;
    }

    public void setTrackColor(String value){
        this.trackColor = value;
    }

    public void setIndex(int value){
        this.index = value;
    }

    public String getTrackColor(){
        return this.trackColor;
    }

    public int getIndex(){
        return this.index;
    }
}
