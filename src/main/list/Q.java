package main.list;

import java.util.NoSuchElementException;

public interface Q<T> {

    T getFront() throws NoSuchElementException;


    int getSize();

    boolean isEmpty();

    void enqueue(T data);

    void dequeue() throws NoSuchElementException;
}