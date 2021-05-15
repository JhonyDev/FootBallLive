package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.action.DialogAction;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.LeagueHomeListAdapter;
import com.bakrin.fblive.adapter.SearchTeamListAdapter;
import com.bakrin.fblive.listener.DialogActionListener;
import com.bakrin.fblive.listener.HomeTeamSelectListener;
import com.bakrin.fblive.listener.LeagueHomeItemSelectListener;
import com.bakrin.fblive.model.Pojo.LeagueListItem;
import com.bakrin.fblive.model.Pojo.LeagueListResponse;
import com.bakrin.fblive.model.Pojo.Team;
import com.bakrin.fblive.model.Pojo.TeamSearchResponse;
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

public class SearchActivity extends BaseActivity {

    @BindView(R.id.countryListRecyclerView)
    RecyclerView countryListRecyclerView;
    @BindView(R.id.leagueListRecyclerView)
    RecyclerView leagueListRecyclerView;
    @BindView(R.id.teamTextView)
    TextView teamTextView;
    @BindView(R.id.leagueTextView)
    TextView leagueTextView;
    @BindView(R.id.searchEditText)
    EditText searchEditText;

    private ArrayList<Team> teamItems;
    private SearchTeamListAdapter teamListAdapter;
    private ArrayList<LeagueListItem> leagueListItems;
    private LeagueHomeListAdapter listAdapter;
    private int selectedTab = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //loadCountries();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

    @OnClick(R.id.backImageView)
    public void onBackClick() {
        finish();
    }

