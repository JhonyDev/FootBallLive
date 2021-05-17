package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.model.response.FixtureStatEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<FixtureStatEvent> dataList;
    private FixtureStatEvent dataBean;
    private int homeTeam, awayTeam;

    public CardListAdapter(Activity context, ArrayList<FixtureStatEvent> dataList,
                           int homeTeam, int awayTeam) {
        this.context = context;
        this.dataList = dataList;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_fixture_card, parent, false);
        return new CardListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);
        if (dataBean.team_id == homeTeam) {
            holder.homeLinearLayout.setVisibility(View.VISIBLE);
            holder.awayLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            holder.awayLinearLayout.setVisibility(View.VISIBLE);
            holder.homeLinearLayout.setVisibility(View.INVISIBLE);
        }
        holder.homePlayerTextView.setText(dataBean.player);
        holder.awayPlayerTextView.setText(dataBean.player);
        holder.timeTextView.setText(String.valueOf(dataBean.elapsed)+"'");
        if (dataBean.detail.contains("Red") || dataBean.detail.contains("red")) {
            holder.awayImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_red));
            holder.homeImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_red));
        }
        if (dataBean.detail.contains("Yellow") || dataBean.detail.contains("yellow")) {
            holder.awayImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yellow));
            holder.homeImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yellow));
        }


    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.homeLinearLayout)
        LinearLayout homeLinearLayout;
        @BindView(R.id.awayLinearLayout)
        LinearLayout awayLinearLayout;
        @BindView(R.id.homePlayerTextView)
        TextView homePlayerTextView;
        @BindView(R.id.awayPlayerTextView)
        TextView awayPlayerTextView;
        @BindView(R.id.timeTextView)
        TextView timeTextView;
        @BindView(R.id.awayImageView)
        ImageView awayImageView;
        @BindView(R.id.homeImageView)
        ImageView homeImageView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
