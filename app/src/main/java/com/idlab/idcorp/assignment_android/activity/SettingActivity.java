package com.idlab.idcorp.assignment_android.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.common.Constants;

/**
 * Created by diygame5 on 2017-03-29.
 * Project : Assignment_Android
 */

public class SettingActivity extends AppCompatActivity {
    private String TAG = SettingActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.title_setting));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
        }

        @Override
        public void onResume() {
            super.onResume();
            findPreference(Constants.PREF_KEY_DELETE_PROFILE).setOnPreferenceClickListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            findPreference(Constants.PREF_KEY_DELETE_PROFILE).setOnPreferenceClickListener(null);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (TextUtils.equals(preference.getKey(), Constants.PREF_KEY_DELETE_PROFILE)) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(Constants.PREF_KEY_PROFILE_IMAGE);
                editor.apply();
            }
            return false;
        }
    }
}
