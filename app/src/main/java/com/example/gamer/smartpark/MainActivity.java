package com.example.gamer.smartpark;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, estacionamientos.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(MainActivity.this,estacionamientos.class);

    }
}
