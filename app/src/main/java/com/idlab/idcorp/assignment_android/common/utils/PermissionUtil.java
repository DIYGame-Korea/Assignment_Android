/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.idlab.idcorp.assignment_android.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.idlab.idcorp.assignment_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2017-03-24.
 * Runtime 에서 권한을 획특하기 위한 유틸리티 클래스
 */

public class PermissionUtil {
    public static final int PERMISSION_CONTACT = 1;

    public static boolean checkAndRequestPermission(Activity activity, int permissionRequestCode, String... permissions) {
        String[] requiredPermissions = getRequiredPermissions(activity, permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (requiredPermissions.length > 0 && !activity.isDestroyed()) {
                ActivityCompat.requestPermissions(activity, requiredPermissions, permissionRequestCode);
                return false;
            } else {
                return true;
            }
        } else {
            if (requiredPermissions.length > 0) {
                ActivityCompat.requestPermissions(activity, requiredPermissions, permissionRequestCode);
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean checkAndRequestPermission(Fragment fragment, int permissionRequestCode, String... permissions) {
        String[] requiredPermissions = getRequiredPermissions(fragment.getContext() != null ?
                fragment.getContext() : fragment.getActivity(), permissions);

        if (requiredPermissions.length > 0 && fragment.isAdded()) {
            fragment.requestPermissions(requiredPermissions, permissionRequestCode);
            return false;
        } else {
            return true;
        }
    }

    public static String[] getRequiredPermissions(Context context, String... permissions) {
        List<String> requiredPermissions = new ArrayList<>();

        if (context == null) return requiredPermissions.toArray(new String[1]);

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(permission);
            }
        }

        return requiredPermissions.toArray(new String[requiredPermissions.size()]);
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) return false;

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    public static void showRationalDialog(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.permission_dialog_title))
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.permission_dialog_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    .setData(Uri.parse("package:" + context.getPackageName()));
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            context.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(context.getString(R.string.permission_dialog_close), null);
        builder.show();
    }

    ////권한 요청 예시 (액티비티,프래그먼트 내에서)
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, null);

        if (PermissionUtil.checkAndRequestPermission(ContactFragment.this, PermissionUtil.PERMISSION_CONTACT, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)) {
            //권한 승인됨
        } else {
            //권한 거부됨
        }
        return view;
    }
     */

    ////권한 결과값 처리 예시 (액티비티,프래그먼트 내에서)
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.PERMISSION_CONTACT:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    // 권한 얻음
                } else {
                    PermissionUtil.showRationalDialog(mContext, getString(R.string.permission_dialog_message_contact));
                }
                break;
        }
    }
     */
}
