package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bakrin.fblive.db.table.TeamTable;
import com.bakrin.fblive.model.Pojo.FixtureItem;
import com.bakrin.fblive.model.Pojo.FixtureStatEvent;
import com.bakrin.fblive.model.Pojo.FixtureStats;
import com.bakrin.fblive.model.Pojo.H2HResponse;
import com.bakrin.fblive.model.Pojo.StatsResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    @BindView(R.id.matchStatsLinearLayout)
    LinearLayout matchStatsLinearLayout;
    @BindView(R.id.h2hListRecyclerView)
    RecyclerView h2hListRecyclerView;
    @BindView(R.id.homeSaveImageView)
    ImageView homeSaveImageView;
    @BindView(R.id.awaySaveImageView)
    ImageView awaySaveImageView;
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
        Log.i(TAG, "onCreate: FixtureDetailsActivity" );


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
}
