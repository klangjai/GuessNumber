package com.example.klangjai.guessnumber;

import android.util.Log;

import java.util.Random;

/**
 * Created by Klangjai on 30-Aug-15.
 */
public class Game {
    private static final String TAG = "Game";
    protected enum CompareResult{
        TOO_SMALL,EQUAL,TOO_BIG
    }

    private int mAnswer;
    private int mTotalGuess;
    private Random random = new Random();

    public Game(int level) {
        mTotalGuess = 0;
        if(level == 0)
            mAnswer = random.nextInt(10);
        else
            mAnswer = random.nextInt(100);
        Log.d(TAG,"The answer is "+mAnswer);
    }

    public CompareResult submitGuess(int guessNumber){
        ++mTotalGuess;
        if(guessNumber < mAnswer)
            return CompareResult.TOO_SMALL;
        else if(guessNumber > mAnswer)
            return CompareResult.TOO_BIG;
        else
            return CompareResult.EQUAL;
    }

    public int getTotalGuess(){
        return mTotalGuess;
    }

}
