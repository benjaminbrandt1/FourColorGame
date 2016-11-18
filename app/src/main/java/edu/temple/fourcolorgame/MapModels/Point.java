package edu.temple.fourcolorgame.MapModels;

/**
 * Created by Ben on 11/13/2016.
 */

//Ordered Pair representing a point on a graph
public class Point {

    private int x, y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Constructor
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(Point p){
        double horizontal = Math.abs(x - p.getX());
        double vertical = Math.abs(y - p.getY());
        double distance = Math.pow(horizontal, 2) + Math.pow(vertical, 2);
        return distance;
    }

    public boolean isSamePoint(Point p){
        if(x == p.getX() && y == p.getY()) {
            return true;
        } else {
            return false;
        }
    }

}
