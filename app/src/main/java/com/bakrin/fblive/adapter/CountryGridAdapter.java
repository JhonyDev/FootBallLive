package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.bakrin.fblive.R;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.activity.LeagueDetailsActivity;
import com.bakrin.fblive.listener.CountrySelectListener;
import com.bakrin.fblive.listener.LeagueHomeItemSelectListener;
import com.bakrin.fblive.model.response.Country;
import com.bakrin.fblive.model.response.LeagueListItem;
import com.bakrin.fblive.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryGridAdapter extends RecyclerView.Adapter<CountryGridAdapter.ViewHolder> implements Filterable {

    private Activity context;
    private ArrayList<Country> dataList;
    private ArrayList<Country> dataFullList;
    private CountrySelectListener listener;
    private Country dataBean;
    private int selectedPos = -1;

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country> filteredList = new ArrayList<>();
            if (constraint == null || constraint.toString().trim().length() == 0) {
                filteredList.addAll(dataFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Country item : dataFullList) {
                    if (item.getCountry().toLowerCase().contains(filterPattern)) {
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

    public CountryGridAdapter(Activity context, ArrayList<Country> dataList,
                              CountrySelectListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;

        this.dataFullList = new ArrayList<>(dataList);
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    @Override
    public CountryGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_home_county, parent, false);
        return new CountryGridAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryGridAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);

        holder.nameTextView.setText(dataBean.getCountry());
        holder.mainLinearLayout.setTag(position);
        holder.mainLinearLayout.setOnClickListener(view -> {
            if (listener != null) {
                int pos = (int) view.getTag();
                listener.onCountrySelect(dataList.get(pos), pos, Actions.VIEW);
            }
        });
        holder.downImageView.setOnClickListener(view -> {
            if (listener != null) {
                String pos = (String) view.getTag();
                Utils.log("TAG", " : " + pos);
//                    listener.onCountrySelect(dataList.get(pos), pos,Actions.CHANGE);
            }
        });

        if (selectedPos == position) {
            holder.listRecyclerView.setVisibility(View.VISIBLE);
            holder.downImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_up));
            holder.downImageView.setTag("up");
        } else {
            holder.listRecyclerView.setVisibility(View.GONE);
            holder.downImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_down));
            holder.downImageView.setTag("down");
        }
        if (dataBean.getFlag() != null) {
            SvgLoader.pluck()
                    .with(context)
                    .setPlaceHolder(R.drawable.img_place_holder, R.drawable.img_place_holder)
                    .load(dataBean.getFlag(), holder.countryFlagImageView);
        }

        holder.listRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(RecyclerView.VERTICAL);
        holder.listRecyclerView.setLayoutManager(llm);
        holder.listRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (dataBean.getLeagueListItems() != null) {
            holder.listAdapter = new HomeLeagueListAdapter(context, dataBean.getLeagueListItems(), new LeagueHomeItemSelectListener() {
                @Override
                public void onHomeLeagueItemSelect(int pos, LeagueListItem leagueListItem) {
                    Intent call = new Intent(context, LeagueDetailsActivity.class);
                    call.putExtra("name", leagueListItem.getName());
                    call.putExtra("id", leagueListItem.getLeagueId());
                    call.putExtra("logo", leagueListItem.getLogo());
                    context.startActivity(call);
                }
            });
            holder.listRecyclerView.setAdapter(holder.listAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextView)
        TextView nameTextView;
        @BindView(R.id.countryFlagImageView)
        ImageView countryFlagImageView;
        @BindView(R.id.downImageView)
        ImageView downImageView;
        @BindView(R.id.mainLinearLayout)
        LinearLayout mainLinearLayout;
        @BindView(R.id.listRecyclerView)
        RecyclerView listRecyclerView;

        HomeLeagueListAdapter listAdapter;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
