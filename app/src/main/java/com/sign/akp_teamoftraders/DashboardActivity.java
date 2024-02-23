package com.sign.akp_teamoftraders;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sign.akp_teamoftraders.ActivationArea.AA_FundHistory;
import com.sign.akp_teamoftraders.ActivationArea.TransferHistory;
import com.sign.akp_teamoftraders.Basic.ApiService;
import com.sign.akp_teamoftraders.Basic.AppUrls;
import com.sign.akp_teamoftraders.Basic.ConnectToRetrofit;
import com.sign.akp_teamoftraders.Basic.GlobalAppApis;
import com.sign.akp_teamoftraders.Basic.RetrofitCallBackListenar;
import com.sign.akp_teamoftraders.Basic.SIgnUpWebview;
import com.sign.akp_teamoftraders.Basic.SplashScreen;
import com.sign.akp_teamoftraders.FundArea.PrincipalWithdrawalRequest;
import com.sign.akp_teamoftraders.FundArea.PrincipalWithdrawalRequestReport;
import com.sign.akp_teamoftraders.IncomeDetails.MatchingIncome;
import com.sign.akp_teamoftraders.IncomeDetails.ROIIncome;
import com.sign.akp_teamoftraders.IncomeDetails.Referralncome;
import com.sign.akp_teamoftraders.IncomeDetails.RewardIncome;
import com.sign.akp_teamoftraders.IncomeDetails.ShopingWallet;
import com.sign.akp_teamoftraders.MyAccount.ChangePassword;
import com.sign.akp_teamoftraders.MyAccount.KYCDocuments;
import com.sign.akp_teamoftraders.MyAccount.MyProfile;
import com.sign.akp_teamoftraders.MyAccount.UpdateProfile;
import com.sign.akp_teamoftraders.MyNetwork.LevelIncome;
import com.sign.akp_teamoftraders.MyNetwork.MyTree;
import com.sign.akp_teamoftraders.SlidingMenu.CustomerRegistrtaion;
import com.sign.akp_teamoftraders.SlidingMenu.IdActivation;
import com.sign.akp_teamoftraders.SlidingMenu.InboxActivity;
import com.sign.akp_teamoftraders.SlidingMenu.OutboxActivity;
import com.sign.akp_teamoftraders.SlidingMenu.PlanPurchase;
import com.sign.akp_teamoftraders.SlidingMenu.ProfileWebview;
import com.sign.akp_teamoftraders.SlidingMenu.RaiseAddRequest;
import com.sign.akp_teamoftraders.SlidingMenu.ReferralList;
import com.sign.akp_teamoftraders.SlidingMenu.ReportList;
import com.sign.akp_teamoftraders.SlidingMenu.Withdraw;
import com.sign.akp_teamoftraders.Walet.MainWalletTransfer;
import com.sign.akp_teamoftraders.Walet.P2PTransfer;
import com.sign.akp_teamoftraders.Walet.WalletHistoy;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;

