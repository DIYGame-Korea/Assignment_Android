package com.idlab.idcorp.assignment_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idlab.idcorp.assignment_android.R;

/**
 * Created by diygame5 on 2017-03-24.
 */

public class MyPageFragment extends Fragment {
    private String TAG = MyPageFragment.class.getSimpleName();
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, null);
        return view;
    }
}
