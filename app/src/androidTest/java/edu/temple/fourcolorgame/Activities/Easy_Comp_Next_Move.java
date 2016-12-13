package edu.temple.fourcolorgame.Activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import edu.temple.fourcolorgame.GameLogic.ComputerPlayerEasy;
import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.MapModels.Territory;

import static org.junit.Assert.assertEquals;

/**
 * Test to see that computer player chooses next largest territory
 */

@RunWith(JUnit4.class)
public class Easy_Comp_Next_Move {
    Context context;

    @Before
    public void getContext(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void comp_easy_next_move() throws Exception {
        int[] colors = new int[]{1, 2, 3, 4};

        Board board = new Board(30, 540, 540, colors , context );

        ArrayList<Territory> territories = board.getTerritories();

        Territory max = territories.get(0);

        for (Territory t:territories) {

                if(t.getSize() >= max.getSize()){
                    max = t;
                }
        }

        ComputerPlayerEasy comp = new ComputerPlayerEasy(board, colors[1], territories);

        Point move = comp.getNextMove();

        int moveSize = board.colorTerritory(move, colors[1]);

        assertEquals(max.getSize(), moveSize);
    }
}
