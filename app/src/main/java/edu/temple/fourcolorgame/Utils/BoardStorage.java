package edu.temple.fourcolorgame.Utils;

import android.app.Application;

import edu.temple.fourcolorgame.MapModels.Map;

/**
 * Created by Ben on 11/18/2016.
 */

public class BoardStorage extends Application {
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private Map map;
}
