package com.idlab.idcorp.assignment_android.network;

import com.idlab.idcorp.assignment_android.network.service.ImageService;

/**
 * Created by diygame5 on 2017-03-28
 * Project : Assignment_Android
 */

public class ApiUtil {
    public static ImageService getImageService() {
        return RetrofitClient.getClient().create(ImageService.class);
    }
}
