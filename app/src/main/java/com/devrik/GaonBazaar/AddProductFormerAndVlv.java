package com.devrik.GaonBazaar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.devrik.GaonBazaar.others.API;
import com.devrik.GaonBazaar.others.APPCONSTANT;
import com.devrik.GaonBazaar.others.Add_Category_Adapter;
import com.devrik.GaonBazaar.others.SharedHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddProductFormerAndVlv extends AppCompatActivity {

    EditText product_name,edt_commiddty,edt_variety,edt_company,edt_price;
    EditText quantity_size;
    MaterialButton btn_ADD;
    Spinner spin_Brand;
    ImageButton photo_upload;
    ProgressBar progressBar;
    ImageView set_photo_upload,back_btn;
    RadioButton radio_btn_old,radio_btn_new;
    RadioGroup Radio_group;
    String UserID="",brandID="",id="";
    String value;

    ArrayList<Add_Category_Adapter> brandModelArrayList;
    ArrayList<String> Arr_BrandID;
    ArrayList<String> Arr_BrandName;


    private static final String IMAGE_DIRECTORY = "/directory";
    File f;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_former_and_vlv);

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            value = extras1.getString("CAT_ID");
            //The key argument here must match that used in the other activity
        }


        Radio_group = (RadioGroup) findViewById(R.id.Radio_group);
        quantity_size = findViewById(R.id.quantity_size);
        product_name = findViewById(R.id.product_name);
        edt_commiddty = findViewById(R.id.edt_commiddty);
        edt_variety = findViewById(R.id.edt_variety);
        edt_company = findViewById(R.id.edt_company);
        edt_price = findViewById(R.id.edt_price);
        photo_upload = findViewById(R.id.photo_upload);
        set_photo_upload = findViewById(R.id.set_photo_upload);
        back_btn = findViewById(R.id.back_btn);
        radio_btn_old = findViewById(R.id.radio_btn_old);
        radio_btn_new = findViewById(R.id.radio_btn_new);
        btn_ADD = findViewById(R.id.btn_ADD);
        progressBar = findViewById(R.id.progressBar);
        spin_Brand = findViewById(R.id.spin_Brand);

        UserID = SharedHelper.getKey(AddProductFormerAndVlv.this, APPCONSTANT.id);
        Log.e("seldghdect", UserID + "");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        btn_ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_product();
            }
        });

        Show_Brand();

        photo_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();
            }
        });

    }

    public void showPictureDialog() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddProductFormerAndVlv.this);
        builder.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture image from camera"};

        builder.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                    case 0:
                        choosePhotoFromGallery();
                        break;

                    case 1:
                        captureFromCamera();
                        break;
                }

            }
        });

        builder.show();
    }


    public void choosePhotoFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, GALLERY);
    }


    public void captureFromCamera() {

        Intent intent_2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent_2, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            set_photo_upload.setVisibility(View.VISIBLE);
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String strimage = getRealPathFromURI(contentURI);
                    f= new File(strimage);
                    set_photo_upload.setImageBitmap(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                    Toast.makeText(AddProductFormerAndVlv.this, "Failed!", Toast.LENGTH_SHORT).show();

                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            set_photo_upload.setImageBitmap(thumbnail);

            Uri getImageUrei=getImageUri(AddProductFormerAndVlv.this,thumbnail);
            String strimage = getRealPathFromURI(getImageUrei);
            f= new File(strimage);
            Toast.makeText(AddProductFormerAndVlv.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        else {

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path); }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }




    public void onclickbuttonMethod(View v){
        int selectedId = Radio_group.getCheckedRadioButtonId();
        radio_btn_new = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(AddProductFormerAndVlv.this,"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AddProductFormerAndVlv.this,radio_btn_new.getText(), Toast.LENGTH_SHORT).show();
        }

    }


    public void add_product() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("sdfgdfhfdfd", UserID);
        if (f == null) {
            AndroidNetworking.post(API.add_product)
                    .addBodyParameter("user_id", UserID)
                    .addBodyParameter("category_id", value)
                    .addBodyParameter("brand", brandID)
                    .addBodyParameter("name", product_name.getText().toString().trim())
                    .addBodyParameter("commdity", edt_commiddty.getText().toString().trim())
                    .addBodyParameter("variety", edt_variety.getText().toString().trim())
                    .addBodyParameter("company", edt_company.getText().toString().trim())
                    .addBodyParameter("price", edt_price.getText().toString().trim())
                    .addBodyParameter("quantity", quantity_size.getText().toString().trim())
                    .addBodyParameter("stock", radio_btn_new.toString().trim())
                    .setTag("add product")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("dchfgjh", response.toString());
                            try {
                                if (response.getString("result").equals("successful")) {
                                    Intent intent = new Intent(AddProductFormerAndVlv.this, ShowMyProductActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(AddProductFormerAndVlv.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("fkjsah", e.getMessage());
                                progressBar.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("dkgjk", anError.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    });
        } else {
            progressBar.setVisibility(View.VISIBLE);
            AndroidNetworking.upload(API.add_product)
                    .addMultipartParameter("user_id", UserID)
                    .addMultipartParameter("category_id", value)
                    .addMultipartParameter("brand", brandID)
                    .addMultipartParameter("name", product_name.getText().toString().trim())
                    .addMultipartParameter("commdity", edt_commiddty.getText().toString().trim())
                    .addMultipartParameter("variety", edt_variety.getText().toString().trim())
                    .addMultipartParameter("company", edt_company.getText().toString().trim())
                    .addMultipartParameter("price", edt_price.getText().toString().trim())
                    .addMultipartParameter("quantity", quantity_size.getText().toString().trim())
                    .addMultipartParameter("stock", radio_btn_new.toString().trim())
                    .addMultipartFile("image", f)
                    .setTag("add product")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.GONE);

                            Log.e("dchfgjh", response.toString());
                            try {
                                if (response.getString("result").equals("successful")) {
                                    Intent intent = new Intent(AddProductFormerAndVlv.this, ShowMyProductActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AddProductFormerAndVlv.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("fkjsah", e.getMessage());
                                progressBar.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("fsukgdsuk", anError.getMessage());
                            progressBar.setVisibility(View.GONE);


                        }
                    });

        }


    }

    public void Show_Brand() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.show_brand)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        brandModelArrayList= new ArrayList<>();

                        Log.e("uygyggv", "onRespo " + response.toString());
                        Arr_BrandID = new ArrayList<>();
                        Arr_BrandName = new ArrayList<>();
                        Arr_BrandID.add("0");
                        Arr_BrandName.add("Select Brand");

                        try {
                            for (int i = 0; i < response.length(); i++) {
                              /*  id = SharedHelper.getKey(AddProductFormerAndVlv.this, APPCONSTANT.id);
                                Log.e("select", id + "");*/
                                //Toast.makeText(AddProductFormerAndVlv.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = response.getJSONObject(i);
                                Arr_BrandID.add(jsonObject.getString("id"));
                                Arr_BrandName.add(jsonObject.getString("name"));

                            }

                            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, Arr_BrandName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spin_Brand.setAdapter(adapter);

                            spin_Brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String brandId = Arr_BrandID.get(position);
                                    Log.e("dcdcdddcdc", brandId );
                                    ((TextView) spin_Brand.getChildAt(0)).setTextColor(Color.BLACK);
                                    ((TextView) spin_Brand.getChildAt(0)).setTextSize(15);

                                    if (brandId.equals("0")){

                                    }else {
                                        brandID=brandId;

                                    }

                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {
                                }
                            });

                        } catch (Exception exception) {
                            Log.e("abcdhd", exception.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("abcdsdhd", "onError: " + anError);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}