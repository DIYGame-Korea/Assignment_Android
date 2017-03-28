package com.idlab.idcorp.assignment_android.network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by diygame5 on 2017-03-28.
 */

public class ScalarRetrofitClient {
    private static final String BASE_URL = "https://a2vkkgaard.execute-api.ap-northeast-2.amazonaws.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
