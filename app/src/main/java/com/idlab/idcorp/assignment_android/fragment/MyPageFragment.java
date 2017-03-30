package com.idlab.idcorp.assignment_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.activity.SettingActivity;
import com.idlab.idcorp.assignment_android.common.utils.ImageUtil;

import static android.app.Activity.RESULT_OK;
import static com.idlab.idcorp.assignment_android.common.Constants.*;

/**
 * Created by diygame5 on 2017-03-24.
 * Project : Assignment_Android
 */

public class MyPageFragment extends Fragment {
    private String TAG = MyPageFragment.class.getSimpleName();
    private Context mContext;

    private TextView mChangeTextView;
    private AppCompatImageView mProfileImageView;
    private AppCompatImageView mProfileBackgroundImageView;

    private TextView mNameTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container,false);

        setHasOptionsMenu(true);
        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        mChangeTextView = (TextView) view.findViewById(R.id.btn_change_profile);
        mProfileImageView = (AppCompatImageView) view.findViewById(R.id.iv_profile);
        mProfileBackgroundImageView = (AppCompatImageView) view.findViewById(R.id.iv_profile_background);
        mChangeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });
        mNameTextView = (TextView) view.findViewById(R.id.tv_name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Glide.with(mContext).load(uri.toString()).asBitmap().into(new SimpleTarget<Bitmap>(IMAGE_DESIRED_WIDTH, IMAGE_DESIRED_HEIGHT) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mProfileImageView.setImageBitmap(resource);
                        mProfileBackgroundImageView.setImageBitmap(resource);
                        saveProfile(resource);
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bitmap profile = getProfile();
        if (profile != null) {
            mProfileImageView.setImageBitmap(profile);
            mProfileBackgroundImageView.setImageBitmap(profile);
        } else {
            //Default profile image
            mProfileImageView.setImageResource(R.drawable.ic_profile);
            mProfileBackgroundImageView.setImageResource(R.drawable.ic_profile);
        }
        mNameTextView.setText(getString(R.string.welcome_text, getName()));
    }

    private Bitmap getProfile() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String encodedStr = pref.getString(PREF_KEY_PROFILE_IMAGE, null);
        if (encodedStr == null) {
            return null;
        } else {
            return ImageUtil.convert(encodedStr);
        }
    }

    private String getName() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        return pref.getString(PREF_KEY_PROFILE_NAME, getString(R.string.default_name));
    }

    private void saveProfile(Bitmap bitmap) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = pref.edit();

        String encodedStr = ImageUtil.convert(bitmap);

        editor.putString(PREF_KEY_PROFILE_IMAGE, encodedStr);
        editor.apply();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my_page, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_setting) {
            Intent intent = new Intent(mContext, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
