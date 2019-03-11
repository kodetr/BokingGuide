package com.ari.bokingguide.network.models;

public class Wisatawan {
    private int id;
    private String nama;
    private int umur;
    private String agama;
    private String bahasa;
    private String jk;
    private String kontak;
    private String foto;
    private String nama_guide;
    private String tgl_mulai;
    private String tgl_akhir;
    private String time_now;
    private String tujuan_wisata;
    private String biaya;
    private int status;
    private boolean pesan;
    private int id_guide;

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getUmur() {
        return umur;
    }

    public String getAgama() {
        return agama;
    }

    public String getBahasa() {
        return bahasa;
    }

    public String getJk() {
        return jk;
    }

    public String getKontak() {
        return kontak;
    }

    public String getFoto() {
        return foto;
    }

    public boolean isPesan() {
        return pesan;
    }

    public String getNama_guide() {
        return nama_guide;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public String getTgl_akhir() {
        return tgl_akhir;
    }

    public String getTujuan_wisata() {
        return tujuan_wisata;
    }

    public String getBiaya() {
        return biaya;
    }

    public int getStatus() {
        return status;
    }

    public int getId_guide() {
        return id_guide;
    }

    public String getTime_now() {
        return time_now;
    }
}
