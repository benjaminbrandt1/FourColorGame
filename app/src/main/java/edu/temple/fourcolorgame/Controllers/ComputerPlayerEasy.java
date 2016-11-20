package edu.temple.fourcolorgame.Controllers;

import java.util.ArrayList;

import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.MapModels.Territory;
import edu.temple.fourcolorgame.Utils.Intents;

/**
 * Created by Ben on 11/19/2016.
 */
//Easy computer logic --> selects the next largest territory available
    //TODO JUST USE COLLECTIONS.SORT
public class ComputerPlayerEasy implements ComputerPlayer {
    private Board board;
    private int selectedColor;
    private ArrayList<Territory> territories;

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public ComputerPlayerEasy(Board board, int selectedColor, ArrayList<Territory> territories) {
        this.board = board;
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
            if(board.isValidMove(p, selectedColor, Intents.comp)){
                max = territories.get(i);
            } else {
                i++;
            }
        }

        while(i < territories.size()){
            if (territories.get(i).getSize() > max.getSize()) {
                Point p = territories.get(i).getBase();
                if (board.isValidMove(p, selectedColor, Intents.comp)) {
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
