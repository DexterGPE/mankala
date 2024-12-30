package mancala.domain;

public class NormalBowl extends Bowl{

    public NormalBowl() {
        super();
    }

    public NormalBowl(String gameState) {
        super(gameState);
    }

    protected NormalBowl(int nrOfCurrentBowl, Bowl firstBowlOfTheGame, String[] gameState) {
        super(nrOfCurrentBowl, firstBowlOfTheGame, gameState);
    }

    private void removeSeeds(int nrOfSeedsToRemove){
        int currentSeedCount = this.getNrOfSeedsInBowl();
        int newSeedCount = currentSeedCount - nrOfSeedsToRemove;
        this.setNrOfSeedsInBowl(newSeedCount);
    }

    private void addOneSeed(){
        int currentSeedCount = this.getNrOfSeedsInBowl();
        setNrOfSeedsInBowl(currentSeedCount + 1);
    }

    @Override
    void keepOneSeedPassRemainingSeedsToNextBowlRecursively(int remainingSeeds, Player opponent) {
            remainingSeeds = addOneOfRemainingSeedsToCurrentBowl(remainingSeeds);
        if (this.thisIsStealMove(opponent, remainingSeeds)){
            steal();
        }
        else if (remainingSeeds > 0) {
            this.getRightSideNeighbour().keepOneSeedPassRemainingSeedsToNextBowlRecursively(remainingSeeds, opponent);
        }
    }

    private int addOneOfRemainingSeedsToCurrentBowl(int remainingSeeds) {
        if (remainingSeeds > 0) {
            this.addOneSeed();
            remainingSeeds--;
        }
        return remainingSeeds;
    }

    private boolean thisIsStealMove(Player opponentOfPlayerInCurrentTurn, int remainingSeedsToPass) {
        Bowl opposingBowl = this.getOpposingBowl();
        if (this.getOwner() == opponentOfPlayerInCurrentTurn){
            return false;
        }
        return this.getNrOfSeedsInBowl() == 1 && opposingBowl.getNrOfSeedsInBowl() != 0 && remainingSeedsToPass == 0;
    }

    private void steal() {
        this.orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowl();
        this.transferAllOwnSeedsToKalaha(this.getOwner());
    }

    public boolean doMove(){
        if (!isGameFinished() && this.getOwner().getIsMyTurn() && getNrOfSeedsInBowl() > 0){
            int nrOfSeedsToPassAround = this.getNrOfSeedsInBowl();

            this.removeSeeds(nrOfSeedsToPassAround);
            this.passAroundSeedsToNextBowl(nrOfSeedsToPassAround);

            this.getOwner().switchTurn();
            return true;
        }
        return false;
    }

    private void passAroundSeedsToNextBowl(int nrOfSeedsToPassAround) {
        Player opponent = this.getOpponent();
        Bowl nextBowl = this.getRightSideNeighbour();
        nextBowl.keepOneSeedPassRemainingSeedsToNextBowlRecursively(nrOfSeedsToPassAround, opponent);
    }

    Bowl transferAllOwnSeedsToKalaha(Player playerToTransferTo) {
        int nrOfSeedsToTransfer = this.getNrOfSeedsInBowl();

        this.removeSeeds(nrOfSeedsToTransfer);
        orderOwnKalahaBowlToAddSeeds(nrOfSeedsToTransfer, playerToTransferTo);
        return this;
    }

    @Override
    Bowl orderOwnKalahaBowlToAddSeeds(int nrOfSeedsToAdd, Player targetOwner){
        return this.getRightSideNeighbour().orderOwnKalahaBowlToAddSeeds(nrOfSeedsToAdd, targetOwner);
    }

    void orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowl() {
        boolean foundOwnKalaha = false;
        int distanceToOwnKalaha = 0;
        Player playerToTransferTo = this.getOwner();
        orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(foundOwnKalaha, distanceToOwnKalaha, playerToTransferTo);
    }

    Bowl orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(boolean foundOwnKalaha, int distanceToOwnKalaha, Player playerToTransferTo){
        if (distanceToOwnKalaha == 0 && this.getOwner() != playerToTransferTo) {
            return this.transferAllOwnSeedsToKalaha(playerToTransferTo);
        }
        else if (foundOwnKalaha) {
            return this.getRightSideNeighbour().orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(foundOwnKalaha, distanceToOwnKalaha-1, playerToTransferTo);
        }
        return this.getRightSideNeighbour().orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(foundOwnKalaha, distanceToOwnKalaha+1, playerToTransferTo);
    }

    @Override
    protected StringBuilder toGameStateRecursively(StringBuilder gameState, boolean foundOpponentsKalaha, Player currentplayer){
        addCurrentSeedCountToGameStateWhenOpponentsKalahaIsFound(gameState, foundOpponentsKalaha);
        return this.getRightSideNeighbour().toGameStateRecursively(gameState, foundOpponentsKalaha,currentplayer);
    }
}
