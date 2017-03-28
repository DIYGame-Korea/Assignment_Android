package com.idlab.idcorp.assignment_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.utils.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by diygame5 on 2017-03-24.
 */

public class MyPageFragment extends Fragment {
    private String TAG = MyPageFragment.class.getSimpleName();
    private Context mContext;

    private TextView mChangeTextView;
    private AppCompatImageView mProfileImageView;
    private AppCompatImageView mProfileBackgroundImageView;

    private final String PREF_NAME_PROFILE_IMAGE = "PREF_NAME_PROFILE_IMAGE";
    private final String PREF_KEY_PROFILE_IMAGE = "PREF_KEY_PROFILE_IMAGE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, null);

        initComponent(view);
        Bitmap profile = getStoredProfile();
        if (profile != null) {
            mProfileImageView.setImageBitmap(profile);
            mProfileBackgroundImageView.setImageBitmap(profile);
        }


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
                startActivityForResult(intent, 300);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Glide.with(mContext).load(uri.toString()).asBitmap().into(new SimpleTarget<Bitmap>(512, 512) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mProfileImageView.setImageBitmap(resource);
                        mProfileBackgroundImageView.setImageBitmap(resource);
                        saveProfileImage(resource);
                    }
                });
            }
        }

    }

    private Bitmap getStoredProfile() {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME_PROFILE_IMAGE, mContext.MODE_PRIVATE);
        String encodedStr = pref.getString(PREF_KEY_PROFILE_IMAGE, null);
        if (encodedStr == null) {
            return null;
        } else {
            Bitmap bitmap = ImageUtil.convert(encodedStr);
            return bitmap;
        }
    }

    private void saveProfileImage(Bitmap bitmap) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME_PROFILE_IMAGE, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String encodedStr = ImageUtil.convert(bitmap);

        editor.putString(PREF_KEY_PROFILE_IMAGE, encodedStr);
        editor.apply();
    }
}
