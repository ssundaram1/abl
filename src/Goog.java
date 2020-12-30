import java.util.*;

/**
 * Created by ssundaram on 5/23/18.
 */
public class Goog {

    public static int[][] reconstructQueue(int[][] people) {

        System.out.println(" before sort "+Arrays.deepToString(people));

        Arrays.sort(people, (p1, p2) -> {
            return p1[0] == p2[0] ? p1[1] - p2[1] : p2[0] - p1[0];
        });
        System.out.println(" after sort "+Arrays.deepToString(people));
        LinkedList<int[]> list = new LinkedList<>();
        for (int i = 0; i < people.length; i++)
            list.add(people[i][1], people[i]);

        return list.toArray(people);
    }


    public static List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> res = new ArrayList<Integer>();

        if (matrix.length == 0) {
            return res;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length-1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            // Traverse Right
            for (int j = colBegin; j <= colEnd; j ++) {
                res.add(matrix[rowBegin][j]);
            }
            rowBegin++;

            // Traverse Down
            for (int j = rowBegin; j <= rowEnd; j ++) {
                res.add(matrix[j][colEnd]);
            }
            colEnd--;

            if (rowBegin <= rowEnd) {
                // Traverse Left
                for (int j = colEnd; j >= colBegin; j --) {
                    res.add(matrix[rowEnd][j]);
                }
            }
            rowEnd--;

            if (colBegin <= colEnd) {
                // Traver Up
                for (int j = rowEnd; j >= rowBegin; j --) {
                    res.add(matrix[j][colBegin]);
                }
            }
            colBegin ++;
        }

        return res;
    }

    public  static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return false;
        }

        final TreeSet<Integer> values = new TreeSet<>();
        for (int ind = 0; ind < nums.length; ind++) {

            final Integer floor = values.floor(nums[ind] + t);
            final Integer ceil = values.ceiling(nums[ind] - t);
            if ((floor != null && floor >= nums[ind])
                    || (ceil != null && ceil <= nums[ind])) {
                return true;
            }

            values.add(nums[ind]);
            if (ind >= k) {
                values.remove(nums[ind - k]);
            }
        }

        return false;
    }

//    Following the hint. Let f(n) = count of number with unique digits of length n.
//
//            f(1) = 10. (0, 1, 2, 3, ...., 9)
//
//    f(2) = 9 * 9. Because for each number i from 1, ..., 9, we can pick j to form a 2-digit number ij and there are 9 numbers that are different from i for j to choose from.
//
//    f(3) = f(2) * 8 = 9 * 9 * 8. Because for each number with unique digits of length 2, say ij, we can pick k to form a 3 digit number ijk and there are 8 numbers that are different from i and j for k to choose from.
//
//    Similarly f(4) = f(3) * 7 = 9 * 9 * 8 * 7....
//
//            ...
//
//    f(10) = 9 * 9 * 8 * 7 * 6 * ... * 1
//
//    f(11) = 0 = f(12) = f(13)....
//
//    any number with length > 10 couldn't be unique digits number.
//
//    The problem is asking for numbers from 0 to 10^n. Hence return f(1) + f(2) + .. + f(n)
//
//    As @4acreg suggests, There are only 11 different ans. You can create a lookup table for it. This problem is O(1) in essence.
    public static int countNumbersWithUniqueDigits(int n) {
        if (n == 0)     return 1;

        int res = 10;
        int uniqueDigits = 9;
        int availableNumber = 9;
        while (n-- > 1 && availableNumber > 0) {
            uniqueDigits = uniqueDigits * availableNumber;
            res += uniqueDigits;
            availableNumber--;
        }
        return res;
    }

//    SlidingWindow window = new SlidingWindow(3);
//window.add(1); // [1]
//window.add(2); // [1, 2]
//window.getProduct(); // 2
//window.add(3); // [1, 2, 3]
//window.getProduct(); // 6
//window.add(4); // [2, 3, 4]
//window.getProduct(); // 24

