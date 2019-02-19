package com.ari.bokingguide.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.ari.bokingguide.adapter.AdapterAdminGuide;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Guide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentGuide extends Fragment implements
        AdapterAdminGuide.MClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DataService nService;
    private Guide guide;
    private AdapterAdminGuide adapterAdminGuide;
    private List<Guide> guideList;
    private ProgressDialog prgDialog;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView mSearchView = null;
    private MenuItem mSearchItem;

    public FragmentGuide() {
        DataProvider provider = new DataProvider();
        nService = provider.getTService();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);
        prgDialog = new ProgressDialog(getContext());
        prgDialog.setMessage("Tunggu sebentar...!!!");
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
        return v;
    }

    @Override
    public void onRefresh() {
        showData();
    }

    private void showData() {
        swipeRefreshLayout.setRefreshing(true);
        configRecycleView();
        Call<List<Guide>> call = nService.view_guide();
        call.enqueue(new Callback<List<Guide>>() {
                         @Override
                         public void onResponse(@NotNull Call<List<Guide>> call, @NotNull Response<List<Guide>> response) {
                             if (response.isSuccessful()) {
                                 guideList = response.body();
                                 for (int i = 0; i < guideList.size(); i++) {
                                     guide = guideList.get(i);
                                     adapterAdminGuide.addGuide(guide);

                                 }
                             }
                             swipeRefreshLayout.setRefreshing(false);
                         }

                         @Override
                         public void onFailure(@NotNull Call<List<Guide>> call, @NotNull Throwable t) {
                             prgDialog.dismiss();
                             Log.e("HHHHHHH",t.getMessage());
                         }
                     }
        );
    }

    public void configRecycleView() {
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapterAdminGuide = new AdapterAdminGuide(this);
        mRecycleView.setAdapter(adapterAdminGuide);
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
                final List<Guide> filteredModelList = filter(guideList, s);
                adapterAdminGuide.setFilter(filteredModelList);

                if (filteredModelList.size() == 0) {
                    Toast.makeText(getContext(), "Data tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                final List<Guide> filteredModelList = filter(guideList, s);
                adapterAdminGuide.setFilter(filteredModelList);
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

    //    Proses klik recycleview
    private Guide selectGuide;

    @Override
    public void onClick(int position) {
        selectGuide = adapterAdminGuide.getGuide(position);
    }
}

