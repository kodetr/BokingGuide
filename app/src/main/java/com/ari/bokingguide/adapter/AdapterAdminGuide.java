package com.ari.bokingguide.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.models.Guide;
import com.ari.bokingguide.network.models.Wisatawan;
import com.ari.bokingguide.utils.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterAdminGuide extends RecyclerView.Adapter<AdapterAdminGuide.Holder> {

    private final MClickListener nListener;
    private List<Guide> lguide;

    public AdapterAdminGuide(MClickListener listener) {
        lguide = new ArrayList<>();
        nListener = listener;
    }

    @Override
    public int getItemCount() {
        return lguide.size();
    }

    @Override
    public Holder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(@NotNull Holder holder, int position) {
        Guide getGuide = lguide.get(position);
        holder.tvNama.setText(getGuide.getNama());
        holder.tvUmur.setText(String.valueOf(getGuide.getUmur()));
        holder.tvAgama.setText(getGuide.getAgama());
        holder.tvBahasa.setText(getGuide.getBahasa());
        holder.tvKelamin.setText(getGuide.getJk());
        holder.tvKontak.setText(getGuide.getKontak());
        holder.tvLokasi.setText(getGuide.getLokasi());
        holder.rating.setRating(Integer.valueOf(getGuide.getRating()));
        Glide.with(holder.itemView.getContext())
                .load(getGuide.getFoto())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(holder.ivFoto);
    }

    public void addGuide(Guide guide) {
        lguide.add(guide);
        notifyDataSetChanged();
    }

    public Guide getGuide(int position) {
        return lguide.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNama, tvUmur, tvAgama, tvBahasa, tvKelamin, tvKontak, tvLokasi;
        private RatingBar rating;
        private CircleImageView ivFoto;

        public Holder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvUmur = itemView.findViewById(R.id.tvUmur);
            tvAgama = itemView.findViewById(R.id.tvAgama);
            tvBahasa = itemView.findViewById(R.id.tvBahasa);
            tvKontak = itemView.findViewById(R.id.tvKontak);
            tvKelamin = itemView.findViewById(R.id.tvKelamin);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            rating = itemView.findViewById(R.id.rating);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            rating.setFocusable(false);
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

    public void setFilter(List<Guide> guideModels) {
        lguide = new ArrayList<>();
        lguide.addAll(guideModels);
        notifyDataSetChanged();
    }
}