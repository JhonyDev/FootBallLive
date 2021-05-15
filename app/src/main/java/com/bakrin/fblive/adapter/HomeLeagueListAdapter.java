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
import com.bakrin.fblive.listener.LeagueHomeItemSelectListener;
import com.bakrin.fblive.model.Pojo.LeagueListItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLeagueListAdapter   extends RecyclerView.Adapter<HomeLeagueListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<LeagueListItem> dataList;
    private LeagueHomeItemSelectListener listener;
    private LeagueListItem dataBean;
    private SimpleDateFormat fmt;
    private Date nowDate;

    public HomeLeagueListAdapter(Activity context, ArrayList<LeagueListItem> dataList,
                                 LeagueHomeItemSelectListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
        fmt = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = new Date();
    }

    @Override
    public HomeLeagueListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_country_leagues, parent, false);
        return new HomeLeagueListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeLeagueListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);

        holder.leagueNameTextView.setText(dataBean.getName());


        holder.mainLinearLayout.setTag(position);
        holder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = (int) view.getTag();
                    listener.onHomeLeagueItemSelect(pos, dataList.get(pos));
                }
            }
        });

        if (dataBean.getLogo() != null) {
            Picasso.get()
                    .load(dataBean.getLogo())
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(holder.leagueImageView);
        }

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leagueNameTextView)
        TextView leagueNameTextView;
        @BindView(R.id.leagueImageView)
        ImageView leagueImageView;
        @BindView(R.id.mainLinearLayout)
        LinearLayout mainLinearLayout;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
