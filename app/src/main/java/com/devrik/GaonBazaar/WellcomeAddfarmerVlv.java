package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;

public class WellcomeAddfarmerVlv extends AppCompatActivity {
    public static DrawerLayout drawer;
    ImageView back,menu;
    MaterialCardView farmer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_addfarmer_vlv);

        back=findViewById(R.id.back);
        menu=findViewById(R.id.menu);
        farmer1=findViewById(R.id.farmer1);
        drawer = findViewById(R.id.drawer);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WellcomeAddfarmerVlv.this,HomeScreenActivity.class));
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreenActivity.drawer.openDrawer(GravityCompat.START);
            }
        });

        farmer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WellcomeAddfarmerVlv.this,ShowMyFarmerActivity.class));
            }
        });

    }
}