package com.bakrin.fblive.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.CardListAdapter;
import com.bakrin.fblive.adapter.FixtureGoalListAdapter;
import com.bakrin.fblive.adapter.FixtureListAdapter;
import com.bakrin.fblive.adapter.SubstListAdapter;
import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.api.APIService;
import com.bakrin.fblive.db.table.FixtureTable;
import com.bakrin.fblive.db.table.NotificationPriorityTable;
import com.bakrin.fblive.db.table.TeamTable;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.FixtureStatEvent;
import com.bakrin.fblive.model.response.FixtureStats;
import com.bakrin.fblive.model.response.H2HResponse;
import com.bakrin.fblive.model.response.NotificationPriority;
import com.bakrin.fblive.model.response.StatsResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixtureDetailsActivity extends BaseActivity {

    /**
     * download h2h
     */
    public static final String TAG = "///////";
    @BindView(R.id.liveScoreLinearLayout)
    LinearLayout liveScoreLinearLayout;
    @BindView(R.id.leagueActionBarImageView)
    ImageView leagueActionBarImageView;
    @BindView(R.id.leagueNameActionBarTextView)
    TextView leagueNameActionBarTextView;
    @BindView(R.id.countryNameActionBarTextView)
    TextView countryNameActionBarTextView;
    @BindView(R.id.roundTextView)
    TextView roundTextView;
    @BindView(R.id.homeImageView)
    ImageView homeImageView;
    @BindView(R.id.homeTextView)
    TextView homeTextView;
    @BindView(R.id.awayImageView)
    ImageView awayImageView;
    @BindView(R.id.awayTextView)
    TextView awayTextView;
    @BindView(R.id.homeGoalTextView)
    TextView homeGoalTextView;
    @BindView(R.id.awayGoalTextView)
    TextView awayGoalTextView;
    @BindView(R.id.venueTextView)
    TextView venueTextView;
    @BindView(R.id.statusTextView)
    TextView statusTextView;
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.statsLinearLayout)
    LinearLayout statsLinearLayout;
    @BindView(R.id.statsRootLinearLayout)
    LinearLayout statsRootLinearLayout;
    @BindView(R.id.h2hRootLinearLayout)
    LinearLayout h2hRootLinearLayout;
    @BindView(R.id.statView)
    View statView;
    @BindView(R.id.h2hView)
    View h2hView;
    @BindView(R.id.goalsRecyclerView)
    RecyclerView goalsRecyclerView;
    @BindView(R.id.cardRecyclerView)
    RecyclerView cardRecyclerView;
    @BindView(R.id.substRecyclerView)
    RecyclerView substRecyclerView;
    @BindView(R.id.homePosTextView)
    TextView homePosTextView;
    @BindView(R.id.awayPosTextView)
    TextView awayPosTextView;
    @BindView(R.id.homeTotalShotTextView)
    TextView homeTotalShotTextView;
    @BindView(R.id.awayTotalShotTextView)
    TextView awayTotalShotTextView;
    @BindView(R.id.homeShotTargetTextView)
    TextView homeShotTargetTextView;
    @BindView(R.id.awayShotTargetTextView)
    TextView awayShotTargetTextView;
    @BindView(R.id.homeShotOffTextView)
    TextView homeShotOffTextView;
    @BindView(R.id.awayShotOffTextView)
    TextView awayShotOffTextView;
    @BindView(R.id.homeBlockedTextView)
    TextView homeBlockedTextView;
    @BindView(R.id.awayBlockedTextView)
    TextView awayBlockedTextView;
    @BindView(R.id.homeFoulsTextView)
    TextView homeFoulsTextView;
    @BindView(R.id.awayFoulsTextView)
    TextView awayFoulsTextView;
    @BindView(R.id.homeCornersTextView)
    TextView homeCornersTextView;
    @BindView(R.id.awayCornersTextView)
    TextView awayCornersTextView;
    @BindView(R.id.homeOffsidesTextView)
    TextView homeOffsidesTextView;
    @BindView(R.id.awayOffsidesTextView)
    TextView awayOffsidesTextView;
    @BindView(R.id.homeAccurateTextView)
    TextView homeAccurateTextView;
    @BindView(R.id.awayAccurateTextView)
    TextView awayAccurateTextView;
    @BindView(R.id.homeYellowCardTextView)
    TextView homeYellowCardTextView;
    @BindView(R.id.awayYellowCardTextView)
    TextView awayYellowCardTextView;
    @BindView(R.id.homeRedCardTextView)
    TextView homeRedCardTextView;
    @BindView(R.id.awayRedCardTextView)
    TextView awayRedCardTextView;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.matchStatsLinearLayout)
    LinearLayout matchStatsLinearLayout;
    @BindView(R.id.h2hListRecyclerView)
    RecyclerView h2hListRecyclerView;
    @BindView(R.id.homeSaveImageView)
    ImageView homeSaveImageView;
    @BindView(R.id.awaySaveImageView)
    ImageView awaySaveImageView;

    @BindView(R.id.ib_notification_bell)
    ImageButton notificationBellIcon;

    @BindView(R.id.ib_fav)
    ImageButton fav;

    FixtureTable table;


    int fullTimeResultInt = 0;
    int halfTimeResultInt = 0;
    int kickOffInt = 0;
    int redCardsInt = 0;
    int yellowCardsInt = 0;
    int goalsInt = 0;
    PopupWindow mypopupWindow;
    NotificationPriorityTable notificationPriorityTable;
    private FixtureItem fixture;
    private SimpleDateFormat fmt;
    private SimpleDateFormat apiFmt;
    private ArrayList<FixtureStatEvent> goalList;
    private ArrayList<FixtureStatEvent> cardList;
    private ArrayList<FixtureStatEvent> substList;
    private FixtureStats fixtureStats;
    private ArrayList<FixtureItem> h2hFixtureItems;
    private FixtureGoalListAdapter goalListAdapter;
    private CardListAdapter cardListAdapter;
    private SubstListAdapter substListAdapter;
    private FixtureListAdapter listAdapter;
    private TeamTable teamTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixture = getIntent().getExtras().getParcelable("fixture");
        fmt = new SimpleDateFormat("EEE dd MMM, yyyy hh:mm aaa");
        apiFmt = new SimpleDateFormat("yyyy-MM-dd");
        init();
        Log.i(TAG, "onCreate: FixtureDetailsActivity");
        table = new FixtureTable(this);
        initTimer();

        if (table.getTeamStatus(fixture.fixtureId) > 0) {
            fav.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star_fill));
        } else {
            fav.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star));
        }

        notificationPriorityTable = new NotificationPriorityTable(context);


        boolean isNotificationEnabled = false;
        ArrayList<Integer> integers = notificationPriorityTable.getPriorityData(fixture.getFixtureId());
        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i).equals(1)) {
                isNotificationEnabled = true;
                break;
            }
        }

        for (int i = 0; i < integers.size(); i++) {
            Log.i(TAG, "onCreate: " + integers.get(i));
            if (integers.get(i) == 1) {
                isNotificationEnabled = true;
                break;
            }
        }

        if (isNotificationEnabled) {
            notificationBellIcon.setBackgroundResource(R.drawable.notificications_enabled);
        } else {
            notificationBellIcon.setBackgroundResource(R.drawable.notifications_disabled);
        }
        setPopUpWindow();

    }

    private void initTimer() {
        String strElapsed = fixture.getElapsed();
        try {
            Log.i(TAG, "initTimer: " + fixture.elapsed + 2);
            final int[] sec = {0};
            final int[] min = {Integer.parseInt(strElapsed) + 2};
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        String timer = min[0] + ":" + sec[0];

                        if (sec[0] > 60) {
                            sec[0] = 0;
                            min[0]++;
                        }
                        tvTimer.setText(timer);
                        sec[0]++;

                    });
                }
            }, 0, 1000);


        } catch (Exception e) {
            Log.i(TAG, "initTimer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_fixture_details;
    }

    @OnClick(R.id.backFixtureImageView)
    public void onBackClick() {
        finish();
    }

    /**
     * initialize component
     */
    private void init() {
        setActionBar("", new ActionbarMenu[]{ActionbarMenu.FIXTURE_LAYOUT});

        goalList = new ArrayList<>();
        cardList = new ArrayList<>();
        substList = new ArrayList<>();

        teamTable = new TeamTable(context);
        homeBookMarkCheck();
        awayBookMarkCheck();

        Utils.log("Fixture ID", " : " + fixture.fixtureId);
        Date matchDate = null;
        try {
            matchDate = apiFmt.parse(fixture.eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (fixture.statusShort.equalsIgnoreCase("NS") ||
                fixture.statusShort.equalsIgnoreCase("SUSP") ||
                fixture.statusShort.equalsIgnoreCase("PST") ||
                fixture.statusShort.equalsIgnoreCase("CANC") ||
                fixture.statusShort.equalsIgnoreCase("ABD") ||
                fixture.statusShort.equalsIgnoreCase("INT")) {
            liveScoreLinearLayout.setVisibility(View.GONE);
            statsLinearLayout.setVisibility(View.GONE);
            goalsRecyclerView.setVisibility(View.GONE);
            setupTab(1);
        } else {
            liveScoreLinearLayout.setVisibility(View.VISIBLE);
            statsLinearLayout.setVisibility(View.VISIBLE);
            goalsRecyclerView.setVisibility(View.VISIBLE);
            setupTab(0);
        }


        h2hListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmH2h = new LinearLayoutManager(context);
        llmH2h.setOrientation(LinearLayoutManager.VERTICAL);
        h2hListRecyclerView.setLayoutManager(llmH2h);
        h2hListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        goalsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJustPay = new LinearLayoutManager(context);
        llmJustPay.setOrientation(LinearLayoutManager.VERTICAL);
        goalsRecyclerView.setLayoutManager(llmJustPay);
        goalsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        cardRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJCard = new LinearLayoutManager(context);
        llmJCard.setOrientation(LinearLayoutManager.VERTICAL);
        cardRecyclerView.setLayoutManager(llmJCard);
        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());

        substRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJSubst = new LinearLayoutManager(context);
        llmJSubst.setOrientation(LinearLayoutManager.VERTICAL);
        substRecyclerView.setLayoutManager(llmJSubst);
        substRecyclerView.setItemAnimator(new DefaultItemAnimator());

        roundTextView.setText(fixture.round);
        statusTextView.setText(fixture.status);
        venueTextView.setText(fixture.venue);
        timeTextView.setText(fmt.format(matchDate));
        homeGoalTextView.setText(String.valueOf(fixture.goalsHomeTeam));
        awayGoalTextView.setText(String.valueOf(fixture.goalsAwayTeam));

        if (fixture.league != null) {
            leagueNameActionBarTextView.setText(fixture.league.name);
            countryNameActionBarTextView.setText(fixture.league.country);
            Picasso.get()
                    .load(fixture.league.logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(leagueActionBarImageView);
        }

        if (fixture.homeTeam != null) {
            homeTextView.setText(fixture.homeTeam.teamName);
            Picasso.get()
                    .load(fixture.homeTeam.logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(homeImageView);
        }

        if (fixture.awayTeam != null) {
            awayTextView.setText(fixture.awayTeam.teamName);
            Picasso.get()
                    .load(fixture.awayTeam.logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(awayImageView);
        }
    }

    private void awayBookMarkCheck() {
        int count = teamTable.getTeamStatus(fixture.awayTeam.teamId);
        Utils.log("count 01 ", " : " + count);
        awaySaveImageView.setTag(count);
        if (count > 0) {
            awaySaveImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_fill));
        } else {
            awaySaveImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_out_line));
        }
    }

    private void homeBookMarkCheck() {
        int count = teamTable.getTeamStatus(fixture.homeTeam.teamId);
        Utils.log("count 01 ", " : " + count);
        homeSaveImageView.setTag(count);
        if (count > 0) {
            homeSaveImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_fill));
        } else {
            homeSaveImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_out_line));
        }
    }

    @OnClick(R.id.awaySaveImageView)
    public void onAwaySaveClick(View view) {
        int count = (int) view.getTag();
        Utils.log("count 02 ", " : " + count);
        if (count <= 0) {
            teamTable.insertTeam(fixture.awayTeam);
        } else {
            teamTable.deleteTeam(fixture.awayTeam.teamId);
        }
        awayBookMarkCheck();
    }

    @OnClick(R.id.homeSaveImageView)
    public void onHomeSaveClick(View view) {
        int count = (int) view.getTag();
        Utils.log("count 02 ", " : " + count);
        if (count <= 0) {
            teamTable.insertTeam(fixture.homeTeam);
        } else {
            teamTable.deleteTeam(fixture.homeTeam.teamId);
        }
        homeBookMarkCheck();
    }

    @OnClick(R.id.statsLinearLayout)
    public void onStatsClick() {
        setupTab(0);
    }

    @OnClick(R.id.h2hLinearLayout)
    public void onH2HClick() {
        setupTab(1);
    }

    /**
     * setup tab view
     */
    private void setupTab(int pos) {
        statView.setVisibility(View.INVISIBLE);
        h2hView.setVisibility(View.INVISIBLE);
        if (pos == 0) {
            statView.setVisibility(View.VISIBLE);
            statsRootLinearLayout.setVisibility(View.VISIBLE);
            h2hRootLinearLayout.setVisibility(View.GONE);
            if (fixtureStats == null) {
                downloadStat();
            }
        } else {
            h2hView.setVisibility(View.VISIBLE);
            statsRootLinearLayout.setVisibility(View.GONE);
            h2hRootLinearLayout.setVisibility(View.VISIBLE);
            if (h2hFixtureItems == null) {
                downloadH2H();
            }
        }
    }

    private void downloadH2H() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getH2H(fixture.homeTeam.teamId, fixture.awayTeam.teamId).enqueue(
                    new Callback<H2HResponse>() {
                        @Override
                        public void onResponse(Call<H2HResponse> call, Response<H2HResponse> response) {
                            hideProgressBar(context);

                            Log.i(TAG, "onResponse: " + response);

                            if (response.code() == 200) {
                                h2hFixtureItems = response.body().api.fixtures;
                                setupH2hListAdapter();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<H2HResponse> call, Throwable t) {
                            hideProgressBar(context);
                            t.printStackTrace();
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

    private void setupH2hListAdapter() {
        listAdapter = new FixtureListAdapter(false, this, h2hFixtureItems, (pos, fixtureItem, actions) -> {
            Intent call = new Intent(context, FixtureDetailsActivity.class);
            call.putExtra("fixture", fixtureItem);
            startActivity(call);
        });
        h2hListRecyclerView.setAdapter(listAdapter);
    }

    /**
     * download stats
     */
    private void downloadStat() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getFixtureStat(fixture.fixtureId).enqueue(
                    new Callback<StatsResponse>() {
                        @Override
                        public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                fixtureStats = response.body().api.fixtures.get(0);
                                setupStatsLists();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<StatsResponse> call, Throwable t) {
                            hideProgressBar(context);
                            t.printStackTrace();
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

    private void setupStatsLists() {
        if (fixtureStats != null) {
            if (fixtureStats.events != null) {
                for (int i = 0; i < fixtureStats.events.size(); i++) {
                    FixtureStatEvent event = fixtureStats.events.get(i);
                    if (event.type.equalsIgnoreCase("Card")) {
                        cardList.add(event);
                    }
                    if (event.type.equalsIgnoreCase("Goal")) {
                        goalList.add(event);
                    }
                    if (event.type.equalsIgnoreCase("subst")) {
                        substList.add(event);
                    }
                }
            }

            updateGoalList();
            updateCardList();
            updateSubstList();
            setupStats();
        }
    }

    private void setupStats() {
        if (fixtureStats.statistics != null) {
            if (fixtureStats.statistics.BallPossession != null) {
                homePosTextView.setText(fixtureStats.statistics.BallPossession.home);
                awayPosTextView.setText(fixtureStats.statistics.BallPossession.away);
            }
            if (fixtureStats.statistics.Total_Shots != null) {
                homeTotalShotTextView.setText(fixtureStats.statistics.Total_Shots.home);
                awayTotalShotTextView.setText(fixtureStats.statistics.Total_Shots.away);
            }
            if (fixtureStats.statistics.Shots_on_Goal != null) {
                homeShotTargetTextView.setText(fixtureStats.statistics.Shots_on_Goal.home);
                awayShotTargetTextView.setText(fixtureStats.statistics.Shots_on_Goal.away);
            }
            if (fixtureStats.statistics.Shots_off_Goal != null) {
                homeShotOffTextView.setText(fixtureStats.statistics.Shots_off_Goal.home);
                awayShotOffTextView.setText(fixtureStats.statistics.Shots_off_Goal.away);
            }
            if (fixtureStats.statistics.Blocked_Shots != null) {
                homeBlockedTextView.setText(fixtureStats.statistics.Blocked_Shots.home);
                awayBlockedTextView.setText(fixtureStats.statistics.Blocked_Shots.away);
            }
            if (fixtureStats.statistics.Fouls != null) {
                homeFoulsTextView.setText(fixtureStats.statistics.Fouls.home);
                awayFoulsTextView.setText(fixtureStats.statistics.Fouls.away);
            }

            if (fixtureStats.statistics.Corner_Kicks != null) {
                homeCornersTextView.setText(fixtureStats.statistics.Corner_Kicks.home);
                awayCornersTextView.setText(fixtureStats.statistics.Corner_Kicks.away);
            }

            if (fixtureStats.statistics.Offsides != null) {
                homeOffsidesTextView.setText(fixtureStats.statistics.Offsides.home);
                awayOffsidesTextView.setText(fixtureStats.statistics.Offsides.away);
            }

            if (fixtureStats.statistics.PassesAccurate != null) {
                homeAccurateTextView.setText(fixtureStats.statistics.PassesAccurate.home);
                awayAccurateTextView.setText(fixtureStats.statistics.PassesAccurate.away);
            }

            if (fixtureStats.statistics.YellowCards != null) {
                homeYellowCardTextView.setText(fixtureStats.statistics.YellowCards.home);
                awayYellowCardTextView.setText(fixtureStats.statistics.YellowCards.away);
            }

            if (fixtureStats.statistics.RedCards != null) {
                homeRedCardTextView.setText(fixtureStats.statistics.RedCards.home);
                awayRedCardTextView.setText(fixtureStats.statistics.RedCards.away);
            }
        } else {
            matchStatsLinearLayout.setVisibility(View.GONE);
        }
    }

    private void updateSubstList() {
        substListAdapter = new SubstListAdapter(this, substList, fixture.homeTeam.teamId, fixture.awayTeam.teamId);
        substRecyclerView.setAdapter(substListAdapter);
    }

    private void updateCardList() {
        cardListAdapter = new CardListAdapter(this, cardList, fixture.homeTeam.teamId, fixture.awayTeam.teamId);
        cardRecyclerView.setAdapter(cardListAdapter);
    }

    /**
     * update goal list adapter
     */

    private void updateGoalList() {
        goalListAdapter = new FixtureGoalListAdapter(this, goalList, fixture.homeTeam.teamId, fixture.awayTeam.teamId);
        goalsRecyclerView.setAdapter(goalListAdapter);
    }

    private void initFav() {
        FixtureItem item = fixture;
        item.final_result_cast = Info.NOT_SENT_RESULT_STATUS;
        if (table.getTeamStatus(item.fixtureId) > 0)
            table.deleteFixture(item.fixtureId);
        else
            table.insertFixture(item);


        if (table.getTeamStatus(fixture.fixtureId) > 0)
            fav.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star_fill));
        else
            fav.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star));


    }

    private void setPopUpWindow() {
        fullTimeResultInt = 0;
        halfTimeResultInt = 0;
        kickOffInt = 0;
        redCardsInt = 0;
        yellowCardsInt = 0;
        goalsInt = 0;

        Log.i(TAG, "setPopUpWindow: Setting up popup Window");
        LayoutInflater inflater = (LayoutInflater)
                context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup, null);

        notificationPriorityTable = new NotificationPriorityTable(context);
        Log.i(TAG, "setPopUpWindow: " + fixture.getFixtureId());

        ArrayList<Integer> arrayList = notificationPriorityTable.getPriorityData(fixture.getFixtureId());

        mypopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        CheckBox fullTimeResult = view.findViewById(R.id.full_time_result);
        CheckBox halfTimeResult = view.findViewById(R.id.half_time_result);
        CheckBox kickOff = view.findViewById(R.id.kick_off);
        CheckBox redCards = view.findViewById(R.id.red_cards);
        CheckBox yellowCards = view.findViewById(R.id.yellow_cards);
        CheckBox goals = view.findViewById(R.id.goals);
        CheckBox notifications = view.findViewById(R.id.notification);

        fullTimeResult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fullTimeResultInt = 1;
            } else {
                fullTimeResultInt = 0;
            }
        });
        halfTimeResult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                halfTimeResultInt = 1;
            } else {
                halfTimeResultInt = 0;
            }
        });
        kickOff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                kickOffInt = 1;
            } else {
                kickOffInt = 0;
            }
        });
        redCards.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                redCardsInt = 1;
            } else {
                redCardsInt = 0;
            }
        });
        yellowCards.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                yellowCardsInt = 1;
            } else {
                yellowCardsInt = 0;
            }
        });
        goals.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                goalsInt = 1;
            } else {
                goalsInt = 0;
            }
        });
        mypopupWindow.setOnDismissListener(() -> {

            if (fullTimeResultInt == 0
                    && halfTimeResultInt == 0
                    && kickOffInt == 0
                    && redCardsInt == 0
                    && yellowCardsInt == 0
                    && goalsInt == 0) {
                notificationBellIcon.setImageResource(R.drawable.notifications_disabled);
                notificationPriorityTable.deleteFixture(fixture.getFixtureId());

            } else {
                try {
                    notificationPriorityTable.deleteFixture(fixture.getFixtureId());
                    notificationPriorityTable.insertFixture(fixture.getFixtureId(),
                            fullTimeResultInt, halfTimeResultInt, kickOffInt, redCardsInt, yellowCardsInt, goalsInt);
                    Log.i(TAG, "setPopUpWindow: inserted");
                } catch (Exception e) {
                    Toast.makeText(context, "Error updating", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "setPopUpWindow: updated");
                }


                notificationBellIcon.setImageResource(R.drawable.notificications_enabled);
            }
            NotificationPriority notificationPriority = new NotificationPriority(
                    Utils.getIdInSharedPrefs(FixtureDetailsActivity.this),
                    fixture.getFixtureId(),
                    fullTimeResultInt,
                    halfTimeResultInt,
                    kickOffInt,
                    redCardsInt,
                    yellowCardsInt,
                    goalsInt

            );
            if (!Utils.getIdInSharedPrefs(FixtureDetailsActivity.this).equals(Info.NO_ID)) {
                Toast.makeText(FixtureDetailsActivity.this, "Posting user Id", Toast.LENGTH_SHORT).show();
                APIManager.getRetrofit()
                        .create(APIService.class)
                        .postFixtureItem(notificationPriority)
                        .enqueue(new Callback<NotificationPriority>() {
                            @Override
                            public void onResponse(Call<NotificationPriority> call, Response<NotificationPriority> response) {
                                Log.i(TAG, "onResponse: " + response.body());
                                Log.i(TAG, "onResponse: " + response.message());
                                Log.i(TAG, "onResponse: " + response.errorBody());
                                Log.i(TAG, "onResponse: " + response.code());
                                Log.i(TAG, "onResponse: " + response.raw());
                                if (response.isSuccessful()) {
                                    Toast.makeText(FixtureDetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "onResponse: " + response.message());
                                }

                            }

                            @Override
                            public void onFailure(Call<NotificationPriority> call, Throwable t) {
                                Toast.makeText(FixtureDetailsActivity.this, "Error communicating server", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onFailure: " + t.getMessage());
                                Log.i(TAG, "onFailure: " + t.getCause());
                            }
                        });
            }
            Log.i(TAG, "setPopUpWindow: Data Should be written");
        });

        if (arrayList.size() < 1) {
            return;
        }

        boolean isNotificationEnabled = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(1)) {
                isNotificationEnabled = true;
                break;
            }
        }

        if (isNotificationEnabled)
            notificationBellIcon.setImageResource(R.drawable.notificications_enabled);
        else
            notificationBellIcon.setImageResource(R.drawable.notifications_disabled);


        notifications.setChecked(isNotificationEnabled);

        fullTimeResult.setChecked(arrayList.get(1).equals(1));
        if (arrayList.get(1).equals(1)) {
            fullTimeResultInt = 1;
        } else {
            fullTimeResultInt = 0;
        }
        halfTimeResult.setChecked(arrayList.get(2).equals(1));
        if (arrayList.get(2).equals(1)) {
            halfTimeResultInt = 1;
        } else {
            halfTimeResultInt = 0;
        }
        kickOff.setChecked(arrayList.get(3).equals(1));
        if (arrayList.get(3).equals(1)) {
            kickOffInt = 1;
        } else {
            kickOffInt = 0;
        }
        redCards.setChecked(arrayList.get(4).equals(1));
        if (arrayList.get(4).equals(1)) {
            redCardsInt = 1;
        } else {
            redCardsInt = 0;
        }
        yellowCards.setChecked(arrayList.get(5).equals(1));
        if (arrayList.get(5).equals(1)) {
            yellowCardsInt = 1;
        } else {
            yellowCardsInt = 0;
        }
        goals.setChecked(arrayList.get(6).equals(1));
        if (arrayList.get(6).equals(1)) {
            goalsInt = 1;
        } else {
            goalsInt = 0;
        }


    }

    public void initNotifications(View view) {
        mypopupWindow.showAsDropDown(view, -500, 0);

    }

    public void initFavs(View view) {
        initFav();
    }
}
