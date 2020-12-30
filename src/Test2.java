import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2 {

    int maxScore =0;

    static class Node{
        List<Node> children =new ArrayList<>();
        int score;
    }
    public static int getMaxScore(int[][] employees){
        Map<Integer, Node> map = new HashMap<>();
        Node node = new Node();
        node.score =-1;
        node.children = new ArrayList<>();
        map.put(0, node);
        buildTree(employees, 0, map);
        Node root = map.get(1);
        int[] result = maxScoreHelper(root);
        return Math.max(result[0], result[1]);
    }

    public static void buildTree(int[][] employees, int idx, Map<Integer, Node> map){
        if(idx == employees.length){
            return;
        }
        int parent = employees[idx][0];
        int selfId = employees[idx][1];
        int score = employees[idx][2];
        Node child = new Node();
        child.score = score;
        child.children = new ArrayList<>();
        map.put(selfId, child);
        if(map.containsKey(parent)){
            map.get(parent).children.add(child);
        }
        buildTree(employees, idx+1, map);
    }

    public static int[] maxScoreHelper(Node root){
        if(root.children.isEmpty()) {
            return new int[]{root.score, 0};
        }
        int excludingScore =0;
        int[][] scores = new int[root.children.size()][2];
        int i=0;
        int includingScore =root.score;
        for(Node child: root.children){
            scores[i++] =maxScoreHelper(child);
        }
        for(int[] score: scores){
            excludingScore+=Math.max(score[1], score[0]);
            includingScore+=score[1];
        }
        return new int[]{includingScore, excludingScore};
    }




    public static void main(String[] args) {
        int[][] input= new int[][]{
                {0, 1, 5},
                {1, 2, 2},
                {1, 3, 7},
                {2, 4, 3},
                {2, 5, 4},
                {3, 6, 1},
                {3, 7, 2}
        };

        System.out.println(getMaxScore(input));

       input= new int[][]{
                {0, 1, 5},
                {1, 2, 2},
                {1, 3, 9},
                {2, 4, 3},
                {2, 5, 4},
                {3, 6, 1},
                {3, 7, 2}
        };

        System.out.println(getMaxScore(input));

    }
}
