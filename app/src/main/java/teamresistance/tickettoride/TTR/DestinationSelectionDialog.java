package teamresistance.tickettoride.TTR;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

import teamresistance.tickettoride.Game.Game;
import teamresistance.tickettoride.R;
import teamresistance.tickettoride.TTR.Actions.ConfirmSelectionAction;

/**
 *
 * Class that inflates view into dialog for custom handling user choice of which destination cards
 * to keep.
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
public class DestinationSelectionDialog extends  Dialog implements android.view.View.OnClickListener, Serializable {
    private static final long serialVersionUID = 388255575192016L;
    /** Class Instance Variables */
    private TTRGameState myState; //TTRGameState
    private Button selectBtn; //button for user to select their choices
    private ImageButton ticket1, ticket2, ticket3; //imagebuttons for tickets user can click
    private int min; //int representing minimum number of cards user must select
    private int selected; //boolean set when select is clicked
    private TextView text; //textview to display any errors/messages to user
    private Card[] destinationCards = new Card[3]; //temp cards representing user's choices
    private Game game; //the game used to send action
    private TTRHumanPlayer player; //player used in sending action

    /**
     * Contructor for dialog
     * @param me
     * @param start
     * @param cards
     * @param game
     */
    public DestinationSelectionDialog(TTRHumanPlayer me, boolean start, Deck cards, Game game) {
        super(me.myActivity);
        min = 0;
        selected = 0;
        this.player = me;
        this.game = game;
        this.myState = me.myState;
        if(start){
            min = 2;
        } else {
            min = 1;
        }
        //deep copy of cards
        for(int i = 0; i < 3; i++){
            if(cards != null) {
                destinationCards[i] = cards.getCards().get(i);
                destinationCards[i].setHighlight(false);
            }
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
        setContentView(R.layout.destination_selection_dialog);
        //set all buttons
        selectBtn = (Button) findViewById(R.id.btn_select);
        ticket1 = (ImageButton) findViewById(R.id.ticket1);
        ticket2 = (ImageButton) findViewById(R.id.ticket2);
        ticket3 = (ImageButton) findViewById(R.id.ticket3);
        text = (TextView)findViewById(R.id.dsd_text);

        //display text representing destination tickets
        DestinationCards tempCard1 = (DestinationCards)destinationCards[0];
        DestinationCards tempCard2 = (DestinationCards)destinationCards[1];
        DestinationCards tempCard3 = (DestinationCards)destinationCards[2];

        //sets image resource of the image buttons
        ticket1.setImageResource(setDestCard(tempCard1.getCity1(), tempCard1.getCity2()));
        ticket2.setImageResource(setDestCard(tempCard2.getCity1(), tempCard2.getCity2()));
        ticket3.setImageResource(setDestCard(tempCard3.getCity1(), tempCard3.getCity2()));
        //set listeners
        ticket1.setOnClickListener(this);
        ticket2.setOnClickListener(this);
        ticket3.setOnClickListener(this);
        selectBtn.setOnClickListener(this);

    }

    /**
     * Returns the resource id for the destination ticket images
     * @param firstCity
     * @param secondCity
     * @return
     */
    public int setDestCard(String firstCity, String secondCity){
        if(firstCity.equals("Winnipeg") && secondCity.equals("Little Rock")){
            return R.drawable.winnipeg_littlerock;
        } else if(firstCity.equals("Winnipeg") && secondCity.equals("Houston")){
            return R.drawable.winnipeg_houston;
        } else if(firstCity.equals("Vancouver") && secondCity.equals("Santa Fe")){
            return R.drawable.vancouver_santafe;
        } else if(firstCity.equals("Vancouver") && secondCity.equals("Montreal")){
            return R.drawable.vancouver_montreal;
        } else if(firstCity.equals("Toronto") && secondCity.equals("Miami")){
            return R.drawable.toronto_miami;
        } else if(firstCity.equals("Seattle") && secondCity.equals("New York")){
            return R.drawable.seattle_newyork;
        } else if(firstCity.equals("Seattle") && secondCity.equals("Los Angeles")){
            return R.drawable.seattle_losangeles;
        } else if(firstCity.equals("Sault Ste Marie") && secondCity.equals("Oklahoma City")){
            return R.drawable.saultstemarie_oklahomacity;
        } else if(firstCity.equals("Sault Ste Marie") && secondCity.equals("Nashville")){
            return R.drawable.saultstemarie_nashville;
        } else if(firstCity.equals("San Francisco") && secondCity.equals("Atlanta")){
            return R.drawable.sanfrancisco_atlanta;
        } else if(firstCity.equals("Portland") && secondCity.equals("Phoenix")){
            return R.drawable.portland_phoenix;
        } else if(firstCity.equals("Portland") && secondCity.equals("Nashville")){
            return R.drawable.portland_nashville;
        } else if(firstCity.equals("New York") && secondCity.equals("Atlanta")){
            return R.drawable.newyork_atlanta;
        } else if(firstCity.equals("Montreal") && secondCity.equals("New Orleans")){
            return R.drawable.montreal_neworleans;
        } else if(firstCity.equals("Montreal") && secondCity.equals("Atlanta")){
            return R.drawable.montreal_atlanta;
        } else if(firstCity.equals("Los Angeles") && secondCity.equals("New York")){
            return R.drawable.losangeles_newyork;
        } else if(firstCity.equals("Los Angeles") && secondCity.equals("Miami")){
            return R.drawable.losangeles_miami;
        } else if(firstCity.equals("Los Angeles") && secondCity.equals("Chicago")){
            return R.drawable.losangeles_chicago;
        } else if(firstCity.equals("Kansas City") && secondCity.equals("Houston")){
            return R.drawable.kansascity_houston;
        } else if(firstCity.equals("Helena") && secondCity.equals("Los Angeles")){
            return R.drawable.helena_losangeles;
        } else if(firstCity.equals("Duluth") && secondCity.equals("Houston")){
            return R.drawable.duluth_houston;
        } else if(firstCity.equals("Duluth") && secondCity.equals("El Paso")){
            return R.drawable.duluth_elpaso;
        } else if(firstCity.equals("Denver") && secondCity.equals("Pittsburgh")){
            return R.drawable.denver_pittsburgh;
        } else if(firstCity.equals("Denver") && secondCity.equals("El Paso")){
            return R.drawable.denver_elpaso;
        } else if(firstCity.equals("Dallas") && secondCity.equals("New York")){
            return R.drawable.dallas_newyork;
        } else if(firstCity.equals("Chicago") && secondCity.equals("Santa Fe")){
            return R.drawable.chicago_santafe;
        } else if(firstCity.equals("Chicago") && secondCity.equals("New Orleans")){
            return R.drawable.chicago_neworleans;
        } else if(firstCity.equals("Calgary") && secondCity.equals("Salt Lake City")){
            return R.drawable.calgary_saltlakecity;
        } else if(firstCity.equals("Calgary") && secondCity.equals("Phoenix")){
            return R.drawable.calgary_phoenix;
        } else if(firstCity.equals("Boston") && secondCity.equals("Miami")){
            return R.drawable.boston_miami;
        } else {
            return R.drawable.ticket_back;
        }
    }

    /**
     * Method for handling user clicks inside the dialog box
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_select){
            if(selected >= min){
                //confirm minimum number of cards selected before confrim
                Card[] tempCards = new Card[selected];
                Card[] deleteCards = new Card[3-selected];
                int count = 0;
                int deleteCount = 0;
                for(int i = 0; i < 3; i++){
                    if(destinationCards[i].getHighlight()){
                        tempCards[count] = destinationCards[i];
                        count++;
                    }
                    else{
                        deleteCards[deleteCount] = destinationCards[i];
                        deleteCount++;
                    }
                }
                destinationCards[0].setHighlight(false);
                destinationCards[1].setHighlight(false);
                destinationCards[2].setHighlight(false);
                Deck sendDeck = new Deck("deck with added cards", tempCards);
                Deck removeDeck = new Deck("Cards to be removed", deleteCards);
                game.sendAction(new ConfirmSelectionAction(player, sendDeck, removeDeck));
                dismiss();
            } else { //display error
                text.setText("Please select at least the minimum number of ticket cards: " + min );
            }
        }
        /**  Control for highlighting (aka selecting) the cards */
        if(v.getId() == R.id.ticket1){
            if(destinationCards[0].getHighlight()){
                destinationCards[0].setHighlight(false);
                ticket1.setAlpha(1f);
                selected--;
            } else{
                destinationCards[0].setHighlight(true);
                ticket1.setAlpha(0.5f);
                selected++;
            }
        }
        if(v.getId() == R.id.ticket2){
            if(destinationCards[1].getHighlight()){
                destinationCards[1].setHighlight(false);
                ticket2.setAlpha(1f);
                selected--;
            } else{
                destinationCards[1].setHighlight(true);
                ticket2.setAlpha(0.5f);
                selected++;
            }
        }
        if(v.getId() == R.id.ticket3){
            if(destinationCards[2].getHighlight()){
                destinationCards[2].setHighlight(false);
                ticket3.setAlpha(1f);
                selected--;
            } else{
                destinationCards[2].setHighlight(true);
                ticket3.setAlpha(0.5f);
                selected++;
            }
        }
    }
}
