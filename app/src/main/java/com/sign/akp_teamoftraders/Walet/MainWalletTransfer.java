package com.sign.akp_teamoftraders.Walet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class MainWalletTransfer extends AppCompatActivity {
    ImageView menuImg;
    TextView wallet_amount_tv,credit_tv,debit_tv;
    TextInputEditText userid_et;
    String UserId,walletBalance,UserPass;
    AppCompatButton raise_add_tv;

    CardView mail_ll,otp_ll;
    TextInputEditText otp_et;
    AppCompatButton otp_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wallet_transfer);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserPass= sharedPreferences.getString("U_pass", "");
        findId();
        OnClick();

        DashboardAPI("4","",UserPass,UserId);

    }

    private void OnClick() {
        raise_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid_et.getText().toString().equalsIgnoreCase("")){
                    userid_et.setError("Fields can't be blank!");
                    userid_et.requestFocus();
                }
                else {
                    mail_ll.setVisibility(View.VISIBLE);
                    otp_ll.setVisibility(View.GONE);
                    SendOTPAPI(UserId);

                }

            }
        });
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findId() {
        menuImg=findViewById(R.id.menuImg);
        wallet_amount_tv=findViewById(R.id.wallet_amount_tv);
        credit_tv=findViewById(R.id.credit_tv);
        debit_tv=findViewById(R.id.debit_tv);
        userid_et=findViewById(R.id.userid_et);
        raise_add_tv=findViewById(R.id.raise_add_tv);


        mail_ll=findViewById(R.id.mail_ll);
        otp_ll=findViewById(R.id.otp_ll);
        otp_et=findViewById(R.id.otp_et);
        otp_btn=findViewById(R.id.otp_btn);

    }
    public void  DashboardAPI(String action, String pakid, String pass,String userid){
        String otp1 = new GlobalAppApis().Dashboard(action,pakid,pass,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getDashboard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        walletBalance  = jsonobject.getString("MainWallet");
                        wallet_amount_tv.setText("$ "+walletBalance);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }
        }, MainWalletTransfer.this, call1, "", true);
    }


    public void  SendOTPAPI(String userid){
        String otp1 = new GlobalAppApis().SendOTP(userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getSendOTP(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")== true){
                        String OTP=object.getString("OTP");
                        mail_ll.setVisibility(View.GONE);
                        otp_ll.setVisibility(View.VISIBLE);
                        otp_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (otp_et.getText().toString().equals("")){
                                    otp_et.setError("Fields can't be blank!");
                                    otp_et.requestFocus();
                                }
                                else {
                                    if (OTP.equals(otp_et.getText().toString())){
                                        IDACTIVATE(userid_et.getText().toString(),UserId,walletBalance);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Sorry! OTP Not Matched",Toast.LENGTH_LONG).show();
                                    } }}});

                    }
                    else {
                        Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }
        }, MainWalletTransfer.this, call1, "", true);
    }




    public void IDACTIVATE(String CashWallet,String UserId,String WalletBalance) {
        String otp = new GlobalAppApis().MainWallet(CashWallet,UserId,WalletBalance);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getMainWallet(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainWalletTransfer.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       finish();
                                        dialog.cancel();
                                    }});
                        builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainWalletTransfer.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) { finish(); }});
                        builder.create().show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, MainWalletTransfer.this, call, "", true);
    }




}