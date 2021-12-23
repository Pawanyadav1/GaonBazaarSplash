package com.devrik.GaonBazaar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.devrik.GaonBazaar.fragments.homefragment;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomeScreenActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static DrawerLayout drawer;
    LinearLayout ll_home,ll_profile,ll_addfarmer,ll_showmyfarmer,ll_contact,ll_setting,ll_logout;
    NavigationView navigation;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.nav_view);
        progressBar = findViewById(R.id.progressBar);
        ll_home = findViewById(R.id.ll_home);
        ll_profile = findViewById(R.id.ll_profile);
        ll_showmyfarmer = findViewById(R.id.ll_showmyfarmer);
        ll_contact = findViewById(R.id.ll_contact);
        ll_addfarmer = findViewById(R.id.ll_addfarmer);
        ll_setting = findViewById(R.id.ll_setting);
        ll_logout = findViewById(R.id.ll_logout);

        ll_home.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_contact.setOnClickListener(this);
        ll_addfarmer.setOnClickListener(this);
        ll_showmyfarmer.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_logout.setOnClickListener(this);

        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
     //   bottomNavigationView.setOnNavigationItemSelectedListener(this);

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(HomeScreenActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(HomeScreenActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to logout in the app")
                        .setPositiveButton(Html.fromHtml("<font color='#008037'>Ok</font>"), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(final DialogInterface dialog, int which) {
                                SharedHelper.putkey(HomeScreenActivity.this, APPCONSTANT.id,"");
                                SharedHelper.putkey(HomeScreenActivity.this,APPCONSTANT.USERTYPE,"");
                                Intent intent = new Intent(HomeScreenActivity.this, SelectTypeActivity.class);
                                if(Build.VERSION.SDK_INT >= 11) {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                } else {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#008037'>Cancel</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.ic_twotone_power_settings_new_24)
                        .show();
            }
        });


        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homefragment()).addToBackStack(null).commit();
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_home:
                startActivity(new Intent(HomeScreenActivity.this,HomeScreenActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_profile:
               startActivity(new Intent(HomeScreenActivity.this,ShowProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_addfarmer:
                startActivity(new Intent(HomeScreenActivity.this,VlvAddFarmerActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_showmyfarmer:
                startActivity(new Intent(HomeScreenActivity.this,WellcomeAddfarmerVlv.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_contact:
                startActivity(new Intent(HomeScreenActivity.this, ContectUsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_setting:

                //startActivity(new Intent(HomeScreenActivity.this, WellcomeAddfarmerVlv.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_logout:
                drawer.closeDrawer(GravityCompat.START);
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homefragment()).commit();
                break;

            case R.id.profile:
                //startActivity(new Intent(HomeScreenActivity.this, BuyItemActivity.class));
                break;
        }
        return true;

    }
}
     /* //  int imgarray[]={R.drawable.crop,R.drawable.spry,R.drawable.gaon,R.drawable.farmer,R.drawable.financial,R.drawable.futurepri};


        sell_item = findViewById(R.id.sell_item);
        buyitems = findViewById(R.id.buyitems);
        txt1 = findViewById(R.id.txt1);
        mail_contact = findViewById(R.id.mail_contact);
        buy1 = findViewById(R.id.buy1);
        logout = findViewById(R.id.logout);
        menu =findViewById(R.id.menu);

       *//* flipper=findViewById(R.id.flipper);

        for (int i = 0; i <imgarray.length ; i++) {
            showimage(imgarray[i]);

        }*//*

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(HomeScreenActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(HomeScreenActivity.this);
                }
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to logout in the app")
                        .setPositiveButton(Html.fromHtml("<font color='#008037'>Ok</font>"), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(final DialogInterface dialog, int which) {
                                Intent intent = new Intent(HomeScreenActivity.this, SelectTypeActivity.class);
                                if(Build.VERSION.SDK_INT >= 11) {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                } else {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(Html.fromHtml("<font color='#008037'>Cancel</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.ic_twotone_power_settings_new_24)
                        .show();
            }
        });


        mail_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this,ContectUsActivity.class);
                startActivity(intent);
            }
        });

        sell_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity .this,SellItemActivity.class);
                startActivity(intent);
            }
        });

        buyitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity .this,BuyItemActivity.class);
                startActivity(intent);
            }
        });

    }

   *//* public void showimage(int img)
    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this,android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }*//*
}*/