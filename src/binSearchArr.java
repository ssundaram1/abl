import java.util.Arrays;

/**
 * Created by ssundaram on 4/17/18.
 */
public class binSearchArr {

    //{5,6,1,2,3,4}
    //5, 6, 7, 8, 9, 10, 1, 2, 3
    private static int searchRotated(int[] rotateArr, int target){

        int pivot = searchPivot1(rotateArr, 0, rotateArr.length -1);
        if(target == rotateArr[pivot]){
            return pivot;
        }
        if(rotateArr[0] < target)
            return binarySearch(rotateArr, 0, pivot-1, target);

        return binarySearch(rotateArr, pivot+1, rotateArr.length -1, target);



    }

    static int binarySearch(int arr[], int low, int high, int key)
    {
        if (high < low)
            return -1;

       /* low + (high - low)/2; */
        int mid = (low + high)/2;
        if (key == arr[mid])
            return mid;
        if (key > arr[mid])
            return binarySearch(arr, (mid + 1), high, key);
        return binarySearch(arr, low, (mid -1), key);
    }


    private static int searchPivot(int[] rotateArr, int low, int high) {
        if(low > high){
            return -1;
        }
        if(low == high){
            return low;
        }
        int mid = (low+high)/2;
        if(rotateArr[mid] >  rotateArr[high]){
            return searchPivot(rotateArr, mid+1, high);
        }else
        {
            return searchPivot(rotateArr, low, mid -1);
        }

    }

    private static int searchPivot1(int[] rotateArr, int low, int high) {
       while(low < high){
           int mid = (low + high)/2;
           if(rotateArr[mid] >  rotateArr[high]){
               low = mid +1;
           }else{
               high = mid;
           }

       }
       return low;

    }

    public static int[] searchRange(int[] nums, int target) {
        int[] targetRange = {-1, -1};

        int leftIdx = extremeInsertionIndex(nums, target, true);

        // assert that `leftIdx` is within the array bounds and that `target`
        // is actually in `nums`.
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums, target, false)-1;

        return targetRange;
    }

    // returns leftmost (or rightmost) index at which `target` should be
    // inserted in sorted array `nums` via binary search.
    //key insight - when mid  > target , search on left  - for left idx we still wanna search if mid <= target so we add nums[mid] > target || (left && target == nums[mid])
    //else block is when mid <= target so search on right - this is whn searching for right idx
    private static int extremeInsertionIndex(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;

        while (lo < hi) {
            int mid = (lo+hi)/2;
            //in both cases we want to move the range left when finding the left range
            if (nums[mid] > target || (left && target == nums[mid])) {
                hi = mid;
            }
            else {
                //if num <= target keep low = mid
                lo = mid+1;
            }
        }

        return lo;
    }


    public static void main(String[] args){
        // Let us search 3 in below array
        int arr1[] = {5, 6, 7, 8, 9, 10, 1, 2, 3};
        int n = arr1.length;
        int key = 9;
        System.out.println("Index of the element is : "
                + searchRotated(arr1, key));

        int[] arr2 = {5,7,7,8,8,10};
        int target = 7;
        int[] rslt = searchRange(arr2, target);
        System.out.println("Index of the range is :{ "
                + rslt[0]+", "+rslt[1]+" }");

    }
}
