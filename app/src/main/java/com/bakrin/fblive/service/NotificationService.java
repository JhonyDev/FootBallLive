package com.bakrin.fblive.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.activity.FixtureDetailsActivity;
import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.db.table.FixtureTable;
import com.bakrin.fblive.db.table.NotificationPriorityTable;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.LiveFixtureResponse;
import com.bakrin.fblive.model.response.NotificationPriority;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service implements Info {

    protected APIManager apiManager;
    Timer timer;
    Context context;
    NotificationManagerCompat notificationManagerCompat;
    Timer timer2;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;

        Log.i(TAG, "onStartCommand: Service Started");




        notificationManagerCompat = NotificationManagerCompat.from(context);
        NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(this);
        ArrayList<NotificationPriority> notificationPriorityArrayList
                = notificationPriorityTable.getNotificationPriorityList();
        if (notificationPriorityArrayList == null) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        } else {
            new Handler().postDelayed(() -> {
                checkStatusEachMinute();
                Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show();
            }, 10 * 60 * 1000);


        }
        apiManager = APIManager.getInstance(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkStatusEachMinute() {
        NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(this);

        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                ArrayList<NotificationPriority> notificationPriorityArrayList =
                        notificationPriorityTable.getNotificationPriorityList();
                for (int i = 0; i < notificationPriorityArrayList.size(); i++) {
                    getFixtureData(notificationPriorityArrayList.get(i).getFixtureId());
                }

            }
        };
        timer.schedule(timerTask, 0, 10 * 60 * 1000);
    }

