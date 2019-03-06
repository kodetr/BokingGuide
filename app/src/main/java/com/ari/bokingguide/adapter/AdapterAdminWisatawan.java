package com.ari.bokingguide.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.models.Wisatawan;
import com.ari.bokingguide.utils.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterAdminWisatawan extends RecyclerView.Adapter<AdapterAdminWisatawan.Holder> {

    private final MClickListener nListener;
    private List<Wisatawan> lwisatawan;
    private SimpleDateFormat sdf;

    @SuppressLint("SimpleDateFormat")
    public AdapterAdminWisatawan(MClickListener listener) {
        lwisatawan = new ArrayList<>();
        nListener = listener;
        sdf = new SimpleDateFormat("dd-MM-yyyy");
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull Holder holder, int position) {
        Wisatawan getWisatawan = lwisatawan.get(position);
        holder.tvNama.setText(getWisatawan.getNama());
        holder.tvUmur.setText(String.valueOf(getWisatawan.getUmur()));
        holder.tvAgama.setText(getWisatawan.getAgama());
        holder.tvBahasa.setText(getWisatawan.getBahasa());
        holder.tvKelamin.setText(getWisatawan.getJk());
        holder.tvKontak.setText(getWisatawan.getKontak());
        holder.tvTujuanWisata.setText(getWisatawan.getTujuan_wisata());
        holder.tvTglMulai.setText(getWisatawan.getTgl_mulai());
        holder.tvTglAkhir.setText(getWisatawan.getTgl_akhir());
        holder.tvBiaya.setText(getWisatawan.getBiaya());

        try {
            Date dateMulai = sdf.parse(getWisatawan.getTgl_mulai());
            Date dateAkhir = sdf.parse(getWisatawan.getTgl_akhir());
            int days = Days.daysBetween(new LocalDate(dateMulai), new LocalDate(dateAkhir)).getDays();
            holder.tvBatasWaktu.setText(String.valueOf(days) + " Hari Lagi");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(holder.itemView.getContext())
                .load(getWisatawan.getFoto())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(holder.ivFoto);

        if (getWisatawan.getStatus() == 1) {
            holder.ivStatus.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_status_proses));
        } else {
            holder.ivStatus.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_status_berhasil));
        }
    }

    public void addWisatawan(Wisatawan wisatawan) {
        lwisatawan.add(wisatawan);
        notifyDataSetChanged();
    }

    public Wisatawan getWisatawan(int position) {
        return lwisatawan.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNama, tvUmur, tvAgama, tvBahasa, tvKelamin, tvKontak, tvTujuanWisata, tvTglMulai, tvTglAkhir, tvBiaya, tvBatasWaktu;
        private CircleImageView ivFoto;
        private ImageView ivStatus;

        public Holder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvUmur = itemView.findViewById(R.id.tvUmur);
            tvAgama = itemView.findViewById(R.id.tvAgama);
            tvBahasa = itemView.findViewById(R.id.tvBahasa);
            tvKontak = itemView.findViewById(R.id.tvKontak);
            tvKelamin = itemView.findViewById(R.id.tvKelamin);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            tvTujuanWisata = itemView.findViewById(R.id.tvTujuanWisata);
            tvTglMulai = itemView.findViewById(R.id.tvTglMulai);
            tvTglAkhir = itemView.findViewById(R.id.tvTglAkhir);
            tvBiaya = itemView.findViewById(R.id.tvBiaya);
            tvBatasWaktu = itemView.findViewById(R.id.tvBatasWaktu);
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