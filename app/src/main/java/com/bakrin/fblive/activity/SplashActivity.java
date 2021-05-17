package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bakrin.fblive.R;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.service.NotificationService;
import com.bakrin.fblive.utils.Utils;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity implements Info {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                    Log.i(TAG, "onComplete: " + task.getResult());
                    Utils.putIdInSharedPrefs(task.getResult(), this);
                }
        );


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LiveFixtureActivity.class);
            startActivity(intent);

            startService(new Intent(SplashActivity.this, NotificationService.class));

            finish();
        }, 3000);

//        if (InternetConnection.isConnectingToInternet(this)) {
//
//            MainApplication.getInstance().getApiManager().getAPIService().
//
//            MainApplication.getInstance().getApiManager().getAPIService().
//
////            MainApplication.getInstance().getAPIService().signInTemp(request).
////                    enqueue(new Callback<SignInTempResponse>() {
////                        @Override
////                        public void onResponse(Call<SignInTempResponse> call,
////                                               Response<SignInTempResponse> response) {
////                            CustomDialog.hideProgressDialog();
////                            if (response.code() == 200) {
////
////                                final SignInTempResponse signInResponse = response.body();
////
////                                if (signInResponse.strRturnRes) {
////
////                                    settings.setSignInTempData(signInResponse);
////                                    settings.setLoginAction(FirstLoginActivity.this, Constant.TEMP_USER);
////
////                                } else {
////                                    CustomDialog.showGeneralDialog(context, "", "Invalid Credentials. Please try again",
////                                            DialogType.INFO, null);
////
////                                }
////
////                            } else {
////                                try {
//////                                    Utils.errorResponse(response.code(), context,
//////                                            response.errorBody().string(), validation,settings,null);
////                                } catch (Exception e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                        }
////
////                        @Override
////                        public void onFailure(Call<SignInTempResponse> call, Throwable t) {
////                            CustomDialog.hideProgressDialog();
////                            t.printStackTrace();
////                            Toast.makeText(SplashActivity.this, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
////                        }
////                    });
//
//        } else {
//            CustomDialog.showGeneralDialog(this, getString(R.string.network),
//                    getString(R.string.no_internet), DialogType.INFO, null);
//        }


    }
}
