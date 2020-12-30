package lyft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TSHashTable {

    private AtomicInteger version;
    private HashMap<Integer, TreeMap<Integer, Integer>> keyVersionValueMap;
    private List<Integer> result;


    private enum Op {
        put,
        get
    }

    private static class Operation {
         Op operation;
         Integer key;
         Integer value;
         public Operation(Op operation, int key, int value){
             this.key = key;
             this.value = value;
             this.operation = operation;
         }
    }

    private void put(int key, int value){
        keyVersionValueMap.putIfAbsent(key, new TreeMap<>());
        keyVersionValueMap.get(key).put(version.incrementAndGet(), value);
    }

    private int get(int key, int version){
        if(!keyVersionValueMap.containsKey(key)){
            return -1;
        }
        Map<Integer, Integer> versionToValueMap = keyVersionValueMap.get(key);
        Integer closestVersion = ((TreeMap<Integer, Integer>) versionToValueMap).floorKey(version);
        if(closestVersion == null){
            closestVersion = ((TreeMap<Integer, Integer>) versionToValueMap).ceilingKey(version);
        }

        return closestVersion == null ? -1: versionToValueMap.get(closestVersion);

    }

    private int get(int key){
        return get(key, version.get());
    }

    private Operation parse(String input){
        Op operation;
        if(input.startsWith(Op.put.name())){
            operation = Op.put;
        }else if(input.startsWith(Op.get.name())){
            operation = Op.get;
        }else{
            throw new IllegalArgumentException(" we only accept get or put as inputs please");
        }
        String[] args = input.substring(input.indexOf("(")+1, input.indexOf(")")).split(",");

        return new Operation(operation,Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    public static void main(String[] args){
        TSHashTable tsHashTable = new TSHashTable();
        List<String> inputStr = tsHashTable.readFile();
        for(String input: inputStr){
            try{
                Operation op = tsHashTable.parse(input);
                List<Integer> result = new LinkedList<>();
                tsHashTable.executeOp(op, result);
                for(Integer out: result){
                    System.out.println(out);
                }
            }catch(IllegalArgumentException e){
                System.out.println("illegal ar found in file :");
            }
        }

    }

    private  void executeOp(Operation op, List<Integer> result) {
        if(op.operation == Op.put){
            put(op.key, op.value);
        }else{
            result.add(op.value == null ? get(op.key): get(op.key, op.value));
        }
    }

    public TSHashTable(){
        keyVersionValueMap= new HashMap<>();
        version = new AtomicInteger();
        version.set(0);
    }

    private List<String> readFile() {
        List<String> input = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Printing the file passed in:");
        while(sc.hasNextLine()) input.add(sc.nextLine());
        return input;
    }
}
