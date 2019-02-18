package com.ari.bokingguide.network;

import com.ari.bokingguide.network.models.Guide;
import com.ari.bokingguide.network.models.Login;
import com.ari.bokingguide.network.models.Wisatawan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {

    @GET("login.php")
    Call<List<Login>> login(@Query("username") String username, @Query("password") String password);

    @GET("view_wisatawan.php")
    Call<List<Wisatawan>> view_wisatawan();

    @GET("guide.php")
    Call<List<Guide>> view_guide();


//    @GET("kelompok_ternak.php")
//    Call<List<Kelompok>> getKelompok(@Query("jenis_ternak") String jenis_ternak);
//

//    @FormUrlEncoded
//    @POST("simpan_ternak_besar.php")
//    Call<ResponseBody> SimpanTernakBesar(
//            @Field("id_kelompok") int id_kelompok,
//            @Field("anak_jantan") int anak_jantan,
//            @Field("anak_betina") int anak_betina,
//            @Field("muda_jantan") int muda_jantan,
//            @Field("muda_betina") int muda_betina,
//            @Field("dewasa_jantan") int dewasa_jantan,
//            @Field("dewasa_betina") int dewasa_betina,
//            @Field("jantan") int jantan,
//            @Field("betina") int betina,
//            @Field("jumlah") int jumlah);
//
//    @FormUrlEncoded
//    @POST("simpan_ternak_sedang.php")
//    Call<ResponseBody> SimpanTernakSedang(
//            @Field("id_kelompok") int id_kelompok,
//            @Field("jantan") int jantan,
//            @Field("betina") int betina,
//            @Field("jumlah") int jumlah);
//
//    @FormUrlEncoded
//    @POST("simpan_ternak_kecil.php")
//    Call<ResponseBody> SimpanTernakKecil(@Field("id_kelompok") int id_kelompok, @Field("jumlah") int jumlah);
//
//    @GET("grafik_pertahun.php")
//    Call<List<Ternak>> GrafikPertahun();

}
