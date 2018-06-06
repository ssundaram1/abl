import com.google.common.collect.ImmutableMap;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.*;

/**
 * Created by ssundaram on 5/5/18.
 */
public class MathProbs {

    private static float power(float x, int y)
    {
        float temp;
        if( y == 0)
            return 1;
        temp = power(x, y/2);

        if (y%2 == 0)
            return temp*temp;
        else
        {
            if(y > 0)
                return x * temp * temp;
            else
                return (temp * temp) / x;
        }
    }


    // A function to print all prime factors
    // of a given number n
    public static void primeFactors(int n)
    {
        // Print the number of 2s that divide n
        while (n%2==0)
        {
            System.out.print(2 + " ");
            n /= 2;
        }

        // n must be odd at this point.  So we can
        // skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i+= 2)
        {
            // While i divides n, print i and divide n
            while (n%i == 0)
            {
                System.out.print(i + " ");
                n /= i;
            }
        }

        // This condition is to handle the case whien
        // n is a prime number greater than 2
        if (n > 2)
            System.out.println(n);
    }

    private static int rand3(){
        Random rand = new Random();
        return rand.nextInt(3) + 1;
    }

    private static void gen(){
        int first, second;
        for ( first=1; first<=3; ++first )
            for ( second=1; second<=3; ++second )
                System.out.printf ("%d \n", 3*first + second - 3);
    }

    private static int rand4(){
        int i;
        i = 3*rand3() + rand3() - 3;
        if (i < 9)
            return i%4 + 1;
        return rand4();
    }


//    Input: 1994
//    Output: "MCMXCIV"
//    Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.

    private static String intToRoman(int  num){


//
//        List<Integer> values = Arrays.asList(new Integer[]{1000,900,500,400,100,90,50,40,10,9,5,4,1});
//        List<String>  strs = Arrays.asList(new String[]{"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"});
//        StringBuilder sb = new StringBuilder();
//        if(num % 1000 > 0){
//            sb.append(strs.get(values.indexOf(1000)));
//            num = num%1000;
//        }
//        if(num % 100 > 0){
//
//            sb.append(strs.get(values.indexOf(1000)));
//        }

        System.out.println("TEst "+num % 1000);
        System.out.println(994 /100);


        return null;
    }

    public static String integToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
    }

    public static int compareVersion(String version1, String version2) {
        String[] levels1 = version1.split("\\.");
        String[] levels2 = version2.split("\\.");

        int length = Math.max(levels1.length, levels2.length);
        for (int i=0; i<length; i++) {
            Integer v1 = i < levels1.length ? Integer.parseInt(levels1[i]) : 0;
            Integer v2 = i < levels2.length ? Integer.parseInt(levels2[i]) : 0;
            int compare = v1.compareTo(v2);
            if (compare != 0) {
                return compare;
            }
        }

        return 0;
    }




    public static int findMaximumXOR(int[] nums) {
        int max = 0, mask = 0;
        for(int i = 31; i >= 0; i--){
            //set the ith bit
            mask = mask | (1 << i);
            Set<Integer> set = new HashSet<>();
            for(int num : nums){
                //get the ith bit
                set.add(num & mask);
            }
            //set the ith bit
            int tmp = max | (1 << i);
            for(int prefix : set){
                if(set.contains(tmp ^ prefix)) {
                    max = tmp;
                    break;
                }
            }
        }
        return max;
    }


    public static int myAtoi(String str) {
        int index = 0, sign = 1, total = 0;
        //1. Empty string
        if(str.length() == 0) return 0;

        //2. Remove Spaces
        while(str.charAt(index) == ' ' && index < str.length())
            index ++;

        //3. Handle signs
        if(str.charAt(index) == '+' || str.charAt(index) == '-'){
            sign = str.charAt(index) == '+' ? 1 : -1;
            index ++;
        }

        //4. Convert number and avoid overflow
        while(index < str.length()){
            int digit = str.charAt(index) - '0';
            if(digit < 0 || digit > 9) break;

            //check if total will be overflow after 10 times and add digit
            if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

            total = 10 * total + digit;
            index ++;
        }
        return total * sign;
    }

    public static String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];

        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2];

                pos[p1] += sum / 10;
                pos[p2] = (sum) % 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int p : pos) if(!(sb.length() == 0 && p == 0)) sb.append(p);
        return sb.length() == 0 ? "0" : sb.toString();
    }



    /* Program to test function power */
    public static void main(String[] args)
    {
        float x = 2;
        int y = -3;
        System.out.printf("%f", power(x, y));
        System.out.println("");
        primeFactors(315);
//        System.out.println("Rand 3: "+ rand3());
//        System.out.println("Rand 3: "+ rand3());
//        System.out.println("Rand 3: "+ rand3());
//        System.out.println("Rand 3: "+ rand3());

        System.out.println("Rand 4: "+ rand4());
        System.out.println("Rand 4: "+ rand4());
        System.out.println("Rand 4: "+ rand4());
        System.out.println("Rand 4: "+ rand4());
        //gen();
        intToRoman(1994);
        System.out.println(integToRoman(1994));
        System.out.println(myAtoi("-1234"));
        System.out.println(" multiplying two string: "+ multiply("123", "45"));
        System.out.println("max XOR: "+ findMaximumXOR(new int[] {3, 10, 5, 25, 2, 8}));
    }
}
