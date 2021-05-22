package com.anjalipancholi.algorithms1.assignment5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> treeSet;

    public PointSET() {
        treeSet = new TreeSet<Point2D>();
    }// construct an empty set of points

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }// is the set empty?

    public int size() {
        return treeSet.size();
    }// number of points in the set

    public void insert(Point2D p) {
        treeSet.add(p);
    }// add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return treeSet.contains(p);
    }// does the set contain point p?

    public void draw() {
        for (Point2D i : treeSet) {
            i.draw();
        }
    }// draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<>();
        for (Point2D i : treeSet) {
            if (rect.contains(i)) {
                list.add(i);
            }
        }
        return list;
    }// all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (treeSet.isEmpty()) {
            return null;
        }
        Point2D point = treeSet.first();
        double minDist = p.distanceSquaredTo(point);
        for (Point2D i : treeSet) {
            if (p.distanceSquaredTo(i) <= minDist) {
                point = i;
                minDist = p.distanceSquaredTo(i);
            }
        }
        return point;
    }// a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
    }
}
