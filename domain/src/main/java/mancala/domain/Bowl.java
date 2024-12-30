package mancala.domain;

public abstract class Bowl {
    public static final int STARTING_NR_OF_SEEDS_IN_BOWL = 4;
    public static final int HALF_SEEDS_OF_THE_GAME = 24;
    public static final int NR_OF_BOWLS_PER_PLAYER = 7;
    private int nrOfSeedsInBowl;
    private final Player bowlOwner;
    private final Bowl rightSideNeighbour;

    public Bowl(){
        int nrOfCurrentBowl = 1;
        this.nrOfSeedsInBowl = STARTING_NR_OF_SEEDS_IN_BOWL;
        this.bowlOwner = new Player();
        String defaultGameState = "4 4 4 4 4 4 0 4 4 4 4 4 4 0 1";
        String[] gameState = defaultGameState.split(" ");
        this.rightSideNeighbour = new NormalBowl(nrOfCurrentBowl + 1, this, gameState);
    }

    public Bowl(String gameStateToLoadIn){
        int nrOfCurrentBowl = 1;
        String[] gameState = gameStateToLoadIn.split(" ");
        this.nrOfSeedsInBowl = Integer.parseInt(gameState[nrOfCurrentBowl-1]);
        this.bowlOwner = new Player();
        this.rightSideNeighbour = new NormalBowl(nrOfCurrentBowl + 1, this, gameState);

        boolean player1turn = Integer.parseInt(gameState[gameState.length - 1]) == 1;
        if (!player1turn){
            this.bowlOwner.switchTurn();
        }
    }

    protected Bowl(int nrOfCurrentBowl, Bowl firstBowlOfTheGame, String[] gameState){
        this.nrOfSeedsInBowl = Integer.parseInt(gameState[nrOfCurrentBowl-1]);
        this.bowlOwner = getPlayerForCurrentBowlNR(nrOfCurrentBowl, firstBowlOfTheGame);

        if (nrOfCurrentBowl == 6 || nrOfCurrentBowl == 13){
            this.rightSideNeighbour = new KalahaBowl(nrOfCurrentBowl+1, firstBowlOfTheGame, gameState);
        }
        else if (nrOfCurrentBowl == 14){
            this.rightSideNeighbour = firstBowlOfTheGame;
        }
        else{
            this.rightSideNeighbour = new NormalBowl(nrOfCurrentBowl+1, firstBowlOfTheGame, gameState);
        }
    }

    private Player getPlayerForCurrentBowlNR(int nrOfCurrentBowl, Bowl firstBowlOfTheGame) {
        return (nrOfCurrentBowl <= 7) ? firstBowlOfTheGame.getOwner() : firstBowlOfTheGame.getOpponent();
    }

    public int getNrOfSeedsInBowl(){
        return this.nrOfSeedsInBowl;
    }

    void setNrOfSeedsInBowl(int newSeedCount){
        this.nrOfSeedsInBowl = newSeedCount;
    }

    public Bowl getRightSideNeighbour(){
        return this.rightSideNeighbour;
    }

    public Player getOwner(){
        return this.bowlOwner;
    }

    public Player getOpponent(){
        return this.getOwner().getOpponent();
    }

    abstract void keepOneSeedPassRemainingSeedsToNextBowlRecursively(int remainingSeeds, Player opponent);

    abstract Bowl orderToTransferAllOwnSeedsToOwnKalahaFromOtherBowlRecursively(boolean foundOwnKalaha, int distanceToOwnKalaha, Player playerToTransferTo);

    Bowl getNthNeighbourBowl(int nrOfNeighboursToTravel) {
        if (nrOfNeighboursToTravel == 0) {
            return this;
        }
        return this.getRightSideNeighbour().getNthNeighbourBowl(nrOfNeighboursToTravel - 1);
    }

    KalahaBowl getKalahaBowl(Player playerToGetKalahaBowlFrom) {
        if (this instanceof KalahaBowl && this.getOwner() == playerToGetKalahaBowlFrom) {
            return (KalahaBowl) this;
        }
        return this.getRightSideNeighbour().getKalahaBowl(playerToGetKalahaBowlFrom);
    }

    public boolean isGameFinished(){
        boolean previousBowlWasFromTargetPlayer = true;
        return this.orderFirstBowlOfCurrentPlayerToCheckIfAllBowlsOfOneSideAreEmpty(this.getOwner(), previousBowlWasFromTargetPlayer);
    }

