package lyft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class WordCounter {

    public static void main(String[] args){
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        System.out.println("Printing the file passed in:");
        while(sc.hasNextLine()) System.out.println(sc.nextLine());
    }
}
