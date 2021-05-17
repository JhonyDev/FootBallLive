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

public class SubstListAdapter extends RecyclerView.Adapter<SubstListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<FixtureStatEvent> dataList;
    private FixtureStatEvent dataBean;
    private int homeTeam, awayTeam;

    public SubstListAdapter(Activity context, ArrayList<FixtureStatEvent> dataList,
                           int homeTeam, int awayTeam) {
        this.context = context;
        this.dataList = dataList;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @Override
    public SubstListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_fixture_subst, parent, false);
        return new SubstListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubstListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);
        if (dataBean.team_id == homeTeam) {
            holder.homeLinearLayout.setVisibility(View.VISIBLE);
            holder.awayLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            holder.awayLinearLayout.setVisibility(View.VISIBLE);
            holder.homeLinearLayout.setVisibility(View.INVISIBLE);
        }

        holder.homePlayerOutTextView.setText(dataBean.player);
        holder.awayPlayerOutTextView.setText(dataBean.player);
        holder.homePlayerInTextView.setText(dataBean.assist);
        holder.awayPlayerInTextView.setText(dataBean.assist);
        holder.timeTextView.setText(String.valueOf(dataBean.elapsed)+"'");


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
        @BindView(R.id.homePlayerOutTextView)
        TextView homePlayerOutTextView;
        @BindView(R.id.homePlayerInTextView)
        TextView homePlayerInTextView;
        @BindView(R.id.awayPlayerOutTextView)
        TextView awayPlayerOutTextView;
        @BindView(R.id.awayPlayerInTextView)
        TextView awayPlayerInTextView;
        @BindView(R.id.timeTextView)
        TextView timeTextView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
