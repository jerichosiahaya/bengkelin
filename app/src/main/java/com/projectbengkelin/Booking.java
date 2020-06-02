package com.projectbengkelin;

/**
 * Jericho Siahaya
 * Senin, 4 Mei 2020
 * 14.38
 *
 * NOTES
 * - Ambil parameter IdKendaraan dari intent extra
 * - Ambil parameter IdServis dari intent extra
 * - Setelah sukses booking, intent ke page untuk liatin kode booking dan estimasi
 * - Estimasi text default: Mobil masih dalam tahap pemeriksaan
 **/

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Booking extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private Button btDatePicker;
    EditText EdttanggalTESAJA;

    EditText Edttanggal;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button btnLanjut;
    String URL_BOOKING = "https://bengkelinteam.000webhostapp.com/api/booking.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btnLanjut = findViewById(R.id.btnLanjut);
        radioGroup = findViewById(R.id.radioGroup);
        Edttanggal = findViewById(R.id.edtTanggal);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        // format tanggal
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Edttanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        // generate random number
        final int min = 100000;
        final int max = 900000;
        // ambil value idBooking dari sini
        final int idBooking = new Random().nextInt((max - min) + 1) + min;

        Intent intent = getIntent();
        // ambil value idServis dan idKendaraan dari sini
        final int idServis = intent.getIntExtra("idServis", 0);
        final int idKendaraan = intent.getIntExtra("idKendaraan", 0);

        Toast.makeText(Booking.this, "IdServis = " + idServis, Toast.LENGTH_LONG).show();
        Toast.makeText(Booking.this, "IdKendaraan = " + idKendaraan, Toast.LENGTH_LONG).show();
        Toast.makeText(Booking.this, "IdBooking =  " + idBooking, Toast.LENGTH_LONG).show();

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressDialog.setMessage("Loading data...");
                //progressDialog.show();
                final String tanggal = Edttanggal.getText().toString();
                final String pickup = radioButton.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_BOOKING, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(Booking.this, "Booking berhasil!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent (Booking.this, KodeBooking.class);
                                intent.putExtra("idBooking", idBooking);
                                startActivity(intent);
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(Booking.this, "Booking Gagal", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Booking.this, "Error " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("idBooking", String.valueOf(idBooking));
                        params.put("idKendaraan", String.valueOf(idKendaraan));
                        params.put("idServis", String.valueOf(idServis));
                        params.put("tanggal", tanggal);
                        params.put("pickup", pickup);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Edttanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}


