package com.projectbengkelin;

/**
 * Jericho Siahaya
 * Senin, 4 Mei 2020
 * 14.17
 *
 * CHANGELOG
 * --
 *
**/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PilihServis extends AppCompatActivity {

    private Session session;
    private SQLite dbsqlite;

    View servisBerkala, servisMesin, servisBodi, spooringBalancing;
    private Button btnBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_servis);

        // declare sqlite
        dbsqlite = new SQLite(getApplicationContext());
        // declare session manager
        session = new Session(getApplicationContext());

        if (!session.isLoggedIn()) {
            // Jika User tidak tercatat di sesiion atau telah login, Maka user automatis akan terlogout.
            logoutUser();
        }

        // all the cardviews and button
        btnBackHome = findViewById(R.id.btnBackHome);
        servisBerkala = findViewById(R.id.servis_berkala);
        servisMesin = findViewById(R.id.servis_mesin);
        servisBodi = findViewById(R.id.servis_bodi);
        spooringBalancing = findViewById(R.id.spooring_balancing);

        // servis berkala
        servisBerkala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihServis.this, PilihKendaraan.class);
                intent.putExtra("idServis", 1);
                startActivity(intent);
            }
        });

        servisMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihServis.this, PilihKendaraan.class);
                intent.putExtra("idServis", 2);
                startActivity(intent);
            }
        });

        servisBodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihServis.this, PilihKendaraan.class);
                intent.putExtra("idServis", 3);
                startActivity(intent);
            }
        });

        spooringBalancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihServis.this, PilihKendaraan.class);
                intent.putExtra("idServis", 4);
                startActivity(intent);
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });
    }


    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    private void logoutUser() {
        session.setLogin(false);

        // menghapus data di SQLite jika di logout. sehingga tidak ada penyimpana di local database
        dbsqlite.deleteUsers();

        // Langsung diarahkan ke halaman login
        Intent intent = new Intent(PilihServis.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
