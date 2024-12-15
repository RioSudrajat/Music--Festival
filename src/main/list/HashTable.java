package main.list;

import java.util.ArrayList;

public class HashTable<T> {

    private int numElements;
    private ArrayList<LinkedList<T> > table;

    public HashTable(int size) throws IllegalArgumentException {
        if (size <= 0) {
            throw new IllegalArgumentException("Size harus lebih besar dari 0.");
        }
        this.table = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            this.table.add(new LinkedList<>());
        }
        this.numElements = 0;
    }

    public HashTable(T[] array, int size) throws IllegalArgumentException {
        this(size); 

        if (array != null) {
            for (T element : array) {
                if (element != null) { 
                    add(element); 
                }
            }
        }
    }
    private int hash(T obj) {
        int code = obj.hashCode();
        return code % table.size();
    }

    public int countBucket(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= table.size()) {
            throw new IndexOutOfBoundsException("indeks berada diluar batas.");
        }
        return table.get(index).getLength();
    }

    public int getNumElements() {
        return numElements;
    }

    public T get(T elmt) throws NullPointerException {
        if (elmt == null) {
            throw new NullPointerException("Elemen yang akan ditemukan tidak boleh nol.");
        }
        int index = hash(elmt);
        LinkedList<T> bucket = table.get(index);

        if (bucket.isEmpty()) {
            return null;
        }

        bucket.positionIterator();
        while (!bucket.offEnd()) {
            if (bucket.getIterator().equals(elmt)) {
                return bucket.getIterator();
            }
            bucket.advanceIterator();
        }
        return null;
    }

    public int find(T elmt) throws NullPointerException {
        if (elmt == null) {
            throw new NullPointerException("Elemen yang akan ditemukan tidak boleh nol.");
        }
        int index = hash(elmt);
        LinkedList<T> bucket = table.get(index);

        if (bucket.isEmpty()) {
            return -1;
        }

        bucket.positionIterator();
        while (!bucket.offEnd()) {
            if (bucket.getIterator().equals(elmt)) {
                return index;
            }
            bucket.advanceIterator();
        }
        return -1;
    }

// Menentukan apakah elemen tertentu ada dalam tabel.
    public boolean contains(T elmt) throws NullPointerException {
        return find(elmt) != -1;
    }
// * Menyisipkan elemen baru ke dalam tabel di akhir rantai dari ember yang benar.
    public void add(T elmt) throws NullPointerException {
        if (elmt == null) {
            throw new NullPointerException("Element cannot be null.");
        }
        int index = hash(elmt);
        LinkedList<T> bucket = table.get(index);

        if (!bucket.equals(elmt)) { 
            bucket.addLast(elmt);
            numElements++;
        }
    }
    public boolean delete(T elmt) throws NullPointerException {
        if (elmt == null) {
            throw new NullPointerException("Element cannot be null.");
        }
        int index = hash(elmt);
        LinkedList<T> bucket = table.get(index);

        bucket.positionIterator();
        while (!bucket.offEnd()) {
            if (bucket.getIterator().equals(elmt)) {
                bucket.removeIterator();
                numElements--;
                return true;
            }
            bucket.advanceIterator();
        }
        return false;
    }

    public void clear() {
        for (LinkedList<T> list : table) {
            list.clear();
        }
        numElements = 0;
    }

    public double getLoadFactor() {
        return (double) numElements / table.size();
    }

    public String bucketToString(int bucket) throws IndexOutOfBoundsException {
        if (bucket < 0 || bucket >= table.size()) {
            throw new IndexOutOfBoundsException("Bucket index is out of bounds.");
        }
        return table.get(bucket).toString();
    }

    public String rowToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.size(); i++) {
            sb.append("Bucket ").append(i).append(": ");
            LinkedList<T> bucket = table.get(i);
            if (bucket.isEmpty()) {
                sb.append("empty");
            } else {
                T firstElement = bucket.getFirst();
                sb.append(firstElement != null ? firstElement.toString() : "empty");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.size(); i++) {
            LinkedList<T> bucket = table.get(i);
            if (!bucket.isEmpty()) {
                sb.append(bucket.toString());
            }
        }
        if (sb.length() > 0) {
            sb.append("\n");
        }
        return sb.toString();
    }
}