import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
public class GMoney {

    /**
     * This class is filtered iterator takes a predicate and an iterator
     * next - returns the next element in the input which satisifies the predicate
     * hasNext will return true if there is an element which satisfies the predicate
     */
    public static class ConditionalIterator implements Iterator<Integer> {
        Iterator iterator = null;
        Predicate<Integer> predicate = null;
        Integer next = null;

        public ConditionalIterator(Iterator iterator, Predicate<Integer> predicate) {
            this.iterator = iterator;
            this.predicate = predicate;
            Integer tempNext = null;
            while (iterator.hasNext()) {
                tempNext = (Integer) iterator.next();
                if (predicate.test(tempNext)) {
                    next = tempNext;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                Integer tmp = next;
                next = null;
                Integer tempNext = null;
                while (iterator.hasNext()) {
                    tempNext = (Integer) iterator.next();
                    if (predicate.test(tempNext)) {
                        next = tempNext;
                        break;
                    }
                }
                return tmp;
            }
            return -1;
        }



    }
    public static class FaultyKeyboard{
        public void generateStrings(char [] str, int index,Set<String> dict,List<String> result){
            if(index==str.length )
            {
                String words[]= new String(str).split("\\s+");
                for(String w:words)
                    if(!dict.contains(w))
                        return;
                result.add(new String(str));
                return;
            }
            generateStrings(str,index+1,dict,result);
            if(str[index]==' '){
                str[index]='e';
                generateStrings(str,index+1,dict,result);
                str[index]=' ';
            }

        }

       public List<String> getValidSentences(String input, Set<String> dict){
           List<String> result = new ArrayList<>();
           helper(input, dict, 0, new StringBuilder(), result);
           return result;
       }

       public void helper(String input, Set<String> dict, int idx, StringBuilder sb, List<String> result){
         if(idx == input.length()){
             String[] words = sb.toString().split("\\s+");
             for(String word: words){
                 if(!dict.contains(word)){
                     return;
                 }
             }
             result.add(sb.toString());
             return;
         }
           helper(input, dict, idx +1, sb.append(input.charAt(idx)), result);
           sb.setLength(sb.length() -1);

           if(input.charAt(idx) == ' '){
               sb.append("e");
               helper(input, dict, idx +1, sb, result);
               sb.setLength(sb.length() -1);
           }
       }

        public void helper2(String input, Set<String> dict, int idx, char[] arr, List<String> result){
            if(idx == input.length()){
                String[] words = new String(arr).split("\\s+");
                for(String word: words){
                    if(!dict.contains(word)){
                        return;
                    }
                }
                result.add(new String(arr));
                return;
            }
            helper2(input, dict, idx +1, arr, result);
            if(input.charAt(idx) == ' '){
                arr[idx] = 'e';
                helper2(input, dict, idx +1, arr, result);
                arr[idx] = ' ';
            }
        }


    }

    //https://www.geeksforgeeks.org/k-th-missing-element-in-sorted-array/
//    Input : a[] = {2, 3, 5, 9, 10};
//    k = 1;
    // 3 4 5 6 8
    // 3 4 5 6 8

//    Input : a[] = {2, 3, 5, 9, 10, 11, 12};
//    k = 4;
    public static int kthMissing(int[] nums, int K){
        int k=0;
        for(int i=1; i< nums.length;i++){
            if(nums[i] - nums[i -1] > 1){
                k++;
            }
            if(k == K){
               return  nums[i] -1 ;
            }
        }
        return -1;
    }

    //    Input : a[] = {2, 3, 5, 9, 11, 12, 13};
    // 1,2,3,5,5,6,7
    // 1,3,4,5,7,8,9
//    k = 4;


    public static int kthMissingBinSearch(int[] nums, int K){
        int k=0;
        int low = 0, high = nums.length;
        while(low < high){
            int mid = low + (high - low)/2;
            int diff = nums[mid] - nums[low];
            int len = mid - low;
            int missing = diff - len;
            if(missing == K){

            } else if(K > missing){
                low = mid;
                K -=missing;
            }else{
                high = mid;
            }

        }
        return -1;
    }
//
//    Input:
//            (0, 3): A
//            (0, 3): B
//            (2, 4): C
//            (5, 6): D
//
//    Output:
//            (0, 2): [A, B]
//            (2, 3): [A, B, C]
//            (3, 4): [C]
//            (5, 6): [D]

//    Input:
//            (0, 3): A
//            (2, 4): B
//            (5, 6): C
//
//    Output:
//            (0, 2): [A]
//            (2, 3): [A, B]
//            (3, 4): [B]
//            (5, 6): [C]

