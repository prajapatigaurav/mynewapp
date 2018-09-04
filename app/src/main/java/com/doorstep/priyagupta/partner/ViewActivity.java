package com.doorstep.priyagupta.partner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    TextView tv_sendid,tv_viewname,tv_about;
    ImageView iv_view;
    //ImageButton iv_button;
    String stvname;
    Boolean alogin,ilogin;
    Button bt_approval;
    LinearLayout linearLayout;
    SharedPreferences sharedPreferences;
    private static final String TAG = "ProfileActivity";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        tv_viewname=findViewById(R.id.tv_showname);
        bt_approval=findViewById(R.id.bt_approval);
        iv_view=findViewById(R.id.iv_view);
         linearLayout=findViewById(R.id.linear);
         //iv_button=findViewById(R.id.iv_button);
         tv_sendid=findViewById(R.id.sendid);
         tv_about=findViewById(R.id.tv_aboutme);
         //stvname=tv_viewname.getText().toString();
         getSupportActionBar().setTitle("Profile");
          sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
         stvname=sharedPreferences.getString("name","");

         String value=sharedPreferences.getString("image","");
       alogin=sharedPreferences.getBoolean("alogin",false);
      // mlogin=sharedPreferences.getBoolean("mlogin",false);
       ilogin=sharedPreferences.getBoolean("ilogin",false);

         tv_viewname.setText(stvname);
         iv_view.setImageBitmap(decodeFromBase64ToBitmap(value));

         bt_approval.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 forapproval();
             }
         });
         tv_about.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 gotoaboutme();
             }
         });
         iv_view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SelectImage();
             }
         });
         tv_sendid.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 senddata();
             }
         });
     }

    private void forapproval() {
      if (alogin==true  && ilogin==true){
      /*    Snackbar snackbar=Snackbar.make(linearLayout,"pending for approval",Snackbar.LENGTH_LONG);
          snackbar.show();*/
          Intent afterprofileintent=new Intent(getApplicationContext(),AfterProfilleActivity.class);
          startActivity(afterprofileintent);
      }
      else {
          Snackbar snackbar=Snackbar.make(linearLayout,"Please  fill your detail",Snackbar.LENGTH_LONG);
          snackbar.show();
      }

     }

    private void gotoaboutme() {
    Intent gotoabout=new Intent(ViewActivity.this,AboutmeActivity.class);
    startActivity(gotoabout);
     }


    private void senddata()
    {
      /*  Intent send = new Intent(Intent.ACTION_SEND);

  //      Boolean ilogin=true;
        send.putExtra(Intent.EXTRA_EMAIL, new String[]{"guptapriya500@gmail.com"});
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //    send.putExtra(Intent.EXTRA_SUBJECT, "");
     //   send.putExtra(Intent.EXTRA_TEXT, "");
        send.setType("message/rfc822");   // code fro email
        startActivity(Intent.createChooser(send, "Select Email to Send:"));
        editor.putBoolean("ilogin",true);
        editor.apply();*/
    Intent mailintent=new Intent(getApplicationContext(),MailActivity.class);
    startActivity(mailintent);
    }

    private void SelectImage() {

         Intent gotosetprofile=new Intent(ViewActivity.this,ChangeProfileActivity.class);
         startActivity(gotosetprofile);
 }


   private Bitmap decodeFromBase64ToBitmap(String encodedImage)
   {
       SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
       byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
       Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       return decodedByte;
   }
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}