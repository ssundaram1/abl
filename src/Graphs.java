/**
 * Created by ssundaram on 5/19/18.
 */
public class Graphs {
    public static int numIslands(int[][] grid) {
        int count = 0;
        int n = grid.length;
        if (n == 0) return 0;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                if (grid[i][j] == 1) {
                    DFSMarking(grid, i, j, n, m);
                    ++count;
                }
        }
        return count;
    }

    private static void DFSMarking(int[][] grid, int i, int j, int n, int m) {
        if (i < 0 || j < 0 || i >= n || j >= m || grid[i][j] != 1)
            return;
        grid[i][j] = 0;
        DFSMarking(grid, i + 1, j, n, m);
        DFSMarking(grid, i - 1, j, n, m);
        DFSMarking(grid, i, j + 1, n, m);
        DFSMarking(grid, i, j - 1, n, m);
    }

    public static boolean wordSearch(char[][] board, String word) {
        return method(board, word);
    }

    public static boolean method(char[][] board, String word) {
        int row = board.length;
        int col = board[0].length;
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if (dfs(board, visited, i, j, 0, word))
                    return true;
        return false;
    }

    public static boolean dfs(char[][] board, boolean[][] visited, int row, int col, int index, String word) {
        if (word.length() == index) {
            return true;
        }
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length) return false;
        char ch = word.charAt(index);
        if (!visited[row][col] && ch == board[row][col]) {
            visited[row][col] = true;
            boolean res = dfs(board, visited, row - 1, col, index + 1, word) || dfs(board, visited, row + 1, col, index + 1, word)
                    || dfs(board, visited, row, col - 1, index + 1, word) || dfs(board, visited, row, col + 1, index + 1, word);
            visited[row][col] = false;
            return res;
        }
        return false;
    }

    public static void main(String[] args) {

        int M[][] = new int[][]{
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1}
        };
        System.out.println("Number of islands is: " + numIslands(M));

        char[][] board = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        System.out.println(" word search: " + wordSearch(board, "ABCCED"));


        // The main function that returns count of islands in a given
        //  boolean 2D matrix
//    int countIslands(int M[][])
//    {
//        // Make a bool array to mark visited cells.
//        // Initially all cells are unvisited
//        boolean visited[][] = new boolean[ROW][COL];
//
//
//        // Initialize count as 0 and travese through the all cells
//        // of given matrix
//        int count = 0;
//        for (int i = 0; i < ROW; ++i)
//            for (int j = 0; j < COL; ++j)
//                if (M[i][j]==1 && !visited[i][j]) // If a cell with
//                {                                 // value 1 is not
//                    // visited yet, then new island found, Visit all
//                    // cells in this island and increment island count
//                    DFS(M, i, j, visited);
//                    ++count;
//                }
//
//        return count;
//    }
    }
}
