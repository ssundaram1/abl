/**
 * Created by ssundaram on 4/9/18.
 */
public class lnkd {
//    Given a list of child->parent relationships, build a binary tree out of it. All the element Ids inside the tree are unique.
//
//            Example:
//
//    Given the following relationships:
//
//       Child Parent IsLeft
//        15 20 true
//        19 80 true
//        17 20 false
//        16 80 false
//        80 50 false
//        50 null false
//        20 50 true
//
//    You should return the following tree:
//
//            50
//            /  \
//            20   80
//            / \   / \
//            15 17 19 16
//    Function Signature
//
//    /**
//     * Represents a pair relation between one parent node and one child node inside a binary tree
//     * If the _parent is null, it represents the ROOT node
//     */
//    class Relation {
//        int parent;
//        int child;
//        bool isLeft;
//    };
//
//
//    /**
//     * Represents a single Node inside a binary tree
//     */
//    class Node {
//        int id;
//        Node * left;
//        Node * right;
//    }
//
///**
// * Implement a method to build a tree from a list of parent-child relationships
// * And return the root Node of the tree
// */
//    Node * buildTree (vector<Relation> & data)
//    {
//    }


    private static class Relation{
        int parent;
        int child;
        boolean isLeft;
    }

}
