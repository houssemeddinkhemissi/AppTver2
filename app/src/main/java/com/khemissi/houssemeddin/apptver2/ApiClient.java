package com.khemissi.houssemeddin.apptver2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Houssem on 20/11/2017.
 */

public class ApiClient {



    public static Retrofit retrofit = null;
    public  static Retrofit getApiClient (String baseUrl){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
