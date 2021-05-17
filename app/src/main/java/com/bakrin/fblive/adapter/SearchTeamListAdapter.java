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
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.listener.HomeTeamSelectListener;
import com.bakrin.fblive.model.response.Team;
import com.bakrin.fblive.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTeamListAdapter extends RecyclerView.Adapter<SearchTeamListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Team> dataList;
    private HomeTeamSelectListener listener;
    private Team dataBean;

    public SearchTeamListAdapter(Activity context, ArrayList<Team> dataList,
                                 HomeTeamSelectListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public SearchTeamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_search_team, parent, false);
        return new SearchTeamListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchTeamListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);

        Utils.log("NAME", " : " + dataBean.name);
        holder.teamNameTextView.setText(dataBean.name);


//        holder.saveImageView.setTag(position);
//        holder.saveImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null) {
//                    int pos = (int) view.getTag();
//                    listener.onTeamSelect(pos, dataList.get(pos), Actions.DELETE);
//                }
//            }
//        });

        holder.mainLinearLayout.setTag(position);
        holder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = (int) view.getTag();
                    listener.onTeamSelect(pos, dataList.get(pos), Actions.VIEW);
                }
            }
        });
//
        if (dataBean.logo != null) {
            Picasso.get()
                    .load(dataBean.logo)
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
        @BindView(R.id.teamNameTextView)
        TextView teamNameTextView;
        @BindView(R.id.teamImageView)
        ImageView teamImageView;
        @BindView(R.id.mainLinearLayout)
        LinearLayout mainLinearLayout;
//        @BindView(R.id.saveImageView)
//        ImageView saveImageView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
