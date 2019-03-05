package com.ari.bokingguide.boking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.utils.InternetConnection;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoTransaksiBiayaActivity extends AppCompatActivity {

    private DataService nService;
    private ProgressDialog prgDialog;

    public BoTransaksiBiayaActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transaksi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.checkConnection(BoTransaksiBiayaActivity.this)) {
                    btnSimpan();
                } else {
                    Toast.makeText(BoTransaksiBiayaActivity.this, getString(R.string.jaringan), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnSimpan() {
        prgDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();

//        Call<ResponseBody> call = nService.add_wisatawan(nama, umur, agama, bahasa, jk, kontak, foto, nama_guide, tglMulai, tglAkhir);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    prgDialog.dismiss();
//                    Toast.makeText(BoTransaksiBiayaActivity.this, getString(R.string.simpan_berhasil), Toast.LENGTH_LONG).show();
//                } else {
//                    prgDialog.dismiss();
//                    Toast.makeText(BoTransaksiBiayaActivity.this, getString(R.string.simpan_gagal), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
//                prgDialog.dismiss();
//                Log.e("ERRR", t.getMessage());
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
