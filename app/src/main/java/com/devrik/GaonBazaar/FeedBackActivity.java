package com.devrik.GaonBazaar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

public class FeedBackActivity extends AppCompatActivity {

    ImageView back;
    RatingBar rating_bar;
    ProgressBar progressBar;
    EditText et_feedback;
    MaterialButton btn_submit;
    TextView store_star_rating;
    String User_Id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        back=findViewById(R.id.back);
        rating_bar=findViewById(R.id.rating_bar);
        et_feedback=findViewById(R.id.et_feedback);
        progressBar=findViewById(R.id.progressBar);
        btn_submit=findViewById(R.id.btn_submit);
        store_star_rating=findViewById(R.id.store_star_rating);

        LayerDrawable stars=(LayerDrawable)rating_bar.getProgressDrawable();

        //Use for changing the color of RatingBar
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        User_Id = SharedHelper.getKey(FeedBackActivity.this, APPCONSTANT.id);

        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      /*  btn_submit.setOnClickListener(new View.OnClickListener() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback();
            }
        });
*/

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = findViewById(R.id.store_star_rating);
                String rating =String.valueOf(rating_bar.getRating());
                t.setText(rating);
                feedback(rating);
            }
        });

    }

    public void feedback(String rating){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.feedback)
                .addBodyParameter("user_id",User_Id)
                .addBodyParameter("rating",rating)
                .addBodyParameter("feedback",et_feedback.getText().toString())
                .setPriority(Priority.HIGH)
                .setTag("terms condition")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dsfkfdg", response.toString());
                        try {
                            if (response.getString("result").equals("Feedback Added successfully")){
                                Toast.makeText(FeedBackActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(FeedBackActivity.this,HomeScreenActivity.class));

                                //  t.setText("You Rated :"+response.get(rating_bar.getRating()));
                                rating_bar.setNumStars(response.getInt("rating"));

                            }else

                                Toast.makeText(FeedBackActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }
}