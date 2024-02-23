package com.sign.akp_teamoftraders.Basic;

import org.json.JSONException;
import org.json.JSONObject;

public class GlobalAppApis {
    public String Login(String action, String pakid, String pass,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Packageid", pakid);
            jsonObject1.put("Password", pass);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String NewAccount(String action, String Country, String Email,String FullName,String MobilNo,String Password,String SponsorId,String UserId,String phonecode,String WalletId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Country", Country);
            jsonObject1.put("Email", Email);
            jsonObject1.put("FullName", FullName);
            jsonObject1.put("MobilNo", MobilNo);
            jsonObject1.put("Password", Password);
            jsonObject1.put("SponsorId", SponsorId);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("phonecode", phonecode);
            jsonObject1.put("WalletId", WalletId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String UpdateProfile(String action, String Country, String Email,String FullName,String MobilNo,String NewPassword,
                                String OldPassword,String SponsorId,String USDTAddress,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Country", Country);
            jsonObject1.put("Email", Email);
            jsonObject1.put("FullName", FullName);
            jsonObject1.put("MobilNo", MobilNo);
            jsonObject1.put("NewPassword", NewPassword);
            jsonObject1.put("OldPassword", OldPassword);
            jsonObject1.put("SponsorId", SponsorId);
            jsonObject1.put("USDTAddress", USDTAddress);
            jsonObject1.put("UserId", UserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String ChangePassword(String NewPassword,
                                String OldPassword,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Country", "");
            jsonObject1.put("Email", "");
            jsonObject1.put("FullName", "");
            jsonObject1.put("MobilNo", "");
            jsonObject1.put("NewPassword", NewPassword);
            jsonObject1.put("OldPassword", OldPassword);
            jsonObject1.put("SponsorId", "");
            jsonObject1.put("USDTAddress", "");
            jsonObject1.put("UserId", UserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Dashboard(String action, String pakid, String pass,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Packageid", pakid);
            jsonObject1.put("Password", pass);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }





    public String Profile(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Wallet(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String TopupDetails(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String MyReferral(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String MyTeam(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String DirectIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String LevelIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String ContractIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String ShowWithdrwarAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String WalletHistoryAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String AccountActivationAPI(String ActivationId,String Amount,String Password,String TransactionHash,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("ActivationId", ActivationId);
            jsonObject1.put("PacakgeId", Amount);
            jsonObject1.put("Password", Password);
            jsonObject1.put("TransactionHash", TransactionHash);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String FundRequestAPI(String Amount,String ReceiptImg,String TransactionHash,String UserId,String CoinType) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Amount", Amount);
            jsonObject1.put("ReceiptImg", ReceiptImg);
            jsonObject1.put("TransactionHash", TransactionHash);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("CoinType", CoinType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetFundHistory(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetPerformanceIncome(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GetClubLeaderIncomeReport(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String RewardIncome(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Inbox(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Outbox(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String MyDirect(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String TransferHistory(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }





    public String MainWallet(String CashWallet,String UserId,String WalletBalance) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("CashWallet", CashWallet);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("WalletBalance", WalletBalance);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String P2PTransfer(String Amount,String ReceiverId,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Amount", Amount);
            jsonObject1.put("ReceiverId", ReceiverId);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Support(String Message,String Subject,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Message", Message);
            jsonObject1.put("Subject", Subject);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String WithdrawlRequest(String Balance,String RequestWalletAmt,String UserId,String CoinType) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "1");
            jsonObject1.put("Balance", Balance);
            jsonObject1.put("RequestWalletAmt", RequestWalletAmt);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("CoinType", CoinType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String GetPrinciplePackageList(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String PrincipalWithdrawlRequestAPI(String Balance,String PacakgeId,String RequestWalletAmt,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Balance", Balance);
            jsonObject1.put("PacakgeId", PacakgeId);
            jsonObject1.put("RequestWalletAmt", RequestWalletAmt);
            jsonObject1.put("UserId", UserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String SendOTP(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String CheckSponserA(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
}

