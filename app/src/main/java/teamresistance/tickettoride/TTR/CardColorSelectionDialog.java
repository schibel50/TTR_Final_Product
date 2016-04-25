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
 *  Class that inflates view into dialog for custom handling user selection of of card color
 *
 *
 * External Citation
 * Date: 4/11/16
 * Problem: Issue setting up dialog boxes
 * Source: http://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
 * Solution: Followed directions
 *
 * External Citation
 * Date 4/17/16
 * Problem: Clicking outside of dialog box overrode the box
 * Source: http://stackoverflow.com/questions/9829453/android-4-0-dialog-gets-canceled-when-
 * touched-outside-of-dialog-window
 * Solution: Formatted examples to fit code requirements
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class CardColorSelectionDialog extends Dialog implements android.view.View.OnClickListener, Serializable {
    private static final long serialVersionUID = 277245564192016L;
    /** Class Instance Variables */
    private Button selectBtn; //button for selection
    private Button cancelBtn; //button for cancellation
    private ImageButton train1, train2, train3, train4, train5, train6, train7, train8; //image buttons for train colors
    private RadioButton noLoc, oneLoc, twoLoc, threeLoc, fourLoc, fiveLoc, sixLoc; //radiobuttons for selecting how many 'locs' needed
    private RadioButton[] locomotives; //array for radio buttons
    private TextView text; //text view for displaying messages to user
    private Deck trainCards; //deck being passed in
    private final String[] trainColors = {"Red", "Orange", "Yellow", "Green",
            "Blue", "Pink", "White", "Black"}; //train colors of trains
    int[] colorCounts = new int[8];
    private boolean[] usable = new boolean[trainColors.length]; // array of booleans representing if player has enough of that color
    private boolean[] highlighted = new boolean[trainColors.length]; // boolean to mark which tracks should be highlighted
    private boolean selected = false; // if select has been pressed
    private int numRainbows = 0; //num of locomotive cards
    private int useRainbows = 0; //num of locomotive cards being used
    private Game game; //game to send action
    private TTRHumanPlayer player; //player sent in the action
    private int min = 0;

    /**
     * CardColorSelectionDialog constructor
     * @param a
     * @param cards
     * @param myState
     * @param track
     * @param game
     * @param player
     */
    public CardColorSelectionDialog(Activity a, Deck cards, TTRGameState myState, Track track, Game game, TTRHumanPlayer player) {
        super(player.myActivity);
        this.game = game;
        this.player = player;
        this.trainCards = cards;
        min = track.getTrainTrackNum();
        selected = false;

        for(int i = 0; i < trainCards.size(); i++){
            if(trainCards.getCards().get(i).toString().equals("Rainbow")){
                numRainbows++;
            }
        }
        for(int i = 0; i < trainColors.length; i++){
            highlighted[i] = false;
        }
        for(int i = 0; i < trainColors.length; i++){
            for(int j = 0; j < trainCards.size(); j++){
                if(trainCards.getCards().get(j).toString().equals(trainColors[i])){
                    colorCounts[i]++;
                }
                if(colorCounts[i]+numRainbows >= min){
                    this.usable[i] = true;
                } else{
                    this.usable[i] = false;
                }
            }
            this.highlighted[i] = false;
        }
    }

    /**
     * Method called when dialog created, used to initialize all widgets
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.card_selection_dialog);
        //initialize all buttons (regular, image, radio) and set as listeners
        selectBtn = (Button) findViewById(R.id.btn_select);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(this);
        train1 = (ImageButton) findViewById(R.id.train1);
        train2 = (ImageButton) findViewById(R.id.train2);
        train3 = (ImageButton) findViewById(R.id.train3);
        train4 = (ImageButton) findViewById(R.id.train4);
        train5 = (ImageButton) findViewById(R.id.train5);
        train6 = (ImageButton) findViewById(R.id.train6);
        train7 = (ImageButton) findViewById(R.id.train7);
        train8 = (ImageButton) findViewById(R.id.train8);
        noLoc = (RadioButton)findViewById(R.id.None_Rainbow);
        oneLoc = (RadioButton)findViewById(R.id.One_Rainbow);
        twoLoc = (RadioButton)findViewById(R.id.Two_Rainbow);
        threeLoc = (RadioButton)findViewById(R.id.Three_Rainbow);
        fourLoc = (RadioButton)findViewById(R.id.Four_Rainbow);
        fiveLoc = (RadioButton)findViewById(R.id.Five_Rainbow);
        sixLoc = (RadioButton)findViewById(R.id.Six_Rainbow);
        locomotives = new RadioButton[]{noLoc, oneLoc, twoLoc, threeLoc, fourLoc, fiveLoc, sixLoc};
        train1.setOnClickListener(this);
        train2.setOnClickListener(this);
        train3.setOnClickListener(this);
        train4.setOnClickListener(this);
        train5.setOnClickListener(this);
        train6.setOnClickListener(this);
        train7.setOnClickListener(this);
        train8.setOnClickListener(this);
        noLoc.setOnClickListener(this);
        oneLoc.setOnClickListener(this);
        twoLoc.setOnClickListener(this);
        threeLoc.setOnClickListener(this);
        fourLoc.setOnClickListener(this);
        fiveLoc.setOnClickListener(this);
        sixLoc.setOnClickListener(this);

        //set no loc as default
        noLoc.setChecked(true);
        //set default visibility
        if(numRainbows != 0){
            for(int i = 1; i <= numRainbows; i++){
                if(i < 7)
                    locomotives[i].setVisibility(View.VISIBLE);
            }
        }
        /** Check if cards are usable */
        if(this.usable[0]){
            train1.setClickable(true);
        } else{
            train1.setClickable(false);
            train1.setAlpha(0.0f);
        }
        if(this.usable[1]){
            train2.setClickable(true);
        } else{
            train2.setClickable(false);
            train2.setAlpha(0.0f);
        }
        if(this.usable[2]){
            train3.setClickable(true);
        } else{
            train3.setClickable(false);
            train3.setAlpha(0.0f);
        }
        if(this.usable[3]){
            train4.setClickable(true);
        } else{
            train4.setClickable(false);
            train4.setAlpha(0.0f);
        }
        if(this.usable[4]){
            train5.setClickable(true);
        } else{
            train5.setClickable(false);
            train5.setAlpha(0.0f);
        }
        if(this.usable[5]){
            train6.setClickable(true);
        } else{
            train6.setClickable(false);
            train6.setAlpha(0.0f);
        }
        if(this.usable[6]){
            train7.setClickable(true);
        } else{
            train7.setClickable(false);
            train7.setAlpha(0.0f);
        }
        if(this.usable[7]){
            train8.setClickable(true);
        } else{
            train8.setClickable(false);
            train8.setAlpha(0.0f);
        }
        selectBtn.setOnClickListener(this);

        text = (TextView) findViewById(R.id.ccsd_text);
    }

    /**
     * Method for handling user clicks inside the dialog box
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_select){
            if(selected || useRainbows >= min){
                int position = -1;
                for(int i = 0; i < trainColors.length; i++){
                    if(highlighted[i]){
                        position = i;
                    }
                }
                if(useRainbows >= min){
                    game.sendAction(new ConfirmSelectionAction(player, trainColors[0], useRainbows));
                    dismiss();
                }
                else if((colorCounts[position] + useRainbows) >= min) {
                    game.sendAction(new ConfirmSelectionAction(player, trainColors[position], useRainbows));
                    dismiss();
                }
                else{
                    text.setText("In order to use this deck, you must use Locomotive cards.");
                }
            } else {
                text.setText("Please choose a deck.");
            }
        } else if (v.getId() == R.id.btn_cancel){
            dismiss();
        }
        if(v.getId() == R.id.train1){
            if(highlighted[0]){
                highlighted[0] = false;
                train1.setAlpha(1f);
                selected = false;
            } else if(!highlighted[0] && !selected){
                highlighted[0] = true;
                train1.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train2){
            if(highlighted[1]){
                highlighted[1] = false;
                train2.setAlpha(1f);
                selected = false;
            } else if(!highlighted[1] && !selected){
                highlighted[1] = true;
                train2.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train3){
            if(highlighted[2]){
                highlighted[2] = false;
                train3.setAlpha(1f);
                selected = false;
            } else if(!highlighted[2] && !selected){
                highlighted[2] = true;
                train3.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train4){
            if(highlighted[3]){
                highlighted[3] = false;
                train4.setAlpha(1f);
                selected = false;
            } else if(!highlighted[3] && !selected){
                highlighted[3] = true;
                train4.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train5){
            if(highlighted[4]){
                highlighted[4] = false;
                train5.setAlpha(1f);
                selected = false;
            } else if(!highlighted[4] && !selected){
                highlighted[4] = true;
                train5.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train6){
            if(highlighted[5]){
                highlighted[5] = false;
                train6.setAlpha(1f);
                selected = false;
            } else if(!highlighted[5] && !selected){
                highlighted[5] = true;
                train6.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train7){
            if(highlighted[6]){
                highlighted[6] = false;
                train7.setAlpha(1f);
                selected = false;
            } else if(!highlighted[6] && !selected){
                highlighted[6] = true;
                train7.setAlpha(0.5f);
                selected = true;
            }
        } else if(v.getId() == R.id.train8){
            if(highlighted[7]){
                highlighted[7] = false;
                train8.setAlpha(1f);
                selected = false;
            } else if(!highlighted[7] && !selected){
                highlighted[7] = true;
                train8.setAlpha(0.5f);
                selected = true;
            }
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
