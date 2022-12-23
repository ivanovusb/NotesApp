package com.example.notesapp.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.testnotification.CustomViewDialogFragment;
import com.example.notesapp.testnotification.NotificationsFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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

        getSupportFragmentManager()
                .setFragmentResultListener(AuthFragment.KEY_RESULT_AUTHORIZED, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        showNotes();
                    }
                });

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
//            if (isAuthorized()) {
                showNotes();
//            } else {
//                showAuth();
//            }
        } else {
            showStartScreen();

            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    if (isAuthorized()) {
                        showNotes();
//                    } else {
//                        showAuth();
//                    }
                }
            });
            thread.start();
        }
    }

    private void showStartScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new StartScreenFragment())
                .commit();
    }

    private void showNotes() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NotesListFragment())
                .commit();
    }

    private boolean isAuthorized() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    private void showAuth() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AuthFragment())
                .commit();
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


