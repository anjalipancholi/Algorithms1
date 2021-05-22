package com.anjalipancholi.algorithms1.assignment3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        checkDuplicate(points);
        Point[] points1 = Arrays.copyOf(points, points.length);
        Arrays.sort(points1);
        List<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < points1.length; i++) {
            for (int j = i + 1; j < points1.length; j++) {
                for (int k = j + 1; k < points1.length; k++) {
                    for (int l = k + 1; l < points1.length; l++) {
                        if (points1[i].slopeTo(points1[j]) == points1[j].slopeTo(points1[l])
                                && points1[i].slopeTo(points1[j]) == points1[i].slopeTo(points1[k])) {
                            LineSegment tempLineSegment = new LineSegment(points1[i], points1[l]);
                            if (!segmentsList.contains(tempLineSegment))
                                segmentsList.add(tempLineSegment);
                        }
                    }
                }
            }
        }
        lineSegments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }// finds all line segments containing 4 points

    private void checkDuplicate(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i] == points[i - 1]) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments;
    }              // the line segments
}
