package com.doorstep.priyagupta.partner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PatternMatcher;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AboutmeActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    RadioButton rd_yes, rd_no;
    EditText ed_presence, ed_number, ed_experience;
    Button bt_aboutsent;
    String srd_y, srd_n, spresence, snumber, sexperience;
    String url = "http://searchkero.com/techsource/Crazy/aboutme.php";
    String ss = "save";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        requestQueue = Volley.newRequestQueue(this);
        rd_yes = findViewById(R.id.radioYes);
        rd_no = findViewById(R.id.radioNo);
        ed_presence = findViewById(R.id.ed_onlinepressence);
        ed_number = findViewById(R.id.ed_number);
        ed_experience = findViewById(R.id.ed_expereince);
        bt_aboutsent = findViewById(R.id.bt_aboutsent);
        getSupportActionBar().setTitle("Enter your Detail");
        bt_aboutsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abooutvalue();
            }
        });
    }

    private void abooutvalue() {
        srd_n = rd_no.getText().toString();
        srd_y = rd_yes.getText().toString();
        snumber = ed_number.getText().toString();
        sexperience = ed_experience.getText().toString();
        spresence = ed_presence.getText().toString();

        if (!validate()) {
            Toast.makeText(getApplicationContext(), "fill up entry", Toast.LENGTH_LONG).show();
        } else {
            SubmitAboutvalue();
        }
    }

    private void SubmitAboutvalue() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ed_presence.setText("");
                ed_number.setText("");
                ed_experience.setText("");
                if (response.equals(ss)) {
                    sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("rdyes",srd_y);
                    editor.putString("rdno",srd_n);
                    editor.putString("presence",spresence);
                    editor.putString("alternaive", snumber);
                    editor.putString("experience",sexperience);
                    editor.putBoolean("alogin",true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    Intent viewintent = new Intent(getApplicationContext(), ViewActivity.class);
                    startActivity(viewintent);
                } else {
                    Toast.makeText(getApplicationContext(), "not save", Toast.LENGTH_LONG).show();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("rdyes", srd_y);
                map.put("rdno", srd_n);
                map.put("presence", spresence);
                map.put("alternaive", snumber);
                map.put("experience", sexperience);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    private boolean validate() {
        boolean valid = true;

        if (snumber.isEmpty()) {
            ed_number.setError("please enter phone number");
            valid = false;
        }
        if (snumber.length() > 10) {

            ed_number.setError("phone number is not correct");
            valid = false;
        }

        if (!Patterns.PHONE.matcher(snumber).matches()) {
            ed_number.setError("please enter valid phone number");
            valid = false;
        }
        return valid;


    }
}