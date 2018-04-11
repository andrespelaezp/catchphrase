package com.zproject.apelaez.catchphrase.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zproject.apelaez.catchphrase.game.ScreenFragment;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private int pages;

    public void addPage() {
        pages++;
    }

    public ScreenSlidePagerAdapter(FragmentManager fm, int pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return new ScreenFragment();
    }

    @Override
    public int getCount() {
        return pages;
    }

}