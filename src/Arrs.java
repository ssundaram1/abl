import java.util.*;

/**
 * Created by ssundaram on 3/30/18.
 */
public class Arrs {


    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }
        return res;
    }

    public static char nextGreatestLetter1(char[] a, char x) {
        int n = a.length;

        //hi starts at 'n' rather than the usual 'n - 1'.
        //It is because the terminal condition is 'lo < hi' and if hi starts from 'n - 1',
        //we can never consider value at index 'n - 1'
        int lo = 0, hi = n;

        //Terminal condition is 'lo < hi', to avoid infinite loop when target is smaller than the first element
       while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] > x) {
                hi = mid;
            }
            else {
                lo = mid + 1;
            }//a[mid] <= x
        }

        //Because lo can end up pointing to index 'n', in which case we return the first element
        return a[lo % n];
    }
    public static char nextGreatestLetter(char[] a, char x) {
        int n = a.length;

        if (x >= a[n - 1])   x = a[0];
        else    x++;

        int lo = 0, hi = n - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] == x)    return a[mid];
            if (a[mid] < x) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        return a[hi];
    }


    public static  int maxSubArray(int[] A) {
        int n = A.length;
        int[] dp = new int[n];//dp[i] means the maximum subarray ending with A[i];
        dp[0] = A[0];
        int max = dp[0];

        for(int i = 1; i < n; i++){
            dp[i] = A[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    private static class Interval{
        int start;
        int end;
        public Interval(int start, int end){
            this.start = start;
            this.end = end;
        }
    }



//    test(1, "123", true);
//    test(2, " 123 ", true);
//    test(3, "0", true);
//    test(4, "0123", true);  //Cannot agree
//    test(5, "00", true);  //Cannot agree
//    test(6, "-10", true);
//    test(7, "-0", true);
//    test(8, "123.5", true);
//    test(9, "123.000000", true);
//    test(10, "-500.777", true);
//    test(11, "0.0000001", true);
//    test(12, "0.00000", true);
//    test(13, "0.", true);  //Cannot be more disagree!!!
//    test(14, "00.5", true);  //Strongly cannot agree
//    test(15, "123e1", true);
//    test(16, "1.23e10", true);
//    test(17, "0.5e-10", true);
//    test(18, "1.0e4.5", false);
//    test(19, "0.5e04", true);
//    test(20, "12 3", false);
//    test(21, "1a3", false);
//    test(22, "", false);
//    test(23, "     ", false);
//    test(24, null, false);
//    test(25, ".1", true); //Ok, if you say so
//    test(26, ".", false);
//    test(27, "2e0", true);  //Really?!
//    test(28, "+.8", true);
//    test(29, " 005047e+6", true);  //Damn = =|||


    private static List<String> fullJustify(String[] words, int L) {
        List<String> list = new LinkedList<String>();

        for (int i = 0, w; i < words.length; i = w) {
            int len = -1;
            for (w = i; w < words.length && len + words[w].length() + 1 <= L; w++) {
                len += words[w].length() + 1;
            }

            StringBuilder strBuilder = new StringBuilder(words[i]);
            int space = 1, extra = 0;
            if (w != i + 1 && w != words.length) { // not 1 char, not last line
                space = (L - len) / (w - i - 1) + 1;
                extra = (L - len) % (w - i - 1);
            }
            for (int j = i + 1; j < w; j++) {
                for (int s = space; s > 0; s--) strBuilder.append(' ');
                if (extra-- > 0) strBuilder.append(' ');
                strBuilder.append(words[j]);
            }
            int strLen = L - strBuilder.length();
            while (strLen-- > 0) strBuilder.append(' ');
            list.add(strBuilder.toString());
        }

        return list;
    }



    int search(int A[], int n, int target) {
        int lo=0,hi=n-1;
        // find the index of the smallest value using binary search.
        // Loop will terminate since mid < hi, and lo or hi will shrink by at least 1.
        // Proof by contradiction that mid < hi: if mid==hi, then lo==hi and loop would have been terminated.
        while(lo<hi){
            int mid=(lo+hi)/2;
            if(A[mid]>A[hi]) lo=mid+1;
            else hi=mid;
        }
        // lo==hi is the index of the smallest value and also the number of places rotated.
        int rot=lo;
        lo=0;hi=n-1;
        // The usual binary search and accounting for rotation.
        while(lo<=hi){
            int mid=(lo+hi)/2;
            int realmid=(mid+rot)%n;
            if(A[realmid]==target)return realmid;
            if(A[realmid]<target)lo=mid+1;
            else hi=mid-1;
        }
        return -1;
    }

    private static List<String> textJustified(String[] words, int maxWidth){
        List<String> resultList = new ArrayList<>();
        for(int startWordIdx =0, currWordIdx; startWordIdx< words.length;startWordIdx= currWordIdx ){
            int currWidth = -1;
            //find max words to fit in a line so find the last idx which is value of  currIdx  after loop
            for(currWordIdx =startWordIdx; currWordIdx < words.length && (currWidth + words[currWordIdx].length()) +1 <= maxWidth ; currWordIdx++ ){
                currWidth = (currWidth + words[currWordIdx].length()) +1;
            }
            ;
            int spacesToFill = maxWidth - currWidth,  spaceBetweenWords = (currWordIdx - startWordIdx) -1,  spaceToEvenlyDistribute = 1,  remainderSpaceToDist = 0;
            StringBuilder sb = new StringBuilder(words[startWordIdx]);
            if(currWordIdx != startWordIdx +1 || currWordIdx != words.length){
                spaceToEvenlyDistribute = spacesToFill/spaceBetweenWords + 1;
                remainderSpaceToDist = spacesToFill % spaceBetweenWords;
            }
            for(int j = startWordIdx+1; j< currWordIdx ;j ++ ){
                for(int k =0; k < spaceToEvenlyDistribute;k++){
                    sb.append(' ');
                }
                if(remainderSpaceToDist > 0){
                    sb.append(' ');
                    remainderSpaceToDist--;
                }
                sb.append(words[j]);
            }
            int remaining = maxWidth-sb.length();
            while(remaining > 0) {
                sb.append(' ');
                remaining--;
            }
            resultList.add(sb.toString());
        }
        return resultList;

    }


    private static class NestedIntegerImpl implements NestedInteger{
        private Integer n;
        private List<NestedInteger> integerList;
         NestedIntegerImpl(Integer n){
            this.n = n;
        }
        NestedIntegerImpl(List<NestedInteger> integerList){
            this.integerList = integerList;
        }

        @Override
        public boolean isInteger() {
            return n != null;
        }

        @Override
        public Integer getInteger() {
            return n;
        }

        @Override
        public List<NestedInteger> getList() {
            return integerList;
        }
    }
    public interface NestedInteger
    {
        // Returns true if this NestedInteger holds a single integer, rather than a nested list
        public boolean isInteger();

        // Returns the single integer that this NestedInteger holds, if it holds a single integer
// Returns null if this NestedInteger holds a nested list
        public Integer getInteger();

        // Returns the nested list that this NestedInteger holds, if it holds a nested list
// Returns null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }


    private static int nestedSum(List<NestedInteger> list, int level){
        int sum =0;
        for(NestedInteger nestedInteger: list){
           if(nestedInteger.isInteger()){
               sum =+ nestedInteger.getInteger()* level ;
           }else{
               sum += nestedSum(nestedInteger.getList() , level+1);
           }
        }
        return sum;

    }

    private static int  depthSum(List<NestedInteger> nestedList) {
        return helper(nestedList, 1);
    }
    private static int  helper(List<NestedInteger> nl, int depth) {
        int res = 0;
        for (NestedInteger a : nl) {
            res += a.isInteger() ? a.getInteger() * depth : helper(a.getList(), depth + 1);
        }
        return res;
    }

    private static int  inverseDepthSumBFS(List<NestedInteger> nestedList) {
        int weightedSum =0, unweightedSum =0;
        List<NestedInteger> nextLevel = new ArrayList<>();
        while(!nestedList.isEmpty()){
            nextLevel = new ArrayList<>();
            for(NestedInteger ni: nestedList){
                if(ni.isInteger()){
                    unweightedSum+= ni.getInteger();
                }else{
                    nextLevel.addAll(ni.getList());
                }

            }
            weightedSum+= unweightedSum;
            nestedList = nextLevel;
        }
        return weightedSum;
    }


    private static int  inverseDepthSum(List<NestedInteger> nestedList) {
        Map<Integer, Integer> levelsToInt = new HashMap<>();
         inverseHelper(nestedList, 0, levelsToInt );
         int sum =0;
         int i =0;
         for(Map.Entry<Integer,Integer> entry : levelsToInt.entrySet()){
             sum+=(entry.getValue()) * (levelsToInt.size() - entry.getKey());
         }
        return sum;

    }
    private static void  inverseHelper(List<NestedInteger> nl, int level, Map<Integer, Integer> levelToInt) {
        for (NestedInteger nestedInteger : nl) {
            if(nestedInteger.isInteger()){
                levelToInt.put(level, levelToInt.getOrDefault(level, 0)+nestedInteger.getInteger());
            }else {
                inverseHelper(nestedInteger.getList(), level + 1, levelToInt);
            }
        }
    }


    // Returns a list containing all ways to factorize
    // a number n.
    public static List<List<Integer> > factComb(int n)
    {
        // making list of lists to store all
        // possible combinations of factors
        List<List<Integer> > result_list =
                new ArrayList<List<Integer> >();
        List<Integer> list = new ArrayList<Integer>();

        // function to calculate all the combinations
        // of factors
        factorsListFunc(2, 1, n, result_list, list);
        return result_list;
    }

    // First is current factor to be considered.
    // each_product is current product of factors.
    public static void factorsListFunc(int first,
                                       int each_prod, int n,
                                       List<List<Integer> > result_list, List<Integer>
                                               single_result_list)
    {
        // Terminating condition of this recursive
        // function
        if (first > n || each_prod > n)
            return;

        // When each_prod is equal to n, we get
        // the list of factors in 'single_result_
        // _list' so it is  added to the result_
        // _list list .
        if (each_prod == n) {

            ArrayList<Integer> t =
                    new ArrayList<Integer>(single_result_list);

            result_list.add(t);

            return;
        }

        // In this loop we first calculate factors
        // of n and then it's combination so that
        // we get the value n in a recursive way .
        for (int i = first; i < n; i++) {
            if (i * each_prod > n)
                break;

            // if i divides n
            // properly then it
            // is factor of n
            if (n % i == 0) {

                // it is added to 'single_result_list' list
                single_result_list.add(i);

                // Here function is called recursively
                // and when (i*each_prod) is equal to n
                // we will store the 'single_result_list' (it is
                // basically the list containing all
                // combination of factors) into result_list.
                factorsListFunc(i, i * each_prod, n,
                        result_list, single_result_list);

                // here we will empty the 'single_result_list'
                // List so that new combination of factors
                // get stored in it.
                single_result_list.remove(single_result_list.size() - 1);
            }
        }
    }

    public static List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < num.length-2; i++) {
            // only for i == 0 or only when the adjacent elements are different
            if (i == 0 || (i > 0 && num[i] != num[i-1])) {
                int lo = i+1, hi = num.length-1,
                        // trick here: since we try to find sum of 3 elems is 0
                sum = 0 - num[i];
                while (lo < hi) {
                    if (num[lo] + num[hi] == sum) {
                        res.add(Arrays.asList(num[i], num[lo], num[hi]));
                        //until same elem adjacent move to right or left accordingly
                        while (lo < hi && num[lo] == num[lo+1]) lo++;
                        while (lo < hi && num[hi] == num[hi-1]) hi--;
                        //finally on last elem move on
                        lo++; hi--;
                    } else if (num[lo] + num[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return res;
    }

    public static int threeSumClosest(int[] num, int target) {
        int result = num[0] + num[1] + num[num.length - 1];
        Arrays.sort(num);
        for (int i = 0; i < num.length - 2; i++) {
            int start = i + 1, end = num.length - 1;
            while (start < end) {
                int sum = num[i] + num[start] + num[end];
                if (sum > target) {
                    end--;
                } else {
                    start++;
                }
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
            }
        }
        return result;
    }

    public static int triangleNumber(int[] nums) {
        int result = 0;
        if (nums.length < 3) return result;

        Arrays.sort(nums);
        //key insight
        //for every sum starting from 2 to end - check if left init at 0 and right init at sum -1 add up to more than sum
        //find all Rs for the above condition starting from L ...  then we can easily say number of triangles with sum is right - left
        //because if for every L++ until R we will have L+R > sum so R-L triangles
        //if no then increment left and bring it to a point where left
        for (int sum = 2; sum < nums.length; sum++) {
            int left = 0, right = sum - 1;
            while (left < right) {
                if (nums[left] + nums[right] > nums[sum]) {
                    result += (right - left);
                    right--;
                }
                else {
                    left++;
                }
            }
        }

        return result;
    }

    public static void setZeroes(int[][] matrix) {
        boolean fr = false,fc = false;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0) {
                    if(i == 0) fr = true;
                    if(j == 0) fc = true;
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }
        for(int i = 1; i < matrix.length; i++) {
            for(int j = 1; j < matrix[0].length; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if(fr) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        if(fc) {
            for(int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

    }

    //TODO: generates a random integer number from [0,1] with a uniform distribution

    //jump game
     static boolean  canJump(int A[], int n) {
        int last=n-1,i,j;
        for(i=n-2;i>=0;i--){
            if(i+A[i]>=last)last=i;
        }
        return last<=0;
    }


    public static boolean isNumber(String s) {
        s = s.trim();
        boolean pointSeen = false;
        boolean eSeen = false;
        boolean numberSeen = false;
        for(int i=0; i<s.length(); i++) {
            if('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                numberSeen = true;
            } else if(s.charAt(i) == '.') {
                if(eSeen || pointSeen)
                    return false;
                pointSeen = true;
            } else if(s.charAt(i) == 'e') {
                if(eSeen || !numberSeen)
                    return false;
                numberSeen = false;
                eSeen = true;
            } else if(s.charAt(i) == '-' || s.charAt(i) == '+') {
                if(i != 0 && s.charAt(i-1) != 'e')
                    return false;
            } else
                return false;
        }
        return numberSeen;
    }

    double myPow(double x, int n) {
        if(n==0) return 1;
        double t = myPow(x,n/2);
        if(n%2 == 1) {
            return n < 0 ? 1 / x * t * t : x * t * t;
        }
        else {
            return t * t;
        }
    }


    private static double myPower(double number, int pow){
        if(pow == 0){
            return 1;
        }
        double tmp = myPower(number, pow/2);
        if(pow % 2 ==0){
            return tmp * tmp;
        }
        return (pow < 0) ? (1/tmp * tmp * number) : (tmp * tmp * number);
    }

//    You are given two lists of intervals, A and B.
//
//    In A, the intervals are sorted by their starting points. None of the intervals within A overlap.
//
//            Likewise, in B, the intervals are sorted by their starting points. None of the intervals within B overlap.
//
//    Return the intervals that overlap between the two lists.
//
//            Example:
//
//    A: {[0,4], [7,12]}
//    B: {[1,3], [5,8], [9,11]}
//    Return:
//
//    {[1,3], [7,8], [9,11]}



    //Given [1,3],[2,6],[8,10],[15,18],
    //return [1,6],[8,10],[15,18].
    private static List<Interval> mergeIntvl(List<Interval> intervals){
        Collections.sort(intervals,  (o1,o2)-> Integer.compare(o1.start, o2.start) );
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        List<Interval> mergedIntervals = new ArrayList<>();
        for(Interval interval : intervals){
            if(interval.start <= end){
                end = Math.max(end,interval.end); // Overlapping intervals, move the end if needed
            }else{
                // Disjoint intervals, add the previous one and reset bounds
                mergedIntervals.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            }
        }
        mergedIntervals.add(new Interval(start, end));
        return mergedIntervals;

    }


    //key insights
    ///median property - median of a set is an element which divides
    //https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2481/Share-my-O(log(min(mn))-solution-with-explanation
//    /To solve this problem, we need to understand “What is the use of median”.
// In statistics, the median is used for dividing a set into two equal length subsets, that one subset is always greater than the other. If we understand the use of median for dividing, we are very close to the answer.
//
//    First let’s cut A into two parts at a random position i:
//
//    left_A             |        right_A
//    A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
//    Since A has m elements, so there are m+1 kinds of cutting( i = 0 ~ m ). And we know: len(left_A) = i, len(right_A) = m - i
// . Note: when i = 0 , left_A is empty, and when i = m , right_A is empty.
//
//    With the same way, cut B into two parts at a random position j:
//
//    left_B             |        right_B
//    B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
//    Put left_A and left_B into one set, and put right_A and right_B into another set. Let’s name them left_part and right_part :
//
//    left_part          |        right_part
//    A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
//    B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
//    If we can ensure:
//
//            1) len(left_part) == len(right_part)
//2) max(left_part) <= min(right_part)
//    then we divide all elements in {A, B} into two parts with equal length, and one part is always greater than the other. Then median = (max(left_part) + min(right_part))/2.
//
//    To ensure these two conditions, we just need to ensure:
//
//            (1) i + j == m - i + n - j (or: m - i + n - j + 1)
//    if n >= m, we just need to set: i = 0 ~ m, j = (m + n + 1)/2 - i
//            (2) B[j-1] <= A[i] and A[i-1] <= B[j]
//    ps.1 For simplicity, I presume A[i-1],B[j-1],A[i],B[j] are always valid even if i=0/i=m/j=0/j=n . I will talk about how to deal with these edge values at last.
//
//            ps.2 Why n >= m? Because I have to make sure j is non-nagative since 0 <= i <= m and j = (m + n + 1)/2 - i. If n < m , then j may be nagative, that will lead to wrong result.
//
//            So, all we need to do is:
//
//    Searching i in [0, m], to find an object `i` that:
//    B[j-1] <= A[i] and A[i-1] <= B[j], ( where j = (m + n + 1)/2 - i )
//    Now let’s consider the edges values i=0,i=m,j=0,j=n where A[i-1],B[j-1],A[i],B[j] may not exist. Actually this situation is easier than you think.
//
//    What we need to do is ensuring that max(left_part) <= min(right_part). So, if i and j are not edges values(means A[i-1],B[j-1],A[i],B[j] all exist),
// then we must check both B[j-1] <= A[i] and A[i-1] <= B[j]. But if some of A[i-1],B[j-1],A[i],B[j] don’t exist,
// then we don’t need to check one(or both) of these two conditions.
// For example, if i=0, then A[i-1] doesn’t exist, then we don’t need to check A[i-1] <= B[j]. So, what we need to do is:
//
//    Searching i in [0, m], to find an object `i` that:
//            (j == 0 or i == m or B[j-1] <= A[i]) and
//            (i == 0 or j == n or A[i-1] <= B[j])
//    where j = (m + n + 1)/2 - i
//    And in a searching loop, we will encounter only three situations:
//
//<a> (j == 0 or i == m or B[j-1] <= A[i]) and
//            (i == 0 or j = n or A[i-1] <= B[j])
//    Means i is perfect, we can stop searching.
//
//<b> j > 0 and i < m and B[j - 1] > A[i]
//    Means i is too small, we must increase it.
//
//<c> i > 0 and j < n and A[i - 1] > B[j]
//    Means i is too big, we must decrease it.


    private static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        if (m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;
        double maxLeft = 0, minRight = 0;
        while(imin <= imax){
            i = (imin + imax) / 2;
            j = half - i;
            if(j > 0 && i < m && nums2[j - 1] > nums1[i]){
                imin = i + 1;
            }else if(i > 0 && j < n && nums1[i - 1] > nums2[j]){
                imax = i - 1;
            }else{
                if(i == 0){
                    maxLeft = (double)nums2[j - 1];
                }else if(j == 0){
                    maxLeft = (double)nums1[i - 1];
                }else{
                    maxLeft = (double)Math.max(nums1[i - 1], nums2[j - 1]);
                }
                break;
            }
        }
        if((m + n) % 2 == 1){
            return maxLeft;
        }
        if(i == m){
            minRight = (double)nums2[j];
        }else if(j == n){
            minRight = (double)nums1[i];
        }else{
            minRight = (double)Math.min(nums1[i], nums2[j]);
        }

        return (double)(maxLeft + minRight) / 2;
    }

//    //Given a contiguous sequence of numbers in which each number repeats thrice, there is exactly one missing number. Find the missing number.
//    eg: 11122333 : Missing number 2
//            11122233344455666 Missing number 5
static int missingNumber(int[] nums) {

    int lo = 0, hi = nums.length-1;

    while (lo < hi) {
        int mid = (lo+hi)/2;

        // We are left with 2 elements:
        if (hi - lo + 1 < 3) break;

        // Move mid to last element of same number:
        while (mid+1 <= hi && nums[mid] == nums[mid+1]) mid++;

        // If mid is at the end of the array we need to move it to the left:
        if (mid == hi) {
            while (nums[mid] == nums[mid-1]) mid--;
            mid--;
        }

        // Recurse on half that doesn't have 3 * number of elements:
        if ((mid-lo+1)% 3 == 0) {
            // Right:
            lo = mid+1;
        } else {
            // Left:
            hi = mid;
        }
    }

    return nums[lo];
}

  //2[a]3c aacccc
    //2[a2[c]b] accbaccb
    //2[abc]3[cd]ef
   private static String decodeString(String s){
       Stack<Integer> intStack = new Stack<>();
       Stack<StringBuilder> preFixStrStack = new Stack<>();
       StringBuilder cur = new StringBuilder();
       int k = 0;
       for (char ch : s.toCharArray()) {
           if (Character.isDigit(ch)) {
               k = k * 10 + ch - '0';
           } else if ( ch == '[') {
               intStack.push(k);
               preFixStrStack.push(cur);
               cur = new StringBuilder();
               k = 0;
           } else if (ch == ']') {
               StringBuilder tmp = cur;
               cur = preFixStrStack.pop();
               for (k = intStack.pop(); k > 0; --k) cur.append(tmp);
           } else cur.append(ch);
       }
       return cur.toString();

   }

//[
//        ["5","3",".",".","7",".",".",".","."],
//        ["6",".",".","1","9","5",".",".","."],
//        [".","9","8",".",".",".",".","6","."],
//        ["8",".",".",".","6",".",".",".","3"],
//        ["4",".",".","8",".","3",".",".","1"],
//        ["7",".",".",".","2",".",".",".","6"],
//        [".","6",".",".",".",".","2","8","."],
//        [".",".",".","4","1","9",".",".","5"],
//        [".",".",".",".","8",".",".","7","9"]
//        ]
    private static boolean isValidSudoku(char[][] board) {
        for(int i = 0; i<9; i++){
            HashSet<Character> rows = new HashSet<Character>();
            HashSet<Character> columns = new HashSet<Character>();
            HashSet<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9;j++){
                if(board[i][j]!='.' && !rows.add(board[i][j]))
                    return false;
                if(board[j][i]!='.' && !columns.add(board[j][i]))
                    return false;
                int RowIndex = 3*(i/3);
                int ColIndex = 3*(i%3);
                int idx1 = RowIndex + j/3;
                int idx2 = ColIndex + j%3;
                if(board[RowIndex + j/3][ColIndex + j%3]!='.' && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
                    return false;
            }
        }
        return true;
    }



    public static boolean isValidSudoku1(char[][] board) {
        Set seen = new HashSet();
        for (int i=0; i<9; ++i) {
            for (int j=0; j<9; ++j) {
                char number = board[i][j];
                if (number != '.')
                    if (!seen.add(number + " in row " + i) ||
                            !seen.add(number + " in column " + j) ||
                            !seen.add(number + " in block " + i/3 + "-" + j/3))
                        return false;
            }
        }
        return true;
    }

    public static void rotate(int[][] matrix) {
        for(int i = 0; i<matrix.length; i++){
            for(int j = i; j<matrix[0].length; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for(int i =0 ; i<matrix.length; i++){
            for(int j = 0; j<matrix.length/2; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length-1-j];
                matrix[i][matrix.length-1-j] = temp;
            }
        }
    }



//
//    Query(row1, col1, row2, col2)
//    (0, 2, 1,0)
//            (0,2)+ (0,0) + (1,2) + (1,0)
//            0, 1, 1,2
//    [1  2  3
//    2  3   4
//    2   5  6]

//    Input: tasks = ["A","A","A","B","B","B"], n = 2
//    Output: 8
//    Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.


    public static int leastInterval(char[] tasks, int n) {
        int[] map = new int[26];
        for (char c: tasks)
            map[c - 'A']++;
        Arrays.sort(map);
        int time = 0;
        //sort by task frequency
        //for the maximum freq task has pending tasks
        while (map[25] > 0) {
            int i = 0;
            //interleave with other tasks until cool off interval n
            while (i <= n) {
                if (map[25] == 0)
                    break;
                if (i < 26 && map[25 - i] > 0)
                    map[25 - i]--;
                time++;
                i++;
            }
            Arrays.sort(map);
        }
        return time;
    }

    public static int leastIntervalWithHeap(char[] tasks, int n) {
        int[] map = new int[26];
        for (char c: tasks)
            map[c - 'A']++;
        PriorityQueue < Integer > queue = new PriorityQueue < > (26, Collections.reverseOrder());
        for (int f: map) {
            if (f > 0)
                queue.add(f);
        }
        int time = 0;
        while (!queue.isEmpty()) {
            int i = 0;
            List < Integer > temp = new ArrayList < > ();
            while (i <= n) {
                if (!queue.isEmpty()) {
                    if (queue.peek() > 1)
                        temp.add(queue.poll() - 1);
                    else
                        queue.poll();
                }
                time++;
                if (queue.isEmpty() && temp.size() == 0)
                    break;
                i++;
            }
            for (int l: temp)
                queue.add(l);
        }
        return time;
    }

    static void sortColors(int A[], int n) {
        int second=n-1, zero=0;
        for (int i=0; i<=second; i++) {
            while (A[i]==2 && i<second) {
                swap(A, i, second);
                second --;
            }
            while (A[i]==0 && i>zero) {
                swap(A,i, zero);
                zero++;
            }
        }
    }

    private static void swap(int[] A, int i , int j){
       int temp = A[i];
       A[i] = A[j];
       A[j] = temp;
    }

    //73, 74, 75, 71, 69, 72, 76, 73
    private static int[] dailyTemp(int[] temps){
        int j =0;
        int[] result = new int[temps.length];
        for(int i=0; i< temps.length;i++){
            j =i+1;
            while(j < temps.length && temps[j] <= temps[i]){
               j++;
            }
            if(i != temps.length - 1) {
                result[i] = j - i;
            }
        }
        return result;

    }

    public static int maxProfitSingleTx(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        for(int i = 1; i < prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
    }


    public static int maxProfitMultiTxLocalPeakAndValley(int[] prices) {
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxprofit = 0;
        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1])
                i++;
            valley = prices[i];
            while (i < prices.length - 1 && prices[i] <= prices[i + 1])
                i++;
            peak = prices[i];
            maxprofit += peak - valley;
        }
        return maxprofit;
    }

    public static int maxProfitMultiTx(int[] prices) {
        int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                maxprofit += prices[i] - prices[i - 1];
        }
        return maxprofit;
    }

    public static int subarraySumN3(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                if (sum == k)
                    count++;
            }
        }
        return count;
    }

    public static int subarraySumN2(int[] nums, int k) {
        int count = 0;
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sum[end] - sum[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int subarraySumN1(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap < Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    static void find3Numbers(int arr[])
    {
        int n = arr.length;
        int max = n-1; //Index of maximum element from right side
        int min = 0; //Index of minimum element from left side
        int i;

        // Create an array that will store index of a smaller
        // element on left side. If there is no smaller element
        // on left side, then smaller[i] will be -1.
        int[] smaller = new int[n];
        smaller[0] = -1;  // first entry will always be -1
        for (i = 1; i < n; i++)
        {
            if (arr[i] <= arr[min])
            {
                min = i;
                smaller[i] = -1;
            }
            else
                smaller[i] = min;
        }

        // Create another array that will store index of a
        // greater element on right side. If there is no greater
        // element on right side, then greater[i] will be -1.
        int[] greater = new int[n];
        greater[n-1] = -1;  // last entry will always be -1
        for (i = n-2; i >= 0; i--)
        {
            if (arr[i] >= arr[max])
            {
                max = i;
                greater[i] = -1;
            }
            else
                greater[i] = max;
        }

        // Now find a number which has both a greater number
        // on right side and smaller number on left side
        for (i = 0; i < n; i++)
        {
            if (smaller[i] != -1 && greater[i] != -1)
            {
                System.out.print(arr[smaller[i]]+" "+
                        arr[i]+" "+ arr[greater[i]]);
                return;
            }
        }

        // If we reach number, then there are no such 3 numbers
        System.out.println("No such triplet found");
        return;
    }


//    initial : [1, 2, 0, 3], small = MAX, big = MAX
//    loop1 : [1, 2, 0, 3], small = 1, big = MAX
//    loop2 : [1, 2, 0, 3], small = 1, big = 2
//    loop3 : [1, 2, 0, 3], small = 0, big = 2 // <- Uh oh, 0 technically appeared after 2
//    loop4 : return true since 3 > small && 3 > big // Isn't this a violation??
//
//    If you observe carefully, the moment we updated big from MAX to some other value,
//    that means that there clearly was a value less than it (which would have been assigned to small in the past).
//    What this means is that once you find a value bigger than big, you've implicitly found an increasing triplet.
    public static boolean increasingTriplet(int[] nums) {
        // start with two largest values, as soon as we find a number bigger than both, while both have been updated, return true.
        int small = Integer.MAX_VALUE, big = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n <= small) { small = n; } // update small if n is smaller than both
            else if (n <= big) { big = n; } // update big only if greater than small but smaller than big
            else return true; // return if you find a number bigger than both
        }
        return false;
    }





    public static void main(String[] args){

        List<String> justifiedText = textJustified(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16);
        System.out.println("justified text biatch");
        justifiedText.forEach((e)-> System.out.println(e));
       ;
        justifiedText = fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16);
        System.out.println("Full justified text biatch");
        justifiedText.forEach((e)-> System.out.println(e));
        ;
        //depthSum({{1,1}, 2, {1,1}});
        int arr[] = {10, 13, 21, 22, 100, 101, 200, 300};
        System.out.println("Total number of triangles is " +
                triangleNumber(arr));

        System.out.println("Median of twi arrays is "+findMedianSortedArrays(new int[]{1,2,3,4},new int[]{5,6,7,8}));
        System.out.println(" product cept self "+ productExceptSelf(new int[]{1,2,3,4}));

        System.out.println(" factor combos: "+ factComb(32));
        NestedInteger n1 = new NestedIntegerImpl(1);
        List<NestedInteger> tmp = new ArrayList<>();
        tmp.add(new NestedIntegerImpl(2));
        tmp.add(new NestedIntegerImpl(3));
        NestedInteger n2 = new NestedIntegerImpl(tmp);

        List<NestedInteger> n3 = new ArrayList<>();
        n3.add(n1);
        n3.add(n2);
        System.out.println("Inverse depth sum is :" +inverseDepthSumBFS(n3));
        System.out.println(" Decoded Str "+decodeString("2[a2[c]b]"));
        System.out.println(nextGreatestLetter(new char[]{'c','f','j'}, 'a'));
        System.out.println(nextGreatestLetter1(new char[]{'c','f','j'}, 'k'));

        System.out.println(" valid soduku : "+ isValidSudoku(new char[][]{

                {5,3,4,6,7,8,9,1,2},
                {6,7,2,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,6,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,2,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,8,7,4,1,9,6,3,5},
                {3,4,5,2,8,6,1,7,9}
        }
        ));

        System.out.println(" valid soduku : "+ isValidSudoku1(new char[][]{

                        {5,3,4,6,7,8,9,1,2},
                        {6,7,2,1,9,5,3,4,8},
                        {1,9,8,3,4,2,5,6,7},
                        {8,5,9,7,6,1,4,2,3},
                        {4,2,6,8,5,3,7,9,1},
                        {7,1,3,9,2,4,8,5,6},
                        {9,6,1,5,3,7,2,8,4},
                        {2,8,7,4,1,9,6,3,5},
                        {3,4,5,2,8,6,1,7,9}
                }
        ));

        //Baozi Training
        System.out.println(" Schedule intervals: " +leastInterval(new char[]{'A','A','A','B','B','B'},2  ));
        int[] colors = new int[]{2,0,2,1,1,0};
        sortColors(colors, colors.length);
        System.out.println("daily temps: "+ Arrays.toString(dailyTemp(new int[] {73, 74, 75, 71, 69, 72, 76, 73})));
        System.out.println(" max stock: "+ maxProfitSingleTx(new int[]{7,1,5,3,6,}));

        System.out.println("stock sell multi tx peak and valley:"+ maxProfitMultiTxLocalPeakAndValley(new int[]{7,1,5,3,6,4}));
        System.out.println("stock sell multi tx :"+ maxProfitMultiTxLocalPeakAndValley(new int[]{7,1,5,3,6,4}));
        System.out.println(" sub arr sum: "+ subarraySumN3(new int[]{1,1,1}, 2));

        System.out.println(" sub arr sum: "+ subarraySumN2(new int[]{1,1,1}, 2));

    }
}
