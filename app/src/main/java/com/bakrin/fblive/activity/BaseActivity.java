package com.bakrin.fblive.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bakrin.fblive.MainApplication;
import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.ui.CustomDialog;
import com.bakrin.fblive.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected MainApplication application;
    protected Context context;
    protected APIManager apiManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.versionTextView)
    TextView versionTextView;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.fixtureActionBarImageView)
    ImageView fixtureActionBarImageView;
    @BindView(R.id.backImageView)
    ImageView backImageView;
    @BindView(R.id.drawerImageView)
    ImageView drawerImageView;
    @BindView(R.id.searchImageView)
    ImageView searchImageView;
    @BindView(R.id.fixtureActionBarLinearLayout)
    LinearLayout fixtureActionBarLinearLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    private Unbinder unbinder;
    private boolean isHomeMenuExpand = false;
    private boolean isNavDisable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());

        unbinder = ButterKnife.bind(this);
        context = this;
        application = MainApplication.getInstance();
        apiManager = APIManager.getInstance(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

    }

    protected abstract int getLayoutResourceId();


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isHomeMenuExpand", isHomeMenuExpand);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.termsTextView)
    public void onTermsClick() {
        drawerLayout.closeDrawer(GravityCompat.START);
        Utils.log("TERMS---->", "CLICK");
        Intent call = new Intent(context, PDFViewerActivity.class);
        call.putExtra("title", "Terms of Use");
        call.putExtra("type", "terms");
        startActivity(call);
    }

    @OnClick(R.id.privacyTextView)
    public void onPrivacyClick() {
        drawerLayout.closeDrawer(GravityCompat.START);
        Utils.log("TERMS---->", "CLICK");
        Intent call = new Intent(context, PDFViewerActivity.class);
        call.putExtra("title", "Privacy Policy");
        call.putExtra("type", "privacy");
        startActivity(call);
    }

    /**
     * set up action bar for logged user
     */
    protected void setActionBar(String title, ActionbarMenu[] actionbarMenus) {
        if (title.isEmpty()) {
            titleTextView.setVisibility(View.GONE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
        }
        titleTextView.setText(title);

        fixtureActionBarImageView.setVisibility(View.GONE);
        searchImageView.setVisibility(View.GONE);
        backImageView.setVisibility(View.GONE);
        drawerImageView.setVisibility(View.GONE);
        fixtureActionBarLinearLayout.setVisibility(View.GONE);

        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);

            versionTextView.setText(info.versionName);
        }catch (Exception e){
            e.printStackTrace();
        }


        if (actionbarMenus != null) {
            for (int i = 0; i < actionbarMenus.length; i++) {

                if (actionbarMenus[i] == ActionbarMenu.FIXTURE) {
                    fixtureActionBarImageView.setVisibility(View.VISIBLE);
                }
                if (actionbarMenus[i] == ActionbarMenu.BACK) {
                    backImageView.setVisibility(View.VISIBLE);
                }
                if (actionbarMenus[i] == ActionbarMenu.DRAWER) {
                    drawerImageView.setVisibility(View.VISIBLE);
                }
                if (actionbarMenus[i] == ActionbarMenu.FIXTURE_LAYOUT) {
                    fixtureActionBarLinearLayout.setVisibility(View.VISIBLE);
                }
                if (actionbarMenus[i] == ActionbarMenu.SEARCH) {
                    searchImageView.setVisibility(View.VISIBLE);
                }
            }
        }


    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @OnClick(R.id.fixtureActionBarImageView)
    public void onFixtureClick() {
        Intent call = new Intent(context, HomeActivity.class);
        startActivity(call);
    }

    /**
     * hide keyboard
     */
    protected void hideKeyBoard(Activity context) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    protected void showProgressBar(Context context, String label) {
        CustomDialog.showProgressDialog(context, label);
    }

    protected void hideProgressBar(Context context) {
        CustomDialog.hideProgressDialog();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void hideNavDrawer(boolean isNavDisable) {
        this.isNavDisable = isNavDisable;
        if (isNavDisable) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }

    @OnClick(R.id.drawerImageView)
    public void onDrawerToggleClick() {
        if (isNavDisable) {
            return;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        invalidateOptionsMenu();
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
