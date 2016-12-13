package edu.temple.fourcolorgame.MapModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import edu.temple.fourcolorgame.GameLogic.MapLogic;
import edu.temple.fourcolorgame.R;
import edu.temple.fourcolorgame.Utils.Intents;

/**
 * Created by Ben on 11/13/2016.
 */

//Model for the game board, represented as 2-d arrays with method to translate into bitmap
//Includes methods for determining whether or not a move is valid and if the game is over
public class Map {
    private int numTerritories;
    private int width;
    private int height;
    private int backgroundColor;
    private int edgeColor;
    private int[][] board, edges, adjacencyMatrix;
    private ArrayList<Territory> territories;
    private ArrayList<Point> basePoints;
    int[] colors;
    Context context;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }



    public Map(int numTerritories, int width, int height, int[] colors, Context context){
        this.context = context;
        this.colors = colors;
        this.numTerritories = numTerritories;
        this.width = width;
        this.height = height;
        backgroundColor = context.getResources().getColor(R.color.default_region);
        edgeColor = context.getResources().getColor(R.color.edge_color);

        //Build game board
        basePoints = MapLogic.generateBasePoints(this.numTerritories, this.width, this.height);
        board = new int[this.height][this.width];
        ArrayList<Point>corners = getCorners();
        MapLogic.build(board, basePoints, corners);

        //Retrieve list of edges and adjacency matrix for board
        HashMap<String, int[][]> edgesAndAdjacency = MapLogic.generateEdgesAndAdjacency(board, numTerritories);
        adjacencyMatrix = edgesAndAdjacency.get(MapLogic.adjacencyKey);
        edges = edgesAndAdjacency.get(MapLogic.edgeKey);

        territories = new ArrayList<>();
        fillTerritories();
        this.context = null;

    }

    //Retrieves the corners of the board
    private ArrayList<Point> getCorners(){
        ArrayList<Point> corners = new ArrayList<>();
        //Bottom left
        corners.add(new Point(0, 0));
        //Bottom right
        corners.add(new Point(width-1, 0));
        //Top right
        corners.add(new Point(width-1, height-1));
        //Bottom left
        corners.add(new Point(0, height-1));

        return corners;
    }

    //Adds all interior points to each territory
    private void fillTerritories(){
        //Create a territory for each base point
        for(int i = 0; i< basePoints.size(); i++){
            territories.add(new Territory(basePoints.get(i), backgroundColor));
        }
        //add included points for each territory
        for (int y = 0; y < height; y++){
            for (int x = 0; x<width; x++){
                Point p = new Point(x, y);
                if(edges[y][x] != 1){ //if the point isn't an edge, add it to the proper territory
                    territories.get(board[y][x]).addPoint(p);
                }
            }
        }
    }

    //Convert the 2d array representation into a bitmap
    public Bitmap createBitmap(){
        int[] map = new int[width*height];
        Log.d("WIDTHLOADING", String.valueOf(width));
        Log.d("WIDTHLOADING", String.valueOf(height));

        for(int y = 0; y<height; y++){
            for(int x = 0; x<width; x++){
                int position = y*width + x;
                int bit = edges[y][x];
                if(bit == 0) { //not an edge
                    map[position] = backgroundColor;
                } else if (bit == 1) { //an edge
                    map[position] = edgeColor;
                } else {
                    map[position] = edges[y][x];
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(map, width, height, Bitmap.Config.ARGB_8888); //each pixel stored on 4 bytes
        return bitmap;
    }

    //Retrieve all adjacent territories for a given territory
    public ArrayList<Territory> getAdjacentTerritories(Territory territory){
        int index  = territories.indexOf(territory);
        ArrayList<Territory>adjacentTerritories = new ArrayList<>();

        for(int i = 0; i<territories.size(); i++){
            if(i == index){
                //itself
                continue;
            }
            //If in adjacency matrix, add territory to adjacency list
            if(adjacencyMatrix[i][index] == 1){
                adjacentTerritories.add(territories.get(i));
            }
        }
        return  adjacentTerritories;

    }

    //Fill in a territory once a move has been determined as valid
    public int colorTerritory(Point p, int color){
        //Get the selected territory
        int index = board[p.getY()][p.getX()];
        Territory territory = territories.get(index);
        if(color == territory.getColor()){
            color = backgroundColor;
        }
        //Color the points
        ArrayList<Point> includedPoints = territory.getIncludedPoints();
        for(int i = 0; i<includedPoints.size(); i++){
            Point point = includedPoints.get(i);
            edges[point.getY()][point.getX()] = color;
        }

        territory.setColor(color);

        return territory.getSize();
    }

    //Checks the board to make sure that the given point is in an unclaimed territory
    //and that none of the territory's neighbors are the same color as the given color
    public boolean isValidMove(Point p, int color, int gameMode){
        if(color == backgroundColor){ //unclaimed territory
            return true;
        } else {
            int currentColor = territories.get(board[p.getY()][p.getX()]).getColor();
            Log.d("FALSECOLOR", String.valueOf(color));
            Log.d("FALSECOLOR", String.valueOf(currentColor));
            //Log.d("FALSECOLOR", String.valueOf(backgroundColor));
            if(currentColor == color){ //claimed but same color
                if(gameMode == Intents.puzzle) {
                    return true;
                } else {
                    return false;
                }
            } else if (currentColor != backgroundColor){ //claimed territory
                return false;
            }

            //Check adjacency list for territory to make sure no neighbors are the target color
            int[] adjacencyList = adjacencyMatrix[board[p.getY()][p.getX()]];
            for(int i = 0; i<adjacencyList.length; i++){
                if(adjacencyList[i] == 1){
                    if(territories.get(i).getColor() == color){
                        Log.d("FALSECOLOR", "2");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Check to see if point can potentially hold this color
    public boolean potentialMove(Point p, int color){
        if(color == backgroundColor) {
            return true;
        } else {
            int currentColor = territories.get(board[p.getY()][p.getX()]).getColor();
            if(currentColor != backgroundColor){
                return false;
            }

            //Check adjacency list for territory to make sure no neighbors are the target color
            int[] adjacencyList = adjacencyMatrix[board[p.getY()][p.getX()]];
            for(int i = 0; i<adjacencyList.length; i++){
                if(adjacencyList[i] == 1){
                    if(territories.get(i).getColor() == color){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Test to see if every territory has been colored
    public boolean isFilledOut(){

        for(int i = 0; i<territories.size(); i++){
            if(territories.get(i).getColor() == backgroundColor){
                return false;
            }
        }
        return true;
    }

    //Test to see if a specific color cannot move
    public boolean noMovesAvailable(int color){
        for(int i = 0; i<territories.size(); i++){
            Territory t = territories.get(i);
            Log.d("Territory", String.valueOf(i));
            if(t.getColor() == backgroundColor){
                if(potentialMove(t.getBase(), color)){
                    return false;
                }
            }
        }
        return true;
    }

    //Test to see if nobody can move
    public boolean noMovesAvailable(int[] colors){
        for(int i = 0; i<territories.size(); i++){
            Territory t = territories.get(i);
            if(t.getColor() == backgroundColor){
                if(potentialMove(t.getBase(), colors[0])
                        || potentialMove(t.getBase(), colors[1])
                        || potentialMove(t.getBase(), colors[2])
                        || potentialMove(t.getBase(), colors[3])
                        ) {
                    return false;
                }
            }
        }
        return true;
    }


}
