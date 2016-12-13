package edu.temple.fourcolorgame.Activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import edu.temple.fourcolorgame.GameLogic.ComputerPlayerEasy;
import edu.temple.fourcolorgame.MapModels.Map;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.MapModels.Territory;
import edu.temple.fourcolorgame.Utils.Intents;

import static org.junit.Assert.assertEquals;

/**
 * Test to see that the board can tell when no moves are left for a specific color
 * A board is created with 2 territories
 * One territory is colored and the board checks to see if no moves remain for that color
 * Should return true
 */

public class No_Moves_Left_Specific_Color {
    Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void no_moves_one_color() throws Exception {
        int[] colors = new int[]{1, 2, 3, 4};

        Map map = new Map(2, 540, 540, colors, context);

        ArrayList<Territory> territories = map.getTerritories();


        ComputerPlayerEasy comp = new ComputerPlayerEasy(map, colors[0], territories);

        Point p = comp.getNextMove();
        if (map.isValidMove(p, colors[0], Intents.comp)) {
            map.colorTerritory(p, colors[0]);
        }

        boolean complete = map.noMovesAvailable(colors[0]);

        assertEquals(true, complete);
    }
}
