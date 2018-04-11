package com.zproject.apelaez.catchphrase.game;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.model.Word;
import com.zproject.apelaez.catchphrase.util.Utilities;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScreenFragment extends Fragment {

    private Word word;

    @BindView(R.id.txtWord)
    TextView textView;

    @BindView(R.id.frame)
    View frame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = Utilities.getRandomWord(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_word, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        textView.setText(word.getValue());
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        frame.setBackgroundColor(color);
    }
}
