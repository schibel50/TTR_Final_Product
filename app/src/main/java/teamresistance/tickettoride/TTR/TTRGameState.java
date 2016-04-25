package teamresistance.tickettoride.TTR;

import java.io.Serializable;
import java.util.ArrayList;
import teamresistance.tickettoride.Game.infoMsg.GameState;


/**
 *  TTRGameState creates the GameState
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */
public class TTRGameState extends GameState implements Serializable{

    private static final long serialVersionUID = 388245678192016L;
    private Track tempTrack;
    private Track blankTrack = new Track(-1, "Blank", "N/A", "N/A");
    private ArrayList<Track> trackSet1 = new ArrayList<Track>();
    private ArrayList<Track> trackSet2 = new ArrayList<Track>();

    int MAX_NUM_PLAYERS = 4;

    /** Decks Used by the Game */
    private Deck faceDownTrainCards;
    private Deck faceUpTrainCards;
    private Deck destinationCards;
    private Deck destinationPool;
    private Deck trainDiscard;
    private Deck destinationDiscard;
    private Deck destinationCardsDrawn;

    /** Which player's turn it is */
    private int playerID;
    /** The number of players playing the game */
    private int numPlayers = MAX_NUM_PLAYERS;
    /** Says whether or not the player is in track select mode */
    private boolean trackModeSelected;
    /** Says whether or not the player is in card select mode */
    private boolean cardModeSelected;
    /** Says if the destination card pile has been clicked */
    private boolean destinationCardsSelected;
    /** Says if either the face down or face up decks have been selected */
    private boolean trainCardsSelected;
    /** Says if the player has clicked on a placeable track */
    private boolean placeTrainSelected;
    /** If the down deck was clicked on and no face up card was selected, this boolean will say to just pull two cards from the down deck */
    private boolean onlyDownDeck;
    /** The selected track on screen. Set to -1 if no track is selected. */
    private int trackSpot = -1;
    /** the currently selected track color*/
    private String selectedTrackColor;
    private boolean useRainbow; //says whether to use rainbow cards or not.
    private boolean gameStart; //says if the game has started.
    private boolean reset; //checks to see if some of the states need to be reset at the start of a turn
    private boolean isLastRound = false; //indicates the start of the final round
    private boolean isGameOver = false; //indicates if the game is over

    /**
     * All of these boolean arrays are used in order to ensure that the correct information is passed
     * to the surface view. Particularly important for network play.
     */
    private boolean[] faceUpCardsHighlight = new boolean[5];
    private boolean[] selectedTracks;
    private boolean[] coveredTracks;
    private int[] trackIds;
    private boolean[] selectedTracks2;
    private boolean[] coveredTracks2;
    private int[] trackIds2;

    //PARALLEL ARRAYS//
    private int[] trainTokens = new int[numPlayers];
    private int[] scores = new int[numPlayers];
    private String[] names = new String[numPlayers];
    private Deck[] playerTrainDecks = new Deck[numPlayers];
    private Deck[] playerDestinationDecks = new Deck[numPlayers];
    private int numRainbows;

