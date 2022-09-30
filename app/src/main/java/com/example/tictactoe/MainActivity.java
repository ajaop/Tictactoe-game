package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private  int[][] board = new int[3][3];
    private TextView tplayer1;
    private TextView tplayer2;
    private int roundCount;
    private int player1points;
    private int player2points;
    public int aiPlayer = 2;
    public int huPlayer = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tplayer1 = findViewById(R.id.textpl1);
        tplayer2 = findViewById(R.id.textpl2);

        // THE BUTTONS IN THE GRID LAYOUT ARE BEING MERGED WITH THE VARIABLE BUTTON
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                // THE BUTTON IDS ARE FOUND AND USED TO PUT EACH BUTTON IN THE resID VARIABLE
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //THE BUTTONS IN THE LAYOUT ARE MERGED TO THE VARIABLE buttons
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setEnabled(true);
                buttons[i][j].setOnClickListener(new buttonClickListener(i,j));
            }
        }

        //WHEN THE RESET BUTTON IS CLICKED IT RESETS THE GAME USING THE FUNCTION resetGame()
        Button buttonReset = findViewById(R.id.buttonRes);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });


    }

    // WHEN THE BUTTONS IN THE GRID ARE CLICKED
    private class buttonClickListener implements View.OnClickListener {
      int x;
      int y;

      public buttonClickListener(int i, int j){
          this.x = i;
          this.y = j;
      }



        @Override
        public void onClick(View v) {
          //IF THE CONDITIONS IN GAME OVER ARE NOT MET

            if (!isGameOver()) {
                if (!((Button) v).getText().toString().equals("")) {
                    return;
                }

                Point point = new Point(x, y);

                //HELPS THE USER MAKE A MOVE ON THE BOARD
                setMove(point, 1);

                // CALCULATES WHERE THE COMPUTER SHOULD PLAY
                minimax(0, aiPlayer);

                //MAKES THE MOVE FOR THE COMPUTER
                setMove(computerMove, 2);

                //IF STATEMENTS FOR IF ANY OF THEM WIN OR ITS A DRAW
                if (hasXWon()) {

                    player1wins();

                } else if (hasOWon()) {

                    player2wins();
                } else if (getAvailableStates().isEmpty()) {
                    draw();
                }
            } else {
                resetBoard();
            }
        }
    }

    /*USING THE PLACEAMOVE METHOD TO PLACE A MOVE */
    public void setMove( Point point, int turn) {
        placeAMove(point, turn);
        //buttons[point.x][point.y].setEnabled(false);
        //WHEN ITS HUMANS TURN PLACE X WHEN ITS COMPUTERS TURN PLACE O
        if (turn == huPlayer) {

            buttons[point.x][point.y].setText("X");

        } else if (turn == aiPlayer) {

            buttons[point.x][point.y].setText("O");

        }

    }

    /*
    IF X OR O HAS WON
    ITS A DRAW THEN THE GAME IS OVER
     */
    public boolean isGameOver() {

        return (hasXWon()  || hasOWon() || getAvailableStates().isEmpty());
    }


    //THIS METHOD IS USED TO CHECK IF THEIR ARE STILL PLACES ON THE BOARD TO PLAY
    public List<Point> getAvailableStates() {

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

    //THIS METHOD CHECKS IF THE PLAYER O HAS WON
    public boolean hasOWon() {
        for (int i = 0; i < 3; ++i) {
            if (board[0][i] == (board[1][i])
                    && board[0][i] == (board[2][i])
                    && board[0][i] == 2) {
                return true;
            }
        }

        for (int i = 0; i < 3; ++i) {

            if (board[i][0] == (board[i][1])
                    && board[i][0] == (board[i][2])
                    && board[i][0] == (2)) {
                return true;
            }
        }

        if (board[0][0] == (board[1][1])
                && board[0][0] == (board[2][2])
                && board[0][0] == (2)) {
            return true;
        }

        if (board[0][2] == (board[1][1])
                && board[0][2] == (board[2][0])
                && board[0][2] == (2)) {
            return true;


        }
        return false;
    }


    //CHECKS IF THE PLAYER X HAS WON
    public boolean hasXWon() {
        for (int i = 0; i < 3; ++i) {
            if (board[0][i] == (board[1][i])
                    && board[0][i] == (board[2][i])
                    && board[0][i] == (1)) {
                return true;
            }
        }

        for (int i = 0; i < 3; ++i) {
            if (board[i][0] == (board[i][1])
                    && board[i][0] == (board[i][2])
                    && board[i][0] == (1)) {
                return true;
            }
        }

        if (board[0][0] == (board[1][1])
                && board[0][0] == (board[2][2])
                && board[0][0] == (1)) {
            return true;
        }

        if (board[0][2] == (board[1][1])
                && board[0][2] == (board[2][0])
                && board[0][2] == (1)) {
            return true;
        }

        return false;
    }

    // THE COMPUTERS MOVE ARE STORED HERE
    Point computerMove;

    // THIS ALGORITHM IS USED TO HELP THE COMPUTER PLAY
    public int minimax(int depth, int turn) {

        if (hasOWon()) return +1;
        if (hasXWon()) return -1;
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
                    board[point.x][point.y] = 0;
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
                    board[point.x][point.y] = 0;
                    break;
                }
            }
            board[point.x][point.y] = 0;
        }

        return turn == 2 ? max : min;

    }

    // used to place move in a given point on the board

    public void placeAMove(Point point, int turn) {

        board[point.x][point.y] = turn;


    }


    //THIS METHOD DISPLAYS THE POP UP IF PLAYER 1 WINS
    public void player1Toast(){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.customToast);
        text.setText("Human wins !!");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    //THIS METHOD DISPLAYS THE POP UP IF PLAYER 2 WINS
    public void player2Toast(){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.customToast);
        text.setText("Computer wins !!");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    //THIS METHOD DISPLAYS THE POP IP IF ITS A DRAW
    public void drawToast(){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.customToast);
        text.setText("Draw !!");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /* THIS METHOD DOES THE THINGS WRITTEN BELOW
    IF PLAYER ONE WINS,
    IT ADDS TO THE POINTS OF PLAYER ONE,
    SHOWS THE POP UP THROUGH player1toast()
    CLEAR ALL THE TEXT ON THE BOARD THROUGH resetBoard()
    UPDATES THE ADDED POINTS TO THE POINTS TEXT THROUGH updatePointsText()
     */
    private void player1wins(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player1points++;
                player1Toast();
                resetBoard();
                updatePointsText();
            }
        }, 2000);

    }

    /* THIS METHOD DOES THE THINGS WRITTEN BELOW
   IF PLAYER TWO WINS,
   IT ADDS TO THE POINTS OF PLAYER TWO,
   SHOWS THE POP UP THROUGH player2toast()
   CLEAR ALL THE TEXT ON THE BOARD THROUGH resetBoard()
   UPDATES THE ADDED POINTS TO THE POINTS TEXT THROUGH updatePointsText()
    */
    private void player2wins(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player2points++;
                player2Toast();
                resetBoard();
                updatePointsText();
            }
        }, 2000);
    }

    /* THIS METHOD DOES THE THINGS WRITTEN BELOW
    IF ITS A DRAW,
    SHOWS THE POP UP THROUGH drawToast()
     CLEAR ALL THE TEXT ON THE BOARD THROUGH resetBoard()
     */
    private void draw(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawToast();
                resetBoard();
            }
        }, 2000);
    }

    /*
    THIS METHOD UPDATE THE POINTS OF EACH PLAYER
     */
    private void updatePointsText(){
        tplayer2.setText("Human : " + player1points);
        tplayer1.setText("Computer : " + player2points);
    }

    /*
    THIS METHOD CLEARS ALL THE TEXTS ON THE BOARD
     */
    private void resetBoard(){

        for (int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                buttons[i][j].setText("");
                board[i][j] = 0;
                    }
                }
            }

    /*
    THIS METHOD STARTS THE WHOLE GAME BY IMPLEMENTING THE METHODS,
    updatePointsText(),
    resetBoard()
     */
    public void resetGame(){

        player1points=0;
        player2points=0;
        updatePointsText();
        resetBoard();
    }

    /*
    THE TWO METHODS BELOW SAVE THE VALUES OF THE VARIABLES INCASE THE SCREEN CHANGES FOR ANY REASON
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1points", player1points);
        outState.putInt("player2points", player2points);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){

        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1points");
        player2points = savedInstanceState.getInt("player2points");
    }


}





