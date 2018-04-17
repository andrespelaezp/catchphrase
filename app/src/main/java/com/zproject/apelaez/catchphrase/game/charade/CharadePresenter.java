package com.zproject.apelaez.catchphrase.game.charade;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.game.GameContract;

public class CharadePresenter implements GameContract.UserActionsListener {

    GameContract.View view;

    int time;

    public CharadePresenter(GameContract.View view, Context context) {
        this.view = view;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        time = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.edittext_time), "40"));
    }

    @Override
    public void initGame() {
        view.showMsg(R.string.game_started);
    }

    @Override
    public void exitGame() {

    }

    @Override
    public void finishGame(int redId) {

    }

    @Override
    public void swipe() {

    }
}
