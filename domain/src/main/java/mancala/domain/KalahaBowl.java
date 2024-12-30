package mancala.domain;

public class KalahaBowl extends Bowl{

    protected KalahaBowl(int nrOfCurrentBowl, Bowl firstBowlOfTheGame, String[] gameState) {
        super(nrOfCurrentBowl, firstBowlOfTheGame, gameState);
        this.setNrOfSeedsInBowl(Integer.parseInt(gameState[nrOfCurrentBowl-1]));
    }

    public void addSeeds(int nrOfSeedsToAdd){
        int currentSeedCount = this.getNrOfSeedsInBowl();
        int newSeedCount = currentSeedCount + nrOfSeedsToAdd;
        this.setNrOfSeedsInBowl(newSeedCount);
    }

    @Override
    void keepOneSeedPassRemainingSeedsToNextBowlRecursively(int remainingSeeds, Player opponent) {
        keepTurnAtCurrentPlayerWhenTurnEndsInOwnKalaha(remainingSeeds, opponent);
        
        if (this.getOwner() != opponent) {
            this.addSeeds(1);
            this.getRightSideNeighbour().keepOneSeedPassRemainingSeedsToNextBowlRecursively(remainingSeeds - 1, opponent);
        }
        else{
            this.getRightSideNeighbour().keepOneSeedPassRemainingSeedsToNextBowlRecursively(remainingSeeds, opponent);
        }
    }

    private void keepTurnAtCurrentPlayerWhenTurnEndsInOwnKalaha(int remainingSeeds, Player opponent){
        if (remainingSeeds == 1 && this.getOwner() != opponent) {
            this.getOwner().switchTurn();
        }
    }

    Bowl orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(boolean foundOwnKalaha, int distanceToOwnKalaha, Player playerToTransferTo){
        foundOwnKalaha = true;
        return this.getRightSideNeighbour().orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(foundOwnKalaha, distanceToOwnKalaha-1, playerToTransferTo);
    }

    @Override
    Bowl orderOwnKalahaBowlToAddSeeds(int nrOfSeedsToAdd, Player targetOwner) {
        if (this.getOwner() == targetOwner){
            this.addSeeds(nrOfSeedsToAdd);
            return this;
        }
        else{
            return this.getRightSideNeighbour().orderOwnKalahaBowlToAddSeeds(nrOfSeedsToAdd, targetOwner);
        }

    }

    @Override
    protected StringBuilder toGameStateRecursively(StringBuilder gameState, boolean foundOpponentsKalaha, Player currentplayer) {
        if (this.getOwner() != currentplayer) {
            if (!foundOpponentsKalaha) {
                foundOpponentsKalaha = true;
            } else {
                return addCurrentSeedCountToGameState(gameState);
            }
        } else {
            addCurrentSeedCountToGameStateWhenOpponentsKalahaIsFound(gameState, foundOpponentsKalaha);
        }
        return this.getRightSideNeighbour().toGameStateRecursively(gameState, foundOpponentsKalaha, currentplayer);
    }
}