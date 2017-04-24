package com.saike.tzh.homepage.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saike.tzh.R;
import com.saike.tzh.base.BaseFragment;

import butterknife.ButterKnife;


/**
 * Created by admin on 2017/4/22.
 */

public class MainFragment extends BaseFragment {
    private View viewroot;
    private FragmentActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewroot = inflater.inflate(R.layout.fragment_tab_main,container,false);
        mActivity = getActivity();
        initView();
        return viewroot;
    }

    private void initView() {

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
