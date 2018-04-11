package com.zproject.apelaez.catchphrase.configurations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zproject.apelaez.catchphrase.R;

public class ConfigActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().add(R.id.container, new ConfigFragment(), "configurations").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
}