package com.example.tictactoe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView scoreViewOne, scoreViewTwo;
        scoreViewOne=findViewById(R.id.scoreView1);
        scoreViewTwo=findViewById(R.id.scoreView2);
        scoreViewOne.setText(String.valueOf(getIntent().getExtras().getInt("playerOnePoints",0)));
        scoreViewTwo.setText(String.valueOf(getIntent().getExtras().getInt("playerTwoPoints",0)));
    }
}
