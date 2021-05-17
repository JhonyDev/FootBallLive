package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.LeagueHomeListAdapter;
import com.bakrin.fblive.listener.LeagueHomeItemSelectListener;
import com.bakrin.fblive.model.response.LeagueListItem;
import com.bakrin.fblive.model.response.LeagueListResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeagueHomeActivity extends BaseActivity {

    @BindView(R.id.countryTextView)
    TextView countryTextView;
    @BindView(R.id.countyImageView)
    ImageView countyImageView;
    @BindView(R.id.leagueListRecyclerView)
    RecyclerView leagueListRecyclerView;

    private String country, code, flag;
    private ArrayList<LeagueListItem> leagueListItems;
    private LeagueHomeListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country = getIntent().getExtras().getString("country");
        code = getIntent().getExtras().getString("code");
        flag = getIntent().getExtras().getString("flag");
        init();
        downloadLeagues();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_league_home;
    }


    @OnClick(R.id.backImageView)
    public void onBackClick() {
        finish();
    }

    /**
     * initialize component
     */
    private void init() {
        setActionBar(getResources().getString(R.string.leagues), new ActionbarMenu[]{ActionbarMenu.BACK});
        countryTextView.setText(country);

        leagueListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmJustPay = new LinearLayoutManager(context);
        llmJustPay.setOrientation(LinearLayoutManager.VERTICAL);
        leagueListRecyclerView.setLayoutManager(llmJustPay);
        leagueListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (flag != null) {
            SvgLoader.pluck()
                    .with(this)
                    .setPlaceHolder(R.drawable.img_place_holder, R.drawable.img_place_holder)
                    .load(flag, countyImageView);
        }

    }


    /**
     * download league details
     */
    private void downloadLeagues() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getLeagueByCountryList(code).enqueue(
                    new Callback<LeagueListResponse>() {
                        @Override
                        public void onResponse(Call<LeagueListResponse> call, Response<LeagueListResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                leagueListItems = response.body().getApi().getLeagues();
                                processData();
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
     * get only live leagues
     */
    private void processData() {
        ArrayList<LeagueListItem> listItems = new ArrayList<>();
        for (int i = 0; i < leagueListItems.size(); i++) {
            if (leagueListItems.get(i).getIsCurrent() == 1) {
//                leagueListItems.remove(i);
                listItems.add(leagueListItems.get(i));
            }

        }

        setupListAdapter(listItems);
    }

    /**
     * setup list adapter
     */
    private void setupListAdapter(ArrayList<LeagueListItem> listItems) {
        listAdapter = new LeagueHomeListAdapter(this, listItems, new LeagueHomeItemSelectListener() {
            @Override
            public void onHomeLeagueItemSelect(int pos, LeagueListItem leagueListItem) {
                Intent call = new Intent(context, LeagueDetailsActivity.class);
                call.putExtra("name", leagueListItem.getName());
                call.putExtra("id", leagueListItem.getLeagueId());
                call.putExtra("logo", leagueListItem.getLogo());
                startActivity(call);

            }
        });
        leagueListRecyclerView.setAdapter(listAdapter);
    }
}
