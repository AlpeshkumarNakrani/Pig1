package com.example.alpesh.pairodice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Player2 extends ActionBarActivity {
    private FrameLayout die1, die2;
    private Button roll, hold;

    private TextView p1_score, p2_score, rollTotal2;
    private int player2_Total, total=0, score;
    private String score1,score2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player2);

        rollTotal2 = (TextView) findViewById(R.id.round1);
        score = Integer.parseInt(rollTotal2.getText().toString());


        Intent i = getIntent();
        score1 = i.getStringExtra("player1_Score");
        score2 = i.getStringExtra("player2_Score");

        p1_score = (TextView) findViewById(R.id.scorep3);
        p2_score = (TextView) findViewById(R.id.scorep4);


        p1_score.setText(score1);
        p2_score.setText(score2);
        player2_Total = Integer.parseInt(score2);

        roll = (Button) findViewById(R.id.button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp =0;
                temp        = rollDice();
                rollTotal2.setText(String.valueOf(temp));
            }
        });

        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //method to store the total of player1
                switchPlayer();

            }
        });

        die1 = (FrameLayout) findViewById(R.id.die1);
        die2 = (FrameLayout) findViewById(R.id.die2);

    }

    //get two random ints between 1 and 6 inclusive
    public void switchPlayer() {
        player2_Total = player2_Total + score;

        if (player2_Total<100) {
            p2_score.setText(String.valueOf(player2_Total));

            Intent i = new Intent(Player2.this, MainActivity.class);
            i.putExtra("player1_Score", p1_score.getText().toString());
            i.putExtra("player2_Score", String.valueOf(player2_Total));
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else  {

            AlertDialog alertDialog = new AlertDialog.Builder(Player2.this).create();
            alertDialog.setTitle("Yey...You Won!!");
            alertDialog.setMessage("Player 2 is Winner!!");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                            Intent i = new Intent(Player2.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            return;
                        }
                    });

            alertDialog.show();
        }

    }



    //get two random ints between 1 and 6 inclusive
    public int rollDice() {
        int val1 = 1 + (int) (6 * Math.random());
        int val2 = 1 + (int) (6 * Math.random());
        setDie(val1, die1);
        setDie(val2, die2);

        //Added Code
        if (val1!=1 && val2!=1) {
            total = val1 + val2;
            score += total;
        }
        else{
            score =0;
            Intent intent = new Intent(Player2.this,MainActivity.class);
            intent.putExtra("player1_Score", p1_score.getText().toString());
            intent.putExtra("player2_Score",Integer.toString(player2_Total) );
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent);
        }

        return score;
    }

    //set the appropriate picture for each die per int
    public void setDie(int value, FrameLayout layout) {
        Drawable pic = null;

        switch (value) {
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }
        layout.setBackground(pic);
    }

}
