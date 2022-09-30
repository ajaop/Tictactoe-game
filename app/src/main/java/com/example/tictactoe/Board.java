package com.example.tictactoe;
/*
import java.util.ArrayList;
import java.util.List;


public class Board {
    //int[][] board = new int[3][3];
    public int aiPlayer = 2;
    public int huPlayer = 1;
    //public Point point;
    MainActivity mainActivity;

public Board(){

}


    public List<Point> getAvailableStates() {
        //String[][] field = new String[3][3];

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (mainActivity.board[i][j] == 0) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }



    Point computerMove;

    public int minimax(int depth, int turn) {


        if (mainActivity.hasOWon()) return +1;
        if (mainActivity.hasXWon()) return -1;
        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0;

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);
            if (turn == 2) {
                placeAMove(point, 2);
                // setMove(point,aiPlayer);
                int currentScore = minimax(depth + 1, 1);
                max = Math.max(currentScore, max);

                if (currentScore >= 0) {
                    if (depth == 0) computerMove = point;
                }

                if (currentScore == 1) {
                    mainActivity.board[point.x][point.y] = 0;
                    break;
                }

                if (i == pointsAvailable.size() - 1 && max < 0) {
                    if (depth == 0) computerMove = point;
                }
            } else if (turn == 1) {
                placeAMove(point, 1);
                //setMove(point,huPlayer);
                int currentScore = minimax(depth + 1, 2);
                min = Math.min(currentScore, min);

                if (min == -1) {
                    mainActivity.board[point.x][point.y] = 0;
                    break;
                }
            }
            mainActivity.board[point.x][point.y] = 0;
        }

        return turn == 2 ? max : min;
    }


    // used to place move in a given point on the board

    public void placeAMove(Point point, int turn) {

        mainActivity.board[point.x][point.y] = turn;
    }


}

 */