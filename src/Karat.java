/*

Suppose we have some input data describing a graph of relationships between parents and children over multiple generations. The data is formatted as a list of (parent, child) pairs, where each individual is assigned a unique integer identifier.

For example, in this diagram, 6 and 8 have common ancestors of 4 and 14.

         14  13
         |   |
1   2    4   12
 \ /   / | \ /
  3   5  8  9
   \ / \     \
    6   7     11

parentChildPairs1 = [
    (1, 3), (2, 3), (3, 6), (5, 6), (5, 7), (4, 5),
    (4, 8), (4, 9), (9, 11), (14, 4), (13, 12), (12, 9)
]

Write a function that takes the graph, as well as two of the individuals in our dataset, as its inputs and returns true if and only if they share at least one ancestor.

Sample input and output:

hasCommonAncestor(parentChildPairs1, 3, 8) => false
hasCommonAncestor(parentChildPairs1, 5, 8) => true
hasCommonAncestor(parentChildPairs1, 6, 8) => true
hasCommonAncestor(parentChildPairs1, 6, 9) => true
hasCommonAncestor(parentChildPairs1, 1, 3) => false
hasCommonAncestor(parentChildPairs1, 3, 1) => false
hasCommonAncestor(parentChildPairs1, 7, 11) => true
hasCommonAncestor(parentChildPairs1, 6, 5) => true
hasCommonAncestor(parentChildPairs1, 5, 6) => true

Additional example: In this diagram, 4 and 12 have a common ancestor of 11.

        11
       /  \
      10   12
     /  \
1   2    5
 \ / \   / \
  3    6   7
   \        \
    4        8

parentChildPairs2 = [
    (11, 10), (11, 12), (2, 3), (10, 2), (10, 5),
    (1, 3), (3, 4), (5, 6), (5, 7), (7, 8),
]

hasCommonAncestor(parentChildPairs2, 4, 12) => true
hasCommonAncestor(parentChildPairs2, 1, 6) => false
hasCommonAncestor(parentChildPairs2, 1, 12) => false

n: number of pairs in the input

*/

import java.io.*;
import java.util.*;

public class Karat {
    public static void main(String[] argv) {
        int[][] parentChildPairs1 = new int[][] {
                {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7}, {4, 5},
                {4, 8}, {4, 9}, {9, 11}, {14, 4}, {13, 12}, {12, 9}
        };

        int[][] parentChildPairs2 = new int[][] {
                {11, 10}, {11, 12}, {2, 3}, {10, 2}, {10, 5},
                {1, 3}, {3, 4}, {5, 6}, {5, 7}, {7, 8}
        };

//        System.out.println(hasCommonAncestor(parentChildPairs1, 3, 8));
//        System.out.println(hasCommonAncestor(parentChildPairs1, 5, 8)) ;
//        System.out.println(hasCommonAncestor(parentChildPairs1, 6, 8)) ;
       System.out.println(hasCommonAncestor(parentChildPairs1, 6, 9));
//        System.out.println(hasCommonAncestor(parentChildPairs1, 1, 3)) ;
//        System.out.println(hasCommonAncestor(parentChildPairs1, 3, 1));
//        System.out.println(hasCommonAncestor(parentChildPairs1, 7, 11));
//        System.out.println(hasCommonAncestor(parentChildPairs1, 6, 5));
//        System.out.println(hasCommonAncestor(parentChildPairs1, 5, 6)) ;
//
//
//
//        hasCommonAncestor(parentChildPairs2, 4, 12);
//        hasCommonAncestor(parentChildPairs2, 1, 6);
//        hasCommonAncestor(parentChildPairs2, 1, 12);
//
//
//        hasCommonAncestor(parentChildPairs2, 4, 12);
//        hasCommonAncestor(parentChildPairs2, 1, 6);
//        hasCommonAncestor(parentChildPairs2, 1, 12);

        List<Integer> list = Arrays.asList(1,2,3,5);
        System.out.println(-(Collections.binarySearch(list, 0)) -1);

    }



    static boolean hasCommonAncestor(int[][] parentChildArr, int node1, int node2){
        Map<Integer, Set<Integer>> childToParentMap = new HashMap<>();
        for(int[] parentToChild: parentChildArr){
            int parent = parentToChild[0];
            int child = parentToChild[1];
            childToParentMap.computeIfAbsent(child, k -> new HashSet<>()).add(parent);
            childToParentMap.putIfAbsent(parent, new HashSet<>());
        }
        Set<Integer> ancestors1 = new HashSet<>();
        dfs(node1, ancestors1, childToParentMap);
        return isCommonAncestorHelper(node2, ancestors1, childToParentMap);
    }

    public static boolean isCommonAncestorHelper(int root, Set<Integer> temp, Map<Integer, Set<Integer>> graph){
        for(Integer parent: graph.get(root)){
            if(!temp.add(parent) || isCommonAncestorHelper(parent, temp, graph)){
                return true;
            }

        }
        return false;
    }
    public static void dfs(int root, Set<Integer> temp, Map<Integer, Set<Integer>> graph){
        for(Integer parent: graph.get(root)){
            temp.add(parent);
            dfs(parent, temp, graph);
        }
    }


    public static List<List<Integer>> findNodesWithZeroAndOneParents(int[][] parentChildArr){
        Map<Integer, Integer> childToParenCountMap = new HashMap<>();
        for(int[] parentToChild: parentChildArr){
            int parent = parentToChild[0];
            int child = parentToChild[1];
            childToParenCountMap.put(parent, childToParenCountMap.getOrDefault(parent, 0) +0);
            childToParenCountMap.put(child, childToParenCountMap.getOrDefault(child, 0) +1);
        }
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0; i< 2; i++){
            result.add(new ArrayList<>());
        }
        for(int child: childToParenCountMap.keySet()){
            int val = childToParenCountMap.get(child);
            if( val < 2){
                result.get(val).add(child);
            }
        }
        return result;
    }
}
