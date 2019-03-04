package com.ari.bokingguide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.ari.bokingguide.fragment.FragmentDestinasi;
import com.ari.bokingguide.fragment.FragmentGuide;
import com.ari.bokingguide.fragment.FragmentWisatawan;
import com.ari.bokingguide.utils.Constants;

import org.salient.artplayer.MediaPlayerManager;

public class MenuAdminActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_wisatawan:
                    transaction.replace(R.id.frame, new FragmentWisatawan()).commit();
                    return true;
                case R.id.navigation_guide:
                    transaction.replace(R.id.frame, new FragmentGuide()).commit();
                    return true;
                case R.id.navigation_destinasi:
                    transaction.replace(R.id.frame, new FragmentDestinasi()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Boking Guide Admin");
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, new FragmentWisatawan()).commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Keluar();
            return true;
        } else if (id == R.id.action_notif) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Keluar();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void Keluar() {
        AlertDialog.Builder b = new AlertDialog.Builder(this)
                .setTitle("Yakin ingin keluar!")
                .setPositiveButton("Keluar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(Constants.LOGGEDIN_ADMIN_SHARED_PREF, false);
                                editor.commit();
                                Intent intent = new Intent(MenuAdminActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                )
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
        b.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayerManager.instance().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerManager.instance().releasePlayerAndView(this);
    }
}
