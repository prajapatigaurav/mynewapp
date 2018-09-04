package com.doorstep.priyagupta.partner;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AfterProfilleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_profille);
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);

    }
}