//    private void updateTimeEachSecond() {
//        timer2 = new Timer();
//
//        TimerTask timerTask = new TimerTask() {
//            public void run() {
//
//                Log.i(TAG, "run: Service Running");
//
//            }
//        };
//        timer2.schedule(timerTask, 0, 1000);
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer2.cancel();
    }

    private void getFixtureData(int fixtureId) {
        if (InternetConnection.isConnectingToInternet(this)) {
            apiManager.getAPIService().getFixtureById(fixtureId).enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            Log.i(TAG, "onResponse: id: " + fixtureId);
                            if (response.code() == 200) {
                                assert response.body() != null;
                                FixtureItem fixtureItem = response.body().api.fixtures.get(0);

                                int fixtureID = fixtureItem.fixtureId;
                                String firstHalfResult;
                                String secondHalfResult;

                                Log.i(TAG, "onResponse: Score: " + fixtureItem.score.firstHalfResult);
                                firstHalfResult = fixtureItem.score.firstHalfResult;


                                Log.i(TAG, "onResponse: Score: " + fixtureItem.score.secondHalfResult);
                                secondHalfResult = fixtureItem.score.secondHalfResult;

                                FixtureTable fixtureTable = new FixtureTable(context);

                                if (secondHalfResult != null &&
                                        !fixtureTable.getFinalResultStatus(fixtureItem.fixtureId).equals(SENT_RESULT_STATUS)) {
                                    checkIfInPriority(1, fixtureID, fixtureItem);
                                    fixtureTable.updateFixtureFinalResult(fixtureItem.fixtureId, SENT_RESULT_STATUS);
                                }

                                if (firstHalfResult != null) {
                                    if (Integer.parseInt(fixtureItem.elapsed) < (90 / 2) + 10) {
                                        checkIfInPriority(2, fixtureID, fixtureItem);
                                    }
                                }
                                if (Integer.parseInt(fixtureItem.elapsed) < 10 && fixtureItem.status.equals(MATCH_STARTED)) {
                                    Log.i(TAG, "onResponse: Elapsed: " + fixtureItem.elapsed);
                                    checkIfInPriority(3, fixtureID, fixtureItem);
                                }

                                String CARD = "Card";
                                String GOAL = "Goal";

                                if (fixtureItem.eventsArray == null) {
                                    return;
                                }

                                for (int i = 0; i < fixtureItem.eventsArray.size(); i++) {
                                    Log.i(TAG, "onResponse: Events: " + fixtureItem.eventsArray.get(i).type);
                                    Log.i(TAG, "onResponse: Detail: " + fixtureItem.eventsArray.get(i).detail);
                                    if (fixtureItem.eventsArray.get(i).type.equals(CARD)) {
                                        if (fixtureItem.eventsArray.get(i).detail.equals("Yellow Card")) {
                                            fixtureItem.numberYellow += 1;
                                            checkIfInPriority(4, fixtureID, fixtureItem);
                                        }
                                        if (fixtureItem.eventsArray.get(i).detail.equals("Red Card")) {
                                            fixtureItem.numberRed += 1;
                                            checkIfInPriority(5, fixtureID, fixtureItem);
                                        }
                                    }
                                    if (fixtureItem.eventsArray.get(i).type.equals(GOAL)) {
                                        checkIfInPriority(6, fixtureID, fixtureItem);

                                    }

                                }


                            } else {
                                try {
                                    Utils.errorResponse(response.code(),
                                            NotificationService.this, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<LiveFixtureResponse> call, Throwable t) {
                            Toast.makeText(NotificationService.this, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

    private void checkIfInPriority(int switchValue, int fixtureId, FixtureItem fixtureItem) {
        NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(this);
        ArrayList<Integer> integers = notificationPriorityTable.getPriorityData(fixtureId);
        if (integers.size() < 1) {
            return;
        }


        switch (switchValue) {
            case 1:
                if (integers.get(1).equals(1)) {
                    //TODO: SEND FULL_TIME NOTIFICATION
                    fullTimeNotification(fixtureItem);
                }
                break;
            case 2:
                if (integers.get(2).equals(1)) {
                    //TODO: SEND HALF_TIME NOTIFICATION
                    halfTimeNotification(fixtureItem);

                }
                break;
            case 3:
                if (integers.get(3).equals(1)) {
                    //TODO: SEND KICK_OFF NOTIFICATION
                    kickOffNotification(fixtureItem);

                }
                break;
            case 4:
                if (integers.get(4).equals(1)) {
                    //TODO: SEND RED_CARD NOTIFICATION
                    redNotification(fixtureItem);

                }
                break;
            case 5:
                if (integers.get(5).equals(1)) {
                    //TODO: SEND YELLOW_CARD NOTIFICATION
                    yellowNotification(fixtureItem);

                }
                break;
            case 6:
                if (integers.get(6).equals(1)) {
                    //TODO: SEND GOAL NOTIFICATION
                    goalNotification(fixtureItem);
                }
                break;
        }
    }

    private void fullTimeNotification(FixtureItem fixtureItem) {


        String title = "Full Time";

        String subtitle = fixtureItem.homeTeam.teamName + " " +
                fixtureItem.goalsHomeTeam + "-" + fixtureItem.goalsAwayTeam + " " + fixtureItem.awayTeam.teamName;

        generateCustomLayoutNotification(title, subtitle, "", 1, fixtureItem);
        NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
        if (fixtureItem.status.equals(MATCH_FINISHED)) {
            notificationPriorityTable.deleteFixture(fixtureItem.fixtureId);
        }


    }

    private void halfTimeNotification(FixtureItem fixtureItem) {
        if (fixtureItem.status.equals(MATCH_FINISHED)) {
            NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
            notificationPriorityTable.deleteFixture(fixtureItem.fixtureId);
        } else {
            String title = "Half Time";
            String subtitle = fixtureItem.homeTeam.teamName + " " +
                    fixtureItem.goalsHomeTeam + "-" + fixtureItem.goalsAwayTeam + " " + fixtureItem.awayTeam.teamName;
            generateCustomLayoutNotification(title, subtitle, "", 2, fixtureItem);
        }

    }

    private void kickOffNotification(FixtureItem fixtureItem) {
        if (fixtureItem.status.equals(MATCH_FINISHED)) {
            NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
            notificationPriorityTable.deleteFixture(fixtureItem.fixtureId);
        } else {
            String title = "Kick Off";
            String subtitle = fixtureItem.homeTeam.teamName + " v " + fixtureItem.awayTeam.teamName;
            generateCustomLayoutNotification(title, subtitle, "", 3, fixtureItem);
        }

    }

    private void redNotification(FixtureItem fixtureItem) {
        if (fixtureItem.status.equals(MATCH_FINISHED)) {
            NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
            notificationPriorityTable.deleteFixture(fixtureItem.fixtureId);
        } else {

            String title = "Red Card";
            String subtitle = fixtureItem.homeTeam.teamName + " v " + fixtureItem.awayTeam.teamName;
            String subtitle2 = fixtureItem.elapsed + " min - " + fixtureItem.getNumberOfRedCards();
            generateCustomLayoutNotification(title, subtitle, subtitle2, 4, fixtureItem);
        }

    }

    private void yellowNotification(FixtureItem fixtureItem) {
        if (fixtureItem.status.equals(MATCH_FINISHED)) {
            NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
            notificationPriorityTable.deleteFixture(fixtureItem.fixtureId);
        } else {
            String title = "Yellow Card";
            String subtitle = fixtureItem.homeTeam.teamName + " v " + fixtureItem.awayTeam.teamName;
            String subtitle2 = fixtureItem.elapsed + " min - " + fixtureItem.getNumberOfYellowCards();
            generateCustomLayoutNotification(title, subtitle, subtitle2, 5, fixtureItem);
        }
    }

    private void goalNotification(FixtureItem fixtureItem) {
        if (fixtureItem.status.equals(MATCH_FINISHED)) {
            NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
            notificationPriorityTable.deleteFixture(fixtureItem.fixtureId);
        } else {
            String title = "Goal";
            String subtitle = fixtureItem.homeTeam.teamName + " " +
                    fixtureItem.goalsHomeTeam + "-" + fixtureItem.goalsAwayTeam + " " + fixtureItem.awayTeam.teamName;
            String subtitle2 = fixtureItem.elapsed + " min";
            generateCustomLayoutNotification(title, subtitle, subtitle2, 6, fixtureItem);
        }

    }

    public void generateCustomLayoutNotification(String title, String subTitle, String subtitle2, int id, FixtureItem fixtureItem) {
        Log.i(TAG, "///////generateNotification: ");

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

//        Intent clickIntent = new Intent(context, NotificationReceiver.class);
//        clickIntent.putExtra(FIXTURE_ID_KEY, fixtureItem.fixtureId);
//        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);

//        collapsedView.setOnClickPendingIntent(R.id.click_field, clickPendingIntent);


        collapsedView.setTextViewText(R.id.title, title);
        collapsedView.setTextViewText(R.id.subtitle, subTitle);
        collapsedView.setTextViewText(R.id.subtitle_2, subtitle2);
        switch (id) {
            case 1:
                Intent clickIntent = new Intent(context, FixtureDetailsActivity.class);
                clickIntent.putExtra("fixture", fixtureItem);
                PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 1, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID_1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(collapsedView)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setContentIntent(clickPendingIntent)
                        .build();

                notificationManagerCompat.notify(1, notification);
                break;
            case 2:
                Intent clickIntent2 = new Intent(context, FixtureDetailsActivity.class);
                clickIntent2.putExtra("fixture", fixtureItem);
                PendingIntent clickPendingIntent2 = PendingIntent.getActivity(context, 2, clickIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification2 = new NotificationCompat.Builder(context, CHANNEL_ID_2)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(collapsedView)
                        .setContentIntent(clickPendingIntent2)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManagerCompat.notify(2, notification2);
                break;
            case 3:
                Intent clickIntent3 = new Intent(context, FixtureDetailsActivity.class);
                clickIntent3.putExtra("fixture", fixtureItem);
                PendingIntent clickPendingIntent3 = PendingIntent.getActivity(context, 3, clickIntent3, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification3 = new NotificationCompat.Builder(context, CHANNEL_ID_3)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(collapsedView)
                        .setContentIntent(clickPendingIntent3)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManagerCompat.notify(3, notification3);
                break;
            case 4:
                Intent clickIntent4 = new Intent(context, FixtureDetailsActivity.class);
                clickIntent4.putExtra("fixture", fixtureItem);
                PendingIntent clickPendingIntent4 = PendingIntent.getActivity(context, 4, clickIntent4, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification4 = new NotificationCompat.Builder(context, CHANNEL_ID_4)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(clickPendingIntent4)
                        .setCustomContentView(collapsedView)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManagerCompat.notify(4, notification4);
                break;
            case 5:
                Intent clickIntent5 = new Intent(context, FixtureDetailsActivity.class);
                clickIntent5.putExtra("fixture", fixtureItem);
                PendingIntent clickPendingIntent5 = PendingIntent.getActivity(context, 5, clickIntent5, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification5 = new NotificationCompat.Builder(context, CHANNEL_ID_5)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(collapsedView)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(clickPendingIntent5)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManagerCompat.notify(5, notification5);
                break;
            case 6:
                Intent clickIntent6 = new Intent(context, FixtureDetailsActivity.class);
                clickIntent6.putExtra("fixture", fixtureItem);
                PendingIntent clickPendingIntent6 = PendingIntent.getActivity(context, 6, clickIntent6, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification6 = new NotificationCompat.Builder(context, CHANNEL_ID_6)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setCustomContentView(collapsedView)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(clickPendingIntent6)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManagerCompat.notify(6, notification6);
                break;
        }

    }
}
