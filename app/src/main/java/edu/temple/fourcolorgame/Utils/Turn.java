package edu.temple.fourcolorgame.Utils;

/**
 * Iterator to handle determining which turn it is (from 0 to 3)
 * Player 1 has 0 and 1 and Player 2 has 2 and 3
 * Thus, the order should go 0, 2, 1, 3, 0, 2, 1, 3, etc...
 */

public class Turn {
    private int turn;

    public Turn (){
        turn = 0;
    }

    public void next(){
        turn++;
        if(turn == 3){
            turn = 0;
        }
        turn++;
        if(turn == 5){
            turn = 0;
        }
    }

    public int getTurn(){
        return turn;
    }
}
