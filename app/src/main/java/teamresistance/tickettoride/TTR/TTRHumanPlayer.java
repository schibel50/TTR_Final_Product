package teamresistance.tickettoride.TTR;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import teamresistance.tickettoride.Game.GameHumanPlayer;
import teamresistance.tickettoride.Game.GameMainActivity;
import teamresistance.tickettoride.Game.infoMsg.GameInfo;
import teamresistance.tickettoride.R;
import teamresistance.tickettoride.TTR.Actions.ChangeModeAction;
import teamresistance.tickettoride.TTR.Actions.ConfirmSelectionAction;
import teamresistance.tickettoride.TTR.Actions.DrawDestinationCardAction;
import teamresistance.tickettoride.TTR.Actions.DrawDownCardAction;
import teamresistance.tickettoride.TTR.Actions.DrawUpCardAction;
import teamresistance.tickettoride.TTR.Actions.TrackPlaceAction;

/**
 * TTRHumanPlayer implements the human player
 *
 * RESOURCES:
 * 4/24/16
 * http://stackoverflow.com/questions/9656853/the-correct-way-to-play-short-sounds-android
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class TTRHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener, Serializable {
    public TTRSurfaceView myBoard;
    public GameMainActivity myActivity;
    public TTRGameState myState;
    private Deck trainDeck;
    private final String[] trainColors = {"Yellow", "Blue", "Orange", "White",
            "Pink", "Black", "Red", "Green", "Rainbow"};
    private Button confirmSelection;
    private static final long serialVersionUID = 333245564192016L;
    protected SoundPool soundArray;
    private Random rand = new Random();

    /** TextViews for player's names*/
    private TextView cpu1PlayerTextView;
    private TextView cpu2PlayerTextView;
    private TextView cpu3PlayerTextView;
    private TextView humanTextView;

    /** TextViews for player's scores*/
    private TextView cpu1ScoreTextview;
    private TextView cpu2ScoreTextview;
    private TextView cpu3ScoreTextview;
    private TextView humanScoreTextview;

    /**extViews for player's trainToken count */
    private TextView cpu1TrainTokenTextView;
    private TextView cpu2TrainTokenTextView;
    private TextView cpu3TrainTokenTextView;
    private TextView humanTrainTokenTextView;

    /** TextViews for player's destination card count */
    private TextView playerDestinationCardTextView;
    private Button viewPlayerDestinationCards;

    /** tells whose turn it is */
    private TextView playerTurnTextView;
    private TextView[] destinationCards;
    private TextView cpu1DestinationCardTextView;
    private TextView cpu2DestinationCardTextView;
    private TextView cpu3DestinationCardTextView;

    /** TextViews for player's train card count */
    private TextView cpu1TrainCardTextView;
    private TextView cpu2TrainCardTextView;
    private TextView cpu3TrainCardTextView;

    /** TextViews for the player deck card colors */
    private TextView redColorCount;
    private TextView orangeColorCount;
    private TextView yellowColorCount;
    private TextView greenColorCount;
    private TextView blueColorCount;
    private TextView pinkColorCount;
    private TextView blackColorCount;
    private TextView whiteColorCount;
    private TextView rainbowColorCount;

    /** Widjets to control what player Stats display/Scaling */
    private FrameLayout cpu1FrameLayout;
    private FrameLayout cpu2FrameLayout;
    private FrameLayout cpu3FrameLayout;
    /*
    Variables for all of the buttons on the right side of the screen,
    the place holder variables to eliminate hard coding, and the
    ArrayList for all of the ImageButtons.
     */
    private ImageButton[] faceUpTrainCards = new ImageButton[5];
    private ImageButton clickTrain;
    private ImageButton clickDestination;
    private CheckBox cardCheck;
    private CheckBox trainCheck;
    private LinearLayout.LayoutParams lp;

    private boolean startGame = true;
    private boolean[] highlights;
    private boolean[] selected;
    private boolean[] covered;
    private int[] playerIDs;
    private boolean[] highlights2;
    private boolean[] selected2;
    private boolean[] covered2;
    private int[] playerIDs2;

    /*
     * The human player
     * @name
     */
    public TTRHumanPlayer(String name) {
        super(name);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //Update all TextViews to Display player details properly
        if (info instanceof TTRGameState) {
            myState = (TTRGameState) info;
            highlights = new boolean[myState.getTracks().size()];
            selected = new boolean[myState.getTracks().size()];
            covered = new boolean[myState.getTracks().size()];
            playerIDs = new int[myState.getTracks().size()];
            highlights2 = new boolean[myState.getTracks().size()];
            selected2 = new boolean[myState.getTracks().size()];
            covered2 = new boolean[myState.getTracks().size()];
            playerIDs2 = new int[myState.getTracks().size()];
            trainDeck = myState.getPlayerTrainDecks()[this.playerNum];
            int playerNum = ((TTRGameState) info).getNumPlayers();
            for (int i = 0; i < myState.getFaceUpTrainCards().size(); i++) {
                if(myState.getFaceUpTrainCards().getCards().get(i).toString().equals("Blank")){
                    this.faceUpTrainCards[i].setClickable(false);
                }
                else if (myState.getFaceUpCardsHighlight()[i]) {
                    this.faceUpTrainCards[i].setAlpha(0.5f);
                    this.faceUpTrainCards[i].setClickable(true);
                } else {
                    this.faceUpTrainCards[i].setAlpha(1.0f);
                    this.faceUpTrainCards[i].setClickable(true);
                }
            }
            if(myState.getFaceDownTrainCards().getCards().isEmpty()){
                this.clickTrain.setClickable(false);
                this.clickTrain.setVisibility(View.INVISIBLE);
            }
            else{
                this.clickTrain.setClickable(true);
                this.clickTrain.setVisibility(View.VISIBLE);
            }
            if(myState.getDestinationCards().getCards().size() < 3){
                this.clickDestination.setClickable(false);
                this.clickDestination.setVisibility(View.INVISIBLE);
            }
            else{
                this.clickDestination.setClickable(true);
                this.clickDestination.setVisibility(View.VISIBLE);
            }
            if (myState.getFaceDownTrainCards().getHighlight()) {
                this.clickTrain.setAlpha(0.5f);
            } else {
                this.clickTrain.setAlpha(1.0f);
            }

            if (myState.getDestinationCards().getHighlight()) {
                this.clickDestination.setAlpha(0.5f);
            } else {
                this.clickDestination.setAlpha(1.0f);
            }

            this.trainCheck.setChecked(myState.getTrackModeSelected());
            this.cardCheck.setChecked(myState.getCardModeSelected());

            boolean val = myState.getTrackModeSelected();

            if(myState.getNumPlayers() < 4) {
                if (myState.getPlayerID() == this.playerNum) {
                    for (int i = 0; i < myState.getTracks().size(); i++) {
                        if ((canChoose(myState.getTracks().get(i)) || !val)
                                && myState.getTrainTokens()[this.playerNum]
                                >= myState.getTracks().get(i).getTrainTrackNum()) {
                            highlights[i] = val;
                            if(myState.getTracks2().get(i).getTrackColor().equals("Blank")){
                                highlights2[i] = false;
                            }
                            else if(canChoose(myState.getTracks2().get(i)) || !val){
                                highlights2[i] = val;
                            }
                        } else if(myState.getTrainTokens()[this.playerNum]
                                < myState.getTracks().get(i).getTrainTrackNum()){
                            highlights[i] = false;
                            if(!myState.getTracks2().get(i).getTrackColor().equals("Blank")){
                                highlights2[i] = false;
                            }
                        } else {
                            highlights[i] = !val;
                            if(myState.getTracks2().get(i).getTrackColor().equals("Blank")){
                                highlights2[i] = false;
                            }
                            else if(canChoose(myState.getTracks2().get(i)) || !val){
                                highlights2[i] = val;
                            }else if(myState.getTrainTokens()[this.playerNum]
                                    < myState.getTracks().get(i).getTrainTrackNum()){
                                highlights2[i] = false;
                            }
                            else{
                                highlights2[i] = !val;
                            }
                        }
                    }
                }
            }
            else{
                if (myState.getPlayerID() == this.playerNum) {
                    for (int i = 0; i < myState.getTracks().size(); i++) {
                        if ((canChoose(myState.getTracks().get(i)) || !val)
                                && myState.getTrainTokens()[this.playerNum]
                                >= myState.getTracks().get(i).getTrainTrackNum()) {
                            highlights[i] = val;
                        } else if(myState.getTrainTokens()[this.playerNum]
                                < myState.getTracks().get(i).getTrainTrackNum()){
                            highlights[i] = false;
                            if(!myState.getTracks2().get(i).getTrackColor().equals("Blank")){
                                highlights2[i] = false;
                            }
                        } else {
                            highlights[i] = !val;
                        }
                        if(myState.getTracks2().get(i).getTrackColor().equals("Blank")){
                            highlights2[i] = false;
                        }
                        else if((canChoose(myState.getTracks2().get(i)) || !val)
                                && myState.getTrainTokens()[this.playerNum]
                                >= myState.getTracks().get(i).getTrainTrackNum()){
                            highlights2[i] = val;
                        } else if(myState.getTrainTokens()[this.playerNum]
                                < myState.getTracks().get(i).getTrainTrackNum()){
                            highlights2[i] = false;
                        }
                        else{
                            highlights2[i] = !val;
                        }
                    }
                }
            }
            for (int i = 0; i < myState.getTracks().size(); i++) {
                if (myState.getSelectedTracks()[i]) {
                    selected[i] = true;
                } else {
                    selected[i] = false;
                }
                if (myState.getCoveredTracks()[i]) {
                    covered[i] = true;
                    playerIDs[i] = myState.getTrackIds()[i];
                } else {
                    covered[i] = false;
                    playerIDs[i] = -1;
                }
                if(myState.getTrackIds()[i] != -1){
                    playerIDs[i] = myState.getTrackIds()[i];
                }
                else{
                    playerIDs[i] = -1;
                }
                if (myState.getSelectedTracks2()[i]) {
                    selected2[i] = true;
                } else {
                    selected2[i] = false;
                }
                if (myState.getCoveredTracks2()[i]) {
                    covered2[i] = true;
                } else {
                    covered2[i] = false;
                }
                if(myState.getTrackIds2()[i] != -1){
                    playerIDs2[i] = myState.getTrackIds2()[i];
                }
                else{
                    playerIDs2[i] = -1;
                }
            }
            myBoard.setHighlights(highlights);
            myBoard.setSelected(selected);
            myBoard.setCovered(covered);
            myBoard.setPlayerIDs(playerIDs);

            myBoard.setHighlights2(highlights2);
            myBoard.setSelected2(selected2);
            myBoard.setCovered2(covered2);
            myBoard.setPlayerIDs2(playerIDs2);

            if (playerNum >= 2) { //2 is the minimum number of players
                lp = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, 1);
                cpu1FrameLayout.setLayoutParams(lp);
                cpu2FrameLayout.setVisibility(View.GONE);
                cpu3FrameLayout.setVisibility(View.GONE);
                this.humanTextView.setText("" + this.allPlayerNames[this.playerNum]);
                this.humanScoreTextview.setText("" + ((TTRGameState) info).getScores()[this.playerNum]);
                this.humanTrainTokenTextView.setText("" + ((TTRGameState) info).getTrainTokens()[this.playerNum]);
                this.playerDestinationCardTextView.setText("" + myState.getPlayerDestinationDecks()[this.playerNum].size());
                if (myState.getPlayerID() == this.playerNum) {
                    this.playerTurnTextView.setText("It is your turn!");
                } else {
                    this.playerTurnTextView.setText("" + allPlayerNames[myState.getPlayerID()] + "'s turn!");
                }
                this.cpu1PlayerTextView.setText("" + this.allPlayerNames[(this.playerNum + 1) % myState.getNumPlayers()]);
                this.cpu1TrainTokenTextView.setText("" + ((TTRGameState) info).getTrainTokens()[(this.playerNum + 1) % myState.getNumPlayers()]);
                this.cpu1ScoreTextview.setText("" + ((TTRGameState) info).getScores()[(this.playerNum + 1) % myState.getNumPlayers()]);
                this.cpu1DestinationCardTextView.setText("" + ((TTRGameState) info).getPlayerDestinationDecks()[(this.playerNum + 1) % myState.getNumPlayers()].getCards().size());
                this.cpu1TrainCardTextView.setText("" + ((TTRGameState) info).getPlayerTrainDecks()[(this.playerNum + 1) % myState.getNumPlayers()].getCards().size());
            }
            if (playerNum >= 3) {
                lp = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, 0.5f);
                cpu1FrameLayout.setLayoutParams(lp);
                cpu2FrameLayout.setLayoutParams(lp);
                cpu2FrameLayout.setVisibility(View.VISIBLE);
                cpu3FrameLayout.setVisibility(View.GONE);
                this.cpu2PlayerTextView.setText("" + this.allPlayerNames[(this.playerNum + 2) % myState.getNumPlayers()]);
                this.cpu2TrainTokenTextView.setText("" + ((TTRGameState) info).getTrainTokens()[(this.playerNum + 2) % myState.getNumPlayers()]);
                this.cpu2ScoreTextview.setText("" + ((TTRGameState) info).getScores()[(this.playerNum + 2) % myState.getNumPlayers()]);
                this.cpu2DestinationCardTextView.setText("" + ((TTRGameState) info).getPlayerDestinationDecks()[(this.playerNum + 2) % myState.getNumPlayers()].getCards().size());
                this.cpu2TrainCardTextView.setText("" + ((TTRGameState) info).getPlayerTrainDecks()[(this.playerNum + 2) % myState.getNumPlayers()].getCards().size());
            }
            if (playerNum >= 4) {
                lp = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, 0.33f);
                cpu1FrameLayout.setLayoutParams(lp);
                cpu2FrameLayout.setLayoutParams(lp);
                cpu3FrameLayout.setLayoutParams(lp);
                cpu2FrameLayout.setVisibility(View.VISIBLE);
                cpu3FrameLayout.setVisibility(View.VISIBLE);
                this.cpu3PlayerTextView.setText("" + this.allPlayerNames[(this.playerNum + 3) % myState.getNumPlayers()]);
                this.cpu3TrainTokenTextView.setText("" + ((TTRGameState) info).getTrainTokens()[(this.playerNum + 3) % myState.getNumPlayers()]);
                this.cpu3ScoreTextview.setText("" + ((TTRGameState) info).getScores()[(this.playerNum + 3) % myState.getNumPlayers()]);
                this.cpu3DestinationCardTextView.setText("" + ((TTRGameState) info).getPlayerDestinationDecks()[(this.playerNum + 3) % myState.getNumPlayers()].getCards().size());
                this.cpu3TrainCardTextView.setText("" + ((TTRGameState) info).getPlayerTrainDecks()[(this.playerNum + 3) % myState.getNumPlayers()].getCards().size());
            }
            for (int i = 0; i < myState.getFaceUpTrainCards().size(); i++) {
                if ((myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Black")) {
                    faceUpTrainCards[i].setImageResource(R.drawable.black_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Pink")) {
                    faceUpTrainCards[i].setImageResource(R.drawable.pink_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Blue")) {
                    this.faceUpTrainCards[i].setImageResource(R.drawable.blue_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Green")) {
                    this.faceUpTrainCards[i].setImageResource(R.drawable.green_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Orange")) {
                    this.faceUpTrainCards[i].setImageResource(R.drawable.orange_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Red")) {
                    this.faceUpTrainCards[i].setImageResource(R.drawable.red_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("White")) {
                    this.faceUpTrainCards[i].setImageResource(R.drawable.white_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Yellow")) {
                    this.faceUpTrainCards[i].setImageResource(R.drawable.yellow_train);
                } else if (((TrainCards) myState.getFaceUpTrainCards().getCards().get(i)).toString().equals("Rainbow")){
                    this.faceUpTrainCards[i].setImageResource(R.drawable.rainbow_train);
                } else{
                    this.faceUpTrainCards[i].setAlpha(0.0f);
                }
            }
            blackColorCount.setText("" + myState.getTrainColorCount("Black", this.playerNum));
            whiteColorCount.setText("" + myState.getTrainColorCount("White", this.playerNum));
            blueColorCount.setText("" + myState.getTrainColorCount("Blue", this.playerNum));
            redColorCount.setText("" + myState.getTrainColorCount("Red", this.playerNum));
            orangeColorCount.setText("" + myState.getTrainColorCount("Orange", this.playerNum));
            yellowColorCount.setText("" + myState.getTrainColorCount("Yellow", this.playerNum));
            pinkColorCount.setText("" + myState.getTrainColorCount("Pink", this.playerNum));
            greenColorCount.setText("" + myState.getTrainColorCount("Green", this.playerNum));
            rainbowColorCount.setText("" + myState.getTrainColorCount("Rainbow", this.playerNum));
            myBoard.postInvalidate();
            if (startGame && myState.getPlayerID() == this.playerNum) {
                startGame = false;
                Deck tempDeck = new Deck("temp");
                myState.getDestinationCards().moveTopCardTo(tempDeck, myState.getDestinationCards());
                myState.getDestinationCards().moveTopCardTo(tempDeck, myState.getDestinationCards());
                myState.getDestinationCards().moveTopCardTo(tempDeck, myState.getDestinationCards());
                displayDestinationPopup(tempDeck, true);
            }
        }
    }

    /**
     * determines which non gray tracks should be highlight-able
     *
     * @param track reference to Tracks
     * @return boolean
     */
    public boolean canChoose(Track track) {
        if (track.getCovered()) {
            return false;
        }
        String tempColor = track.getTrackColor();
        int trackLength = track.getTrainTrackNum();
        Deck tempDeck = this.trainDeck;
        int numberOfCards = 0;
        if (!tempColor.equals("Gray")) {
            for (int i = 0; i < tempDeck.size(); i++) {
                if (tempDeck.getCards().get(i).toString().equals(tempColor) ||
                        tempDeck.getCards().get(i).toString().equals("Rainbow")) {
                    numberOfCards++;
                }
            }
        }
        else if(tempColor.equals("Gray")){
            return chooseGray(track);
        }
        if(numberOfCards >= trackLength){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Checks to see what gray tracks should be highlighted
     *
     * @param track reference to Track
     * @return boolean
     */
    public boolean chooseGray(Track track) {
        int rainbowCount = myState.getTrainColorCount("Rainbow", this.playerNum);
        if ((myState.getTrainColorCount("Black", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("White", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("Blue", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("Red", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("Orange", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("Yellow", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("Pink", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else if ((myState.getTrainColorCount("Green", this.playerNum) + rainbowCount) >= track.getTrainTrackNum()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * returns the top view for the player
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /*
     * Sets the GUI for the player
     * @mainActivity
     */
    public void setAsGui(GameMainActivity mainActivity) {
        myActivity = mainActivity;
        myActivity.setContentView(R.layout.activity_ttr_main);
        this.myBoard = (TTRSurfaceView) mainActivity.findViewById(R.id.GameBoard);
        soundArray = myActivity.sounds;

        //Initialize the widget reference member variables
        this.cpu1PlayerTextView = (TextView) myActivity.findViewById(R.id.CPU1Title);
        this.cpu2PlayerTextView = (TextView) myActivity.findViewById(R.id.CPU2Title);
        this.cpu3PlayerTextView = (TextView) myActivity.findViewById(R.id.CPU3Title);
        this.humanTextView = (TextView) myActivity.findViewById(R.id.playerName);
        this.cpu1ScoreTextview = (TextView) myActivity.findViewById(R.id.CPU1Score);
        this.cpu2ScoreTextview = (TextView) myActivity.findViewById(R.id.CPU2Score);
        this.cpu3ScoreTextview = (TextView) myActivity.findViewById(R.id.CPU3Score);
        this.humanScoreTextview = (TextView) myActivity.findViewById(R.id.playerScore);
        this.cpu1TrainTokenTextView = (TextView) myActivity.findViewById(R.id.CPU1TrainCount);
        this.cpu2TrainTokenTextView = (TextView) myActivity.findViewById(R.id.CPU2TrainCount);
        this.cpu3TrainTokenTextView = (TextView) myActivity.findViewById(R.id.CPU3TrainCount);
        this.humanTrainTokenTextView = (TextView) myActivity.findViewById(R.id.playerTrainCount);
        this.cpu1DestinationCardTextView = (TextView) myActivity.findViewById(R.id.CPU1DestinationCardCount);
        this.cpu2DestinationCardTextView = (TextView) myActivity.findViewById(R.id.CPU2DestinationCardCount);
        this.cpu3DestinationCardTextView = (TextView) myActivity.findViewById(R.id.CPU3DestinationCardCount);
        this.playerDestinationCardTextView = (TextView) myActivity.findViewById(R.id.playerDestinationCardCount);
        this.playerTurnTextView = (TextView) myActivity.findViewById(R.id.Turn_View);
        this.cpu1TrainCardTextView = (TextView) myActivity.findViewById(R.id.CPU1TrainCardCount);
        this.cpu2TrainCardTextView = (TextView) myActivity.findViewById(R.id.CPU2TrainCardCount);
        this.cpu3TrainCardTextView = (TextView) myActivity.findViewById(R.id.CPU3TrainCardCount);
        this.cpu1FrameLayout = (FrameLayout) myActivity.findViewById(R.id.CPU1);
        this.cpu2FrameLayout = (FrameLayout) myActivity.findViewById(R.id.CPU2);
        this.cpu3FrameLayout = (FrameLayout) myActivity.findViewById(R.id.CPU3);

        this.redColorCount = (TextView) myActivity.findViewById(R.id.redCardCount);
        this.orangeColorCount = (TextView) myActivity.findViewById(R.id.orangeCardCount);
        this.yellowColorCount = (TextView) myActivity.findViewById(R.id.yellowCardCount);
        this.greenColorCount = (TextView) myActivity.findViewById(R.id.greenCardCount);
        this.blueColorCount = (TextView) myActivity.findViewById(R.id.blueCardCount);
        this.greenColorCount = (TextView) myActivity.findViewById(R.id.greenCardCount);
        this.pinkColorCount = (TextView) myActivity.findViewById(R.id.pinkCardCount);
        this.blackColorCount = (TextView) myActivity.findViewById(R.id.blackCardCount);
        this.whiteColorCount = (TextView) myActivity.findViewById(R.id.whiteCardCount);
        this.rainbowColorCount = (TextView) myActivity.findViewById(R.id.locomotiveCardCount);
        this.confirmSelection = (Button) myActivity.findViewById(R.id.confirmSelection);

        faceUpTrainCards[0] = (ImageButton) myActivity.findViewById(R.id.Train1);
        faceUpTrainCards[1] = (ImageButton) myActivity.findViewById(R.id.Train2);
        faceUpTrainCards[2] = (ImageButton) myActivity.findViewById(R.id.Train3);
        faceUpTrainCards[3] = (ImageButton) myActivity.findViewById(R.id.Train4);
        faceUpTrainCards[4] = (ImageButton) myActivity.findViewById(R.id.Train5);

        this.clickTrain = (ImageButton) myActivity.findViewById(R.id.DrawTrainStack);
        this.clickDestination = (ImageButton) myActivity.findViewById(R.id.DrawTicketStack);
        this.cardCheck = (CheckBox) myActivity.findViewById(R.id.drawCardCheckBox);
        this.trainCheck = (CheckBox) myActivity.findViewById(R.id.drawTrainCheckBox);

        this.confirmSelection.setOnClickListener(this);
        this.clickTrain.setOnClickListener(this);

        faceUpTrainCards[0].setOnClickListener(this);
        faceUpTrainCards[1].setOnClickListener(this);
        faceUpTrainCards[2].setOnClickListener(this);
        faceUpTrainCards[3].setOnClickListener(this);
        faceUpTrainCards[4].setOnClickListener(this);

        this.clickDestination.setOnClickListener(this);
        this.cardCheck.setOnClickListener(this);
        this.trainCheck.setOnClickListener(this);
        this.myBoard.setOnTouchListener(this);

        viewPlayerDestinationCards = (Button) myActivity.findViewById(R.id.viewDestinationCards);
        viewPlayerDestinationCards.setOnClickListener(this);

        //easter egg sound bits (see external citation located in GameMainActivity for SoundPool
        // initialization)
        /*
         *  External Citation
         *      Date: 23 April 2016
         *      Problem: could not get sound to play/be initialized
         *
         *      Resource:
         *      http://stackoverflow.com/questions/9656853/the-correct-way-to-
         *      play-short-sounds-android
         *      Solution: I used the code from this post with some modifications.  It is a similar
         *      code used in Nick Scacciotti's PongApp program.
         */
        if(name.equals("his name is")){
            soundArray.play(4, myActivity.leftVolume - .2f,
                    myActivity.rightVolume - .2f, 1, 0, 1.0f);
        } else if (name.equals("father")){
            soundArray.play(5, myActivity.leftVolume - .2f,
                    myActivity.rightVolume - .2f, 1, 0, 1.0f);
        } else if (name.equals("Ozzy")){
            soundArray.play(6, myActivity.leftVolume - .7f,
                    myActivity.rightVolume - .7f, 1, 0, 1.0f);
        } else if (name.equals("MAUL")){
            soundArray.play(7, myActivity.leftVolume - .2f,
                    myActivity.rightVolume - .2f, 1, 0, 1.0f);
        } else if (name.equals("Nux")){
            soundArray.play(8, myActivity.leftVolume + 0.7f, myActivity.rightVolume + 0.7f, 1, 0, 1.0f);
        }
    }

    /**
     * The click handler for the buttons.
     */
    @Override
    public void onClick(View v) {
        Random rand = new Random();
        if(myState.getPlayerID() == this.playerNum) {
            if (v.getId() == R.id.confirmSelection) {
                if (myState.getPlayerID() == this.playerNum) {
                    final TTRHumanPlayer me = this;
                    if (myState.getDestinationCardsSelected()) {

                        Deck tempDeck = new Deck("temp");
                        myState.getDestinationCards().moveTopCardTo(tempDeck, myState.getDestinationCards());
                        myState.getDestinationCards().moveTopCardTo(tempDeck, myState.getDestinationCards());
                        myState.getDestinationCards().moveTopCardTo(tempDeck, myState.getDestinationCards());

                        displayDestinationPopup(tempDeck, false);
                    } else if (myState.getTrackSpot() != -1 &&
                            myState.getTrackModeSelected() &&
                            myState.getTracks().get(myState.getTrackSpot()).getTrackColor().equals("Gray")) {
                        Deck tempDeck = myState.getPlayerTrainDecks()[playerNum];
                        displayCardSelectionPopup(tempDeck, myState.getTracks().get(myState.getTrackSpot()));
                        soundArray.play(rand.nextInt(3) + 1, myActivity.leftVolume - .2f,
                                myActivity.rightVolume - .2f, 2, 0, 1.0f);
                        soundArray.autoResume();
                    } else if (myState.getTrackSpot() != -1 &&
                            myState.getTrackModeSelected() &&
                            myState.getTrainColorCount("Rainbow", 0) != 0) {
                        Deck tempDeck = myState.getPlayerTrainDecks()[playerNum];
                        if(!myState.getTracks2().get(myState.getTrackSpot()).getTrackColor().equals("Blank")) {
                            if(selected2[myState.getTrackSpot()]){
                                displayLocomotiveSelectionPopup(tempDeck, myState.getTracks2().get(myState.getTrackSpot()));
                            }
                            else {
                                displayLocomotiveSelectionPopup(tempDeck, myState.getTracks().get(myState.getTrackSpot()));
                            }
                        }
                        else{
                            displayLocomotiveSelectionPopup(tempDeck, myState.getTracks().get(myState.getTrackSpot()));
                        }
                        soundArray.play(rand.nextInt(3)+1, myActivity.leftVolume - .2f,
                                myActivity.rightVolume - .2f, 1, 0, 1.0f);

                    } else {
                        game.sendAction(new ConfirmSelectionAction(me, myState.getSelectedTrackColor(), 0));
                    }
                }
            } else if (v.getId() == R.id.Train1) {
                game.sendAction(new DrawUpCardAction(this, 0));
            } else if (v.getId() == R.id.Train2) {
                game.sendAction(new DrawUpCardAction(this, 1));
            } else if (v.getId() == R.id.Train3) {
                game.sendAction(new DrawUpCardAction(this, 2));
            } else if (v.getId() == R.id.Train4) {
                game.sendAction(new DrawUpCardAction(this, 3));
            } else if (v.getId() == R.id.Train5) {
                game.sendAction(new DrawUpCardAction(this, 4));
            } else if (v.getId() == R.id.DrawTrainStack) {
                game.sendAction(new DrawDownCardAction(this));
            } else if (v.getId() == R.id.DrawTicketStack) {
                game.sendAction(new DrawDestinationCardAction(this));
            } else if (v.getId() == R.id.drawCardCheckBox) {
                if (myState.getTrackModeSelected() && !myState.getCardModeSelected()) {
                    game.sendAction(new ChangeModeAction(this));
                } else {
                    this.cardCheck.setChecked(true);
                }
            } else if (v.getId() == R.id.drawTrainCheckBox) {
                if (myState.getCardModeSelected() && !myState.getTrackModeSelected()) {
                    game.sendAction(new ChangeModeAction(this));
                } else {
                    this.trainCheck.setChecked(true);
                }
            } else if (v.getId() == R.id.viewDestinationCards) {
                Deck tempDeck = myState.getPlayerDestinationDecks()[playerNum];
                displayDestinationViewDialog(tempDeck);
            }
        }
    }

    /**
     * The touch handler for the touches.
     *
     * @param v     reference to surface view
     * @param event event on surface view
     * @return boolean
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(myState.getPlayerID() == this.playerNum) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            if (v.getId() == R.id.GameBoard && event.getAction() == MotionEvent.ACTION_DOWN) {
                int index = myBoard.clickedTrack(x, y - 191);
                String colorString = null;
                if (index != -1) {
                    if ((highlights[index] || highlights2[index]) &&
                            myState.getTrainTokens()[this.playerNum] >=
                                    myState.getTracks().get(index).getTrainTrackNum()) {
                        if (!highlights[index] && selected2[index]) {
                            index = -1;
                        } else if (!highlights2[index] && selected[index]) {
                            index = -1;
                        }
                        else{
                            if (canChoose(myState.getTracks().get(index)) && !covered[index]) {
                                colorString = myState.getTracks().get(index).getTrackColor();
                            } else if (canChoose(myState.getTracks2().get(index)) && !covered2[index]) {
                                colorString = myState.getTracks2().get(index).getTrackColor();
                            }
                        }
                    }
                    else {
                        index = -1;
                    }
                    game.sendAction(new TrackPlaceAction(this, colorString, index));
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Pop up for selecting destination cards
     *
     * @param tempDeck reference to the destination deck
     */
    public void displayDestinationPopup(Deck tempDeck, boolean value) {
        DestinationSelectionDialog dsd = new DestinationSelectionDialog(this, value, tempDeck, game);
        dsd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dsd.show();
    }

    /**
     * Pop up for selecting destination cards
     *
     * @param tempDeck reference to the destination deck
     */
    public void displayDestinationViewDialog(Deck tempDeck) {
        DestinationViewDialog dvd = new DestinationViewDialog(this, tempDeck, game);
        dvd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dvd.show();
    }

    /**
     * Pop-up for gray track
     *
     * @param tempDeck reference to player deck
     * @param track    reference to current track
     */
    public void displayCardSelectionPopup(Deck tempDeck, Track track) {
        CardColorSelectionDialog ccsd = new CardColorSelectionDialog(myActivity, tempDeck, myState,
                track, game, this);
        ccsd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ccsd.show();
    }

    /**
     * Pop-up that asks user if they want to use a locomotive card
     *
     * @param tempDeck reference to player deck
     * @param track    reference to current track
     */
    public void displayLocomotiveSelectionPopup(Deck tempDeck, Track track) {
        LocomotiveSelectionDialog lsd = new LocomotiveSelectionDialog(myActivity, tempDeck, myState,
                track, game, this);
        lsd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lsd.show();
    }
}
