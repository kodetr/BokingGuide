package com.ari.bokingguide.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.models.Destinasi;
import com.ari.bokingguide.network.models.Guide;
import com.ari.bokingguide.utils.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterAdminDestinasi extends RecyclerView.Adapter<AdapterAdminDestinasi.Holder> {

    private final MClickListener nListener;
    private List<Destinasi> ldestinasi;

    public AdapterAdminDestinasi(MClickListener listener) {
        ldestinasi = new ArrayList<>();
        nListener = listener;
    }

    @Override
    public int getItemCount() {
        return ldestinasi.size();
    }

    @Override
    public Holder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_destinasi, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(@NotNull Holder holder, int position) {
        Destinasi getDestinasi = ldestinasi.get(position);
        holder.tvNama.setText(getDestinasi.getNama());
        holder.tvJenis.setText(getDestinasi.getJenis());
        holder.tvLokasi.setText(getDestinasi.getLokasi());
        holder.tvKeterangan.setText(getDestinasi.getKeterangan());
        Glide.with(holder.itemView.getContext())
                .load(getDestinasi.getFoto())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(holder.ivFoto);
    }

    public void addDestinasi(Destinasi destinasi) {
        ldestinasi.add(destinasi);
        notifyDataSetChanged();
    }

    public Destinasi getDestinasi(int position) {
        return ldestinasi.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNama, tvJenis, tvLokasi, tvKeterangan;
        private CircleImageView ivFoto;

        public Holder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJenis = itemView.findViewById(R.id.tvJenis);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            tvKeterangan = itemView.findViewById(R.id.tvKeterangan);
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

}