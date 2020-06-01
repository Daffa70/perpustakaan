package com.uas.perpustakaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uas.perpustakaan.fragment.BookFragment;
import com.uas.perpustakaan.fragment.BookingFragment;
import com.uas.perpustakaan.fragment.UserFragment;
import com.uas.perpustakaan.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setPage(menuItem.getItemId());

                return true;
            }
        });

        if (savedInstanceState == null) {
            setPage(R.id.navigation_booking);
        }
    }
    public void setPage(int itemId) {
        String title = "";
        Fragment fragment = null;

        switch (itemId) {
            case R.id.navigation_booking:
                title = "Booking";
                fragment = new BookingFragment();
                break;
            case R.id.navigation_cari_buku:
                title = "Cari Buku";
                fragment = new BookFragment();
                break;
            case R.id.navigation_user:
                title = "User";
                fragment = new UserFragment();
                break;
        }

        MainActivity.this.getSupportActionBar().setTitle(title);

        //set content fragment
        if (fragment != null) {
            MainActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content, fragment)
                    .commit();
        }
    }
}
