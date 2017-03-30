package com.idlab.idcorp.assignment_android.data;

import android.graphics.Bitmap;

/**
 * Created by diygame5 on 2017-03-24.
 * Project : Assignment_Android
 */

public class Contact {
    private String userName;
    private String phoneNumber;
    private Bitmap profileImage;

    public Contact(String userName, String phoneNumber, Bitmap profileImage) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }
}
