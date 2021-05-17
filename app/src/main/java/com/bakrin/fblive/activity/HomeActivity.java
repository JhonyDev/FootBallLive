package com.bakrin.fblive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.adapter.CountryGridAdapter;
import com.bakrin.fblive.listener.CountrySelectListener;
import com.bakrin.fblive.model.response.Country;
import com.bakrin.fblive.model.response.CountryResponse;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.InternetConnection;
import com.bakrin.fblive.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.countryListRecyclerView)
    RecyclerView countryListRecyclerView;

    private ArrayList<Country> countryItems;
    private CountryGridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }


    /**
     * initialize component
     */
    private void init() {
        setActionBar(getResources().getString(R.string.app_name), new ActionbarMenu[]{ActionbarMenu.BACK});

//        countryListRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager llmJustPay = new LinearLayoutManager(context);
//        llmJustPay.setOrientation(LinearLayoutManager.HORIZONTAL);
//        countryListRecyclerView.setLayoutManager(llmJustPay);
//        countryListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        countryListRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        loadCountries();
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
                                setupGridAdapter();
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
    private void setupGridAdapter() {
        gridAdapter = new CountryGridAdapter(this, countryItems, new CountrySelectListener() {
            @Override
            public void onCountrySelect(Country country, int pos, Actions actions) {
                if (actions == Actions.VIEW) {
                    Intent call = new Intent(context, LeagueHomeActivity.class);
                    call.putExtra("country", country.getCountry());
                    call.putExtra("code", country.getCode());
                    call.putExtra("flag", country.getFlag());

                    startActivity(call);
                }
            }
        });
        countryListRecyclerView.setAdapter(gridAdapter);
    }

}
