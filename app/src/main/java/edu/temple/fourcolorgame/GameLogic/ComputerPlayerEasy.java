package edu.temple.fourcolorgame.GameLogic;

import java.util.ArrayList;

import edu.temple.fourcolorgame.MapModels.Map;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.MapModels.Territory;
import edu.temple.fourcolorgame.Utils.Intents;

/**
 * Created by Ben on 11/19/2016.
 */
//Easy computer logic --> selects the next largest territory available
    //TODO JUST USE COLLECTIONS.SORT
public class ComputerPlayerEasy implements ComputerPlayer {
    private Map map;
    private int selectedColor;
    private ArrayList<Territory> territories;

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public ComputerPlayerEasy(Map map, int selectedColor, ArrayList<Territory> territories) {
        this.map = map;
        this.selectedColor = selectedColor;
        this.territories = territories;
    }


    @Override
    public Point getNextMove(){
        Territory maxSize = findMaxTerritory();
        if(maxSize != null) {
            return maxSize.getBase();
        } else { return null;}
    }

    private Territory findMaxTerritory(){
        Territory max = null;
        int i = 0;
        while(max == null){
            if(i > territories.size()){
                return null;
            }
            Point p = territories.get(i).getBase();
            if(map.isValidMove(p, selectedColor, Intents.comp)){
                max = territories.get(i);
            } else {
                i++;
            }
        }

        while(i < territories.size()){
            if (territories.get(i).getSize() > max.getSize()) {
                Point p = territories.get(i).getBase();
                if (map.isValidMove(p, selectedColor, Intents.comp)) {
                    max = territories.get(i);
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }
        return max;
    }


}
