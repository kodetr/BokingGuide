package com.ari.bokingguide.network;

import com.ari.bokingguide.network.models.Destinasi;
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
import retrofit2.http.Part;
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
            @Field("foto") String foto,
            @Field("nama_guide") String nama_guide,
            @Field("tgl_mulai") String tgl_mulai,
            @Field("tgl_akhir") String tgl_akhir
    );

    @GET("delete_wisatawan.php")
    Call<ResponseBody> delete_wisatawan(@Query("id") int id);

    @Multipart
    @POST("upload_foto_wisatawan.php")
    Call<ResponseBody> upload_foto_wisatawan(@Query("id") int id, @Part MultipartBody.Part foto);

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
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("update_guide.php")
    Call<ResponseBody> update_guide(
            @Query("id") int id,
            @Field("nama") String nama,
            @Field("umur") int umur,
            @Field("agama") String agama,
            @Field("bahasa") String bahasa,
            @Field("kontak") String kontak,
            @Field("lokasi") String lokasi,
            @Field("jk") String jk
    );

    @FormUrlEncoded
    @POST("update_guide_rating.php")
    Call<ResponseBody> update_guide_rating(
            @Query("id") int id,
            @Field("rating") String rating,
            @Field("jmh_rating") int jmh_rating,
            @Field("jmh_num") String jmh_num
    );

    @GET("delete_guide.php")
    Call<ResponseBody> delete_guide(@Query("id") int id);

    @Multipart
    @POST("upload_foto_guide.php")
    Call<ResponseBody> upload_foto_guide(@Query("id") int id, @Part MultipartBody.Part foto);

    @Multipart
    @POST("upload_vidio.php")
    Call<ResponseBody> upload_vidio(@Query("id") int id, @Part MultipartBody.Part foto);


    @GET("view_destinasi.php")
    Call<List<Destinasi>> view_destinansi(@Query("jenis") String jenis);

    @FormUrlEncoded
    @POST("add_destinasi.php")
    Call<ResponseBody> add_destinasi(
            @Field("nama") String nama,
            @Field("jenis") String jenis,
            @Field("lokasi") String lokasi,
            @Field("keterangan") String keterangan,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("update_destinasi.php")
    Call<ResponseBody> update_destinasi(
            @Query("id") int id,
            @Field("nama") String nama,
            @Field("jenis") String jenis,
            @Field("lokasi") String lokasi,
            @Field("keterangan") String keterangan
    );


    @GET("delete_destinasi.php")
    Call<ResponseBody> delete_destinasi(@Query("id") int id);

    @Multipart
    @POST("upload_foto_destinasi.php")
    Call<ResponseBody> upload_foto_destinasi(@Query("id") int id, @Part MultipartBody.Part foto);

    @Multipart
    @POST("upload_vidio_destinasi.php")
    Call<ResponseBody> upload_vidio_destinasi(@Query("id") int id, @Part MultipartBody.Part foto);

}