    /*
     * Initializes a new GameState
     */
    public TTRGameState() {
        /** initialize train deck */
        faceDownTrainCards = new Deck("Face Down Deck");
        for (int i = 0; i < 9; i++){
            if(i == 8){ // "Yellow", "Blue", "Orange", "White", "Pink", "Black", "Red", "Green",
                for(int j = 0; j < 14; j++){
                    faceDownTrainCards.add(new TrainCards(i));
                }
            }
            else { //"Rainbow"
                for (int k = 0; k < 12; k++){
                    faceDownTrainCards.add(new TrainCards(i));
                }
            }

        }
        //shuffles newly created deck
        faceDownTrainCards.shuffle();
        faceUpTrainCards = new Deck("Face up Cards");
        //place five train cards 'face up'
        for(int i = 0; i < 5; i++){
            faceDownTrainCards.moveTopCardTo(faceUpTrainCards, faceDownTrainCards);
            faceUpCardsHighlight[i] = false;
        }

        /** initialize destination deck */
        //needs to be corrected later
        destinationCards = new Deck("Destination Cards");
        destinationDiscard = new Deck("Destination Discard");
        for (int i = 0; i < 30; i++) {
            destinationCards.add(new DestinationCards(i, i, i));
        }
        destinationCards.shuffle();
        /** initialize array values to max possible size */
        for (int i = 0; i < numPlayers; i++){
            trainTokens[i] = 45;
            int k = 0;
            scores[i] = 0;
            names[i] = "";
            playerTrainDecks[i] = new Deck("Player " + i + " Train Card Deck");
            //places the top 5 cards of the face down deck into the current players train card deck
            while (k != 4) {
                //checks to make sure that player does not get a locomotive card
                if (!faceDownTrainCards.peekAtTopCard().toString().equals("Rainbow")) {
                    faceDownTrainCards.moveTopCardTo(playerTrainDecks[i], faceDownTrainCards);
                    k++;
                } else
                {
                    faceDownTrainCards.shuffle();
                }
            }
            //places 3 destination cards into the current players destination deck
            playerDestinationDecks[i] = new Deck("Player " + i + " Destination Card Deck");
        }
        selectedTrackColor = null;
        trainDiscard = new Deck("Train Card Discard");
        destinationCardsDrawn = new Deck("Destination Cards Drawn");
        destinationPool = new Deck("Destination Card Pool");
        numRainbows = 0;

        //Booleans set to defaults
        trackModeSelected = false;
        cardModeSelected = false;
        destinationCardsSelected = false;
        trainCardsSelected = false;
        useRainbow = false;
        trackModeSelected = false;
        cardModeSelected = false;
        destinationCardsSelected = true;
        trainCardsSelected = false;
        placeTrainSelected = false;
        onlyDownDeck = false;
        gameStart = false;
        reset = false;

        //tracks and the cities they're connected to.

        //0
        tempTrack= new Track(5, "Green", "Portland", "San Francisco");
        trackSet1.add(tempTrack);
        tempTrack = new Track(5, "Pink", "Portland", "San Francisco");
        trackSet2.add(tempTrack);

        //1
        tempTrack= new Track(3, "Yellow", "San Francisco", "Los Angeles");
        trackSet1.add(tempTrack);
        tempTrack = new Track(3, "Pink", "San Francisco", "Los Angeles");
        trackSet2.add(tempTrack);

        //2
        tempTrack= new Track(2, "Gray", "Los Angeles", "Las Vegas");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //3
        tempTrack= new Track(2, "Gray", "Montreal", "Boston");
        trackSet1.add(tempTrack);
        tempTrack= new Track(2, "Gray", "Montreal", "Boston");
        trackSet2.add(tempTrack);

        //4
        tempTrack= new Track(3, "Blue", "Montreal", "New York");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //5
        tempTrack = new Track(2, "Yellow", "New York", "Boston");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Red", "New York", "Boston");
        trackSet2.add(tempTrack);

        //6
        tempTrack = new Track(2, "Orange", "New York", "Washington");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Black", "New York", "Washington");
        trackSet2.add(tempTrack);

        //7
        tempTrack = new Track(2, "Gray", "Raleigh", "Washington");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Gray", "Raleigh", "Washington");
        trackSet2.add(tempTrack);

        //8
        tempTrack = new Track(2, "Gray", "Raleigh", "Charleston");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //9
        tempTrack = new Track(4, "Pink", "Miami", "Charleston");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //10
        tempTrack = new Track(5, "Blue", "Atlanta", "Miami");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //11
        tempTrack = new Track(2, "Gray", "Atlanta", "Charleston");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //12
        tempTrack = new Track(2, "Gray", "Raleigh", "Atlanta");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Gray", "Raleigh", "Atlanta");
        trackSet2.add(tempTrack);

        //13
        tempTrack = new Track(2, "Gray", "Pittsburgh", "Raleigh");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //14
        tempTrack = new Track(2, "Gray", "Pittsburgh", "Washington");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //15
        tempTrack = new Track(2, "Green", "Pittsburgh", "New York");
        trackSet2.add(tempTrack);
        tempTrack = new Track(2, "White", "Pittsburgh", "New York");
        trackSet1.add(tempTrack);

        //16
        tempTrack = new Track(2, "Gray", "Toronto", "Pittsburgh");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //17
        tempTrack = new Track(3, "Gray", "Toronto", "Montreal");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //18
        tempTrack = new Track(5, "Black", "Sault Ste Marie", "Montreal");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //19
        tempTrack = new Track(6, "Red", "New Orleans", "Miami");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //20
        tempTrack = new Track(6, "Black", "Los Angeles", "El Paso");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //21
        tempTrack = new Track(1, "Gray", "Vancouver", "Seattle");
        trackSet2.add(tempTrack);
        tempTrack = new Track(1, "Gray", "Vancouver", "Seattle");
        trackSet1.add(tempTrack);

        //22
        tempTrack = new Track(1, "Gray", "Seattle", "Portland");
        trackSet2.add(tempTrack);
        tempTrack = new Track(1, "Gray", "Seattle", "Portland");
        trackSet1.add(tempTrack);

        //23
        tempTrack = new Track(3, "Gray", "Vancouver", "Calgary");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //24
        tempTrack = new Track(4, "Gray", "Seattle", "Calgary");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //25
        tempTrack = new Track(3, "Gray", "Los Angeles", "Phoenix");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //26
        tempTrack = new Track(6, "Yellow", "Seattle", "Helena");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //27
        tempTrack = new Track(6, "Blue", "Portland", "Salt Lake City");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //28
        tempTrack = new Track(5, "White", "San Francisco", "Salt Lake City");
        trackSet2.add(tempTrack);
        tempTrack = new Track(5, "Orange", "San Francisco", "Salt Lake City");
        trackSet1.add(tempTrack);

        //29
        tempTrack = new Track(3, "Orange", "Las Vegas", "Salt Lake City");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //30
        tempTrack = new Track(6, "White", "Calgary", "Winnipeg");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //31
        tempTrack = new Track(4, "Gray", "Calgary", "Helena");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //32
        tempTrack = new Track(3, "Pink", "Helena", "Salt Lake City");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //33
        tempTrack = new Track(3, "Red", "Salt Lake City", "Denver");
        trackSet1.add(tempTrack);
        tempTrack = new Track(3, "Yellow", "Salt Lake City", "Denver");
        trackSet2.add(tempTrack);

        //34
        tempTrack = new Track(5, "White", "Phoenix", "Denver");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //35
        tempTrack = new Track(3, "Gray", "Phoenix", "Santa Fe");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //36
        tempTrack = new Track(3, "Gray", "Phoenix", "El Paso");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //37
        tempTrack = new Track(4, "Blue", "Helena", "Winnipeg");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //38
        tempTrack = new Track(4, "Green", "Helena", "Denver");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //39
        tempTrack = new Track(2, "Gray", "Denver", "Santa Fe");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //40
        tempTrack = new Track(2, "Gray", "Santa Fe", "El Paso");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //41
        tempTrack = new Track(6, "Orange", "Helena", "Duluth");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //42
        tempTrack = new Track(4, "Orange", "New Orleans", "Atlanta");
        trackSet2.add(tempTrack);
        tempTrack = new Track(4, "Yellow", "New Orleans", "Atlanta");
        trackSet1.add(tempTrack);

        //43
        tempTrack = new Track(1, "Gray", "Nashville", "Atlanta");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //44
        tempTrack = new Track(3, "Black", "Nashville", "Raleigh");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //45
        tempTrack = new Track(4, "Yellow", "Nashville", "Pittsburgh");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //46
        tempTrack = new Track(5, "Green", "Saint Louis", "Pittsburgh");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //47
        tempTrack = new Track(3, "Orange", "Chicago", "Pittsburgh");
        trackSet1.add(tempTrack);
        tempTrack = new Track(3, "Black", "Chicago", "Pittsburgh");
        trackSet2.add(tempTrack);

        //48
        tempTrack = new Track(4, "White", "Chicago", "Toronto");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //49
        tempTrack = new Track(2, "Gray", "Sault Ste Marie", "Toronto");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //50
        tempTrack = new Track(5, "Red", "Helena", "Omaha");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //51
        tempTrack = new Track(4, "Pink", "Denver", "Omaha");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //52
        tempTrack = new Track(4, "Black", "Denver", "Kansas City");
        trackSet1.add(tempTrack);
        tempTrack = new Track(4, "Orange", "Denver", "Kansas City");
        trackSet2.add(tempTrack);

        //53
        tempTrack = new Track(4, "Red", "Denver", "Oklahoma City");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //54
        tempTrack = new Track(3, "Blue", "Santa Fe", "Oklahoma City");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //55
        tempTrack = new Track(5, "Yellow", "El Paso", "Oklahoma City");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //56
        tempTrack = new Track(2, "Gray", "Saint Louis", "Nashville");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //57
        tempTrack = new Track(3, "White", "Little Rock", "Nashville");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //58
        tempTrack = new Track(3, "Green", "Little Rock", "New Orleans");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //59
        tempTrack = new Track(2, "Gray", "Houston", "New Orleans");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //60
        tempTrack = new Track(2, "Gray", "Saint Louis", "Little Rock");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //61
        tempTrack = new Track(2, "Gray", "Dallas", "Little Rock");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //62
        tempTrack = new Track(2, "Gray", "Oklahoma City", "Little Rock");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //63
        tempTrack = new Track(2, "Pink", "Kansas City", "Saint Louis");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Blue", "Kansas City", "Saint Louis");
        trackSet2.add(tempTrack);

        //64
        tempTrack = new Track(2, "Green", "Chicago", "Saint Louis");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "White", "Chicago", "Saint Louis");
        trackSet2.add(tempTrack);

