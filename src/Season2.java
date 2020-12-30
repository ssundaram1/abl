import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Season2 {

    public static void main(String[] args){
        Map<String,Integer> map = new HashMap<>();
        map.put("mobile.sports.yahoo.com", 12);
        map.put("sports.yahoo.com", 23);
        map.put("football.sports.yahoo.com", 34);
        map.put("movies.google.com", 45);
        //printCountsForDomains(map);
        //System.out.println(braceExpansionII("{a{b,c}}"));

        System.out.println(braceExpansionII("{c,{d,e}}"));
        System.out.println(braceExpansionII("{a,b}{c,{d,e}}"));
    }



    public static List<String> braceExpansionII(String expression) {
        String s = expression;
        char preSign = ',';
        Stack<List<String>> stack = new Stack<>();// Save List<String>
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            // case 1. {...} recursive, stack.operate(resList) by preSign
            if (c == '{'){
                int j = i, p = 1;
                while (s.charAt(j) != '}' || p != 0){
                    j++;
                    if (s.charAt(j) == '{') p++;
                    if (s.charAt(j) == '}') p--;
                }
                List<String> slist = braceExpansionII(s.substring(i+1, j));
                if (preSign == '*'){
                    stack.push(merge(stack.pop(), slist));
                }
                else stack.push(slist);
                i = j;
                //default preSign is *
                preSign = '*';
            }
            // case 2 letter operate by preSign
            else if (Character.isLetter(c)){
                List<String> slist = new ArrayList<>();
                slist.add(""+c);
                if (preSign == '*'){
                    stack.push(merge(stack.pop(), slist));
                }
                else stack.push(slist);
                // //default preSign is *
                preSign = '*';
            }
            // case 3. if  == ", ", preSign is  plus, (default preSign is *);
            if (c ==',' || i == s.length()-1){
                preSign = ',';
            }
        }
        // output stack to one dimesion list;
        List<String> res = new ArrayList<>();
        while(!stack.isEmpty()){
            for (String l : stack.pop())
                if (!res.contains(l))res.add(l);
        }
        // sort by lexi-order
        Collections.sort(res);
        return res;
    }
    // multiply operation of 2 List<letter>
    public static List<String> merge(List<String> list1, List<String> list2){
        List<String> res = new ArrayList<>();
        for (String l1 : list1){
            for (String l2 : list2){
                res.add(l1+l2);
            }
        }
        return res;
    }


    private static void printCountsForDomains(Map<String,Integer> urlToClicksMap){
        Map<String, Integer> domainToCountMap = new HashMap<>();
        PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>((a,b) -> (Integer)b.getValue() - (Integer)a.getValue());
        for(Map.Entry<String,Integer> entry:urlToClicksMap.entrySet()){
            Integer val = entry.getValue();
            String[] domains = entry.getKey().split( "\\.");
            String prev = "";
            for(int i=domains.length - 1; i >=0 ;i--){
                String key = domains[i];
                if(!prev.isEmpty()){
                    key = key +"."+ prev;
                }
                domainToCountMap.put(key, domainToCountMap.getOrDefault(key, 0)+val);
                prev = key;
            }
        }
        for(Map.Entry<String,Integer> entry:domainToCountMap.entrySet()){
            pq.add(entry);
       }
        while(!pq.isEmpty()){
            Map.Entry<String, Integer> entry = pq.remove();
            System.out.println(entry.getValue() +"    "+entry.getKey());
        }

    }





    public String customSortString(String S, String T) {
        int[] map = new int[26];

        for(int i=0; i < S.toCharArray().length; i++){
            map[S.charAt(i) - 'a'] = i;
        }
        Character[] resultArr = new Character[T.length()];
        for (int i = 0; i < T.length(); i++) {
            resultArr[i] = T.charAt(i);
        }
        Arrays.sort(resultArr, new Comparator<Character>(){

            @Override
            public int compare(Character c1, Character c2)
            {
                // ignoring case
                return Character.compare(Character.toLowerCase(c1),
                        Character.toLowerCase(c2));
            }
        });




        return null;

    }
}
