package com.stembo.android.countdowntoendphonecall;

import android.os.CountDownTimer;

/**
 * Created by myxom on 16/02/18. Unused class for now, may be used if
 * I decide to implement the Pause & Reset buttons.
 */
public abstract class CountDownTimerPausable {

    long millisInFuture = 0;
    long countDownInterval = 0;
    long millisRemaining = 0;

    CountDownTimer countDownTimer = null;

    boolean isPaused = true;

    public CountDownTimerPausable(long millisInFuture, long countDownInterval){
        super();
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.millisRemaining = this.millisInFuture;
    }

    private void createCountDownTimer(){
        countDownTimer = new CountDownTimer(millisRemaining, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisRemaining = millisUntilFinished;
                CountDownTimerPausable.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                CountDownTimerPausable.this.onFinish();
            }
        };
    }

    public abstract void onTick(long millisUntilFinished);
    public abstract void onFinish();
    public final void cancel(){
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        this.millisRemaining = 0;
    }

    public synchronized final CountDownTimerPausable start(){
        if(isPaused){
            createCountDownTimer();
            countDownTimer.start();
            isPaused = false;
        }
        return this;
    }

    public void pause()throws IllegalStateException {
        if (isPaused==false){
            countDownTimer.cancel();
        } else {
            throw new IllegalStateException("CountDownTimerPausable is " +
                    "already in pause state, start counter before pausing it.");
        }
        isPaused = true;
    }

    public boolean isPaused(){
        return isPaused;
    }

}
