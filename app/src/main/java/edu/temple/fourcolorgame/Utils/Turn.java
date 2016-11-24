package edu.temple.fourcolorgame.Utils;

/**
 * Created by Ben on 11/18/2016.
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
