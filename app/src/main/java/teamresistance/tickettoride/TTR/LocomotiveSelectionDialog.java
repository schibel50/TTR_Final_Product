package teamresistance.tickettoride.TTR;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Serializable;

import teamresistance.tickettoride.Game.Game;
import teamresistance.tickettoride.R;
import teamresistance.tickettoride.TTR.Actions.ConfirmSelectionAction;

/**
 *
 *  Class that inflates view into dialog for custom handling of user options of cards when placing track.
 *  Allows user to select train color for grey tracks and allows user to select how many locomotive
 *  cards they wish to use.
 *

  * External Citation
  * Date: 4/11/16
  * Problem: Setting up dialog boxes
  * Resource: http://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
  * Solution: Followed outlined steps
  *
  * External Citation
  * Date: 4/17/16
  * Resource: http://stackoverflow.com/questions/9829453/android-4-0-dialog-gets-canceled-when-touched-outside-of-dialog-window
  * Solution: Modified examples

 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class LocomotiveSelectionDialog extends Dialog implements View.OnClickListener, Serializable {
    private static final long serialVersionUID = 588445564192016L;
    /** Class Instance Variables */
    private Button selectBtn; //button for user to select
    private Button cancelBtn; //button for cancellation
    private RadioButton noLoc, oneLoc, twoLoc, threeLoc, fourLoc, fiveLoc, sixLoc; //radio buttons repreenting how many 'loc' you want to use
    private RadioButton[] locomotives; //array to contain all radio buttons
    private Deck trainCards; //player deck of train cards of user
    private final String[] trainColors = {"Red", "Orange", "Yellow", "Green",
            "Blue", "Pink", "White", "Black"}; //array of the train colors
    private int numRainbows = 0; //how many locomotive cards player has
    private int useRainbows = 0; //how many locomotive cards player wants to user
    private int numCards = 0;
    private int min = 0;
    private Game game; //game to send action
    private TTRHumanPlayer player; //player to send in action

    /**
     *  Constructor for creating the locomotive selection dialog
     * @param a
     * @param cards
     * @param myState
     * @param track
     * @param game
     * @param player
     */
    public LocomotiveSelectionDialog(Activity a, Deck cards, TTRGameState myState, Track track, Game game, TTRHumanPlayer player) {
        super(player.myActivity);
        this.game = game;
        this.player = player;
        this.trainCards = cards;
        this.min = track.getTrainTrackNum();
        for(int i = 0; i < trainCards.size(); i++){
            if(trainCards.getCards().get(i).toString().equals("Rainbow")){
                numRainbows++;
            }
        }
        numCards = contains(track.getTrackColor());
    }

    /**
     * Method called when dialog created, used to initialize all widgets
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.locomotive_selection);
        setCanceledOnTouchOutside(false);

        //initialize buttons and set as listeners
        selectBtn = (Button) findViewById(R.id.btn_select);
        selectBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(this);
        noLoc = (RadioButton)findViewById(R.id.None_Rainbow);
        oneLoc = (RadioButton)findViewById(R.id.One_Rainbow);
        twoLoc = (RadioButton)findViewById(R.id.Two_Rainbow);
        threeLoc = (RadioButton)findViewById(R.id.Three_Rainbow);
        fourLoc = (RadioButton)findViewById(R.id.Four_Rainbow);
        fiveLoc = (RadioButton)findViewById(R.id.Five_Rainbow);
        sixLoc = (RadioButton)findViewById(R.id.Six_Rainbow);
        locomotives = new RadioButton[]{noLoc, oneLoc, twoLoc, threeLoc, fourLoc, fiveLoc, sixLoc};
        noLoc.setOnClickListener(this);
        oneLoc.setOnClickListener(this);
        twoLoc.setOnClickListener(this);
        threeLoc.setOnClickListener(this);
        fourLoc.setOnClickListener(this);
        fiveLoc.setOnClickListener(this);
        sixLoc.setOnClickListener(this);

        //set visibility
        if(numRainbows != 0){
            for(int i = numRainbows; i >= 0; i--){
                if(numCards + i >= min && i < 7) {
                    locomotives[i].setVisibility(View.VISIBLE);
                    locomotives[i].setChecked(true);
                    useRainbows = i;
                }
                else if(i < 7){
                    locomotives[i].setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * Method for handling user clicks inside the dialog box
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_select){
            game.sendAction(new ConfirmSelectionAction(player, useRainbows));
            dismiss();
        } else if (v.getId() == R.id.btn_cancel){
            dismiss();
        } else if(v.getId() == R.id.None_Rainbow){
            noLoc.setChecked(true);
            useRainbows = 0;
        } else if(v.getId() == R.id.One_Rainbow){
            oneLoc.setChecked(true);
            useRainbows = 1;
        } else if(v.getId() == R.id.Two_Rainbow){
            twoLoc.setChecked(true);
            useRainbows = 2;
        } else if(v.getId() == R.id.Three_Rainbow){
            threeLoc.setChecked(true);
            useRainbows = 3;
        } else if(v.getId() == R.id.Four_Rainbow){
            fourLoc.setChecked(true);
            useRainbows = 4;
        } else if(v.getId() == R.id.Five_Rainbow){
            fiveLoc.setChecked(true);
            useRainbows = 5;
        } else if(v.getId() == R.id.Six_Rainbow){
            sixLoc.setChecked(true);
            useRainbows = 6;
        }
    }

    /**
     * Method to check if a train of a certain color exists in the passed in deck
     * @param trainColor
     * @return
     */
    public int contains(String trainColor){
        Deck tempDeck = this.trainCards;
        int count = 0;
        for(int i = 0; i < tempDeck.size();i++){
            if(tempDeck.getCards().get(i).toString().equals(trainColor)){
                count++;
            }
        }
        return count;
    }
}
