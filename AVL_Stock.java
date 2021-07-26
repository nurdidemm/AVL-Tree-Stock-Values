import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.Scanner;

class Node {
    public String key;
    public int value;
    public Node left;
    public Node right;
    public int height;
    public String min;
    public String max;
    
    //public int carry;
    
    public boolean isFlipped;
    
    //constructor
    Node(String key, int value){
        this.key=key;
        this.value=value;
        this.height=1;
        this.min = key;
        this.max = key;
    }
}

class AVLTree {
    public static Node root;

    //get height of tree
    public static int getHeight(Node node){
        if(node == null)
            return 0;
        return node.height;
    }

    public static void updateInfo(Node node){
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        if (node.left != null) {
            node.min = node.left.min;
        }
        if (node.right != null) {
            node.max = node.right.max;
        }
    }

    //right rotate
    public static Node rightRotate(Node node) {
        Node nodel = node.left;
        Node nodelr = nodel.right;
        nodel.right = node;
        node.left = nodelr;
        updateInfo(node);
        updateInfo(nodel);
        return nodel;
    }

    //left rotate
    public static Node leftRotate(Node node) {
        Node noder = node.right;
        Node noderl = noder.left;
        noder.left = node;
        node.right = noderl;
        updateInfo(node);
        updateInfo(noder);
        return noder;
    }

    //get balance at node
    public static int getBalance(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }
        
    public static Node doInsert(Node node, String key, int value) {
        if (node == null)
            return new Node(key, value);
        pushDown(node);
        if (key.compareTo(node.key) < 0)
            node.left = doInsert(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = doInsert(node.right, key, value);
        else {
            node.value = value;
            return node;
        }
        pushDown(node);
        updateInfo(node);
        pushDown(node);
        int balance = getBalance(node);
        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rightRotate(node);
        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return leftRotate(node);
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        pushDown(node);
        return node;
    }

    //insert a node
    public static void insert(String key, int value) { root = doInsert(root, key, value); }

    public static int find(String key) { return doFind(root, key); }

    public static int doFind(Node node, String key) {
        if (node == null) {
            return -1;
        }
        pushDown(node);
        if (key.equals(node.key))
            return node.value;
        else if (key.compareTo(node.key) < 0)
            return doFind(node.left, key);
        return doFind(node.right, key);
    }
    
    public static boolean isInRange(Node node, String a, String b) {
        boolean isInRange = false;
        char c = node.key.charAt(0);
        int ascii = (int) c; 
        if ((ascii > (int) a.charAt(0)) && (ascii < (int) b.charAt(0))) {
            isInRange = true;
        }
        return isInRange;
    }
    
    //flip function
    public static void flip(Node node, String a, String b) {
        
        //handle inversed range input
        if (b.compareTo(a) < 0) {
            String temp = a;
            a = b;
            b = temp;
        }
        
        if (node == null) {
            return;
        } else {
            //inclusive
            if (node.max.compareTo(b) <= 0 && node.min.compareTo(a) >= 0) {       
                node.isFlipped ^= true;
            }
            //disjoint
            else if (node.min.compareTo(b) > 0 || node.max.compareTo(a) < 0) {
                return;
            }
            else {
                if (node.key.compareTo(a) >= 0 && node.key.compareTo(b) <= 0) {
                    node.value *= -1;
                }
                //recur in all children 
                flip(node.left, a, b);
                flip(node.right, a, b);
            }
        }
    }

    // public static void carryUpdate(Node node, String a, String b, int v) {
    //     if(node == null) {
    //         return;
    //     } 
    //     //if range of subtree fully inside of a,b
    //     else if (isInRange(node, a, b)) {
    //         node.carry += v;
    //     }
    //     //if current node's range is disjoint from a,b
    //     else if (!isInRange(node, a, b)) {
    //         return;
    //     }
    //     else {
    //         if (isInRange(node, a, b)) {
    //             node.value += v;
    //         }
    //         else {
    //             carryUpdate(node.left, a, b, v);
    //             carryUpdate(node.right, a, b, v);
    //         }
    //     }
    // }

    //push the current flip value down to children
    public static void pushDown(Node node) {
        if (node.isFlipped == true) {
            node.value *= -1;
            if (node.left != null) {
                node.left.isFlipped ^= node.isFlipped;
            }
            if (node.right != null) {
                node.right.isFlipped ^= node.isFlipped;
            }
            node.isFlipped = false;
        } else {
            return;
        }
    }
    
    AVLTree(){
        this.root = null;
    }

}

public class AVL_Stock {
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
       
        AVLTree tree = new AVLTree();
        
        Scanner input = new Scanner(System.in);
        
        int queries = input.nextInt();
        
        for(int q = 0; q < queries; q++){
            int t = input.nextInt();
            if(t == 1){
                String key = input.next();
                int value = input.nextInt();
                tree.insert(key, value);
            }
            else if (t==2) {
                String a = input.next();
                String b = input.next();
                tree.flip(tree.root, a, b);
            }
            else {          // t = 3
                String key = input.next();
                System.out.println(tree.find(key));
            }
        }
    } 
}
