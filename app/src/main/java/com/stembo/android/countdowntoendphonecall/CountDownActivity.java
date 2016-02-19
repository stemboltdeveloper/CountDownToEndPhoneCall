package com.stembo.android.countdowntoendphonecall;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

public class CountDownActivity extends AppCompatActivity {

    private TextView displayCountDown;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private Button quitButton;
    private CheckBox endCallBox;
    private NumberPicker mNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        displayCountDown = (TextView) findViewById(R.id.countDownTextView);
        startButton = (Button) findViewById(R.id.startButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        quitButton = (Button) findViewById(R.id.quitButton);
        endCallBox = (CheckBox) findViewById(R.id.endCallBox);

        displayCountDown.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        quitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        endCallBox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Program me to do something or shut up.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_count_down, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
