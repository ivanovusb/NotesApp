package com.example.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notesapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_notes:
                        Toast.makeText(MainActivity.this, "Notes", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        return true;

                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        return true;

                    case R.id.action_about:
                        Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        return true;
                }
                return false;
            }
        });

        if (savedInstanceState != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NotesListFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new StartScreenFragment())
                    .commit();

            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NotesListFragment())
                            .commit();
                }
            });
            thread.start();
        }
    }
}