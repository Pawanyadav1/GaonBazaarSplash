package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInGaonActivity extends AppCompatActivity {
EditText phone;
EditText password;
TextView forget;
Button button2;
TextView text;
String USERTYPE="";
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_gaon);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget);
        button2 = findViewById(R.id.button2);
        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressBar);

        USERTYPE = SharedHelper.getKey(SignInGaonActivity.this,APPCONSTANT.USERTYPE);
        Log.e("fdhgj", USERTYPE);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInGaonActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInGaonActivity.this,SignUpGaon.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateDetail();

            }


        });
    }


    private void validateDetail() {
        if (phone.getText().toString().equals("")) {
            phone.setError("please enter Mobile Number");
            phone.requestFocus();

        } else if (password.getText().toString().equals("")) {
            password.setError("please enter your password");
            password.requestFocus();
        } else {

            signinGoan();

        }
    }

    private void signinGoan(){
        progressBar.setVisibility(View.VISIBLE);
        Log.e("type", USERTYPE);
        Log.e("mobile",phone.getText().toString().trim());
        Log.e("password", password.getText().toString().trim());
        AndroidNetworking.post(API.login)
                .addBodyParameter("type",USERTYPE)
                .addBodyParameter("phone",phone.getText().toString().trim())
                .addBodyParameter("password",password.getText().toString().trim())
                .setTag("signin")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("API_RESPONSE", response.toString() );
                        try {
                            if (response.getString("result").equals("login sucessfully")) {
                                Toast.makeText(SignInGaonActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInGaonActivity.this,HomeScreenActivity.class));
                                SharedHelper.putkey(SignInGaonActivity.this,APPCONSTANT.id,response.getString("id"));
                                Log.e("dmscxa",response.getString("id"));

                                }
                            else {
                                Toast.makeText(SignInGaonActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("success", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("dffgdfg", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}
