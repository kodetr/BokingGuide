package com.ari.bokingguide.network.models;

public class Destinasi {
    private int id;
    private String nama;
    private String jenis;
    private String lokasi;
    private String keterangan;
    private String foto;
    private String vidio;
    private boolean pesan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getVidio() {
        return vidio;
    }

    public void setVidio(String vidio) {
        this.vidio = vidio;
    }

    public boolean getPesan() {
        return pesan;
    }

}
