package teamresistance.tickettoride.TTR;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import teamresistance.tickettoride.Game.LocalGame;

/**
 *  TTRGameState creates the GameState
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class TTRGameStateTest extends TestCase {
    /**
     * Tests the initial constructor
     * @throws Exception
     */
    @Test
    public void testTTRStateConstructor() throws Exception
    {
        TTRGameState testState = new TTRGameState();
        testState.setNumPlayers(3);
        assertTrue("3 players not initialized", testState.getNumPlayers() == 3);
        assertNotNull("", testState.getDestinationCards());
        //cannot have card mode and track mode simultaneously, mutually exclusive
        testState.setCardModeSelected(true);
        assertTrue("card mode not true", testState.getCardModeSelected());
        assertFalse("track mode not false", testState.getTrackModeSelected());
    }
    /**
     * Tests the copy constructor, checking various values
     *
     * @throws Exception
     */
    @Test
    public void testCopyConstructor() throws Exception {
        TTRGameState testState = new TTRGameState();
        testState.setNumPlayers(3);
        TTRGameState copyState = new TTRGameState(testState);
        assertTrue("Number of Players", testState.getNumPlayers() == copyState.getNumPlayers());
        assertNotNull("Face Down Deck", copyState.getFaceDownTrainCards());
        assertNotNull("Destination Deck", copyState.getDestinationCards());
        assertNotNull("Face Up Deck", copyState.getFaceUpTrainCards());
        assertTrue("trackSets", copyState.getTracks() == testState.getTracks());
    }

    /**
     * Checks the face up deck at game start
     *
     * @throws Exception
     */
    @Test
    public void testFaceUpDeck() throws Exception {
        TTRGameState testState = new TTRGameState();
        Deck testDeck = testState.getFaceUpTrainCards();
        int size = testDeck.size();
        assertTrue("Not 5 cards", size == 5);
        for (int k = 0; k < size; k++) {
            assertNotNull("Face Up Deck Draw Cards", testDeck.getCards().get(k));
        }
    }

    /**
     * test the destination deck
     *
     * @throws Exception
     */
    @Test
    public void testDestinationDeck() throws Exception {
        TTRGameState testState = new TTRGameState();
        Deck testDeck = testState.getDestinationCards();
        int size = testDeck.size();
        for (int k = 0; k < size; k++) {
            assertNotNull("Destination deck has null reference", testDeck.getCards().get(k));
        }
    }

    /**
     * Checks to see if train card deck is null
     *
     * @throws Exception
     */
    @Test
    public void testTrainCardDeck() throws Exception {
        TTRGameState testState = new TTRGameState();
        Deck testDeck = testState.getFaceDownTrainCards();
        int size = testDeck.size();
        for (int j = 0; j < size; j++) {
            assertNotNull("train card deck has null reference", testDeck.getCards().get(j));
        }
    }

    /**
     * Checks to see that the discard deck starts out empty
     *
     * @throws Exception
     */
    @Test
    public void testDiscardTrainCards() throws Exception {
        TTRGameState testState = new TTRGameState();
        Deck testDeck = testState.getTrainDiscard();
        assertTrue("Train discard should be empty (size 0) at start of game", testDeck.size() == 0);
    }

    /**
     * Checks to see if the tracks have been initialized
     *
     * @throws Exception
     */
    @Test
    public void testTrack() throws Exception {
        TTRGameState testState = new TTRGameState();
        ArrayList testTrack = testState.getTracks();
        int size = testTrack.size();
        for (int k = 0; k < size; k++) {
            assertNotNull("tracks have null reference",testTrack.get(k));
        }
    }
}


