package com.ari.bokingguide.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.ViewDestinasiActivity;
import com.ari.bokingguide.adapter.AdapterAdminWisatawan;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Wisatawan;
import com.ari.bokingguide.utils.InternetConnection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWisatawan extends Fragment implements
        AdapterAdminWisatawan.MClickListener, SwipeRefreshLayout.OnRefreshListener {

    private String[] dialogitem = {"Status", "Hapus"};
    private DataService nService;
    private Wisatawan wisatawan;
    private AdapterAdminWisatawan adapterAdminWisatawan;
    private List<Wisatawan> wisatawanList;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView mSearchView = null;
    private MenuItem mSearchItem;
    private ProgressDialog prgDialog;

    public FragmentWisatawan() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);
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

        FloatingActionButton btnAdd = v.findViewById(R.id.btnAdd);
        btnAdd.setVisibility(View.GONE);

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
            Call<List<Wisatawan>> call = nService.view_wisatawan();
            call.enqueue(new Callback<List<Wisatawan>>() {
                             @Override
                             public void onResponse(@NotNull Call<List<Wisatawan>> call, @NotNull Response<List<Wisatawan>> response) {
                                 if (response.isSuccessful()) {
                                     wisatawanList = response.body();
                                     for (int i = 0; i < wisatawanList.size(); i++) {
                                         wisatawan = wisatawanList.get(i);
                                         if (wisatawan.isPesan()) {
                                             Toast.makeText(getContext(), "Belum ada aktifitas", Toast.LENGTH_SHORT).show();
                                         } else {
                                             adapterAdminWisatawan.addWisatawan(wisatawan);
                                         }
                                     }
                                     swipeRefreshLayout.setRefreshing(false);
                                 }
                             }

                             @Override
                             public void onFailure(@NotNull Call<List<Wisatawan>> call, @NotNull Throwable t) {
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
        adapterAdminWisatawan = new AdapterAdminWisatawan(this);
        mRecycleView.setAdapter(adapterAdminWisatawan);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_admin, menu);
        mSearchItem = menu.findItem(R.id.action_search);
        if (mSearchItem != null) {
            mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
            initSearchView();
        }
    }

    private void initSearchView() {
        mSearchView.clearFocus();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();
                final List<Wisatawan> filteredModelList = filter(wisatawanList, s);
                adapterAdminWisatawan.setFilter(filteredModelList);

                if (filteredModelList.size() == 0) {
                    Toast.makeText(getContext(), "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                final List<Wisatawan> filteredModelList = filter(wisatawanList, s);
                adapterAdminWisatawan.setFilter(filteredModelList);
                if (filteredModelList.size() == 0) {
                    Toast.makeText(getContext(), "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
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

    private List<Wisatawan> filter(List<Wisatawan> models, String query) {
        query = query.toLowerCase();

        final List<Wisatawan> filteredModelList = new ArrayList<>();
        for (Wisatawan model : models) {
            final String nama = model.getNama().toLowerCase();
            final String umur = String.valueOf(model.getUmur()).toLowerCase();
            final String agama = model.getAgama().toLowerCase();
            final String bahasa = model.getBahasa().toLowerCase();
            final String kontak = model.getKontak().toLowerCase();
            if (nama.contains(query) || umur.contains(query) || agama.contains(query)
                    || bahasa.contains(query) || kontak.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    // Proses klik recycleview
    private Wisatawan selectedwisatawan;

    @Override
    public void onClick(int position) {
        selectedwisatawan = adapterAdminWisatawan.getWisatawan(position);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        AlertDialog.Builder status = new AlertDialog.Builder(getActivity())
                                .setTitle("Yakin ingin disetujui!")
                                .setPositiveButton("Iya",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                prosesStatus(selectedwisatawan.getId(), selectedwisatawan.getId_guide());
                                            }
                                        }
                                )
                                .setNegativeButton("Tidak",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.dismiss();
                                            }
                                        }
                                );
                        status.show();
                        break;
                    case 1:
                        delete(selectedwisatawan.getId());
                        break;
                }
            }
        });

        builder.show();
    }

    private void prosesStatus(int id, int id_guide) {
        prgDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();

        prosesTagGuide(id_guide);

        Call<ResponseBody> call = nService.update_status_wisatawan(id, 1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    prgDialog.dismiss();
                    Toast.makeText(getContext(), getString(R.string.proses_berhasil), Toast.LENGTH_LONG).show();
                    showData();
                } else {
                    prgDialog.dismiss();
                    Toast.makeText(getContext(), getString(R.string.proses_gagal), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                prgDialog.dismiss();
                Log.e("ERRR", t.getMessage());
            }
        });
    }

    private void prosesTagGuide(int id) {
        Call<ResponseBody> call = nService.update_guide(id, 1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
            }
        });
    }

    private void delete(int id) {
        prgDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        prgDialog.setMessage("Tunggu sebentar...!!!");
        prgDialog.setCancelable(false);
        prgDialog.show();

        Call<ResponseBody> call = nService.delete_wisatawan(id);
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
