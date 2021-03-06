package com.zproject.apelaez.catchphrase.game.catchphrase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.model.Game;

public class CatchphraseActivity extends AppCompatActivity {

    CatchphraseFragment catchphraseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catchphraseFragment = CatchphraseFragment.newInstance(Game.CATCHPHRASE);

        getSupportFragmentManager().beginTransaction().add(R.id.container, catchphraseFragment, "catchphrase").commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        catchphraseFragment.backPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            catchphraseFragment.backPressed();
            finish();
            return true;
        }
        return false;
    }
}
