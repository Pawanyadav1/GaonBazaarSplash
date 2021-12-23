package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends AppCompatActivity {
EditText change;
ProgressBar progressBar;
Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        change = findViewById(R.id.change);
        button2 = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDetail();
            }
        });

        }
    private void validateDetail() {
        if (change.getText().toString().equals("")) {
            change.setError("please enter Mobile Number");
            change.requestFocus();

        } else {

            forget();

        }

    }

    private void forget() {
        progressBar.setVisibility(View.VISIBLE);
            AndroidNetworking.post(API.forget_password)
                    .addBodyParameter("phone",change.getText().toString().trim())
                    .setTag("forgetpassword")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("sdsdasdsd", response.toString() );
                            try {
                                if (response.getString("result").equals("Successful")) {
                                    JSONArray jsonArray = new JSONArray(response.getString("data"));
                                    for (int i =0; i < jsonArray.length(); i++) {
                                        JSONObject j = jsonArray.getJSONObject(i);
                                        SharedHelper.getKey(ForgetPasswordActivity.this, APPCONSTANT.id);
                                        Intent intent = new Intent(ForgetPasswordActivity.this, HomeScreenActivity.class);
                                        startActivity(intent);
                                    }
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressBar.setVisibility(View.GONE);
                            }
                        }




                        @Override
                        public void onError(ANError anError) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("xSscSsdssxsx", anError.getMessage());
                        }
                    });



    }
 }
