package com.devrik.GaonBazaar.activity.Other;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class checksum  extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="", mid="",st_grand_total="", st_user_id="456", st_address_id="", st_add_to_cart_id="",
            st_add_to_detail_cart_id="", st_count="",Delivdate="",Delivtime="";
    private String st_payment_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppsContants.sharedpreferences=getSharedPreferences(AppsContants.MyPREFERENCES,0);
//        st_grand_total= AppsContants.sharedpreferences.getString(AppsContants.grant_total,"");
//        st_user_id= AppsContants.sharedpreferences.getString(AppsContants.Userid,"");
//        //st_address_id= AppsContants.sharedpreferences.getString(AppsContants.address_id,"");
//        st_address_id= AppsContants.sharedpreferences.getString(AppsContants.AddressId,"");
//        st_count= AppsContants.sharedpreferences.getString(AppsContants.AddToCartTotal,"");
//        Delivdate = AppsContants.sharedpreferences.getString(AppsContants.Delivdate, "");
//        Delivtime = AppsContants.sharedpreferences.getString(AppsContants.Delivtime, "");
//        getSupportActionBar().hide();
        Log.e("fdjghg",st_address_id);
         st_grand_total ="1";
//        setContentView(R.layout.activity_main);
        //initOrderId();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        // orderId = intent.getExtras().getString("orderid");
        int i = new Random().nextInt(900000) + 100000;
        orderId = String.valueOf(i);
        Log.e("bjoiewop", orderId);
        // orderId = "ord568942";
        custid = st_user_id;
        //  st_grand_total = "1";
        mid = "PgbnBL08352361696684"; /// your marchant key
        //  mid = "DcQiGX35775094472292"; /// your marchant key
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
// vollye , retrofit, asynch
    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(checksum.this);
        //private String orderId , mid, custid, amt;
        String url ="https://deal2easy.com/paytmapp/generateChecksum.php";
       // String url ="https://ruparnatechnology.com/axomi_education/paytmapp/generateChecksum.php";
        // String varifyurl = " https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        String varifyurl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum.this);
            Log.e("dlheoaifyr", st_grand_total );
            Log.e("dlheoaifyr", custid );

            String param = "MID=" + mid + "&ORDER_ID=" + orderId + "&CUST_ID=" + custid + "&CHANNEL_ID=WAP" + "&TXN_AMOUNT=" +st_grand_total + "&WEBSITE=DEFAULT" +
                    "&CALLBACK_URL=" + varifyurl + "&INDUSTRY_TYPE_ID=Retail";
            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            PaytmPGService Service = PaytmPGService.getProductionService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            Log.e("dlheoaifyr", st_grand_total );
            Log.e("dlheoaifyr", custid );
            HashMap<String, String> paramMap = new HashMap<String, String>();

            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", st_grand_total );
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );
        }
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        String status = bundle.getString("STATUS");
        Log.e("checksum",status);

        if (status.equals("TXN_SUCCESS")) {
            finish();
            return;
        }else{
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();

        }

//        st_add_to_cart_id= AppsContants.sharedpreferences.getString(AppsContants.add_to_Product_id,"");
//        st_add_to_detail_cart_id= AppsContants.sharedpreferences.getString(AppsContants.add_to_Product_detail_id,"");

    }
    @Override
    public void networkNotAvailable() {
    }
    @Override
    public void clientAuthenticationFailed(String s) {
    }
    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum", " ui fail respon  "+ s );
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum", " error loading pagerespon true "+ s + "  s1 " + s1);
    }
    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum", " cancel call back respon  " );
        finish();
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum", "  transaction cancel " );
        finish();
    }
  /* public void cash_place_order() {
        //st_add_to_cart_id= AppsContants.sharedpreferences.getString(AppsContants.add_to_Product_id,"");
       // st_add_to_detail_cart_id= AppsContants.sharedpreferences.getString(AppsContants.add_to_Product_detail_id,"");
       AndroidNetworking.post(API.BASEURL+ API.place_order)
                .addBodyParameter("user_id",st_user_id)
                .addBodyParameter("pay_type","2")
                .addBodyParameter("address_id",st_address_id)
                .addBodyParameter("delivery_date",Delivdate)
                .addBodyParameter("delivery_time",Delivtime)
                .setTag("Categories")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("hkjhghg",response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String result = jsonObject.getString("result");
                                if(result.equals("order placed")){
                                    st_count.equals("");
                                    st_add_to_cart_id.equals("");
                                    st_add_to_detail_cart_id.equals("");
                                    String grand_total=jsonObject.getString("grand_total");
                                    String order_id=jsonObject.getString("order_id");
                                   *//* SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(APPCONSTANT.order_no, order_id);
                                    editor.putString(APPCONSTANT.order_total,grand_total );
                                    editor.putString(APPCONSTANT.AddToCartTotal,"" );
                                    editor.putString(APPCONSTANT.UniqRandomNumber,"");
                                    editor.putString(APPCONSTANT.SelectedAddressPosition, "");
                                    editor.putString(APPCONSTANT.AddressId, "");
                                    editor.commit();*//*
                                    startActivity(new Intent(checksum.this, AvailableBalanceActivity.class));
                                }else {
                                    Toast.makeText(checksum.this, ""+jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("palklala",ex.getMessage() );
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("palklala", String.valueOf(anError));
                    }
                });
    }*/

}

