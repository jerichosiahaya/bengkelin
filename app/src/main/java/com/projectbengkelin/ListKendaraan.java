package com.projectbengkelin;

public class ListKendaraan {

    private int id;
    private String jenis, tipe, tahun;

    public ListKendaraan(int idKendaraan, String jenis, String tipe, String tahun){
        this.id = idKendaraan;
        this.jenis = jenis;
        this.tipe = tipe;
        this.tahun = tahun;
    }


    public int getId() {
        return id;
    }

    public String getJenis() {
        return jenis;
    }

    public String getTipe() {
        return tipe;
    }

    public String getTahun(){
        return tahun;
    }
}
