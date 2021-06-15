package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
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
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.FixtureListAdapter;
import com.bakrin.fblive.adapter.PointListAdapter;
import com.bakrin.fblive.adapter.TopScorerListAdapter;
import com.bakrin.fblive.listener.FixtureItemSelectListener;
import com.bakrin.fblive.listener.PointTableItemSelectListener;
import com.bakrin.fblive.listener.TopScorerSelectListener;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.LeagueTableResponse;
import com.bakrin.fblive.model.response.LiveFixtureResponse;
import com.bakrin.fblive.model.response.PointTableItem;
import com.bakrin.fblive.model.response.TopScorerItem;
import com.bakrin.fblive.model.response.TopScorerResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeagueDetailsActivity extends BaseActivity {

    @BindView(R.id.leagueImageView)
    ImageView leagueImageView;
    @BindView(R.id.tableRecyclerView)
    RecyclerView tableRecyclerView;
    @BindView(R.id.tableTextView)
    TextView tableTextView;
    @BindView(R.id.fixtureTextView)
    TextView fixtureTextView;
    @BindView(R.id.tableView)
    View tableView;
    @BindView(R.id.fixtureView)
    View fixtureView;
    @BindView(R.id.tableMainLinearLayout)
    LinearLayout tableMainLinearLayout;
    @BindView(R.id.fixtureMainLinearLayout)
    LinearLayout fixtureMainLinearLayout;
    @BindView(R.id.fixtureRecyclerView)
    RecyclerView fixtureRecyclerView;
    @BindView(R.id.topRecyclerView)
    RecyclerView topRecyclerView;
    @BindView(R.id.topTextView)
    TextView topTextView;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.topView)
    View topView;
    @BindView(R.id.topMainLinearLayout)
    LinearLayout topMainLinearLayout;
    private ArrayList<FixtureItem> fixtureItems;
    private FixtureListAdapter listFixtureAdapter;
    private String name, logo;
    private int leagueId;
    private ArrayList<PointTableItem> pointTableItems;
    private PointListAdapter listAdapter;
    private ArrayList<TopScorerItem> topScorerItems;
    private TopScorerListAdapter topScorerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getExtras().getString("name");
        logo = getIntent().getExtras().getString("logo");
        leagueId = getIntent().getExtras().getInt("id");

        init();
        selectTab(0);



    }

    @OnClick(R.id.backImageView)
    public void onBackClick() {
        finish();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_league_details;
    }

    /**
     * initialize component
     */

    private void init() {
        setActionBar(name, new ActionbarMenu[]{ActionbarMenu.BACK});

        tableRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJustPay = new LinearLayoutManager(context);
        llmJustPay.setOrientation(LinearLayoutManager.VERTICAL);
        tableRecyclerView.setLayoutManager(llmJustPay);
        tableRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fixtureRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmFixture = new LinearLayoutManager(context);
        llmFixture.setOrientation(LinearLayoutManager.VERTICAL);
        fixtureRecyclerView.setLayoutManager(llmFixture);
        fixtureRecyclerView.setItemAnimator(new DefaultItemAnimator());

        topRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmTopScorer = new LinearLayoutManager(context);
        llmTopScorer.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(llmTopScorer);
        topRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (logo != null) {
            Picasso.get()
                    .load(logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(leagueImageView);
        }
    }

    @OnClick(R.id.tableLinearLayout)
    public void onTableClick() {
        selectTab(0);
    }

    @OnClick(R.id.fixtureLinearLayout)
    public void onFixtureClick() {
        selectTab(1);
    }

    @OnClick(R.id.topScoreLinearLayout)
    public void onTopScoreClick() {
        selectTab(2);
    }

    /**
     * set up tab view
     */
    private void selectTab(int tab) {
        tableMainLinearLayout.setVisibility(View.GONE);
        fixtureMainLinearLayout.setVisibility(View.GONE);
        topMainLinearLayout.setVisibility(View.GONE);

        tableView.setVisibility(View.INVISIBLE);
        tableTextView.setTextColor(getResources().getColor(R.color.text_light_gray));

//        tableMainLinearLayout.setVisibility(View.GONE);
//        fixtureMainLinearLayout.setVisibility(View.GONE);
        fixtureView.setVisibility(View.INVISIBLE);
        fixtureTextView.setTextColor(getResources().getColor(R.color.text_light_gray));


//        tableMainLinearLayout.setVisibility(View.GONE);
//        fixtureMainLinearLayout.setVisibility(View.GONE);
        topView.setVisibility(View.INVISIBLE);
        topTextView.setTextColor(getResources().getColor(R.color.text_light_gray));


        tableTextView.setTextColor(getResources().getColor(R.color.text_light_gray));
        if (tab == 0) {

            tableMainLinearLayout.setVisibility(View.VISIBLE);
            tableView.setVisibility(View.VISIBLE);
            tableTextView.setTextColor(getResources().getColor(R.color.text_green));
            if (pointTableItems == null) {
                downloadPoints();
            }
        } else if (tab == 1) {
            fixtureMainLinearLayout.setVisibility(View.VISIBLE);
            fixtureView.setVisibility(View.VISIBLE);
            fixtureTextView.setTextColor(getResources().getColor(R.color.text_green));
            if (fixtureItems == null) {
                downloadFixture();
            }


        } else if (tab == 2) {
            topMainLinearLayout.setVisibility(View.VISIBLE);
            topView.setVisibility(View.VISIBLE);
            topTextView.setTextColor(getResources().getColor(R.color.text_green));
            if (topScorerItems == null) {
                downloadTopScore();
            }

        }

    }

    private void downloadTopScore() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getTopScorers(leagueId).enqueue(
                    new Callback<TopScorerResponse>() {
                        @Override
                        public void onResponse(Call<TopScorerResponse> call, Response<TopScorerResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                topScorerItems = response.body().getApi().getTopscorers();
                                setupTopScorerListAdapter();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<TopScorerResponse> call, Throwable t) {
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

    /**
     * setup list adapter to top score
     */
    private void setupTopScorerListAdapter() {

        topScorerListAdapter = new TopScorerListAdapter(this, topScorerItems, new TopScorerSelectListener() {
            @Override
            public void onTopScorerSelect(int pos, TopScorerItem topScorerItem) {

            }
        });
        topRecyclerView.setAdapter(topScorerListAdapter);
    }

    /**
     * download fixture from API
     */
    private void downloadFixture() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getLeagueFixture(leagueId).enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                fixtureItems = response.body().api.fixtures;
                                setupFixtureListAdapter();
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

    /**
     * setup fixture list adapter
     */
    private void setupFixtureListAdapter() {

        Collections.sort(fixtureItems, new Comparator<FixtureItem>() {

            public int compare(FixtureItem o2, FixtureItem o1) {

                Date date1 = new Date(o1.event_timestamp);
                Date date2 = new Date(o2.event_timestamp);

                return date1.compareTo(date2);

            }
        });
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        int pos = 0;
        for (int i = 0; i < fixtureItems.size(); i++) {
            try {
                Date api = fmt.parse(fixtureItems.get(i).eventDate);
                Date today = new Date();
                if (DateUtils.isToday(api.getTime())) {
                    pos = i;
                    break;
                } else {
                    if (today.after(api)) {
                        if (i != 0) {
                            pos = i - 1;
                        } else {
                            pos = i;
                        }

                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        listFixtureAdapter = new FixtureListAdapter(false, this, fixtureItems, new FixtureItemSelectListener() {
            @Override
            public void onFixtureSelect(int pos, FixtureItem fixtureItem, Actions actions) {

                Intent call = new Intent(context, FixtureDetailsActivity.class);
                call.putExtra("fixture", fixtureItem);
                startActivity(call);


            }
        });
        fixtureRecyclerView.setAdapter(listFixtureAdapter);
        fixtureRecyclerView.scrollToPosition(pos);
    }

    /**
     * download league table
     */
    private void downloadPoints() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getLeagueTable(leagueId).enqueue(
                    new Callback<LeagueTableResponse>() {
                        @Override
                        public void onResponse(Call<LeagueTableResponse> call, Response<LeagueTableResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                if (response.body().getApi().getStandings().size() > 0) {
                                    pointTableItems = response.body().getApi().getStandings().get(0);
                                } else {
                                    pointTableItems = new ArrayList<>();
                                }

                                setupListAdapter();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<LeagueTableResponse> call, Throwable t) {
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

    /**
     * setup list adapter
     */
    private void setupListAdapter() {
        listAdapter = new PointListAdapter(this, pointTableItems, new PointTableItemSelectListener() {
            @Override
            public void onPointTableItemSelect(int pos, PointTableItem pointTableItem) {

            }
        });
        tableRecyclerView.setAdapter(listAdapter);


    }


}
