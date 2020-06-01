package com.uas.perpustakaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uas.perpustakaan.MainActivity;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.helper.SessionManager;
import com.uas.perpustakaan.helper.UtilMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

public class LoginUserActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegistrasi;
    private SessionManager sessionManager;
    private UtilMessage utilMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        tvRegistrasi = findViewById(R.id.tv_registrasi);

        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);

        tvRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistrasi = new Intent(LoginUserActivity.this, RegistrasiActivity.class);
                startActivity(intentRegistrasi);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();

        //validasi
        if (username.trim().isEmpty()) {
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.trim().isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            utilMessage.showProgressBar("Logging in...");

            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            //convert string to json object
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int status = jsonResponse.getInt("status");
                                String message = jsonResponse.getString("message");

                                Toast.makeText(LoginUserActivity.this, message, Toast.LENGTH_SHORT).show();
                                if (status == 0) {
                                    String userId = jsonResponse.getString("data");
                                    // simpan ke session
                                    sessionManager.setUserId(userId);

                                    Intent intentMain = new Intent(LoginUserActivity.this, MainActivity.class);
                                    startActivity(intentMain);
                                    finish();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(LoginUserActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            utilMessage.dismissProgressBar();
                            Toast.makeText(LoginUserActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();

                    params.put("username", username);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }
}
