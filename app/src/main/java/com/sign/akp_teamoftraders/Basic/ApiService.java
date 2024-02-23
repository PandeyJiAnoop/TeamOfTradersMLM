package com.sign.akp_teamoftraders.Basic;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("Login")
    Call<String> getLogin(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("NewAccount")
    Call<String> getNewAccount(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("UpdateAccountProfile")
    Call<String> getUpdateAccountProfile(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("ChangePassword")
    Call<String> getChangePassword(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Dashboard")
    Call<String> getDashboard(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("Profile")
    Call<String> getProfile(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("TopupDetails")
    Call<String> getTopupDetails(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("GetWallet")
    Call<String> getWallet(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("MyRefferal")
    Call<String> getReferral(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("MyTeam")
    Call<String> getMyTeam(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("DirectIncome")
    Call<String> getDirectincome(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("LevelIncome")
    Call<String> getLevelincome(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("ContractIncome")
    Call<String> getContractincome(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("OpenWithdrawal")
    Call<String> getwithdrwar(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("WithdrawalHistory")
    Call<String> getWalletHistory(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("AccountActivation")
    Call<String> getAccountActivation(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("FundRequest")
    Call<String> getFundRequest(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetFundHistory")
    Call<String> getGetFundHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("PerformanceIncome")
    Call<String> getPerformanceIncome(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ClubLeaderIncomeReport")
    Call<String> getClubLeaderIncomeReport(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("RewardIncome")
    Call<String> getRewardIncome(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Inbox")
    Call<String> getInbox(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Outbox")
    Call<String> getOutbox(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("MyDirect")
    Call<String> getMyDirect(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("TransferHistory")
    Call<String> getTransferHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("MainWallet")
    Call<String> getMainWallet(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("P2PTransfer")
    Call<String> getP2PTransfer(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Support")
    Call<String> getSupport(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("WithdrawlRequest")
    Call<String> getWithdrawlRequest(
            @Body String body);
    @Headers("Content-Type: application/json")
    @GET("GetCountryList")
    Call<String> API_GetCountryList();

    @Headers("Content-Type: application/json")
    @GET("GetPackageList")
    Call<String> API_GetPackageList();

    @Headers("Content-Type: application/json")
    @POST("GetPrinciplePackageList")
    Call<String> API_GetPrinciplePackageList(
            @Body String body);


    @Headers("Content-Type: application/json")
    @POST("PrincipalWithdrawlRequest")
    Call<String> API_PrincipalWithdrawlRequest(
            @Body String body);


    @Headers("Content-Type: application/json")
    @POST("SendOTP")
    Call<String> getSendOTP(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("CheckSponser")
    Call<String> getCheckSponser(
            @Body String body);

}