package com.projectbengkelin;

/**
 * Jericho Siahaya
 * Sabtu, 2 Mei 2020
 * 20.45
 *
 * LOGIN ACTIVITY
 * CHANGELOG
 * -- Store string nama dan email dalam SQLite yang digunakan sebagai Session
 **/

import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText Edtemail, Edtpassword;
    private Button btnLogin, btnRegister;
    private ProgressBar loading;
    private static String URL_LOGIN = "https://bengkelinteam.000webhostapp.com/api/login.php/";
    private Session session;
    private SQLite dbsqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.loading);
        Edtemail = findViewById(R.id.email);
        Edtpassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // declare sqlite
        dbsqlite = new SQLite(getApplicationContext());

        // declare session
        session = new Session(getApplicationContext());

        // cek apakah user sudah pernah login
        if (session.isLoggedIn()) {
            // User is already logged in. Take to ActivityUser
            // Jika User telah login maka akan diarahkan ke ActivityUser
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        // tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Edtemail.getText().toString().trim();
                final String pass = Edtpassword.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Logging in...");
                progressDialog.show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // Toast.makeText(MainActivity.this, "Setelah Response", Toast.LENGTH_LONG).show();
                            int success = jsonObject.getInt("success");
                            // Toast.makeText(MainActivity.this, "Setelah GetString", Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                          //  Toast.makeText(MainActivity.this, "Setelah GetArray", Toast.LENGTH_LONG).show();
                            if (success == 1) {

                                // user successfully logged in
                                // Create login session - membuat session
                                session.setLogin(true);

                                //Toast.makeText(MainActivity.this, "Setelah IF", Toast.LENGTH_LONG).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String namaOBJ = object.getString("nama").trim();
                                    String emailOBJ = object.getString("email").trim();
                                    // menambahkan string nama dan email yang diambil dari db host
                                    // ditambahkan ke lokal db lewat parameter
                                    dbsqlite.addUser(emailOBJ, namaOBJ);
                                    Intent intent = new Intent (MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loading.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Gagal login", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<String, String>();
                        params.put("email",email);
                        params.put("password",pass);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

        // tombol register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }
    
    public void openRegisterActivity(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}