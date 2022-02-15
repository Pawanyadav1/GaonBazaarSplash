package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.others.API;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;


public class ContectUsActivity extends AppCompatActivity {
    EditText et_email, et_massage,et_name,et_moblie;
    ImageView back_btn,call_btn;
    MaterialButton btn_sand_massage;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contect_us);


        btn_sand_massage = findViewById(R.id.btn_sand_massage);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_moblie = findViewById(R.id.et_moblie);
        et_massage = findViewById(R.id.et_massage);
        back_btn = findViewById(R.id.back_btn);
        call_btn = findViewById(R.id.call_btn);
        progressBar = findViewById(R.id.progressBar);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContectUsActivity.this,HomeScreenActivity.class));
               finish();
            }
        });

      /*  call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContectUsActivity.this,));
            }
        });*/

        btn_sand_massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dsfdfsd", "onClick: ");
                validateDetail();
            }
        });
    }

    private void validateDetail() {
        if (et_name.getText().toString().equals("")) {
            et_name.setError("please enter Name ");
            et_name.requestFocus();

        } else if (et_moblie.getText().toString().equals("")) {
            et_moblie.setError("please enter moblie ");
            et_moblie.requestFocus();

        } else if (et_email.getText().toString().equals("")) {
                et_email.setError("please enter email ");
                et_email.requestFocus();

            } else if(et_massage.getText().toString().equals("")) {
                et_massage.setError("please enter massage");
                et_massage.requestFocus();
            }
            else {

                contact();

            }

        }

        private void contact() {
            progressBar.setVisibility(View.VISIBLE);
            AndroidNetworking.post(API.contact_us)
                    .addBodyParameter("name", et_name.getText().toString().trim())
                    .addBodyParameter("mobile", et_moblie.getText().toString().trim())
                    .addBodyParameter("email",et_email.getText().toString().trim())
                    .addBodyParameter("messages",et_massage.getText().toString().trim())
                    .setTag("contact us")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("dhkjfb",response.toString());
                            try {
                                if (response.getString("result").equals("successful")){

                                  finish();

                                }else {

                                    Toast.makeText(ContectUsActivity.this,"sand massage successfully"+response.getString("result"),Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("fjsghdfs",e.getMessage());
                                progressBar.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("jdchbgf",anError.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    });


        }
}


