package com.anjalipancholi.algorithms1.assignment5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;


public class KdTree {
    private TreeNode treeNode;
    private int size;
    private RectHV rectHV;

    private class TreeNode {
        private TreeNode left, right;
        private Point2D key;
        private Point2D value;
        private boolean isHorizontal;

        public TreeNode(Point2D key, Point2D value, TreeNode left, TreeNode right, boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
            this.key = key;
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }

    public KdTree() {
        treeNode = null;
        size = 0;
        rectHV = new RectHV(0.0, 0.0, 1.0, 1.0);
    }// construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }// is the set empty?

    public int size() {
        return size;
    }// number of points in the set

    public void insert(Point2D p) {
        treeNode = helper(treeNode, p, p, false);
    }// add the point to the set (if it is not already in the set)

    private TreeNode helper(TreeNode root, Point2D key, Point2D value, boolean isHorizontal) {
        if (root == null) {
            size++;
            return new TreeNode(key, value, null, null, isHorizontal);
        }
        if (root.key.x() == key.x() && root.key.y() == key.y()) {
            root.value = value;
        } else if (!isHorizontal && root.key.x() >= key.x() || isHorizontal && root.key.y() >= key.y()) {
            root.left = helper(root.left, key, value, !isHorizontal);
        } else {
            root.right = helper(root.right, key, value, !isHorizontal);
        }
        return root;
    }

    public boolean contains(Point2D p) {
        TreeNode root = treeNode;
        while (root != null) {
            if (root.key.x() == p.x() && root.key.y() == p.y()) {
                return true;
            } else if ((!root.isHorizontal && root.key.x() >= p.x()) || (root.isHorizontal && root.key.y() >= p.y())) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return false;
    }// does the set contain point p?

    public void draw() {
        StdDraw.setScale(0, 1);
        draw(treeNode, rectHV);
    }// draw all points to standard draw

    private void draw(TreeNode root, RectHV board) {
        if (root != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            new Point2D(root.key.x(), root.key.y()).draw();
            StdDraw.setPenRadius();
            if (!root.isHorizontal) {
                StdDraw.setPenColor(StdDraw.RED);
                new Point2D(root.key.x(), rectHV.ymin()).drawTo(new Point2D(root.key.x(), rectHV.ymax()));
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                new Point2D(rectHV.xmin(), root.key.x()).drawTo(new Point2D(rectHV.xmax(), root.key.y()));
            }
            draw(root.left, leftRect(rectHV, root));
            draw(root.right, rightRect(rectHV, root));
        }
    }

    private RectHV leftRect(RectHV rect, TreeNode p) {
        if (p.isHorizontal) {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.key.y());
        }
        return new RectHV(rect.xmin(), rect.ymin(), p.key.x(), rect.ymax());
    }

    private RectHV rightRect(RectHV rect, TreeNode p) {
        if (p.isHorizontal) {
            return new RectHV(rect.xmin(), p.key.y(), rect.xmax(), rect.ymax());
        }
        return new RectHV(p.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> list = new ArrayList<Point2D>();
        helperRange(treeNode, rectHV, rect, list);
        return list;
    }             // all points that are inside the rectangle (or on the boundary)

    private void helperRange(TreeNode node, RectHV nodeRect, RectHV rect, List<Point2D> list) {
        if (node != null) {
            if (nodeRect.intersects(rect)) {
                if (rect.contains(node.value)) {
                    list.add(node.value);
                }
                helperRange(node.left, leftRect(nodeRect, node), rect, list);
                helperRange(node.right, rightRect(nodeRect, node), rect, list);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        return helperNearest(treeNode, rectHV, p, null);
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D helperNearest(TreeNode node, RectHV rect, Point2D p, Point2D nearPoint) {
        Point2D nearestPoint = nearPoint;
        if (node != null) {
            if (nearestPoint == null || nearestPoint.distanceSquaredTo(p) > rect.distanceSquaredTo(p)) {
                if (nearestPoint == null) {
                    nearestPoint = node.value;
                } else {
                    if (node.key.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {
                        nearestPoint = node.key;
                    }
                }
                if (!node.isHorizontal) {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                    RectHV rightRect = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
                    if (p.x() <= node.key.x()) {
                        nearestPoint = helperNearest(node.left, leftRect, p, nearestPoint);
                        nearestPoint = helperNearest(node.right, rightRect, p, nearestPoint);
                    } else {
                        nearestPoint = helperNearest(node.right, rightRect, p, nearestPoint);
                        nearestPoint = helperNearest(node.left, leftRect, p, nearestPoint);
                    }
                } else {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                    RectHV rightRect = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
                    if (p.y() <= node.key.y()) {
                        nearestPoint = helperNearest(node.left, leftRect, p, nearestPoint);
                        nearestPoint = helperNearest(node.right, rightRect, p, nearestPoint);
                    } else {
                        nearestPoint = helperNearest(node.right, rightRect, p, nearestPoint);
                        nearestPoint = helperNearest(node.left, leftRect, p, nearestPoint);
                    }
                }
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {             // unit testing of the methods (optional)
    }
}
