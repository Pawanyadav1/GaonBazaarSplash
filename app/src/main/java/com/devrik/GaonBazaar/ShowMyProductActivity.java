package com.devrik.GaonBazaar;

import android.content.Context;
import android.content.Intent;
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
import com.devrik.GaonBazaar.Model.MyModel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.devrik.GaonBazaar.others.ShowMyProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowMyProductActivity extends AppCompatActivity {

    Context context = ShowMyProductActivity.this;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<MyModel> myModelArrayList = new ArrayList<>();
    RecyclerView rv_myProduct;
    ImageView back;
    ProgressBar progressBar;

    String User_ID="";
    String ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_product);
        rv_myProduct = findViewById(R.id.rv_myProduct);
        back=findViewById(R.id.back);
        progressBar=findViewById(R.id.progressBar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowMyProductActivity.this,SellItemActivity.class));
                finish();
            }
        });

        ID = SharedHelper.getKey(ShowMyProductActivity.this,APPCONSTANT.id);

        show_product();
    }
    public void show_product() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("fdsfds", User_ID+"");
        Log.e("ID", ID+"");
        AndroidNetworking.post(API.show_my_product)
                .addBodyParameter("user_id",ID)
                .setTag("product")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.getString(i));
                                MyModel mymodel = new MyModel();
                                mymodel.setCommdity(jsonObject.getString("commdity"));
                                Log.e("commdity", jsonObject.getString("commdity") );
                                mymodel.setVariety(jsonObject.getString("variety"));
                                mymodel.setBrand(jsonObject.getString("brand"));
                                mymodel.setCompany(jsonObject.getString("company"));
                                mymodel.setImage(jsonObject.getString("image"));
                                mymodel.setName(jsonObject.getString("name"));
                                mymodel.setPath(jsonObject.getString("path"));
                                mymodel.setBrandName(jsonObject.getString("brand_name"));
                                mymodel.setPrice(jsonObject.getString("price"));
                                mymodel.setCategoryName(jsonObject.getString("category_name"));
                                mymodel.setQuantity(jsonObject.getString("quantity"));
                                mymodel.setStock(jsonObject.getString("stock"));
                                myModelArrayList.add(mymodel);
                                rv_myProduct.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(ShowMyProductActivity.this, RecyclerView.VERTICAL, false);
                                rv_myProduct.setLayoutManager(layoutManager);
                                ShowMyProductAdapter adapter = new ShowMyProductAdapter(context, myModelArrayList);
                                rv_myProduct.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("dfgdfg", e.getMessage());
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dfgdfg", anError.getMessage() );
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}

