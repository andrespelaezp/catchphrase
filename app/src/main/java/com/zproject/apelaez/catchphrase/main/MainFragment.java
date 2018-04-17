package com.zproject.apelaez.catchphrase.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zproject.apelaez.catchphrase.R;
import com.zproject.apelaez.catchphrase.configurations.ConfigActivity;
import com.zproject.apelaez.catchphrase.game.catchphrase.CatchphraseActivity;
import com.zproject.apelaez.catchphrase.game.charade.CharadeActivity;

import butterknife.OnClick;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.btn_catchphrase)
    Button btnCatchphrase;

    @BindView(R.id.btn_charade)
    Button btnCharade;

    @BindView(R.id.btn_config)
    Button btnConfig;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_catchphrase)
    void catchphrase() {
        startActivity(new Intent(getActivity(), CatchphraseActivity.class));
    }

    @OnClick(R.id.btn_charade)
    void charade() {
        startActivity(new Intent(getActivity(), CharadeActivity.class));
    }

    @OnClick(R.id.btn_config)
    void config() {
        startActivity(new Intent(getActivity(), ConfigActivity.class));
    }


}
