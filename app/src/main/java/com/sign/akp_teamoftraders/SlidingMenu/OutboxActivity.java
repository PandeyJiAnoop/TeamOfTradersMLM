package com.sign.akp_teamoftraders.SlidingMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.NetworkConnectionHelper;
import com.sign.akp_teamoftraders.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class OutboxActivity extends AppCompatActivity {
    ImageView menuImg;
    String UserId;
    SwipeRefreshLayout srl_refresh;
    RecyclerView chat_recyclerView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbox);
       FindId();
       OnClick();
        TopupDetails("",UserId);
    }

    private void OnClick() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(OutboxActivity.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(OutboxActivity.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        srl_refresh = findViewById(R.id.srl_refresh);
        chat_recyclerView = findViewById(R.id.chat_recyclerView);
        menuImg=findViewById(R.id.menuImg);
    }


    public void  TopupDetails(String action,String userid){
        String otp1 = new GlobalAppApis().Outbox(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getOutbox(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("OutboxResp");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonObject1 = Jarray.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("Message", jsonObject1.getString("Message"));
                        hm.put("SentDate", jsonObject1.getString("SentDate"));
                        hm.put("Subject", jsonObject1.getString("Subject"));
                        hm.put("TicketId", jsonObject1.getString("TicketId"));
                        arrayList.add(hm);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(OutboxActivity.this, 1);
                    InboxAdpter walletHistoryAdapter = new InboxAdpter(OutboxActivity.this, arrayList);
                    chat_recyclerView.setLayoutManager(gridLayoutManager);
                    chat_recyclerView.setAdapter(walletHistoryAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, OutboxActivity.this, call1, "", true);
    }


    public class InboxAdpter extends RecyclerView.Adapter<InboxAdpter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public InboxAdpter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public InboxAdpter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_outbox, viewGroup, false);
            InboxAdpter.VH viewHolder = new InboxAdpter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull InboxAdpter.VH vh, int i) {
            vh.srtv.setText(String.valueOf(" "+(i+1)));
            vh.tv.setText(arrayList.get(i).get("Message"));
            vh.tv1.setText(arrayList.get(i).get("SentDate"));
            vh.tv2.setText(arrayList.get(i).get("Subject"));
            vh.tv3.setText(arrayList.get(i).get("TicketId"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,srtv,tv3;
            //        CircleImageView img_icon;
            public VH(@NonNull View itemView) {
                super(itemView);
                srtv = itemView.findViewById(R.id.srtv);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
            }
        }
    }

}