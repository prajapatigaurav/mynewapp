package com.doorstep.priyagupta.partner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MailActivity extends AppCompatActivity {
     Button btmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
      btmail=findViewById(R.id.bt_mail);
      getSupportActionBar().setTitle("Identity Verification");
      btmail.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              document();
          }
      });
    }

    private void document() {
        Intent send = new Intent(Intent.ACTION_SEND);

        //      Boolean ilogin=true;
        send.putExtra(Intent.EXTRA_EMAIL, new String[]{"lavi.saini28@gmail.com"});
        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //    send.putExtra(Intent.EXTRA_SUBJECT, "");
        //   send.putExtra(Intent.EXTRA_TEXT, "");
        send.setType("message/rfc822");   // code fro email
        startActivity(Intent.createChooser(send, "Select Email to Send:"));
        editor.putBoolean("ilogin",true);
        editor.apply();
    }
}
