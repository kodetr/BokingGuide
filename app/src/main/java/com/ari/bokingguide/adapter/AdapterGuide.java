package com.ari.bokingguide.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ari.bokingguide.R;
import com.ari.bokingguide.network.DataProvider;
import com.ari.bokingguide.network.DataService;
import com.ari.bokingguide.network.models.Guide;
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


public class AdapterGuide extends RecyclerView.Adapter<AdapterGuide.Holder> {

    private final MClickListener nListener;
    private List<Guide> lguide;
    private DataService nService;

    public AdapterGuide(MClickListener listener) {
        lguide = new ArrayList<>();
        nListener = listener;

        DataProvider provider = new DataProvider();
        nService = provider.getTService();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull final Holder holder, int position) {
        Guide getGuide = lguide.get(position);
        holder.tvNama.setText(getGuide.getNama());
        holder.tvUmur.setText(String.valueOf(getGuide.getUmur()));
        holder.tvAgama.setText(getGuide.getAgama());
        holder.tvBahasa.setText(getGuide.getBahasa());
        holder.tvKelamin.setText(getGuide.getJk());
        holder.tvKontak.setText(getGuide.getKontak());
        holder.tvLokasi.setText(getGuide.getLokasi());
        holder.tvjmhGuide.setText(String.valueOf(getGuide.getJmh_rating()));
        holder.rating.setRating(Float.valueOf(getGuide.getRating()));
        Glide.with(holder.itemView.getContext())
                .load(getGuide.getFoto())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(holder.ivFoto);

        if (getGuide.getTag() == 0) {
            holder.llContainerBoking.setVisibility(View.GONE);
            holder.containerA.setEnabled(true);
            holder.containerB.setVisibility(View.GONE);
            holder.containerC.setAlpha((float) 1);
        } else {
            holder.containerC.setAlpha((float) 0.1);
            holder.llContainerBoking.setVisibility(View.VISIBLE);
            holder.containerA.setEnabled(false);
            holder.containerB.setVisibility(View.VISIBLE);

            holder.tvNamaWisatawan.setText(getGuide.getNama_wisatawan());
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date dateMulai = sdf.parse(getGuide.getTgl_mulai());
                Date dateAkhir = sdf.parse(getGuide.getTgl_akhir());
                int days = Days.daysBetween(new LocalDate(dateMulai), new LocalDate(dateAkhir)).getDays();
                holder.tvTanggal.setText(String.valueOf(days) + " Hari Lagi");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void addGuide(Guide guide) {
        lguide.add(guide);
        notifyDataSetChanged();
    }

    public Guide getGuide(int position) {
        return lguide.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNama, tvUmur, tvAgama, tvBahasa, tvKelamin, tvKontak, tvLokasi, tvjmhGuide, tvNamaWisatawan, tvTanggal;
        private RatingBar rating;
        private CircleImageView ivFoto, ivFotoWisatawan;
        private LinearLayout llContainerBoking, containerA, containerB, containerC;

        Holder(View itemView) {
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
            tvjmhGuide = itemView.findViewById(R.id.tv_jmhGuide);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            llContainerBoking = itemView.findViewById(R.id.llContainerBoking);
            ivFotoWisatawan = itemView.findViewById(R.id.ivFotoWisatawan);
            tvNamaWisatawan = itemView.findViewById(R.id.tvNamaWisatawan);
            containerA = itemView.findViewById(R.id.containerA);
            containerB = itemView.findViewById(R.id.containerB);
            containerC = itemView.findViewById(R.id.containerC);
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