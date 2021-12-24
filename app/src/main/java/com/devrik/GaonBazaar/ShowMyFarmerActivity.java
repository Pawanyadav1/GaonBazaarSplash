package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ShowMyFarmerActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView rv_myfarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_farmer);
        back=findViewById(R.id.back);
        rv_myfarmer=findViewById(R.id.rv_myfarmer);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowMyFarmerActivity.this,WellcomeAddfarmerVlv.class));
            }
        });
    }
}