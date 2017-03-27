package com.idlab.idcorp.assignment_android.network.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageService {
    @GET("/assignment")
    Call<ResponseBody> getImages();
}
