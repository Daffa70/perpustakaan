package com.uas.perpustakaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uas.perpustakaan.fragment.AddBookFragment;
import com.uas.perpustakaan.fragment.BookFragment;
import com.uas.perpustakaan.fragment.CekBookingFragment;
import com.uas.perpustakaan.fragment.UserAdminFragment;

public class MainActivityAdmin extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);


        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setPageAdmin(menuItem.getItemId());

                return true;
            }
        });

        if (savedInstanceState == null) {
            setPageAdmin(R.id.navigation_cek_booking);
        }
    }
    public void setPageAdmin(int itemId){
        String title = "";
        Fragment fragment = null;

        switch (itemId){
            case R.id.navigation_cek_booking:
                title = "Cek Booking";
                fragment = new CekBookingFragment();
                break;
            case R.id.navigation_daftar_buku:
                title = "Daftar Buku";
                fragment = new BookFragment();
                break;
            case R.id.navigation_tambah_buku:
                title = "Tambah Buku";
                fragment = new AddBookFragment();
                break;
            case R.id.navigation_user_admin:
                title = "User Admin";
                fragment = new UserAdminFragment();
                break;
        }
        MainActivityAdmin.this.getSupportActionBar().setTitle(title);

        //set content fragment
        if (fragment != null) {
            MainActivityAdmin.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content, fragment)
                    .commit();
        }
    }
}
