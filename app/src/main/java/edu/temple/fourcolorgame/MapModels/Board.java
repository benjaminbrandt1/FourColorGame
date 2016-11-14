package edu.temple.fourcolorgame.MapModels;

import android.graphics.Bitmap;

import java.util.ArrayList;

import edu.temple.fourcolorgame.Controllers.BoardLogic;
import edu.temple.fourcolorgame.R;

/**
 * Created by Ben on 11/13/2016.
 */

//Model for the game board, represented as 2-d arrays with method to translate into bitmap
public class Board {
    private int numTerritories, width, height, backgroundColor, edgeColor;
    private int[][] board, edges, adjacencyMatrix;
    private ArrayList<Territory> territories;
    private ArrayList<Point> basePoints;
    int[] colors;

    public Board(int numTerritories, int width, int height, int[] colors){
        this.colors = colors;
        this.numTerritories = numTerritories;
        this.width = width;
        this.height = height;
        backgroundColor = R.color.default_region;
        edgeColor = R.color.edge_color;

        //Build game board
        basePoints = BoardLogic.getBasePoints(this.numTerritories, this.width, this.height);
        board = new int[this.height][this.width];
        ArrayList<Point>corners = getCorners();
        BoardLogic.build(board, basePoints, corners);

        //Retrieve list of edges and adjacency matrix for board
        ArrayList<int[][]> edgesAndAdjacency = BoardLogic.generateEdgesAndAdjacency(board, numTerritories);
        adjacencyMatrix = edgesAndAdjacency.get(0);
        edges = edgesAndAdjacency.get(1);

        territories = new ArrayList<>();
        fillTerritories();

    }

    private ArrayList<Point> getCorners(){
        ArrayList<Point> corners = new ArrayList<>();
        corners.add(new Point(0, 0));
        corners.add(new Point(width-1, 0));
        corners.add(new Point(width-1, height-1));
        corners.add(new Point(0, height-1));

        return corners;
    }

    private void fillTerritories(){
        //Create a territory for each base point
        for(int i = 0; i< basePoints.size(); i++){
            territories.add(new Territory(basePoints.get(i)));
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

    public Bitmap createBitmap(){
        int[] map = new int[width*height];

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

    public int colorTerritory(Point p, int color){
        //Get the selected territory
        int index = board[p.getY()][p.getX()];
        Territory territory = territories.get(index);
        //TODO Make sure this is only during puzzle mode
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

    public boolean isValidMove(Point p, int color){
        if(color == backgroundColor){ //unclaimed territory
            return true;
        } else {
            int currentColor = territories.get(board[p.getY()][p.getX()]).getColor();
            if(currentColor != backgroundColor){ //claimed territory
                return false;
            } else if (currentColor == color){ //claimed, but same color
                return true;
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
        return false;
    }

    //Check to see if game over
    public boolean isValidEndState(Point p, int color){
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
        return false;
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
            if(t.getColor() == backgroundColor){
                if(isValidEndState(t.getBase(), color)){
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
                if(isValidEndState(t.getBase(), colors[0])
                        || isValidEndState(t.getBase(), colors[1])
                        || isValidEndState(t.getBase(), colors[2])
                        || isValidEndState(t.getBase(), colors[3])
                        ) {
                    return false;
                }
            }
        }
        return true;
    }


}
