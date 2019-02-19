package com.ari.bokingguide.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.models.Wisatawan;
import com.ari.bokingguide.utils.CircleImageView;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterAdminWisatawan extends RecyclerView.Adapter<AdapterAdminWisatawan.Holder> {

    private final MClickListener nListener;
    private List<Wisatawan> lwisatawan;

    public AdapterAdminWisatawan(MClickListener listener) {
        lwisatawan = new ArrayList<>();
        nListener = listener;
    }

    @Override
    public int getItemCount() {
        return lwisatawan.size();
    }

    @Override
    public Holder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wisatawan, parent, false);
        return new Holder(row);
    }


    @Override
    public void onBindViewHolder(@NotNull Holder holder, int position) {
        Wisatawan getWisatawan = lwisatawan.get(position);
        holder.tvNama.setText(getWisatawan.getNama());
        holder.tvUmur.setText(String.valueOf(getWisatawan.getUmur()));
        holder.tvAgama.setText(getWisatawan.getAgama());
        holder.tvBahasa.setText(getWisatawan.getBahasa());
        holder.tvKelamin.setText(getWisatawan.getJk());
        holder.tvKontak.setText(getWisatawan.getKontak());
        Glide.with(holder.itemView.getContext())
                .load(getWisatawan.getFoto())
                .into(holder.ivFoto);
    }

    public void addWisatawan(Wisatawan wisatawan) {
        lwisatawan.add(wisatawan);
        notifyDataSetChanged();
    }

    public Wisatawan getWisatawan(int position) {
        return lwisatawan.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNama, tvUmur, tvAgama, tvBahasa, tvKelamin, tvKontak;
        private CircleImageView ivFoto;

        public Holder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvUmur = itemView.findViewById(R.id.tvUmur);
            tvAgama = itemView.findViewById(R.id.tvAgama);
            tvBahasa = itemView.findViewById(R.id.tvBahasa);
            tvKontak = itemView.findViewById(R.id.tvKontak);
            tvKelamin = itemView.findViewById(R.id.tvKelamin);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            nListener.onClick(getLayoutPosition());
        }
    }

    public interface MClickListener {
        void onClick(int position);
    }

    public void setFilter(List<Wisatawan> wisatawanModels) {
        lwisatawan = new ArrayList<>();
        lwisatawan.addAll(wisatawanModels);
        notifyDataSetChanged();
    }
}