package mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NormalBowlTest {

    @Test
    public void keepOneSeedAndGiveRestToNextBowlIncludingOwnKalahaBowl(){
        Bowl normalBowl = new NormalBowl();
        Player opponent = normalBowl.getOpponent();
        Bowl ownKalaha = normalBowl.getKalahaBowl(normalBowl.getOwner());

        normalBowl.keepOneSeedPassRemainingSeedsToNextBowlRecursively(8,opponent);

        assertEquals(5, normalBowl.getNrOfSeedsInBowl());
        assertEquals(5, normalBowl.getRightSideNeighbour().getNrOfSeedsInBowl());
        assertEquals(1, ownKalaha.getNrOfSeedsInBowl());
        assertEquals(5, ownKalaha.getRightSideNeighbour().getNrOfSeedsInBowl());
    }

    @Test
    public void keepOneSeedAndGiveRestToNextBowlExceptOpponentKalahaBowl(){
        Bowl normalBowl = new NormalBowl();
        Player opponent = normalBowl.getOpponent();
        Bowl ownKalahaBowl = normalBowl.getKalahaBowl(normalBowl.getOwner());
        Bowl opponentKalahaBowl = normalBowl.getKalahaBowl(normalBowl.getOpponent());

        normalBowl.keepOneSeedPassRemainingSeedsToNextBowlRecursively(Bowl.NR_OF_BOWLS_PER_PLAYER * 2,opponent);

        assertEquals(0, opponentKalahaBowl.getNrOfSeedsInBowl());
        assertEquals(1, ownKalahaBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void transferFourSeedsFromNormalBowlToOwnKahala(){
        NormalBowl firstBowl = new NormalBowl();
        Bowl ownKalaha = firstBowl.getKalahaBowl(firstBowl.getOwner());

        firstBowl.transferAllOwnSeedsToKalaha(firstBowl.getOwner());

        assertEquals(0, firstBowl.getNrOfSeedsInBowl());
        assertEquals(4, ownKalaha.getNrOfSeedsInBowl());
    }

    @Test
    public void doMoveDistributesItsSeedsToNeighbours(){
        NormalBowl firstBowl = new NormalBowl();
        Bowl secondBowl = firstBowl.getRightSideNeighbour();
        Bowl thirdBowl = secondBowl.getRightSideNeighbour();

        firstBowl.doMove();

        assertEquals(0, firstBowl.getNrOfSeedsInBowl());
        assertEquals(5, secondBowl.getNrOfSeedsInBowl());
        assertEquals(5, thirdBowl.getRightSideNeighbour().getNrOfSeedsInBowl());
    }

    @Test
    public void moveResultedInStealGivesCorrectNrOfSeedsInKalaha(){
        String expectedGameState = "4 4 4 4 0 4 4 4 4 4 4 4 4 0 1";
        NormalBowl firstBowl = new NormalBowl(expectedGameState);
        Bowl ownKalahaBowl = firstBowl.getKalahaBowl(firstBowl.getOwner());

        firstBowl.doMove();

        assertEquals(9, ownKalahaBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void moveResultsInStealEmptiesTheCorrectBowls(){
        NormalBowl firstBowl = new NormalBowl();
        NormalBowl secondBowl = (NormalBowl) firstBowl.getRightSideNeighbour();
        Bowl ownKalaha = firstBowl.getKalahaBowl(firstBowl.getOwner());
        NormalBowl firstOpponentBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(7);
        NormalBowl lastOwnBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(5);
        lastOwnBowl.doMove();
        lastOwnBowl.getOwner().switchTurn();

        secondBowl.doMove();

        assertEquals(0, secondBowl.getNrOfSeedsInBowl());
        assertEquals(7, ownKalaha.getNrOfSeedsInBowl());
        assertEquals(0, lastOwnBowl.getNrOfSeedsInBowl());
        assertEquals(0, firstOpponentBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void moveResultedInEmptyBowlButOnOpponentsSideSoNoSteal(){
        String expectedGameState = "8 4 4 4 4 4 0 0 4 4 4 4 4 0 1";
        NormalBowl firstBowl = new NormalBowl(expectedGameState);
        NormalBowl startingBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(3);
        KalahaBowl ownKalahaBowl = firstBowl.getKalahaBowl(firstBowl.getOwner());
        KalahaBowl opponentKalahaBowl = firstBowl.getKalahaBowl(firstBowl.getOpponent());

        startingBowl.doMove();

        assertEquals(1, ownKalahaBowl.getNrOfSeedsInBowl());
        assertEquals(0, opponentKalahaBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void moveResultedInEmptyBowlAndOpposingBowlIsEmptySoNoSteal(){
        String expectedGameState = "4 4 4 4 0 4 0 4 0 4 4 4 4 8 1";
        NormalBowl firstBowl = new NormalBowl(expectedGameState);
        Bowl ownKalahaBowl = firstBowl.getKalahaBowl(firstBowl.getOwner());

        ((NormalBowl) firstBowl.getRightSideNeighbour()).doMove();

        assertEquals(0, ownKalahaBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void cannotDoMoveBecauseGameIsFinished(){
        String expectedGameState = "0 0 0 0 0 0 24 4 4 4 4 4 4 0 1";
        NormalBowl loadedGame = new NormalBowl(expectedGameState);

        boolean canIDoMove = loadedGame.doMove();

        assertFalse(canIDoMove);
    }

    @Test
    public void cannotDoMoveBecauseBowlIsEmpty() {
        NormalBowl firstBowl = new NormalBowl();
        firstBowl.transferAllOwnSeedsToKalaha(firstBowl.getOwner());

        boolean canIDoMove = firstBowl.doMove();

        assertFalse(canIDoMove);
    }

    @Test
    public void canDoMoveWhenGameIsNotYetFinished(){
        NormalBowl firstBowl = new NormalBowl();

        boolean canIPerformThisMove = firstBowl.doMove();

        assertTrue(canIPerformThisMove);
    }

    @Test
    public void cannotDoMoveWhenSelectedBowlIsFromOpponent(){
        NormalBowl firstBowl = new NormalBowl();
        firstBowl.getOwner().switchTurn();

        boolean canIPerformThisMove = firstBowl.doMove();

        assertFalse(canIPerformThisMove);
    }


    @Test
    public void moveIsSwitchedToOpponentAfterTurn(){
        NormalBowl normalBowl = new NormalBowl();

        normalBowl.doMove();

        assertFalse(normalBowl.getOwner().getIsMyTurn());
        assertTrue(normalBowl.getOpponent().getIsMyTurn());
    }

    @Test
    public void moveIsNotSwitchedAfterTurnEndsInKalaha(){
        NormalBowl normalBowl = new NormalBowl();
        NormalBowl thirdBowl = (NormalBowl) normalBowl.getNthNeighbourBowl(2);

        thirdBowl.doMove();

        assertTrue(thirdBowl.getOwner().getIsMyTurn());
        assertFalse(thirdBowl.getOpponent().getIsMyTurn());
    }

    @Test
    public void correctNumberOfBowlsAddedSeedByMove(){
        NormalBowl firstBowl = new NormalBowl();
        NormalBowl fifthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(4);
        NormalBowl sixthBowl = (NormalBowl) fifthBowl.getRightSideNeighbour();

        firstBowl.doMove();

        assertEquals(0, firstBowl.getNrOfSeedsInBowl());
        assertEquals(5, fifthBowl.getNrOfSeedsInBowl());
        assertEquals(4, sixthBowl.getNrOfSeedsInBowl());

    }

    @Test
    public void stealMoveCausesCorrectNrOfSeedsPerBowl(){
        String expectedGameState = "5 4 4 4 4 0 1 5 5 0 5 5 5 1 1";
        NormalBowl firstBowl = new NormalBowl(expectedGameState);
        NormalBowl secondBowl = (NormalBowl) firstBowl.getRightSideNeighbour();
        NormalBowl sixthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(5);
        Bowl ownKalaha = firstBowl.getKalahaBowl(firstBowl.getOwner());

        secondBowl.doMove();

        assertEquals(0, secondBowl.getNrOfSeedsInBowl());
        assertEquals(7, ownKalaha.getNrOfSeedsInBowl());
        assertEquals(0, sixthBowl.getNrOfSeedsInBowl());
    }

    @Test
    public void bugFixedMoveEndingInOwnKalahaCreatesExtraSeedInFirstBowlAfterOwnKalaha(){
        NormalBowl firstBowl = new NormalBowl();
        NormalBowl thirdBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(2);
        NormalBowl fourthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(3);
        NormalBowl fifthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(4);
        NormalBowl sixthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(5);
        KalahaBowl ownKalaha = firstBowl.getKalahaBowl(firstBowl.getOwner());
        NormalBowl firstOpponentNormalBowl = (NormalBowl) ownKalaha.getRightSideNeighbour();

        thirdBowl.doMove();

        assertEquals(5, fourthBowl.getNrOfSeedsInBowl());
        assertEquals(5, fifthBowl.getNrOfSeedsInBowl());
        assertEquals(5, sixthBowl.getNrOfSeedsInBowl());
        assertEquals(1, ownKalaha.getNrOfSeedsInBowl());
        assertEquals(4, firstOpponentNormalBowl.getNrOfSeedsInBowl());

    }

    @Test
    public void player2PerformsStealMoveCheckCorrectNumberOfSeedsInKalaha(){
        NormalBowl firstBowl = new NormalBowl();
        NormalBowl secondBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(1);
        NormalBowl firstBowlOfSecondPlayer = (NormalBowl) firstBowl.getNthNeighbourBowl(7);
        NormalBowl lastBowlOfSecondPlayer = (NormalBowl) firstBowl.getNthNeighbourBowl(12);
        KalahaBowl kalahaBowlOfSecondPlayer = firstBowl.getKalahaBowl(firstBowl.getOpponent());

        firstBowl.doMove();
        lastBowlOfSecondPlayer.doMove();
        secondBowl.doMove();
        firstBowlOfSecondPlayer.doMove();

        assertEquals(3, kalahaBowlOfSecondPlayer.getNrOfSeedsInBowl());
        assertEquals(0, firstBowlOfSecondPlayer.getNrOfSeedsInBowl());
        assertEquals(0, firstBowl.getNrOfSeedsInBowl());

    }

    @Test
    public void finalStageOfGameMoveWithSingleSeedLeftCausesGameToFinishOnlyAfterLastMove(){
        String expectedGameState = "0 0 0 1 0 0 23 0 0 4 4 4 4 8 1";
        NormalBowl firstBowl = new NormalBowl(expectedGameState);
        NormalBowl fourthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(3);
        NormalBowl fifthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(4);
        NormalBowl sixthBowl = (NormalBowl) firstBowl.getNthNeighbourBowl(5);

        fourthBowl.doMove();
        assertFalse(fourthBowl.isGameFinished());
        fourthBowl.getOwner().switchTurn();
        fifthBowl.doMove();
        assertFalse(fifthBowl.isGameFinished());
        fifthBowl.getOwner().switchTurn();
        sixthBowl.doMove();
        assertTrue(sixthBowl.isGameFinished());
    }

}
