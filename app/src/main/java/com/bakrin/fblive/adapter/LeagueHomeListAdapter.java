package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.listener.FixtureItemSelectListener;
import com.bakrin.fblive.listener.LeagueHomeItemSelectListener;
import com.bakrin.fblive.model.response.Country;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.LeagueListItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeagueHomeListAdapter  extends RecyclerView.Adapter<LeagueHomeListAdapter.ViewHolder>implements Filterable {

    private Activity context;
    private ArrayList<LeagueListItem> dataList;
    private ArrayList<LeagueListItem> dataFullList;
    private LeagueHomeItemSelectListener listener;
    private LeagueListItem dataBean;
    private SimpleDateFormat fmt;
    private Date nowDate;

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LeagueListItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.toString().trim().length() == 0) {
                filteredList.addAll(dataFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (LeagueListItem item : dataFullList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }


            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public LeagueHomeListAdapter(Activity context, ArrayList<LeagueListItem> dataList,
                                 LeagueHomeItemSelectListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
        fmt = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = new Date();

        this.dataFullList = new ArrayList<>(dataList);
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    @Override
    public LeagueHomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_league_home_item, parent, false);
        return new LeagueHomeListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeagueHomeListAdapter.ViewHolder holder, int position) {
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
