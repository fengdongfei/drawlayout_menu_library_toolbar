package com.saike.tzh.homepage.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saike.tzh.R;
import com.saike.tzh.base.BaseFragment;

/**
 * Created by admin on 2017/4/22.
 */

public class MineFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maintab, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//        if (activity instanceof MainActivity_drawlayout_toolbar ) {
//            MainActivity_drawlayout_toolbar mainActivity = (MainActivity_drawlayout_toolbar) activity;
//        } else {
//            throw new IllegalArgumentException("The activity must be a MainActivity !");
//        }
    }
}
