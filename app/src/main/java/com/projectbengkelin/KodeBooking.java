package com.projectbengkelin;

/**
 * Jericho Siahaya
 * Jumat, 8 Mei 2020
 * 17.00
 **/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class KodeBooking extends AppCompatActivity {

    TextView kodeBookingServis;
    Button btnCek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kode_booking);

        kodeBookingServis = findViewById(R.id.txtKode);
        btnCek = findViewById(R.id.btnCek);


        Intent intent = getIntent();
        final int idBooking = intent.getIntExtra("idBooking", 0);
        kodeBookingServis.setText(String.valueOf(idBooking));

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (KodeBooking.this, DaftarBooking.class);
                startActivity(intent);
            }
        });

    }
}
