package com.projectbengkelin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class DetailServis extends AppCompatActivity {

    private Button btnBayar, btnBeranda;
    int getid, getbiaya, idbooking;
    TextView txtidbooking;
    TextView txttotalbiaya;



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListServisDetail> listServiss;
    private static final String LIST_SERVIS_DETAIL = "https://bengkelinteam.000webhostapp.com/api/servis_detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_servis);

        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listServiss= new ArrayList<>();

        txtidbooking = findViewById(R.id.txtidbooking);
        txttotalbiaya = findViewById(R.id.txttotalbiaya);

        Intent i = getIntent();
        getid = i.getIntExtra("idBooking", 0);
        getbiaya = i.getIntExtra("total_biaya", 0);

        txtidbooking.setText(String.valueOf(getid));
        txttotalbiaya.setText(String.valueOf(getbiaya));

        loadRecyclerViewData();

        btnBayar = findViewById(R.id.btnBayar);
        btnBeranda = findViewById(R.id.btnBeranda);

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMetodePembayaranActivity();
            }
        });

        btnBeranda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });
    }

    private void loadRecyclerViewData() {
        Intent i = getIntent();
        getid = i.getIntExtra("idBooking", 0);

        // Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest( Request.Method.POST, LIST_SERVIS_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        ListServisDetail listServisDetail = new ListServisDetail(
                                object.getInt("biaya"),
                                object.getString("detail").trim()
                                                );
                        listServiss.add(listServisDetail);
                    }
                    adapter = new ServisAdapter(listServiss, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {

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
                params.put("idBooking", String.valueOf(getid));
                return params;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openMetodePembayaranActivity() {
        Intent intent = new Intent(DetailServis.this, MetodePembayaran.class);
        intent.putExtra("id", getid);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(DetailServis.this, HomeActivity.class);
        startActivity(intent);
    }
}