        //65
        tempTrack = new Track(4, "Blue", "Omaha", "Chicago");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //66
        tempTrack = new Track(4, "Red", "El Paso", "Dallas");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //67
        tempTrack = new Track(6, "Green", "El Paso", "Houston");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //68
        tempTrack = new Track(4, "Black", "Winnipeg", "Duluth");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //69
        tempTrack = new Track(1, "Gray", "Dallas", "Houston");
        trackSet1.add(tempTrack);
        tempTrack = new Track(1, "Gray", "Dallas", "Houston");
        trackSet2.add(tempTrack);

        //70
        tempTrack = new Track(2, "Gray", "Oklahoma City", "Dallas");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Gray", "Oklahoma City", "Dallas");
        trackSet2.add(tempTrack);

        //71
        tempTrack = new Track(2, "Gray", "Kansas City", "Oklahoma City");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Gray", "Kansas City", "Oklahoma City");
        trackSet2.add(tempTrack);

        //72
        tempTrack = new Track(3, "Red", "Duluth", "Chicago");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //73
        tempTrack = new Track(6, "Pink", "Duluth", "Toronto");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //74
        tempTrack = new Track(3, "Gray", "Duluth", "Sault Ste Marie");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //75
        tempTrack = new Track(6, "Gray", "Winnipeg", "Sault Ste Marie");
        trackSet1.add(tempTrack);
        trackSet2.add(blankTrack);

