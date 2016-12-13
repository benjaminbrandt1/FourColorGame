package edu.temple.fourcolorgame.Activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.Utils.Intents;

import static org.junit.Assert.assertEquals;

/**
 * Test to see that a player cannot select a territory that has been filled with the color selected already
 */

@RunWith(JUnit4.class)
public class Multi_Fill_Claimed_Same_Color_Test {
    Context context;

    @Before
    public void getContext(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void fill_in_same_color_region_multi_mode() throws Exception {
        int[] colors = new int[]{1, 2, 3, 4};

        Board board = new Board(30, 540, 540, colors , context );

        Point p = new Point(300, 300);
        board.colorTerritory(p, colors[0]);
        boolean validMove = board.isValidMove(p, colors[0], Intents.multi);
        assertEquals(false, validMove);
    }
}