import static com.sign.akp_teamoftraders.Basic.API_Config.getApiClient_ByPost;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout one, two, three, four, five, six;
    private ImageView sliding_menu;
    private DrawerLayout mDrawer;
    LinearLayout one_ll, ll_two, ll_three, ll_four, ll_five, ll_six;
    int i = 0;
    LinearLayout logout_ll, dash_ll;
    LinearLayout update_ll, myprofile_ll, password_ll, kyc_ll, address_ll, welcome_ll, order_ll, level_income_ll, repoert_ll, inbox_ll, outbox_ll;
    LinearLayout refferal_ll, mytree_ll, downlineleftright_ll;
    LinearLayout referral_ll, matching_ll, contract_level_ll;
    LinearLayout cust_reg_ll;
    String UserId, UserPass, UserRole, UserName;
    TextView level_income_tv, withdraw_tv, direct_saponser_income_tv, toatl_income_tv, user_id_tv, user_name_tv, sponser_id_tv, activaation_date_tv,
            email_tv, mobile_tv;
    RelativeLayout share_ll;
    TextView referral_tv;
    LinearLayout ticket_ll;
    SwipeRefreshLayout srl_refresh;
    LinearLayout package_ll, total_team_ll, total_active_team_ll, total_incative_team_ll, das_withdraw_ll, contact_ll, direct_sponsor_income_ll, total_business_ll, total_income_ll, das_level_income_ll;

    LinearLayout addfund_ll, idactivation_ll, activation_history_ll, addfundhistory_ll;
    LinearLayout roi_income_ll, reward_ll;
    LinearLayout mainwallet_transfer_ll, p2pfund_ll, transfer_history_ll, withdrawlrequest_ll, withdrawl_history_ll;

    LinearLayout shiba_main_ll, balance_ll, hold_income_ll;
    private AlertDialog alertDialog;

    TextView ewallet_tv, totalreferral_tv, joining_tv, activationdate_tv, roi_income_tv, direct_income_tv,
            reward_tv, active_referral_tv, total_activeteam_tv, inactive_team_tv;
    Button team_business_tv;
    TextView name_tv, package_tv, main_wallet_tv, package_name_tv;

    LinearLayout pricipal_withdrawal_request_ll, pricipal_withdrawal_report_ll, whatsap_ll;

    FloatingActionButton fab_add_my_album_listing;
    AppCompatButton withdraw_das_btn, deposite_das_btn;

    List<BannerData> bannerData = new ArrayList<>();
    private static int currentPage = 0;
    CirclePageIndicator indicator;
    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        UserPass = sharedPreferences.getString("U_pass", "");
        UserRole = sharedPreferences.getString("U_role", "");
        UserName = sharedPreferences.getString("U_name", "");
        FindId();
        OnClick();
        getBanner();
        DashboardAPI("4", "", UserPass, UserId);
        referral_tv.setText("https://teamoftraders.in/Account/CustomerSignUp?Id=" + UserId);

//        getDashboardCatAPI();
    }

    private void OnClick() {

        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        sliding_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                mDrawer.openDrawer(GravityCompat.START);
                mDrawer.openDrawer(GravityCompat.START);
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDrawer.closeDrawer(GravityCompat.START);
//                    }
//                });
            }
        });

        withdraw_das_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Withdraw.class);
                startActivity(intent);
            }
        });
        deposite_das_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlanPurchase.class);
                startActivity(intent);
            }
        });


        whatsap_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsNumber = "18402521975"; //without '+'
                String message = "Hello Team of Traders  I need a Help?";
                String appPackage = "";
                if (isAppInstalled(DashboardActivity.this, "com.whatsapp.w4b")) {
                    appPackage = "com.whatsapp.w4b";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage(appPackage);
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + message;
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);

                    //do ...
                } else if (isAppInstalled(DashboardActivity.this, "com.whatsapp")) {
                    appPackage = "com.whatsapp";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage(appPackage);
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + message;
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);
                    //do ...
                } else {
                    Toast.makeText(DashboardActivity.this, "whatsApp is not installed", Toast.LENGTH_LONG).show();
                }


            }
        });

        fab_add_my_album_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsNumber = "18402521975"; //without '+'
                String message = "Hello Team of Traders  I need a Help?";
                String appPackage = "";
                if (isAppInstalled(DashboardActivity.this, "com.whatsapp.w4b")) {
                    appPackage = "com.whatsapp.w4b";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage(appPackage);
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + message;
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);

                    //do ...
                } else if (isAppInstalled(DashboardActivity.this, "com.whatsapp")) {
                    appPackage = "com.whatsapp";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage(appPackage);
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + message;
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);
                    //do ...
                } else {
                    Toast.makeText(DashboardActivity.this, "whatsApp is not installed", Toast.LENGTH_LONG).show();
                }

              /*  String smsNumber = "18402521975"; //without '+'
                String message="Hello Team of Traders  I need a Help?";
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed){
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage("com.whatsapp");
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text="+message;
                    sendIntent.setData(Uri.parse(url));
                    if(sendIntent.resolveActivity(getPackageManager()) == null){
                        Toast.makeText(DashboardActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(sendIntent);
                }else {
                    Toast.makeText(DashboardActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }*/
            }
        });


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(one_ll.getVisibility()==View.VISIBLE){
                    one_ll.setVisibility(View.GONE);
                } else {
                    one_ll.setVisibility(View.VISIBLE);
                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_two.getVisibility()==View.VISIBLE){
                    ll_two.setVisibility(View.GONE);
                } else {
                    ll_two.setVisibility(View.VISIBLE);
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_three.getVisibility()==View.VISIBLE){
                    ll_three.setVisibility(View.GONE);
                } else {
                    ll_three.setVisibility(View.VISIBLE);
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_four.getVisibility()==View.VISIBLE){
                    ll_four.setVisibility(View.GONE);
                } else {
                    ll_four.setVisibility(View.VISIBLE);
                }
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_five.getVisibility()==View.VISIBLE){
                    ll_five.setVisibility(View.GONE);
                } else {
                    ll_five.setVisibility(View.VISIBLE);
                }
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_six.getVisibility()==View.VISIBLE){
                    ll_six.setVisibility(View.GONE);
                } else {
                    ll_six.setVisibility(View.VISIBLE);
                }}});

        logout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this).setTitle("Logout")
                        .setMessage("Are you sure want to Logout").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences myPrefs = getSharedPreferences("login_preference", MODE_PRIVATE);
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                                startActivity(intent);
                                Intent i = new Intent();
                                i.putExtra("finish", true);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                //startActivity(i);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        update_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), UpdateProfile.class);
                startActivity(intent);
            }
        });

        mytree_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), MyTree.class);
                startActivity(intent);
            }
        });
        dash_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
            }
        });
        cust_reg_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), CustomerRegistrtaion.class);
                startActivity(intent);
            }
        });
        myprofile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ProfileWebview.class);
                startActivity(intent);
