package cz.comkop.exercises.binarytree;

public class BinaryTree {
    public static void main(String[] args) {

        BinaryTree binaryTree = new BinaryTree();
        binaryTree.root = new Node(8);
        binaryTree.root.left = new Node(3);
        binaryTree.root.right = new Node(10);
        binaryTree.root.left.left = new Node(1);
        binaryTree.root.left.right = new Node(4);

        Node node = binaryTree.binarySearch(4);
        System.out.println(node);
        BinaryTree fromArray = BinaryTree.getFromArray(new int[]{5, 6, 3, 2, 4, 9, 7});
        Node node1 = fromArray.binarySearch(2);
        System.out.println(node1);

    }

    //DU implement method to create binary tree from array
    public static BinaryTree getFromArray(int[] array) {
        BinaryTree bt = new BinaryTree();
        bt.root = new Node(array[0]);
        for (int i = 1; i < array.length; i++) {
            getSuitableNode(array[i], bt.root);
        }
        return bt;
    }

    public static void getSuitableNode(int value, Node node) {
        if (node.value <= value) {
            if (node.right == null) {
                node.right = new Node(value, node);
            } else {
                getSuitableNode(value, node.right);
            }
        } else if (node.left == null) {
            node.left = new Node(value, node);
        } else {
            getSuitableNode(value, node.left);
        }
    }

    Node binarySearch(int value) {
        return binarySearch(value, root);
    }

    Node binarySearch(int value, Node node) {
        if (node != null) {
            if (node.value == value) {
                return node;
            }
            if (value > node.value) {
                return binarySearch(value, node.right);
            }
            if (value < node.value) {
                return binarySearch(value, node.left);
            }
            //nikdy nenastane
            return null;
        } else {
            return null;
        }
    }

    Node root;

    public static class Node {

        int value;
        Node parent;
        Node left;//mensi nez parent
        Node right;//vetsi nez parent

        public Node(int value) {
            this.value = value;

        }

        public Node(int value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
