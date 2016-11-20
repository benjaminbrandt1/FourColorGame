package edu.temple.fourcolorgame.Controllers;

import java.util.ArrayList;

import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.MapModels.Territory;

/**
 * Created by Ben on 11/19/2016.
 */

public interface ComputerPlayer {

    Point getNextMove();
}
