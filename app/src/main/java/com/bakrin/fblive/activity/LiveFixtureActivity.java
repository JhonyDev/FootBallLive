package com.bakrin.fblive.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.CountryGridAdapter;
import com.bakrin.fblive.adapter.FixtureListAdapter;
import com.bakrin.fblive.adapter.HomeTeamListAdapter;
import com.bakrin.fblive.db.table.FixtureTable;
import com.bakrin.fblive.db.table.NotificationPriorityTable;
import com.bakrin.fblive.db.table.TeamTable;
import com.bakrin.fblive.listener.CountrySelectListener;
import com.bakrin.fblive.listener.FixtureItemSelectListener;
import com.bakrin.fblive.listener.HomeTeamSelectListener;
import com.bakrin.fblive.model.response.Country;
import com.bakrin.fblive.model.response.CountryResponse;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.LeagueListItem;
import com.bakrin.fblive.model.response.LeagueListResponse;
import com.bakrin.fblive.model.response.LiveFixtureResponse;
import com.bakrin.fblive.model.response.Team;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveFixtureActivity extends BaseActivity {


    public static final String TAG = "///";
    private static boolean handlerFlag = false;
    @BindView(R.id.liveListRecyclerView)
    RecyclerView liveListRecyclerView;
    @BindView(R.id.todayListRecyclerView)
    RecyclerView todayListRecyclerView;
    @BindView(R.id.matchListRecyclerView)
    RecyclerView matchListRecyclerView;
    @BindView(R.id.allImageView)
    ImageView allImageView;
    @BindView(R.id.fixtureImageView)
    ImageView fixtureImageView;
    @BindView(R.id.gameImageView)
    ImageView gameImageView;
    @BindView(R.id.teamImageView)
    ImageView teamImageView;
    @BindView(R.id.allTextView)
    TextView allTextView;
    @BindView(R.id.fixtureTextView)
    TextView fixtureTextView;
    @BindView(R.id.liveTextView)
    TextView liveTextView;
    @BindView(R.id.teamTextView)
    TextView teamTextView;
    @BindView(R.id.gameTextView)
    TextView gameTextView;
    @BindView(R.id.allEmptyTextView)
    TextView allEmptyTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.allLinearLayout)
    LinearLayout allLinearLayout;
    @BindView(R.id.fixtureLinearLayout)
    LinearLayout fixtureLinearLayout;
    @BindView(R.id.liveScoreLinearLayout)
    LinearLayout liveScoreLinearLayout;
    @BindView(R.id.teamLinearLayout)
    LinearLayout teamLinearLayout;
    @BindView(R.id.gameLinearLayout)
    LinearLayout gameLinearLayout;
    @BindView(R.id.allMainLinearLayout)
    LinearLayout allMainLinearLayout;
    @BindView(R.id.liveMainLinearLayout)
    LinearLayout liveMainLinearLayout;
    @BindView(R.id.teamMainLinearLayout)
    LinearLayout teamMainLinearLayout;
    @BindView(R.id.fixtureMainLinearLayout)
    LinearLayout fixtureMainLinearLayout;
    @BindView(R.id.matchMainLinearLayout)
    LinearLayout matchMainLinearLayout;
    @BindView(R.id.fixtureEmptyTextView)
    TextView fixtureEmptyTextView;
    @BindView(R.id.fixtureListRecyclerView)
    RecyclerView fixtureListRecyclerView;
    @BindView(R.id.teamListRecyclerView)
    RecyclerView teamListRecyclerView;
    @BindView(R.id.liveEmptyTextView)
    TextView liveEmptyTextView;
    @BindView(R.id.teamEmptyTextView)
    TextView teamEmptyTextView;
    @BindView(R.id.matchEmptyTextView)
    TextView matchEmptyTextView;
    private ArrayList<FixtureItem> fixtureLiveItems;
    private ArrayList<FixtureItem> myMatchFixtureItems;
    private ArrayList<FixtureItem> fixtureByDateItems;
    private FixtureListAdapter listAdapter, myMatchAdapter, fixtureByDateListAdapter;
    private int selectedPos = 0;
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat fmtShow = new SimpleDateFormat("EEEE, dd MMM yyyy");
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Date selectedDate;
    private ArrayList<Country> countryItems;
    private ArrayList<Team> savedTeam;
    private CountryGridAdapter gridAdapter;
    private HomeTeamListAdapter teamListAdapter;
    private TeamTable teamTable;
    private FixtureTable fixtureTable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        init();
        Log.i(TAG, "onCreate: LiveFixtureActivity");
        //throw new RuntimeException("Test Crash");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_live_fixture;
    }


    @OnClick(R.id.searchImageView)
    public void onSearchClick() {
        Intent call = new Intent(context, SearchActivity.class);
        startActivity(call);
    }

    /**
     * initialize component
     */
    private void init() {


        setActionBar(getResources().getString(R.string.app_name), new ActionbarMenu[]{ActionbarMenu.SEARCH, ActionbarMenu.DRAWER});

        selectedDate = new Date();
        fixtureByDateItems = new ArrayList<>();
        savedTeam = new ArrayList<>();

        teamTable = new TeamTable(context);
        fixtureTable = new FixtureTable(context);


        liveListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJustPay = new LinearLayoutManager(context);
        llmJustPay.setOrientation(LinearLayoutManager.VERTICAL);
        liveListRecyclerView.setLayoutManager(llmJustPay);
        liveListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        matchListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmMatch = new LinearLayoutManager(context);
        llmMatch.setOrientation(LinearLayoutManager.VERTICAL);
        matchListRecyclerView.setLayoutManager(llmMatch);
        matchListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fixtureListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmByDate = new LinearLayoutManager(context);
        llmByDate.setOrientation(LinearLayoutManager.VERTICAL);
        fixtureListRecyclerView.setLayoutManager(llmByDate);
        fixtureListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        todayListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmToday = new LinearLayoutManager(context);
        llmToday.setOrientation(LinearLayoutManager.VERTICAL);
        todayListRecyclerView.setLayoutManager(llmToday);
        todayListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        teamListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmTeam = new LinearLayoutManager(context);
        llmTeam.setOrientation(LinearLayoutManager.VERTICAL);
        teamListRecyclerView.setLayoutManager(llmTeam);
        teamListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        setupDate(false);


        setupTabs(1);


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Utils.log("THREAD CALL","<--------------->");
//            }
//        }, 5000);

        handler = new Handler();

        // callTimer();


    }

    private void callTimer() {
        final Runnable r = new Runnable() {
            public void run() {
                Utils.log("THREAD CALL", "<--------------->");
                Utils.log("THREAD CALL", "Tab : " + selectedPos);
                if (selectedPos == 1) {
                    Date today = new Date();
                    String date1 = fmt.format(today);
                    String date2 = fmt.format(selectedDate);
                    if (date1.equalsIgnoreCase(date2)) {
                        setupDate(true);
                    }

                } else if (selectedPos == 2) {
                    if (fixtureLiveItems != null) {
                        loadLiveFixture(true);
                    }

                } else if (selectedPos == 4) {
                    updateSavedGame();
                }

                handler.postDelayed(this, 15000);
            }
        };

        handler.postDelayed(r, 15000);
    }

    private void updateSavedGame() {

        try {
            ArrayList<FixtureItem> savedList = fixtureTable.getSavedFixtureList();

            Date today = new Date();
            String checkToday = fmt.format(today);

            for (int i = 0; i < savedList.size(); i++) {
                FixtureItem savedItem = savedList.get(i);
//                Utils.log("SAVED EVENT DATE", ": " + savedItem.eventDate);
                Date date1 = fmt.parse(savedItem.eventDate);

                String checkDate = fmt.format(date1);


                Utils.log("SAVED checkDate", ": " + checkDate);
                Utils.log("SAVED checkToday", ": " + checkToday);


                if (checkDate.equalsIgnoreCase(checkToday)) {
                    Utils.log("DATE", "TODAY");

                    if (!(savedItem.statusShort.equalsIgnoreCase("CANC") ||
                            savedItem.statusShort.equalsIgnoreCase("PST") ||
                            savedItem.statusShort.equalsIgnoreCase("SUSP") ||
                            savedItem.statusShort.equalsIgnoreCase("INT") ||
                            savedItem.statusShort.equalsIgnoreCase("ABD") ||
                            savedItem.statusShort.equalsIgnoreCase("AWD") ||
                            savedItem.statusShort.equalsIgnoreCase("WO"))) {
                        refreshMatch(savedItem.fixtureId, i, true);
                    } else {
                        Utils.log("SAVED Status", ": " + savedItem.statusShort);
                        //
                    }


                } else {
                    Utils.log("DATE", "NOT TODAY");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handlerFlag = false;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!handlerFlag) {
            callTimer();

        }
    }

    @OnClick(R.id.datePickerLinearLayout)
    public void onDateClick() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        try {
                            fixtureByDateItems.clear();
                            fixtureByDateListAdapter.notifyDataSetChanged();
                            selectedDate = fmt.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            setupDate(false);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setupDate(boolean isRefresh) {
        dateTextView.setText(fmtShow.format(selectedDate));
        if (isRefresh) {
            if (selectedPos == 1) {
                loadDateFixture(fmt.format(selectedDate), isRefresh);
            }
        } else {
            if (fixtureByDateItems.size() == 0) {
                if (selectedPos == 1) {
                    loadDateFixture(fmt.format(selectedDate), isRefresh);
                }
            }
        }
    }

    @OnClick(R.id.allLinearLayout)
    public void onAllTabClick() {
        setupTabs(0);
    }

    @OnClick(R.id.fixtureLinearLayout)
    public void onFixturesTabClick() {
        setupTabs(1);
    }

    @OnClick(R.id.liveScoreLinearLayout)
    public void onLiveTabClick() {
        setupTabs(2);
    }

    @OnClick(R.id.teamLinearLayout)
    public void onTeamTabClick() {
        setupTabs(3);
    }

    @OnClick(R.id.gameLinearLayout)
    public void onGameTabClick() {
        setupTabs(4);
    }

    private void setupTabs(int pos) {
        this.selectedPos = pos;
        allImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_ball_gray_light));
        fixtureImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_24_light_gray));
        teamImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_gray_light));
        gameImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_game_gray_light));

        allTextView.setTextColor(getResources().getColor(R.color.text_light_gray));
        fixtureTextView.setTextColor(getResources().getColor(R.color.text_light_gray));
        liveTextView.setTextColor(getResources().getColor(R.color.text_light_gray));
        teamTextView.setTextColor(getResources().getColor(R.color.text_light_gray));
        gameTextView.setTextColor(getResources().getColor(R.color.text_light_gray));

        allLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        fixtureLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        liveScoreLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        teamLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));
        gameLinearLayout.setBackgroundColor(getResources().getColor(R.color.white));

        allMainLinearLayout.setVisibility(View.GONE);
        fixtureMainLinearLayout.setVisibility(View.GONE);
        liveMainLinearLayout.setVisibility(View.GONE);
        teamMainLinearLayout.setVisibility(View.GONE);
        matchMainLinearLayout.setVisibility(View.GONE);

        Utils.log("Selcted pos", " : " + pos);

        if (pos == 0) {
            allImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_ball_gray));
            allTextView.setTextColor(getResources().getColor(R.color.text_gray));
            allLinearLayout.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
            allMainLinearLayout.setVisibility(View.VISIBLE);
            Date today = new Date();
            if (countryItems == null) {
                //loadDateFixture(fmt.format(today));
                loadCountries();
            }


        }
        if (pos == 1) {
            fixtureImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_24_gray));
            fixtureTextView.setTextColor(getResources().getColor(R.color.text_gray));
            fixtureLinearLayout.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));

            fixtureMainLinearLayout.setVisibility(View.VISIBLE);

            setupDate(false);

        }
        if (pos == 2) {
            liveTextView.setTextColor(getResources().getColor(R.color.text_gray));
            liveScoreLinearLayout.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));

            liveMainLinearLayout.setVisibility(View.VISIBLE);
            if (fixtureLiveItems == null) {
                loadLiveFixture(false);
            }

        }
        if (pos == 3) {
            teamImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_gray));
            teamTextView.setTextColor(getResources().getColor(R.color.text_gray));
            teamLinearLayout.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
            teamMainLinearLayout.setVisibility(View.VISIBLE);
            savedTeam = teamTable.getSavedTeams();
            setupTeamListAdapter();

        }
        if (pos == 4) {
            gameImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_game_gray));
            gameTextView.setTextColor(getResources().getColor(R.color.text_gray));
            gameLinearLayout.setBackgroundColor(getResources().getColor(R.color.tab_selected_bg));
            matchMainLinearLayout.setVisibility(View.VISIBLE);
            myMatchFixtureItems = fixtureTable.getSavedFixtureList();
            setupMyMatchListAdapter();
        }


    }

    private void setupMyMatchListAdapter() {
        if (myMatchFixtureItems == null) {
            matchListRecyclerView.setVisibility(View.GONE);
            matchEmptyTextView.setVisibility(View.VISIBLE);
        } else if (myMatchFixtureItems.size() == 0) {
            matchListRecyclerView.setVisibility(View.GONE);
            matchEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            matchListRecyclerView.setVisibility(View.VISIBLE);
            matchEmptyTextView.setVisibility(View.GONE);
        }
        myMatchAdapter = new FixtureListAdapter(true, this, true, myMatchFixtureItems, new FixtureItemSelectListener() {
            @Override
            public void onFixtureSelect(int pos, FixtureItem fixtureItem, Actions actions) {
                if (actions == Actions.VIEW) {
                    Intent call = new Intent(context, FixtureDetailsActivity.class);
                    call.putExtra("fixture", fixtureItem);
                    startActivity(call);
                }
                if (actions == Actions.REFRESH) {
                    refreshMatch(fixtureItem.fixtureId, pos, false);
                }
                if (actions == Actions.LIST_REFRESH) {
                    myMatchFixtureItems.remove(fixtureItem);
                    myMatchAdapter.notifyDataSetChanged();
                }
                if (actions == Actions.VIEW_LEAGUE) {
                    Intent call = new Intent(context, LeagueDetailsActivity.class);
                    call.putExtra("name", fixtureItem.league.name);
                    call.putExtra("id", fixtureItem.leagueId);
                    call.putExtra("logo", fixtureItem.league.logo);
                    startActivity(call);
                }
            }
        });
        matchListRecyclerView.setAdapter(myMatchAdapter);

    }

    private void refreshMatch(int fixtureId, int pos, boolean allUpdate) {
        if (InternetConnection.isConnectingToInternet(this)) {

            if (!allUpdate) {
                showProgressBar(context, getResources().getString(R.string.loading));
            }
            apiManager.getAPIService().getFixtureById(fixtureId).enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            hideProgressBar(context);

                            Log.i(TAG, "onResponse: id: " + fixtureId);


                            if (response.code() == 200) {
                                myMatchFixtureItems.remove(pos);
                                myMatchFixtureItems.add(pos, response.body().api.fixtures.get(0));
                                fixtureTable.updateFixture(response.body().api.fixtures.get(0));
                                myMatchAdapter.notifyDataSetChanged();


                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<LiveFixtureResponse> call, Throwable t) {
                            hideProgressBar(context);
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

    private void setupTeamListAdapter() {
        if (savedTeam == null) {
            teamListRecyclerView.setVisibility(View.GONE);
            teamEmptyTextView.setVisibility(View.VISIBLE);
        } else if (savedTeam.size() == 0) {
            teamListRecyclerView.setVisibility(View.GONE);
            teamEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            teamListRecyclerView.setVisibility(View.VISIBLE);
            teamEmptyTextView.setVisibility(View.GONE);
        }
        teamListAdapter = new HomeTeamListAdapter(this, savedTeam, new HomeTeamSelectListener() {
            @Override
            public void onTeamSelect(int pos, Team team, Actions actions) {

                if (actions == Actions.DELETE) {
                    teamTable.deleteTeam(team.teamId);
                    savedTeam.remove(pos);
                    teamListAdapter.notifyDataSetChanged();

                } else if (actions == Actions.VIEW) {
                    Intent call = new Intent(context, TeamFixtureActivity.class);
                    call.putExtra("id", team.teamId);
                    call.putExtra("logo", team.logo);
                    call.putExtra("name", team.teamName);
                    startActivity(call);
                }
            }
        });
        teamListRecyclerView.setAdapter(teamListAdapter);

    }

    /**
     * load country from API
     */
    private void loadCountries() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getCountiesList().enqueue(
                    new Callback<CountryResponse>() {
                        @Override
                        public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                countryItems = response.body().getApi().getCountries();
                                setupCountryAdapter();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<CountryResponse> call, Throwable t) {
                            hideProgressBar(context);
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }

    }

    /**
     * load county to grid view
     */
    private void setupCountryAdapter() {
        if (countryItems == null) {
            todayListRecyclerView.setVisibility(View.GONE);
            allEmptyTextView.setVisibility(View.VISIBLE);
        } else if (countryItems.size() == 0) {
            todayListRecyclerView.setVisibility(View.GONE);
            allEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            todayListRecyclerView.setVisibility(View.VISIBLE);
            allEmptyTextView.setVisibility(View.GONE);
        }
        gridAdapter = new CountryGridAdapter(this, countryItems, new CountrySelectListener() {
            @Override
            public void onCountrySelect(Country country, int pos, Actions actions) {

                if (actions == Actions.VIEW) {
                    if (country.getLeagueListItems() != null) {
                        gridAdapter.setSelectedPos(pos);
                        gridAdapter.notifyDataSetChanged();
                    } else {
                        downloadLeagues(country.getCode(), pos);
                    }
                }
//                if (actions == Actions.CHANGE) {
//                    gridAdapter.setSelectedPos(-1);
//                    gridAdapter.notifyDataSetChanged();
//                }

            }
        });
        todayListRecyclerView.setAdapter(gridAdapter);
    }


    /**
     * download league details
     */
    private void downloadLeagues(String code, int pos) {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getLeagueByCountryList(code).enqueue(
                    new Callback<LeagueListResponse>() {
                        @Override
                        public void onResponse(Call<LeagueListResponse> call, Response<LeagueListResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                ArrayList<LeagueListItem> showItems = new ArrayList<>();
                                ArrayList<LeagueListItem> listItems = response.body().getApi().getLeagues();
                                for (int i = 0; i < listItems.size(); i++) {
                                    if (listItems.get(i).getIsCurrent() == 1) {
                                        showItems.add(listItems.get(i));
                                    }

                                }
                                countryItems.get(pos).setLeagueListItems(showItems);
                                gridAdapter.setSelectedPos(pos);
                                gridAdapter.notifyDataSetChanged();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<LeagueListResponse> call, Throwable t) {
                            hideProgressBar(context);
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

    /**
     * load date fixture
     */
    private void loadDateFixture(String date, boolean isRefresh) {
        if (InternetConnection.isConnectingToInternet(this)) {

            if (!isRefresh) {
                showProgressBar(context, getResources().getString(R.string.loading));
            }

            apiManager.getAPIService().getByDateFixtureList(date).enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            hideProgressBar(context);

                            Log.i(TAG, "onResponse: body  " + response.body());
                            Log.i(TAG, "onResponse: date " + date);

                            if (response.body() == null){
                                loadDateFixture(date, false);
                                return;
                            }

                            if (response.body().api.fixtures == null) {
                                Toast.makeText(application, "Error Fetching data from server", Toast.LENGTH_SHORT).show();


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(application, "Retrying", Toast.LENGTH_SHORT).show();
                                        loadDateFixture(date, false);
                                    }
                                }, 2 * 1000);

                                return;
                            }


                            if (response.code() == 200) {
                                Log.i(TAG, "onResponse: selected position" + selectedPos);
                                if (selectedPos == 0) {
                                } else if (selectedPos == 1) {
                                    if (isRefresh) {
                                        fixtureByDateItems.clear();
                                        fixtureByDateItems.addAll(response.body().api.fixtures);
                                        Log.i(TAG, "onResponse: Should be added to the list");
                                    } else {
                                        fixtureByDateItems = response.body().api.fixtures;
                                        Log.i(TAG, "onResponse: list equals: " + response.body().api.fixtures);
                                    }

                                    setUpFixtureByDateListAdapter(isRefresh);
                                }

                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<LiveFixtureResponse> call, Throwable t) {
                            hideProgressBar(context);
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

    /**
     * by date fixture data adapter
     */
    private void setUpFixtureByDateListAdapter(boolean isRefresh) {
        if (fixtureByDateItems == null) {
            fixtureListRecyclerView.setVisibility(View.GONE);
            fixtureEmptyTextView.setVisibility(View.VISIBLE);
        } else if (fixtureByDateItems.size() == 0) {
            fixtureListRecyclerView.setVisibility(View.GONE);
            fixtureEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            fixtureListRecyclerView.setVisibility(View.VISIBLE);
            fixtureEmptyTextView.setVisibility(View.GONE);
        }

        if (fixtureByDateItems != null) {
            Utils.log("fixtureByDateItems", ": " + fixtureByDateItems.size());
        }

        if (isRefresh) {
            if (fixtureByDateListAdapter != null) {
                fixtureByDateListAdapter.notifyDataSetChanged();
            }
        } else {

            fixtureByDateListAdapter = new FixtureListAdapter(true, this, fixtureByDateItems, new FixtureItemSelectListener() {
                @Override
                public void onFixtureSelect(int pos, FixtureItem fixtureItem, Actions actions) {
                    if (actions == Actions.VIEW) {
                        Intent call = new Intent(context, FixtureDetailsActivity.class);
                        call.putExtra("fixture", fixtureItem);
                        startActivity(call);
                    }
                    if (actions == Actions.VIEW_LEAGUE) {
                        Intent call = new Intent(context, LeagueDetailsActivity.class);
                        call.putExtra("name", fixtureItem.league.name);
                        call.putExtra("id", fixtureItem.leagueId);
                        call.putExtra("logo", fixtureItem.league.logo);
                        startActivity(call);
                    }

                }
            });





            fixtureListRecyclerView.setAdapter(fixtureByDateListAdapter);
        }

    }

    /**
     * load live fixture
     */
    private void loadLiveFixture(boolean isRefresh) {
        if (InternetConnection.isConnectingToInternet(this)) {

            if (!isRefresh) {
                showProgressBar(context, getResources().getString(R.string.loading));
            }

            apiManager.getAPIService().getLiveFixtureList().enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {

                                if (isRefresh) {
                                    fixtureLiveItems.clear();
                                    fixtureLiveItems.addAll(response.body().api.fixtures);
                                    listAdapter.notifyDataSetChanged();
                                } else {
                                    fixtureLiveItems = response.body().api.fixtures;
                                    setupListAdapter();
                                }

                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<LiveFixtureResponse> call, Throwable t) {
                            hideProgressBar(context);
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );


        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, null);
        }
    }

//    /**
//     * setup date view
//     */
//    private void setupTodayListAdapter() {
//        if (fixtureTodayItems == null) {
//            todayListRecyclerView.setVisibility(View.GONE);
//            allEmptyTextView.setVisibility(View.VISIBLE);
//        } else if (fixtureTodayItems.size() == 0) {
//            todayListRecyclerView.setVisibility(View.GONE);
//            allEmptyTextView.setVisibility(View.VISIBLE);
//        } else {
//            todayListRecyclerView.setVisibility(View.VISIBLE);
//            allEmptyTextView.setVisibility(View.GONE);
//        }
//
//        todayListAdapter = new FixtureListAdapter(this, fixtureTodayItems, new FixtureItemSelectListener() {
//            @Override
//            public void onFixtureSelect(int pos, FixtureItem fixtureItem, Actions actions) {
//                if (actions == Actions.VIEW) {
//                    Intent call = new Intent(context, FixtureDetailsActivity.class);
//                    call.putExtra("fixture", fixtureItem);
//                    startActivity(call);
//                }
//                if (actions == Actions.VIEW_LEAGUE) {
//                    Intent call = new Intent(context, LeagueDetailsActivity.class);
//                    call.putExtra("name", fixtureItem.league.name);
//                    call.putExtra("id", fixtureItem.leagueId);
//                    call.putExtra("logo", fixtureItem.league.logo);
//                    startActivity(call);
//                }
//
//            }
//        });
//        todayListRecyclerView.setAdapter(todayListAdapter);
//    }

    /**
     * live list adapter
     */
    private void setupListAdapter() {

        if (fixtureLiveItems == null) {
            liveEmptyTextView.setVisibility(View.VISIBLE);
            liveListRecyclerView.setVisibility(View.GONE);
        } else if (fixtureLiveItems.size() == 0) {
            liveEmptyTextView.setVisibility(View.VISIBLE);
            liveListRecyclerView.setVisibility(View.GONE);
        } else {
            liveEmptyTextView.setVisibility(View.GONE);
            liveListRecyclerView.setVisibility(View.VISIBLE);
        }
        listAdapter = new FixtureListAdapter(true, this, fixtureLiveItems, (pos, fixtureItem, actions) -> {
            if (actions == Actions.VIEW) {
                Intent call = new Intent(context, FixtureDetailsActivity.class);
                call.putExtra("fixture", fixtureItem);
                startActivity(call);
            }
            if (actions == Actions.VIEW_LEAGUE) {
                Intent call = new Intent(context, LeagueDetailsActivity.class);
                call.putExtra("name", fixtureItem.league.name);
                call.putExtra("id", fixtureItem.leagueId);
                call.putExtra("logo", fixtureItem.league.logo);
                startActivity(call);
            }

        });
        liveListRecyclerView.setAdapter(listAdapter);
    }
}
