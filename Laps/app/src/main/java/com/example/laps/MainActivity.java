package com.example.laps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int playerOnePosition, playerTwoPosition, step, playerOneLaps = 0, playerTwoLaps = 0;
    int playerOnePrev, playerTwoPrev;
    boolean playerOneTurn = true;
    TextView[] textViews = new TextView[15];
    Button playerOneDice, playerTwoDice;
    TextView p1, p2, laps1, laps2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        laps1=findViewById(R.id.laps1);
        laps2=findViewById(R.id.laps2);
        laps1.setText("0");
        laps2.setText("0");
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        playerOnePosition = 15;
        playerTwoPosition = 0;
        for (int t = 1; t < 15; t++) {
            int resId = getResources().getIdentifier("textView" + t, "id", getPackageName());
            textViews[t] = findViewById(resId);
        }
        playerOneDice = findViewById(R.id.playerOneDice);
        playerTwoDice = findViewById(R.id.playerTwoDice);
        playerTwoDice.setVisibility(View.INVISIBLE);


        playerOneDice.setOnTouchListener(new View.OnTouchListener() {
            Handler mHandler = new Handler();
            int i;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAction.run();
                        i = 1;
                        p1.setText(String.valueOf(i));
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAction);
                        step = Integer.parseInt(p1.getText().toString());
                        calcPosition();
                        checkForWin();
                        if(playerOnePosition!=15)
                            makemove();
                        else
                            textViews[playerOnePrev].setText("");
                        laps1.setText(String.valueOf(playerOneLaps));
                        playerOneTurn = false;
                        playerOneDice.setVisibility(View.INVISIBLE);
                        playerTwoDice.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    p1.setText(String.valueOf(i));
                    i = (i % 6) + 1;
                    mHandler.postDelayed(this, 50);
                }
            };
        });


        playerTwoDice.setOnTouchListener(new View.OnTouchListener() {
            Handler mHandler2 = new Handler();
            int j;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        j = 1;
                        p2.setText(String.valueOf(j));
                        mAction2.run();
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler2.removeCallbacks(mAction2);
                        step = Integer.parseInt(p2.getText().toString());
                        calcPosition();
                        checkForWin();
                        if(playerTwoPosition!=0)
                            makemove();
                        else
                            textViews[playerTwoPrev].setText("");
                        laps2.setText(String.valueOf(playerTwoLaps));
                        playerOneTurn = true;
                        playerOneDice.setVisibility(View.VISIBLE);
                        playerTwoDice.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }

            Runnable mAction2 = new Runnable() {
                @Override
                public void run() {
                    p2.setText(String.valueOf(j));
                    j = (j % 6) + 1;
                    mHandler2.postDelayed(this, 50);
                }
            };
        });


    }

    void calcPosition() {
        playerOnePrev = playerOnePosition;
        playerTwoPrev = playerTwoPosition;


        if (playerOneTurn) {
            if ((playerOnePosition - step) < 0) {
                Toast.makeText(getApplicationContext(), "IGNORED!!", Toast.LENGTH_SHORT).show();
                step = 0;
            }
        }
        else {
            if ((playerTwoPosition + step) > 15) {
                Toast.makeText(getApplicationContext(), "IGNORED!!", Toast.LENGTH_SHORT).show();
                step = 0;
            }
        }


        if (playerOneTurn)
            playerOnePosition = playerOnePosition - step;
        else
            playerTwoPosition = playerTwoPosition + step;


        if (playerOnePosition == 0 && playerOneTurn) {
            Toast.makeText(getApplicationContext(), "LAP COMPLETED", Toast.LENGTH_SHORT).show();
            playerOneLaps++;
            playerOnePosition = 15;
        } else if (playerTwoPosition == 15 && !playerOneTurn) {
            Toast.makeText(getApplicationContext(), "LAP COMPLETED", Toast.LENGTH_SHORT).show();
            playerTwoLaps++;
            playerTwoPosition = 0;
        }

    }

    void makemove() {
        if (playerOnePosition != playerTwoPosition) {
            if (playerOneTurn) {
                while (playerOnePrev != playerOnePosition) {
                    if (playerOnePrev != 15 && !((textViews[playerOnePrev].getText().toString()).equals("O")))
                        textViews[playerOnePrev].setText("");
                    playerOnePrev--;
                    if (!((textViews[playerOnePrev].getText().toString()).equals("O")))
                        textViews[playerOnePrev].setText("X");
                }
            } else {
                while (playerTwoPrev != playerTwoPosition) {
                    if (playerTwoPrev != 0 && !((textViews[playerTwoPrev].getText().toString()).equals("X")))
                        textViews[playerTwoPrev].setText("");
                    playerTwoPrev++;
                    if (!((textViews[playerTwoPrev].getText().toString()).equals("X")))
                        textViews[playerTwoPrev].setText("O");
                }
            }
        }
        else{
            if(playerOneTurn){
                if(playerOnePrev!=15)
                    textViews[playerOnePrev].setText("");
                textViews[playerOnePosition].setText("X");
                playerTwoPosition=0;
            }
            else{
                if (playerTwoPrev!=0)
                    textViews[playerTwoPrev].setText("");
                textViews[playerTwoPosition].setText("O");
                playerOnePosition=15;
            }

        }
    }

    void checkForWin(){
        Intent i;
        if(playerOneLaps==3) {
            i=new Intent(getApplicationContext(),SecondActivity.class);
            startActivity(i);
            finish();
        } else {
            if (playerTwoLaps==3) {
                i=new Intent(getApplicationContext(),ThirdActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
