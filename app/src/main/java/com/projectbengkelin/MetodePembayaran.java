package com.projectbengkelin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MetodePembayaran extends AppCompatActivity {

    private Button btnCash;
    private Button btnTF;
    int getid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);

        btnCash = findViewById(R.id.btnCash);
        btnTF = findViewById(R.id.btnTF);

        Intent intent = getIntent();
        getid = intent.getIntExtra("id", 0);

        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoPembayaranCashActivity();
            }
        });

        btnTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailPembayaranActivity();
            }
        });
    }

    public void openInfoPembayaranCashActivity() {
        Intent intent = new Intent(MetodePembayaran.this, InfoPembayaranCash.class);
        startActivity(intent);
    }

    public void openDetailPembayaranActivity() {
        Intent intent = new Intent(MetodePembayaran.this, DetailPembayaran.class);
        intent.putExtra("id", getid);
        startActivity(intent);
    }
}
