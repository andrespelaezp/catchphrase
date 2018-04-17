package com.zproject.apelaez.catchphrase.game.results;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.zproject.apelaez.catchphrase.model.Result;

public class ResultFragment extends Fragment {

    public static ResultFragment newInstance(Result result) {
        Bundle args = new Bundle();
        args.putParcelable("result", result);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
