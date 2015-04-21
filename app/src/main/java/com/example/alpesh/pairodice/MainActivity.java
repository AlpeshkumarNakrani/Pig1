package com.example.alpesh.pairodice;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.preference.Preference.OnPreferenceClickListener;


public class MainActivity extends ActionBarActivity {

    //memeber variables
    private FrameLayout die1, die2;
    private Button roll, hold;
    private int player1_Total, total=0, score;
    private TextView p1_score, p2_score, rollTotal1;
    private String score1,score2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = (Button) findViewById(R.id.button);
        rollTotal1 = (TextView) findViewById(R.id.round);
        score = Integer.parseInt(rollTotal1.getText().toString());
        p1_score = (TextView) findViewById(R.id.scorep1);
        player1_Total = Integer.parseInt(p1_score.getText().toString());
        p2_score = (TextView) findViewById(R.id.scorep2);

        Intent intent = getIntent();
        Bundle extras =intent.getExtras();


        if(extras!=null) {

            score1 = intent.getStringExtra("player1_Score");
            player1_Total =Integer.parseInt(score1);
            p1_score.setText(String.valueOf(player1_Total));

            score2 = intent.getStringExtra("player2_Score");
            p2_score.setText(score2);
        }

        //Roll button logic
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });


        //Hold Button logic
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
        player1_Total += score;
        p1_score.setText(String.valueOf(player1_Total));

        if (player1_Total<99) {
            Intent i = new Intent(MainActivity.this, Player2.class);
            i.putExtra("player1_Score", Integer.toString(player1_Total));
            i.putExtra("player2_Score", p2_score.getText().toString());
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Yey...You Won!!");
            alertDialog.setMessage("Player 1 is Winner!!");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    Intent i = new Intent(MainActivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    return;
                }
            });
            alertDialog.show();
        }
    }




    //get two random ints between 1 and 6 inclusive
    public void rollDice() {
        int val1 = 1 + (int) (6 * Math.random());
        int val2 = 1 + (int) (6 * Math.random());
        setDie(val1, die1);
        setDie(val2, die2);

        //Updated Code
        if (val1!=1 && val2!=1) {
            total = val1 + val2;
            score += total;
        }
        else{
            Intent i = new Intent(MainActivity.this,Player2.class);
            i.putExtra("player1_Score",Integer.toString(player1_Total) );
            i.putExtra("player2_Score",p2_score.getText().toString() );
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        rollTotal1.setText(String.valueOf(score));

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
