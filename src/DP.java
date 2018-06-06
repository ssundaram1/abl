/**
 * Created by ssundaram on 1/27/18.
 */
public class DP {




//    Gap = 1:
//            (0, 1) (1, 2) (2, 3) (3, 4)
//
//    Gap = 2:
//            (0, 2) (1, 3) (2, 4)
//
//    Gap = 3:
//            (0, 3) (1, 4)
//
//    Gap = 4:
//            (0, 4)

    // A DP function to find minimum number
    // of insersions
    static int findMinInsertionsDP(char str[], int n)
    {
        // Create a table of size n*n. table[i][j]
        // will store minumum number of insertions
        // needed to convert str[i..j] to a palindrome.
        int table[][] = new int[n][n];
        int l, h, gap;

        // Fill the table
        for (gap = 1; gap < n; ++gap)
            for (l = 0, h = gap; h < n; ++l, ++h)
                table[l][h] = (str[l] == str[h])?
                        table[l+1][h-1] :
                        (Integer.min(table[l][h-1],
                                table[l+1][h]) + 1);


        // Return minimum number of insertions
        // for str[0..n-1]
        return table[0][n-1];
    }

//    We don't know whether the current choice is optimal for next stage or not (the difference with greedy problem)?
// Thus, we delay the decision to make choice for current step at the next step.
//    Case:
//    Stage 1: [0 0] = 1; [0 1] = 5; [0 2] = 6
//    Stage 2: [1 0] = 1; [0 1] = 15; [0 2] = 17
//    At stage 1, if we use greedy algorithm, we definitely should choose the red color ([0 0] = 1). Actually this not a global optimal solution, since at stage 2, we have to choose from blue ([0 1] = 15) and green ([0 2] = 17).
//    The overall minimum cost for this solution is 16, which is much larger than the best plan: "[0 1] = 5" and "[1 0] = 1".
//
//    The problem with this solution is that,
// we could not make the stage 1's choice based on current information,
// since it would affect our available choices at stage 2. At current stage,
// we should only prepare the right information for next stage to directly use, and let the next stage to make choice for the current stage.
//
//    Transitional function:
//    Assume:
//    min_red[i] : the minimum cost to paint houses from 0 to i, iff i was painted with red color.
//    min_blue[i] : the minimum cost to paint houses from 0 to i, iff i was painted with blue color.
//    min_green[i] : the minimum cost to paint houses from 0 to i, iff i was painted with green color.
//
//    Transitional function.
//    min_red[i] = Math.min(min_blue[i-1], min_green[i-1]) + red_cost[i]
//    We actually made the decision for the previous stage at here. (if i house was painted as red).
    public static int paintHouseI(int[][] costs) {
        if (costs == null)
            throw new IllegalArgumentException("costs is null");
        if (costs.length == 0)
            return 0;
        int min_red = costs[0][0];
        int min_blue = costs[0][1];
        int min_green = costs[0][2];
        int temp_red, temp_blue, temp_green;
        for (int i = 1; i < costs.length; i++) {
            temp_red = min_red;
            temp_blue = min_blue;
            temp_green = min_green;
            min_red = Math.min(temp_blue, temp_green) + costs[i][0];
            min_blue = Math.min(temp_red, temp_green) + costs[i][1];
            min_green = Math.min(temp_red, temp_blue) + costs[i][2];
        }
        return Math.min(Math.min(min_red, min_blue), min_green);
    }

    private static int paintHouseMine(int[][] costMatrix){
        int prevMin = 0;
        int prevSecondMin = 0;
        int prevMinIdx =-1;
        //loop over all houses and pick the write the min cost of painting them with that color
        for(int i=0; i< costMatrix.length; i ++){
            //for each house we have current minimum and current second min
            int currentMin = Integer.MAX_VALUE;
            int currentSecondMin = Integer.MAX_VALUE;
            int currentMinIdx =0;
            //iterate over all colors
            for(int j=0; j< costMatrix[0].length; j++){
                if(prevMinIdx ==j){
                    costMatrix[i][j] += prevSecondMin;
                }else{
                    costMatrix[i][j] += prevMin;
                }
                if(currentMin > costMatrix[i][j]){
                    currentSecondMin = currentMin;
                    currentMin = costMatrix[i][j];
                    currentMinIdx = j;
                }else if(currentSecondMin > costMatrix[i][j]){
                    currentSecondMin = costMatrix[i][j];
                }

            }
            prevMin = currentMin;
            prevSecondMin = currentSecondMin;
            prevMinIdx = currentMinIdx;

        }
        int result = Integer.MAX_VALUE;
        for(int i =0; i< costMatrix[0].length;i++){
            if(result < costMatrix[costMatrix.length - 1][i]){
                result = costMatrix[costMatrix.length - 1][i];
            }

        }
        return result;
    }


