package com.ari.bokingguide;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.utils.InternetConnection;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDestinasiActivity extends AppCompatActivity {

    private String[] spdestinasi = {"Air Tejun", "Pantai", "Bukit", "Pegunungan"};
    private EditText etNama, etKeterangan, etLokasi;
    private Spinner spJenis;

    private DataService nService;
    private ProgressDialog prgDialog;
    private int tagBtn = 0;

    public AddDestinasiActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destinasi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Destinasi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        if (getIntent().getBooleanExtra("tag", true)) {
            etNama.setText(getIntent().getStringExtra("nama"));
            etLokasi.setText(getIntent().getStringExtra("lokasi"));
            etKeterangan.setText(getIntent().getStringExtra("keterangan"));
            tagBtn = 1;
        } else {
            tagBtn = 0;
        }
    }

    private void init() {
        etNama = findViewById(R.id.etNama);
        etLokasi = findViewById(R.id.etLokasi);
        etKeterangan = findViewById(R.id.etKeterangan);
        spJenis = findViewById(R.id.spJenis);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spdestinasi);
        spJenis.setAdapter(arrayAdapter);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetConnection.checkConnection(AddDestinasiActivity.this)) {
                    validasiBtnSimpan();
                }else{
                    Toast.makeText(AddDestinasiActivity.this, getString(R.string.jaringan), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validasiBtnSimpan() {
        if (TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etLokasi.getText().toString())
                || TextUtils.isEmpty(etKeterangan.getText().toString())) {

            if (TextUtils.isEmpty(etNama.getText().toString())) {
                etNama.setError(getString(R.string.pesan_nama));
            }
            if (TextUtils.isEmpty(etLokasi.getText().toString())) {
                etLokasi.setError(getString(R.string.pesan_umur));
            }
            if (TextUtils.isEmpty(etKeterangan.getText().toString())) {
                etKeterangan.setError(getString(R.string.pesan_bahasa));
            }

        } else {
            if (tagBtn == 0) {
                btnSimpan();
            } else {
                btnUpdate();
            }
        }

    }

    private void btnSimpan() {
        prgDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();

        String nama = etNama.getText().toString();
        String lokasi = etLokasi.getText().toString();
        String keterangan = etKeterangan.getText().toString();

        String jenis = spJenis.getSelectedItem().toString();
        String foto = "default/default.png";

        Call<ResponseBody> call = nService.add_destinasi(nama, jenis, lokasi, keterangan, foto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    prgDialog.dismiss();
                    Toast.makeText(AddDestinasiActivity.this, getString(R.string.simpan_berhasil), Toast.LENGTH_LONG).show();
                } else {
                    prgDialog.dismiss();
                    Toast.makeText(AddDestinasiActivity.this, getString(R.string.simpan_gagal), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                prgDialog.dismiss();
                Log.e("ERRR", t.getMessage());
            }
        });
    }

    private void btnUpdate() {
        prgDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();

        String nama = etNama.getText().toString();
        String lokasi = etLokasi.getText().toString();
        String keterangan = etKeterangan.getText().toString();
        String jenis = spJenis.getSelectedItem().toString();

        Call<ResponseBody> call = nService.update_destinasi(getIntent().getIntExtra("id", 0), nama, jenis, lokasi, keterangan);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    prgDialog.dismiss();
                    Toast.makeText(AddDestinasiActivity.this, getString(R.string.ubah_berhasil), Toast.LENGTH_LONG).show();
                } else {
                    prgDialog.dismiss();
                    Toast.makeText(AddDestinasiActivity.this, getString(R.string.ubah_gagal), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                prgDialog.dismiss();
                Log.e("ERRR", t.getMessage());
            }
        });
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
