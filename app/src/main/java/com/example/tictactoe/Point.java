package com.example.tictactoe;


public class Point {

   int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;

    }
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}

class PointAndScore {

    int score;
    Point point;

    PointAndScore(int score, Point point) {
        this.score = score;
        this.point = point;
    }


}

