package com.ari.bokingguide.boking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.utils.InternetConnection;

import java.util.Calendar;

public class BoWisatawanActivity extends AppCompatActivity {

    private String[] data_agama = {"Islam", "Hindu", "Kristen", "Katolik", "Ateisme"};
    private EditText etNama, etUmur, etBahasa, etKontak, etNamaGuide, etTglMulai, etTglAkhir;
    private Spinner spAgama;
    private RadioGroup rgKelamin;
    private DatePickerDialog TanggalMulai;
    private DatePickerDialog TanggalAkhir;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wisatawan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Registrasi Wisatawan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatePicker();
        init();

        etNamaGuide.setText(getIntent().getStringExtra("nama_guide"));
    }

    private void DatePicker() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        TanggalMulai = new DatePickerDialog(BoWisatawanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        etTglMulai.setText(day + "-" + month + "-" + year);
                    }
                }, year, month, dayOfMonth);

        TanggalAkhir = new DatePickerDialog(BoWisatawanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        etTglAkhir.setText(day + "-" + month + "-" + year);
                    }
                }, year, month, dayOfMonth);
    }

    private void init() {
        etNama = findViewById(R.id.etNama);
        etUmur = findViewById(R.id.etUmur);
        etBahasa = findViewById(R.id.etBahasa);
        etKontak = findViewById(R.id.etKontak);
        rgKelamin = findViewById(R.id.rgKelamin);
        spAgama = findViewById(R.id.spAgama);
        etNamaGuide = findViewById(R.id.etNamaGuide);
        etTglMulai = findViewById(R.id.etTglMulai);
        etTglAkhir = findViewById(R.id.etTglAkhir);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data_agama);
        spAgama.setAdapter(arrayAdapter);

        etTglMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TanggalMulai.show();
            }
        });
        etTglAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TanggalAkhir.show();
            }
        });

        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.checkConnection(BoWisatawanActivity.this)) {
                    validasiBtnSimpan();
                } else {
                    Toast.makeText(BoWisatawanActivity.this, getString(R.string.jaringan), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validasiBtnSimpan() {
        if (TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etUmur.getText().toString())
                || TextUtils.isEmpty(etBahasa.getText().toString()) || TextUtils.isEmpty(etKontak.getText().toString())
                || TextUtils.isEmpty(etNamaGuide.getText().toString()) || TextUtils.isEmpty(etTglMulai.getText().toString()) ||
                TextUtils.isEmpty(etTglAkhir.getText().toString())) {

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
            if (TextUtils.isEmpty(etNamaGuide.getText().toString())) {
                etNamaGuide.setError(getString(R.string.pesan_nama_guide));
            }
            if (TextUtils.isEmpty(etTglMulai.getText().toString())) {
                etTglMulai.setError(getString(R.string.pesan_tgl_mulai));
            }
            if (TextUtils.isEmpty(etTglAkhir.getText().toString())) {
                etTglAkhir.setError(getString(R.string.pesan_tgl_akhir));
            }
        } else {
            btnSimpan();
        }
    }

    private void btnSimpan() {
        String nama = etNama.getText().toString();
        int umur = Integer.valueOf(etUmur.getText().toString());
        String bahasa = etBahasa.getText().toString();
        String kontak = etKontak.getText().toString();
        String nama_guide = etNamaGuide.getText().toString();
        String tglMulai = etTglMulai.getText().toString();
        String tglAkhir = etTglAkhir.getText().toString();

        int id = rgKelamin.getCheckedRadioButtonId();
        RadioButton rbjk = findViewById(id);
        String jk = rbjk.getText().toString();
        String foto = "default/default.png";

        String agama = spAgama.getSelectedItem().toString();

        Intent intent = new Intent(this, BoDestinasiActivity.class);
        intent.putExtra("nama", nama);
        intent.putExtra("umur", umur);
        intent.putExtra("bahasa", bahasa);
        intent.putExtra("kontak", kontak);
        intent.putExtra("nama_guide", nama_guide);
        intent.putExtra("tglMulai", tglMulai);
        intent.putExtra("tglAkhir", tglAkhir);
        intent.putExtra("jk", jk);
        intent.putExtra("foto", foto);
        intent.putExtra("agama", agama);
        intent.putExtra("id_guide", getIntent().getIntExtra("id_guide", 0));
        startActivity(intent);
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
