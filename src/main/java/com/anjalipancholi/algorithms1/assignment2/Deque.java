package com.anjalipancholi.algorithms1.assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int length;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        length = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        if (isEmpty()) {
            first.next = null;
            last = first;
        } else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            last.prev = null;
            first = last;

        } else {
            last.prev = oldLast;
            oldLast.next = last;

        }
        length++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        if (length == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        length--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(("Deque is empty"));
        }
        Item item = last.item;
        if (length == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        length--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new itemIterator();
    }

    private class itemIterator implements Iterator<Item> {
        private Node current;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("next element does not exist");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() couldn't be supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
