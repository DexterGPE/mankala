package mancala.domain;

import java.util.Scanner;

public class UserInterface_TEMP {
    public static void main(String[] args) {
        NormalBowl firstBowl = new NormalBowl();
        Scanner scanner = new Scanner(System.in);

        while (!firstBowl.isGameFinished() && !firstBowl.getKalahaBowl(firstBowl.getOpponent()).isGameFinished()){
            printTotalAndPrintBoard(firstBowl);
            printWhoseTurnItIs(firstBowl);

            Integer input = getInputAndCheckIt(scanner);
            if (input == null) continue;

            NormalBowl bowlToPlay = (NormalBowl) firstBowl.getNthNeighbourBowl(input);
            bowlToPlay.doMove();
            printWinnerIfFinished(firstBowl);

        }
    }

    private static void printWinnerIfFinished(NormalBowl firstBowl) {
        if (firstBowl.isGameFinished()){
            if (firstBowl.getWinningPlayer() == firstBowl.getOwner()) {
                System.out.println("Game ended, player1 won!");
            }
            else{
                System.out.println("Game ended, player2 won!");
            }
        }
    }

    private static Integer getInputAndCheckIt(Scanner scanner) {
        System.out.println("Enter int for move at bowlNr:");
        int input = scanner.nextInt() -1;
        if (input == 6 || input == 13){
            System.out.println("cannot play kalahaBowls");
            return null;
        }
        return input;
    }

    private static void printWhoseTurnItIs(NormalBowl firstBowl) {
        if (firstBowl.getOwner().getIsMyTurn()){
            System.out.println("Player 1 next turn: ");
        }

        else if (firstBowl.getOpponent().getIsMyTurn()) {
            System.out.println("Player 2 next turn: ");
        }
    }

    private static void printTotalAndPrintBoard(NormalBowl firstBowl) {
        int total = 0;
        for (int i=0;i<14;i++) {
            Bowl currentBowl = firstBowl.getNthNeighbourBowl(i);
            System.out.println((i+1)+" -> "+currentBowl.getNrOfSeedsInBowl()+" Seeds");
            if (i == 6){
                System.out.println();
            }
            total+= currentBowl.getNrOfSeedsInBowl();
        }
        System.out.println("Total nr of seeds: "+ total);
    }
}
