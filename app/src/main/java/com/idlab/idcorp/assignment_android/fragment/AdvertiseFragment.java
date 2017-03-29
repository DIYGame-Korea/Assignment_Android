package com.idlab.idcorp.assignment_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.adapter.CardAdapter;
import com.idlab.idcorp.assignment_android.data.Card;
import com.idlab.idcorp.assignment_android.network.ApiUtil;
import com.idlab.idcorp.assignment_android.network.service.ImageService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diygame5 on 2017-03-24.
 */

public class AdvertiseFragment extends Fragment {
    private String TAG = AdvertiseFragment.class.getSimpleName();
    private Context mContext;

    private RecyclerView mCardRecyclerView;
    private CardAdapter mCardAdapter;
    private ArrayList<Card> mCardList;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertise, null);

        initComponent(view);

        ImageService service = ApiUtil.getImageService();
        //API CALL
        service.getImages().enqueue(new Callback<ArrayList<Card>>() {
            @Override
            public void onResponse(Call<ArrayList<Card>> call, Response<ArrayList<Card>> response) {
                if (mCardList != null) {
                    mCardList.clear();
                }
                //Deep copy
                mCardList.addAll(response.body());
                mCardAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<Card>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }

    private void initComponent(View view) {
        mCardRecyclerView = (RecyclerView) view.findViewById(R.id.card_recycler);
        mCardList = new ArrayList<>();
        mCardAdapter = new CardAdapter(mContext, mCardList);
        mCardRecyclerView.setAdapter(mCardAdapter);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCardRecyclerView.setLayoutManager(mLayoutManager);
        mCardRecyclerView.setNestedScrollingEnabled(false);
        mCardRecyclerView.setHasFixedSize(true);
    }

}
