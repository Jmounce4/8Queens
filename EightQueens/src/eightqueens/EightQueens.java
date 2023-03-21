/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightqueens;

/**
 *
 * @author Julian
 */
public class EightQueens {

    public int[][] board = new int[8][8];
    public static int[][] testBoard = new int[8][8];
    public int[] rowR = new int[8];
    public int column[];
    public int h = 0;
    public static int restarts;
    public static int moves = 0;
    public static int random;
    public int queen[][];
    public static int neighbors = 0;

    //Creates Eightqueens obbject 
    public EightQueens() {
        this.randomBoard();

        this.restarts = 0;
    }

    //Creates board and randomizes it
    public void randomBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = 0;

            }
        }
        for (int k = 0; k < 8; k++) {
            random = (int) (8 * Math.random());
            this.board[random][k] = 1;
            this.rowR[k] = (int) random;
        }
        h = this.getH();
    }

    //Check and count row conflicts
    public int checkRowConflict() {
        int columnH = 0;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (this.board[row][column] == 1) {
                    for (int newRow = column + 1; newRow < 8; newRow++) {
                        if (board[row][column] == board[row][newRow]) {
                            columnH++;
                            break;

                        }
                    }
                }
            }
        }
        return columnH;
    }

    //Check and count diagonal conflict
    public int checkDiagonalConflict() {
        int diagH = 0;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (this.board[row][column] == 1) {
                    for (int i = 1; i < 8; i++) {
                        if (row + i < 8 && column + i < 8) {
                            if (this.board[row][column] == this.board[row + i][column + i]) {
                                diagH++;
                                break;
                            }
                        }
                        /* if (row- i >= 0 && column + i < 8) {
                            if (this.board[col][column] == this.board[row- i][column + i]) {
                                this.h++;
                                System.out.println("conflict");
                                break;
                            }

                        }
                        if (row- i >= 0 && column - i >= 0) {
                            if (this.board[col][column] == this.board[row- i][column - i]) {
                                this.h++;
                                System.out.println("conflict");
                                break;
                            }

                        }*/

                        if (row + i < 8 && column - i >= 0) {
                            if (this.board[row][column] == this.board[row + i][column - i]) {
                                diagH++;
                                break;
                            }
                        }

                    }
                }
            }
        }
        return diagH++;

    }


    //Queens move, tracks changes
    public static int[][] changeBoard(EightQueens e) {

        int currentH = e.getH();
        int tempH = e.getH();
        int newspot = 0;
        int initialspot = 0;
        EightQueens t = new EightQueens();
        neighbors = 0;
        t = e;
        int moves2 = moves;
        for (int row = 0; row < 8; row++) {
            //newspot[row] = t.board[row][rowR[row]]; 

            newspot = t.rowR[row];
            initialspot = t.rowR[row];
            for (int column = 0; column < 8; column++) {

                t.board[0][row] = 0;
                t.board[1][row] = 0;
                t.board[2][row] = 0;
                t.board[3][row] = 0;
                t.board[4][row] = 0;
                t.board[5][row] = 0;
                t.board[6][row] = 0;
                t.board[7][row] = 0;

                t.board[column][row] = 1;
                //Determines if the heuristic is lower, if so, sets to newspot variable
                if (t.getH() < tempH) {
                    newspot = column;
                    tempH = t.getH();
                    t.board[column][row] = 0;
                    neighbors++;
                
                } else if (t.getH() >= tempH) {
                    t.board[column][row] = 0;
                }

            }
            //Moves queen to best spot
            t.board[newspot][row] = 1;
            //If move is different from initial spot, move++
            if (newspot != initialspot) {
                moves2++;
                countMoves(moves2);
            }
        }
        //Applies the copied board to the actual board if the heuristic is lower
        if (t.getH() < e.getH()) {
            e.board = t.board;
        }

        return e.board;
    }

    //Runs the program and display information
    public static void runQueens(EightQueens e) {
        //Displays board
        System.out.println("Current h: " + e.getH());
        System.out.println("Current State");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(e.board[i][j] + " ");
            }

            System.out.println();

        }
        //Solution is found, heuristic = 0;
        if (e.getH() == 0) {
            System.out.println("Solution Found!");
            System.out.println("State changes: " + e.moves);
            System.out.println("Restarts: " + e.restarts);
        }

    }

    public int getH() {
        this.h = this.checkDiagonalConflict() + this.checkRowConflict();
        return this.h;

    }

    public int[][] getBoard() {
        return board;
    }

    public static void countMoves(int m) {
        moves = m;
    }

    public int[] getrowR() {
        return rowR;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EightQueens test = new EightQueens();
        int i = 1;
        //runQueens(test);
        int oldh = test.getH();
        int newh = test.getH();
        int restarts2 = 0;

        while (i == 1) {
            runQueens(test);
            test.board = changeBoard(test);
            newh = test.getH();
            test.restarts = restarts2;
            System.out.println("Neighbors found with lower h: " + neighbors);

            if (newh >= oldh) {

                System.out.println("RESTART\n");
                restarts2++;
                test.randomBoard();
                oldh = test.getH();

            }

            if (newh < oldh) {

                System.out.println("Setting new current state\n");
                oldh = newh;
            }

            if (newh == 0) {
                runQueens(test);
                i++;
            }
            oldh = newh;

        }
    }

}
