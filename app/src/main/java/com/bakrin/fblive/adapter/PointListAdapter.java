package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.listener.PointTableItemSelectListener;
import com.bakrin.fblive.model.Pojo.PointTableItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<PointTableItem> dataList;
    private PointTableItemSelectListener listener;
    private PointTableItem dataBean;

    public PointListAdapter(Activity context, ArrayList<PointTableItem> dataList,
                            PointTableItemSelectListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public PointListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_point_table, parent, false);
        return new PointListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PointListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);

        holder.rankTextView.setText(String.valueOf(dataBean.getRank()));
        holder.mpTextView.setText(String.valueOf(dataBean.getAll().getMatchsPlayed()));
        holder.winTextView.setText(String.valueOf(dataBean.getAll().getWin()));
        holder.drawTextView.setText(String.valueOf(dataBean.getAll().getDraw()));
        holder.lostTextView.setText(String.valueOf(dataBean.getAll().getLose()));
        holder.gfTextView.setText(String.valueOf(dataBean.getAll().getGoalsFor()));
        holder.gaTextView.setText(String.valueOf(dataBean.getAll().getGoalsAgainst()));
        holder.gdTextView.setText(String.valueOf(dataBean.getGoalsDiff()));
        holder.ptsTextView.setText(String.valueOf(dataBean.getPoints()));
        holder.teamTextView.setText(String.valueOf(dataBean.getTeamName()));


//        holder.mainLinearLayout.setTag(position);
//        holder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null) {
//                    int pos = (int) view.getTag();
//                    listener.onHomeLeagueItemSelect(pos, dataList.get(pos));
//                }
//            }
//        });
//
        if (dataBean.getLogo() != null) {
            Picasso.get()
                    .load(dataBean.getLogo())
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(holder.teamImageView);
        }

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rankTextView)
        TextView rankTextView;
        @BindView(R.id.mpTextView)
        TextView mpTextView;
        @BindView(R.id.winTextView)
        TextView winTextView;
        @BindView(R.id.drawTextView)
        TextView drawTextView;
        @BindView(R.id.lostTextView)
        TextView lostTextView;
        @BindView(R.id.gfTextView)
        TextView gfTextView;
        @BindView(R.id.gaTextView)
        TextView gaTextView;
        @BindView(R.id.gdTextView)
        TextView gdTextView;
        @BindView(R.id.ptsTextView)
        TextView ptsTextView;
        @BindView(R.id.teamTextView)
        TextView teamTextView;
        @BindView(R.id.teamImageView)
        ImageView teamImageView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