//                Intent intent=new Intent(getApplicationContext(), MyProfile.class);
//                startActivity(intent);

            }
        });
        repoert_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ReportList.class);
                startActivity(intent);
            }
        });
        ticket_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), Tickets.class);
                startActivity(intent);
            }
        });
        password_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        kyc_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), KYCDocuments.class);
                startActivity(intent);

            }
        });
        referral_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), Referralncome.class);
                startActivity(intent);
            }
        });
        level_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), LevelIncome.class);
                startActivity(intent);

            }
        });
        matching_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), MatchingIncome.class);
                startActivity(intent);
            }
        });
        contract_level_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ShopingWallet.class);
                startActivity(intent);
            }
        });
        refferal_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ReferralList.class);
                startActivity(intent);
            }
        });
        inbox_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), InboxActivity.class);
                startActivity(intent);
            }
        });
        outbox_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), OutboxActivity.class);
                startActivity(intent);

            }
        });
        package_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), PlanPurchaseWebview.class);
//                startActivity(intent);
            }
        });
        total_team_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), Referralncome.class);
//                startActivity(intent);
            }
        });
        total_active_team_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), LevelTreeWebview.class);
//                startActivity(intent);
            }
        });
        total_incative_team_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        das_withdraw_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), Withdraw.class);
//                startActivity(intent);
            }
        });
        contact_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), ShopingWallet.class);
//                startActivity(intent);
            }
        });
        direct_sponsor_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), MatchingIncome.class);
//                startActivity(intent);
            }
        });
        das_level_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), ROIIncome.class);
//                startActivity(intent);
            }
        });
        total_business_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        total_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        addfund_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), PlanPurchase.class);
                startActivity(intent);
            }
        });
        idactivation_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), IdActivation.class);
                startActivity(intent);
            }
        });
        activation_history_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ActivateScreen.class);
                startActivity(intent);
            }
        });
        addfundhistory_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), AA_FundHistory.class);
                startActivity(intent);


            }
        });
        roi_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), ROIIncome.class);
                startActivity(intent);


