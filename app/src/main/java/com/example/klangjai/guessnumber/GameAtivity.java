package com.example.klangjai.guessnumber;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameAtivity extends AppCompatActivity {
    private TextView mGuessNumberTextView;
    private TextView mResulttTextView;
    private EditText mInput;
    private Button mGuessButton;;

    private Game mGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int level = intent.getIntExtra("level",0);
        mGuessNumberTextView = (TextView) findViewById(R.id.guess_number_text_view);
        mResulttTextView = (TextView) findViewById(R.id.result_text_view);
        mInput = (EditText) findViewById(R.id.input);
        mGuessButton = (Button) findViewById(R.id.guess_button);

        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mInput.getText().length() == 0) {
                    Toast.makeText(
                            GameAtivity.this,
                            "กรุณาพิมพ์ตัวเลข",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                int guessNumber = Integer.valueOf(mInput.getText().toString());
                mGuessNumberTextView.setText(String.valueOf(guessNumber));
                mInput.setText(null);
                checkAnswer(guessNumber);
            }
        });
        newGame(level);
    }

    private void newGame(int level){
        mGame = new Game(level);
        if(level == 0){
            mGuessNumberTextView.setText("X");
            mInput.setHint("0-9");
        }else{
            mGuessNumberTextView.setText("XX");
            mInput.setHint("0-99");
        }
        mResulttTextView.setText(null);
        mGuessNumberTextView.setBackgroundResource(R.color.incorrect_guess);

    }

    private void checkAnswer(int guessNumber) {
        Game.CompareResult result = mGame.submitGuess(guessNumber);
        if(result == Game.CompareResult.EQUAL){
            MediaPlayer mp = MediaPlayer.create(this,R.raw.applause);
            mp.start();
            mResulttTextView.setText("ถูกต้องนะครับ");
            mGuessNumberTextView.setBackgroundResource(R.color.correct_guess);
            String msg = String.format(
                    "จำนวนครั้งที่ทาย: %d",
                    mGame.getTotalGuess()
            );

            new AlertDialog.Builder(this)
                    .setTitle("สรุปผล")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("เริ่มเกมใหม่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new AlertDialog.Builder(GameAtivity.this)
                                    .setTitle("เลือกระดับความยาก")
                                    .setItems(
                                            new String[]{"ง่าย", "ยาก"},
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    newGame(which);
                                                }
                                            }
                                    )
                                    .show();
                        }
                    })
                    .setNegativeButton("กลับหน้าหลัก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }else if(result == Game.CompareResult.TOO_BIG){
            mResulttTextView.setText("มากไป");
        }else{
            mResulttTextView.setText("น้อยไป");
        }

    }
}
