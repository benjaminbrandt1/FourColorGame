package edu.temple.fourcolorgame.MapModels;

/**
 * Created by Ben on 11/13/2016.
 */

//This point serves as the center of a territory
public class BasePoint extends Point {
    int territory;

    public BasePoint(int x, int y, int territory){
        super(x, y);
        this.territory = territory;
    }

    public int getTerritory(){
        return territory;
    }
}
