package com.zproject.apelaez.catchphrase.game.catchphrase;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.adapters.DepthPageTransformer;
import com.zproject.apelaez.catchphrase.adapters.ScreenSlidePagerAdapter;
import com.zproject.apelaez.catchphrase.adapters.ZoomOutPageTransformer;
import com.zproject.apelaez.catchphrase.game.GameContract;
import com.zproject.apelaez.catchphrase.game.GameFragment;
import com.zproject.apelaez.catchphrase.game.charade.CharadeFragment;
import com.zproject.apelaez.catchphrase.model.Game;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CatchphraseFragment extends GameFragment implements GameContract.View {

    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.exit)
    ImageView exit;

    private static final String TAG = "CatchphraseFragment";
    private static final String GAME_ARG = TAG + ".game_type";

    MediaPlayer mp;
    CatchphrasePresenter presenter;
    ScreenSlidePagerAdapter mPagerAdapter;
    //Support previous versions
    SoundPool soundPool;
    private int pos = 0;
    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position > pos) {
                nextWord(position);
                //presenter.swipe();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public static CatchphraseFragment newInstance(Game GameType) {
        Bundle args = new Bundle();
        args.putString(GAME_ARG, GameType.name());
        CatchphraseFragment fragment = new CatchphraseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(getActivity(), R.raw.gui);
        presenter = new CatchphrasePresenter(this, getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), 2);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mPager.addOnPageChangeListener(listener);

    }

    @OnClick(R.id.exit) void exit(){
        presenter.exitGame();
        getActivity().finish();
    }

    @Override
    public void start() {
        presenter.startTimer();
    }

    @Override
    public void play(boolean loop, float speed) {
        mp.start();
    }

    @Override
    public void buzzTimeout() {
        //stop countdown sound
        if (mp == null || getActivity() == null)
            return;

        mp.setLooping(false);
        mp.stop();

        mp = MediaPlayer.create(getActivity(), R.raw.horn);
        mp.setLooping(false);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
            }
        });
        vibrate();
    }

    @Override
    public void showMsg(final int resId) {
//        Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
        if (getActivity() != null)
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public void nextWord(int position) {
        pos = position;
        mPagerAdapter.addPage();
        mPagerAdapter.notifyDataSetChanged();
    }

    public void backPressed(){
        presenter.exitGame();
    }

    public void vibrate() {
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    public void playSpeedSupport(float speed) {
        final float playbackSpeed = speed;
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

        final int soundId = soundPool.load(Environment.getExternalStorageDirectory()
                + "/sample.3gp", 1);
        AudioManager mgr = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final float volume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
                soundPool.play(soundId, volume, volume, 1, -1, playbackSpeed);
            }
        });
    }
}
