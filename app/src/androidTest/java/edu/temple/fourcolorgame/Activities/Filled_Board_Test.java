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
import edu.temple.fourcolorgame.Utils.Intents;

import static org.junit.Assert.assertEquals;

/**
 * Test to see that the board can tell when it is completely filled
 * A board with three territories is created
 * A computer player fills in all three territories
 * The board checks to see if it is filled out -> should return true
 */

@RunWith(JUnit4.class)
public class Filled_Board_Test {
    Context context;

    @Before
    public void getContext(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void board_is_filled() throws Exception {
        int[] colors = new int[]{1, 2, 3, 4};

        Board board = new Board(3, 540, 540, colors , context );

        ArrayList<Territory> territories = board.getTerritories();

        for(int i = 0; i<3; i++) {

            ComputerPlayerEasy comp = new ComputerPlayerEasy(board, colors[i], territories);

            Point p = comp.getNextMove();
            if(board.isValidMove(p, colors[i], Intents.comp)) {
                board.colorTerritory(p, colors[i]);
            }
        }

        boolean complete = board.isFilledOut();

        assertEquals(true, complete);
    }
}
