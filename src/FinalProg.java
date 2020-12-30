import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FinalProg {
    public static void main(String[] args){
        System.out.println("Input a list of integers:");
        List<String> input = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            input.add(scanner.next());
        }
        scanner.close();
        List<Integer> integerList = new ArrayList<>();
        int i=0;
        for(String s: input){
            integerList.add(Integer.valueOf(s));
        }
       System.out.println(" Recursive Max :" + recursiveMax(integerList, 0, input.size() -1));
    }


    public static int recursiveMax(List<Integer> list , int first, int last) {
        if(first == last) {
            return list.get(first);
        }
        if(last - first == 1) {
            return Math.max(list.get(first), list.get(last));
        }

        int mid = (first + last)/2;
        return Math.max(recursiveMax(list, first, mid), recursiveMax(list, mid+1, last));

    }
}



