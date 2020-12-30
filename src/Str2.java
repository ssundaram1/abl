import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Str2 {

    static class CustomComp implements Comparator<char[]> {
        public int compare(char[] c1, char[] c2){
            for(int i=0; i< Math.min(c1.length, c2.length); i++){
                if(c1[i] > c2[i]){
                    return -1;
                }else if(c1[i] < c2[i]){
                    return 1;
                }
            }
            if(c1.length != c2.length){
                if(c1.length < c2.length){
                    return c1[c1.length -1] > c2[c1.length] ? -1 : 1;
                }else{
                    return c1[c2.length] > c2[c2.length -1] ? -1:1;
                }
            }
            return 0;
        }
    }
    //1, 2, 34
    //1, 0, 2
    public static String largestNumber(int[] nums) {
        String[] arr = new String[nums.length];
        for(int i=0; i< nums.length;i++){
            arr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(arr, (a,b) -> (b+a).compareTo(a +b));
        if(arr[0].charAt(0) == '0')
            return "0";

        StringBuilder sb = new StringBuilder();
        for(String s: arr){
            sb.append(s);
        }
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(largestNumber(new int[]{3,30,34,5,9}));
    }
}
