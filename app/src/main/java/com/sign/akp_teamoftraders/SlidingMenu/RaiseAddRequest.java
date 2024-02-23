package com.sign.akp_teamoftraders.SlidingMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.R;
import com.sign.akp_teamoftraders.Walet.MainWalletTransfer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class RaiseAddRequest extends AppCompatActivity {
    ImageView menuImg;
    RadioButton usdt, eth, btc, bnb;
    String UserId;
    ImageView image_qr;
    TextView url_tv, select_tv;
    String stringValue = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, OTP="";
    String temp;
    ImageView img_showProfile;
    AppCompatButton btn_submit;

    TextInputEditText txt_et;
    String Getamount;


    private ClipboardManager myClipboard;
    private ClipData myClip;
    Button click_rl;

    CardView mail_ll, otp_ll;
    TextInputEditText otp_et;
    AppCompatButton otp_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_add_request);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        Getamount = getIntent().getStringExtra("amount");
        FindId();
        OnClick();
        WalletAPI("", UserId);

    }

    private void OnClick() {

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        click_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String text = url_tv.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Link Copied", Toast.LENGTH_SHORT).show();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_et.getText().toString().equalsIgnoreCase("")) {
                    txt_et.setError("Fields can't be blank!");
                    txt_et.requestFocus();
                } else {
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
        select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp_et.getText().toString().equals("")) {
                    otp_et.setError("Fields can't be blank!");
                    otp_et.requestFocus();
                } else {
                    if (OTP.equals(otp_et.getText().toString())) {
                        SubmitAPI();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry! OTP Not Matched", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void FindId() {
        menuImg = findViewById(R.id.menuImg);
        image_qr = findViewById(R.id.image_qr);

        usdt = (RadioButton) findViewById(R.id.usdt);
        eth = (RadioButton) findViewById(R.id.eth);
        btc = (RadioButton) findViewById(R.id.btc);
        bnb = (RadioButton) findViewById(R.id.bnb);
        select_tv = findViewById(R.id.select_tv);
        img_showProfile = findViewById(R.id.img_showProfile);
        btn_submit = findViewById(R.id.btn_submit);
        txt_et = findViewById(R.id.txt_et);
        click_rl = findViewById(R.id.click_rl);
        url_tv = findViewById(R.id.url_tv);
        mail_ll = findViewById(R.id.mail_ll);
        otp_ll = findViewById(R.id.otp_ll);
        otp_et = findViewById(R.id.otp_et);
        otp_btn = findViewById(R.id.otp_btn);
    }

    private void SubmitAPI() {
        if (txt_et.getText().toString().equalsIgnoreCase("")) {
            txt_et.setError("Fields can't be blank!");
            txt_et.requestFocus();
        } else {
            if (stringValue.equals("")) {
                FundRequest(Getamount, temp, txt_et.getText().toString(), UserId, "INR");
            } else {
                FundRequest(Getamount, temp, txt_et.getText().toString(), UserId, stringValue);
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.btc:
                if (checked)
                    stringValue = "BTC";
                WalletAPI("", UserId);
                break;
            case R.id.bnb:
                if (checked)
                    stringValue = "INR";
                WalletAPI("", UserId);
                break;
            case R.id.usdt:
                if (checked)
                    stringValue = "USDT";
                WalletAPI("", UserId);
                break;
            case R.id.eth:
                if (checked)
                    stringValue = "ETH";
                WalletAPI("", UserId);
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void WalletAPI(String action, String userid) {
        String otp1 = new GlobalAppApis().Wallet(action, userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getWallet(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("res", result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String credit = jsonobject.getString("Credit");
                        String debit = jsonobject.getString("Debit");
                        String walletBalance = jsonobject.getString("WalletBalance");
                        String bnbAddress = jsonobject.getString("BNBAddress");
                        String bnburl = jsonobject.getString("BNBURL");
                        String btcAddress = jsonobject.getString("BTCAddress");
                        String btcurl = jsonobject.getString("BTCURL");
                        String ethAddress = jsonobject.getString("ETHAddress");
                        String ethurl = jsonobject.getString("ETHURL");
                        String usdtAddress = jsonobject.getString("USDTAddress");
                        String usdturl = jsonobject.getString("USDTURL");

                        if (btcurl.equalsIgnoreCase("")) {
                        } else {
                            Picasso.with(getApplicationContext()).load(usdturl).error(R.drawable.logo).into(image_qr);
                        }
                        url_tv.setText(usdtAddress);
                        if (stringValue.equalsIgnoreCase("BTC")) {
                            if (btcurl.equalsIgnoreCase("")) {
                            } else {
                                Picasso.with(getApplicationContext()).load(btcurl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(btcAddress);
                        } else if (stringValue.equalsIgnoreCase("INR")) {

                            if (bnburl.equalsIgnoreCase("")) {
                            } else {
                                Picasso.with(getApplicationContext()).load(bnburl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(bnbAddress);
                        } else if (stringValue.equalsIgnoreCase("USDT")) {
                            if (usdturl.equalsIgnoreCase("")) {
                            } else {
                                Picasso.with(getApplicationContext()).load(usdturl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(usdtAddress);
                        } else if (stringValue.equalsIgnoreCase("ETH")) {

                            if (ethurl.equalsIgnoreCase("")) {
                            } else {
                                Picasso.with(getApplicationContext()).load(ethurl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(ethAddress);
                        }
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, RaiseAddRequest.this, call1, "", true);
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RaiseAddRequest.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RaiseAddRequest.this);
                if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Toast.makeText(getApplicationContext(),""+bm,Toast.LENGTH_LONG).show();
        img_showProfile.setImageBitmap(bm);
        Bitmap immagex = bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] b = baos.toByteArray();
        temp = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public void FundRequest(String Amount, String ReceiptImg, String TransactionHash, String UserId, String CoinType) {
        String otp = new GlobalAppApis().FundRequestAPI(Amount, ReceiptImg, TransactionHash, UserId, CoinType);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getFundRequest(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.e("TAG", "FundRequestResponse: "+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RaiseAddRequest.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RaiseAddRequest.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        builder.create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, RaiseAddRequest.this, call, "", true);
    }


    public void SendOTPAPI(String userid) {
        String otp1 = new GlobalAppApis().SendOTP(userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getSendOTP(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("sdgsdgsg", "OtpResponse: "+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")) {
                        OTP = object.getString("OTP");
                        mail_ll.setVisibility(View.GONE);
                        otp_ll.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, RaiseAddRequest.this, call1, "", true);
    }


}

