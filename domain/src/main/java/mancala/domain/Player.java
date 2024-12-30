package mancala.domain;

public class Player {

    private boolean isMyTurn;
    private final Player opponent;

    public Player(){
        this.isMyTurn = true;
        this.opponent = new Player(false, this);
    }

    public Player (boolean isMyTurn, Player opponent){
        this.isMyTurn = isMyTurn;
        this.opponent = opponent;
    }

    public boolean getIsMyTurn() {
        return isMyTurn;
    }

    private void setIsMyTurn(boolean isMyTurn){
        this.isMyTurn = isMyTurn;
    }

    public Player getOpponent() {
        return opponent;
    }

    void switchTurn(){
        if (this.getIsMyTurn()) {
            this.setIsMyTurn(false);
            this.getOpponent().setIsMyTurn(true);
        }
        else {
            this.setIsMyTurn(true);
            this.getOpponent().setIsMyTurn(false);
        }
    }
}
