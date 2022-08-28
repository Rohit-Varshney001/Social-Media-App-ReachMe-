package com.example.reachme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.reachme.Fragment.AddFragment;
import com.example.reachme.Fragment.HomeFragment;
import com.example.reachme.Fragment.NotificationFragment;
import com.example.reachme.Fragment.ProfileFragment;
import com.example.reachme.Fragment.SearchFragment;
import com.example.reachme.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);
        MainActivity.this.setTitle("My Profile");



        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        binding.toolbar.setVisibility(View.GONE);
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (i){
                    case 0:
                        binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.container, new HomeFragment());
                        break;
                    case 1:
                        binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.container, new NotificationFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.container, new AddFragment());
                        binding.toolbar.setVisibility(View.GONE);
                        break;
                    case 3:
                        transaction.replace(R.id.container, new SearchFragment());
                        binding.toolbar.setVisibility(View.GONE);
                        break;

                    case 4:
                        transaction.replace(R.id.container, new ProfileFragment());
                        binding.toolbar.setVisibility(View.VISIBLE);
                        break;
                }
                transaction.commit();


            }
        });

    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
