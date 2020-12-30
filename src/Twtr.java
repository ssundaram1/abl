import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Twtr {

    static class MapDetails{
        enum Parent{LEFT, RIGHT};
        private Parent parent;
        private Integer value;
        public MapDetails(Parent parent, Integer value){
            this.parent = parent;
            this.value = value;
        }
    }

    static class MapDiff{
        Map<String, MapDetails> differentKeysToValues = new HashMap<>();
        Map<String, Set<MapDetails>> sameKeysToDifferentValues = new HashMap<>();
    }

    static class MapDiff1{
        Map<MapDetails.Parent, Map<String,Integer>> parentMapToEntry = new HashMap<>();
        Map<MapDetails.Parent, Map<String,Integer>> sameKeysToDifferentValues = new HashMap<>();
    }

//differentKeysToValues {"four" -> {LEFT,9}}
// sameKeysToDifferentValues {}


    private static MapDiff calcDiff(Map<String,Integer> left, Map<String,Integer> right){
        MapDiff diffBetweenMaps = new MapDiff();
        if(left.isEmpty() && right.isEmpty()){
            return diffBetweenMaps;
        }
        if(!left.isEmpty()){
            for(Map.Entry<String,Integer> entry: left.entrySet()){
                String key = entry.getKey();
                Integer val = entry.getValue();
                if(!right.containsKey(key)){
                    MapDetails mapDetails = new MapDetails(MapDetails.Parent.LEFT, entry.getValue());
                    diffBetweenMaps.differentKeysToValues.put(key, mapDetails);
                }else if(!val.equals(right.get(key))){
                    Set<MapDetails> valueSet = new HashSet<>();
                    MapDetails valueInMap1 = new MapDetails(MapDetails.Parent.LEFT, entry.getValue());
                    MapDetails valueInMap2 = new MapDetails(MapDetails.Parent.RIGHT, right.get(key));
                    valueSet.add(valueInMap1);
                    valueSet.add(valueInMap2);
                    diffBetweenMaps.sameKeysToDifferentValues.put(entry.getKey(),valueSet);
                }
            }
        }

        if(!right.isEmpty()){
            for(Map.Entry<String,Integer> entry: right.entrySet()){
                String key = entry.getKey();
                Integer val = entry.getValue();
                if(!left.containsKey(key)){
                    diffBetweenMaps.differentKeysToValues.put(key, new MapDetails(MapDetails.Parent.RIGHT, val));
                }
            }
        }

        return diffBetweenMaps;

    }

    /*
[1, 1518290973]
[2, 1518291032]
[3, 1518291095]
[1, 1518291096]
[4, 1518291120]
[3, 1518291178]
[1, 1518291200]
[1, 1518291200]
[1, 1518291281]

User 1: 4 UAM
User 2: 1 UAM
User 3: 2 UAM
User 4: 1 UAM

Bin width = 2

2 users spend 0 - 1 UAM on Twitter
1 user spends 2 - 3 UAM on Twitter
1 user spends 4 - 5 UAM on Twitter

Expected result: [2, 1, 1]

----

User A: 3 UAM
User B: 4 UAM
User C: 12 UAM
User D: 13 UAM

Bin width = 5

0->4, 5->9, 10->14
 2      0     2
*/
    public static int[] getBinCounts(List<Pair<Integer, Long>> userToMinuteMap, int binWidth){

        Map<Integer, Set<Long>> userIdToDistinctMins = new HashMap<>();
        int max = Integer.MIN_VALUE;
        for(Pair<Integer,Long> entry: userToMinuteMap){
            Integer key = entry.getKey();
            if(!userIdToDistinctMins.containsKey(key)){
                userIdToDistinctMins.put(key, new HashSet<>());
            }
            userIdToDistinctMins.get(key).add(entry.getValue());
            if(userIdToDistinctMins.get(key).size() > max){
                max = userIdToDistinctMins.get(key).size();
            }
        }
        int [] temp = new int[max/binWidth +1];

        for(Map.Entry<Integer,Set<Long>> entry: userIdToDistinctMins.entrySet()){
            int sizeOfMins = entry.getValue().size();
            temp[sizeOfMins/binWidth]++;
        }
        return temp;
    }



    public static void main(String[] args){
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("one", 1);
        map1.put("two", 4);
        map1.put("four", 9);

        Map<String,Integer> map2 = new HashMap<>();
        map2.put("one", 1);
        map2.put("two", 5);

        MapDiff mapDiff = calcDiff(map1, map2);
        System.out.println(" different keys:");
        printMap(mapDiff.differentKeysToValues);
        System.out.println(" same keys:");
        printMapForSameKey(mapDiff.sameKeysToDifferentValues);

        Map<String,Integer> map3 = new HashMap<>();
         mapDiff = calcDiff(map3, map1);
        System.out.println(" different keys:");
        printMap(mapDiff.differentKeysToValues);
        System.out.println(" same keys:");
        printMapForSameKey(mapDiff.sameKeysToDifferentValues);
        List<Pair<Integer,Long>> userIdToMins = new ArrayList<>();
        userIdToMins.add(new Pair<>(1, 1518290973L));
        userIdToMins.add(new Pair<>(2, 1518291032L));
        userIdToMins.add(new Pair<>(3, 1518291095L));
        userIdToMins.add(new Pair<>(1, 1518291096L));
        userIdToMins.add(new Pair<>(4, 1518291120L));
        userIdToMins.add(new Pair<>(3, 1518291178L));
        userIdToMins.add(new Pair<>(1, 1518291200L));
        userIdToMins.add(new Pair<>(1, 1518291200L));
        userIdToMins.add(new Pair<>(1, 1518291281L));

        System.out.println(Arrays.toString(getBinCounts(userIdToMins, 2)));


    }

    private static void printMapForSameKey(Map<String, Set<MapDetails>> map){
        for(Map.Entry<String, Set<MapDetails>> entry: map.entrySet()){
            String key = entry.getKey();
            Set<MapDetails> mapDetails = entry.getValue();
            System.out.println("key: "+key + " parent :" );
            mapDetails.forEach( e -> System.out.println(" parent "+e.parent + " value "+ e.value));
        }
    }

    private static void printMap(Map<String,MapDetails> map) {
        for(Map.Entry<String, MapDetails> entry: map.entrySet()){
           String key = entry.getKey();
           MapDetails mapDetails = entry.getValue();
            System.out.println("key: "+key + " parent :" + mapDetails.parent + " value "+mapDetails.value);
        }
    }


}
