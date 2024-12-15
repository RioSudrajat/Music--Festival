package main.list;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private class Node {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    public BST(BST<T> bst, Comparator<T> cmp) {
        if (bst != null) {
            copyHelper(bst.root, cmp);
        } else {
            root = null;
        }
    }

    private void copyHelper(Node node, Comparator<T> cmp) {
        if (node != null) {
            insert(node.data, cmp);
            copyHelper(node.left, cmp);
            copyHelper(node.right, cmp);
        }
    }

    public BST(T[] array, Comparator<T> cmp) throws IllegalArgumentException {
        if (array == null) {
            root = null; 
        } else if (!isSorted(array, cmp)) {
            throw new IllegalArgumentException("Array harus diurutkan secara ascending order.");
        } else {
            root = arrayHelper(0, array.length - 1, array);
        }
    }

    private boolean isSorted(T[] array, Comparator<T> cmp) {
        if (array == null || array.length == 0) { 
            return true; 
        }
        for (int i = 1; i < array.length; i++) {
            if (cmp.compare(array[i - 1], array[i]) > 0) {
                return false;
            }
        }
        return true;
    }

// recursiive array
    private Node arrayHelper(int begin, int end, T[] array) {
        if (begin > end) return null;
        int mid = (begin + end) / 2;
        Node node = new Node(array[mid]);
        node.left = arrayHelper(begin, mid - 1, array);
        node.right = arrayHelper(mid + 1, end, array);
        return node;
    }

// mengurutkan data kedalam root
    public T getRoot() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The BST is empty.");
        }
        return root.data;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        return getSize(root);
    }

    private int getSize(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + getSize(node.left) + getSize(node.right);
        }
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    public T findMin() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The BST is empty.");
        }
        return findMin(root);
    }

    private T findMin(Node node) {
        if (node.left == null) {
            return node.data;
        } else {
            return findMin(node.left);
        }
    }
// mengembalikan nilai terbesar kedalam tree
    public T findMax() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("The BST is empty.");
        }
        return findMax(root);
    }
// recursif findmax
    private T findMax(Node node) {
        if (node.right == null) {
            return node.data;
        } else {
            return findMax(node.right);
        }
    }

    public T search(T data, Comparator<T> cmp) {
        return search(data, root, cmp);
    }

    private T search(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            return null;
        }
        int compareResult = cmp.compare(data, node.data);

        if (compareResult < 0) {
            return search(data, node.left, cmp);
        }
        else if (compareResult > 0) {
            return search(data, node.right, cmp);
        }
        else {
            return node.data;
        }
    }

// memasukkan node baru kedalam tree
    public void insert(T data, Comparator<T> cmp) {
        if (root == null) {
            root = new Node(data); 
        } else {
            insert(data, root, cmp); 
        }
    }

    private void insert(T data, Node node, Comparator<T> cmp) {
        int comparison = cmp.compare(data, node.data);

        if (comparison < 0) {
            if (node.left == null) {
                node.left = new Node(data);
            } else {
                insert(data, node.left, cmp);
            }
        } else if (comparison > 0) {
            if (node.right == null) {
                node.right = new Node(data);
            } else {
                insert(data, node.right, cmp);
            }
        }
    }

    public void remove(T data, Comparator<T> cmp) {
        root = remove(data, root, cmp);
    }

    private Node remove(T data, Node node, Comparator<T> cmp) {
        if (node == null) {
            return null;
        }
        int compareResult = cmp.compare(data, node.data);

        if (compareResult < 0) {
            node.left = remove(data, node.left, cmp);
        } else if (compareResult > 0) {
            node.right = remove(data, node.right, cmp);
        } else if (node.left != null && node.right != null) {
            node.data = findMin(node.right);
            node.right = remove(node.data, node.right, cmp);
        } else {
            node = (node.left != null) ? node.left : node.right;
        }
        return node;
    }
// mengembalikan string yang berisi data dalam preorder
    public String preOrderString() {
        StringBuilder sb = new StringBuilder();
        preOrderString(root, sb);
        return sb.toString() + "\n";
    }

    private void preOrderString(Node node, StringBuilder preOrder) {
        if (node != null) {
            preOrder.append(node.data + " ");
            preOrderString(node.left, preOrder);
            preOrderString(node.right, preOrder);
        }
    }

    public String inOrderString() {
        StringBuilder sb = new StringBuilder();
        inOrderString(root, sb);
        return sb.toString() + "\n";
    }

    private void inOrderString(Node node, StringBuilder inOrder) {
        if (node != null) {
            inOrderString(node.left, inOrder);
            inOrder.append(node.data + "\n");
            inOrderString(node.right, inOrder);
        }
    }

    public String postOrderString() {
        StringBuilder sb = new StringBuilder();
        postOrderString(root, sb);
        return sb.toString() + "\n";
    }

    private void postOrderString(Node node, StringBuilder postOrder) {
        if (node != null) {
            postOrderString(node.left, postOrder);
            postOrderString(node.right, postOrder);
            postOrder.append(node.data + " ");
        }
    }

    /**
     * Membuat String dengan urutan ketinggian
     * traversal data di pohon mulai dari
     * Node dengan tinggi terbesar (root)
     * turun ke Node dengan ketinggian terkecil - dengan
     * Node dengan tinggi yang sama ditambahkan dari kiri ke kanan.
     */
    public String levelOrderString() {
        Queue<Node> que  = new Queue<>();
        StringBuilder sb = new StringBuilder();
        que.enqueue(root);
        levelOrderString(que, sb);
        return sb.toString() + "\n";
    }

    private void levelOrderString(Queue<Node> que, StringBuilder heightTraverse) {
        if(!que.isEmpty()) {
            Node nd = que.getFront();
            que.dequeue();
            if(nd != null) {
                que.enqueue(nd.left);
                que.enqueue(nd.right);
                heightTraverse.append(nd.data + " ");
            }
            levelOrderString(que, heightTraverse);
        }
    }

    /**
     * Mengembalikan data Node yang merupakan prekursor bersama ke keduanya
     * Node berisi data yang diberikan. Jika data1 atau data2 adalah a
     * nilai duplikat, metode akan menemukan prekursor duplikat
     * dengan ketinggian terbesar
     */
    public T sharedPrecursor(T data1, T data2, Comparator<T> cmp) {
        if (search(data1, cmp) == null || search(data2, cmp) == null) {
            throw new IllegalArgumentException("One or both values do not exist in the BST.");
        }
        return sharedPrecursor(data1, data2, root, cmp);
    }

    private T sharedPrecursor(T data1, T data2, Node currLevel, Comparator<T> cmp) {
        if (currLevel == null) return null;
        int cmp1 = cmp.compare(data1, currLevel.data);
        int cmp2 = cmp.compare(data2, currLevel.data);

        if (cmp1 > 0 && cmp2 > 0) {
            return sharedPrecursor(data1, data2, currLevel.right, cmp);
        } else if (cmp1 < 0 && cmp2 < 0) {
            return sharedPrecursor(data1, data2, currLevel.left, cmp);
        } else {
            return currLevel.data;
        }
    }
}