package edu.temple.fourcolorgame.MapModels;

import java.util.ArrayList;

import edu.temple.fourcolorgame.R;

/**
 * Created by Ben on 11/13/2016.
 */

//A territory is a collection of adjacent points on a map
public class Territory implements Comparable<Territory>{

    private int color, size;
    private Point base;
    private ArrayList<Point> includedPoints;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point getBase() {
        return base;
    }

    public void setBase(Point base) {
        this.base = base;
    }

    public ArrayList<Point> getIncludedPoints() {
        return includedPoints;
    }

    public void setIncludedPoints(ArrayList<Point> includedPoints) {
        this.includedPoints = includedPoints;
    }

    public Territory(Point base){

        this.base = base;
        color = R.color.default_region;
        includedPoints = new ArrayList<>();
        size = includedPoints.size();
    }

    public void addPoint(Point p){
        includedPoints.add(p);
        size++;
    }

    @Override
    public int compareTo(Territory other){
        if(size == other.getSize()){
            return 0;
        } else if (size > other.getSize()){
            return 1;
        } else {
            return -1;
        }
    }




}

