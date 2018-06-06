import java.util.Arrays;
import java.util.Stack;

/**
 * Created by ssundaram on 4/21/18.
 */
public class Matrix {

    //trick in rectangle probs is go get left and right idx

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int start = 0, rows = matrix.length, cols = matrix[0].length;
        int end = rows * cols - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (matrix[mid / cols][mid % cols] == target) {
                return true;
            }
            if (matrix[mid / cols][mid % cols] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return false;
    }

    private static int largestRecArea(int[] histogramHeights){

        Stack<Integer> positionStack = new Stack<>();
        int maxArea =Integer.MIN_VALUE;
        for(int currentHeightIdx=0; currentHeightIdx<=histogramHeights.length; currentHeightIdx++){
            if(positionStack.isEmpty() || histogramHeights[currentHeightIdx] >= histogramHeights[positionStack.peek()]){
                positionStack.push(currentHeightIdx);
            }else{
                int height = currentHeightIdx == histogramHeights.length ? 0 :histogramHeights[positionStack.pop()];
                int rightIndex = currentHeightIdx-1;
                int leftIndex = positionStack.peek();
                int length = positionStack.isEmpty() ? currentHeightIdx : rightIndex - leftIndex;
                int currentArea = length * height;
                maxArea = Math.max(maxArea,currentArea);
                currentHeightIdx--;
            }

        }
        return maxArea;
    }


    //key insight  - to find left index and right index while traversing array
    //1 when traversing array, current height will be compared to stack top height if >= we found the left index for current height
    // and push it on top of its left index
    //2 if current height < stack top height we found the right index for the stack top height
    //3 so when right index is found pop the stack top height and peek the height in the stack which was the left index of that height from step 1


    //add heights of histogram comparing to top of stack which will be the left index meaning from
    //as soon as the current height goes bust u know the right index of the rectangle
    //area = (right idx - left idx) * height
    //6, 2, 5, 4, 5, 1, 6
    public static  int largestRectangleArea(int[] height) {
        int len = height.length;
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= len; i++){
            int h = (i == len ? 0 : height[i]);
            if(s.isEmpty() || h >= height[s.peek()]){
                s.push(i);
            }else{
                int tp = s.pop();
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }

    public int maximalRectangle(char[][] matrix) {
        if(matrix.length == 0) return 0;
        int n = matrix[0].length;
        int[] heights = new int[n]; // using a array to reduce counting step of 1
        int max = 0;
        for(char[] row: matrix){
            for(int i = 0; i < n; i++){
                if(row[i] == '1'){
                    heights[i] += 1;
                } else {
                    heights[i] = 0;
                }
            }

            max = Math.max(max, maxArea(heights)); // go a sub problem of Histogram
        }
        return max;
    }

    // Exact same solution to Histogram
    //histogram refer to this method
    public int maxArea(int[] heights){
        Stack<Integer> stack = new Stack();
        int max = 0;
        for(int i = 0; i <= heights.length; i++){
            int h = (i == heights.length) ? 0 : heights[i];
            while(!stack.isEmpty() && heights[stack.peek()] > h){
                int index = stack.pop();
                int leftBound = -1;
                if(!stack.isEmpty()){
                    leftBound =  stack.peek();
                }
                max = Math.max(max, heights[index] * (i - leftBound - 1));
            }
            stack.push(i);
        }
        return max;
    }

    //https://leetcode.com/problems/maximal-rectangle/discuss/29054/Share-my-DP-solution
    ///trick is to accumulate heights and use the more restrictive left idx and right idx
    //restrcitive will be max (left(j), curr_leftidx)
    int maximalRectangle(int[][] matrix) {
        if(matrix.length == 0) return 0;
     int m = matrix.length;
     int n = matrix[0].length;
        int[] left = new int[n] ;
        int[] right = new int[n] ;
        int[] height = new int[n] ;
        Arrays.fill(left, 0);
        Arrays.fill(right, 0);
        Arrays.fill(height, 0);
        int maxA = 0;
        for(int i=0; i<m; i++) {
            int cur_left=0, cur_right=n;
            for(int j=0; j<n; j++) { // compute height (can do this from either side)
                if(matrix[i][j]=='1') height[j]++;
                else height[j]=0;
            }
            for(int j=0; j<n; j++) { // compute left (from left to right)
                if(matrix[i][j]=='1') left[j]=Math.max(left[j],cur_left);
                else {left[j]=0; cur_left=j+1;}
            }
            // compute right (from right to left)
            for(int j=n-1; j>=0; j--) {
                if(matrix[i][j]=='1') right[j]=Math.min(right[j],cur_right);
                else {right[j]=n; cur_right=j;}
            }
            // compute the area of rectangle (can do this from either side)
            for(int j=0; j<n; j++)
                maxA = Math.max(maxA,(right[j]-left[j])*height[j]);
        }
        return maxA;
    }


    public static void main(String[] args) {

//        Input:
//        matrix = [
//  [1,   3,  5,  7],
//  [10, 11, 16, 20],
//  [23, 30, 34, 50]
//]
//        target = 3
//        Output: true
        int[][] matrix = new int[][]
        {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 50}
        };
        System.out.println( searchMatrix(matrix, 3));
        System.out.println(largestRectangleArea(new int[] {6, 2, 5, 4, 5, 1, 6}));



    }
}
