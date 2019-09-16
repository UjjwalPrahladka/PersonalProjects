package com.example.laps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int playerOnePosition, playerTwoPosition, step, playerOneLaps = 0, playerTwoLaps = 0;
    int playerOnePrev, playerTwoPrev;
    int totalLaps = 3;
    boolean startRandom = false;
    boolean playerOneTurn = true, playerOneFirstTime = true, playerTwoFirstTime = true;
    TextView[] textViews = new TextView[15];
    Button playerOneDice, playerTwoDice;
    TextView p1, p2, laps1, laps2;
    RadioGroup radioBtn, radioBtn2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        laps1 = findViewById(R.id.laps1);
        laps2 = findViewById(R.id.laps2);
        radioBtn = findViewById(R.id.radioBtn);
        radioBtn2 = findViewById(R.id.radioBtn2);
        radioBtn.setVisibility(View.INVISIBLE);
        radioBtn2.setVisibility(View.INVISIBLE);
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
                        if (startRandom)
                            i = (new Random().nextInt(5)) + 1;
                        else
                            i = 1;
                        p1.setText(String.valueOf(i));
                        mHandler.postDelayed(mAction, 150);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAction);
                        step = Integer.parseInt(p1.getText().toString());
                        if (!playerOneFirstTime) {
                            if (radioBtn.getCheckedRadioButtonId() == R.id.downBtn)
                                step = -step;
                            calcPosition();
                            if (step != 0)
                                makemove();
                            checkForWin();
                            laps1.setText(String.valueOf(playerOneLaps));
                            playerOneTurn = false;
                            playerOneDice.setVisibility(View.INVISIBLE);
                            playerTwoDice.setVisibility(View.VISIBLE);
                        } else {
                            if (step == 6) {
                                Toast.makeText(getApplicationContext(), "Now Make A Move", Toast.LENGTH_SHORT).show();
                                playerOneFirstTime = false;
                            } else {
                                Toast.makeText(getApplicationContext(), "Better Luck Next Time", Toast.LENGTH_SHORT).show();
                                playerOneTurn = false;
                                playerOneDice.setVisibility(View.INVISIBLE);
                                playerTwoDice.setVisibility(View.VISIBLE);
                            }
                        }
                        break;

                }
                return true;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    i = (i % 6) + 1;
                    p1.setText(String.valueOf(i));
                    mHandler.postDelayed(this, 150);
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
                        if (startRandom)
                            j = (new Random().nextInt(5)) + 1;
                        else
                            j = 1;
                        p2.setText(String.valueOf(j));
                        mHandler2.postDelayed(mAction2, 150);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler2.removeCallbacks(mAction2);
                        step = Integer.parseInt(p2.getText().toString());
                        if (!playerTwoFirstTime) {
                            if (radioBtn2.getCheckedRadioButtonId() == R.id.downBtn2)
                                step = -step;
                            calcPosition();
                            if (step != 0)
                                makemove();
                            checkForWin();
                            laps2.setText(String.valueOf(playerTwoLaps));
                            playerOneTurn = true;
                            playerOneDice.setVisibility(View.VISIBLE);
                            playerTwoDice.setVisibility(View.INVISIBLE);
                        } else {
                            if (step == 6) {
                                Toast.makeText(getApplicationContext(), "Now Make A Move", Toast.LENGTH_SHORT).show();
                                playerTwoFirstTime = false;
                            } else {
                                Toast.makeText(getApplicationContext(), "Better Luck Next Time", Toast.LENGTH_SHORT).show();
                                playerOneTurn = true;
                                playerOneDice.setVisibility(View.VISIBLE);
                                playerTwoDice.setVisibility(View.INVISIBLE);
                            }
                        }
                        break;
                }
                return true;
            }

            Runnable mAction2 = new Runnable() {
                @Override
                public void run() {
                    j = (j % 6) + 1;
                    p2.setText(String.valueOf(j));
                    mHandler2.postDelayed(this, 150);
                }
            };
        });


    }

    void calcPosition() {
        playerOnePrev = playerOnePosition;
        playerTwoPrev = playerTwoPosition;


        if (playerOneTurn) {
            if ((playerOnePosition - step) < 0 || (playerOnePosition - step) >= 15) {
                Toast.makeText(getApplicationContext(), "You cannot go " + (radioBtn.getCheckedRadioButtonId() == R.id.upBtn ? "UP" : "DOWN"), Toast.LENGTH_SHORT).show();
                step = -step;
            }
        } else {
            if ((playerTwoPosition + step) > 15 || (playerTwoPosition + step) <= 0) {
                Toast.makeText(getApplicationContext(), "You cannot go " + (radioBtn2.getCheckedRadioButtonId() == R.id.upBtn2 ? "UP" : "DOWN"), Toast.LENGTH_SHORT).show();
                step = -step;
            }
        }


        if (playerOneTurn)
            playerOnePosition = playerOnePosition - step;
        else
            playerTwoPosition = playerTwoPosition + step;

    }

    void makemove() {
        if (playerOneTurn) {
            if (playerOnePosition != 0) {
                if (playerOnePrev != 15)
                    textViews[playerOnePrev].setText("");
                if (playerOnePosition == playerTwoPosition) {
                    Toast.makeText(getApplicationContext(), "YOU JUST GOT REKT!!", Toast.LENGTH_SHORT).show();
                    playerTwoFirstTime = true;
                    playerTwoPosition = 0;
                }
                textViews[playerOnePosition].setText("X");
            } else {
                textViews[playerOnePrev].setText("");
                Toast.makeText(getApplicationContext(), "LAP COMPLETED", Toast.LENGTH_SHORT).show();
                playerOneLaps++;
                playerOneFirstTime = true;
                playerOnePosition = 15;
            }
        } else {
            if (playerTwoPosition != 15) {
                if (playerTwoPrev != 0)
                    textViews[playerTwoPrev].setText("");
                if (playerTwoPosition == playerOnePosition) {
                    Toast.makeText(getApplicationContext(), "YOU JUST GOT REKT!!", Toast.LENGTH_SHORT).show();
                    playerOneFirstTime = true;
                    playerOnePosition = 15;
                }
                textViews[playerTwoPosition].setText("O");
            } else {
                textViews[playerTwoPrev].setText("");
                Toast.makeText(getApplicationContext(), "LAP COMPLETED", Toast.LENGTH_SHORT).show();
                playerTwoLaps++;
                playerTwoFirstTime = true;
                playerTwoPosition = 0;

            }


        }
    }

    void checkForWin() {
        Intent i;
        if (playerOneLaps == totalLaps) {
            i = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(i);
            finish();
        } else {
            if (playerTwoLaps == totalLaps) {
                i = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(intent, 0);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    SharedPreferences sh1 = PreferenceManager.getDefaultSharedPreferences(this);
                    int editLaps = Integer.parseInt(sh1.getString("numberOfLaps", "3"));
                    boolean bidir = sh1.getBoolean("bidirection", false);
                    boolean rand = sh1.getBoolean("startRandom", false);
                    totalLaps = editLaps;
                    if (bidir) {
                        radioBtn.check(R.id.upBtn);
                        radioBtn.setVisibility(View.VISIBLE);
                        radioBtn2.check(R.id.upBtn2);
                        radioBtn2.setVisibility(View.VISIBLE);
                    } else {
                        radioBtn.check(R.id.upBtn);
                        radioBtn.setVisibility(View.INVISIBLE);
                        radioBtn2.check(R.id.upBtn2);
                        radioBtn2.setVisibility(View.INVISIBLE);
                    }
                    startRandom = rand;

                }
        }
    }


}