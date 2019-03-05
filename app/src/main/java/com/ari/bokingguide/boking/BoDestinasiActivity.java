package com.ari.bokingguide.boking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.adapter.AdapterAdminDestinasi;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Destinasi;
import com.ari.bokingguide.utils.InternetConnection;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoDestinasiActivity extends AppCompatActivity implements
        AdapterAdminDestinasi.MClickListener, SwipeRefreshLayout.OnRefreshListener {

    private String[] spdestinasi = {"Air Tejun", "Pantai", "Bukit", "Pegunungan"};
    private String dtadestinasi;
    private DataService nService;
    private Destinasi destinasi;
    private AdapterAdminDestinasi adapterAdminDestinasi;
    private List<Destinasi> destinasiList;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SimpleDateFormat sdf;

    @SuppressLint("SimpleDateFormat")
    public BoDestinasiActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_destinasi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Destinasi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecycleView = findViewById(R.id.recylerview);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.white));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        showData();
                                    }
                                }
        );

        Spinner spDestinasi = findViewById(R.id.spDestinasi);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spdestinasi);
        spDestinasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dtadestinasi = spdestinasi[i];
                showData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spDestinasi.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        showData();
    }

    private void showData() {
        swipeRefreshLayout.setRefreshing(true);
        if (InternetConnection.checkConnection(this)) {
            configRecycleView();
            Call<List<Destinasi>> call = nService.view_destinansi(dtadestinasi);
            call.enqueue(new Callback<List<Destinasi>>() {
                             @Override
                             public void onResponse(@NotNull Call<List<Destinasi>> call, @NotNull Response<List<Destinasi>> response) {
                                 if (response.isSuccessful()) {
                                     destinasiList = response.body();
                                     for (int i = 0; i < destinasiList.size(); i++) {
                                         destinasi = destinasiList.get(i);
                                         adapterAdminDestinasi.addDestinasi(destinasi);
                                     }
                                     swipeRefreshLayout.setRefreshing(false);
                                 }
                             }

                             @Override
                             public void onFailure(@NotNull Call<List<Destinasi>> call, @NotNull Throwable t) {
                                 Log.e("HHHHHHH", t.getMessage());
                             }
                         }
            );
        } else {
            Toast.makeText(this, getString(R.string.jaringan), Toast.LENGTH_SHORT).show();
        }
    }

    public void configRecycleView() {
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterAdminDestinasi = new AdapterAdminDestinasi(this);
        mRecycleView.setAdapter(adapterAdminDestinasi);
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

    @Override
    public void onClick(int position) {
        final Destinasi selectDestinasi = adapterAdminDestinasi.getDestinasi(position);
        Intent intent = new Intent(this, BoTransaksiBiayaActivity.class);

        try {
            Date dateMulai = sdf.parse(getIntent().getStringExtra("tglMulai"));
            Date dateAkhir = sdf.parse(getIntent().getStringExtra("tglAkhir"));
            int days = Days.daysBetween(new LocalDate(dateMulai), new LocalDate(dateAkhir)).getDays();
            intent.putExtra("jumlah_hari", days);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intent.putExtra("nama", getIntent().getStringExtra("nama"));
        intent.putExtra("umur", getIntent().getIntExtra("umur", 0));
        intent.putExtra("bahasa", getIntent().getStringExtra("bahasa"));
        intent.putExtra("kontak", getIntent().getStringExtra("kontak"));
        intent.putExtra("nama_guide", getIntent().getStringExtra("nama_guide"));
        intent.putExtra("tglMulai", getIntent().getStringExtra("tglMulai"));
        intent.putExtra("tglAkhir", getIntent().getStringExtra("tglAkhir"));
        intent.putExtra("jk", getIntent().getStringExtra("jk"));
        intent.putExtra("foto", getIntent().getStringExtra("foto"));
        intent.putExtra("agama", getIntent().getStringExtra("agama"));
        intent.putExtra("tujuan_wisatawan", selectDestinasi.getLokasi());
        startActivity(intent);
    }
}
