package teamresistance.tickettoride.TTR;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import teamresistance.tickettoride.Game.GamePlayer;
import teamresistance.tickettoride.Game.LocalGame;
import teamresistance.tickettoride.Game.actionMsg.GameAction;
import teamresistance.tickettoride.TTR.Actions.ChangeModeAction;
import teamresistance.tickettoride.TTR.Actions.ConfirmSelectionAction;
import teamresistance.tickettoride.TTR.Actions.DrawDestinationCardAction;
import teamresistance.tickettoride.TTR.Actions.DrawDownCardAction;
import teamresistance.tickettoride.TTR.Actions.DrawUpCardAction;
import teamresistance.tickettoride.TTR.Actions.TrackPlaceAction;
import teamresistance.tickettoride.TTR.DijkstraAlg.Dijkstra;
import teamresistance.tickettoride.TTR.DijkstraAlg.DijkstraGraph;
import teamresistance.tickettoride.TTR.DijkstraAlg.Edge;
import teamresistance.tickettoride.TTR.DijkstraAlg.Vertex;

/**
 * Controls the game, allowing actions to be performed by
 * the player with the matching ID
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */
public class TTRLocalGame extends LocalGame implements Serializable {
    private static final long serialVersionUID = 388111564192016L;
    //instance variables for the TTRLocalGame
    private TTRGameState mainState; //reference to the game state
    private boolean noMoreTrains; //boolean to indicate the start of a game over
    private int turnsLeft; //number of turns left in the game when a game over state is initiated.
    private int topScorePlayer = 0; //position in the array of players of the player with
    //the highest score
    private int numStarted;
    private String currentTrackColor = null; //the track color of the currently chosen track.
    private final ArrayList<String> destNames = new ArrayList<String>(Arrays.asList("Denver", "El Paso", "Kansas City",
            "Houston", "New York", "Atlanta", "Chicago", "New Orleans", "Calgary",
            "Salt Lake City", "Helena", "Los Angeles", "Duluth", "Sault Ste Marie",
            "Nashville", "Montreal", "Oklahoma City", "Seattle", "Santa Fe",
            "Toronto", "Miami", "Portland", "Phoenix", "Dallas",
            "Pittsburgh", "Winnipeg", "Little Rock", "Boston", "Vancouver",
            "San Francisco", "Las Vegas", "Washington", "Raleigh", "Charleston",
            "Saint Louis", "Omaha"));

    DijkstraGraph playerGraph;
    Dijkstra playerDijkstra;
    ArrayList<Edge> myEdgeList;
    ArrayList<Vertex> myVertexList;


    /**
     * TTRLocalGame constructor
     */
    public TTRLocalGame() {
        mainState = new TTRGameState();
        noMoreTrains = false;
        turnsLeft = 0;
        numStarted = 0;
    }