    public int paintHouseII(int[][] costs) {
        if(costs==null || costs.length==0)
            return 0;
        int preMin=0;
        int preSecond=0;
        int preIndex=-1;
        for(int i=0; i<costs.length; i++){
            int currMin=Integer.MAX_VALUE;
            int currSecond = Integer.MAX_VALUE;
            int currIndex = 0;
            for(int j=0; j<costs[0].length; j++){
                if(preIndex==j){
                    costs[i][j] += preSecond;
                }else{
                    costs[i][j] += preMin;
                }
                if(currMin>costs[i][j]){
                    currSecond = currMin;
                    currMin=costs[i][j];
                    currIndex = j;
                } else if(currSecond>costs[i][j] ){
                    currSecond = costs[i][j];
                }
            }
            preMin=currMin;
            preSecond=currSecond;
            preIndex =currIndex;
        }
        int result = Integer.MAX_VALUE;
        for(int j=0; j<costs[0].length; j++){
            if(result>costs[costs.length-1][j]){
                result = costs[costs.length-1][j];
            }
        }
        return result;
    }

    static int minInsertion2(String str)
    {
        int n = str.length();

        // Create a table to store
        // results of subproblems
        int L[][] = new int[n][n];

        // Strings of length 1
        // are palindrome of length 1
        for (int i = 0; i < n; i++)
            L[i][i] = 0;

        // Build the table. Note that the lower diagonal
        // values of table are useless and not filled in
        // the process. c1 is length of substring
        for (int cl=2; cl<=n; cl++)
        {
            for (int i=0; i<n-cl+1; i++)
            {
                int j = i+cl-1;
                L[i][j] = (str.charAt(i) == str.charAt(j))?
                        L[i+1][j-1] :
                        (Integer.min(L[i][j-1],
                                L[i+1][j]) + 1);
                System.out.println(L[i][j]);
            }
        }

        // length of longest palindromic subsequence
        return L[0][n-1];
    }


    // Returns the length of the longest
    // palindromic subsequence in 'str'
    static int lps(String str)
    {
        int n = str.length();

        // Create a table to store
        // results of subproblems
        int L[][] = new int[n][n];

        // Strings of length 1
        // are palindrome of length 1
        for (int i = 0; i < n; i++)
            L[i][i] = 1;

        // Build the table. Note that the lower diagonal
        // values of table are useless and not filled in
        // the process. c1 is length of substring
        for (int cl=2; cl<=n; cl++)
        {
            for (int i=0; i<n-cl+1; i++)
            {
                int j = i+cl-1;
                if (str.charAt(i) == str.charAt(j) && cl == 2)
                    L[i][j] = 2;
                else if (str.charAt(i) == str.charAt(j))
                    L[i][j] = L[i+1][j-1] + 2;
                else
                    L[i][j] = Integer.max(L[i][j-1], L[i+1][j]);
            }
        }

        // length of longest palindromic subsequence
        return L[0][n-1];
    }

    // function to calculate minimum
    // number of deletions
    static int minimumNumberOfDeletions(String str)
    {
        int n = str.length();

        // Find longest palindromic subsequence
        int len = lps(str);

        // After removing characters other than
        // the lps, we get palindrome.
        return (n - len);
    }



    // Given a digit sequence of length n,
// returns count of possible decodings by
// replacing 1 with A, 2 woth B, ... 26 with Z
    private static int countDecoding(char[] digits, int n)
    {
        // base cases
        if (n == 0 || n == 1)
            return 1;

        // Initialize count
        int count = 0;

        // If the last digit is not 0, then
        // last digit must add to
        // the number of words
        if (digits[n - 1] > '0')
            count = countDecoding(digits, n - 1);

        // If the last two digits form a number
        // smaller than or equal to 26,
        // then consider last two digits and recur
        if (digits[n - 2] == '1' ||
                (digits[n - 2] == '2' && digits[n - 1] < '7'))
            count += countDecoding(digits, n - 2);

        return count;
    }


    static int countDecodingDP(char[] digits, int n)
    {
        int count[] = new int[n+1]; // A table to store results of subproblems
        count[0] = 1;
        count[1] = 1;

        for (int i = 2; i <= n; i++)
        {
            count[i] = 0;

            // If the last digit is not 0, then last digit must add to
            // the number of words
            if (digits[i-1] > '0')
                count[i] = count[i-1];

            // If second last digit is smaller than 2 and last digit is
            // smaller than 7, then last two digits form a valid character
            if (digits[i-2] == '1' || (digits[i-2] == '2' && digits[i-1] < '7') )
                count[i] += count[i-2];
        }
        return count[n];
    }



    // Driver program to test above function.
    public static void main(String args[])
    {
        String str = "geeks";
        String str1 = "abcd";

        System.out.println(" "+
                findMinInsertionsDP(str1.toCharArray(), str1.length()));
        System.out.println(" "+
                minInsertion2(str1));

        System.out.println("Minimum number of deletions = "
                + minimumNumberOfDeletions("geeksforgeeks"));

        char digits[] = {'1', '2', '3'};
        int n = digits.length;
        System.out.println("Decoding ways is"+ countDecoding(digits, n));
        System.out.println("Decoding ways is"+ countDecodingDP(digits, n));

    }


}
