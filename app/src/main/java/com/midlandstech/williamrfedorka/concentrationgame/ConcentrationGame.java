package com.midlandstech.williamrfedorka.concentrationgame;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConcentrationGame {

    private List<String> stringList = Arrays.asList("Pie", "Pie", "Cake", "Cake", "Pizza", "Pizza", "Cow", "Cow", "Chicken", "Chicken", "Ham", "Ham", "Cheese", "Cheese", "Steak", "Steak",
            "Fish", "Fish", "Ice Cream", "Ice Cream");

    private int playersTurn;
    private int playerOneScore;
    private int playerTwoScore;

    public ConcentrationGame() {
        shuffle();
        reset();
    }

    public void reset() {
        playersTurn = 1;
        playerOneScore = 0;
        playerTwoScore = 0;
    }

    public void shuffle() {
        Collections.shuffle(stringList);

    }

    public int getPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(int t) {
        playersTurn = t;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int s) {
        playerOneScore = s;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int s) {
        playerTwoScore = s;
    }
}
