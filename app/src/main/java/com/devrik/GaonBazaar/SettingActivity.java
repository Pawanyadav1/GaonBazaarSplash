package com.devrik.GaonBazaar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;

public class SettingActivity extends AppCompatActivity {
    ImageView iv_logout,iv_back;
    TextView tv_about,tv_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        iv_logout = findViewById(R.id.iv_logout);
        tv_about = findViewById(R.id.tv_about);
        iv_back = findViewById(R.id.iv_back);
        tv_privacy = findViewById(R.id.tv_privacy);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,PrivacyPolicyActivity.class));
            }
        });

        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,AboutUsActivity.class));
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(SettingActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(SettingActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to logout in this app")
                        .setPositiveButton(Html.fromHtml("<font color='#0E0E4C'>Ok</font>"), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(final DialogInterface dialog, int which) {
                                SharedHelper.putkey(SettingActivity.this, APPCONSTANT.id,"");
                                Intent intent = new Intent(SettingActivity.this, SplashGaonScreen.class);
                                if(Build.VERSION.SDK_INT >= 11) {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                } else {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#0E0E4C'>Cancel</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.ic_twotone_power_settings_new_24)
                        .show();
            }
        });
    }
}