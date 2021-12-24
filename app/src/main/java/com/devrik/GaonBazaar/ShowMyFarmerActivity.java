package com.devrik.GaonBazaar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.devrik.GaonBazaar.Model.MyFarmerModel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.MyFarmerAdapter;
import com.devrik.GaonBazaar.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowMyFarmerActivity extends AppCompatActivity {

    Context context = ShowMyFarmerActivity.this;

    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager ;
    ArrayList<MyFarmerModel> myFarmerModelArrayList = new ArrayList<>();;
    ImageView back;
    RecyclerView rv_myfarmer;

    String USERID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_farmer);
        back=findViewById(R.id.back);
        rv_myfarmer=findViewById(R.id.rv_myfarmer);
        progressBar=findViewById(R.id.progressBar);

        My_Farmer();

        USERID= SharedHelper.getKey(ShowMyFarmerActivity.this, APPCONSTANT.id);
        Log.e("gffdfrg", USERID);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void My_Farmer() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_farmer)
                .addBodyParameter("user_id",USERID)
               // .addBodyParameter("user_id",USERID)
                .setTag("showfarmer")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("safdadfa", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);
                                MyFarmerModel farmerModel = new MyFarmerModel();

                                farmerModel.setImage(jsonObject.getString("image"));
                                farmerModel.setPath(jsonObject.getString("path"));
                                farmerModel.setName(jsonObject.getString("name"));
                                farmerModel.setId(jsonObject.getString("id"));
                                farmerModel.setBlock(jsonObject.getString("block"));
                                farmerModel.setDistrict(jsonObject.getString("district"));
                                farmerModel.setEmail(jsonObject.getString("email"));
                                farmerModel.setOtp(jsonObject.getString("otp"));
                                farmerModel.setParent_name(jsonObject.getString("parent_name"));
                                farmerModel.setPassword(jsonObject.getString("password"));
                                farmerModel.setPhone(jsonObject.getString("phone"));
                                farmerModel.setPhoto(jsonObject.getString("photo"));
                                farmerModel.setState(jsonObject.getString("state"));
                                farmerModel.setStatus(jsonObject.getString("status"));
                                farmerModel.setType(jsonObject.getString("type"));
                                farmerModel.setTypeof_id(jsonObject.getString("typeof_id"));
                                farmerModel.setUser_id(jsonObject.getString("user_id"));
                                farmerModel.setVillage(jsonObject.getString("village"));

                                myFarmerModelArrayList.add(farmerModel);
                            }
                            rv_myfarmer.setHasFixedSize(true);
                            rv_myfarmer.setLayoutManager(new LinearLayoutManager(ShowMyFarmerActivity.this, RecyclerView.VERTICAL, false));
                            rv_myfarmer.setAdapter(new MyFarmerAdapter(context,myFarmerModelArrayList));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("dgfffdf", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("fhsdds", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }


}