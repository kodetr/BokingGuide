/*
 * Copyright (c) 2017. Tanwir. All Rights Reserver.
 * <p>
 * Save to the extent permitted by law, you may not use,copy,modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Tanwir.
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

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