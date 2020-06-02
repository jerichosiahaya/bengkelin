package com.projectbengkelin;

/**
 * Jericho Siahaya
 * Rabu, 6 Mei 2020
 * 20.02
 *
 * CHANGELOG
 * -- Session dibuat menggunakan SQLite, sehingga data user (temporary) disimpan
 *    dalam lokal db hp untuk bisa digunakan ke depannya.
 **/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private TextView nama;
    Button btnLogout;
    private Session session;
    private SQLite dbsqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // declare sqlite
        dbsqlite = new SQLite(getApplicationContext());

        // declare session manager
        session = new Session(getApplicationContext());

        // cek apakah user sudah pernah login
        if (!session.isLoggedIn()) {
            // Jika user tidak tercatat di session atau telah login, maka user otomatis akan terlogout.
            logoutUser();
        }

        nama = findViewById(R.id.nama);

        // mengambil data "name" untuk ditampilkan pada pesan Selamat Datang
        HashMap<String, String> user = dbsqlite.getUserDetails();
        String name = user.get("nama");
        String email = user.get("email");
        nama.setText(name);

        // card view data kendaraan
        View dataKendaraan = findViewById(R.id.dataKendaraan);
        dataKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DataKendaraan.class);
                // intent.putExtra("email", extraEmail);
                startActivity(intent);
            }
        });

        // card view tambah kendaraan
        View tambahKendaraan = findViewById(R.id.tambahKendaraan);
        tambahKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TambahKendaraan.class);
                startActivity(intent);
            }
        });

        // card view pilih servis
        View pilihServis = findViewById(R.id.servisKendaraan);
        pilihServis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, PilihServis.class);
                startActivity(intent);
               // logoutUser();
            }
        });


        // card view upload pembayaran
        View uploadPembayaran = findViewById(R.id.UploadPembayaran);
        uploadPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DaftarBooking.class);
                startActivity(intent);
            }
        });


        // button logout
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    // fungsi logout 
    private void logoutUser() {
        session.setLogin(false);
        // menghapus data di SQLite jika di logout. sehingga tidak ada penyimpana di local database
        dbsqlite.deleteUsers();
        // Langsung diarahkan ke halaman login
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}