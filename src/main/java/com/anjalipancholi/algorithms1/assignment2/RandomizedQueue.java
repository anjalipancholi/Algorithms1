package com.anjalipancholi.algorithms1.assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int length = 0;
    private Item[] randomizedQueue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randomizedQueue = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No item present");
        }
        if (length == randomizedQueue.length) {
            Item[] temp = (Item[]) new Object[2 * randomizedQueue.length];
            for (int i = 0; i < length; i++) {
                temp[i] = randomizedQueue[i];
            }
            randomizedQueue = temp;
        }
        randomizedQueue[length++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty dequeue");
        }
        int index = StdRandom.uniform(length);
        Item item = randomizedQueue[index];
        randomizedQueue[index] = randomizedQueue[length - 1];
        randomizedQueue[length - 1] = null;
        length--;
        if (length > 0 && length == randomizedQueue.length / 4) {
            Item[] newTemp = (Item[]) new Object[randomizedQueue.length / 2];
            for (int i = 0; i < length; i++) {
                newTemp[i] = randomizedQueue[i];
            }
            randomizedQueue = newTemp;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty queue");
        }
        return randomizedQueue[StdRandom.uniform(length)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Item[] copy;
        int k;

        public ArrayIterator() {
            copy = (Item[]) new Object[length];
            k = length;
            for (int i = 0; i < length; i++) {
                copy[i] = randomizedQueue[i];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return length > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("");
            }
            return copy[--k];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() couldn't be supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
