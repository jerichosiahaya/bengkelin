package com.projectbengkelin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class Register extends AppCompatActivity {

    private EditText nama,email,alamat,noHp,password;
    private Button btnLanjut;
    private Button btnKembali;
    private ProgressBar loading;
    private static String URL_REGISTER = "https://bengkelinteam.000webhostapp.com/api/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        noHp = findViewById(R.id.noHp);
        password = findViewById(R.id.password);
        loading = findViewById(R.id.loading);
        btnLanjut = findViewById(R.id.btnLanjut);
        btnKembali = findViewById(R.id.btnKembali);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    // fungsi Register untuk memasukkan data ke dalam database
    private void Register() {
        loading.setVisibility(View.VISIBLE);
        btnLanjut.setVisibility(View.GONE);
        final String nama = this.nama.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String alamat = this.alamat.getText().toString().trim();
        final String noHp = this.noHp.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Register.this, "Setelah response!", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // Toast.makeText(Register.this, "Setelah JSONObject", Toast.LENGTH_SHORT).show();
                            int success = jsonObject.getInt("success");
                            if(success == 1) {
                                Toast.makeText(Register.this, "Register Berhasil!", Toast.LENGTH_SHORT).show();
                                openMainActivity();
                            } else if (success == 0){
                                Toast.makeText(Register.this, "Gagal Register!", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(Register.this, "Bad Request 502", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            // e.printStackTrace();
                            Toast.makeText(Register.this, "Bad Request 502: System Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnLanjut.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("noHp", noHp);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

