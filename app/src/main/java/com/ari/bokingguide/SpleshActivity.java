package com.ari.bokingguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

public class SpleshActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);
        StatusBarUtil.setTranslucent(SpleshActivity.this, 1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    startActivity(new Intent(SpleshActivity.this, MenuActivity.class));
                }

            }
        }).start();
    }
}
