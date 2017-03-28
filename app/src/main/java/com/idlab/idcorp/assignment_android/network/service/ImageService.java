package com.idlab.idcorp.assignment_android.network.service;

import com.idlab.idcorp.assignment_android.data.Card;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageService {
    @GET("/assignment")
    Call<ArrayList<Card>> getImages();
}
