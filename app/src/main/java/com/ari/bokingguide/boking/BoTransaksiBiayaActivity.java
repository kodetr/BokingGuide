package com.ari.bokingguide.boking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.utils.InternetConnection;

import java.text.DecimalFormat;

public class BoTransaksiBiayaActivity extends AppCompatActivity {

    private DataService nService;
    private ProgressDialog prgDialog;
    private TextView tvBiaya;

    public BoTransaksiBiayaActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transaksi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvBiaya = findViewById(R.id.tvBiaya);


        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        String biaya = String.valueOf(getIntent().getIntExtra("jumlah_hari", 0) * 450000);
        tvBiaya.setText("Rp." + formatter.format(Double.parseDouble(biaya)));

        init();
    }

    private void init() {
        Button btnConfirmasi = findViewById(R.id.btnConfirmasi);
        btnConfirmasi.setOnClickListener(new View.OnClickListener() {
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
//        prgDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
//        prgDialog.setMessage("Tunggu sebentar...!!!");
//        prgDialog.setCancelable(false);
//        prgDialog.show();
//
//        String nama = getIntent().getStringExtra("nama");
//        int umur = getIntent().getIntExtra("umur", 0);
//        String bahasa = getIntent().getStringExtra("bahasa");
//        String kontak = getIntent().getStringExtra("kontak");
//        String nama_guide = getIntent().getStringExtra("nama_guide");
        String tglMulai = getIntent().getStringExtra("tglMulai");
        String tglAkhir = getIntent().getStringExtra("tglAkhir");

//        String jk = getIntent().getStringExtra("jk");
//        String foto = getIntent().getStringExtra("foto");
//        String agama = getIntent().getStringExtra("agama");
//        String tujuan_wisatawan = getIntent().getStringExtra("tujuan_wisatawan");
//        String biaya = "Rp." + tvBiaya.getText().toString();
//
//        Call<ResponseBody> call = nService.add_wisatawan(nama, umur, agama, bahasa, jk, kontak, foto, nama_guide, tglMulai, tglAkhir, tujuan_wisatawan, biaya, 1);
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
