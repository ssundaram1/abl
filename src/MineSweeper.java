import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MineSweeper {

    /**
     * in a multi threaded env we can make all of the below methods stateless singleton services and have another service pass in the state into these services
     */
    private static char[][] board = new char[3][3];
    private static char[][] displayBoard = new char[3][3];
    private static int revealedEmpty = 0;
    private static int totalEmpty =0;
    //hardcoded input
    private static int numBombs =1;
    private static boolean isGameOn = true;
    private static boolean isGameInit = false;



    public static void main(String[] args) {
        int M = board.length;
        int N = board[0].length;

        for (int i = 0; i < M; i++) {
            Arrays.fill(board[i], ' ');
        }
        //hardcoded bomb pos
        board[0][0] = '*';

        for (int i = 0; i < M; i++) {
            Arrays.fill(displayBoard[i], 'H');
        }
        //Parametrize M, N and numBombs from console input
        //console input will run in a while true loop and break as soon as game ends - basically when isGameOn boolean is unset
        totalEmpty = (M * N) - numBombs;
        //row and col is passed in here we can read that from user input in the console - please change to see different outputs being printed
        move(board, 1,1 , displayBoard, M, N);
        display(M, N, displayBoard);
    }

    public static void display(int M, int N, char[][] grid) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println("");

        }
    }

    public static void move(char[][] board, int row, int col, char[][] displayBoard, int M, int N){
        if (board[row][col] == '*') {
            handlePlayerLoss(board, displayBoard, M, N);
            return;
        }
        moveHelper(board, row , col , displayBoard, M, N);
    }


    public static void moveHelper(char[][] board, int row, int col, char[][] displayBoard, int M, int N) {
        if(row<0 || row >=M || col <0 || col >=N || displayBoard[row][col] != 'H' || board[row][col] != ' ') {
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

        if (bombCount == 0) {
            displayBoard[row][col] = '.';
            if(revealedEmpty == totalEmpty){
                handlePlayerWins(board, displayBoard, M,N);
                return;
            }
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

}
