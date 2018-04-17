package com.zproject.apelaez.catchphrase.game.charade;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.adapters.ScreenSlidePagerAdapter;
import com.zproject.apelaez.catchphrase.adapters.ZoomOutPageTransformer;
import com.zproject.apelaez.catchphrase.game.GameContract;
import com.zproject.apelaez.catchphrase.game.GameFragment;
import com.zproject.apelaez.catchphrase.model.Game;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharadeFragment extends GameFragment
        implements GameContract.View,
        SensorEventListener {

    private static final String TAG = "CharadeFragment";
    private static final String GAME_ARG = TAG + ".game_type";

    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.exit)
    ImageView exit;


    MediaPlayer mp;
    CharadePresenter presenter;
    ScreenSlidePagerAdapter mPagerAdapter;
    private int pos = 0;

    //Movement
    private SensorManager mSensorManager;

    private Sensor accelerometer;
    private Sensor magnetometer;

    float[] inclineGravity = new float[3];
    float[] mGravity;
    float[] mGeomagnetic;
    float pitch;
    float roll;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(getActivity(), R.raw.gui);
        presenter = new CharadePresenter(this, getActivity());

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

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
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.addOnPageChangeListener(listener);
    }

    public static CharadeFragment newInstance(Game GameType) {
        Bundle args = new Bundle();
        args.putString(GAME_ARG, GameType.name());
        CharadeFragment fragment = new CharadeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void backPressed() {
        presenter.exitGame();
    }

    @Override
    public void start() {
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void play(boolean loop, float speed) {

    }

    @Override
    public void buzzTimeout() {

    }

    @Override
    public void showMsg(int resId) {

    }

    @Override
    public void nextWord(int pos) {

    }

    @OnClick(R.id.exit)
    void exit() {
        presenter.exitGame();
        getActivity().finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //If type is accelerometer only assign values to global property mGravity
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;

            if (isTiltDownward()) {
                Log.d("test", "downwards");
            } else if (isTiltUpward()) {
                Log.d("test", "upwards");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isTiltUpward() {
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                /*
                 * If the roll is positive, you're in reverse landscape (landscape right), and if the roll is negative you're in landscape (landscape left)
                 *
                 * Similarly, you can use the pitch to differentiate between portrait and reverse portrait.
                 * If the pitch is positive, you're in reverse portrait, and if the pitch is negative you're in portrait.
                 *
                 * orientation -> azimut, pitch and roll
                 *
                 *
                 */

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on ground or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                /*
                 * Float obj1 = new Float("10.2");
                 * Float obj2 = new Float("10.20");
                 * int retval = obj1.compareTo(obj2);
                 *
                 * if(retval > 0) {
                 * System.out.println("obj1 is greater than obj2");
                 * }
                 * else if(retval < 0) {
                 * System.out.println("obj1 is less than obj2");
                 * }
                 * else {
                 * System.out.println("obj1 is equal to obj2");
                 * }
                 */
                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 30 && inclination < 40)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public boolean isTiltDownward() {
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on groud or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 140 && inclination < 170)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}
