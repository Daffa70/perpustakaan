package com.uas.perpustakaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uas.perpustakaan.R;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnAdmin, btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnAdmin = findViewById(R.id.btn_admin);
        btnUser = findViewById(R.id.btn_user);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdmin = new Intent(MainMenuActivity.this, LoginAdminActivity.class);
                startActivity(intentAdmin);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUser = new Intent(MainMenuActivity.this, LoginUserActivity.class);
                startActivity(intentUser);
            }
        });
    }
}
