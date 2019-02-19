package com.ari.bokingguide.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataProvider {
    private DataService nService;
    public DataProvider() {
        OkHttpClient httpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://webkodetr.000webhostapp.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nService = retrofit.create(DataService.class);
    }
    public DataService getTService() {
        return nService;
    }
}