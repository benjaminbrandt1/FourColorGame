package edu.temple.fourcolorgame.MapModels;

import edu.temple.fourcolorgame.MapModels.Point;

/**
 * Created by Ben on 11/15/2016.
 */
//Store a point and its distance to another point as a pair
public class PointAndDistance {
    private Point point;
    private double distance;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public PointAndDistance(Point point, double distance) {
        this.point = point;
        this.distance = distance;
    }
}
