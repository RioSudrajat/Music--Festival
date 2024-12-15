package main.list;

import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    public LinkedList() {
        length = 0;
        first = null;
        last = null;
        iterator = null;
    }
    public LinkedList(T[] array) {
        this(); 
        for (T data : array) {
            addLast(data); 
        }
    }

    public LinkedList(LinkedList<T> original) {
        this(); 
        Node temp = original.first; 
        while (temp != null) {
            addLast(temp.data); 
            temp = temp.next;
        }
    }

    public T getFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return first.data;
    }
    public T getLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return last.data;
    }

    public T getIterator() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("Iterator is off end.");
        }
        return iterator.data;
    }

    public int getLength() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public boolean offEnd() {
        return iterator == null;
    }

    public void addFirst(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            first = last = newNode; 
        } else { 
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        length++;
    }

    public void addLast(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) { 
            first = last = newNode;
        } else { 
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        length++;
    }

    public void addIterator(T data) throws NullPointerException {
        if (iterator == null) {
            throw new NullPointerException("Iterator is off end. Cannot add.");
        }
        Node newNode = new Node(data);
        if (iterator == last) {
            addLast(data);
        } else { 
            newNode.next = iterator.next;
            newNode.prev = iterator;
            iterator.next.prev = newNode;
            iterator.next = newNode;
            length++;
        }
    }

    public void removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("tidak bisa dihapus dari list kosong");
        }
        if (first == last) { 
            first = last = null;
        } else { 
            first = first.next;
            first.prev = null;
        }
        length--;
    }

    public void removeLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("tidak bisa dihapus dari list kosong");
        }
        if (first == last) { 
            first = last = null;
        } else { 
            last = last.prev;
            last.next = null;
        }
        length--;
    }

    public void removeIterator() throws NullPointerException {
        if (iterator == null) {
            throw new NullPointerException("Iterasi telah berakhir, todak bisa dihapus");
        }
        if (iterator == first) {
            removeFirst();
        } else if (iterator == last) {
            removeLast();
        } else {
            iterator.prev.next = iterator.next;
            iterator.next.prev = iterator.prev;
            length--;
        }
        iterator = null;
    }

    public void positionIterator() {
        if (isEmpty()) throw new NoSuchElementException("List kosong.");
        iterator = first;
    }

    public void advanceIterator() throws NullPointerException {
        if (offEnd()) throw new NullPointerException("Iterasi telah berakhir.");
        iterator = iterator.next;
    }

    public void reverseIterator() throws NullPointerException {
        if (offEnd()) throw new NullPointerException("Iterasi telah berakhir");
        iterator = iterator.prev;
    }

    public void clear() {
        length = 0;
        first = last = iterator = null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        while (temp != null) {
            result.append(temp.data).append(" ");
            temp = temp.next;
        }
        return result.toString() + "\n";
    }
    public String numberedListString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        int count = 1;
        while (temp != null) {
            result.append(count++).append(". ").append(temp.data).append("\n");
            temp = temp.next;
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LinkedList<?> other = (LinkedList<?>) obj;
        if (this.length != other.length) return false;

        Node currentThis = this.first;
        Node currentOther = (Node) other.first;
        while (currentThis != null && currentOther != null) {
            if (!currentThis.data.equals(currentOther.data)) {
                return false;
            }
            currentThis = currentThis.next;
            currentOther = currentOther.next;
        }
        return true;
    }

    
    /**
    * Memindahkan semua node dalam daftar ke arah akhir
     * dari list berapa kali ditentukan
     * Setiap node yang berada di akhir list
     * bergerak maju akan ditempatkan di bagian depan list
     * Contoh: [1, 2, 3, 4, 5], numMoves = 2 -> [4, 5, 1, 2 ,3]
     * Contoh: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4, 5, 1]
     * Contoh: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
     */
    public void spinList(int numMoves) throws IllegalArgumentException {
        if (numMoves < 0) throw new IllegalArgumentException("numMoves must be >= 0");
        if (isEmpty() || numMoves % length == 0) return;

        numMoves = numMoves % length;
        for (int i = 0; i < numMoves; i++) {
            last.next = first;
            first.prev = last;
            first = last;
            last = last.prev;
            first.prev = null;
            last.next = null;
        }
    }

    /**
     * Menyatukan dua LinkedList untuk membuat Daftar ketiga
     * yang berisi nilai bergantian dari daftar ini
     * dan parameter yang diberikan
     * Misalnya: [1,2,3] dan [4,5,6] -> [1,4,2,5,3,6]
     *Misalnya: [1, 2, 3, 4] dan [5, 6] -> [1, 5, 2, 6, 3, 4]
     *Contoh: [1, 2] dan [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     */
    public LinkedList<T> altLists(LinkedList<T> list) {
        LinkedList<T> result = new LinkedList<>();
        Node currentThis = this.first;
        Node currentOther = list.first;
        while (currentThis != null || currentOther != null) {
            if (currentThis != null) {
                result.addLast(currentThis.data);
                currentThis = currentThis.next;
            }
            if (currentOther != null) {
                result.addLast(currentOther.data);
                currentOther = currentOther.next;
            }
        }
        return result;
    }

    public int getIteratorIndex() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("Iterasi telh berakhir, tidak bisa menemukan indeks");
        }

        int index = 0;
        Node temp = first;
        while (temp != null) {
            if (temp == iterator) {
                return index;
            }
            temp = temp.next;
            index++;
        }
        throw new RuntimeException("Iterator berada dalam kondisi tidak valid.");
    }

    public int findIndex(T data) {
        Node current = first;
        int index = 0;
        while (current != null) {
            if (current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public void advanceIteratorToIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException("indeks melebihi batas.");
        iterator = first;
        for (int i = 0; i < index; i++) {
            iterator = iterator.next;
        }
    }
}