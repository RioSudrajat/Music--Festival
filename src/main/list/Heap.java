package main.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<T> {
    private int heapSize;
    private ArrayList<T> heap;
    private Comparator<T> cmp;


    public Heap(ArrayList<T> data, Comparator<T> cmp) {
        this.heap = new ArrayList<T>(data.size() + 1);
        this.cmp = cmp;
        this.heapSize = data.size();
        this.heap.add(null);
        for (T item : data) {
            this.heap.add(item);
        }
        buildHeap();
    }

    public void buildHeap() {
        for (int i = heapSize / 2; i > 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int index) {
        int left = getLeft(index);
        int right = getRight(index);
        int largest = index;
        if (left <= heapSize && cmp.compare(heap.get(left), heap.get(index)) > 0) {
            largest = left;
        }
        if (right <= heapSize && cmp.compare(heap.get(right), heap.get(largest)) > 0) {
            largest = right;
        }
        if (largest != index) {
            T temp = heap.get(index);
            heap.set(index, heap.get(largest));
            heap.set(largest, temp);
            heapify(largest);
        }
    }

    public void insert(T key) {
        heapSize++;
        if (heapSize >= heap.size()) {
            heap.add(key);
        } else {
            heap.set(heapSize, key);
        }
        heapIncreaseKey(heapSize, key);
    }


    private void heapIncreaseKey(int index, T key) {
        while (index > 1 && cmp.compare(heap.get(getParent(index)), key) < 0) {
            heap.set(index, heap.get(getParent(index)));
            index = getParent(index);
        }
        heap.set(index, key);
    }


    public void remove(int index) {
        if (index <= 0 || index > heapSize) {
            throw new NoSuchElementException("index melewati batas dari heap.");
        }
        T removedItem = heap.get(index);
        heap.set(index, heap.get(heapSize));
        heapify(index);
        heapSize--;
    }
    public int getHeapSize() {
        return heapSize;
    }

    public int getLeft(int index) throws IndexOutOfBoundsException {
        int left = 2 * index;
        if (index <= 0 || index > heapSize) throw new IndexOutOfBoundsException("Left index out of bounds.");
        return left;
    }

    public int getRight(int index) throws IndexOutOfBoundsException {
        int right = 2 * index + 1;
        if (index <= 0 || index > heapSize) throw new IndexOutOfBoundsException("indeks kanan melebihi batas");
        return right;
    }

    public int getParent(int index) throws IndexOutOfBoundsException {
        if (index <= 1 || index > heapSize) throw new IndexOutOfBoundsException("Indeks kiri tidak memliki parent");
        return index / 2;
    }

    public T getMax() {
        if (heapSize == 0) throw new NoSuchElementException("Heap kosong.");
        return heap.get(1);
    }

    public T getElement(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index > heapSize) {
            throw new IndexOutOfBoundsException("Index melebihi batas heap.");
        }
        return heap.get(index);
    }

    @Override
    public String toString() {
        if (heapSize == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= heapSize; i++) {
            sb.append(heap.get(i));
            if (i < heapSize) sb.append(", ");
        }
        return sb.toString();
    }

    public ArrayList<T> sort() {
        ArrayList<T> sorted = new ArrayList<>(heapSize);
        ArrayList<T> originalHeap = new ArrayList<>(heap);
        int originalSize = heapSize;
        while (heapSize > 0) {
            T max = getMax();
            sorted.add(0, max);
            remove(1);
        }
        heap = originalHeap;
        heapSize = originalSize;
        return sorted;
    }
}