package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

public class AddAccountDetailsScreen extends AppCompatActivity {
    EditText ac_name,account_no,bankname,branch,ifsc;
    MaterialButton button1;
    String USERID="";
    ImageView back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_details_screen);
        back = findViewById(R.id.back);
        ac_name = findViewById(R.id.ac_name);
        account_no = findViewById(R.id.account_no);
        bankname = findViewById(R.id.bankname);
        branch = findViewById(R.id.branch);
        ifsc = findViewById(R.id.ifsc);
        button1 = findViewById(R.id.button1);
        progressBar = findViewById(R.id.progressBar);

        USERID = SharedHelper.getKey(AddAccountDetailsScreen.this, APPCONSTANT.id);
        Log.e("select", USERID+"");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddAccountDetailsScreen.this,UpDateProfileActivity.class));

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_bank_account();

            }
        });
    }

private void add_bank_account() {
    progressBar.setVisibility(View.VISIBLE);
    AndroidNetworking.post(API.add_bank_account)
            .addBodyParameter("account_holder",ac_name.getText().toString().trim())
            .addBodyParameter("user_id",USERID)
            .addBodyParameter("account_number",account_no.getText().toString().trim())
            .addBodyParameter("bank_name",bankname.getText().toString().trim())
            .addBodyParameter("branch",branch.getText().toString().trim())
            .addBodyParameter("ifsc_code",ifsc.getText().toString().trim())
            .setTag("add_bank_account")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("show_error", response.toString() );
                    try {
                        if (response.getString("result").equals("successful")) {

                            startActivity(new Intent(AddAccountDetailsScreen.this,HomeScreenActivity.class));
                            Toast.makeText(AddAccountDetailsScreen.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            SharedHelper.getKey(AddAccountDetailsScreen.this,APPCONSTANT.id);
                        }else {


                            Toast.makeText(AddAccountDetailsScreen.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("unsuccessfdd", e.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onError(ANError anError) {
                    Log.e("unsuccesssd", anError.getMessage());
                    progressBar.setVisibility(View.GONE);

                }


            });

}
}
