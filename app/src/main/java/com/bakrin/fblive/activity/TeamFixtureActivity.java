package com.bakrin.fblive.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.FixtureListAdapter;
import com.bakrin.fblive.listener.FixtureItemSelectListener;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.LeagueListResponse;
import com.bakrin.fblive.model.response.LiveFixtureResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;

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

public class TeamFixtureActivity extends BaseActivity {

    @BindView(R.id.teamFixtureListRecyclerView)
    RecyclerView teamFixtureListRecyclerView;

    private String name,logo;
    private int id;
    private ArrayList<FixtureItem> fixtures;
    private FixtureListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getExtras().getString("name");
        logo = getIntent().getExtras().getString("logo");
        id = getIntent().getExtras().getInt("id");
        init();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_team_fixture;
    }

    @OnClick(R.id.backImageView)
    public void onBackClick() {
        finish();
    }

    /**
     * initialize component
     */
    private void init() {
        setActionBar(getResources().getString(R.string.team_fixture), new ActionbarMenu[]{ActionbarMenu.BACK});

        teamFixtureListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJustPay = new LinearLayoutManager(context);
        llmJustPay.setOrientation(LinearLayoutManager.VERTICAL);
        teamFixtureListRecyclerView.setLayoutManager(llmJustPay);
        teamFixtureListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        downloadFixture();
    }

    /**
     * download league details
     */
    private void downloadFixture() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getTeamFixtureList(id).enqueue(
                    new Callback<LiveFixtureResponse>() {
                        @Override
                        public void onResponse(Call<LiveFixtureResponse> call, Response<LiveFixtureResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                fixtures = response.body().api.fixtures;
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
     * setup team list adapter
     */
    private void setupListAdapter() {
        Collections.sort(fixtures, new Comparator<FixtureItem>() {

            public int compare(FixtureItem o2, FixtureItem o1) {

                Date date1 = new Date(o1.event_timestamp);
                Date date2 = new Date(o2.event_timestamp);

                return date1.compareTo(date2);

            }
        });
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        int pos = 0;
        for (int i = 0; i < fixtures.size(); i++) {
            try {
                Date api = fmt.parse(fixtures.get(i).eventDate);
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

        listAdapter = new FixtureListAdapter(false, this, fixtures, new FixtureItemSelectListener() {
            @Override
            public void onFixtureSelect(int pos, FixtureItem fixtureItem, Actions actions) {
                if (actions == Actions.VIEW) {
                    Intent call = new Intent(context, FixtureDetailsActivity.class);
                    call.putExtra("fixture", fixtureItem);
                    startActivity(call);
                }
            }
        });
        teamFixtureListRecyclerView.setAdapter(listAdapter);
        teamFixtureListRecyclerView.scrollToPosition(pos);
    }

}
