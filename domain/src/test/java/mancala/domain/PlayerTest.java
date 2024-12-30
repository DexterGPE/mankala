package mancala.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    @Test
    public void turnsAreCorrectlyInitialized(){
        Player testPlayer = new Player();

        assertFalse(testPlayer.getOpponent().getIsMyTurn());
        assertTrue(testPlayer.getIsMyTurn());
    }

    @Test
    public void switchTurnFromPlayerWithCurrentTurn(){
        Player testPlayer = new Player();

        testPlayer.switchTurn();

        assertTrue(testPlayer.getOpponent().getIsMyTurn());
        assertFalse(testPlayer.getIsMyTurn());
    }

    @Test
    public void switchTurnFromPlayerWithoutCurrentTurn(){
        Player testPlayer = new Player();

        testPlayer.getOpponent().switchTurn();

        assertTrue(testPlayer.getOpponent().getIsMyTurn());
        assertFalse(testPlayer.getIsMyTurn());
    }
}
