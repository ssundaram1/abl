import java.io.*;
import java.util.*;
/*

The company you work for has decided to throw a party! You have been ask to join the party planning committee and have one of the most important jobs. One of the goals of this party is to really let employees have fun and let loose. To aid in that goal, the committee has decided that no direct managers will be allowed to attend. In other words, if a manager is invited, no directs of that manager may be invited. If an employee is invited, their direct manager may not be invited. We want the party to be a fun as possible so it will be important who we decide to invite. Each employee has been received a "fun score" that we will use to maximize the total "fun score" of our party.

Our job is to figure out the max fun score given our invitation restriction.

Complete the function maxFun in the editor below. The function takes a list of employees, each a three element array [m, e, s]. The employee array contains the employee's manager id m, the employee id e, and the employee's fun score s. The function must return a score that is the maximum possible fun score for the given employee list. The employee id '0' represents the root of the org. The employee with manager id 0 is presumed to be the CEO.

(Hint: It really helps to use a notepad to draw the employee org chart when working through the sample input/output examples)

*/

/*

Sample Case 0

0 1 5
1 2 2
1 3 7
2 4 3
2 5 4
3 6 1
3 7 2

Output => 15

We invite the CEO (employee 1), employee 4 & 5, and employee 6 & 7. The combined fun score of those employees is 15. If we were to invite employee 3, we would not be able to invite the CEO or employee 3's directs. This would reduce the fun score by 1.

*/

/*

Sample Case 1

0 1 5
1 2 2
1 3 9
2 4 3
2 5 4
3 6 1
3 7 2

Output => 16

This is the same input as example number 2, except employee 3 has a fun score of 9 instead of 7 as in the previous example. We now invite employee 4, 5, and 3 instead of the CEO because we yield a fun score of 16.

Clarification:
In other words, if a manager is invited, no directs of that manager may be invited. 
If an employee is invited, their direct manager may not be invited. We want the party to be a fun as possible so it will be important who we decide to invite.

*/

class Solution {
    int max  =0;
    static class Node{
        int parent;
        List<Integer> children;
        int val;
        public Node(int parent, List<Integer> children, int val){
            this.parent = parent;
            this.children = children;
            this.val = val;
        }
    }
    public static int maxFun(List<List<Integer>> employees) {
        Map<Integer, Node> idToNodeMap = new HashMap<>();
        Node node = new Node(-1, new ArrayList<>(), -1);
        idToNodeMap.put(0, node);
        helper(employees, idToNodeMap, 0);
        int[] result = recurse(idToNodeMap, 1);
        return Math.max(result[0], result[1]);

    }

    public static int[] recurse(Map<Integer, Node> idToNodeMap, int current){
        if(idToNodeMap.get(current).children.isEmpty()) {
            return new int[]{idToNodeMap.get(current).val, 0};
        }

        int[] withOrWithout = new int[2];

        Map<Integer, int[]> childMap = new HashMap<>();
        for(Integer child: idToNodeMap.get(current).children){
            childMap.put(child, recurse(idToNodeMap, child));
        }
        int withoutChild =0;
        for(Integer child: idToNodeMap.get(current).children){
            withoutChild+=childMap.get(child)[1];
        }
        //if current is used
        withOrWithout[0] = idToNodeMap.get(current).val + withoutChild;

        withOrWithout[1] = 0;
        for(Integer child: idToNodeMap.get(current).children){
            withOrWithout[1]+=Math.max(childMap.get(child)[1], childMap.get(child)[0]);
        }
        return withOrWithout;
    }

    public static void helper(List<List<Integer>> employees,  Map<Integer, Node> idToNodeMap, int level){
        if(level == employees.size()){
            return;
        }
        List<Integer> list = employees.get(level);
        int current = list.get(1);
        int parent = list.get(0);
        int val = list.get(2);
        Node node = new Node(current, new ArrayList<>(), val);
        idToNodeMap.put(current, node);
        Node parentNode = idToNodeMap.get(parent);
        parentNode.children.add(current);
        helper(employees, idToNodeMap, level+1);
    }



    public static void main(String[] args) {
        List<List<Integer>> sampleCase0 = new ArrayList<>();
        sampleCase0.add(Arrays.asList(0, 1, 5));
        sampleCase0.add(Arrays.asList(1, 2, 2));
        sampleCase0.add(Arrays.asList(1, 3, 7));
        sampleCase0.add(Arrays.asList(2, 4, 3));
        sampleCase0.add(Arrays.asList(2, 5, 4));
        sampleCase0.add(Arrays.asList(3, 6, 1));
        sampleCase0.add(Arrays.asList(3, 7, 2));

        System.out.println(maxFun(sampleCase0));

        sampleCase0.clear();
        sampleCase0.add(Arrays.asList(0, 1, 5));
        sampleCase0.add(Arrays.asList(1, 2, 2));
        sampleCase0.add(Arrays.asList(1, 3, 9));
        sampleCase0.add(Arrays.asList(2, 4, 3));
        sampleCase0.add(Arrays.asList(2, 5, 4));
        sampleCase0.add(Arrays.asList(3, 6, 1));
        sampleCase0.add(Arrays.asList(3, 7, 2));
        System.out.println(maxFun(sampleCase0));

    }
}