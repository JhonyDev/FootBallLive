package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bakrin.fblive.R;
import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.api.APIService;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.model.Pojo.CreatedUser;
import com.bakrin.fblive.utils.Utils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pusher.pushnotifications.BeamsCallback;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.pushnotifications.PusherCallbackError;
import com.pusher.pushnotifications.auth.AuthData;
import com.pusher.pushnotifications.auth.AuthDataGetter;
import com.pusher.pushnotifications.auth.BeamsTokenProvider;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements Info {

    String instanceId = "1889a652-be8c-4e56-aed1-04bedd6eff47";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!Utils.getIdInSharedPrefs(this).equals(NO_ID)) {
            startNewActivity(instanceId, Utils.getIdInSharedPrefs(this));
            Log.i(TAG, "onCreate: userId " + Utils.getIdInSharedPrefs(this));
            return;
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Utils.putIdInSharedPrefs(token, this);
                });
        Log.i(TAG, "onCreate: " + Utils.getIdInSharedPrefs(this));


        initUserTokenProcess();

        final CreatedUser createdUser = new CreatedUser(String.valueOf(System.currentTimeMillis()));
        APIManager.getRetrofit()
                .create(APIService.class)
                .postUserId(createdUser)
                .enqueue(new Callback<CreatedUser>() {
                    @Override
                    public void onResponse(@NotNull Call<CreatedUser> call, @NotNull Response<CreatedUser> response) {
                        Log.i(TAG, "onResponse: ");
                        if (response.isSuccessful()) {
                            Utils.putIdInSharedPrefs(createdUser.getUser_token(), SplashActivity.this);

                            Log.i(TAG, "onResponse: " + createdUser.getUser_token());
                            startNewActivity(instanceId, createdUser.user_token);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<CreatedUser> call, @NotNull Throwable t) {
                        Toast.makeText(SplashActivity.this, "Error communicating with the server", Toast.LENGTH_SHORT).show();
//                        startNewActivity();
                        Log.i(TAG, "onFailure: " + t.getMessage());
                        Log.i(TAG, "onFailure: " + t.getCause());
                    }
                });

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

    private void initUserTokenProcess() {
        BeamsTokenProvider tokenProvider = new BeamsTokenProvider(
                "http://165.227.124.80/api/post_user_id",
                new AuthDataGetter() {
                    @NotNull
                    @Override
                    public AuthData getAuthData() {
                        // Headers and URL query params your auth endpoint needs to
                        // request a Beams Token for a given user
                        HashMap<String, String> headers = new HashMap<>();
                        // for example:
                        // headers.put("Authorization", sessionToken);
                        HashMap<String, String> queryParams = new HashMap<>();
                        return new AuthData(
                                headers,
                                queryParams
                        );
                    }
                }
        );
        PushNotifications.setUserId("<USER_ID_GOES_HERE>", tokenProvider, new BeamsCallback<Void, PusherCallbackError>() {
            @Override
            public void onSuccess(@NotNull Void... values) {
                Log.i("PusherBeams", "Successfully authenticated with Pusher Beams");
                Toast.makeText(SplashActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(PusherCallbackError error) {
                Log.i("PusherBeams", "Pusher Beams authentication failed: " + error.getMessage());
            }
        });

    }


    private void startNewActivity(String instanceId, String userToken) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            PushNotifications.start(getApplicationContext(), instanceId);
            PushNotifications.addDeviceInterest(userToken);

            Log.i(TAG, "startNewActivity: ----------" + userToken + "------------");
            Intent intent = new Intent(SplashActivity.this, LiveFixtureActivity.class);
            Log.i(TAG, "startNewActivity: " + Utils.getIdInSharedPrefs(this));
            startActivity(intent);
            finish();
        }, 3000);

    }
}
