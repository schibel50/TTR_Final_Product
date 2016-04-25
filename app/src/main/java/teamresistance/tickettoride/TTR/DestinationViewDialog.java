package teamresistance.tickettoride.TTR;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import teamresistance.tickettoride.Game.Game;
import teamresistance.tickettoride.R;

/**
 *
 * Class that inflates view into dialog for custom handling user view of which destination cards.
 *
 * * External Citation
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
public class DestinationViewDialog extends Dialog implements android.view.View.OnClickListener, Serializable {
    private static final long serialVersionUID = 388255575192016L;

    private Deck destinationCards;
    private Game game; //the game used to send action
    private TTRHumanPlayer player; //player used in sending action
    private TTRGameState myState; //TTRGameState
    private Button closeBtn;
    private Button backBtn;
    private Button nextBtn;
    private ImageView ticket1, ticket2, ticket3;
    private ImageView[] tickets = {ticket1,ticket2,ticket3};
    private int screenSpot;
    /**
     * Contructor for dialog
     * @param me
     * @param cards
     * @param game
     */
    public DestinationViewDialog(TTRHumanPlayer me, Deck cards, Game game) {
        super(me.myActivity);
        this.player = me;
        this.game = game;
        this.myState = me.myState;
        destinationCards = new Deck(cards);
        this.screenSpot = 0;
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
        setContentView(R.layout.destination_ticket_view_dialog);
        //set all buttons
        closeBtn = (Button) findViewById(R.id.btn_close);
        closeBtn.setOnClickListener(this);

        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(this);

        nextBtn = (Button) findViewById(R.id.next_button);
        nextBtn.setOnClickListener(this);

        tickets[0] = (ImageView)findViewById(R.id.ticket1);
        tickets[1] = (ImageView)findViewById(R.id.ticket2);
        tickets[2] = (ImageView)findViewById(R.id.ticket3);
        setImages();
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
        if(v.getId() == R.id.btn_close) {
            dismiss();
        }
        else if(v.getId() == R.id.back_button){
            screenSpot--;
            setImages();
        }
        else if(v.getId() == R.id.next_button){
            screenSpot++;
            setImages();
        }
    }

    public void setImages(){
        int count = 0;
        boolean noMoreForward = false;
        DestinationCards tempCard;
        for (int i = screenSpot*3; i < (screenSpot*3) + 3; i++) {
            if(i < myState.getPlayerDestinationDecks()[myState.getPlayerID()].getCards().size()) {
                tempCard = (DestinationCards) myState.getPlayerDestinationDecks()[myState.getPlayerID()].getCards().get(i);
                //http://stackoverflow.com/questions/2859212/how-to-clear-an-imageview-in-android
                tickets[i-(screenSpot*3)].setImageResource(0);
                tickets[i-(screenSpot*3)].setImageResource(setDestCard(tempCard.getCity1(), tempCard.getCity2()));
                tickets[i-(screenSpot*3)].setVisibility(View.VISIBLE);
                count++;
            }
            else{
                tickets[i-(screenSpot*3)].setVisibility(View.GONE);
                noMoreForward = true;
            }
            if((i+1) == myState.getPlayerDestinationDecks()[myState.getPlayerID()].getCards().size()){
                noMoreForward = true;
            }
        }
        if(noMoreForward){
            nextBtn.setClickable(false);
            nextBtn.setAlpha(0.5f);
        }
        else{
            nextBtn.setClickable(true);
            nextBtn.setAlpha(1.0f);
        }
        if(screenSpot == 0){
            backBtn.setClickable(false);
            backBtn.setAlpha(0.5f);
        }
        else {
            backBtn.setClickable(true);
            backBtn.setAlpha(1.0f);
        }
    }
}
