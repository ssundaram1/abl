import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InterviewingIo {

//    Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y.
//    For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, but "star" is not similar to "tars", "rats", or "arts".
//    Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that a word is in the group if and only if it is similar to at least one other word in the group.
//    We are given a list A of strings.  Every string in A is an anagram of every other string in A.  How many groups are there?
//    Example 1:
//    Input: ["tars","rats","arts","star"]
//    Output: 2


    public static void main(String[] args){
        System.out.println(" num comps "+ numOfGroups(Arrays.asList(new String[]{"tars","rats","arts","star"})));

        System.out.println(" num comps "+ numOfGroups(Arrays.asList(new String[]{"abcd","bcda","adcb","badc", "dcba", "adbc"})));

        System.out.println(" num comps "+ numOfGroups(Arrays.asList(new String[]{"abcd","adcb", "bdac", "dcab"})));
        List<List<Integer>> fgList = new ArrayList<>();
        fgList.add(Arrays.asList(new Integer[]{1,4}));
        fgList.add(Arrays.asList(new Integer[]{2,5}));
        fgList.add(Arrays.asList(new Integer[]{3,7}));

        List<List<Integer>> bgList = new ArrayList<>();
        bgList.add(Arrays.asList(new Integer[]{1,3}));
        bgList.add(Arrays.asList(new Integer[]{2,5}));
        bgList.add(Arrays.asList(new Integer[]{3,6}));


        System.out.println(findOptimalPairs(fgList, bgList, 10));

    }

    private static List<List<Integer>> findOptimalPairs(List<List<Integer>> fgList, List<List<Integer>> bgList, int deviceCapacity) {
        List<List<Integer>> result = new ArrayList<>();
        TreeMap<Integer, Integer> valToIdx = new TreeMap<>();
        int max = -1;
        if(deviceCapacity < 1){
            return result;
        }
        for(List<Integer> fg : fgList){
            valToIdx.put(fg.get(1), fg.get(0));
        }
        for(List<Integer> bg: bgList){
            int bgVal = bg.get(1);
            int idx = bg.get(0);
            Integer fgVal = valToIdx.floorKey(deviceCapacity - bgVal);
            if(fgVal != null && bgVal + fgVal >= max){
                if(bgVal + fgVal > max){
                    result = new ArrayList<>();
                    max = bgVal + fgVal;
                }
                result.add(Arrays.asList(new Integer[]{valToIdx.get(fgVal), idx}));
            }
        }
        return result;
    }

    public static int numOfGroups(List<String> inputList){

        Map<String, String> parentMap = new HashMap<>();
        for(String input: inputList){
            parentMap.put(input, input);
        }
        int numOfComps = inputList.size();
        for(int i=0; i< inputList.size(); i++){
            String curr = inputList.get(i);
            for(int j= i+1; j < inputList.size();j++){
                String next = inputList.get(j);
                if(isConnected(curr, next)){
                    String p1 = find(curr, parentMap);
                    String p2 = find(next, parentMap);
                    union(p1, p2, parentMap);
                    numOfComps--;
                }
            }
        }
        return numOfComps;
    }

    public static void union(String word1, String word2, Map<String,String> parentMap){
        parentMap.put(word1, word2);
    }

    public static String find(String word, Map<String,String> parentMap){
        while(!parentMap.get(word).equals(word)){
            parentMap.put(word, parentMap.get(parentMap.get(word)));
            word = parentMap.get(word);
        }
        return word;
    }

    public static boolean isConnected(String word1, String word2){

        int diff =0;
        for(int i=0; i< Math.min(word1.length(), word2.length()) ; i++){
            if(word1.charAt(i) != word2.charAt(i)){
                diff++;
            }
            if(diff > 2){
                return false;
            }
        }
        return diff == 2;
    }


}
