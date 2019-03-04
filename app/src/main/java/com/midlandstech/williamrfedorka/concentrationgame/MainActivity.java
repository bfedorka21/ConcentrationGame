package com.midlandstech.williamrfedorka.concentrationgame;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int rows = 5;
    public static final int columns = 4;

    private TextView playerOneLbl, playerTwoLbl, playerOneScore, playerTwoScore;
    private Button[] buttons;
    private Button play, reset;
    private ConcentrationGame game;
    private List<String> stringList;
    private int buttonOne, buttonTwo, currentButton, current;
    private boolean buttonOnePressed, buttonTwoPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int w = point.x / columns;

        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setRowCount(rows);
        gridLayout.setColumnCount(columns);

        ClickHandler ch = new ClickHandler();
        buttons = new Button[20];
        for (int i = 0; i < 20; i++) {
                buttons[i] = new Button(this);
                buttons[i].setOnClickListener(ch);
                buttons[i].setEnabled(false);
                gridLayout.addView(buttons[i], w, w);
        }

        play = new Button(this);
        play.setText("Play");
        play.setOnClickListener(ch);
        gridLayout.addView(play, w, w);

        reset = new Button(this);
        reset.setText("Reset");
        reset.setOnClickListener(ch);
        gridLayout.addView(reset, w, w);

        playerOneLbl = new TextView(this);
        playerOneLbl.setText("Player One:");
        gridLayout.addView(playerOneLbl, w, w);

        playerOneScore = new TextView(this);
        playerOneScore.setText("0");
        gridLayout.addView(playerOneScore, w, w);

        playerTwoLbl = new TextView(this);
        playerTwoLbl.setText("Player Two:");
        gridLayout.addView(playerTwoLbl, w, w);

        playerTwoScore = new TextView(this);
        playerTwoScore.setText("0");
        gridLayout.addView(playerTwoScore, w, w);

        setContentView(gridLayout);
    }

    public void play() {
        game = new ConcentrationGame();
        stringList = game.getStringList();
        for (int i = 0; i < 20; i++) {
            buttons[i].setEnabled(true);
            buttons[i].setText("");
        }
        reset();
        Log.w("MainActivity", "player 1 turn");
    }

    public void reset() {
        for (int i = 0; i < 20; i++) {
            buttons[i].setText("");
        }
        current = 0;
        game.reset();
        resetButtonStatus();
    }

    public void resetButtonStatus() {
        buttonOnePressed = false;
        buttonTwoPressed = false;
    }

    public void tilePressed() {
        Log.w("MainActivity", "button pressed");

        if (buttonOnePressed && !buttonTwoPressed) {
            buttonTwo = currentButton;
            buttons[buttonTwo].setText(stringList.get(buttonTwo));
            Log.w("MainActivity", "second button");
            checkForMatch();
            buttonTwoPressed = true;
        }
        else if (!buttonOnePressed && !buttonTwoPressed) {
            buttonOne = currentButton;
            buttons[buttonOne].setText(stringList.get(buttonOne));
            Log.w("MainActivity", "first button");
            buttonOnePressed = true;
        }
        else {
            resetButtonStatus();
        }

    }

    public void checkForMatch() {
        if(stringList.get(buttonOne).equals(stringList.get(buttonTwo))) {
            Log.w("MainActivity", "They match!");
            giveScore();
            buttons[buttonOne].setEnabled(false);
            buttons[buttonTwo].setEnabled(false);
        }
        else if(!stringList.get(buttonOne).equals(stringList.get(buttonTwo))) {
            Log.w("MainActivity", "They don't match");
            if(game.getPlayersTurn() == 1) {
                game.setPlayersTurn(2);
                Log.w("MainActivity", "player 2 turn");
            }
            else if(game.getPlayersTurn() == 2) {
                game.setPlayersTurn(1);
                Log.w("MainActivity", "player 1 turn");
            }
            buttons[buttonOne].setText("");
            buttons[buttonTwo].setText("");
        }
        resetButtonStatus();
    }

    public void giveScore() {
        if(game.getPlayersTurn() == 1) {
            game.setPlayerOneScore(game.getPlayerOneScore() + 1);
            playerOneScore.setText(Integer.toString(game.getPlayerOneScore()));
            game.setPlayersTurn(2);
            Log.w("MainActivity", "player 2 turn");
        }
        else if(game.getPlayersTurn() == 2) {
            game.setPlayerTwoScore(game.getPlayerTwoScore() + 1);
            playerTwoScore.setText(Integer.toString(game.getPlayerTwoScore()));
            game.setPlayersTurn(1);
            Log.w("MainActivity", "player 1 turn");
        }
    }

    private class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if(view == play) {
                play();
            }

            if(view == reset) {
                reset();
            }

            for (int b = 0; b < 20; b++) {
                if (view == buttons[b]) {
                    currentButton = b;
                    tilePressed();
                }
            }
        }
    }
}

