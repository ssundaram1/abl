public class MatrixOrderOptimization {
    protected int[][]m;
    protected int[][]s;
    public static void matrixChainOrder(int[] dims) {
        int n = dims.length - 1;
         int[][]m;
         int[][]s;
        m = new int[n][n];
        s = new int[n][n];

        for (int lenMinusOne = 1; lenMinusOne < n; lenMinusOne++) {
            for (int i = 0; i < n - lenMinusOne; i++) {
                int j = i + lenMinusOne;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = m[i][k] + m[k+1][j] + dims[i]*dims[k+1]*dims[j+1];
                    if (cost < m[i][j]) {
                        m[i][j] = cost;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    /* A naive recursive implementation that simply follows
   the above optimal substructure property */
        static int matrixChainOrderRec(int p[], int i, int j)
        {
            if (i == j)
                return 0;

            int min = Integer.MAX_VALUE;

            // place parenthesis at different places between first
            // and last matrix, recursively calculate count of
            // multiplications for each parenthesis placement and
            // return the minimum count
            for (int k=i; k<j; k++)
            {
                int count = matrixChainOrderRec(p, i, k) +
                        matrixChainOrderRec(p, k+1, j) +
                        p[i-1]*p[k]*p[j];

                if (count < min)
                    min = count;
            }

            // Return minimum count
            return min;
        }

        // Nate program to test above function
        public static void main(String args[])
        {
            int arr[] = new int[] {1, 2, 3, 4, 3};
            int n = arr.length;

          matrixChainOrder(arr);

        }

//https://www.youtube.com/watch?v=GMzVeWpyTN0
   public static void matrixChainOrderv1(int dims[])
    {
        // length[dims] = n + 1

        // A1  A2   A3   A4  A5
        //P0 P1  P2   P3   P4  P5
        //4 * 10 * 5 * 6  * 6 * 10
        int n = dims.length - 1;
         int[][]m= new int[n][n];
         int[][]s = new int[n][n];
        // m[i,j] = Minimum number of scalar multiplications (i.e., cost)
        // needed to compute the matrix A[i]A[i+1]...A[j] = A[i..j]
        // The cost is zero when multiplying one matrix

        //m[i,j] = m[i,k] + m[k+1,j] +p[i-1][k][j]
        for (int i = 1; i <= n; i++)
            m[i] [i] = 0;

        for (int len = 2; len <= n; len++) { // Subsequence lengths
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int cost = m[i][k] + m[k+1] [j] + dims[i-1]*dims[k]*dims[j];
                    if (cost < m[i] [j]) {
                        m[i][j] = cost;
                        s[i][j] = k; // Index of the subsequence split that achieved minimal cost
                    }
                }
            }
        }
        System.out.println("min no of ops "+ m[n][n]);
    }

    public void printOptimalParenthesizations() {
        boolean[] inAResult = new boolean[s.length];
        printOptimalParenthesizations(s, 0, s.length - 1, inAResult);
    }

    void printOptimalParenthesizations(int[][]s, int i, int j,  /* for pretty printing: */ boolean[] inAResult) {
        if (i != j) {
            printOptimalParenthesizations(s, i, s[i][j], inAResult);
            printOptimalParenthesizations(s, s[i][j] + 1, j, inAResult);
            String istr = inAResult[i] ? "_result " : " ";
            String jstr = inAResult[j] ? "_result " : " ";
            System.out.println(" A_" + i + istr + "* A_" + j + jstr);
            inAResult[i] = true;
            inAResult[j] = true;
        }
    }

}
