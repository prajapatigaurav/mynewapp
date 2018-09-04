package com.doorstep.priyagupta.partner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
   RequestQueue rq;
    Spinner spinner, spinner1;
    EditText et_name, et_email, et_phone;
    Button btn_send;
    Button textview_login;
    String cname, cemail, cphone;
    String ss="your account is successfully created";
    String URL="http://searchkero.com/techsource/Crazy/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

      //  getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().hide();


        initial();
    }

    private void initial()
    {
        rq= Volley.newRequestQueue(this);
        textview_login=findViewById(R.id.textview_login);
        btn_send = findViewById(R.id.btn_send);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        // et_city=findViewById(R.id.et_city);
        et_phone = findViewById(R.id.et_phone);
        spinner = findViewById(R.id.et_city);
        spinner.setOnItemSelectedListener(this);
        //btn_share = findViewById(R.id.btn_share);
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select your city");
        categories.add("Ahmadabad");
        categories.add("Mumbai");
        categories.add("Pune");
        categories.add("Thane");
        categories.add("Kolkata");

        List<String> list = new ArrayList<String>();
        list.add("Select category");
        list.add("AC Repair And Services");
        list.add("Chimney Repair And Services");
        list.add("Geyser Repair And Services");
        list.add("Microwave Repair And Services");
        list.add("Refrigerator Repair And Services");
        list.add("RO Repair And Services");
        list.add("TV Repair And Services");
        list.add("Washing Machine Repair And Services");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner1.setAdapter(dataAdapter1);

        textview_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
             Intent loginIntent=new Intent(getApplicationContext(),MainActivity.class);
             startActivity(loginIntent);

             }
         });



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    private void send() {
        cname = et_name.getText().toString();
        cemail = et_email.getText().toString();
        cphone = et_phone.getText().toString();
        if (!validate()) {
            Toast.makeText(getApplicationContext(), "Please fill correct entries", Toast.LENGTH_LONG).show();
        } else {
            onaccountsucces();
        }
    }

    private void onaccountsucces() {
        StringRequest str=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               et_name.setText("");
               et_email.setText("");
               et_phone.setText("");

                  // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();;
                   SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putString("name",cname);
                   editor.putString("email",cemail);
                   editor.putString("phone",cphone);
                   editor.putBoolean("isloged",true);
                   editor.apply();
                Toast.makeText(getApplicationContext(),"your account is created",Toast.LENGTH_LONG).show();
                   Intent intentview=new Intent(ProfileActivity.this,MainActivity.class);
                   startActivity(intentview);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> maping=new HashMap<>();
                maping.put("name",cname);
                maping.put("email",cemail);
                maping.put("phone",cphone);
                return maping;
            }
        };
    rq.add(str);
    }

    private boolean validate() {
        boolean valid = true;
        if (cname.isEmpty()) {
            et_name.setError("please enter valid name");
            et_name.requestFocus();
            valid = false;
        }
        if (cemail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(cemail).matches()) {
            et_email.setError("please enter valid email");
            valid = false;

        }
        if (cphone.isEmpty()) {
            et_phone.setError("please enter phone number");
            valid = false;
        }
        if (cphone.length() > 10) {

            et_phone.setError("phone number is not correct");
            valid = false;
        }
        if (!Patterns.PHONE.matcher(cphone).matches()) {
            et_phone.setError("please enter valid phone number");
            valid = false;
        }
        return valid;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


}

