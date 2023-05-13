package com.example.aexpress.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aexpress.R;
import com.example.aexpress.databinding.ActivityMainBinding;
import com.example.aexpress.databinding.ActivityProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    Button btnOrder, btnLoginSignup;
    ActivityProfileBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottom_navigation();




        // Set click listeners for the buttons
//        binding.orderHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open the order page
//           //     Intent intent = new Intent(ProfileActivity.this, OrderActivity.class);
//           //     startActivity(intent);
//            }
//        });

//        binding.loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Open the login/signup page
//            //    Intent intent = new Intent(ProfileActivity.this, LoginSignupActivity.class);
//             //   startActivity(intent);
//            }
//        });
    }

    public void viewebside(View view) {
        Intent intent = new Intent(ProfileActivity.this, Websideview.class);
        startActivity(intent);
    }
    public void viewOffer(View view) {
        Intent offers = new Intent(ProfileActivity.this, YourOffers.class);
        startActivity(offers);
    }

    void bottom_navigation() {
        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i2 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.cartview:
                        Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        Toast.makeText(ProfileActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }
                return true;
            }
        });
    }
}
