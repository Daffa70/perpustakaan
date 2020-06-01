package com.uas.perpustakaan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Booking implements Parcelable {
    private String idBuku;
    private String id_Anggota;
    private String id_Book;
    private String tanggal_pinjam;
    private String tanggal_proses;
    private String status_booking;
    private String denda;
    private String namaAnggota;
    private String judulBuku;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getId_Anggota() {
        return id_Anggota;
    }

    public void setId_Anggota(String id_Anggota) {
        this.id_Anggota = id_Anggota;
    }

    public String getId_Book() {
        return id_Book;
    }

    public void setId_Book(String id_Book) {
        this.id_Book = id_Book;
    }

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public String getTanggal_proses() {
        return tanggal_proses;
    }

    public void setTanggal_proses(String tanggal_proses) {
        this.tanggal_proses = tanggal_proses;
    }

    public String getStatus_booking() {
        return status_booking;
    }

    public void setStatus_booking(String status_booking) {
        this.status_booking = status_booking;
    }

    public String getDenda() {
        return denda;
    }

    public void setDenda(String denda) {
        this.denda = denda;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_Book);
        dest.writeString(this.idBuku);
        dest.writeString(this.id_Anggota);
        dest.writeString(this.tanggal_pinjam);
        dest.writeString(this.tanggal_proses);
        dest.writeString(this.status_booking);
        dest.writeString(this.denda);
        dest.writeString(this.judulBuku);
        dest.writeString(this.namaAnggota);
        dest.writeString(this.imageUrl);
    }

    public Booking() {
    }

    protected Booking(Parcel in) {
        this.id_Book = in.readString();
        this.idBuku = in.readString();
        this.id_Anggota = in.readString();
        this.tanggal_pinjam = in.readString();
        this.tanggal_proses = in.readString();
        this.status_booking = in.readString();
        this.denda = in.readString();
        this.judulBuku = in.readString();
        this.namaAnggota = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel source) {
            return new Booking(source);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

}
