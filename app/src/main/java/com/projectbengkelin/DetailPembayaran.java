package com.projectbengkelin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DetailPembayaran extends AppCompatActivity {

    private Button button4, btnBayar;
    int getid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran);

        button4 = findViewById(R.id.button4);
        btnBayar = findViewById(R.id.btnBayar);

        Intent intent = getIntent();
        getid = intent.getIntExtra("id", 0);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUploadPaymentActivity();
            }
        });
    }

    public void openHomeActivity() {
        Intent intent = new Intent(DetailPembayaran.this, HomeActivity.class);
        startActivity(intent);
    }

    public void openUploadPaymentActivity() {
        Intent intent = new Intent(DetailPembayaran.this, UploadPayment.class);
        intent.putExtra("id", getid);
        startActivity(intent);
    }
}
