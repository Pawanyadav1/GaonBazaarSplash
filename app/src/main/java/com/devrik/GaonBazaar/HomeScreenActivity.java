package com.devrik.GaonBazaar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeScreenActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static DrawerLayout drawer;
    LinearLayout ll_home,ll_profile,ll_addfarmer,ll_showmyfarmer,ll_contact,ll_feedback,ll_setting,ll_logout;
    NavigationView navigation;
    CircleImageView profile;
    TextView text_name,text_num;

    String USERID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.nav_view);
        ll_home = findViewById(R.id.ll_home);
        ll_profile = findViewById(R.id.ll_profile);
        ll_showmyfarmer = findViewById(R.id.ll_showmyfarmer);
        ll_contact = findViewById(R.id.ll_contact);
        ll_addfarmer = findViewById(R.id.ll_addfarmer);
        ll_setting = findViewById(R.id.ll_setting);
        ll_feedback = findViewById(R.id.ll_feedback);
        ll_logout = findViewById(R.id.ll_logout);

        ll_home.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_contact.setOnClickListener(this);
        ll_addfarmer.setOnClickListener(this);
        ll_showmyfarmer.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_logout.setOnClickListener(this);

        USERID=SharedHelper.getKey(HomeScreenActivity.this,APPCONSTANT.id);

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

                case R.id.ll_feedback:
                startActivity(new Intent(HomeScreenActivity.this,FeedBackActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_contact:
                startActivity(new Intent(HomeScreenActivity.this, ContectUsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_setting:

                startActivity(new Intent(HomeScreenActivity.this, SettingActivity.class));
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
        }
        return true;

    }
}