//window.add(1); // [1]
//window.add(2); // [1, 2]
//window.getProduct(); // 2
//window.add(3); // [1, 2, 3]
//window.getProduct(); // 6
//window.add(4); // [2, 3, 4]
//window.getProduct(); // 24


    static class SlidingWindow{
        int size =0;
        int idx =0;
        int[] vals;
        int product =1;
        int count =0;
        int zeroCount =0;
        public SlidingWindow(int size){
            this.size =size;
            vals = new int[size];
        }
        void add(int val){
            int temp = vals[idx];
            vals[idx]= val;
            idx = (idx+1)%size;
            count++;
            if(val ==0){
                zeroCount++;
            }else{
                product = product *val;
            }
            if(count > size){
                if(temp ==0){
                    zeroCount--;
                }else{
                    product = product/temp;
                }
            }
        }
        int getProduct(){
            return zeroCount >0? 0: product;
        }
    }


    //2, 1, 3, 4, 6  d =2
    // 3 - [2,1,3]

    //30,15,1,4,1,2,3,4,1

    public static int subArraySum(int[] nums, int k){
        PriorityQueue<Integer> minPq = new PriorityQueue<>();
        PriorityQueue<Integer> maxPq = new PriorityQueue<>(Collections.reverseOrder());

        int start=0, end =0;
        int sum =0, length = Integer.MIN_VALUE;
        while(end < nums.length){
            int current = nums[end++];
            minPq.add(current);
            maxPq.add(current);
            int max = maxPq.peek();
            int min = minPq.peek();
            while(start< nums.length && max - min > k){
                minPq.remove(nums[start]);
                maxPq.remove(nums[start]);
                max = maxPq.peek();
                min = minPq.peek();
                start++;
            }
            length = Math.max(length, end - start);
        }
        return length > Integer.MIN_VALUE ? length : -1;
    }


    //[2, 4, 5, 3, 6, 4, 7, 3]
    // 0-3, 3-7 largest


    //3, 4, 5, 3, 6, 4, 7, 2

    // 1,2,3,4,5,6,7

    //7,6,5,4,2

    //1 3 4 5 6 7 2
    //2 4 5 6 2 4 6 1


    public static int largestMountain(int[] nums){
        int left = 0, right = nums.length -1;
        int max = Math.max(nums[left], nums[right]);
        int largest =0;
        int iMax =0;
        int leftMAx =0;
        for(int i=1; i< nums.length; i++){
            if(nums[i] <= max){
               if(largest < i -left +1){
                   iMax =i;
                   leftMAx = left;
               }
                largest = Math.max(largest, i - left +1);
                left = i;
                max= Math.max(nums[left], nums[right]);
            }
        }
        System.out.println(" largest subarray "+Arrays.toString(Arrays.copyOfRange(nums, leftMAx, iMax+1)));
        return largest;
    }

    public static void main(String[] args){

        SlidingWindow window = new SlidingWindow(3);
        window.add(1); // [1]
        window.add(2); // [1, 2]
        System.out.println(" getProd: "+window.getProduct()); // 2
        window.add(3); // [1, 2, 3]
        System.out.println(" getProd: "+window.getProduct()); // 6
        window.add(4); // [2, 3, 4]
        System.out.println(" getProd: "+window.getProduct()); // 24
        window.add(0); // [3, 4, 0]
        System.out.println(" getProd: "+window.getProduct()); // 0
        window.add(1); // [4,0,1]
        window.add(2); // [0,1, 2]
        System.out.println(" getProd: "+window.getProduct()); // 0
        window.add(3); // [1, 2, 3]
        System.out.println(" getProd: "+window.getProduct()); // 6



        System.out.println(" sorted recon queue: "+ Arrays.deepToString(reconstructQueue(new int[][]{{7,0}, {4,4}, {7,1}, {5,0}, {6,1}, {5,2}})));
        System.out.println(" spiral matrix : "+ spiralOrder(
                new int[][]
                {
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9,10,11,12}
                }
        ));

        //[1,2,3,1], k = 4, t = 0
        System.out.println(" containsNearbyAlmostDuplicate :"+ containsNearbyAlmostDuplicate(new int[]{1,2,3,1}, 4, 0));

        System.out.println(subArraySum(new int[]{2, 1, 3, 4, 6}, 2));

        System.out.println(subArraySum(new int[]{30,15,1,4,1,2,3,4,1}, 15));


        System.out.println(subArraySum(new int[]{30,15,1,4,1,2,3,4,1}, 2));

  //      System.out.println("Largest mtn :"+ largestMountain(new int[]{ 2, 4, 5, 3, 6, 4, 7, 3}));
//
//        System.out.println("Largest mtn :"+ largestMountain(new int[]{ 1,1,1,2,5,6,7,3}));
//
//        System.out.println("Largest mtn :"+ largestMountain(new int[]{ 2,3,3,1,2,2,4,0}));

        System.out.println("Largest mtn :"+ largestMountain(new int[]{ 1,2,3,4}));

        System.out.println("Largest mtn :"+ largestMountain(new int[]{ 4,2,3,1}));

        System.out.println("Largest mtn :"+ largestMountain(new int[]{ 4,3,2,1}));





    }

}
