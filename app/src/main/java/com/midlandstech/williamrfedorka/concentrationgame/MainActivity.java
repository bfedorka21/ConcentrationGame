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
    private boolean buttonsPressed, secondButtonPressed;


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
        buttonsPressed = false;
        secondButtonPressed = false;
    }

    public void checkForMatch() {
        if(stringList.get(buttonOne) == stringList.get(buttonTwo)) {
            Log.w("MainActivity", "They match!");
            if(game.getPlayersTurn() == 1) {
                game.setPlayerOneScore(game.getPlayerOneScore() + 1);
                playerOneScore.setText(Integer.toString(game.getPlayerOneScore()));
            }
            buttons[buttonOne].setEnabled(false);
            buttons[buttonTwo].setEnabled(false);
            resetButtonStatus();
        }
        else {
            buttons[buttonOne].setText("");
            buttons[buttonTwo].setText("");
            Log.w("MainActivity", "They don't match");
            resetButtonStatus();
        }
    }

    public void tilePressed() {

            Log.w("MainActivity", "button pressed");

            if (buttonsPressed) {
                buttonTwo = currentButton;
                buttons[currentButton].setText(stringList.get(currentButton));
                Log.w("MainActivity", "second button");
                checkForMatch();
                secondButtonPressed = true;
            }

            if (!buttonsPressed && !secondButtonPressed) {
                buttonOne = currentButton;
                buttons[currentButton].setText(stringList.get(currentButton));
                Log.w("MainActivity", "first button");
            }

            else
                Log.w("MainActivity", "i dont know what to do");

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