    /**
     * Sends updated game state
     *
     * @param p - receiving player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        TTRGameState copy = new TTRGameState(mainState);
        p.sendInfo(copy);
    }

    /**
     * Returns if the player can make a move
     *
     * @param playerIdx the player's player-number (ID)
     * @return
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == mainState.getPlayerID();
    }

    /**
     * Returns the end game status
     *
     * @return
     */
    @Override
    protected String checkIfGameOver() {
        //if the criteria for a start of the game over is met, set up the number
        //of turns left.
        for (int i = 0; i < mainState.getTrainTokens().length; i++) {
            if (mainState.getTrainTokens()[i] <= 2 && !mainState.getIsLastRound()) {
                mainState.setIsGameOver(true);
                mainState.setIsLastRound(true);
                turnsLeft = mainState.getNumPlayers();
            }
        }
        //if the start of a game over has been initiated, reduce the number of turns.
        //if the final turns are over, find and announce the winner
        if (turnsLeft == 0 && mainState.getIsGameOver()) {

            //holds the player number who has the longest track and the
            //longest evaluated distance thus far.
            int longest = 0;
            int longestDistance = 0;

            //run through all the players to see what destination cards they
            //completed and see who had the longest continuous track.
            for (int j = 0; j < this.players.length; j++) {

                //ArrayList of all the cities
                myVertexList = new ArrayList<Vertex>();
                Vertex temp = null;

                //placeHolders for the Vertexes to be added to edges
                Vertex startCity = null;
                Vertex endCity = null;

                //add all of the cities to the Vertex list
                for (int l = 0; l < destNames.size(); l++) {
                    temp = new Vertex(destNames.get(l), l);
                    myVertexList.add(temp);
                }

                //create an arrayList of Edges that correspond to the
                //tracks owned by the player.
                myEdgeList = new ArrayList<Edge>();
                for (int i = 0; i < mainState.getTracks().size(); i++) {

                    //used to create the correct Edge
                    String city1 = mainState.getTracks().get(i).getStartCity();
                    String city2 = mainState.getTracks().get(i).getEndCity();

                    //run through every Vertex on the map and get only the Edges
                    //that the player owns
                    for (Vertex vert : myVertexList) {
                        int spot = vert.getId();
                        if (city1.equals(vert.getName())
                                && (mainState.getTracks().get(i).getPlayerID() == j
                                || mainState.getTracks2().get(i).getPlayerID() == j)
                                && startCity == null) {
                            startCity = vert;
                        } else if (city2.equals(vert.getName())
                                && (mainState.getTracks().get(i).getPlayerID() == j
                                || mainState.getTracks2().get(i).getPlayerID() == j)
                                && endCity == null) {
                            endCity = vert;
                        }
                    }

                    //if both Vertexes for an Edge were found, add it to the list of Edges
                    Edge temporary = null;
                    if (startCity != null && endCity != null) {
                        temporary = new Edge(mainState.getTracks().get(i), startCity, endCity,
                                mainState.getTracks().get(i).getTrainTrackNum());
                    }
                    startCity = null;
                    endCity = null;
                    if (!myEdgeList.contains(temporary) && temporary != null) {
                        myEdgeList.add(temporary);
                    }
                }

                //Create a Graph and Dijkstra using the cities on the map and all of
                //the players claimed tracks.
                playerGraph = new DijkstraGraph(myVertexList, myEdgeList);
                playerDijkstra = new Dijkstra(playerGraph);

                //run through all of the destination cards in the player's hand
                //to see which they completed and which they did not.
                for (int k = 0; k < mainState.getPlayerDestinationDecks()[j].getCards().size(); k++) {
                    int spot1 = -1;
                    int spot2 = -1;

                    //get the current destination card being looked at and save it to a temporary variable
                    DestinationCards lookCard =
                            (DestinationCards) mainState.getPlayerDestinationDecks()[j].getCards().get(k);
                    String city1 = lookCard.getCity1();
                    String city2 = lookCard.getCity2();

                    //run through every vertex and find the ones that correspond to the current
                    //destination card.
                    for (int m = 0; m < playerGraph.getVertexes().size(); m++) {
                        if (playerGraph.getVertexes().get(m).getName().equals(city1)) {
                            spot1 = m;
                        } else if (playerGraph.getVertexes().get(m).getName().equals(city2)) {
                            spot2 = m;
                        }
                    }

                    //perform dijkstra on the first city on the destination card.
                    playerDijkstra.dijkstra(spot1);

                    //only enter if the player has any tracks
                    if (!playerDijkstra.getMyGraph().getEdges().isEmpty()) {

                        //if one the spots is the source enter. Then check to see if the second
                        //city on the card still has the maximum distance value. If the distance
                        //value is still the maximum, this means the Vertex was never reached
                        //from the source.
                        if (playerDijkstra.getMyGraph().getVertexes().get(spot1).getDistance() == 0) {
                            if (playerDijkstra.getMyGraph().getVertexes().get(spot2).getDistance() != 100000000) {
                                mainState.setScore(mainState.getScores()[j] + lookCard.getScore(), j);
                            } else {
                                mainState.setScore(mainState.getScores()[j] - lookCard.getScore(), j);
                            }
                        } else if (playerDijkstra.getMyGraph().getVertexes().get(spot2).getDistance() == 0) {
                            if (playerDijkstra.getMyGraph().getVertexes().get(spot1).getDistance() != 100000000) {
                                mainState.setScore(mainState.getScores()[j] + lookCard.getScore(), j);
                            } else {
                                mainState.setScore(mainState.getScores()[j] - lookCard.getScore(), j);
                            }
                        }
                    }else{
                        mainState.setScore(mainState.getScores()[j] - lookCard.getScore(), j);
                    }

                }

                //Get all of the Vertexes with one claimed route next to it. This means that
                //this vertex could potentially be the start of the longest continuous track.
                ArrayList<Vertex> lonelyVertex = playerDijkstra.getSingleNeighbor();

                //Run through and perform dijkstra on all of the lonely Vertexes
                for (int n = 0; n < lonelyVertex.size(); n++) {
                    playerDijkstra.dijkstra(lonelyVertex.get(n).getId());

                    //after performing dijkstra, run through every other vertex until the
                    //farthest one from the source is found. If it is the new longest track
                    //save the player who owns it and its size.
                    for (Vertex vertex : playerGraph.getVertexes()) {
                        if (vertex.getDistance() != 100000000 && vertex.getDistance() > longestDistance) {
                            longestDistance = vertex.getDistance();
                            longest = j;
                        }
                    }
                }
                //clear the list of edges and vertexes to prepare for the next player.
                myEdgeList.clear();
                myVertexList.clear();
            }

            //whichever player has the longest continuous track, give them bonus points.
            mainState.setScore(mainState.getScores()[longest] + 10, longest);

            for (int j = 0; j < this.players.length; j++) {
                if (mainState.getScores()[j] > mainState.getScores()[topScorePlayer]) {
                    topScorePlayer = j;
                }
            }
            return ("" + this.playerNames[topScorePlayer] + " wins with " + mainState.getScores()[topScorePlayer] + " points!");
        }
        return null;
    }


