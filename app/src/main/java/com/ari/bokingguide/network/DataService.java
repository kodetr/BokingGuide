package com.ari.bokingguide.network;

import com.ari.bokingguide.network.models.Guide;
import com.ari.bokingguide.network.models.Login;
import com.ari.bokingguide.network.models.Wisatawan;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @GET("login.php")
    Call<List<Login>> login(@Query("username") String username, @Query("password") String password);

    @GET("view_wisatawan.php")
    Call<List<Wisatawan>> view_wisatawan();

    @GET("view_guide.php")
    Call<List<Guide>> view_guide();

    @FormUrlEncoded
    @POST("add_wisatawan.php")
    Call<ResponseBody> add_wisatawan(
            @Field("nama") String nama,
            @Field("umur") int umur,
            @Field("agama") String agama,
            @Field("bahasa") String bahasa,
            @Field("jk") String jk,
            @Field("kontak") String kontak,
            @Field("foto") String foto);

    @FormUrlEncoded
    @POST("add_guide.php")
    Call<ResponseBody> add_guide(
            @Field("nama") String nama,
            @Field("umur") int umur,
            @Field("agama") String agama,
            @Field("bahasa") String bahasa,
            @Field("kontak") String kontak,
            @Field("lokasi") String lokasi,
            @Field("jk") String jk,
            @Field("foto") String foto,
            @Field("vidio") String vidio);

    @Multipart
    @POST("upload_foto_wisatawan.php")
    Call<Object> upload_foto_wisatawan(@Query("id") int id, @Field("foto") MultipartBody.Part foto);

}
