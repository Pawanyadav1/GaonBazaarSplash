package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.devrik.GaonBazaar.Model.CategoryModel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.Add_Category_Adapter;
import com.devrik.GaonBazaar.others.SharedHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellItemActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager ;
    ArrayList<CategoryModel> categoryModelArrayList ;
    RecyclerView rv_cate;
    ImageView back;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);
        rv_cate = findViewById(R.id.rv_cate);
        progressBar = findViewById(R.id.progressBar);
        back=findViewById(R.id.back);
        SharedHelper.getKey(SellItemActivity.this,APPCONSTANT.id);
        Add_Category();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellItemActivity.this,HomeScreenActivity.class));
            }
        });
    }
    public void Add_Category() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_category)
                .setTag("showCategory")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("APIcatrespo", response.toString());
                        categoryModelArrayList= new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.e("dfzcc",response.toString());
                                CategoryModel catModel = new CategoryModel();
                                catModel.setImage(jsonObject.getString("image"));
                                Log.e("fdgfg", jsonObject.getString("image"));
                                catModel.setPath(jsonObject.getString("path"));
                                Log.e("fdgufg", jsonObject.getString("path"));
                                catModel.setName(jsonObject.getString("name"));
                                Log.e("fdgjfg", jsonObject.getString("name"));
                                catModel.setId(jsonObject.getString("id"));
                                categoryModelArrayList.add(catModel);
                            }
                            rv_cate.setHasFixedSize(true);
                            layoutManager = new GridLayoutManager(SellItemActivity.this,2);
                            rv_cate.setLayoutManager(layoutManager);
                            Add_Category_Adapter adapter = new Add_Category_Adapter(SellItemActivity.this,categoryModelArrayList);
                            rv_cate.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("dgfffdf", e.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("fhsdddfsds", anError.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}