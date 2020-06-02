package com.projectbengkelin;

/**
 * --- Debug ---
 * Jericho Siahaya
 * OC: Ricky Ng
 * Jumat, 8 Mei 2020
 * 21.46
 *
 * NOTES:
 * Ini activity update/delete
 *
 * CHANGELOG:
 * Parameter idKendaraan yang dilempar String
 * Typo di parameter tahunKendaraan (harusnya tahunKeluaran)
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class KendaraanDetail extends AppCompatActivity {

    private Session session;
    private SQLite dbsqlite;
    TextView txtid;
    EditText txtjenis, txttipe, txttahun;
    String getjenis, gettipe, gettahun;
    int getid;
    Button btnDelete, btnUpdate, btnBack;
    // int idKendaraan;
    private static String URL_UPDATE = "https://bengkelinteam.000webhostapp.com/api/update_kendaraan.php/";
    private static String URL_DELETE = "https://bengkelinteam.000webhostapp.com/api/delete_kendaraan.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan_detail);

        txtid = (TextView)  findViewById(R.id.txtid);
        txtjenis = (EditText) findViewById(R.id.txtjenis);
        txttipe = (EditText) findViewById(R.id.txttipe);
        txttahun = (EditText) findViewById(R.id.txttahun);

        // intent dari KendaraanAdapter.java
        Intent i = getIntent();
        getid = i.getIntExtra("id", 0);
        // getid = idKendaraan;
        getjenis = i.getStringExtra("jenis");
        gettipe = i.getStringExtra("tipe");
        gettahun = i.getStringExtra("tahun");
        // Toast.makeText(KendaraanDetail.this, "Id kendaraan: " + getid, Toast.LENGTH_SHORT).show();

        final int idKendaraan = getid;

        // set text untuk di edit text layout
        txtid.setText(String.valueOf(getid));
        txtjenis.setText(getjenis);
        txttipe.setText(gettipe);
        txttahun.setText(gettahun);

        // UPDATE
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // variabel yang akan dilempar pada parameter
                // final int idKendaraan = getid;
                final String mJenis = txtjenis.getText().toString().trim();
                final String mTipe = txttipe.getText().toString().trim();
                final String mTahun = txttahun.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(KendaraanDetail.this);
                progressDialog.setMessage("Updating...");
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(KendaraanDetail.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonObject = new JSONObject(response);
                                        int success = jsonObject.getInt("success");
                                        if(success == 1) {
                                            Toast.makeText(KendaraanDetail.this, "Update berhasil!", Toast.LENGTH_SHORT).show();
                                        } else if (success == 0) {
                                            Toast.makeText(KendaraanDetail.this, "Update gagal!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(KendaraanDetail.this, "Data kosong", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        //e.printStackTrace();
                                        Toast.makeText(KendaraanDetail.this, "Update Gagal!" + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(KendaraanDetail.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,  String> params = new HashMap<>();
                            params.put("idKendaraan", String.valueOf(idKendaraan));
                            params.put("jenisKendaraan", mJenis);
                            params.put("tipeKendaraan", mTipe);
                            params.put("tahunKeluaran", mTahun);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                    requestQueue.getCache().clear();
                    Intent intent = new Intent(KendaraanDetail.this, DataKendaraan.class);
                    startActivity(intent);
            }
        });


        // DELETE
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // variabel yang akan dilempar pada parameter
                // final int idKendaraan = getid;
                final String mJenis = txtjenis.getText().toString().trim();
                final String mTipe = txttipe.getText().toString().trim();
                final String mTahun = txttahun.getText().toString().trim();

                new AlertDialog.Builder(KendaraanDetail.this)
                        .setTitle("Hapus Kendaraan")
                        .setMessage("Kamu yakin mau hapus kendaraan ini?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(KendaraanDetail.this);
                                progressDialog.setMessage("Deleting...");
                                progressDialog.show();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try{
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    int success = jsonObject.getInt("success");
                                                    if(success == 1 ) {
                                                        Toast.makeText(KendaraanDetail.this, "Delete berhasil!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(KendaraanDetail.this, DataKendaraan.class);
                                                        startActivity(intent);
                                                    } else if (success == 0) {
                                                        Toast.makeText(KendaraanDetail.this, "Delete gagal!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Toast.makeText(KendaraanDetail.this, "Data kosong, cek parameter!", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    //e.printStackTrace();
                                                    Toast.makeText(KendaraanDetail.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(KendaraanDetail.this, "Delete Failed!" + error.toString(), Toast.LENGTH_SHORT).show();
                                                error.printStackTrace();

                                            }
                                        })
                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,  String> params = new HashMap<>();
                                        params.put("idKendaraan", String.valueOf(idKendaraan));
                                        params.put("jenisKendaraan", mJenis);
                                        params.put("tipeKendaraan", mTipe);
                                        params.put("tahunKeluaran", mTahun);
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("Content-Type", "application/x-www-form-urlencoded");
                                        return params;
                                    }

                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(KendaraanDetail.this);
                                requestQueue.add(stringRequest);
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .show();



            }
        });


        // BERANDA
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KendaraanDetail.this, DataKendaraan.class);
                startActivity(intent);
            }
        });
    }
}

