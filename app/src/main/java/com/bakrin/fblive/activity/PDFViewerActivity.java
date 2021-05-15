package com.bakrin.fblive.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.ActionbarMenu;
import com.pdfview.PDFView;

import butterknife.BindView;
import butterknife.OnClick;

public class PDFViewerActivity extends BaseActivity {

    @BindView(R.id.activityMainPdfView)
    PDFView activityMainPdfView;

    private String type;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getIntent().getExtras().getString("title");
        type = getIntent().getExtras().getString("type");

        init();

        if (type.equalsIgnoreCase("terms")){
            activityMainPdfView.fromAsset("terms_of_use.pdf").show();
        }

        if (type.equalsIgnoreCase("privacy")){
            activityMainPdfView.fromAsset("privacy_policy.pdf").show();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_p_d_f_viewer;
    }

    @OnClick(R.id.backImageView)
    public void onBackClick() {
        finish();
    }

    /**
     * initialize component
     */

    private void init() {
        setActionBar(title, new ActionbarMenu[]{ActionbarMenu.BACK});
    }
}