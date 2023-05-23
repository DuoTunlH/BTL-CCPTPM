package com.example.app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen extends AppCompatActivity {
    private static int SPLASH_TIMES_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent intent = new Intent(Splash_Screen.this, DangNhap.class);
                 startActivity(intent);
                 overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
             }
         }, SPLASH_TIMES_OUT);
    }
}