package edu.temple.fourcolorgame.Controllers;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.temple.fourcolorgame.MapModels.BasePoint;
import edu.temple.fourcolorgame.MapModels.Point;
import edu.temple.fourcolorgame.Utils.PointAndDistance;

/**
 * Created by Ben on 11/13/2016.
 */

public class BoardLogic {
    public static final String edgeKey = "edges";
    public static final String adjacencyKey = "adj";
    public static final String distanceKey = "dist";
    public static final String pointKey = "point";

    //Given the desired number of territories, generate the starting locations of all territories
    public static ArrayList<Point> generateBasePoints(int numTerritories, int width, int height) {
        ArrayList<Point> basePoints = new ArrayList<>(numTerritories);

        //Generate minimum distance required for each territory from its neighbors
        double widthPerPoint = ((double) width) / numTerritories;
        double heightPerPoint = ((double)height)/ numTerritories;
        double minDistance = Math.sqrt(Math.pow(widthPerPoint, 2) + Math.pow(heightPerPoint, 2));

        int numPoints = 0;
        while(numPoints < numTerritories){

            Point newBase = getRandomPoint(height, width, basePoints.size());

            //find nearest neighboring territory, if it is far enough away, add the new territory
            PointAndDistance pnd = BoardLogic.getNearestNeighbor(basePoints, newBase);

            if(pnd == null){
                basePoints.add(newBase);
                numPoints++;
            } else {
                double distance = pnd.getDistance();
                if(distance >= minDistance){
                    basePoints.add(newBase);
                    numPoints++;
                }
            }
        }

        return basePoints;
    }

    private static Point getRandomPoint(int height, int width, int territory) {
        //generate random ordered pair for location of territory
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        Point base = new BasePoint(x, y, territory);
        return base;
    }

    //get the point closest to the point specified and its distance
    private static PointAndDistance getNearestNeighbor(ArrayList<Point> basePoints, Point base) {

        if(basePoints.size() == 0){
            return null;
        }

        Point neighbor = basePoints.get(0);
        double distance = neighbor.getDistanceTo(base);

        for(int i = 1; i<basePoints.size(); i++){
            double temp = base.getDistanceTo(basePoints.get(i));
            if(temp < distance){
                neighbor = basePoints.get(i);
                distance = temp;
            }
        }

        return new PointAndDistance(neighbor, distance);
    }

    //Fill out the board based on the given base points for territories
    public static void build(int[][] board, ArrayList<Point> basePoints, ArrayList<Point> corners) {

        if(corners.get(0).isSamePoint(corners.get(1)) || corners.get(1).isSamePoint(corners.get(2))){
            //All corners are the same
            for(int i = 0; i < corners.size(); i++){
                Point corner = corners.get(i);
                int territory = getCornerTerritory(basePoints, corner);
                board[corner.getY()][corner.getX()] = territory;
            }
        } else {
            //find and store the corner territories
            for(int i = 0; i<corners.size(); i++){
                Point corner = corners.get(i);
                int territory = getCornerTerritory(basePoints, corner);
                board[corner.getY()][corner.getX()] = territory;

            }

            int topLeft = board[corners.get(0).getY()][corners.get(0).getX()];
            int topRight = board[corners.get(1).getY()][corners.get(1).getX()];
            int bottomRight = board[corners.get(2).getY()][corners.get(2).getX()];
            int bottomLeft = board[corners.get(3).getY()][corners.get(3).getX()];

            //if all parts of rectangle are in same territory
            if(bottomLeft == bottomRight && bottomRight==topRight && topRight==topLeft){
                //Fill in this rectangle
                for(int y = corners.get(0).getY(); y<= corners.get(2).getY(); y++){
                    for (int x = corners.get(0).getX(); x<=corners.get(2).getX(); x++){
                        board[y][x] = topLeft;
                    }
                }
            } else {
                //Corners belong to different territories -> Recursively divide the space
                divideCorners(board, basePoints, corners);
            }

        }
    }

    private static int getCornerTerritory(ArrayList<Point> basePoints, Point corner){
        PointAndDistance pnd = getNearestNeighbor(basePoints, corner);
        BasePoint cornerTerritory = (BasePoint)pnd.getPoint();
        return cornerTerritory.getTerritory();
    }

    //Generate an edge list and adjacency matrix for a board and its territories
    public static HashMap<String, int[][]> generateEdgesAndAdjacency(int[][] board, int numTerritories) {
        HashMap<String, int[][]> edgesAndAdjacency = new HashMap<>();
        //Get num rows and columns
        int height = board.length;
        int width = board[0].length;

        int[][] edges = new int[height][width];
        int[][] adjacencyMatrix = new int[numTerritories][numTerritories];

        for(int y = 0; y < height; y++){
            for (int x = 0; x<width; x++){
                //Get a list of adjacent territories
                ArrayList<Integer> indexesOfAdjacents = adjacentTerritories(board, y, x);
                int index = board[y][x];
                //For each adjacent territory, fill out the adjacency matrix
                for(int iterator = 0; iterator < indexesOfAdjacents.size(); iterator++){
                    adjacencyMatrix[index][indexesOfAdjacents.get(iterator)] = 1;
                    adjacencyMatrix[indexesOfAdjacents.get(iterator)][index] = 1;
                }

                if(indexesOfAdjacents.isEmpty()){
                    edges[y][x] = 0;
                } else {
                    edges[y][x] = 1;
                }

            }
        }
        edgesAndAdjacency.put(edgeKey, edges);
        edgesAndAdjacency.put(adjacencyKey, adjacencyMatrix);
        return edgesAndAdjacency;

    }

