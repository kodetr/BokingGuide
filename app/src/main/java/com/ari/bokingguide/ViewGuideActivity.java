package com.ari.bokingguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ari.bokingguide.adapter.AdapterGuide;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Guide;
import com.ari.bokingguide.utils.Constants;
import com.ari.bokingguide.utils.InternetConnection;

import org.jetbrains.annotations.NotNull;
import org.salient.artplayer.VideoView;
import org.salient.artplayer.ui.ControlPanel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewGuideActivity extends AppCompatActivity implements
        AdapterGuide.MClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DataService nService;
    private Guide guide;
    private AdapterGuide adapterGuide;
    private List<Guide> guideList;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView mSearchView = null;
    private ProgressDialog prgDialog;
    private boolean tagRating;

    public ViewGuideActivity() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guide);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftar Guide");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_RATING, Context.MODE_PRIVATE);
        tagRating = sharedPreferences.getBoolean(Constants.RATING_PREF, false);

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
    }

    @Override
    public void onRefresh() {
        showData();
    }

    private void showData() {
        swipeRefreshLayout.setRefreshing(true);
        if (InternetConnection.checkConnection(this)) {
            configRecycleView();
            Call<List<Guide>> call = nService.view_guide();
            call.enqueue(new Callback<List<Guide>>() {
                             @Override
                             public void onResponse(@NotNull Call<List<Guide>> call, @NotNull Response<List<Guide>> response) {
                                 if (response.isSuccessful()) {
                                     guideList = response.body();
                                     for (int i = 0; i < guideList.size(); i++) {
                                         guide = guideList.get(i);
                                         adapterGuide.addGuide(guide);
                                     }
                                     swipeRefreshLayout.setRefreshing(false);
                                 }
                             }

                             @Override
                             public void onFailure(@NotNull Call<List<Guide>> call, @NotNull Throwable t) {
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
        adapterGuide = new AdapterGuide(this);
        mRecycleView.setAdapter(adapterGuide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        MenuItem mSearchItem = menu.findItem(R.id.action_search);
        if (mSearchItem != null) {
            mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
            initSearchView();
        }
        return true;
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

    private void initSearchView() {
        mSearchView.clearFocus();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();
                final List<Guide> filteredModelList = filter(guideList, s);
                adapterGuide.setFilter(filteredModelList);

                if (filteredModelList.size() == 0) {
                    Toast.makeText(ViewGuideActivity.this, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                final List<Guide> filteredModelList = filter(guideList, s);
                adapterGuide.setFilter(filteredModelList);
                if (filteredModelList.size() == 0) {
                    Toast.makeText(ViewGuideActivity.this, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showData();
                return false;
            }
        });

    }

    private List<Guide> filter(List<Guide> models, String query) {
        query = query.toLowerCase();

        final List<Guide> filteredModelList = new ArrayList<>();
        for (Guide model : models) {
            final String nama = model.getNama().toLowerCase();
            final String umur = String.valueOf(model.getUmur()).toLowerCase();
            final String agama = model.getAgama().toLowerCase();
            final String bahasa = model.getBahasa().toLowerCase();
            final String kontak = model.getKontak().toLowerCase();
            final String lokasi = model.getLokasi().toLowerCase();
            if (nama.contains(query) || umur.contains(query) || agama.contains(query)
                    || bahasa.contains(query) || kontak.contains(query) || lokasi.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private String[] dialogitem = {"Hubungi", "Boking", "Rating", "Vidio"};
    private Guide selectGuide;

    @Override
    public void onClick(int position) {
        selectGuide = adapterGuide.getGuide(position);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectGuide.getKontak()));
                        startActivity(intent);
                        break;
                    case 1:

                        break;
                    case 2:
                        if (!tagRating) {
                            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_RATING, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Constants.RATING_PREF, true);
                            editor.commit();

                            ShowDialog(selectGuide.getId(), selectGuide.getJmh_rating(), selectGuide.getJmh_num());
                        } else {
                            Toast.makeText(ViewGuideActivity.this, "Sudah melakukan Rating", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        VideoView videoView = new VideoView(ViewGuideActivity.this);
                        videoView.setUp(selectGuide.getVidio());
                        videoView.setControlPanel(new ControlPanel(ViewGuideActivity.this));
                        videoView.start();
                        videoView.startFullscreen(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                        break;
                }
            }
        });
        builder.show();
    }


    private void ShowDialog(final int id, final int jmh_rating, final String num_rating) {
        LayoutInflater myLayout = LayoutInflater.from(this);
        final View dialogView = myLayout.inflate(R.layout.dialog_rating, null);
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar ratingBar = dialogView.findViewById(R.id.dialog_ratingbar);
        popDialog.setTitle("Rating!!");
        popDialog.setView(dialogView);
        popDialog.setPositiveButton("Simpan",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int hasil_jmh_rating = 1 + jmh_rating;
                        double hasil_num_rating = ratingBar.getRating() + Double.valueOf(num_rating);
                        String hasil_rating = String.valueOf(hasil_num_rating / hasil_jmh_rating);

                        update_guide_rate(id, hasil_rating, hasil_jmh_rating, String.valueOf(hasil_num_rating));
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        popDialog.create();
        popDialog.show();
    }

    private void update_guide_rate(int id, String rating, int jmh_rating, String num_rating) {
        prgDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();
        Call<ResponseBody> call = nService.update_guide_rating(id, rating, jmh_rating, num_rating);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showData();
                    prgDialog.dismiss();
                    Toast.makeText(ViewGuideActivity.this, getString(R.string.simpan_berhasil), Toast.LENGTH_LONG).show();
                } else {
                    prgDialog.dismiss();
                    Toast.makeText(ViewGuideActivity.this, getString(R.string.simpan_gagal), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                prgDialog.dismiss();
                Log.e("ERRR", t.getMessage());
            }
        });
    }

}
