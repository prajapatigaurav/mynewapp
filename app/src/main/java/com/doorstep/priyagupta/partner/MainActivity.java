package com.doorstep.priyagupta.partner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue1;
    Button bt_loginsignup;

    EditText editText_email,editText_phone;
    String seditText_email,sedit_Text_phone;
    public static final String LOGIN_URL ="http://searchkero.com/techsource/Crazy/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue1= Volley.newRequestQueue(this);
        getSupportActionBar().hide();
        bt_loginsignup=(Button)findViewById(R.id.bt_login);

        editText_email=(EditText)findViewById(R.id.edit_Text_email);
        editText_phone=(EditText)findViewById(R.id.edit_Text_phone);
        bt_loginsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginaccount();
            }
        });


    }

    private void loginaccount() {
        seditText_email=editText_email.getText().toString();
        sedit_Text_phone=editText_phone.getText().toString();
        if (sedit_Text_phone.trim().equalsIgnoreCase("")) {
            editText_email.setError("This field can not be blank");
            editText_email.requestFocus();
        }

        if (sedit_Text_phone.isEmpty()) {
            editText_phone.setError("please enter phone number");
            editText_phone.requestFocus();
        }
        if (sedit_Text_phone.length() > 10) {

            editText_phone.setError("phone number is not correct");
            editText_phone.requestFocus();
        }
        if (!Patterns.PHONE.matcher(sedit_Text_phone).matches()) {
            editText_phone.setError("please enter valid phone number");
            editText_phone.requestFocus();
        }

        else {

            StringRequest lstr=new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("Login success"))
                    {
                        editText_email.setText("");
                        editText_phone.setText("");

                        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
              /*          editor.putString("email",seditText_email);
                        editor.putString("phone",sedit_Text_phone);*/
                        editor.putBoolean("bloged",true);
                        editor.apply();
                        Toast.makeText(getApplicationContext(),"Login Success ",Toast.LENGTH_LONG).show();
                        Intent intentloginnextview=new Intent(getApplicationContext(),ViewActivity.class);
                        startActivity(intentloginnextview);
                    }

                    else {

                        Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> maping=new HashMap<>();
                    maping.put("email",seditText_email);
                    maping.put("phone",sedit_Text_phone);
                    return maping;
                }

            };

            requestQueue1.add(lstr);


        }

    }


}