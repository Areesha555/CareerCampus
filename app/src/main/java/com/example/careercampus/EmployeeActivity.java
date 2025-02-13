package com.example.careercampus;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.careercampus.databinding.EmployeeBinding;
import com.example.careercampus.DiscoverFragment;
import com.example.careercampus.HomeFragment;
import com.example.careercampus.NotificationFragment;
import com.example.careercampus.ProfileFragment;
import com.example.careercampus.R;
import com.example.careercampus.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeActivity extends AppCompatActivity {
    EmployeeBinding binding;
    boolean isLoggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(Color.parseColor("#FF000000"));

        // Set the default fragment to HomeFragment and show the LinearLayout
        replaceFragment(new HomeFragment());

        isLoggedIn = FirebaseAuth.getInstance().getCurrentUser() != null;

        // Set image resource


        // Bottom navigation item selected listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.profile) {
                if (isLoggedIn) {
                    replaceFragment(new ProfileFragment());
                }else{
                    Intent intent = new Intent(EmployeeActivity.this, Getstarted.class);
                    startActivity(intent);
                    finish();
                }
            } else if (item.getItemId() == R.id.discover) {
                replaceFragment(new DiscoverFragment());
            } else if (item.getItemId() == R.id.notifications) {
                if(isLoggedIn) {
                    replaceFragment(new NotificationFragment());
                }else {
                    Intent intent = new Intent(EmployeeActivity.this, Getstarted.class);
                    startActivity(intent);
                    finish();
                }
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        // Hide or show the LinearLayout based on the fragment
        if (fragment instanceof HomeFragment) {
            binding.linearlayout1.setVisibility(View.VISIBLE);  // Show for HomeFragment
        } else {
            binding.linearlayout1.setVisibility(View.GONE);  // Hide for other fragments
        }

        // Fragment transaction to replace the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in2,
                        R.anim.slide_out
                );
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
    }


}

