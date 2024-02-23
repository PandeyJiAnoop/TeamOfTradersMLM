package com.sign.akp_teamoftraders.Basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.sign.akp_teamoftraders.DashboardActivity;
import com.sign.akp_teamoftraders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class LoginScreen extends AppCompatActivity {
    TextView forget_tv,btn_signup;
    EditText user_name_et,pass_et;
    AppCompatButton btn_login;
    private PopupWindow popupWindow;
    RelativeLayout mail_rl;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        findId();
        OnClick();
    }

    private void OnClick() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name_et.getText().toString().equalsIgnoreCase("")){
                    user_name_et.setError("Fields can't be blank!");
                    user_name_et.requestFocus();
                }
                else if (pass_et.getText().toString().equalsIgnoreCase("")){
                    pass_et.setError("Fields can't be blank!");
                    pass_et.requestFocus();
                }
                else {
                    getLoginAPI("1","",pass_et.getText().toString(),user_name_et.getText().toString());
                }}});

        forget_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetpasswordpopup();
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignupScreen.class);
                startActivity(intent);
            } });
    }

    private void findId() {
        user_name_et=findViewById(R.id.user_name_et);
        pass_et=findViewById(R.id.pass_et);
        forget_tv=findViewById(R.id.forget_tv);
        btn_login=findViewById(R.id.btn_login);
        mail_rl=findViewById(R.id.mail_rl);
        btn_signup=findViewById(R.id.btn_signup);
    }

    private void forgetpasswordpopup() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.forget_password,null);
        ImageView Goback = (ImageView) customView.findViewById(R.id.Goback);
        EditText email_et = (EditText) customView.findViewById(R.id.email_et);
        AppCompatButton continue_btn = (AppCompatButton) customView.findViewById(R.id.continue_btn);
        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //display the popup window
        popupWindow.showAtLocation(mail_rl, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        // Settings disappear when you click somewhere else
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.update();

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void  getLoginAPI(String action, String pakid, String pass,String userid){
        String otp1 = new GlobalAppApis().Login(action,pakid,pass,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getLogin(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String activeStatus       = jsonobject.getString("ActiveStatus");
                        String customerId    = jsonobject.getString("CustomerId");
                        String groupName       = jsonobject.getString("GroupName");
                        String name       = jsonobject.getString("Name");
                        String role       = jsonobject.getString("Role");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        login_preference = getSharedPreferences("login_preference", MODE_PRIVATE);
                        login_editor = login_preference.edit();
                        login_editor.putString("U_id",customerId);
                        login_editor.putString("U_name",name);
                        login_editor.putString("U_pass",pass_et.getText().toString());
                        login_editor.putString("U_role",role);
                        login_editor.commit();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }}, LoginScreen.this, call1, "", true);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}