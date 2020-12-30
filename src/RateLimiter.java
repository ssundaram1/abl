import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateLimiter {
    Map<String, Long> clientToTimeStamp = new HashMap<>();
    //c1 - 1,2,3,4,5,5,5
    //c2 - 1,6,9
    //c3 - 1,7,10
    //limit =2 , interval = 5
    public static void main(String[] args){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("c1", Arrays.asList(new Integer[]{1,4,5,5,5}));
        map.put("c2", Arrays.asList(new Integer[]{1,6,9}));
        map.put("c3", Arrays.asList(new Integer[]{1,7,10}));
        System.out.println(getBurstyClients(2, 2, map));
        System.out.println("with SL "+getBurstyClientsWithSlidingWindow(2,2, map));
    }

    public static List<String> getBurstyClients(int limit, int interval, Map<String, List<Integer>> map){
        List<String> result = new ArrayList<>();
        for(String client: map.keySet()){
            List<Integer> timestamps = map.get(client);
            Collections.sort(timestamps);
            for(int i=0; i< timestamps.size(); i++){
                if(i !=0 && timestamps.get(i -1) == timestamps.get(i)){
                    continue;
                }
                if(isBursty(i, timestamps, limit, interval)){
                    result.add(client);
                    break;
                }
            }
        }
        return result;
    }

    public static List<String> getBurstyClientsWithSlidingWindow(int limit, int interval, Map<String, List<Integer>> map){
        List<String> result = new ArrayList<>();
        for(String client: map.keySet()){
            List<Integer> timestamps = map.get(client);
            Collections.sort(timestamps);
            if(isBurstyWithSL(timestamps, interval, limit)){
               result.add(client);
            }
        }
        return result;
    }

    private static boolean isBurstyWithSL(List<Integer> timestamps, int interval, int limit) {
        int start =0, end =0;
        while(end < timestamps.size()){
            while(start <= end && timestamps.get(end) - timestamps.get(start) +1 > interval){
                start++;
            }
            if(end - start +1 >= limit){
                return true;
            }
            end++;
        }
        return end - start  >= limit;
    }

    public static boolean isBursty(int start, List<Integer> timestamps, int limit, int interval){
        int end  =start;
        while(end < timestamps.size() && timestamps.get(end) == timestamps.get(start)){
            end++;
        }
        if(end - start >= limit){
            return true;
        }
        int target = timestamps.get(start) + interval -1;
        end = Collections.binarySearch(timestamps, target);
        if(end  < 0){
            end = -end -2;
        }
        return end - start +1 >= limit;
    }
}
