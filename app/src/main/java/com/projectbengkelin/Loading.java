package com.projectbengkelin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Thread thread = new Thread() {
            public void run() {
                try {
                // splash screen diset 5000 milisecods
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // setelah itu masuk ke Main Activity
                    startActivity(new Intent(Loading.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}