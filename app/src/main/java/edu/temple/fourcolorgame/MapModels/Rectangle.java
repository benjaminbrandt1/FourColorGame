package edu.temple.fourcolorgame.MapModels;

import java.util.ArrayList;

/**
 * Created by Ben on 11/24/2016.
 */

/* Represents a rectangle shape
   Takes an array of four corners: 0 is top left, 1 is top right, 2 is bottom right, 4 is bottom left
   Has methods to divide rectangle into 4 smaller sub-rectangles
 */
public class Rectangle {
    private ArrayList<Point> corners;
    private Point topLeft;
    private Point topRight;
    private Point bottomRight;
    private Point bottomLeft;

    public Rectangle (ArrayList<Point> corners){
        if(corners.size() != 4){
            this.corners = null;
        } else {
            this.corners = corners;
            topLeft = corners.get(0);
            topRight = corners.get(1);
            bottomRight = corners.get(2);
            bottomLeft = corners.get(3);
        }
    }

    //Create a new rectangle taking the top left point as stationary and halving the distance between
    //the top left point and all other points
    public Rectangle subdivideTopLeft(){
        ArrayList<Point> topLeftRectangle = new ArrayList<Point>();
        topLeftRectangle.add(new Point(topLeft.getX(), topLeft.getY()));
        topLeftRectangle.add(new Point((topLeft.getX() + topRight.getX()) / 2, topLeft.getY()));
        topLeftRectangle.add(new Point((topLeft.getX() + bottomRight.getX()) / 2,
                (topLeft.getY() + bottomRight.getY()) / 2));
        topLeftRectangle.add(new Point(topLeft.getX(), (topLeft.getY() + bottomLeft.getY()) / 2));

        return new Rectangle(topLeftRectangle);
    }

    //Create a new rectangle with the top right point stationary
    //The distance between the top right and all other points is halved
    public Rectangle subdivideTopRight(){
        ArrayList<Point> topRightRectangle = new ArrayList<Point>();
        topRightRectangle.add(new Point((topLeft.getX() + topRight.getX()) / 2 + 1, topLeft.getY()));
        topRightRectangle.add(new Point(topRight.getX(), topRight.getY()));
        topRightRectangle.add(new Point(topRight.getX(), (topRight.getY() + bottomRight.getY()) / 2));
        topRightRectangle.add(new Point((topLeft.getX() + bottomRight.getX()) / 2 + 1,
                (topLeft.getY() + bottomRight.getY()) / 2));

        return new Rectangle(topRightRectangle);
    }

    //Create a new rectangle with the bottom right point stationary
    //The distance between the bottom right and all other points is halved
    public Rectangle subdivideBottomRight(){
        ArrayList<Point> bottomRightRectangle = new ArrayList<Point>();
        bottomRightRectangle.add(new Point((topLeft.getX() + bottomRight.getX()) / 2 + 1,
                (topLeft.getY() + bottomRight.getY()) / 2 + 1));
        bottomRightRectangle.add(new Point(bottomRight.getX(), (topRight.getY() + bottomRight.getY()) / 2 + 1));
        bottomRightRectangle.add(new Point(bottomRight.getX(), bottomRight.getY()));
        bottomRightRectangle.add(new Point((bottomLeft.getX() + bottomRight.getX()) / 2 + 1, bottomRight.getY()));

        return new Rectangle(bottomRightRectangle);
    }

    //Create a new rectangle with the bottom left point stationary
    //The distance between the bottom left and all other points is halved
    public Rectangle subdivideBottomLeft(){
        ArrayList<Point> bottomLeftRectangle = new ArrayList<Point>();
        bottomLeftRectangle.add(new Point(bottomLeft.getX(), (bottomLeft.getY() + topLeft.getY()) / 2 + 1));
        bottomLeftRectangle.add(new Point((bottomLeft.getX() + bottomRight.getX()) / 2,
                (bottomLeft.getY() + topLeft.getY()) / 2 + 1));
        bottomLeftRectangle.add(new Point((bottomLeft.getX() + bottomRight.getX()) / 2, bottomLeft.getY()));
        bottomLeftRectangle.add(new Point(bottomLeft.getX(), bottomLeft.getY()));

        return new Rectangle(bottomLeftRectangle);
    }

    public ArrayList<Point> getCorners(){
        return this.corners;
    }
}
