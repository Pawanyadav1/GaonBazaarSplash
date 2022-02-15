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

public class AboutUsActivity extends AppCompatActivity {

    TextView tv_about,tv_title;
  ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        tv_about=findViewById(R.id.tv_about);
        tv_title=findViewById(R.id.tv_title);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        about();

    }


    public void  about(){
        AndroidNetworking.post(API.about_us)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hghsddsd", response.toString());

                        tv_about.setText((response.optString("description")));
                        tv_title.setText((response.optString("title")));


                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}