package com.idlab.idcorp.assignment_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.network.service.ImageService;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by diygame5 on 2017-03-24.
 */

public class AdvertiseFragment extends Fragment {
    private String TAG = AdvertiseFragment.class.getSimpleName();
    private Context mContext;

    private ImageView mCardImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertise, null);

        mCardImage = (ImageView) view.findViewById(R.id.card_image);
        Glide.with(AdvertiseFragment.this)
                .load("https://s3.ap-northeast-2.amazonaws.com/into-assignment/images/test_1.jpg")
                .centerCrop()
                .into(mCardImage);

        //test
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://a2vkkgaard.execute-api.ap-northeast-2.amazonaws.com")
                .build();

        ImageService service = retrofit.create(ImageService.class);

        Call<ResponseBody> imageCall = service.getImages();
        imageCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return view;
    }
}
