package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.listener.TopScorerSelectListener;
import com.bakrin.fblive.model.response.TopScorerItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopScorerListAdapter extends RecyclerView.Adapter<TopScorerListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<TopScorerItem> dataList;
    private TopScorerSelectListener listener;
    private TopScorerItem dataBean;

    public TopScorerListAdapter(Activity context, ArrayList<TopScorerItem> dataList,
                                TopScorerSelectListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public TopScorerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_top_scorer, parent, false);
        return new TopScorerListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopScorerListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);

        holder.playerNameTextView.setText(dataBean.getPlayerName());
        holder.countryTextView.setText(dataBean.getNationality());
        holder.clubTextView.setText(dataBean.getTeamName());

        if (dataBean.getGames() != null) {
            holder.matchTextView.setText(String.valueOf(dataBean.getGames().getAppearences()));
        }
        if (dataBean.getGoals() != null) {
            holder.goalTextView.setText(String.valueOf(dataBean.getGoals().getTotal()));
            holder.assistsTextView.setText(String.valueOf(dataBean.getGoals().getAssists()));
        }
        if (dataBean.getCards() != null) {
            holder.redCardTextView.setText(String.valueOf(dataBean.getCards().getRed()));
            holder.doubleCardTextView.setText(String.valueOf(dataBean.getCards().getSecondYellow()));
            holder.yellowCardTextView.setText(String.valueOf(dataBean.getCards().getYellow()));
        }


    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playerNameTextView)
        TextView playerNameTextView;
        @BindView(R.id.matchTextView)
        TextView matchTextView;
        @BindView(R.id.goalTextView)
        TextView goalTextView;
        @BindView(R.id.assistsTextView)
        TextView assistsTextView;
        @BindView(R.id.redCardTextView)
        TextView redCardTextView;
        @BindView(R.id.doubleCardTextView)
        TextView doubleCardTextView;
        @BindView(R.id.yellowCardTextView)
        TextView yellowCardTextView;
        @BindView(R.id.countryTextView)
        TextView countryTextView;
        @BindView(R.id.clubTextView)
        TextView clubTextView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