    /**
     * Returns if the player made a move
     *
     * @param action The move that the player has sent to the game
     * @return
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //changes the mode of the game if a check box is pressed.
        if (action instanceof ChangeModeAction) {
            //If draw cards is the selected mode, de-highlight all of the tracks on the board
            //and set the necessary booleans
            if (mainState.getCardModeSelected()) {
                mainState.setCardModeSelected(false);
                mainState.setTrackModeSelected(true);
                mainState.getFaceDownTrainCards().setHighlight(false);
                mainState.getDestinationCards().setHighlight(false);
                mainState.setDestinationCardsSelected(false);
                mainState.setTrainCardsSelected(false);
                mainState.setPlaceTrainSelected(false);
                for (int i = 0; i < mainState.getFaceUpTrainCards().size(); i++) {
                    mainState.getFaceUpCardsHighlight()[i] = false;
                }

                //if track mode is the selected mode, de-highlight all selected cards
                //and set the necessary booleans
            }
            else if (mainState.getTrackModeSelected()) {
                mainState.setCardModeSelected(true);
                mainState.setTrackModeSelected(false);
                for (int i = 0; i < mainState.getTracks().size(); i++) {
                    mainState.getTracks().get(i).setSelected(false);
                    if(!mainState.getTracks2().get(i).getTrackColor().equals("None")){
                        mainState.getTracks2().get(i).setSelected(false);
                    }
                }
            }//88
            return true;

            //If ConfirmSelectionAction is thrown, perform one of these changes to the game state.
        }
        //if the player wants to get destination cards
        else if (action instanceof DrawDestinationCardAction) {

            //ensure they aren't in track mode
            if (mainState.getTrackModeSelected()) {
                return false;
            }

            //de-highlight any highlighted train cards
            else if (mainState.getTrainCardsSelected()) {
                mainState.setTrainCardsSelected(false);
                mainState.setOnlyDownDeck(false);
                mainState.getFaceDownTrainCards().setHighlight(false);
                for (int i = 0; i < mainState.getFaceUpTrainCards().size(); i++) {
                    mainState.getFaceUpTrainCards().getCards().get(i).setHighlight(false);
                }
                mainState.getDestinationCards().setHighlight(true);
                mainState.setDestinationCardsSelected(true);
            }

            //if destination deck wasn't selected already, highlight it, otherwise,
            //de-highlight it.
            else if (mainState.getDestinationCardsSelected()
                    && mainState.getDestinationCards().getHighlight()) {
                mainState.getDestinationCards().setHighlight(false);
                mainState.setDestinationCardsSelected(false);
            } else {
                mainState.getDestinationCards().setHighlight(true);
                mainState.setDestinationCardsSelected(true);
            }
        }
        else if (action instanceof ConfirmSelectionAction
                && (mainState.getPlaceTrainSelected()
                || mainState.getTrainCardsSelected()
                || mainState.getDestinationCardsSelected())) {
            //if a track has been selected
            if (mainState.getGameStart()
                    && mainState.getTrackModeSelected()
                    && mainState.getPlaceTrainSelected()
                    && mainState.getTrackSpot() != -1) {

                ConfirmSelectionAction myAction = (ConfirmSelectionAction) action;
                int trackSpot = mainState.getTrackSpot();
                String trackColor = mainState.getSelectedTrackColor();
                int count = mainState.getTracks().get(trackSpot).getTrainTrackNum();
                int num = mainState.getTracks().get(trackSpot).getTrainTrackNum();
                boolean track2 = false;
                if(!mainState.getTracks2().get(trackSpot).getTrackColor().equals("Blank")) {
                    if(mainState.getTracks2().get(trackSpot).getSelected()){
                        track2 = true;
                    }
                }

                if(!track2) {
                    //if the track selected was not Gray, run through the player's hand to find
                    //and remove the cards that share the same color as the track
                    if (mainState.getTracks().get(trackSpot).getSelected() &&
                            trackColor.equals("Gray")) {

                        //if the player chose to use rainbow cards, remove the corresponding cards
                        //from their hand and reduce the count for the number of cards used to get
                        //the track.
                        if (myAction.getUseRainbow() != 0) {
                            int numRainbows = myAction.getUseRainbow();
                            for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                                String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                        .getCards().get(j).toString();
                                if (cardColor.equals("Rainbow") && numRainbows != 0 && count != 0) {
                                    mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                            mainState.getTrainDiscard(),
                                            mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                    j = 0;
                                    count--;
                                    numRainbows--;
                                }
                            }
                        }

                        //remove the correct number of cards that match the color of the track. Once
                        //one card is removed, the count is reduced, which indicates how many more
                        //cards need to be removed.
                        for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                            String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                    .getCards().get(j).toString();
                            if (myAction.getTrainColor().equals(cardColor) && count != 0) {
                                mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                        mainState.getTrainDiscard(),
                                        mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                j = 0;
                                count--;
                            }
                        }
                    }

                    //if the color of the track is Gray, remove the cards of the chosen card colors
                    //until enough have been removed.
                    else if (mainState.getTracks().get(trackSpot).getSelected()
                            && !trackColor.equals("Gray")) {

                        //if the player chose to use rainbow cards, remove the corresponding cards
                        //from their hand and reduce the count for the number of cards used to get
                        //the track.
                        if (myAction.getUseRainbow() != 0) {
                            int takeRainbows = myAction.getUseRainbow();
                            for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                                String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                        .getCards().get(j).toString();
                                if (cardColor.equals("Rainbow") && count != 0 && takeRainbows != 0) {
                                    mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                            mainState.getTrainDiscard(),
                                            mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                    j = 0;
                                    count--;
                                    takeRainbows--;
                                }
                            }
                        }

                        //remove the correct number of cards that match the color of the track. Once
                        //one card is removed, the count is reduced, which indicates how many more
                        //cards need to be removed.

                        for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                            String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                    .getCards().get(j).toString();
                            if (trackColor.equals(cardColor) && count != 0) {
                                mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                        mainState.getTrainDiscard(),
                                        mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                j = 0;
                                count--;
                            }
                        }


                        //cover the track and assign the player's number to the track
                        mainState.getTracks().get(trackSpot).setCovered(true);
                        mainState.getTracks().get(trackSpot).setPlayerID(mainState.getPlayerID());
                        mainState.getTracks().get(trackSpot).setSelected(false);
                    }
                }
                else if(track2) {
                    //if the track selected was not Gray, run through the player's hand to find
                    //and remove the cards that share the same color as the track
                    if (mainState.getTracks2().get(trackSpot).getSelected() &&
                            trackColor.equals("Gray")) {

                        //cover the track and assign the player's number to the track
                        mainState.getTracks2().get(trackSpot).setCovered(true);
                        mainState.getTracks2().get(trackSpot).setPlayerID(mainState.getPlayerID());
                        mainState.getTracks2().get(trackSpot).setSelected(false);

                        //if the player chose to use rainbow cards, remove the corresponding cards
                        //from their hand and reduce the count for the number of cards used to get
                        //the track.
                        if (myAction.getUseRainbow() != 0) {
                            int numRainbows = myAction.getUseRainbow();
                            for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                                String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                        .getCards().get(j).toString();
                                if (cardColor.equals("Rainbow") && numRainbows != 0 && count != 0) {
                                    mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                            mainState.getTrainDiscard(),
                                            mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                    count--;
                                    numRainbows--;
                                }
                            }
                        }

                        //remove the correct number of cards that match the color of the track. Once
                        //one card is removed, the count is reduced, which indicates how many more
                        //cards need to be removed.
                        for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                            String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                    .getCards().get(j).toString();
                            if (myAction.getTrainColor().equals(cardColor) && count != 0) {
                                mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                        mainState.getTrainDiscard(),
                                        mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                j = 0;
                                count--;
                            }
                        }
                    }

                    //if the color of the track is Gray, remove the cards of the chosen card colors
                    //until enough have been removed.
                    else if (mainState.getTracks2().get(trackSpot).getSelected()
                            && !trackColor.equals("Gray")) {

                        //if the player chose to use rainbow cards, remove the corresponding cards
                        //from their hand and reduce the count for the number of cards used to get
                        //the track.
                        if (myAction.getUseRainbow() != 0) {
                            int takeRainbows = myAction.getUseRainbow();
                            for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                                String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                        .getCards().get(j).toString();
                                if (cardColor.equals("Rainbow") && count != 0 && takeRainbows != 0) {
                                    mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                            mainState.getTrainDiscard(),
                                            mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                    count--;
                                    takeRainbows--;
                                }
                            }
                        }

                        //remove the correct number of cards that match the color of the track. Once
                        //one card is removed, the count is reduced, which indicates how many more
                        //cards need to be removed.

                        for (int j = 0; j < mainState.getPlayerTrainDecks()[mainState.getPlayerID()].size(); j++) {
                            String cardColor = mainState.getPlayerTrainDecks()[mainState.getPlayerID()]
                                    .getCards().get(j).toString();
                            if (trackColor.equals(cardColor) && count != 0) {
                                mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                                        mainState.getTrainDiscard(),
                                        mainState.getPlayerTrainDecks()[mainState.getPlayerID()], j);
                                j = 0;
                                count--;
                            }
                        }
                    }
                }

                if(mainState.getNumPlayers() < 4) {
                    if(!mainState.getTracks2().get(trackSpot).getTrackColor().equals("Blank")){
                        mainState.getTracks2().get(trackSpot).setCovered(true);
                        mainState.getTracks2().get(trackSpot).setPlayerID(mainState.getPlayerID());
                        mainState.getTracks2().get(trackSpot).setSelected(false);
                    }
                    //cover the track and assign the player's number to the track
                    mainState.getTracks().get(trackSpot).setCovered(true);
                    mainState.getTracks().get(trackSpot).setPlayerID(mainState.getPlayerID());
                    mainState.getTracks().get(trackSpot).setSelected(false);
                }
                else{
                    if(track2 && !mainState.getTracks2().get(trackSpot).getTrackColor().equals("Blank")){
                        mainState.getTracks2().get(trackSpot).setCovered(true);
                        mainState.getTracks2().get(trackSpot).setPlayerID(mainState.getPlayerID());
                        mainState.getTracks2().get(trackSpot).setSelected(false);
                    }
                    else{
                        mainState.getTracks().get(trackSpot).setCovered(true);
                        mainState.getTracks().get(trackSpot).setPlayerID(mainState.getPlayerID());
                        mainState.getTracks().get(trackSpot).setSelected(false);
                    }
                }
                //depending on the size of the track, assign the correct number of points
                //to the player.
                switch (num) {
                    case 1:
                        mainState.setScore(mainState.getScores()[mainState.getPlayerID()] + 1, mainState.getPlayerID());
                        mainState.setTrainToken(mainState.getTrainTokens()[mainState.getPlayerID()] - 1, mainState.getPlayerID());
                        break;
                    case 2:
                        mainState.setScore(mainState.getScores()[mainState.getPlayerID()] + 2, mainState.getPlayerID());
                        mainState.setTrainToken(mainState.getTrainTokens()[mainState.getPlayerID()] - 2, mainState.getPlayerID());
                        break;
                    case 3:
                        mainState.setScore(mainState.getScores()[mainState.getPlayerID()] + 4, mainState.getPlayerID());
                        mainState.setTrainToken(mainState.getTrainTokens()[mainState.getPlayerID()] - 3, mainState.getPlayerID());
                        break;
                    case 4:
                        mainState.setScore(mainState.getScores()[mainState.getPlayerID()] + 7, mainState.getPlayerID());
                        mainState.setTrainToken(mainState.getTrainTokens()[mainState.getPlayerID()] - 4, mainState.getPlayerID());
                        break;
                    case 5:
                        mainState.setScore(mainState.getScores()[mainState.getPlayerID()] + 10, mainState.getPlayerID());
                        mainState.setTrainToken(mainState.getTrainTokens()[mainState.getPlayerID()] - 5, mainState.getPlayerID());
                        break;
                    case 6:
                        mainState.setScore(mainState.getScores()[mainState.getPlayerID()] + 15, mainState.getPlayerID());
                        mainState.setTrainToken(mainState.getTrainTokens()[mainState.getPlayerID()] - 6, mainState.getPlayerID());
                        break;
                }

            }

            //if some form of card has been selected
            else if (mainState.getCardModeSelected() || !mainState.getGameStart()) {

                ConfirmSelectionAction myAction = (ConfirmSelectionAction) action;
                if (mainState.getDestinationCardsSelected()
                        && ((ConfirmSelectionAction) action).getRemoveDeck() != null
                        && ((ConfirmSelectionAction) action).getSendDeck() != null) {

                    //move destination cards to players hand they selected
                    while (!((ConfirmSelectionAction) action).getSendDeck().getCards().isEmpty()) {
                        mainState.getPlayerDestinationDecks()[mainState.getPlayerID()].moveCardTo(
                                mainState.getPlayerDestinationDecks()[mainState.getPlayerID()],
                                ((ConfirmSelectionAction) action).getSendDeck(), 0);
                        mainState.getDestinationCards().getCards().remove(mainState.getDestinationCards().size() - 1);
                    }

                    //move destination cards to discard pile if not selected. 1
                    while (!((ConfirmSelectionAction) action).getRemoveDeck().getCards().isEmpty()) {
                        mainState.getDestinationDiscard().moveCardTo(mainState.getDestinationDiscard(),
                                ((ConfirmSelectionAction) action).getRemoveDeck(), 0);
                        mainState.getDestinationCards().getCards().remove(mainState.getDestinationCards().size() - 1);
                    }

                    if (numStarted == players.length - 1) {
                        mainState.setGameStart(true);
                    }
                    else {
                        numStarted++;
                    }
                    mainState.getDestinationCards().setHighlight(false);
                }
                else if (mainState.getTrainCardsSelected()) {
                    int numFaceDown = 0;
                    if (mainState.getFaceDownTrainCards().getHighlight()) {
                        if (mainState.getOnlyDownDeck()) {
                            numFaceDown = 2;
                        } else {
                            numFaceDown = 1;
                        }
                    }

                    for (int i = 0; i < numFaceDown; i++) {
                        mainState.getFaceDownTrainCards().moveTopCardTo(
                                mainState.getPlayerTrainDecks()[mainState.getPlayerID()],
                                mainState.getFaceDownTrainCards());
                    }


                    //resets the state of selected cards
                    mainState.getFaceDownTrainCards().setHighlight(false);
                    mainState.setOnlyDownDeck(false);
                    mainState.setTrainCardsSelected(false);
                }
            }


            if (mainState.getIsGameOver()) {
                turnsLeft--;
            }

            //deselect all of the games tracks.
            for (int i = 0; i < mainState.getTracks().size(); i++) {
                mainState.getTracks().get(i).setSelected(false);
                mainState.getTracks().get(i).setHighlight(false);
            }

            if (mainState.getDestinationCards().size() <= 3) {
                mainState.getDestinationDiscard().shuffle();
                while (!mainState.getDestinationDiscard().getCards().isEmpty()) {
                    mainState.getDestinationCards().moveAllCardsTo(
                            mainState.getDestinationCards(), mainState.getDestinationDiscard());
                }
            }
            if (mainState.getFaceDownTrainCards().size() <= 10) {
                mainState.getTrainDiscard().shuffle();
                while (!mainState.getTrainDiscard().getCards().isEmpty()) {
                    mainState.getFaceDownTrainCards().moveAllCardsTo(
                            mainState.getFaceDownTrainCards(), mainState.getTrainDiscard());
                }
            }
            for (int i = mainState.getFaceUpTrainCards().size() - 1; i >= 0; i--) {
                if (mainState.getFaceUpCardsHighlight()[i]) {
                    mainState.getPlayerTrainDecks()[mainState.getPlayerID()].moveCardTo(
                            mainState.getPlayerTrainDecks()[mainState.getPlayerID()],
                            mainState.getFaceUpTrainCards(), i);
                }
                else if(mainState.getFaceUpTrainCards().getCards().get(i).toString().equals("Blank")){
                    mainState.getFaceUpTrainCards().getCards().remove(i);
                }
            }
            while (mainState.getFaceUpTrainCards().getCards().size() < 5) {
                if(mainState.getFaceDownTrainCards().getCards().isEmpty()){
                    TrainCards blankTrain = new TrainCards(9);
                    mainState.getFaceUpTrainCards().add(blankTrain);
                }
                else {
                    mainState.getFaceDownTrainCards().moveTopCardTo(
                            mainState.getFaceUpTrainCards(), mainState.getFaceDownTrainCards());
                }
            }
            if (mainState.getTrainColorCount("Rainbow", -1) >= 3) {
                while (!mainState.getFaceUpTrainCards().getCards().isEmpty()) {
                    mainState.getFaceUpTrainCards().getCards().remove(0);
                }
                for (int i = 0; i < 5; i++) {
                    mainState.getFaceDownTrainCards().moveTopCardTo(
                            mainState.getFaceUpTrainCards(), mainState.getFaceDownTrainCards());
                    mainState.getFaceUpTrainCards().getCards().get(i).setHighlight(false);
                }
            }
            for(int i = 0; i < mainState.getFaceUpTrainCards().size(); i++){
                mainState.getFaceUpTrainCards().getCards().get(i).setHighlight(false);
            }
            mainState.setPlayerID((mainState.getPlayerID() + 1) % mainState.getNumPlayers());
            mainState.setTrackModeSelected(false);
            mainState.setCardModeSelected(true);
            if(mainState.getGameStart()) {
                mainState.setDestinationCardsSelected(false);
            }
            else{
                mainState.setDestinationCardsSelected(true);
            }
            mainState.setTrainCardsSelected(false);
            mainState.setOnlyDownDeck(false);
            mainState.setPlaceTrainSelected(false);
            for(int i = 0; i < mainState.getFaceUpCardsHighlight().length; i++){
                mainState.getFaceUpCardsHighlight()[i] = false;
            }
            mainState.setTrackSpot(-1);
            mainState.setSelectedTrackColor("");
            return true;
        }

        //if the player wants to select a track enter this if statement.
        else if (action instanceof TrackPlaceAction) {

            if(mainState.getCardModeSelected()){
                return false;
            }
            TrackPlaceAction thisAction = (TrackPlaceAction) action;
            int currentSelected = -1;
            for(int i = 0; i < mainState.getTracks().size(); i++){
                if(mainState.getTracks().get(i).getSelected()){
                    currentSelected = i;
                }
            }
            if (thisAction.getIndex() != -1) {
                if(mainState.getTracks2().get(thisAction.getIndex()).getTrackColor().equals("Blank")) {
                    if (!mainState.getTracks().get(thisAction.getIndex()).getCovered()) {
                        mainState.getTracks().get(thisAction.getIndex()).setSelected(true);
                        mainState.setSelectedTrackColor(thisAction.getTrackColor());
                        mainState.setTrackSpot(thisAction.getIndex());
                        mainState.setPlaceTrainSelected(true);
                    } else {
                        mainState.setSelectedTrackColor("");
                        mainState.setTrackSpot(-1);
                    }
                }
                else{
                    if(mainState.getTracks().get(thisAction.getIndex()).getSelected()) {
                        if (mainState.getTracks().get(thisAction.getIndex()).getTrackColor().equals(thisAction.getTrackColor())
                                || mainState.getTracks().get(thisAction.getIndex()).getCovered()) {
                            mainState.getTracks().get(thisAction.getIndex()).setSelected(false);
                            mainState.getTracks2().get(thisAction.getIndex()).setSelected(true);
                            mainState.setSelectedTrackColor(thisAction.getTrackColor());
                            mainState.setTrackSpot(thisAction.getIndex());
                            mainState.setPlaceTrainSelected(true);
                        }
                    }
                    else if(mainState.getTracks2().get(thisAction.getIndex()).getSelected()){
                        if(mainState.getTracks2().get(thisAction.getIndex()).getTrackColor().equals(thisAction.getTrackColor())
                                || mainState.getTracks2().get(thisAction.getIndex()).getCovered()) {
                            mainState.getTracks2().get(thisAction.getIndex()).setSelected(false);
                            mainState.getTracks().get(thisAction.getIndex()).setSelected(true);
                            mainState.setSelectedTrackColor(thisAction.getTrackColor());
                            mainState.setTrackSpot(thisAction.getIndex());
                            mainState.setPlaceTrainSelected(true);
                        }
                    }
                    else{
                        if(currentSelected != -1) {
                            mainState.getTracks().get(currentSelected).setSelected(false);
                            mainState.getTracks2().get(currentSelected).setSelected(false);
                        }
                        else if(mainState.getTracks().get(thisAction.getIndex()).getTrackColor().equals(thisAction.getTrackColor())
                                || mainState.getTracks2().get(thisAction.getIndex()).getCovered()) {
                            mainState.getTracks().get(thisAction.getIndex()).setSelected(true);
                            mainState.setSelectedTrackColor(thisAction.getTrackColor());
                            mainState.setTrackSpot(thisAction.getIndex());
                            mainState.setPlaceTrainSelected(true);
                        }
                        else if(mainState.getTracks2().get(thisAction.getIndex()).getTrackColor().equals(thisAction.getTrackColor())
                                || mainState.getTracks().get(thisAction.getIndex()).getCovered()){
                            mainState.getTracks2().get(thisAction.getIndex()).setSelected(true);
                            mainState.setSelectedTrackColor(thisAction.getTrackColor());
                            mainState.setTrackSpot(thisAction.getIndex());
                            mainState.setPlaceTrainSelected(true);
                        }
                    }
                }
            }
            else{
                mainState.setSelectedTrackColor("");
                mainState.setTrackSpot(-1);
            }

            for(int i = 0; i < mainState.getTracks().size(); i++){
                if(i != mainState.getTrackSpot()){
                    mainState.getTracks().get(i).setSelected(false);
                }
            }
            for(int i = 0; i < mainState.getTracks2().size(); i++){
                if(!mainState.getTracks2().get(i).getTrackColor().equals("Blank")
                        && i != mainState.getTrackSpot()){
                    mainState.getTracks2().get(i).setSelected(false);
                }
            }
            return true;
        }

        //if the action is for choosing face up cards, enter this if statement.
        else if (action instanceof DrawUpCardAction) {

            //if the mode is track mode, return false so no cards will be highlighted.
            if (mainState.getTrackModeSelected()) {
                return false;
            }

            //if the destination cards were already highlighted, de-highlight them.
            else if (mainState.getDestinationCardsSelected()) {
                mainState.getDestinationCards().setHighlight(false);
                mainState.setDestinationCardsSelected(false);
            }

            //find out how many cards or decks are already highlighted.
            DrawUpCardAction temp = (DrawUpCardAction) action;
            int pos = temp.getPos();
            int numHighlights = 0;
            for (int i = 0; i < mainState.getFaceUpTrainCards().size(); i++) {
                if (mainState.getFaceUpTrainCards().getCards().get(i).getHighlight()) {
                    //if a rainbow card was selected, numHighlights is automatically set to 2.
                    //Otherwise, it's one.
                    if (mainState.getFaceUpTrainCards().getCards().get(i).toString().equals("Rainbow")) {
                        numHighlights = numHighlights + 2;
                    } else {
                        numHighlights++;
                        mainState.setOnlyDownDeck(false);
                        mainState.setTrainCardsSelected(true);
                    }
                }
            }

            //if the face down deck has been selected add to numHighlights.
            if (mainState.getFaceDownTrainCards().getHighlight()) {
                numHighlights++;
                mainState.setTrainCardsSelected(true);
            }

            //if numHighlights is less than 2 highlight the card if its a legal move
            if (numHighlights <= 2) {

                //only highlight a rainbow card if it's the only one.
                if (mainState.getFaceUpTrainCards().getCards().get(pos).toString().equals("Rainbow")) {
                    if (numHighlights == 0) {
                        mainState.getFaceUpTrainCards().getCards().get(pos).setHighlight(true);
                        mainState.getFaceUpCardsHighlight()[pos] = true;
                        mainState.setTrainCardsSelected(true);
                    } else {
                        mainState.getFaceUpTrainCards().getCards().get(pos).setHighlight(false);
                        mainState.getFaceUpCardsHighlight()[pos] = false;
                    }
                }

                //if its already been highlighted, de-highlight it.
                else if (mainState.getFaceUpTrainCards().getCards().get(pos).getHighlight()) {
                    mainState.getFaceUpTrainCards().getCards().get(pos).setHighlight(false);
                    mainState.getFaceUpCardsHighlight()[pos] = false;
                    mainState.setTrainCardsSelected(false);
                    if(numHighlights == 2 && mainState.getFaceDownTrainCards().getHighlight()){
                        mainState.setOnlyDownDeck(true);
                        mainState.setTrainCardsSelected(true);
                    }
                }

                //Otherwise, highlight the card
                else if(numHighlights < 2){
                    mainState.getFaceUpTrainCards().getCards().get(pos).setHighlight(true);
                    mainState.getFaceUpCardsHighlight()[pos] = true;
                    mainState.setTrainCardsSelected(true);
                }
            }
            //if the maximum number of cards are highlighted, don't let the card be highlighted.
            else if (mainState.getFaceUpTrainCards().getCards().get(pos).getHighlight()) {
                mainState.getFaceUpTrainCards().getCards().get(pos).setHighlight(false);
                mainState.getFaceUpCardsHighlight()[pos] = false;
                mainState.setTrainCardsSelected(true);
            }
            return true;
        }

        //if the down deck was selected, enter this if statement.
        else if (action instanceof DrawDownCardAction) {

            //leave if in track mode.
            if (mainState.getTrackModeSelected()) {
                return false;
            }

            //de-highlight destination card deck.
            else if (mainState.getDestinationCardsSelected()) {
                mainState.getDestinationCards().setHighlight(false);
                mainState.setDestinationCardsSelected(false);
            }

            //set the correct boolean values depending on only if the down deck was selected.
            else if (mainState.getTrainCardsSelected() &&
                    mainState.getFaceDownTrainCards().getHighlight()) {
                if (mainState.getOnlyDownDeck()) {
                    mainState.setTrainCardsSelected(false);
                }
                mainState.setOnlyDownDeck(false);
                mainState.getFaceDownTrainCards().setHighlight(false);
            }

            //if cards have already been selected and the face down deck has not
            else if (mainState.getTrainCardsSelected()
                    && !mainState.getFaceDownTrainCards().getHighlight()) {
                int highlightNum = 0;

                //count how many cards have been highlighted
                for (int i = 0; i < mainState.getFaceUpTrainCards().size(); i++) {
                    if (mainState.getFaceUpTrainCards().getCards().get(i).getHighlight()) {

                        //rainbow cards are equal to two highlights
                        if (mainState.getFaceUpTrainCards().getCards().get(i).toString().equals("Rainbow")) {
                            highlightNum = highlightNum + 2;
                        }

                        //other cards are equal to one highlight
                        else {
                            highlightNum++;
                            mainState.setOnlyDownDeck(false);
                            mainState.setTrainCardsSelected(true);
                        }
                    }
                }

                //if less than two highlights are available, highlight the deck.
                if (highlightNum < 2) {
                    mainState.getFaceDownTrainCards().setHighlight(true);
                }
                mainState.setOnlyDownDeck(false);
            }

            //if no cards have been selected set onlyDownDeck
            else if (!mainState.getTrainCardsSelected() && !mainState.getOnlyDownDeck()) {
                mainState.setOnlyDownDeck(true);
                mainState.setTrainCardsSelected(true);
                mainState.getFaceDownTrainCards().setHighlight(true);
            }
            else {
                return false;
            }
        }
        return true;
    }
    public void setTurnsLeft(int turnsLeft) {this.turnsLeft = turnsLeft;}

    /**
     * Sets the players in the game state
     * @param players
     */
    @Override
    public void start(GamePlayer[] players) {
        //Sets gameState's numPlayer and play order
        mainState.setNumPlayers(players.length);
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        mainState.setPlayerID(rand.nextInt(players.length));
        super.start(players);
    }
}
