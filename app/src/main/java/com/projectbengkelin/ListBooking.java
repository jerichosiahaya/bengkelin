package com.projectbengkelin;

public class ListBooking {


    private int idbooking, total_biaya;
    private String tipeKendaraan, jenisServis, tanggal, pickup,estimasi;

    public ListBooking(int idbooking, int total_biaya, String tipeKendaraan, String jenisServis, String tanggal, String pickup, String estimasi){
        this.idbooking = idbooking;
        this.tipeKendaraan = tipeKendaraan;
        this.jenisServis = jenisServis;
        this.tanggal = tanggal;
        this.pickup = pickup;
        this.total_biaya = total_biaya;
        this.estimasi = estimasi;


    }


    public int getIdBooking() {
        return idbooking;
    }

    public int getTotalBiaya() {
       return total_biaya;
    }

    public String getJenisKendaraan() {
        return tipeKendaraan;
    }

    public String getJenisServis() {
        return jenisServis;
    }

    public String getTanggal(){
        return tanggal;
    }

    public String getPickup(){
        return pickup;
    }

    public String getEstimasi(){
        return estimasi;
    }
}

