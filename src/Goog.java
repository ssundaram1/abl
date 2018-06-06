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


    public static void main(String[] args){

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

    }

}
