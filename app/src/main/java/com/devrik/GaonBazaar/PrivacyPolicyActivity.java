package com.devrik.GaonBazaar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.others.API;

import org.json.JSONObject;

public class PrivacyPolicyActivity extends AppCompatActivity {

    TextView text_policy,text_tital;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        text_policy=findViewById(R.id.text_policy);
        text_tital=findViewById(R.id.text_tital);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        privacy();
    }
    public void  privacy(){
        AndroidNetworking.post(API.privacy_policy)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hghsddsd", response.toString());

                        text_policy.setText((response.optString("description")));
                        text_tital.setText((response.optString("title")));

                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}