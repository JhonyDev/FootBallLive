package com.bakrin.fblive.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.activity.FixtureDetailsActivity;
import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.LiveFixtureResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver implements Info {
    APIManager apiManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        apiManager = APIManager.getInstance(context);

        onClickListener(context);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);

        Log.i(TAG, "onReceive: " + intent.getExtras());
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        int fixtureId = 0;
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            fixtureId = bundle.getInt(FIXTURE_ID_KEY);
            Log.i(TAG, "onReceive: idddddd: " + fixtureId);
        }
        if (fixtureId != 0) {
            Log.i(TAG, "onReceive: " + fixtureId);
            getFixtureData(fixtureId, context);
        }


    }

    private void onClickListener(Context context) {
        Toast.makeText(context, "Notification CLicked", Toast.LENGTH_SHORT).show();
    }

    private void getFixtureData(int fixtureId, Context context) {
        if (InternetConnection.isConnectingToInternet(context)) {
            apiManager.getAPIService().getFixtureById(fixtureId).enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            Log.i(TAG, "onResponse: id: " + fixtureId);
                            if (response.code() == 200) {
                                assert response.body() != null;
                                FixtureItem fixtureItem = response.body().api.fixtures.get(0);
                                Intent intent1 = new Intent(context, FixtureDetailsActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                                if (fixtureItem != null) {
                                    intent1.putExtra("fixture", fixtureItem);
                                    context.startActivity(intent1);
                                    Log.i(TAG, "onReceive: fixtureId: " + fixtureItem.fixtureId);
                                    Log.i(TAG, "onReceive: Starting the activity");
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<LiveFixtureResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(context, context.getString(R.string.network),
                    context.getString(R.string.no_internet), DialogType.INFO, null);
        }


    }


}
