package main.list;

import java.util.NoSuchElementException;

public class Queue<T> implements Q<T> {
    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private int size;
    private Node front;
    private Node end;

    public Queue() {
        front = end = null;
        size = 0;
    }

    public Queue(T[] array) {
        for (T data : array) {
            enqueue(data);
        }
    }

    public Queue(Queue<T> original) {
        if (original.size > 0) {
            Node temp = original.front;
            while (temp != null) {
                enqueue(temp.data);
                temp = temp.next;
            }
        }
    }

    public T getFront() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue kosong.");
        }
        return front.data;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            front = end = newNode;
        } else {
            end.next = newNode;
            end = newNode;
        }
        size++;
    }

    public void dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue kosong.");
        }
        if (size == 1) {
            front = end = null;
        } else {
            front = front.next;
        }
        size--;
    }

    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = front;
        while (temp != null) {
            result.append(temp.data).append(" ");
            temp = temp.next;
        }
        return result + "\n";
    }

    @Override public boolean equals(Object obj)  {
        if (this == obj) return true;
        if (!(obj instanceof Queue)) return false;
        Queue<?> other = (Queue<?>) obj;
        if (this.size != other.size) return false;
        Node currentThis = this.front;
        Node currentOther = (Node) other.front;
        while (currentThis != null) {
            if (!currentThis.data.equals(currentOther.data)) {
                return false;
            }
            currentThis = currentThis.next;
            currentOther = currentOther.next;
        }
        return true;
    }
}