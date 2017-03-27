package com.idlab.idcorp.assignment_android.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.idlab.idcorp.assignment_android.R;

/**
 * Created by diygame5 on 2017-03-24.
 */

public class DetailSettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }
}
