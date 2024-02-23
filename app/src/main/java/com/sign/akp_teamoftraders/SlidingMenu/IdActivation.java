package com.sign.akp_teamoftraders.SlidingMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.LoginScreen;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.Basic.SignupScreen;
import com.sign.akp_teamoftraders.Basic.SplashScreen;
import com.sign.akp_teamoftraders.DashboardActivity;
import com.sign.akp_teamoftraders.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByGet;
import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class IdActivation extends AppCompatActivity {
    ImageView menuImg;
    TextView wallet_amount_tv,credit_tv,debit_tv;
    TextInputEditText activation_id_et;
    String UserId,UserPass,walletBalance;
    AppCompatButton raise_add_tv;
    SearchableSpinner country_et;
    String stateid; ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();
    TextView name_tv;
    private final Handler handler = new Handler();
    //SPLASHTIMEOUT
    private static int SPLASHTIMEOUT = 600; // Splash screen timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_activation);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserPass= sharedPreferences.getString("U_pass", "");
        findId();
        OnClick();
        PackageList();

        WalletAPI("",UserId);
//        DashboardAPI("4","",UserPass,UserId);

    }

    private void OnClick() {
        final Handler handler = new Handler();
        final long delayMillis = 5000; // Delay in milliseconds
        raise_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activation_id_et.getText().toString().equalsIgnoreCase("")){
                    activation_id_et.setError("Fields can't be blank!");
                    activation_id_et.requestFocus();
                }
                else {
                    // Disable the button
                    raise_add_tv.setEnabled(false);
                    // Enable the button after a delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            raise_add_tv.setEnabled(true);
                        }
                    }, delayMillis);
                    getDataFromSponser(activation_id_et.getText().toString());

                } }});
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activation_id_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } }});

    }

    private void findId() {
        name_tv=findViewById(R.id.name_tv);
        country_et=findViewById(R.id.country_et);
        menuImg=findViewById(R.id.menuImg);
        wallet_amount_tv=findViewById(R.id.wallet_amount_tv);
        credit_tv=findViewById(R.id.credit_tv);
        debit_tv=findViewById(R.id.debit_tv);
        activation_id_et=findViewById(R.id.activation_id_et);
        raise_add_tv=findViewById(R.id.raise_add_tv);

        country_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        if (country_et.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
                            stateid = StateId.get(j);
//                            stateid = StateId.get(j - 1);
                            break; } } } }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
    }

    public void IDACTIVATE(String ActivationId,String Amount,String Password,String TransactionHash,String UserId) {
        String otp = new GlobalAppApis().AccountActivationAPI(ActivationId,Amount,Password,TransactionHash,UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getAccountActivation(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(IdActivation.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) { overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);  dialog.cancel();
                                    }}); builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(IdActivation.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       finish();
                                        }});
                        builder.create().show(); } }
                catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, IdActivation.this, call, "", true);
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
        }, IdActivation.this, call1, "", true);
    }



    public void  WalletAPI(String action,String userid){
        String otp1 = new GlobalAppApis().Wallet(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getWallet(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String credit       = jsonobject.getString("Credit");
                        String debit    = jsonobject.getString("Debit");
                        //                        String walletBalance      = jsonobject.getString("WalletBalance");
                        walletBalance      = jsonobject.getString("EWallet");
                        wallet_amount_tv.setText("$ "+walletBalance);
                        credit_tv.setText("$ "+credit);
                        debit_tv.setText("$ "+debit);
//                        activation_id_et.setText(UserId);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, IdActivation.this, call1, "", true);
    }

    public void PackageList() {
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_GetPackageList();
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("res","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")==false){
                        String msg       = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONArray jsonArray = object.getJSONArray("LoginRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("PackageId"));
                            String statename = jsonObject1.getString("PackageName");
                            StateName.add(statename); }
                        country_et.setAdapter(new ArrayAdapter<String>(IdActivation.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    } } catch (JSONException e) {
                    e.printStackTrace();
                } }}, IdActivation.this, call1, "", true);
    }



    private void getDataFromSponser(String refid)
    {
        String otp1 = new GlobalAppApis().CheckSponserA(refid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getCheckSponser(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equals("false")){
                        activation_id_et.setText("");
                        name_tv.setText("");
                        AlertDialog.Builder builder = new AlertDialog.Builder(IdActivation.this);
                        builder.setTitle("Invalid")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    } });
                        builder.create().show();
                    } else {
                        JSONArray Jarray = object.getJSONArray("Resp");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String Name = jsonobject.getString("Name");
                            name_tv.setText(Name);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    IDACTIVATE(activation_id_et.getText().toString(),stateid,"","",UserId);
                                }}, SPLASHTIMEOUT);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        }  }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }}
        }, IdActivation.this, call1, "", true);}

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getDataFromSponser(activation_id_et.getText().toString());
    }
}
