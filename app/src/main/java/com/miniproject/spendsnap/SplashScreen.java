package com.miniproject.spendsnap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


// Remove the action bar (optional, if you have an action bar)



        setContentView(R.layout.splash_screen);



        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this,LoginPage.class));
            finish();
        }, 1000);
    }
}
