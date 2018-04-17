package com.zproject.apelaez.catchphrase.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.game.charade.CharadeFragment;
import com.zproject.apelaez.catchphrase.game.results.ResultFragment;
import com.zproject.apelaez.catchphrase.model.Game;
import com.zproject.apelaez.catchphrase.model.Result;

public class GameActivity extends AppCompatActivity {

    GameFragment gameFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameFragment = CharadeFragment.newInstance(Game.CHARADE);

        getSupportFragmentManager().beginTransaction().add(R.id.container, gameFragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameFragment.backPressed();
        finish();
    }

    public void showResults(Result results) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ResultFragment().newInstance(results));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            gameFragment.backPressed();
            finish();
            return true;
        }
        return false;
    }

}
