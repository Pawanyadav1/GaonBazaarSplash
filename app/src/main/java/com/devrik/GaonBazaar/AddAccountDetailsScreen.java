package com.devrik.GaonBazaar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.Model.AddStateModel;
import com.devrik.GaonBazaar.Model.Adddistrictmodel;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddAccountDetailsScreen extends AppCompatActivity {
    EditText ac_name,account_no,bankname,branch,ifsc;
    MaterialButton button1;
    String USERID="";
    ImageView back;
    Spinner spinbank,spinbranch;
    ProgressBar progressBar;


    ArrayList<AddStateModel> addBankModelArrayList;
    ArrayList<String> Arr_BanmkID;
    ArrayList<String> Arr_BankName;

    ArrayList<Adddistrictmodel> addBranchModelArrayList;
    ArrayList<String> Arr_BranchID = new ArrayList<>();
    ArrayList<String> Arr_BranchName = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_details_screen);
        back = findViewById(R.id.back);
        ac_name = findViewById(R.id.ac_name);
        account_no = findViewById(R.id.account_no);
        bankname = findViewById(R.id.bankname);
        branch = findViewById(R.id.branch);
        spinbank = findViewById(R.id.spinbank);
        spinbranch = findViewById(R.id.spinbranch);
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

        Arr_BanmkID = new ArrayList<>();
        Arr_BankName = new ArrayList<>();
        Arr_BanmkID.add("0");
        Arr_BankName.add("Select");

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Arr_BankName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinbank.setAdapter(adapter);

        Arr_BranchID = new ArrayList<>();
        Arr_BranchName = new ArrayList<>();
        Arr_BranchID.add("0");
        Arr_BranchName.add("Select");

        ArrayAdapter adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Arr_BranchName);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinbranch.setAdapter(adapter1);


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
