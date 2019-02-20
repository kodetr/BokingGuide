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
import com.ari.bokingguide.utils.CircleImageView;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGuideActivity extends AppCompatActivity {

    private String[] data_agama = {"Islam", "Hindu", "Kristen", "Katolik", "Ateisme"};
    private EditText etNama, etUmur, etBahasa, etKontak;
    private Spinner spAgama;
    private RadioGroup rgKelamin;
    private CircleImageView ivFoto;

    private DataService nService;
    private ProgressDialog prgDialog;

    public AddGuideActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wisatawan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Wisatawan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        etNama = findViewById(R.id.etNama);
        etUmur = findViewById(R.id.etUmur);
        etBahasa = findViewById(R.id.etBahasa);
        etKontak = findViewById(R.id.etKontak);
        rgKelamin = findViewById(R.id.rgKelamin);
        spAgama = findViewById(R.id.spAgama);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data_agama);
        spAgama.setAdapter(arrayAdapter);
        ivFoto = findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiBtnSimpan();
            }
        });
    }

    private void validasiBtnSimpan() {
        if (TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etUmur.getText().toString())
                || TextUtils.isEmpty(etBahasa.getText().toString()) || TextUtils.isEmpty(etKontak.getText().toString())) {
            if (TextUtils.isEmpty(etNama.getText().toString())) {
                etNama.setError(getString(R.string.pesan_nama));
            }
            if (TextUtils.isEmpty(etUmur.getText().toString())) {
                etUmur.setError(getString(R.string.pesan_umur));
            }
            if (TextUtils.isEmpty(etBahasa.getText().toString())) {
                etBahasa.setError(getString(R.string.pesan_bahasa));
            }
            if (TextUtils.isEmpty(etKontak.getText().toString())) {
                etKontak.setError(getString(R.string.pesan_kontak));
            }
        } else {
            btnSimpan();
        }

    }

    private void btnSimpan() {
        prgDialog = ProgressDialog.show(AddGuideActivity.this, "Proses Data", "Tunggu sebentar..!", false, false);

        String nama = etNama.getText().toString();
        int umur = Integer.valueOf(etUmur.getText().toString());
        String bahasa = etBahasa.getText().toString();
        String kontak = etKontak.getText().toString();

        int id = rgKelamin.getCheckedRadioButtonId();
        RadioButton rbjk = findViewById(id);
        String jk = rbjk.getText().toString();

        String agama = spAgama.getSelectedItem().toString();
        String foto = "default.png";

        prgDialog.show();
        Call<ResponseBody> call = nService.add_wisatawan(nama, umur, agama, bahasa, jk, kontak, foto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    prgDialog.dismiss();
                    Toast.makeText(AddGuideActivity.this, getString(R.string.simpan_berhasil), Toast.LENGTH_LONG).show();
                } else {
                    prgDialog.dismiss();
                    Toast.makeText(AddGuideActivity.this, getString(R.string.simpan_gagal), Toast.LENGTH_LONG).show();
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
