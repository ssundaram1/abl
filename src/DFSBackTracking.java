import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ssundaram on 5/20/18.
 */
public class DFSBackTracking {

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private static void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
        list.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

//    Input: [1,2,2]
//    Output:
//            [
//            [2],
//            [1],
//            [1,2,2],
//            [2,2],
//            [1,2],
//            []
//            ]

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrackWithDups(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private static void backtrackWithDups(List<List<Integer>> list, List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            if(i > start && nums[i] == nums[i-1]) {
                continue; // skip duplicates
            }
            tempList.add(nums[i]);
            backtrackWithDups(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }


//    Input: [1,2,3]
//    Output:
//            [
//            [1,2,3],
//            [1,3,2],
//            [2,1,3],
//            [2,3,1],
//            [3,1,2],
//            [3,2,1]
//            ]


    //Given a collection of distinct integers, return all possible permutations.

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        // Arrays.sort(nums); // not necessary
        backtrack(list, new ArrayList<>(), nums);
        return list;
    }

    private static void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums){
        if(tempList.size() == nums.length){
            list.add(new ArrayList<>(tempList));
        } else{
            for(int i = 0; i < nums.length; i++){
                if(tempList.contains(nums[i]))
                    continue; // element already exists, skip
                tempList.add(nums[i]);
                backtrack(list, tempList, nums);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    //Given a collection of numbers that might contain duplicates, return all possible unique permutations.

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
        return list;
    }

    private static void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
        if(tempList.size() == nums.length){
            list.add(new ArrayList<>(tempList));
        } else{
            for(int i = 0; i < nums.length; i++){

//                if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) {
//                    continue;
//                }
                if(used[i]){
                    continue;
                }
                //basically used[i-1] being false symbolizes we have started from used[i] in this case in the prv i-1 we have already calculated combos from i-1 which is same as i hence skip
                if(i > 0 && nums[i] == nums[i-1] && !used[i - 1]){
                    continue;
                }
                used[i] = true;
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, used);
                used[i] = false;
                tempList.remove(tempList.size() - 1);
            }
        }
    }


//    Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
// find all unique combinations in candidates where the candidate numbers sums to target.
//
//    The same repeated number may be chosen from candidates unlimited number of times.

//similar to subset prob above
    public static List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, target, 0);
        return list;
    }

    private static void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start){
        if(remain < 0) return;
        else if(remain == 0) list.add(new ArrayList<>(tempList));
        else{
            for(int i = start; i < nums.length; i++){
                tempList.add(nums[i]);
                backtrack(list, tempList, nums, remain - nums[i], i); // not i + 1 because we can reuse same elements
                tempList.remove(tempList.size() - 1);
            }
        }
    }

//    Given a collection of candidate numbers (candidates) and a target number (target),
// find all unique combinations in candidates where the candidate numbers sums to target.
//    Each number in candidates may only be used once in the combination.
    public static List<List<Integer>> combinationSum2(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrackCs(list, new ArrayList<>(), nums, target, 0);
        return list;

    }

    private static void backtrackCs(List<List<Integer>> list, List<Integer> tempList, int [] nums, int remain, int start){
        if(remain < 0) return;
        else if(remain == 0) list.add(new ArrayList<>(tempList));
        else{
            for(int i = start; i < nums.length; i++){
                if(i > start && nums[i] == nums[i-1]) continue; // skip duplicates
                tempList.add(nums[i]);
                backtrackCs(list, tempList, nums, remain - nums[i], i + 1);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    public static List<List<String>> partition(String s) {
        List<List<String>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), s, 0);
        return list;
    }

    public static void backtrack(List<List<String>> list, List<String> tempList, String s, int start){
        if(start == s.length())
            list.add(new ArrayList<>(tempList));
        else{
            for(int i = start; i < s.length(); i++){
                if(isPalindrome(s, start, i)){
                    tempList.add(s.substring(start, i + 1));
                    backtrack(list, tempList, s, i + 1);
                    tempList.remove(tempList.size() - 1);
                }
            }
        }
    }

    public static boolean isPalindrome(String s, int low, int high){
        while(low < high)
            if(s.charAt(low++) != s.charAt(high--)) return false;
        return true;
    }


//    private void permute(String str, int l, int r)
//    {
//        if (l == r)
//            System.out.println(str);
//        else
//        {
//            for (int i = l; i <= r; i++)
//            {
//                str = swap(str,l,i);
//                permute(str, l+1, r);
//                str = swap(str,l,i);
//            }
//        }
//    }

    private static void perm1(char[] a, int l) {
        if (l == a.length -1) {
            System.out.println(new String(a));
            return;
        }
        for (int i = l; i < a.length-1; i++) {
            swap(a, l, i);
            perm1(a, l+1);
            swap(a, l, i);
        }
    }

    private static void permute123(char[] str, int l, int r)
    {
        if (l == r)
            System.out.println(str);
        else
        {
            for (int i = l; i <= r; i++)
            {
                swap(str,l,i);
                permute123(str, l+1, r);
                swap(str,l,i);
            }
        }
    }

    // print n! permutation of the elements of array a (not in order)
    public static void perm2(String s) {
        int n = s.length();
        char[] a = new char[n];
        for (int i = 0; i < n; i++)
            a[i] = s.charAt(i);
        //perm2(a, n);
        perm1(s.toCharArray(),0);
    }

    private static void perm2(char[] a, int n) {
        if (n == 1) {
            System.out.println(new String(a));
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            perm2(a, n-1);
            swap(a, i, n-1);
        }
    }





    // swap the characters at indices i and j
    private static void swap(char[] a, int i, int j) {
        char c = a[i];
        a[i] = a[j];
        a[j] = c;
    }


    public void helper(int[] nums, List<Integer> temp, List<List<Integer>> result){
        if(nums.length == temp.size()){
            result.add(new ArrayList<>(temp));
        }
        for(int i=0; i< nums.length;i++){
            if(temp.contains(nums[i])){
                continue;
            }
            temp.add(nums[i]);
            helper(nums, temp, result);
            temp.remove(temp.size() - 1);
        }
    }

    public static void permutation(String str) {
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }



    public static void main(String[] args) {
        System.out.println("Subset: "+ subsets(new int[]{1,2,3}));

        System.out.println("Subset DUPS: "+ subsetsWithDup(new int[]{1,2,2}));

        System.out.println("premutations: "+ permute(new int[]{1,2,3}));

        System.out.println("premutations unique with dupes: "+ permuteUnique(new int[]{1,1,2}));

        System.out.println("combo sum: "+ combinationSum(new int[]{2,3,5},8 ));

        System.out.println("combo sum: "+ combinationSum2(new int[]{2,3,5},8 ));

        System.out.println("pali partition sum: "+ partition("aab"));

        System.out.println("printing string perms");perm2("abc");

        System.out.println("printing string perms");permute123("abc".toCharArray(), 0 , 2);


        //System.out.println(" distinct str perm "); permutation("", "aab");





    }
}
