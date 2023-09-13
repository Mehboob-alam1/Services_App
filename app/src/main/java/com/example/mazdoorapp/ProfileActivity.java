package com.example.mazdoorapp;

import static com.example.mazdoorapp.Utils.showSnackBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.mazdoorapp.fragments.AboutFragment;
import com.example.mazdoorapp.R;
import com.example.mazdoorapp.databinding.ActivityProfileBinding;
import com.example.mazdoorapp.fragments.MainFragment;
import com.example.mazdoorapp.fragments.ProfileFragment;
import com.example.mazdoorapp.fragments.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        actionBar = getSupportActionBar();



        binding.btnOrders.setOnClickListener(view -> {

 startActivity(new Intent(ProfileActivity.this,BookingActivity.class));

        });

      binding.navigation.setOnNavigationItemSelectedListener(selectedListener);

        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "");
        fragmentTransaction.commit();
       String name= getIntent().getStringExtra("user");

        Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();


        binding.switchAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    binding.txtStatus.setText("Online");
                    setStatus(true);
                }else{
                    binding.txtStatus.setText("offline");
                    setStatus(false);
                }
            }
        });


    }

    private void setStatus(boolean isStatus) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProviderAvailability");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(new Available(isStatus,FirebaseAuth.getInstance().getCurrentUser().getUid()))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        showSnackBar(this,"Status set to " +isStatus);

                    }else{
                        showSnackBar(this,"Something went wrong");
                    }
                }).addOnFailureListener(e -> {
                    showSnackBar(this,e.getLocalizedMessage());
                });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.nav_home:

                    MainFragment fragment = new MainFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_profile:

                    ProfileFragment fragment1 = new ProfileFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, fragment1);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.nav_services:

                    ServicesFragment fragment2 = new ServicesFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, fragment2, "");
                    fragmentTransaction2.commit();
                    return true;
                case R.id.nav_about:
                    AboutFragment fragment3=new AboutFragment();
                    FragmentTransaction fragmentTransaction3 =getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content,fragment3,"");
                    fragmentTransaction3.commit();
                    return true;



            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case R.id.menu_logout:
                logout();
                break;


        }

        return true;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);


        return true;
    }
}