    //Retrieve list of adjacent territories given a specific territory and a board
    private static ArrayList<Integer> adjacentTerritories(int[][] board, int y, int x) {
        ArrayList<Integer> adjacentTerritories = new ArrayList<>();
        int prevRow = y-1;
        int row = y;
        int nextRow = y+1;
        int prevCol = x-1;
        int col = x;
        int nextCol = x+1;
        int maxSize = board[0].length;

        //Case 1: entry directly below
        if(nextRow < maxSize){
            if(board[nextRow][col] != board[row][col] ){
                adjacentTerritories.add(board[nextRow][col]);
            }
        }

        //Case 2: bottom diagonal left entry
        if(nextRow < maxSize && prevCol > 0){
            if(board[nextRow][prevCol] != board[row][col] ){
                adjacentTerritories.add(board[nextRow][prevCol]);
            }
        }

        //Case 3: bottom diagonal right entry
        if(nextRow < maxSize && nextCol < maxSize){
            if(board[nextRow][nextCol] != board[row][col] ){
                adjacentTerritories.add(board[nextRow][nextCol]);
            }
        }

        //Case 4: directly left
        if(prevCol > 0){
            if(board[row][prevCol] != board[row][col] ){
                adjacentTerritories.add(board[row][prevCol]);
            }
        }

        //Case 5: directly right
        if(nextCol < maxSize){
            if(board[row][nextCol] != board[row][col] ){
                adjacentTerritories.add(board[row][nextCol]);
            }
        }

        //Case 6: upper left diagonal entry
        if(prevRow > 0 && prevCol > 0){
            if (board[prevRow][prevCol] != board[row][col]){
                adjacentTerritories.add(board[prevRow][prevCol]);
            }
        }

        //Case 7: directly above entry
        if(prevRow > 0){
            if(board[prevRow][col] != board[row][col]){
                adjacentTerritories.add(board[prevRow][col]);
            }
        }

        //Case 8: upper right diagonal entry
        if(prevRow > 0 && nextCol < maxSize){
            if(board[prevRow][nextCol] != board[row][col]){
                adjacentTerritories.add(board[prevRow][nextCol]);
            }
        }

        return adjacentTerritories;
    }

    //Takes the four corners of a rectangle and divides the rectangle into four smaller pieces
    //Used to recursively divide and fill board in build()
    private static void divideCorners(int[][] board, ArrayList<Point> basePoints, ArrayList<Point> corners){
        Point topLeft = corners.get(0);
        Point topRight = corners.get(1);
        Point bottomRight = corners.get(2);
        Point bottomLeft = corners.get(3);

        //TODO Possibly Remove The +1s and definitely rework this sections -> create a method and a for loop?
        ArrayList<Point> topLeftRectangle = new ArrayList<Point>();
        topLeftRectangle.add(new Point(topLeft.getX(), topLeft.getY()));
        topLeftRectangle.add(new Point((topLeft.getX() + topRight.getX()) / 2, topLeft.getY()));
        topLeftRectangle.add(new Point((topLeft.getX() + bottomRight.getX()) / 2,
                (topLeft.getY() + bottomRight.getY()) / 2));
        topLeftRectangle.add(new Point(topLeft.getX(), (topLeft.getY() + bottomLeft.getY()) / 2));

        ArrayList<Point> topRightRectangle = new ArrayList<Point>();
        topRightRectangle.add(new Point((topLeft.getX() + topRight.getX()) / 2 + 1, topLeft.getY()));
        topRightRectangle.add(new Point(topRight.getX(), topRight.getY()));
        topRightRectangle.add(new Point(topRight.getX(), (topRight.getY() + bottomRight.getY()) / 2));
        topRightRectangle.add(new Point((topLeft.getX() + bottomRight.getX()) / 2 + 1,
                (topLeft.getY() + bottomRight.getY()) / 2));

        ArrayList<Point> bottomRightRectangle = new ArrayList<Point>();
        bottomRightRectangle.add(new Point((topLeft.getX() + bottomRight.getX()) / 2 + 1,
                (topLeft.getY() + bottomRight.getY()) / 2 + 1));
        bottomRightRectangle.add(new Point(bottomRight.getX(), (topRight.getY() + bottomRight.getY()) / 2 + 1));
        bottomRightRectangle.add(new Point(bottomRight.getX(), bottomRight.getY()));
        bottomRightRectangle.add(new Point((bottomLeft.getX() + bottomRight.getX()) / 2 + 1, bottomRight.getY()));

        ArrayList<Point> bottomLeftRectangle = new ArrayList<Point>();
        bottomLeftRectangle.add(new Point(bottomLeft.getX(), (bottomLeft.getY() + topLeft.getY()) / 2 + 1));
        bottomLeftRectangle.add(new Point((bottomLeft.getX() + bottomRight.getX()) / 2,
                (bottomLeft.getY() + topLeft.getY()) / 2 + 1));
        bottomLeftRectangle.add(new Point((bottomLeft.getX() + bottomRight.getX()) / 2, bottomLeft.getY()));
        bottomLeftRectangle.add(new Point(bottomLeft.getX(), bottomLeft.getY()));

        build(board, basePoints, topLeftRectangle);
        build(board, basePoints, topRightRectangle);
        build(board, basePoints, bottomRightRectangle);
        build(board, basePoints, bottomLeftRectangle);

    }

    public static int[][] getBoard(ArrayList<Point> basePoints, int width, int height){
        int[][] board = new int[height][width];
        int y = 0;
        int x = 0;

        while(y < height){
            while(x < width){
                Point p = new Point(x, y);
                PointAndDistance pnd = getNearestNeighbor(basePoints, p);
                BasePoint neighbor = (BasePoint)pnd.getPoint();
                board[y][x] = neighbor.getTerritory();
                x++;
            }
            y++;
        }
        return board;

    }

}