    private boolean orderFirstBowlOfCurrentPlayerToCheckIfAllBowlsOfOneSideAreEmpty(Player targetPlayer, boolean previousOrCurrentBowlWasFromTargetPlayer){
        if (this.getOwner() != targetPlayer) {
            previousOrCurrentBowlWasFromTargetPlayer = false;
        }
        if (this.getOwner() == targetPlayer && !previousOrCurrentBowlWasFromTargetPlayer ){
            return this.checkIfAllBowlsOfOneSideAreEmptyRecursively();
        }
        return this.getRightSideNeighbour().orderFirstBowlOfCurrentPlayerToCheckIfAllBowlsOfOneSideAreEmpty(targetPlayer, previousOrCurrentBowlWasFromTargetPlayer);
    }

    private boolean checkIfAllBowlsOfOneSideAreEmptyRecursively() {
        if (this instanceof KalahaBowl) {
            return true;
        }
        if (this.getNrOfSeedsInBowl() > 0) {
            return false;
        }
        return this.getRightSideNeighbour().checkIfAllBowlsOfOneSideAreEmptyRecursively();
    }

    public Player getWinningPlayer() {
        int totalSeedsOfCurrentPlayer = 0;
        int nrOfOwnBowlsChecked = 0;
        totalSeedsOfCurrentPlayer = getNrOfSeedsTotalOfCurrentPlayer(this.getOwner(), totalSeedsOfCurrentPlayer, nrOfOwnBowlsChecked);
        return getWinningPlayerFromTotalNumberOfSeedsForPlayer(totalSeedsOfCurrentPlayer);
    }

    private Player getWinningPlayerFromTotalNumberOfSeedsForPlayer(int totalSeedsOfCurrentPlayer) {
        if (totalSeedsOfCurrentPlayer == HALF_SEEDS_OF_THE_GAME){
            return null;
        }
        else if (totalSeedsOfCurrentPlayer > HALF_SEEDS_OF_THE_GAME){
            return getOwner();
        }
        return getOpponent();
    }

    private int getNrOfSeedsTotalOfCurrentPlayer(Player currentPlayer, int totalSeedsOfCurrentPlayer, int nrOfOwnBowlsChecked){
        if (this.getOwner() == currentPlayer) {
            if (nrOfOwnBowlsChecked < NR_OF_BOWLS_PER_PLAYER){
                totalSeedsOfCurrentPlayer += this.getNrOfSeedsInBowl();
                return this.getRightSideNeighbour().getNrOfSeedsTotalOfCurrentPlayer(currentPlayer,totalSeedsOfCurrentPlayer, nrOfOwnBowlsChecked+1);
            }
            else if (nrOfOwnBowlsChecked == NR_OF_BOWLS_PER_PLAYER) {
                return totalSeedsOfCurrentPlayer;
            }
        }
        return this.getRightSideNeighbour().getNrOfSeedsTotalOfCurrentPlayer(currentPlayer,totalSeedsOfCurrentPlayer, nrOfOwnBowlsChecked);
    }

    Bowl getOpposingBowl() {
        int distanceToKalaha = calculateDistanceToClosestKalaha();
        return getNthNeighbourBowl(distanceToKalaha*2);
    }

    private int calculateDistanceToClosestKalaha() {
        int distanceToKalaha = 0;
        return calculateDistanceToKalahaRecursively(distanceToKalaha);
    }

    private int calculateDistanceToKalahaRecursively(int distanceToKalaha) {
        if (this instanceof KalahaBowl) {
            return distanceToKalaha;
        }
        return this.getRightSideNeighbour().calculateDistanceToKalahaRecursively(distanceToKalaha + 1);
    }

    abstract Bowl orderOwnKalahaBowlToAddSeeds(int nrOfSeedsToAdd, Player targetOwner);

    public String toGameState(){
        StringBuilder gameState = new StringBuilder();
        boolean foundOpponentsKalaha = false;
        Player currentplayer = this.getOwner();

        gameState = toGameStateRecursively(gameState, foundOpponentsKalaha,currentplayer);
        gameState.append(currentplayer.getIsMyTurn() ? 1 : 0);
        return gameState.toString();
    }

    abstract StringBuilder toGameStateRecursively(StringBuilder gameState, boolean foundOpponentsKalaha, Player currentplayer);

    protected StringBuilder addCurrentSeedCountToGameState(StringBuilder gameState) {
        gameState.append(getNrOfSeedsInBowl());
        gameState.append(" ");
        return gameState;
    }

    protected void addCurrentSeedCountToGameStateWhenOpponentsKalahaIsFound(StringBuilder gameState, boolean foundOpponentsKalaha) {
        if (foundOpponentsKalaha){
            addCurrentSeedCountToGameState(gameState);
        }
    }

}