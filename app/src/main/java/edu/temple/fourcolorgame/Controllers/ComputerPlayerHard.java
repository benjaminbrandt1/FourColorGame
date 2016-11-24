package edu.temple.fourcolorgame.Controllers;



import java.util.ArrayList;
import java.util.Collections;

import edu.temple.fourcolorgame.MapModels.Board;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.MapModels.Territory;
import edu.temple.fourcolorgame.Utils.Intents;

/**
 * Created by Ben on 11/19/2016.
 */
//Chooses the largest territory possible, but avoids blocking territories that are safe
    //Safe territories are ones that the opponent cannot fill
public class ComputerPlayerHard implements ComputerPlayer {
    private Point nextMove;
    private Board board;
    private int selectedColor;
    private int[] colors;
    private int compsOtherColor;

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    private ArrayList<Territory> territories;

    public ComputerPlayerHard(Board board, int selectedColor, ArrayList<Territory> territories, int[] colors) {
        this.board = board;
        this.selectedColor = selectedColor;
        this.territories = territories;
        this.colors = colors;
        if(selectedColor == colors[2]){
            compsOtherColor = colors[3];
        } else {
            compsOtherColor = colors[2];
        }
    }

    @Override
    public Point getNextMove() {
        //Get all possible moves
        ArrayList<Territory> possibleMoves = getPossibleMoves();
        if(possibleMoves.size() == 0){
            return null;
        }

        //Get all regions that the opponent cannot fill
        ArrayList<Territory> protectedMoves = getProtectedMoves(possibleMoves);
        ArrayList<Territory> dontBlockList = new ArrayList<>();

        Collections.sort(possibleMoves);
        //TODO switch order to 0 to size()?
        for(int i = possibleMoves.size() - 1; i>= 0; i--){
            Territory potential = possibleMoves.get(i);
            if(protectedMoves.contains(potential)){
                if(!board.isValidMove(potential.getBase(), compsOtherColor, Intents.comp)){
                    dontBlockList.add(potential);
                }
                continue;

            }
            for(Territory adjacent : board.getAdjacentTerritories(potential)){
                if (dontBlockList.contains(adjacent)){
                    continue;
                }
            }
            return potential.getBase();
        }

        //No smart moves possible, just pick the largest possible move
        return possibleMoves.get(possibleMoves.size() - 1).getBase();

    }

    private ArrayList<Territory> getProtectedMoves(ArrayList<Territory> moves){
        boolean color1 = false;
        boolean color2 = false;
        ArrayList<Territory> protectedMoves = new ArrayList<>();
        for(Territory move : moves){
            for(Territory adjacent : board.getAdjacentTerritories(move)){
                if(adjacent.getColor() == colors[0]){
                    color1 = true;
                } else if (adjacent.getColor() == colors[1]){
                    color2 = true;
                }
            }
            if(color1 && color2){
                protectedMoves.add(move);
            }
            color1 = false;
            color2 = false;
        }
        return protectedMoves;
    }

    private ArrayList<Territory> getPossibleMoves(){
        ArrayList<Territory> moves = new ArrayList<>();
        for(Territory potential : territories) {
            if (board.isValidMove(potential.getBase(), selectedColor, Intents.comp)) {
                moves.add(potential);
            }
        }
        return moves;
    }
}