//                Toast.makeText(getApplicationContext(),"roi_income_ll",Toast.LENGTH_LONG).show();
            }
        });
        reward_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), RewardIncome.class);
                startActivity(intent);

            }
        });
        mainwallet_transfer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), MainWalletTransfer.class);
                startActivity(intent);

            }
        });
        p2pfund_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), P2PTransfer.class);
                startActivity(intent);

            }
        });
        transfer_history_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), TransferHistory.class);
                startActivity(intent);

            }
        });
        withdrawlrequest_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), Withdraw.class);
                startActivity(intent);
            }
        });
        withdrawl_history_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), WalletHistoy.class);
                startActivity(intent);
            }
        });

        pricipal_withdrawal_request_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), PrincipalWithdrawalRequest.class);
                startActivity(intent);
            }
        });
        pricipal_withdrawal_report_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(getApplicationContext(), PrincipalWithdrawalRequestReport.class);
                startActivity(intent);
            }
        });


        share_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = referral_tv.getText().toString();
                String sub = "Hey check out app and use My Referral Link";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(myIntent, "Team Of Traders Referral Link"));
            }
        });
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(DashboardActivity.this)) {
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
                    Toast.makeText(DashboardActivity.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void FindId() {
        withdraw_das_btn = findViewById(R.id.withdraw_das_btn);
        deposite_das_btn = findViewById(R.id.deposite_das_btn);

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        pager = (ViewPager) findViewById(R.id.pager1);

        whatsap_ll = findViewById(R.id.whatsap_ll);
        fab_add_my_album_listing = findViewById(R.id.fab_add_my_album_listing);
        balance_ll = findViewById(R.id.balance_ll);
        hold_income_ll = findViewById(R.id.hold_income_ll);
        shiba_main_ll = findViewById(R.id.shiba_main_ll);
        Animation anim = new AlphaAnimation(0.8f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(10);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        shiba_main_ll.startAnimation(anim);
        srl_refresh = findViewById(R.id.srl_refresh);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);

        one_ll = findViewById(R.id.one_ll);
        ll_two = findViewById(R.id.ll_two);
        ll_three = findViewById(R.id.ll_three);
        ll_four = findViewById(R.id.ll_four);
        ll_five = findViewById(R.id.ll_five);
        ll_six = findViewById(R.id.ll_six);
        logout_ll = findViewById(R.id.logout_ll);
        dash_ll = findViewById(R.id.dash_ll);

        update_ll = findViewById(R.id.update_ll);
        myprofile_ll = findViewById(R.id.myprofile_ll);
        password_ll = findViewById(R.id.password_ll);
        kyc_ll = findViewById(R.id.kyc_ll);
        address_ll = findViewById(R.id.address_ll);
        welcome_ll = findViewById(R.id.welcome_ll);
        order_ll = findViewById(R.id.order_ll);

        repoert_ll = findViewById(R.id.repoert_ll);
        inbox_ll = findViewById(R.id.inbox_ll);
        outbox_ll = findViewById(R.id.outbox_ll);

        referral_tv = findViewById(R.id.referral_tv);
        share_ll = findViewById(R.id.share_ll);
        ticket_ll = findViewById(R.id.ticket_ll);
        package_ll = findViewById(R.id.package_ll);
        total_team_ll = findViewById(R.id.total_team_ll);
        total_active_team_ll = findViewById(R.id.total_active_team_ll);
        total_incative_team_ll = findViewById(R.id.total_incative_team_ll);
        das_withdraw_ll = findViewById(R.id.das_withdraw_ll);
        contact_ll = findViewById(R.id.contact_ll);
        direct_sponsor_income_ll = findViewById(R.id.direct_sponsor_income_ll);
        total_business_ll = findViewById(R.id.total_business_ll);
        total_income_ll = findViewById(R.id.total_income_ll);
        das_level_income_ll = findViewById(R.id.das_level_income_ll);

        level_income_tv = findViewById(R.id.level_income_tv);
        withdraw_tv = findViewById(R.id.withdraw_tv);
        direct_saponser_income_tv = findViewById(R.id.direct_saponser_income_tv);
        toatl_income_tv = findViewById(R.id.toatl_income_tv);
        user_id_tv = findViewById(R.id.user_id_tv);
        user_name_tv = findViewById(R.id.user_name_tv);
        sponser_id_tv = findViewById(R.id.sponser_id_tv);
        activaation_date_tv = findViewById(R.id.activaation_date_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);

        ewallet_tv = findViewById(R.id.ewallet_tv);
        totalreferral_tv = findViewById(R.id.totalreferral_tv);
        joining_tv = findViewById(R.id.joining_tv);
        activationdate_tv = findViewById(R.id.activationdate_tv);
        roi_income_tv = findViewById(R.id.roi_income_tv);
        direct_income_tv = findViewById(R.id.direct_income_tv);
        reward_tv = findViewById(R.id.reward_tv);
        active_referral_tv = findViewById(R.id.active_referral_tv);
        total_activeteam_tv = findViewById(R.id.total_activeteam_tv);
        inactive_team_tv = findViewById(R.id.inactive_team_tv);
        team_business_tv = findViewById(R.id.team_business_tv);

        name_tv = findViewById(R.id.name_tv);
        package_tv = findViewById(R.id.package_tv);
        package_name_tv = findViewById(R.id.package_name_tv);
        main_wallet_tv = findViewById(R.id.main_wallet_tv);

        addfund_ll = findViewById(R.id.addfund_ll);
        idactivation_ll = findViewById(R.id.idactivation_ll);
        activation_history_ll = findViewById(R.id.activation_history_ll);
        addfundhistory_ll = findViewById(R.id.addfundhistory_ll);
        roi_income_ll = findViewById(R.id.roi_income_ll);
        reward_ll = findViewById(R.id.reward_ll);

        mainwallet_transfer_ll = findViewById(R.id.mainwallet_transfer_ll);
        p2pfund_ll = findViewById(R.id.p2pfund_ll);
        transfer_history_ll = findViewById(R.id.transfer_history_ll);
        withdrawlrequest_ll = findViewById(R.id.withdrawlrequest_ll);
        withdrawl_history_ll = findViewById(R.id.withdrawl_history_ll);

        pricipal_withdrawal_request_ll = findViewById(R.id.pricipal_withdrawal_request_ll);
        pricipal_withdrawal_report_ll = findViewById(R.id.pricipal_withdrawl_history_ll);


        level_income_ll = findViewById(R.id.level_income_ll);


        refferal_ll = findViewById(R.id.refferal_ll);
        mytree_ll = findViewById(R.id.mytree_ll);
        downlineleftright_ll = findViewById(R.id.downlineleftright_ll);

        referral_ll = findViewById(R.id.referral_ll);
        matching_ll = findViewById(R.id.matching_ll);
        contract_level_ll = findViewById(R.id.contract_level_ll);

        sliding_menu = (ImageView) findViewById(R.id.sliding_menu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        cust_reg_ll = findViewById(R.id.cust_reg_ll);
    }

    private void rewardpopup() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.shibacoin_popup, viewGroup, false);
        Button exit = (Button) dialogView.findViewById(R.id.exit);
        EditText Quantity_et = dialogView.findViewById(R.id.Quantity_et);
        EditText SHIBAddress_et = dialogView.findViewById(R.id.SHIBAddress_et);

        Button submit = (Button) dialogView.findViewById(R.id.submit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Quantity_et.getText().toString().equalsIgnoreCase("")) {
                    Quantity_et.setError("Fields can't be blank!");
                    Quantity_et.requestFocus();
                }
                if (SHIBAddress_et.getText().toString().equalsIgnoreCase("")) {
                    SHIBAddress_et.setError("Fields can't be blank!");
                    SHIBAddress_et.requestFocus();
                } else {
                    Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }
        });
        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DashboardActivity.this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();

    }


    public void DashboardAPI(String action, String pakid, String pass, String userid) {
        String otp1 = new GlobalAppApis().Dashboard(action, pakid, pass, userid);
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
                        String activationDate = jsonobject.getString("ActivationDate");
                        String activeTeam = jsonobject.getString("ActiveTeam");
                        String contractIncome = jsonobject.getString("ContractIncome");
                        String customerId = jsonobject.getString("CustomerId");
                        String directSponsorIncome = jsonobject.getString("DirectSponsorIncome");
                        String emailId = jsonobject.getString("EmailId");
                        String inActiveTeam = jsonobject.getString("InActiveTeam");
                        String levelIncome = jsonobject.getString("LevelIncome");
                        String mainWallet = jsonobject.getString("MainWallet");
                        String mobileNo = jsonobject.getString("MobileNo");
                        String name = jsonobject.getString("Name");
                        String Package = jsonobject.getString("Package");
                        String PackagePrice = jsonobject.getString("PackagePrice");
                        String performanceIncome = jsonobject.getString("PerformanceIncome");
                        String ROIIncome = jsonobject.getString("ROIIncome");
                        String Reward = jsonobject.getString("Reward");
                        String ShibaCoin = jsonobject.getString("ShibaCoin");
                        String SponsorId = jsonobject.getString("SponsorId");
                        String TotalBussiness = jsonobject.getString("TotalBussiness");
                        String TotalIncome = jsonobject.getString("TotalIncome");
                        String TotalTeam = jsonobject.getString("TotalTeam");
                        String Withdrawal = jsonobject.getString("Withdrawal");

                        String TotalReferral = jsonobject.getString("TotalReferral");
                        String EWallet = jsonobject.getString("EWallet");
                        String JoiningDate = jsonobject.getString("JoiningDate");
                        String ActiveReferral = jsonobject.getString("ActiveReferral");

                        ewallet_tv.setText("$ " + EWallet);
                        totalreferral_tv.setText(TotalReferral);
                        joining_tv.setText(JoiningDate);
                        activationdate_tv.setText(activationDate);
                        roi_income_tv.setText("$ " + ROIIncome);
                        direct_income_tv.setText("$ " + directSponsorIncome);
                        reward_tv.setText("$ " + ShibaCoin);
                        active_referral_tv.setText(ActiveReferral);
                        total_activeteam_tv.setText(activeTeam);
                        inactive_team_tv.setText(inActiveTeam);
                        package_name_tv.setText("Package Name\nPackage " + PackagePrice);
                        name_tv.setText(name + "(" + customerId + ")");
                        package_tv.setText("Package Price\n$ " + PackagePrice);
                        main_wallet_tv.setText("Main Wallet\n$ " + mainWallet);
                        team_business_tv.setText("$ " + TotalBussiness);

                        level_income_tv.setText("$ " + Reward);
                        withdraw_tv.setText("$ " + Withdrawal);
                        direct_saponser_income_tv.setText("$ " + performanceIncome);
                        toatl_income_tv.setText("$ " + TotalIncome);
                        user_id_tv.setText(UserId);
                        user_name_tv.setText(name);
                        sponser_id_tv.setText(SponsorId);
                        activaation_date_tv.setText(activationDate);
                        email_tv.setText(emailId);
                        mobile_tv.setText(mobileNo);

//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, DashboardActivity.this, call1, "", true);
    }


    public void getBanner() {
        final ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.show();
        progressDialog.setMessage("Loading");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.teamoftraders.in/Service/GetBannerList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String JsonInString = jsonObject.getString("BannerRes");
                    bannerData = BannerData.createJsonInList(JsonInString);
                    pager.setAdapter(new AdapterForBanner(DashboardActivity.this, bannerData));
                    indicator.setViewPager(pager);
                    indicator.setFillColor(Color.RED);
                    final float density = getResources().getDisplayMetrics().density;
                    indicator.setRadius(5 * density);
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == bannerData.size()) {
                                currentPage = 0;
                            }
                            pager.setCurrentItem(currentPage++, true); }};
                    Timer swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 5000, 3000);
                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;
                        }

                        @Override
                        public void onPageScrolled(int pos, float arg1, int arg2) {
                        }

                        @Override
                        public void onPageScrollStateChanged(int pos) {
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DashboardActivity.this, "Please check your Internet Connection! try again...", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);
    }

    //Create method appInstalledOrNot
//    private boolean appInstalledOrNot(String url){
//        PackageManager packageManager =getPackageManager();
//        boolean app_installed;
//        try {
//            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
//            app_installed = true;
//        }catch (PackageManager.NameNotFoundException e){
//            app_installed = false;
//        }
//        return app_installed;
//    }


    private boolean isAppInstalled(Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}