package com.amelia.dv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.amelia.dv.rt.HomeFragment;
import com.amelia.dv.rw.statusTopNav;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityRt extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    // Initialize fragments here to use throughout the class
    HomeFragment home = new HomeFragment();
    statusTopNav status = new statusTopNav ();
    SuratFragment surat = new SuratFragment();
    com.amelia.dv.FragmentProfil profile = new com.amelia.dv.FragmentProfil();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rt);

        Toast.makeText(this, "On Start", Toast.LENGTH_SHORT).show();

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Set default selected item
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        // Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, home) // Home fragment as default
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Handle fragment transactions based on selected item ID
        if (itemId == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, home)
                    .commit();
            return true;
        } else if (itemId == R.id.nav_surat) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, surat)
                    .commit();
            return true;
        } else if (itemId == R.id.nav_status) {
            Log.d("Navigation", "Navigating to Pengajuan Surat Status"); // Log for debugging
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, status)
                    .commit();
            return true;
        } else if (itemId == R.id.nav_profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, profile)
                    .commit();
            return true;
        }

        return false;
    }
}
