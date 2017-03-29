package com.idlab.idcorp.assignment_android.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.idlab.idcorp.assignment_android.R;
import com.idlab.idcorp.assignment_android.adapter.ContactAdapter;
import com.idlab.idcorp.assignment_android.common.utils.ImageUtil;
import com.idlab.idcorp.assignment_android.common.utils.PermissionUtil;
import com.idlab.idcorp.assignment_android.data.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
            new GetContacts().execute();
        } else {
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


    public class GetContacts extends AsyncTask<Void, Void, Void> {
        private final String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
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
                if (cursor.getCount() > 0) {
                    try {
                        final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        final int photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);

                        String name, number;
                        Bitmap profile;
                        while (cursor.moveToNext()) {
                            name = cursor.getString(nameIndex);
                            number = cursor.getString(numberIndex);
                            profile = null;

                            if (cursor.getString(photoUriIndex) != null) {
                                profile = ImageUtil.getBitmapFromUri(mContext, Uri.parse(cursor.getString(photoUriIndex)));
                            }
                            Contact contact = new Contact(name, number, profile);
                            mContactList.add(contact);
                        }

                    } finally {
                        cursor.close();
                    }
                    Collections.sort(mContactList, new NameAscending());
                }
            }
            return null;
        }

        //이름 오름차순
        private class NameAscending implements Comparator<Contact> {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        }

        //    private Bitmap getContactPhoto(Context context, long contact_id) {
        //        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact_id);
        //        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        //        Cursor cursor = context.getContentResolver().query(photoUri, new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        //        if (cursor == null)
        //            return null;
        //        try {
        //            if (cursor.getCount() > 0) {
        //                if (cursor.moveToFirst()) {
        //                    byte[] data = cursor.getBlob(0);
        //                    cursor.close();
        //                    if (data != null) {
        //                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //                        return bitmap;
        //                    }
        //                }
        //            }
        //        } finally {
        //            cursor.close();
        //        }
        //        return null;
        //    }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mContactAdapter.notifyDataSetChanged();
            mSearchEditText.addTextChangedListener(mOnSearchListener);
        }
    }

}
