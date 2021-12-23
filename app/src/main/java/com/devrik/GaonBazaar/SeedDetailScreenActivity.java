package com.devrik.GaonBazaar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.Model.MyModel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.devrik.GaonBazaar.others.Show_Product_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeedDetailScreenActivity extends AppCompatActivity {
    Context context = SeedDetailScreenActivity.this;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<MyModel> myModelArrayList = new ArrayList<>();
    RecyclerView rvProduct;
    String value;
    ImageView buy_products,back_btn;
    String USERID = "";
    ProgressBar progressBar;
    Button btn_addcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_detail_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("CAT_ID");
            //The key argument here must match that used in the other activity
        }

        USERID = SharedHelper.getKey(SeedDetailScreenActivity.this, APPCONSTANT.id);
        btn_addcard = findViewById(R.id.btn_addcard);
        buy_products = findViewById(R.id.buy_products);
        rvProduct = findViewById(R.id.rvProduct);
        progressBar = findViewById(R.id.progressBar);
        back_btn =findViewById(R.id.back_btn);

        show_product();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeedDetailScreenActivity.this,BuyItemActivity.class));
                finish();
            }
        });
        buy_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeedDetailScreenActivity.this,TotalAmountActivity.class);
                startActivity(intent);
            }
        });
    }
    public void show_product() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_product)
                .addBodyParameter("user_id", USERID)
                .setTag("product")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dsafdadfa", response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response.getString("data"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("dfzcc", response.toString());

                                MyModel mymodel = new MyModel();
                                mymodel.setCommdity(jsonObject.getString("commdity"));
                                mymodel.setVariety(jsonObject.getString("variety"));
                                mymodel.setBrandName(jsonObject.getString("BrandName"));
                                mymodel.setBrand(jsonObject.getString("brand"));
                                mymodel.setCompany(jsonObject.getString("company"));
                                mymodel.setImage(jsonObject.getString("image"));
                                mymodel.setPath(jsonObject.getString("path"));
                                mymodel.setPrice(jsonObject.getString("price"));
                                mymodel.setQuantity(jsonObject.getString("quantity"));
                                mymodel.setStock(jsonObject.getString("stock"));
                                mymodel.setName(jsonObject.getString("name"));
                                mymodel.setId(jsonObject.getString("id"));
                                myModelArrayList.add(mymodel);
                                rvProduct.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(SeedDetailScreenActivity.this, RecyclerView.VERTICAL, false);
                                rvProduct.setLayoutManager(layoutManager);
                                Show_Product_Adapter adapter = new Show_Product_Adapter(context, myModelArrayList);
                                rvProduct.setAdapter(adapter);

                            }
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
