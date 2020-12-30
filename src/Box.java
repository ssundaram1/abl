import java.io.*;
import java.util.*;

/**
 * How many unique, 7-digit numbers can a chess knight dial, starting from the 1?
 *
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *   0
 *
 * For example, two such 7-digit numbers include:
 * 1 6 1 6 1 6 1
 * 1 6 0 4 3 8 3
 *
 * It is sometimes useful to begin with 3-digit numbers, as these can be explicitly enumerated:
 * - 161
 * - 167
 * - 160
 * - 181
 * - 183
 * Therefore, the answer is 5
 */

/*
 * To execute Java, please define "static void main" on a class
 * named Box.
 *
 * If you need more classes, simply define them inline.
 */

class Box {

    private static int getNumCombos(int size){
        Map<Integer,List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(6,8));
        graph.put(2, Arrays.asList(7,9));
        graph.put(3, Arrays.asList(4,8));
        graph.put(4, Arrays.asList(9,3,0));
        graph.put(5, Arrays.asList());
        graph.put(6, Arrays.asList(1,7,0));
        graph.put(7, Arrays.asList(2,6));
        graph.put(8, Arrays.asList(1,3));
        graph.put(9, Arrays.asList(2,4));
        graph.put(0, Arrays.asList(4,6));
        List<List<Integer>> result = new ArrayList<>();
        //helper(1, graph, 0, new ArrayList<>(), result, size);
        return getComboSize(size, graph, 1, new HashMap<>());
        //return result.size();
    }

    private static Integer getComboSize(int size, Map<Integer, List<Integer>> graph, int start, Map<String, Integer> memo){
        if(size ==1){
            return 1;
        }
      if(memo.containsKey(start + ":" +size)){
          return memo.get(start + ":" +size);
      }
        List<Integer> neighbors = graph.get(start);
        int sum =0;
        for(Integer neighbor: neighbors){
            sum+=getComboSize(size -1, graph, neighbor, memo);
        }
        memo.put(start + ":" +size, sum);
        return sum;
    }

    private static void helper(int start, Map<Integer, List<Integer>> graph, int level, List<Integer> temp, List<List<Integer>> result, int maxLevel){
        if(level == maxLevel -1){
            result.add(new ArrayList<>(temp));
            return;
        }
        List<Integer> neighbors = graph.get(start);
        for(Integer neighbor: neighbors){
            temp.add(neighbor);
            helper(neighbor, graph, level +1, temp, result, maxLevel);
            temp.remove(temp.size() - 1);
        }

    }

    public static void main(String[] args) {
        //System.out.println(" Size for combos of size 7:" + getNumCombos(7));
        System.out.println(" Size for combos of size 30:" + getNumCombos(30));

    }
}
