package com.zproject.apelaez.catchphrase.game.catchphrase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.game.GameContract;
import com.zproject.apelaez.catchphrase.util.Utilities;

import java.util.Timer;
import java.util.TimerTask;

public class CatchphrasePresenter implements GameContract.UserActionsListener {

    private static final String TAG = "CatchphrasePresenter";

    GameContract.View view;
    private Timer myTimer;
    private long starttime = System.currentTimeMillis();

    private int min;
    private int max;
    private boolean running;

    Thread one;

    public CatchphrasePresenter(GameContract.View view, Context context) {
        this.view = view;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        min = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.edittext_mintime), "30"));
        max = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.edittext_maxtime), "60"));

        startTimer();
    }

    @Override
    public void initGame() {
        view.showMsg(R.string.game_started);
        view.play(true, 1f);
    }

    @Override
    public void exitGame() {
        running = false;
        if (one != null && one.isAlive())
            one.interrupt();
    }

    @Override
    public void exitGame(int resId){
        if (running)
            view.showMsg(resId);
        running = false;
        view.buzzTimeout();
    }

    @Override
    public void swipe() {
        //Logging
    }

    void startTimer() {
        running = true;

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                exitGame(R.string.timeout);
            }
        }, Utilities.randomValue(min, max) * 1000);


        one = new Thread() {
            public void run() {
                try {
                    while (running) {

                        // /*Milisegundos mínimos*/ - /*Milisegundos trancurridos*/
                        long speed = min * 1000 - (System.currentTimeMillis() - starttime);
                        Log.d(TAG, "SPEED = " + speed );

                        if (speed > 200)
                            if (speed > 3000)
                                Thread.sleep(3000);
                            else
                                Thread.sleep(speed);
                        else
                            Thread.sleep(200);

                        view.play(false, speed);
                    }
                } catch (InterruptedException e) {
                    running = false;
                    Log.d(TAG, "STOPPED");
                }
            }
        };

        one.start();
    }

    void changeSpeed(float speed) {
//        if (speed < 0.6f)
//            view.play(true, 1f);
//        else
            view.play(true, speed);
    }

//    @Override
//    public void run() {
//        while (running) {
//            try {
//                float speed = (float) (current / 1000) /*Segundos trancurridos*/
//                        / min       /*Segundos mínimos*/;
//                changeSpeed(speed);
//                Log.d(TAG, "SPEED = " + speed);
//                Thread.sleep((long) 5000);
//            } catch (InterruptedException e) {
//                running = false;
//                Log.d(TAG, "STOPPED");
//            }
//        }
//    }

}
