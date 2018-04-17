package com.zproject.apelaez.catchphrase.game.charade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.model.Game;

public class CharadeActivity extends AppCompatActivity {

    CharadeFragment gameFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameFragment = CharadeFragment.newInstance(Game.CHARADE);

        getSupportFragmentManager().beginTransaction().add(R.id.container, gameFragment, "catchphrase").commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameFragment.backPressed();
        finish();
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