        //76
        tempTrack = new Track(2, "Gray", "Duluth", "Omaha");
        trackSet1.add(tempTrack);
        tempTrack = new Track(2, "Gray", "Duluth", "Omaha");
        trackSet2.add(tempTrack);

        //77
        tempTrack = new Track(1, "Gray", "Omaha", "Kansas City");
        trackSet1.add(tempTrack);
        tempTrack = new Track(1, "Gray", "Omaha", "Kansas City");
        trackSet2.add(tempTrack);

        selectedTracks = new boolean[trackSet1.size()];
        coveredTracks = new boolean[trackSet1.size()];
        trackIds = new int[trackSet1.size()];
        selectedTracks2 = new boolean[trackSet1.size()];
        coveredTracks2 = new boolean[trackSet1.size()];
        trackIds2 = new int[trackSet1.size()];
        for(int i = 0; i < trackSet1.size(); i++){
            selectedTracks[i] = false;
            coveredTracks[i] = false;
            trackIds[i] = -1;
            selectedTracks2[i] = false;
            coveredTracks2[i] = false;
            trackIds2[i] = -1;
        }
    }
    /*
     * Creates a deep copy of the GameState
     * @original
     */
    public TTRGameState(TTRGameState original) {
        numPlayers = original.getNumPlayers();
        playerID = original.getPlayerID();
        faceDownTrainCards = new Deck(original.faceDownTrainCards);
        faceUpTrainCards = new Deck(original.faceUpTrainCards);
        for(int i = 0; i < original.getFaceUpCardsHighlight().length; i++){
            faceUpCardsHighlight[i] = original.getFaceUpCardsHighlight()[i];
        }
        destinationCards = new Deck(original.destinationCards);
        trainDiscard = new Deck(original.trainDiscard);
        destinationDiscard = new Deck(original.destinationDiscard);

        for(int i = 0; i < original.getNumPlayers(); i++){
            trainTokens[i] = original.getTrainTokens()[i];
            scores[i] = original.getScores()[i];
            names[i] = original.getNames()[i];
            playerTrainDecks[i] = new Deck(original.getPlayerTrainDecks()[i]);
            playerDestinationDecks[i] = new Deck(original.getPlayerDestinationDecks()[i]);
        }

        trackSet1 = original.getTracks();
        trackSet2 = original.getTracks2();

        trackSpot = original.getTrackSpot();
        numRainbows = original.getNumRainbows();
        selectedTrackColor = original.getSelectedTrackColor();
        trackModeSelected = original.getTrackModeSelected();
        cardModeSelected = original.getCardModeSelected();
        destinationCardsSelected = original.getDestinationCardsSelected();
        trainCardsSelected = original.getTrainCardsSelected();
        onlyDownDeck = original.getOnlyDownDeck();
        useRainbow = original.getUseRainbow();
        gameStart = original.getGameStart();
        setNames(original.getNames());
        selectedTracks = new boolean[trackSet1.size()];
        coveredTracks = new boolean[trackSet1.size()];
        trackIds = new int[trackSet1.size()];
        selectedTracks2 = new boolean[trackSet2.size()];
        coveredTracks2 = new boolean[trackSet2.size()];
        trackIds2 = new int[trackSet2.size()];
        for(int i = 0; i < trackSet1.size(); i++){
            selectedTracks[i] = trackSet1.get(i).getSelected();
            coveredTracks[i] = trackSet1.get(i).getCovered();
            trackIds[i] = trackSet1.get(i).getPlayerID();
            selectedTracks2[i] = trackSet2.get(i).getSelected();
            coveredTracks2[i] = trackSet2.get(i).getCovered();
            trackIds2[i] = trackSet2.get(i).getPlayerID();
        }
        if(gameStart){
            cardModeSelected = original.getCardModeSelected();
            trackModeSelected = original.getTrackModeSelected();
        }
        else{
            cardModeSelected = false;
            trackModeSelected = false;
        }
    }

    public int getTrackPosition(String city1, String city2){
        int position = -1;
        for(int i = 0; i < trackSet1.size(); i++){
            if(city1.equals(trackSet1.get(i).getStartCity()) && city2.equals(trackSet1.get(i).getEndCity())){
                position = i;
            }
        }
        return position;
    }

    public Deck getFaceDownTrainCards() {
        return faceDownTrainCards;
    }

    public void setFaceDownTrainCards(Deck faceDownTrainCards) {
        this.faceDownTrainCards = faceDownTrainCards;
    }

    public Deck getFaceUpTrainCards() {
        return faceUpTrainCards;
    }

    public void setFaceUpTrainCards(Deck faceUpTrainCards) {
        this.faceUpTrainCards = faceUpTrainCards;
    }

    public Deck getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(Deck destinationCards) {
        this.destinationCards = destinationCards;
    }

    public String[] getNames() {
        return names;
    }

    /**
     * Sets the names
     * @param names string array of the names
     */
    public void setNames(String[] names) {
        String[] temp = new String[MAX_NUM_PLAYERS];
        for(int i = 0; i < MAX_NUM_PLAYERS; i ++){
            temp[i] = names[i];
        }
        this.names = temp;
    }

    public Deck getTrainDiscard() {
        return trainDiscard;
    }

    public void setTrainDiscard(Deck trainDiscard) {
        this.trainDiscard = trainDiscard;
    }

    public Deck getDestinationPool() {
        return destinationPool;
    }

    public void setDestinationPool(Deck destinationPool) {
        this.destinationPool = destinationPool;
    }

    public Deck getDestinationDiscard() {
        return destinationDiscard;
    }

    public void setDestinationDiscard(Deck destinationDiscard) {
        this.destinationDiscard = destinationDiscard;
    }

    public Deck getDestinationCardsDrawn() {
        return destinationCardsDrawn;
    }

    public void setDestinationCardsDrawn(Deck destinationCardsDrawn) {
        this.destinationCardsDrawn = destinationCardsDrawn;
    }

    public Deck[] getPlayerTrainDecks() {
        return playerTrainDecks;
    }

    public void setPlayerTrainDecks(Deck[] playerTrainDecks) {
        this.playerTrainDecks = playerTrainDecks;
    }

    public Deck[] getPlayerDestinationDecks() {
        return playerDestinationDecks;
    }

    public void setPlayerDestinationDecks(Deck[] playerDestinationDecks) {
        this.playerDestinationDecks = playerDestinationDecks;
    }

    /**
     * Returns the number of a particular card type in a players hand
     * @param color string reference to train card type
     * @param index int reference to current player
     * @return int of card type
     */
    public int getTrainColorCount(String color, int index){
        int count = 0;
        if(index != -1) {
            for (int i = 0; i < playerTrainDecks[index].size(); i++) {
                if (playerTrainDecks[index].getCards().get(i).toString().equals(color)) {
                    count++;
                }
            }
        }
        else{
            for(int i = 0; i< faceUpTrainCards.size(); i++){
                if (faceUpTrainCards.getCards().get(i).toString().equals(color)){
                    count++;
                }
            }
        }
        return count;
    }
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScore(int score, int index) {
        this.scores[index] = score;
    }

    public Boolean getTrackModeSelected() {
        return trackModeSelected;
    }

    public void setTrackModeSelected(Boolean trackModeSelected) {
        this.trackModeSelected = trackModeSelected;
    }

    public Boolean getCardModeSelected() {
        return cardModeSelected;
    }

    public void setCardModeSelected(Boolean cardModeSelected) {
        this.cardModeSelected = cardModeSelected;
    }

    public Boolean getDestinationCardsSelected() {
        return destinationCardsSelected;
    }

    public void setDestinationCardsSelected(Boolean destinationCardsSelected) {
        this.destinationCardsSelected = destinationCardsSelected;
    }

    public Boolean getTrainCardsSelected() {
        return trainCardsSelected;
    }

    public void setTrainCardsSelected(Boolean trainCardsSelected) {
        this.trainCardsSelected = trainCardsSelected;
    }

    public Boolean getPlaceTrainSelected() {
        return placeTrainSelected;
    }

    public void setPlaceTrainSelected(Boolean placeTrainSelected) {
        this.placeTrainSelected = placeTrainSelected;
    }

    public boolean getOnlyDownDeck() {
        return onlyDownDeck;
    }

    public void setOnlyDownDeck(Boolean onlyDownDeck) {
        this.onlyDownDeck = onlyDownDeck;
    }

    public ArrayList<Track> getTracks() { return trackSet1; }

    public ArrayList<Track> getTracks2(){ return trackSet2; }

    public void setTracks(ArrayList<Track> tracks) {
        this.trackSet1 = tracks;
    }

    public int[] getTrainTokens() {
        return trainTokens;
    }

    public void setTrainToken(int trainToken, int index) {
        this.trainTokens[index] = trainToken;
    }

    public int getTrackSpot() {
        return trackSpot;
    }

    public void setTrackSpot(int trackSpot) {
        this.trackSpot = trackSpot;
    }

    public Boolean getIsLastRound() {
        return isLastRound;
    }

    public void setIsLastRound(Boolean isLastRound) {
        this.isLastRound = isLastRound;
    }

    public Boolean getIsGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(Boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public String getSelectedTrackColor(){
        return selectedTrackColor;
    }

    public void setSelectedTrackColor(String value){
        this.selectedTrackColor = value;
    }

    public boolean getUseRainbow(){
        return useRainbow;
    }

    public void setUseRainbow(boolean value){
        this.useRainbow = value;
    }

    public int getNumRainbows() {
        return numRainbows;
    }

    public void setNumRainbows(int value) {
        this.numRainbows = value;
    }

    public boolean getGameStart(){ return gameStart; }

    public void setGameStart(boolean val){ gameStart = val; }

    public boolean getReset(){ return reset; }

    public void setReset(boolean reset){ this.reset = reset; }

    public boolean[] getFaceUpCardsHighlight() {
        return faceUpCardsHighlight;
    }
    public int[] getTrackIds() {
        return trackIds;
    }
    public boolean[] getCoveredTracks() {
        return coveredTracks;
    }
    public boolean[] getSelectedTracks() {
        return selectedTracks;
    }
    public int[] getTrackIds2() {
        return trackIds2;
    }
    public boolean[] getCoveredTracks2() {
        return coveredTracks2;
    }
    public boolean[] getSelectedTracks2() {
        return selectedTracks2;
    }

}