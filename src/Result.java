import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Result {

    /*
     * Complete the 'largestContiguousBlock' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY matrix as parameter.
     */

    private static List<List<Integer>> board;
    private static int ROWS = 0;
    private static int COLS =0;
    private static int globalMax =1;
    private static int[][] dirs = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};
    public static int largestContiguousBlock(List<List<Integer>> matrix) {
        ROWS = matrix.size();
        COLS = matrix.get(0).size();
        board = matrix;
        Set<String> visited = new HashSet<>();

        // Write your code here
        for(int i=0; i< ROWS; i++){
            for(int j=0; j< COLS; j++){
                //String key = i +":"+j;
                if(visited.add(i +":"+j)){
                    int[] max = new int[1];
                    max[0]++;
                    dfs(i, j, visited, max);
                }


            }

        }
        return globalMax;

    }
//       //1 1 1 2 4
    // 5 1 5 3 1
    // 3 4 2 1 1

    /*
    3
    3
    1 2 3
    4 5 3
    7 8 9
    */
    //1, 2, 2
    //1, 2, 3

    public static void dfs(int row, int col, Set<String> visited, int[] runningMax){
        globalMax = Math.max(runningMax[0], globalMax);
        for(int dir[]: dirs){
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if(newRow >= 0  && newRow < ROWS && newCol >= 0 &&  newCol < COLS && board.get(newRow).get(newCol) == board.get(row).get(col) && !visited.contains(newRow +":"+newCol)){
                visited.add(newRow+":"+newCol);
                runningMax[0]++;
                dfs(newRow, newCol, visited, runningMax);
                //visited.remove(newRow+":"+newCol);
                //runningMax[0]--;
            }
        }
    }

    //ghiabcdefhelloadamhelloabcdefghi
    public static int largestPossibleSplit(String input){
     return recurse(input, 0, input.length());
    }

    public static int recurse(String input, int start, int endIdx){
        int max =1;
        for(int end =start+1; end< input.length(); end++){
            String front = input.substring(start, end);
            int backIdx = endIdx - front.length();
            String back = input.substring(backIdx, endIdx);
            if(front.equals(back)){
                max = Math.max(1 + recurse(input, end, backIdx), max);
            }
        }
        return max;
    }
    public static void main(String[] args) throws IOException {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(new Integer[]{3,3,3}));
        list.add(Arrays.asList(new Integer[]{3,3,3}));

        //System.out.println(largestContiguousBlock(list));
        System.out.println(largestPossibleSplit("ghiabcdefhelloadamhelloabcdefghi"));
        System.out.println(largestPossibleSplit("aba"));

        }
    }




