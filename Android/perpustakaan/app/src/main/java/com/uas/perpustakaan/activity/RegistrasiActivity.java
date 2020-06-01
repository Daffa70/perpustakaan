package com.uas.perpustakaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uas.perpustakaan.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

public class RegistrasiActivity extends AppCompatActivity {
    private EditText edtNama, edtUsername, edtPassword, edtKonfirmasiPassword, edtAlamat, edtHp;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        edtNama = findViewById(R.id.edt_nama);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtKonfirmasiPassword = findViewById(R.id.edt_konfirmasi_password);
        edtAlamat = findViewById(R.id.edt_alamat);
        edtHp = findViewById(R.id.edt_hp);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }
    private void submitData() {
        final String nama = edtNama.getText().toString();
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();
        final String alamat = edtAlamat.getText().toString();
        final String hp = edtHp.getText().toString();
        String konfirmasiPassword = edtKonfirmasiPassword.getText().toString();

        //validasi input
        if (nama.trim().isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (username.trim().isEmpty()) {
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.trim().isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(konfirmasiPassword)) {
            Toast.makeText(this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
        } else {
            //input valid
            //siapkan request
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "registrasi.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //convert string to json object
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int status = jsonResponse.getInt("status");
                                String message = jsonResponse.getString("message");

                                Toast.makeText(RegistrasiActivity.this, message, Toast.LENGTH_SHORT).show();
                                if (status == 0) {
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegistrasiActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("nama", nama);
                    params.put("username", username);
                    params.put("password", password);
                    params.put("alamat", alamat);
                    params.put("hp", hp);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }
}
