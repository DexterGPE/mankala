package mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BowlTest {

    @Test
    public void normalBowlShouldHaveANeighbourBowl() {
        Bowl normalBowl = new NormalBowl();

        Bowl neighbourBowl = normalBowl.getRightSideNeighbour();

        assertInstanceOf(Bowl.class, neighbourBowl);
    }

    @Test
    public void normalBowlShouldHaveABowlOwner(){
        Bowl normalBowl = new NormalBowl();

        Player bowlOwner = normalBowl.getOwner();

        assertInstanceOf(Player.class, bowlOwner);
    }

    @Test
    public void normalBowlOwnerAndItsOpponentHaveOppositeTurns(){
        Bowl normalBowl = new NormalBowl();

        Player bowlOwner = normalBowl.getOwner();
        Player opponent = bowlOwner.getOpponent();

        assertNotEquals(bowlOwner.getIsMyTurn(), opponent.getIsMyTurn());
    }

    @Test
    public void seventhBowlShouldBeKalahaBowl(){
        Bowl firstBowl = new NormalBowl();

        Bowl fourteenthBowl = firstBowl.getNthNeighbourBowl(6);

        assertInstanceOf(KalahaBowl.class, fourteenthBowl);
    }

    @Test
    public void fourteenthBowlShouldBeKalahaBowl(){
        Bowl firstBowl = new NormalBowl();

        Bowl fourteenthBowl = firstBowl.getNthNeighbourBowl(13);

        assertInstanceOf(KalahaBowl.class, fourteenthBowl);
    }

    @Test
    public void eighthBowlOwnerIsSeventhBowlOpponent(){
        Bowl firstBowl = new NormalBowl();

        Bowl seventhBowl = firstBowl.getNthNeighbourBowl(6);
        Bowl eighthBowl = firstBowl.getNthNeighbourBowl(7);

        assertNotEquals(seventhBowl.getOwner(), eighthBowl.getOwner());
    }

    @Test
    public void fifteenthBowlShouldBeTheFirstBowl(){
        Bowl firstBowl= new NormalBowl();

        Bowl fifteenthBowl = firstBowl.getNthNeighbourBowl(14);

        assertEquals(firstBowl, fifteenthBowl);
    }

    @Test
    public void secondBowlShouldBeNormalBowl(){
        Bowl normalBowl = new NormalBowl();

        Bowl secondBowl = normalBowl.getRightSideNeighbour();

        assertInstanceOf(NormalBowl.class, secondBowl);
    }

    @Test
    public void kahalaBowlShouldHaveZeroSeedsAtInitialization(){
        Bowl normalBowl = new NormalBowl();

        Bowl seventhBowl = normalBowl.getNthNeighbourBowl(6);

        assertEquals(0, seventhBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void normalBowlShouldHaveFourSeedsAtInitialization(){
        Bowl normalBowl = new NormalBowl();

        Bowl thirdBowl = normalBowl.getNthNeighbourBowl(2);

        assertEquals(4, thirdBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void get3rdNeighbourBowlReturnsCorrectNeighbour(){
        Bowl firstBowl = new NormalBowl();
        Bowl firstNeighbour = firstBowl.getRightSideNeighbour();
        Bowl secondNeighbour = firstNeighbour.getRightSideNeighbour();
        Bowl thirdNeighbour = secondNeighbour.getRightSideNeighbour();

        Bowl resultingNeighbour = firstBowl.getNthNeighbourBowl(3);

        assertEquals(thirdNeighbour, resultingNeighbour);
    }

    @Test
    public void get6thNeighbourBowlReturnsCorrectNeighbour(){
        Bowl firstBowl = new NormalBowl();
        Bowl firstNeighbour = firstBowl.getRightSideNeighbour();
        Bowl secondNeighbour = firstNeighbour.getRightSideNeighbour();
        Bowl thirdNeighbour = secondNeighbour.getRightSideNeighbour();
        Bowl fourthNeighbour = thirdNeighbour.getRightSideNeighbour();
        Bowl fifthNeighbour = fourthNeighbour.getRightSideNeighbour();
        Bowl sixthNeighbour = fifthNeighbour.getRightSideNeighbour();

        Bowl resultingNeighbour = firstBowl.getNthNeighbourBowl(6);

        assertEquals(sixthNeighbour, resultingNeighbour);
    }

    @Test
    public void findOwnKalahaReturnsCorrectBowl(){
        Bowl normalBowl = new NormalBowl();
        Bowl ownKalaha = normalBowl.getNthNeighbourBowl(6);

        Bowl result = normalBowl.getKalahaBowl(normalBowl.getOwner());

        assertEquals(ownKalaha, result);
    }

    @Test
    public void findOpponentsKalahaReturnsCorrectBowl(){
        Bowl normalBowl = new NormalBowl();
        Bowl opponentsKalaha = normalBowl.getNthNeighbourBowl(13);

        Bowl result = normalBowl.getKalahaBowl(normalBowl.getOpponent());

        assertEquals(opponentsKalaha, result);
    }

    @Test
    public void opponentIsCorrectlyAssignedAsWinner(){
        String expectedGameState = "4 4 4 0 5 5 1 0 0 0 0 0 0 25 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        Player winningPlayer = loadedGame.getWinningPlayer();

        assertEquals(loadedGame.getOpponent(), winningPlayer);
    }

    @Test
    public void player1IsCorrectlyAssignedAsWinner(){
        String expectedGameState = "5 5 5 4 4 4 0 0 0 0 0 0 0 21 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        Player winningPlayer = loadedGame.getWinningPlayer();

        assertEquals(loadedGame.getOwner(), winningPlayer);
    }

    @Test
    public void checkPlayer1IsWinnerButGameHasNotEndedYet(){
        NormalBowl normalBowl = new NormalBowl();
        NormalBowl eleventhBowl = (NormalBowl) normalBowl.getNthNeighbourBowl(10);
        normalBowl.getOwner().switchTurn();
        eleventhBowl.doMove();

        Player winningPlayer = eleventhBowl.getWinningPlayer();

        assertEquals(normalBowl.getOwner(), winningPlayer);
    }

    @Test
    public void checkOpponentIsWinnerButGameHasNotEndedYet(){
        NormalBowl normalBowl = new NormalBowl();
        NormalBowl sixthBowl = (NormalBowl) normalBowl.getNthNeighbourBowl(5);
        sixthBowl.doMove();

        Player winningPlayer = normalBowl.getWinningPlayer();

        assertEquals(normalBowl.getOpponent(), winningPlayer);
    }

    @Test
    public void gameIsTieReturnCorrectly(){
        String expectedGameState = "4 6 2 0 8 4 0 0 0 0 0 0 0 24 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        Player winningPlayer = loadedGame.getWinningPlayer();

        assertNull(winningPlayer);
    }

    @Test
    public void endOfGameFound(){
        String expectedGameState = "4 4 4 4 4 4 0 0 0 0 0 0 0 24 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);
        NormalBowl playerTwoNormalBowl = (NormalBowl) loadedGame.getNthNeighbourBowl(7);

        boolean result = playerTwoNormalBowl.isGameFinished();

        assertTrue(result);
    }

    @Test
    public void notYetEndOfGameFound(){
        String expectedGameState = "4 4 4 4 4 4 1 0 0 0 0 0 0 23 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        boolean result = loadedGame.isGameFinished();

        assertFalse(result);
    }

    @Test
    public void opposingBowlCorrectlyFound(){
        Bowl normalBowl = new NormalBowl();
        Bowl expectedBowl = normalBowl.getNthNeighbourBowl(12);

        Bowl opposingBowl = normalBowl.getOpposingBowl();

        assertEquals(opposingBowl, expectedBowl);
    }

    @Test
    public void gameStateCorrectFormat(){
        NormalBowl newGame = new NormalBowl();
        newGame.doMove();
        String expectedGameState = "0 5 5 5 5 4 0 4 4 4 4 4 4 0 0";

        String gameState = newGame.toGameState();

        assertEquals(expectedGameState, gameState.trim());
    }

    @Test
    public void gameStateCorrectlyLoaded(){
        NormalBowl expectedGameState = new NormalBowl();
        expectedGameState.doMove();
        NormalBowl fifthBowlExpected = (NormalBowl) expectedGameState.getNthNeighbourBowl(4);
        String gameState = expectedGameState.toGameState();

        NormalBowl loadedGame = new NormalBowl(gameState);

        assertEquals(expectedGameState.getNrOfSeedsInBowl(), loadedGame.getNrOfSeedsInBowl());
        assertEquals(expectedGameState.getRightSideNeighbour().getNrOfSeedsInBowl(), loadedGame.getRightSideNeighbour().getNrOfSeedsInBowl());
        assertEquals(fifthBowlExpected.getNrOfSeedsInBowl(), loadedGame.getNthNeighbourBowl(4).getNrOfSeedsInBowl());
    }

    @Test
    public void makeSameGameStateAfterUsingItToLoadGame(){
        String expectedGameState = "0 5 5 5 5 4 0 2 2 6 6 4 4 0 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        String resultingGameState = loadedGame.toGameState();

        assertEquals(expectedGameState, resultingGameState.trim());
    }

    @Test
    public void getGameStateFromFifthBowlAlsoReturnsGameStateFromFirstBowl(){
        String expectedGameState = "0 5 5 5 5 4 0 2 2 6 6 4 4 0 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);
        NormalBowl fifthBowl = (NormalBowl) loadedGame.getNthNeighbourBowl(4);

        String resultingGameState = fifthBowl.toGameState();

        assertEquals(expectedGameState, resultingGameState.trim());
    }

    @Test
    public void numbersFromTenAndUpAlsoWorkWithLoadingGameState(){
        String expectedGameState = "0 11 0 4 5 4 0 2 2 6 6 4 4 0 1";

        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        assertEquals(0, loadedGame.getNrOfSeedsInBowl());
        assertEquals(11, loadedGame.getRightSideNeighbour().getNrOfSeedsInBowl());
        assertEquals(6, loadedGame.getNthNeighbourBowl(10).getNrOfSeedsInBowl());
    }

    @Test
    public void numbersFromTenAndUpAlsoWorkWithCreatingGameState(){
        String expectedGameState = "0 11 0 4 5 4 0 2 2 6 6 4 4 0 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        String resultingGameState = loadedGame.toGameState();

        assertEquals(expectedGameState, resultingGameState.trim());
    }

    @Test
    public void gameEndsOnOwnKalahaButStopsAndNoExtraTurnIsGiven(){
        String expectedGameState = "0 0 0 0 0 1 24 2 2 6 6 4 3 0 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);
        NormalBowl sixthBowl = (NormalBowl) loadedGame.getNthNeighbourBowl(5);
        sixthBowl.doMove();

        boolean result = sixthBowl.isGameFinished();

        assertTrue(result);
    }
}