    public static List<String> getNewIntervals(int[][] nums1, char[] chars1){
        Map<Integer, List<Event>> map = new TreeMap<>();
        int i=0;
        for(int[] interval: nums1){
            int start = interval[0];
            int end = interval[1];
            Event startEvt = new Event(chars1[i], true);
            Event endEvt = new Event(chars1[i], false);
            map.computeIfAbsent(start, k -> new ArrayList<>()).add(startEvt);
            map.computeIfAbsent(end, k -> new ArrayList<>()).add(endEvt);
            i++;
        }
        Set<Character> temp = new HashSet<>();
        List<String> result = new ArrayList<>();
        Integer prev =null;
        for(Integer time: map.keySet()){
            List<Event> events = map.get(time);
            if(prev != null && !temp.isEmpty()){
                String str = "("+prev+","+time+") :"+temp.toString();
                result.add(str);
            }
            prev = time;
            for(Event event: events){
                if(event.isStart){
                    temp.add(event.c);
                }else{
                    temp.remove(event.c);
                }
            }
        }
        return result;
    }

    static class Event{
        Character c;
        boolean isStart;
        public Event(Character c, boolean isStart){
            this.c = c;
            this.isStart = isStart;
        }
    }


//    Given an array nums of length n and an int k. All the elements are non-negative integers and the same elements are adjacent in the array. Find the majority element. The majority element is any element that appears more than|n/k| times. If there's no majority element return -1.
//
//    Example 1:
//
//    Input: nums = [1, 1, 1, 1, 3, 4, 0, 0, 0, 0, 0, 9], k = 3
//    Output: 0
//    Explanation: n = 12 -> n / k = 12 / 3 = 4. Only 0 appears more than 4 times so return 0.
//    Example 2:
//
//    Input: nums = [1, 1, 1, 1, 3, 4, 0, 0, 0, 0, 9, 9], k = 3
//    Output: -1
//    Explanation: There's no element that appears more than 4 times so return -1.

    // k =2
    //1,1,1,2
    public int generate(){
        Random rand = new Random();
        int n =0;
        n = n+1;
        int idx = rand.nextInt(n--);
        Map<Integer, Integer> map = new HashMap<>();
        int result = map.getOrDefault(idx, idx);
        map.put(idx, map.getOrDefault(n, n));
        return result;

    }

    // m = 3, n =4
    //a0,b0,c0,d0
    //a1,b1,c1,d1
    //a2,b2,c2,d2

    //nmm
//    0,0,0
//    0,0,1
//    0,0,2
//
//    0,1,0
//    1



    public static void main(String[] args) {
        //filtered iterator
        Predicate<Integer> odd = i -> (i % 2 == 1);
        Iterator<Integer> iterator = Arrays.asList(2, 2, 2, 1, 2).iterator();
        ConditionalIterator conditionalIterator = new ConditionalIterator(iterator, odd);
        while (conditionalIterator.hasNext()) {
            System.out.println("output for  "+conditionalIterator.next());
        }
       iterator = Arrays.asList(1,3,5,6,9).iterator();
       conditionalIterator = new ConditionalIterator(iterator, odd);
        while(conditionalIterator.hasNext()){
            System.out.println(conditionalIterator.next());
        }

        //faulty keyboard
        //Given a string which is the sentence typed using that keyboard and a dictionary of valid words, return all possible correct formation of the sentence.
        //Input: s = "I lik   to   xplor   univ rs ", dictionary  = {"like", "explore", "toe", "universe", "rse"}
        //        Output:
        //                ["I like to explore universe",
        //                "I like toe xplor  universe",
        //                "I like to explore univ rse",
        //                "I like toe xplor  univ rse"]
        FaultyKeyboard faultyKeyboard = new FaultyKeyboard();
        String input = "I lik   to   xplor   univ rs  ";
        //Set<String> dict = new HashSet<>(Arrays.asList("like", "explore", "toe", "universe", "rse"));
        Set<String> dict = new HashSet<>(Arrays.asList("I","like","to", "explore", "xplore", "toe", "universe", "rse"));
        System.out.println(faultyKeyboard.getValidSentences(input, dict));

        input="I lik  to  xplor  univ rs ";
       dict=  new HashSet<>(Arrays.asList("I","like","to", "explore", "xplore", "toe", "universe", "rse"));
        System.out.println(faultyKeyboard.getValidSentences(input, dict));

        input="I lik   to   xplor   univ rs  ";
        dict=  new HashSet<>(Arrays.asList("I","like","to", "explore", "xplore", "toe", "universe", "rse"));
        List<String> result= new ArrayList<>();
        faultyKeyboard.generateStrings(input.toCharArray(), 0,dict,result);
        System.out.println("Result="+ result.toString());
        int[][] nums1 = {{0, 3}, {2, 4}, {5,6}};
        char[] chars1 = {'A', 'B', 'C'};
        int[][] nums2 = {{0, 3}, {0, 3}, {2, 4}, {5, 6}};
        char[] chars2 = {'A', 'B', 'C', 'D'};
        System.out.println(getNewIntervals(nums1, chars1));
        System.out.println(getNewIntervals(nums2, chars2));
    }
}
