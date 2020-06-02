package com.projectbengkelin;

/**
 * Ricky Ng
 * Jumat, 8 Mei 2020
 **/

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class DaftarBooking extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListBooking> listBookings;
    // SessionManager sessionManager;
    private Session session;
    private SQLite dbsqlite;

    SwipeRefreshLayout swipeRefreshLayout;
    // String email;
    private static final String LIST_BOOKING_URL = "https://bengkelinteam.000webhostapp.com/api/list_booking.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_booking);

        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listBookings = new ArrayList<>();
        // declare sqlite
        dbsqlite = new SQLite(getApplicationContext());
        // declare session manager
        session = new Session(getApplicationContext());

        // cek apakah user sudah pernah login
        if (!session.isLoggedIn()) {
            // Jika User tidak tercatat di sesiion atau telah login, Maka user automatis akan terlogout.
            logoutUser();
        }
        // Intent intent = getIntent();
        // email = intent.getStringExtra("email");
        // declare fungsi untuk memanggil data dari db
        loadRecyclerViewData();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        } );
    }

    private void loadRecyclerViewData() {
        HashMap<String, String> user = dbsqlite.getUserDetails();
        final String email = user.get("email");
        // Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LIST_BOOKING_URL, new Response.Listener<String>() {
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
                        ListBooking listBooking = new ListBooking(
                                object.getInt("idBooking"),
                                object.getInt("total_biaya"),
                                object.getString("tipeKendaraan").trim(),
                                object.getString("jenisServis").trim(),
                                object.getString("tanggal").trim(),
                                object.getString("pickup").trim(),
                                object.getString("estimasi").trim()
                        );
                        listBookings.add(listBooking);
                    }
                    adapter = new BookingAdapter(listBookings, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Kamu belum booking servis apapun", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                // email diambil aja dari intent put extra atau session
                // params.put("email", email);
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
        Intent intent = new Intent(DaftarBooking.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
