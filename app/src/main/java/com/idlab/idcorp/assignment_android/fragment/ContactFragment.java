package com.idlab.idcorp.assignment_android.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.adapter.ContactAdapter;
import com.idlab.idcorp.assignment_android.data.Contact;
import com.idlab.idcorp.assignment_android.utils.PermissionUtil;

import java.util.ArrayList;

/**
 * Created by diygame5 on 2017-03-24.
 */

public class ContactFragment extends Fragment {
    private String TAG = ContactFragment.class.getSimpleName();
    private Context mContext;

    private RecyclerView mContactRecyclerView;
    private ContactAdapter mContactAdapter;
    private ArrayList<Contact> mContactList;
    private LinearLayoutManager mLayoutManager;

    private EditText mSearchEditText;
    private ArrayList<Contact> mSearchContactList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        mContactList = new ArrayList<>();
        mContactAdapter = new ContactAdapter(mContext, mContactList);
        mSearchContactList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, null);

        initComponent(view);

        if (PermissionUtil.checkAndRequestPermission(ContactFragment.this, PermissionUtil.PERMISSION_CONTACT, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)) {
            //권한 승인됨
            new GetContacts().execute();
        } else {
            //권한 거부됨
        }
        return view;
    }

    private void initComponent(View view) {
        mContactRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler);

        mContactRecyclerView.setAdapter(mContactAdapter);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContactRecyclerView.setLayoutManager(mLayoutManager);
        mContactRecyclerView.setHasFixedSize(true);
        mSearchEditText = (EditText) view.findViewById(R.id.contact_search);
        //mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        //    @Override
        //    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        //            performSearch(v.getText().toString());
        //            return true;
        //        }
        //        return false;
        //    }
        //});
    }

    private TextWatcher mOnSearchListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            performSearch(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void performSearch(String key) {
        if (key.length() == 0) {
            mContactAdapter.setDataList(mContactList);
            mContactAdapter.notifyDataSetChanged();
        } else {
            mSearchContactList.clear();
            for (int i = 0; i < mContactList.size(); i++) {
                if (mContactList.get(i).getUserName().contains(key)) {
                    mSearchContactList.add(mContactList.get(i));
                }
            }
            mContactAdapter.setDataList(mSearchContactList);
            mContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.PERMISSION_CONTACT:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    new GetContacts().execute();
                } else {
                    PermissionUtil.showRationalDialog(mContext, getString(R.string.permission_dialog_message_contact));
                }
                break;
        }
    }


    class GetContacts extends AsyncTask<Void, Void, Void> {
        private final String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContactList.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ContentResolver cr = mContext.getContentResolver();
            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                try {
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    String name, number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex);
                        Contact contact = new Contact(name, number);
                        mContactList.add(contact);
                    }
                } finally {
                    cursor.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mContactAdapter.notifyDataSetChanged();
            mSearchEditText.addTextChangedListener(mOnSearchListener);
        }
    }

}
