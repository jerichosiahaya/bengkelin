package com.projectbengkelin;

/**
 * --- Update adapter constructor
 * OC: Ricky Ng
 *
 * CHANGELOG:
 * --- Variabel int ditambahkan pada constructor adapter untuk idServis
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PilihKendaraan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListKendaraan> listKendaraans;
    // SessionManager sessionManager;

    private Session session;
    private SQLite dbsqlite;
    // String email;
    private static final String KENDARAAN_URL = "https://bengkelinteam.000webhostapp.com/api/data_kendaraan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kendaraan);

        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listKendaraans = new ArrayList<>();
        // declare sqlite
        dbsqlite = new SQLite(getApplicationContext());
        // declare session manager
        session = new Session(getApplicationContext());

        // cek apakah user sudah pernah login
        if (!session.isLoggedIn()) {
            // Jika User tidak tercatat di sesiion atau telah login, Maka user automatis akan terlogout.
            logoutUser();
        }

        loadRecyclerViewData();

    }

    private void loadRecyclerViewData() {
        Intent intent = getIntent();
        final int idServis =  intent.getIntExtra("idServis", 0);
       //  MyAdapter adapter = new MyAdapter(context,yourIntent);
        HashMap<String, String> user = dbsqlite.getUserDetails();
        final String email = user.get("email");
        // Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, KENDARAAN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Toast.makeText(getApplicationContext(), "Setelah JSONObject", Toast.LENGTH_LONG).show();
                    JSONArray array = jsonObject.getJSONArray("data");
                    // Toast.makeText(getApplicationContext(), "Setelah Array", Toast.LENGTH_LONG).show();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        ListKendaraan listKendaraan = new ListKendaraan(
                                object.getInt("idKendaraan"),
                                object.getString("jenisKendaraan").trim(),
                                object.getString("tipeKendaraan").trim(),
                                object.getString("tahunKeluaran").trim()
                        );
                        listKendaraans.add(listKendaraan);
                    }
                    adapter = new PilihKendaraanAdapter(listKendaraans, idServis ,getApplicationContext());
                    // adapter PilihKendaraanAdapter = new PilihKendaraanAdapter(context, idServis);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Kamu belum memasukkan data kendaraan", Toast.LENGTH_LONG).show();
                    //e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void logoutUser() {
        session.setLogin(false);

        // menghapus data di SQLite jika di logout. sehingga tidak ada penyimpana di local database
        dbsqlite.deleteUsers();

        // Langsung diarahkan ke halaman login
        Intent intent = new Intent(PilihKendaraan.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
