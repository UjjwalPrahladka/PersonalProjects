package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] button = new Button[3][3];
    private Button resetBtn, viewScoresBtn;
    private boolean playerOneTurn = true;
    private int roundCount;
    private int playerOnePoints,playerTwoPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int resId = getResources().getIdentifier("button" + i + j, "id", getPackageName());
                button[i][j] = findViewById(resId);
                button[i][j].setOnClickListener(this);
            }
        }
        resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
                roundCount=0;
                playerOnePoints=0;
                playerTwoPoints=0;
            }
        });
        viewScoresBtn = findViewById(R.id.viewScoresButton);
        viewScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1;
                int1=new Intent(getApplicationContext(),SecondActivity.class);
                int1.putExtra("playerOnePoints",playerOnePoints);
                int1.putExtra("playerTwoPoints",playerTwoPoints);
                startActivity(int1);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals(""))
            return;
        else {
            if (playerOneTurn)
                ((Button) v).setText("X");
            else
                ((Button) v).setText("O");
            roundCount++;
            if(checkForWin()){
                if(playerOneTurn)
                    playerOneWins();
                else
                    playerTwoWins();
            }
            else if(roundCount==9)
                draw();
            else
                playerOneTurn = !playerOneTurn;

        }
    }

    public boolean checkForWin() {

        for (int i = 0; i < 3; i++) {
            if (button[i][0].getText().toString().equals(button[i][1].getText().toString())
                    && button[i][0].getText().toString().equals(button[i][2].getText().toString())
                    && !button[i][0].getText().toString().equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (button[0][i].getText().toString().equals(button[1][i].getText().toString())
                    && button[0][i].getText().toString().equals(button[2][i].getText().toString())
                    && !button[0][i].getText().toString().equals("")) {
                return true;
            }
        }

        if (button[0][0].getText().toString().equals(button[1][1].getText().toString())
                && button[0][0].getText().toString().equals(button[2][2].getText().toString())
                && !button[0][0].getText().toString().equals("")) {
            return true;
        }

        if (button[0][2].getText().toString().equals(button[1][1].getText().toString())
                && button[0][2].getText().toString().equals(button[2][0].getText().toString())
                && !button[0][2].getText().toString().equals("")) {
            return true;
        }
        return false;

    }

    public void playerOneWins(){
        playerOnePoints++;
        Toast.makeText(this,"PLAYER ONE WINS",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    public void playerTwoWins(){
        playerTwoPoints++;
        Toast.makeText(this,"PLAYER TWO WINS",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    public void draw(){
        Toast.makeText(this,"DRAW",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    public void resetBoard(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                button[i][j].setText("");
            }
        }
        roundCount=0;
    }

}
