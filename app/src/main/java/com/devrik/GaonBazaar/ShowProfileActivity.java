package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.devrik.GaonBazaar.Model.profilemodel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowProfileActivity extends AppCompatActivity {
ImageView img_back,idcardphoto;
CircleImageView myprofile_photo;
TextView name,fname,mobilenumber,State,District,Block,Village,id_card,edit_profile;
String USERID="",ID_Card="";
ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        img_back=findViewById(R.id.img_back);
        myprofile_photo=findViewById(R.id.myprofile_photo);
        name=findViewById(R.id.name);
        fname=findViewById(R.id.fname);
        mobilenumber=findViewById(R.id.mobilenumber);
        id_card=findViewById(R.id.id_card);
        State=findViewById(R.id.State);
        edit_profile=findViewById(R.id.edit_profile);
        District=findViewById(R.id.District);
        Block=findViewById(R.id.Block);
        Village=findViewById(R.id.Village);
        idcardphoto=findViewById(R.id.idcardphoto);
        progressBar=findViewById(R.id.progressBar);
        USERID= SharedHelper.getKey(ShowProfileActivity.this, APPCONSTANT.id);
        ID_Card= SharedHelper.getKey(ShowProfileActivity.this, APPCONSTANT.USER_ID_CARD);

     // id_card.setText(ID_Card);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowProfileActivity.this,HomeScreenActivity.class));
            }
        });
        showprofile();

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowProfileActivity.this,UpDateProfileActivity.class));
            }
        });

    }


    public void showprofile(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_profile)
                .addBodyParameter("user_id",USERID)
                .setPriority(Priority.HIGH)
                .setTag("show_profile")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("smdfhg", response.toString());
                        try {

                            profilemodel profilemodel = new profilemodel();
                           name.setText(response.getString("name"));
                           fname.setText(response.getString("parent_name"));
                           mobilenumber.setText(response.getString("phone"));
                           State.setText(response.getString("state_name"));
                           District.setText(response.getString("district_name"));
                           Block.setText(response.getString("block_name"));
                           Village.setText(response.getString("village_name"));
                           id_card.setText(response.getString("typeof_id"));
                           profilemodel.setPhoto(response.getString("photo"));
                           profilemodel.setImage(response.getString("image"));
                           profilemodel.setPath(response.getString("path"));

                            Glide.with(ShowProfileActivity.this)
                                    .load(profilemodel.getPath()+profilemodel.getImage())
                                    .into(myprofile_photo);
                            Log.e("rgfdfgdgf", profilemodel.getPath()+profilemodel.getImage());

                            Log.e("kdujhgf",response.getString("photo"));
                            Log.e("kdujhgf",response.getString("image"));
                            Log.e("kdujhgf",response.getString("path"));

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("dgjhsdd", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dghadfs", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }
}