package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.model.Pojo.FixtureStatEvent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FixtureGoalListAdapter extends RecyclerView.Adapter<FixtureGoalListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<FixtureStatEvent> dataList;
    private FixtureStatEvent dataBean;
    private int homeTeam, awayTeam;

    public FixtureGoalListAdapter(Activity context, ArrayList<FixtureStatEvent> dataList,
                                  int homeTeam, int awayTeam) {
        this.context = context;
        this.dataList = dataList;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @Override
    public FixtureGoalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_fixture_goal, parent, false);
        return new FixtureGoalListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FixtureGoalListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);
        holder.awayLinearLayout.setVisibility(View.INVISIBLE);
        holder.homeLinearLayout.setVisibility(View.INVISIBLE);

        if (dataBean.team_id == homeTeam) {
            holder.homeLinearLayout.setVisibility(View.VISIBLE);
        } else {
            holder.awayLinearLayout.setVisibility(View.VISIBLE);
        }
        holder.homePlayerTextView.setText(dataBean.player);
        holder.awayPlayerTextView.setText(dataBean.player);
        holder.homeGoalTextView.setText(String.valueOf(dataBean.elapsed)+"'");
        holder.awayGoalTextView.setText(String.valueOf(dataBean.elapsed)+"'");

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.awayLinearLayout)
        LinearLayout awayLinearLayout;
        @BindView(R.id.homeLinearLayout)
        LinearLayout homeLinearLayout;
        @BindView(R.id.homePlayerTextView)
        TextView homePlayerTextView;
        @BindView(R.id.homeGoalTextView)
        TextView homeGoalTextView;
        @BindView(R.id.awayGoalTextView)
        TextView awayGoalTextView;
        @BindView(R.id.awayPlayerTextView)
        TextView awayPlayerTextView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
