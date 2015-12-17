package com.learn2develop.checkers;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.learn2develop.checkers.view.Board;

public class MainActivity extends AppCompatActivity {
    public TextView textView;
    private Board gameBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameBoard = (Board) findViewById(R.id.board);
        textView = (TextView) findViewById(R.id.textView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();

            }
        });
        newGame();
    }

    private void newGame() {
        Board.number = 0;
        Board.shouldWait = true;
        Board.turn = 0;
        Board.hackedRed = 0;
        Board.hackedBlack = 0;
        Board.hackedBlackKing = 0;
        Board.hackedRedKing = 0;
        for (int i = 1; i < 8; i += 2) {
            gameBoard.board[i][3] = 0;
        }
        for (int j = 0; j < 7; j += 2) {
            gameBoard.board[j][4] = 0;
        }
        gameBoard.invalidate();

    }
    public void boardClick(View view){
        textView.setTextColor(Board.turn == 0 ? Color.BLACK : Color.RED);
        textView.setText(Board.turn == 0 ? "blacks turn" : "reds turn");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://boardgames.about.com/cs/checkersdraughts/ht/play_checkers.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.refresh) {
            newGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
