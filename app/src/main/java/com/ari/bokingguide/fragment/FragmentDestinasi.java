package com.ari.bokingguide.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ari.bokingguide.AddDestinasiActivity;
import com.ari.bokingguide.R;
import com.ari.bokingguide.UploadDestinasiActivity;
import com.ari.bokingguide.UploadVidioDestinasiActivity;
import com.ari.bokingguide.adapter.AdapterAdminDestinasi;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Destinasi;
import com.ari.bokingguide.utils.InternetConnection;

import org.jetbrains.annotations.NotNull;
import org.salient.artplayer.VideoView;
import org.salient.artplayer.ui.ControlPanel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDestinasi extends Fragment implements
        AdapterAdminDestinasi.MClickListener, SwipeRefreshLayout.OnRefreshListener {

    private String[] dialogitem = {"Ganti Foto", "Tambah Vidio", "Lihat Vidio", "Ubah", "Hapus"};
    private String[] spdestinasi = {"Air Tejun", "Pantai", "Bukit", "Pegunungan"};
    private String dtadestinasi;
    private DataService nService;
    private Destinasi destinasi;
    private AdapterAdminDestinasi adapterAdminDestinasi;
    private List<Destinasi> destinasiList;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog prgDialog;

    public FragmentDestinasi() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destinasi, container, false);
        mRecycleView = v.findViewById(R.id.recylerview);
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
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

        Spinner spDestinasi = v.findViewById(R.id.spDestinasi);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, spdestinasi);
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

        FloatingActionButton btnAdd = v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itag = new Intent(getContext(), AddDestinasiActivity.class);
                itag.putExtra("tag", false);
                startActivity(itag);
            }
        });
        return v;
    }

    @Override
    public void onRefresh() {
        showData();
    }

    private void showData() {
        swipeRefreshLayout.setRefreshing(true);
        if (InternetConnection.checkConnection(getContext())) {
            configRecycleView();
            Call<List<Destinasi>> call = nService.view_destinansi(dtadestinasi);
            call.enqueue(new Callback<List<Destinasi>>() {
                             @Override
                             public void onResponse(@NotNull Call<List<Destinasi>> call, @NotNull Response<List<Destinasi>> response) {
                                 if (response.isSuccessful()) {
                                     destinasiList = response.body();
                                     for (int i = 0; i < destinasiList.size(); i++) {
                                         destinasi = destinasiList.get(i);
                                         if (destinasi.isPesan()) {
                                             Toast.makeText(getContext(), "Belum ada aktifitas", Toast.LENGTH_SHORT).show();
                                         } else {
                                             adapterAdminDestinasi.addDestinasi(destinasi);
                                         }
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
            Toast.makeText(getContext(), getString(R.string.jaringan), Toast.LENGTH_SHORT).show();
        }
    }

    public void configRecycleView() {
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapterAdminDestinasi = new AdapterAdminDestinasi(this);
        mRecycleView.setAdapter(adapterAdminDestinasi);
    }

    // Proses klik recycleview
    private Destinasi selectedDestinasi;

    @Override
    public void onClick(int position) {
        selectedDestinasi = adapterAdminDestinasi.getDestinasi(position);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Intent ifoto = new Intent(getContext(), UploadDestinasiActivity.class);
                        ifoto.putExtra("id", selectedDestinasi.getId());
                        startActivity(ifoto);
                        break;
                    case 1:
                        Intent ividio = new Intent(getContext(), UploadVidioDestinasiActivity.class);
                        ividio.putExtra("id", selectedDestinasi.getId());
                        startActivity(ividio);
                        break;
                    case 2:
                        VideoView videoView = new VideoView(getContext());
                        videoView.setUp(selectedDestinasi.getVidio());
                        videoView.setControlPanel(new ControlPanel(getContext()));
                        videoView.start();
                        videoView.startFullscreen(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                        break;
                    case 3:
                        Intent iupdate = new Intent(getContext(), AddDestinasiActivity.class);
                        iupdate.putExtra("id", selectedDestinasi.getId());
                        iupdate.putExtra("nama", selectedDestinasi.getNama());
                        iupdate.putExtra("jenis", selectedDestinasi.getJenis());
                        iupdate.putExtra("lokasi", selectedDestinasi.getLokasi());
                        iupdate.putExtra("keterangan", selectedDestinasi.getKeterangan());
                        iupdate.putExtra("tag", true);
                        startActivity(iupdate);
                        break;
                    case 4:
                        delete(selectedDestinasi.getId());
                        break;
                }
            }
        });

        builder.show();
    }

    private void delete(int id) {
        prgDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();

        Call<ResponseBody> call = nService.delete_destinasi(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    prgDialog.dismiss();
                    Toast.makeText(getContext(), getString(R.string.hapus_berhasil), Toast.LENGTH_LONG).show();
                    showData();
                } else {
                    prgDialog.dismiss();
                    Toast.makeText(getContext(), getString(R.string.hapus_gagal), Toast.LENGTH_LONG).show();
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
