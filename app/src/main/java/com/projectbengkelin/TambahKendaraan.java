package com.projectbengkelin;

/**
 * Jericho Siahaya
 * Senin, 4 Mei 2020
 * 10.57
 *
 * UPDATE -- SPINNER 28 Mei 2020
 **/

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahKendaraan extends AppCompatActivity {

    EditText EdtjenisKendaraan, EdttipeKendaraan, EdttahunKeluaran;
    Button btnLanjut;
    private Session session;
    private SQLite dbsqlite;
    String URL_REG_CAR = "https://bengkelinteam.000webhostapp.com/api/insert_kendaraan.php";
    // ProgressBar loading;

    // daftar jenis kendaraan fixed
    private String[] jenisKendaraan = {
            "Agya",
            "Rush", "Fortuner",
            "Calya",
            "Yaris",
            "Avanza",
            "Avanza Veloz",
            "Kijang Innova",
            "Alphard",
            "Land Cruiser", "Toyota 86",
            "Venturer", "Voxy",
            "Toyota CHR",
            "Hilux"
    };

    // daftar tipe kendaraan fixed
    private String[] tipeKendaraan = {
            "Minibus",
            "Hatchback", "Sedan",
            "MPV",
            "SUV",
            "Crossover",
            "Coupe",
            "Convertible"
    };

    // daftar tahun keluaran fixed
    private String[] tahunKeluaran = {
            "2006",
            "2007", "2008",
            "2009",
            "2010",
            "2011",
            "2012",
            "2013", "2014",
            "2015", "2016",
            "2017", "2018",
            "2019", "2020"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kendaraan);

        EdtjenisKendaraan = findViewById(R.id.jenisKendaraan);
        EdttipeKendaraan = findViewById(R.id.tipeKendaraan);
        EdttahunKeluaran = findViewById(R.id.tahunKeluaran);
        btnLanjut = findViewById(R.id.btnLanjut);
        EdtjenisKendaraan.setInputType(InputType.TYPE_NULL); // hide the softkeyboard
        EdttipeKendaraan.setInputType(InputType.TYPE_NULL);  // hide the softkeyboard
        EdttahunKeluaran.setInputType(InputType.TYPE_NULL);  // hide the softkeyboard


        // inisialiasi Array Adapter Jenis Kendaraan
        final ArrayAdapter<String> adapterJenisKendaraan = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jenisKendaraan);

        // memunculkan list jenis kendaraan
        EdtjenisKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TambahKendaraan.this)
                        .setTitle("Pilih Jenis Kendaraan")
                        .setAdapter(adapterJenisKendaraan, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EdtjenisKendaraan.setText(jenisKendaraan[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        // inisialiasi Array Adapter Tipe Kendaraan
        final ArrayAdapter<String> adapterTipeKendaraan = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tipeKendaraan);

        // memunculkan list tipe kendaraan
        EdttipeKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TambahKendaraan.this)
                        .setTitle("Pilih Tipe Kendaraan")
                        .setAdapter(adapterTipeKendaraan, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EdttipeKendaraan.setText(tipeKendaraan[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        // inisialiasi Array Adapter Tahun Keluaran
        final ArrayAdapter<String> adapterTahunKeluaran = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tahunKeluaran);
        
        // memunculkan list tahun keluaran
        EdttahunKeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TambahKendaraan.this)
                        .setTitle("Pilih Tahun Keluaran")
                        .setAdapter(adapterTahunKeluaran, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EdttahunKeluaran.setText(tahunKeluaran[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        // declare sqlite
        dbsqlite = new SQLite(getApplicationContext());
        // declare session manager
        session = new Session(getApplicationContext());

        // mengambil data email dari SQLite
        HashMap<String, String> user = dbsqlite.getUserDetails();
        final String email = user.get("email");

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jenisKendaraan = EdtjenisKendaraan.getText().toString();
                final String tipeKendaraan = EdttipeKendaraan.getText().toString();
                final String tahunKeluaran = EdttahunKeluaran.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(TambahKendaraan.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                // Request Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG_CAR, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Intent intent = new Intent(TambahKendaraan.this, DataKendaraan.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(TambahKendaraan.this, "Kendaraan gagal ditambahkan", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TambahKendaraan.this, "Error " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(TambahKendaraan.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("jenisKendaraan", jenisKendaraan);
                        params.put("tipeKendaraan", tipeKendaraan);
                        params.put("tahunKeluaran", tahunKeluaran);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            } 
        }); 
    } 
} 
