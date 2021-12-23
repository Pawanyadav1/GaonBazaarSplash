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

import org.json.JSONObject;

public class Otp_Sign_Up_Activity extends AppCompatActivity {
    EditText edittextotp;
    Button button;
    String Mobile="";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__sign__up);

        Mobile = SharedHelper.getKey(Otp_Sign_Up_Activity.this, APPCONSTANT.MOBILE);
        Log.e("dsgfgs", Mobile);

        edittextotp = findViewById(R.id.edittextotp);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    validateDetail();
            }
        });
    }

    private void validateDetail() {
        if (edittextotp.getText().toString().equals("")) {
            edittextotp.setError("Please Enter OTP");
            edittextotp.requestFocus();

        } else {

            otpsignUpGoan();
        }
    }
    private void otpsignUpGoan(){
        progressBar.setVisibility(View.VISIBLE);
        Log.e("fdfdfdf", Mobile);
        AndroidNetworking.post(API.otp_verfication)
                .addBodyParameter("otp",edittextotp.getText().toString().trim())
                .addBodyParameter("phone",Mobile)
                .setTag("otp")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("fnskjv",response.toString());
                        try {
                            if (response.optString("result").equals("Login Successful")){
                                Toast.makeText(Otp_Sign_Up_Activity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Otp_Sign_Up_Activity.this,UpDateProfileActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(Otp_Sign_Up_Activity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("eofji",e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("vfcjh",anError.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }
}
