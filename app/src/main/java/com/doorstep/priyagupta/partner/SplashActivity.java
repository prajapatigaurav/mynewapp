package com.doorstep.priyagupta.partner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.iv);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        imageView.startAnimation(animation);

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        final Boolean isloged=sharedPreferences.getBoolean("isloged",false);
        final Boolean bloged=sharedPreferences.getBoolean("bloged",false);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isloged==true && bloged==true)
                {
                    Intent intent=new Intent(SplashActivity.this,ViewActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            }
        },3000);



    }




}


