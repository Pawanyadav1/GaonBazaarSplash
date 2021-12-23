package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpGaon extends AppCompatActivity {
    EditText name, mobile, etpassword;
    MaterialButton btnSignup;
    ProgressBar progressBar;
     String USERTYPE = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_gaon);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        progressBar = findViewById(R.id.progressBar);
        etpassword = findViewById(R.id.etpassword);
        btnSignup = findViewById(R.id.btnSignup);

        USERTYPE = SharedHelper.getKey(SignUpGaon.this,APPCONSTANT.USERTYPE);
        Log.e("dfag",USERTYPE);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateDetail();

            }
        });
    }

    private void validateDetail() {

        if (name.getText().toString().trim().equals("")) {
            name.setError("please enter name");
            name.requestFocus();
        } else if (mobile.getText().toString().trim().equals("")) {
            mobile.setError("enter your mobile number");
            mobile.requestFocus();
        } else if (etpassword.getText().toString().trim().equals("")) {
            etpassword.setError("please enter password");
            etpassword.requestFocus();
        } else {
                 signup();
        }
    }
    private void signup() {
        progressBar.setVisibility(View.VISIBLE);
       Log.e("dedttry", USERTYPE );
        AndroidNetworking.post(API.Signup)
                .addBodyParameter("type", USERTYPE)
                .addBodyParameter("name", name.getText().toString().trim())
                .addBodyParameter("phone", mobile.getText().toString().trim())
                .addBodyParameter("password", etpassword.getText().toString().trim())
                .setTag("signup")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("show_error", response.toString() );

                        try {
                             if (response.getString("result").equals("successful")) {
                                 Toast.makeText(SignUpGaon.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(SignUpGaon.this,Otp_Sign_Up_Activity.class));
                                 SharedHelper.putkey(SignUpGaon.this,APPCONSTANT.id,response.getString("id"));
                                 SharedHelper.putkey(SignUpGaon.this,APPCONSTANT.MOBILE,response.getString("phone"));

                             }else {
                                 Toast.makeText(SignUpGaon.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                             Log.e("unsucces", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("unsucesds", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}


