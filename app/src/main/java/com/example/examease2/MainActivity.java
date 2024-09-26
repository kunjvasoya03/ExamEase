package com.example.examease2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examease2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    FrameLayout main_frame;
    TextView DrawerProfileName;
    TextView DrawerProfileEmail;

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == R.id.nav_home) {
                setFragment(new HomeFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_acc) {
                setFragment(new ProfileFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_leaderboard) {
                setFragment(new LeaderBoardFragment());
                return true;
            }
            return false;
        }
    };

    NavigationView.OnNavigationItemSelectedListener drawerNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle drawer menu item clicks here
            if(item.getItemId()==R.id.nav_home)
                    setFragment(new HomeFragment());
                else if(item.getItemId()==R.id.nav_acc)
                    setFragment(new ProfileFragment());
            else if (item.getItemId()==R.id.nav_leaderboard)
                setFragment(new LeaderBoardFragment());

                // Add more cases for other items if needed

            // Close the drawer when an item is selected
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        main_frame = findViewById(R.id.main_frame);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_acc, R.id.nav_leaderboard)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set up the drawer profile information
        DrawerProfileEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        DrawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_profile_circle);
        String name = DBQuery.myProfile.getName().toUpperCase();
        DrawerProfileEmail.setText(DBQuery.myProfile.getName());
        DrawerProfileName.setText(name.substring(0, 1));

        // Set the listener for the drawer
        navigationView.setNavigationItemSelectedListener(drawerNavigationItemSelectedListener);

        // Default fragment to display
        setFragment(new HomeFragment());
    }

    void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(), fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
