package com.stembo.android.countdowntoendphonecall;

import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class CountDownActivity extends AppCompatActivity {

    private TextView displayCountDown;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;
    private Button quitButton;
    private CheckBox endCallBox;
    private TextView quitTextView;
    private NumberPicker mNumberPickerMinute;
    private NumberPicker mNumberPickerHour;

    private int mDefaultCountDown = 5000;
    private int mOneSecondInterval = 1000;

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
        quitTextView = (TextView) findViewById(R.id.quitTextView);
        mNumberPickerMinute = (NumberPicker) findViewById(R.id.numberPickerMinute);
        mNumberPickerHour = (NumberPicker) findViewById(R.id.numberPickerHour);

        displayCountDown.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startButton.setEnabled(false);
                int countDownTime = mNumberPickerHour.getValue()*3600+
                        mNumberPickerMinute.getValue()*60;
                CountDownTimerPausable cDTP =
                        new CountDownTimerPausable(1000*countDownTime, mOneSecondInterval) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished<60000){
                                    long secUntilFinished = millisUntilFinished/1000;
                                    displayCountDown.setText(Long.toString(secUntilFinished) + "s");
                                } else if (millisUntilFinished<60000*60) {
                                    long minUntilFinished = millisUntilFinished/1000/60;
                                    long secUntilFinished = millisUntilFinished/1000%60;
                                    displayCountDown.setText(Long.toString(minUntilFinished)
                                            + "m " + secUntilFinished + "s");
                                } else {
                                    long hourUntilFinished = millisUntilFinished/1000/60/60;
                                    long minUntilFinished = millisUntilFinished/1000/60%60;
                                    long secUntilFinished = millisUntilFinished/1000%60;
                                    displayCountDown.setText(Long.toString(hourUntilFinished)
                                            + "h " + minUntilFinished
                                            + "m " + secUntilFinished + "s");
                                }
                            }

                            @Override
                            public void onFinish() {
                                displayCountDown.setText("Finished.");
                                startButton.setEnabled(true);
                                if (endCallBox.isChecked()){
                                    disconnectCall();
                                }
                            }
                        }.start();

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startButton.setEnabled(false);
                pauseButton.setEnabled(false);
                resetButton.setEnabled(false);
                quitButton.setEnabled(false);
                mNumberPickerHour.setEnabled(false);
                mNumberPickerMinute.setEnabled(false);
                endCallBox.setEnabled(false);
                Toast.makeText(getApplicationContext(),
                        R.string.quitText, Toast.LENGTH_LONG).show();
                CountDownTimer cDT = new CountDownTimer(mDefaultCountDown, mOneSecondInterval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int millis = Math.round(millisUntilFinished) / 1000;
                        millis -= 1;
                        quitTextView.setText(Integer.toString(millis));
                    }

                    @Override
                    public void onFinish() {
                        System.exit(0);
                    }
                }.start();

            }
        });

        endCallBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (endCallBox.isChecked()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.checkBoxEndCalls, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.checkBoxNotEndCalls, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNumberPickerMinute.setMinValue(1);
        mNumberPickerMinute.setMaxValue(59);
        mNumberPickerMinute.setWrapSelectorWheel(true);
        mNumberPickerMinute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateView();
                //displayCountDown.setText(Integer.toString(newVal) + " minutes");
            }
        });

        mNumberPickerHour.setMinValue(0);
        mNumberPickerHour.setMaxValue(9);
        mNumberPickerHour.setWrapSelectorWheel(true);
        mNumberPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateView();
                //displayCountDown.setText(Integer.toString(newVal) + " hours");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,
                        R.string.insult, Snackbar.LENGTH_SHORT)
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

    public void updateView(){
        int hourPicked = mNumberPickerHour.getValue();
        int minutePicked = mNumberPickerMinute.getValue();
        if (hourPicked<2){
            if (minutePicked<2){
                displayCountDown.setText(Integer.toString(hourPicked) +
                        " hour " + Integer.toString(minutePicked) + " minute");
            } else {
                displayCountDown.setText(Integer.toString(hourPicked) +
                        " hour " + Integer.toString(minutePicked) + " minutes");
            }
        } else if (minutePicked<2){
            displayCountDown.setText(Integer.toString(hourPicked) +
                    " hours " + Integer.toString(minutePicked) + " minute");
        } else {
            displayCountDown.setText(Integer.toString(hourPicked) +
                    " hours " + Integer.toString(minutePicked) + " minutes");
        }
    }

    public void disconnectCall(){
        //disconnect the active phone call
        try {
            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod =
                    serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            //Log.error(DialerActivity.this,
            //        "FATAL ERROR: could not connect to telephony subsystem");
            //Log.error(DialerActivity.this, "Exception object: " + e);
        }
    }
}
