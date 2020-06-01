package com.uas.perpustakaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.uas.perpustakaan.activity.MainMenuActivity;
import com.uas.perpustakaan.helper.SessionManager;

public class CekSessionActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_session);

        sessionManager = new SessionManager(this);


        if (!sessionManager.isLoggedIn() && !sessionManager.isLoggedInAdmin()) {
            Intent intentLogin = new Intent(this, MainMenuActivity.class);
            startActivity(intentLogin);
            finish();
        }
        else if (sessionManager.isLoggedIn()){
            Intent intentUser = new Intent(this, MainActivity.class);
            startActivity(intentUser);
            finish();
        }
        else if(sessionManager.isLoggedInAdmin()){
            Intent intentAdmin = new Intent(this, MainActivityAdmin.class);
            startActivity(intentAdmin);
            finish();
        }
    }
}