    private void init() {
        setActionBar(getResources().getString(R.string.search), new ActionbarMenu[]{ActionbarMenu.BACK});
        teamItems = new ArrayList<>();
        leagueListItems = new ArrayList<>();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Utils.log("selectedTab", "  : " + selectedTab);
                if (selectedTab == 0) {
//                    if (gridAdapter != null) {
//                        gridAdapter.setSelectedPos(-1);
//                        gridAdapter.getFilter().filter(editable.toString());
//                    }
                }
                if (selectedTab == 1) {
                    if (listAdapter != null) {
                        listAdapter.getFilter().filter(editable.toString());
                    }

                }
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (selectedTab == 0) {
                        String text = searchEditText.getText().toString();
                        if (text.trim().length() > 2) {
                            searchTeam(text);
                        } else {
                            Toast.makeText(context, "Please enter more than 3 character to search", Toast.LENGTH_LONG).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        countryListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmCountry = new LinearLayoutManager(context);
        llmCountry.setOrientation(LinearLayoutManager.VERTICAL);
        countryListRecyclerView.setLayoutManager(llmCountry);
        countryListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        leagueListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llmLeague = new LinearLayoutManager(context);
        llmLeague.setOrientation(LinearLayoutManager.VERTICAL);
        leagueListRecyclerView.setLayoutManager(llmLeague);
        leagueListRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * search team
     */
    private void searchTeam(String text) {
        if (InternetConnection.isConnectingToInternet(this)) {
            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getSearchTeamList(text).enqueue(
                    new Callback<TeamSearchResponse>() {
                        @Override
                        public void onResponse(Call<TeamSearchResponse> call, Response<TeamSearchResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                teamItems.clear();
                                teamItems.addAll(response.body().getApi().getTeams());
                                setTeamListAdapter();
                            } else {
                                try {
                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<TeamSearchResponse> call, Throwable t) {
                            hideProgressBar(context);
                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );
        } else {
            CustomDialog.showGeneralDialog(this, getString(R.string.network),
                    getString(R.string.no_internet), DialogType.INFO, new DialogActionListener() {
                        @Override
                        public void onDialogAction(DialogAction action) {
                            finish();
                        }
                    });
        }

    }

    private void setTeamListAdapter() {
        teamListAdapter = new SearchTeamListAdapter(this, teamItems, new HomeTeamSelectListener() {
            @Override
            public void onTeamSelect(int pos, Team team, Actions actions) {
                Intent call = new Intent(context, TeamFixtureActivity.class);
                call.putExtra("id", team.teamId);
                call.putExtra("logo", team.logo);
                call.putExtra("name", team.name);
                startActivity(call);
            }
        });
        countryListRecyclerView.setAdapter(teamListAdapter);
    }


    @OnClick(R.id.teamTextView)
    public void onTeamClick() {
        selectedTab = 0;
        teamItems.clear();
        teamListAdapter.notifyDataSetChanged();

        searchEditText.setText("");
        countryListRecyclerView.setVisibility(View.VISIBLE);
        leagueListRecyclerView.setVisibility(View.GONE);
        teamTextView.setBackground(getResources().getDrawable(R.drawable.border_light_grey));
        leagueTextView.setBackground(null);

    }

    @OnClick(R.id.leagueTextView)
    public void onLeagueClick() {

        selectedTab = 1;

        teamTextView.setBackground(null);
        leagueTextView.setBackground(getResources().getDrawable(R.drawable.border_light_grey));
        countryListRecyclerView.setVisibility(View.GONE);
        leagueListRecyclerView.setVisibility(View.VISIBLE);
        searchEditText.setText("");
        if (listAdapter != null) {

            listAdapter.getFilter().filter("");
        }

        if (leagueListItems.isEmpty()) {
            downloadLeagueList();
        }

    }

//    /**
//     * load country from API
//     */
//    private void loadCountries() {
//        if (InternetConnection.isConnectingToInternet(this)) {
//
//            showProgressBar(context, getResources().getString(R.string.loading));
//            apiManager.getAPIService().getCountiesList().enqueue(
//                    new Callback<CountryResponse>() {
//                        @Override
//                        public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
//                            hideProgressBar(context);
//
//                            if (response.code() == 200) {
//                                countryItems.addAll(response.body().getApi().getCountries());
//                                setupCountryAdapter();
//                            } else {
//                                try {
//                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<CountryResponse> call, Throwable t) {
//                            hideProgressBar(context);
//                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//            );
//
//
//        } else {
//            CustomDialog.showGeneralDialog(this, getString(R.string.network),
//                    getString(R.string.no_internet), DialogType.INFO, new DialogActionListener() {
//                        @Override
//                        public void onDialogAction(DialogAction action) {
//                            finish();
//                        }
//                    });
//        }
//
//    }

//    private void setupCountryAdapter() {
//        gridAdapter = new CountryGridAdapter(this, countryItems, new CountrySelectListener() {
//            @Override
//            public void onCountrySelect(Country country, int pos, Actions actions) {
//
//                if (actions == Actions.VIEW) {
//                    if (country.getLeagueListItems() != null) {
//                        gridAdapter.setSelectedPos(pos);
//                        gridAdapter.notifyDataSetChanged();
//                    } else {
//                        downloadLeagues(country.getCode(), pos);
//                    }
//                }
//            }
//        });
//        countryListRecyclerView.setAdapter(gridAdapter);
//    }


//    /**
//     * download league details
//     */
//    private void downloadLeagues(String code, int pos) {
//        if (InternetConnection.isConnectingToInternet(this)) {
//
//            showProgressBar(context, getResources().getString(R.string.loading));
//            apiManager.getAPIService().getLeagueByCountryList(code).enqueue(
//                    new Callback<LeagueListResponse>() {
//                        @Override
//                        public void onResponse(Call<LeagueListResponse> call, Response<LeagueListResponse> response) {
//                            hideProgressBar(context);
//
//                            if (response.code() == 200) {
//                                ArrayList<LeagueListItem> showItems = new ArrayList<>();
//                                ArrayList<LeagueListItem> listItems = response.body().getApi().getLeagues();
//                                for (int i = 0; i < listItems.size(); i++) {
//                                    if (listItems.get(i).getIsCurrent() == 1) {
//                                        showItems.add(listItems.get(i));
//                                    }
//
//                                }
//                                countryItems.get(pos).setLeagueListItems(showItems);
//                                gridAdapter.setSelectedPos(pos);
//                                gridAdapter.notifyDataSetChanged();
//                            } else {
//                                try {
//                                    Utils.errorResponse(response.code(), context, response.errorBody().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<LeagueListResponse> call, Throwable t) {
//                            hideProgressBar(context);
//                            Toast.makeText(context, t.getMessage() + " " + t.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//            );
//
//
//        } else {
//            CustomDialog.showGeneralDialog(this, getString(R.string.network),
//                    getString(R.string.no_internet), DialogType.INFO, null);
//        }
//    }


    /**
     * download league details
     */
    private void downloadLeagueList() {
        if (InternetConnection.isConnectingToInternet(this)) {

            showProgressBar(context, getResources().getString(R.string.loading));
            apiManager.getAPIService().getLeagueAllList().enqueue(
                    new Callback<LeagueListResponse>() {
                        @Override
                        public void onResponse(Call<LeagueListResponse> call, Response<LeagueListResponse> response) {
                            hideProgressBar(context);

                            if (response.code() == 200) {
                                leagueListItems.addAll(response.body().getApi().getLeagues());
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
     * setup list adapter
     */
    private void setupListAdapter() {
        listAdapter = new LeagueHomeListAdapter(this, leagueListItems, new LeagueHomeItemSelectListener() {
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
