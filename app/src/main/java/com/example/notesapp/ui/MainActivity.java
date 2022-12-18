package com.example.notesapp.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.testnotification.CustomViewDialogFragment;
import com.example.notesapp.testnotification.NotificationsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    public static OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
        }
    };

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
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new NotesListFragment())
                                .addToBackStack("notes_list")
                                .commit();
                        drawerLayout.close();
                        return true;

                    case R.id.action_settings:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new NotesSettingsFragment())
                                .addToBackStack("notes_list")
                                .commit();
                        drawerLayout.close();
                        return true;

                    case R.id.action_about:
                        Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        return true;

                    case R.id.action_notification:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new NotificationsFragment())
                                .addToBackStack("notes_list")
                                .commit();
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



    @Override
    public void onBackPressed() {

        if (onBackPressedCallback.isEnabled()) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.exit_dialog_title))
                    .setMessage(getResources().getString(R.string.exit_dialog_message))
                    .setIcon(R.drawable.ic_error_24)
                    .setCancelable(false)
                    .setPositiveButton(R.string.positive_answer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.negative_answer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}


