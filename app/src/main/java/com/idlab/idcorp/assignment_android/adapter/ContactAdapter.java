package com.idlab.idcorp.assignment_android.adapter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.data.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ListItemViewHolder> {
    private static final String TAG = ContactAdapter.class.getSimpleName();
    private Context mContext = null;
    private ArrayList<Contact> mDataList;

    public ContactAdapter(Context mContext, ArrayList<Contact> dataList) {
        super();
        this.mContext = mContext;
        this.mDataList = dataList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ListItemViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        final Contact data = mDataList.get(position);
        holder.tvName.setText(data.getUserName());
        holder.tvPhone.setText(data.getPhoneNumber());

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getPhoneNumber()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //call activity 가 없을경우
                }
            }
        });

    }

    public void setDataList(ArrayList<Contact> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPhone;
        AppCompatImageView ivProfile;
        AppCompatImageView ivCall;

        public ListItemViewHolder(View itemView, int viewType) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.contact_item_name);
            tvPhone = (TextView) itemView.findViewById(R.id.contact_item_phone);
            ivProfile = (AppCompatImageView) itemView.findViewById(R.id.contact_item_profile);
            ivCall = (AppCompatImageView) itemView.findViewById(R.id.contact_item_call);
        }
    }

}