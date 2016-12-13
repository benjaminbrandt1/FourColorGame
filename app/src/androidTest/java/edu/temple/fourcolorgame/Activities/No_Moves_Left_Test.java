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
 * Test to see that the board can tell when no moves are left for any color
 * A board is created with 2 territories and only one color available
 * One territory is colored and the board checks to see if no moves remain
 * Should return true
 */

@RunWith(JUnit4.class)
public class No_Moves_Left_Test {
    Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void no_moves_all_colors() throws Exception {
        int[] colors = new int[]{1, 1, 1, 1};

        Board board = new Board(3, 540, 540, colors, context);

        ArrayList<Territory> territories = board.getTerritories();

        ComputerPlayerEasy comp = new ComputerPlayerEasy(board, colors[0], territories);

        Point p = comp.getNextMove();

        if (board.isValidMove(p, colors[0], Intents.comp)) {
            board.colorTerritory(p, colors[0]);
        }

        boolean complete = board.noMovesAvailable(colors);

        assertEquals(true, complete);
    }
}
