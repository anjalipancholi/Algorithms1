package com.anjalipancholi.algorithms1.assignment3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException("Point in array is null");
            }
        }
        Point[] points1 = Arrays.copyOf(points, points.length);
        Point[] points2 = Arrays.copyOf(points, points.length);
        List<LineSegment> segmentsList = new ArrayList<>();
        Arrays.sort(points2);
        checkForDuplicatedPoints(points2);
        for (int i = 0; i < points2.length; ++i) {
            Point index = points2[i];
            Arrays.sort(points1);
            Arrays.sort(points1, index.slopeOrder());
            int count = 1;
            Point line = null;
            for (int j = 0; j < points1.length - 1; ++j) {
                if (points1[j].slopeTo(index) == points1[j + 1].slopeTo(index)) {
                    count++;
                    if (count == 2) {
                        line = points1[j];
                        count++;
                    } else if (count >= 4 && j + 1 == points1.length - 1) {
                        if (line.compareTo(index) > 0) {
                            segmentsList.add(new LineSegment(index, points1[j + 1]));
                        }
                        count = 1;
                    }
                } else if (count >= 4) {
                    if (line.compareTo(index) > 0) {
                        segmentsList.add(new LineSegment(index, points1[j]));
                    }
                    count = 1;
                } else {
                    count = 1;
                }
            }

        }
        lineSegments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }     // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, numberOfSegments());
    }    // the line segments

    private void checkForDuplicatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; ++i) {
            if (points[i] == (points[i + 1])) {
                throw new java.lang.IllegalArgumentException("Duplicate points exists in array");
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

