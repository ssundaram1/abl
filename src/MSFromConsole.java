import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MSFromConsole {

    /**
     * in a multi threaded env we can make all of the below methods stateless singleton services and have another service pass in the state into these services
     */
    private static char[][] board = new char[3][3];
    private static char[][] displayBoard = new char[3][3];
    private static int revealedEmpty = 0;
    private static int totalEmpty =0;
    private static boolean isGameOn = true;
    private static boolean isGameInit = false;
    private static int M,N,numBombs;
    private static Random rand;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        rand = new Random();
        while (true) {
            if(!isGameInit){
                initGameParams(scanner);
                display(M, N, board);
            }else{
                int[] move = getMovePos(scanner);
                int row = move[0], col = move[1];
                move(board, row,col , displayBoard, M, N);
                display(M, N, displayBoard);
                if(!isGameOn){
                    scanner.close();
                    break;
                }
            }

        }
    }

    private static void handlePlayerLoss(char[][] board, char[][] displayBoard, int M , int N){
        System.out.println(" You Lose!");
        for(int i=0; i< M; i++){
            for(int j=0; j< N ; j++){
                //reveal bombs
                if(board[i][j] == '*'){
                    displayBoard[i][j] = '*';
                }
            }
        }
        isGameOn = false;
    }

    private static void handlePlayerWins(char[][] board, char[][] displayBoard, int M , int N){
        System.out.println(" You Win!");
        isGameOn = false;
    }

    public static void move(char[][] board, int row, int col, char[][] displayBoard, int M, int N){
        if (board[row][col] == '*') {
            handlePlayerLoss(board, displayBoard, M, N);
            return;
        }
        moveHelper(board, row , col , displayBoard, M, N);
    }

    public static void display(int M, int N, char[][] grid) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println("");

        }
    }


    public static void moveHelper(char[][] board, int row, int col, char[][] displayBoard, int M, int N) {
        if(row<0 || row >=M || col <0 || col >=N || displayBoard[row][col] != 'H' || board[row][col] != 'E') {
            return;
        }
        revealedEmpty++;
        int bombCount = 0;
        for (int i = -1; i <=1; i++) {
            for (int j = -1; j <=1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int adjRow = row + i;
                int adjCol = col + j;
                if (adjRow >= 0 && adjRow < M && adjCol >= 0 && adjCol < N && board[adjRow][adjCol] == '*') {
                    bombCount++;
                }
            }
        }

        if(revealedEmpty == totalEmpty){
            displayBoard[row][col] = bombCount == 0 ?'.': (char) (bombCount + '0');
            handlePlayerWins(board, displayBoard, M,N);
            return;
        }

        if (bombCount == 0) {
            displayBoard[row][col] = '.';
            for (int i = -1; i <=1; i++) {
                for (int j = -1; j <=1; j++) {
                    if(i==0 && j==0){
                        continue;
                    }
                    moveHelper(board, row +i, col +j, displayBoard, M, N);
                }
            }

        } else {
            displayBoard[row][col] = (char)(bombCount + '0');
        }
    }


    private static int[] getMovePos(Scanner scanner) {
        int[] move = new int[2];
        System.out.println(" enter row");
        move[0] = scanner.nextInt();
        System.out.println(" enter col");
        move[1] = scanner.nextInt();
        System.out.println(" row "+ move[0] + " col "+ move[1]);
        return move;
    }

    private static void initGameParams(Scanner scanner) {
        System.out.println("usage : <rows>,<cols>,<bombs>");
        String[] input = scanner.nextLine().split(",");
        M = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);
        numBombs = Integer.parseInt(input[2]);
        System.out.println(" M " + M + " N " + N + " numBombs " + numBombs);
        isGameInit = true;
        board = new char[M][N];
        displayBoard= new char[M][N];
        for(int i=0; i<M; i++){
            Arrays.fill(board[i], 'E');
        }
        for (int i = 0; i < M; i++) {
            Arrays.fill(displayBoard[i], 'H');
        }
        //bomb positions
        for(int i=0; i<numBombs; i++){
            bombRandomPos();
        }
        totalEmpty = (M * N) - numBombs;
    }

    private static int[] bombRandomPos() {
        int row = rand.nextInt(M);
        int col = rand.nextInt(N);
        while(board[row][col] == '*'){
            row = rand.nextInt(M);
            col = rand.nextInt(N);
        }
        board[row][col] = '*';
        return new int[]{row, col};
    }

}
