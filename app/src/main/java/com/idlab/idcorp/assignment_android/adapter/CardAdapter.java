package com.idlab.idcorp.assignment_android.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.data.Card;

import java.util.ArrayList;

/**
 * Created by diygame5 on 2017-03-27.
 * Project : Assignment_Android
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ListItemViewHolder> {
    private static final String TAG = CardAdapter.class.getSimpleName();
    private Context mContext = null;
    private final ArrayList<Card> mDataList;

    public CardAdapter(Context mContext, ArrayList<Card> dataList) {
        super();
        this.mContext = mContext;
        this.mDataList = dataList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertise_card, parent, false);
        return new ListItemViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        final Card data = mDataList.get(position);
        holder.tvMainTitle.setText(data.getMainTitle());
        holder.tvSubTitle.setText(data.getSubTitle());
        holder.tvDescription.setText(data.getDescription());
        Glide.with(mContext)
                .load(data.getImageUrl())
                .fitCenter()
                .into(holder.ivImage);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvMainTitle;
        TextView tvSubTitle;
        TextView tvDescription;
        AppCompatImageView ivImage;

        ListItemViewHolder(View itemView, int viewType) {
            super(itemView);
            tvMainTitle = (TextView) itemView.findViewById(R.id.card_main_title);
            tvSubTitle = (TextView) itemView.findViewById(R.id.card_sub_title);
            tvDescription = (TextView) itemView.findViewById(R.id.card_description);
            ivImage = (AppCompatImageView) itemView.findViewById(R.id.card_image);
        }
    }

}
