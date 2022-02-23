package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.bakrin.fblive.R;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.db.table.FixtureTable;
import com.bakrin.fblive.db.table.NotificationPriorityTable;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.listener.FixtureItemSelectListener;
import com.bakrin.fblive.model.response.FixtureItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FixtureListAdapter extends RecyclerView.Adapter<FixtureListAdapter.ViewHolder> implements Info {
    int fullTimeResultInt = 0;
    int halfTimeResultInt = 0;
    int kickOffInt = 0;
    int redCardsInt = 0;
    int yellowCardsInt = 0;
    int goalsInt = 0;
    private PopupWindow mypopupWindow;
    private Activity context;
    private ArrayList<FixtureItem> dataList;
    private FixtureItemSelectListener listener;
    private FixtureItem dataBean;
    private SimpleDateFormat fmt, fmtShow;
    private SimpleDateFormat time;
    private Date nowDate;
    private FixtureTable table;
    private boolean isRefresh;
    private boolean isLiveFixtureActivity;

    public FixtureListAdapter(boolean isLiveFixtureActivity, Activity context, ArrayList<FixtureItem> dataList,
                              FixtureItemSelectListener listener) {

        this.isLiveFixtureActivity = isLiveFixtureActivity;

        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
        fmt = new SimpleDateFormat("yyyy-MM-dd");
//        fmtTimeShow = new SimpleDateFormat("HH:mm");
//        fmtMain = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        fmtShow = new SimpleDateFormat("d MMM yyyy");
        time = new SimpleDateFormat("HH:mm");
        nowDate = new Date();
        table = new FixtureTable(context);
        this.isRefresh = false;
    }

    public FixtureListAdapter(boolean isLiveFixtureActivity, Activity context, boolean isRefresh, ArrayList<FixtureItem> dataList,
                              FixtureItemSelectListener listener) {
        this.isLiveFixtureActivity = isLiveFixtureActivity;
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
        fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmtShow = new SimpleDateFormat("d MMM yyyy");
        time = new SimpleDateFormat("HH:mm");
        nowDate = new Date();
        table = new FixtureTable(context);
        this.isRefresh = isRefresh;

    }

    @Override
    public FixtureListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_fixture, parent, false);
        return new FixtureListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FixtureListAdapter.ViewHolder holder, int position) {
        dataBean = dataList.get(position);

        holder.roundTextView.setText(dataBean.round);
        holder.tvCountry.setText(dataBean.getLeague().country);
        holder.venueTextView.setText(dataBean.venue);
        holder.refreshImageView.setVisibility(View.GONE);

        try {


            if (dataBean.statusShort.equalsIgnoreCase("1H") ||
                    dataBean.statusShort.equalsIgnoreCase("HT") ||
                    dataBean.statusShort.equalsIgnoreCase("ET") ||
                    dataBean.statusShort.equalsIgnoreCase("P") ||
                    dataBean.statusShort.equalsIgnoreCase("BT") ||
                    dataBean.statusShort.equalsIgnoreCase("2H")) {

                holder.dateTextView.setText("Live");
                holder.dateTextView.setTextColor(context.getResources().getColor(R.color.red_text));
                String elapsed = dataBean.elapsed + "'";

                try {
                    if (!dataBean.status.equals("Halftime"))
                        elapsed = (Integer.parseInt(dataBean.elapsed) + 2) + "'";

                } catch (Exception e) {
                    e.printStackTrace();
                }


                holder.statusTextView.setText(dataBean.timerString);
                holder.statusTextView.setTextColor(context.getResources().getColor(R.color.text_green));
            } else {
                Date api = fmt.parse(dataBean.eventDate);

//                Utils.log("TIME DATE"," : "+dataBean.eventDate);
//                Utils.log("TIME",fmtTimeShow.format(fmtMain.parse(dataBean.eventDate)));
//                Utils.log("TIMESTAMP",new Date(dataBean.event_timestamp).toString());

                holder.statusTextView.setText(time.format(new Date(dataBean.event_timestamp * 1000)));
                holder.statusTextView.setTextColor(context.getResources().getColor(R.color.home_text_gray));
                holder.statusTextView.setTextAppearance(context, R.style.Label03_Multi_Line);
                if (dataBean.statusShort.equalsIgnoreCase("FT")
                        || dataBean.statusShort.equalsIgnoreCase("AET")
                        || dataBean.statusShort.equalsIgnoreCase("PEN")) {
                    holder.statusTextView.setText(dataBean.status);
                    holder.statusTextView.setTextAppearance(context, R.style.Label04_multi_line);
                    holder.statusTextView.setTextColor(context.getResources().getColor(R.color.text_light_gray));

                    if (DateUtils.isToday(api.getTime())) {
                        holder.dateTextView.setText("Today");
                    } else {
                        holder.dateTextView.setText(fmtShow.format(api));
                    }
                    holder.dateTextView.setTextColor(context.getResources().getColor(R.color.text_gray));
                } else {
                    if (dataBean.statusShort.equalsIgnoreCase("CANC") ||
                            dataBean.statusShort.equalsIgnoreCase("PST") ||
                            dataBean.statusShort.equalsIgnoreCase("TBD")) {
                        holder.statusTextView.setText(dataBean.status);

                        if (dataBean.statusShort.equalsIgnoreCase("TBD") ||
                                dataBean.statusShort.equalsIgnoreCase("PST")) {
                            holder.statusTextView.setTextColor(context.getResources().getColor(R.color.text_light_gray));
                        } else {
                            holder.statusTextView.setTextColor(context.getResources().getColor(R.color.red_text));
                        }
                        holder.statusTextView.setTextAppearance(context, R.style.Label04_multi_line);

                    }
                    if (DateUtils.isToday(api.getTime())) {
                        holder.dateTextView.setText("Today");
                    } else {
                        holder.dateTextView.setText(fmtShow.format(api));
                    }
                    holder.dateTextView.setTextColor(context.getResources().getColor(R.color.text_green));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mainLinearLayout.setTag(position);
        holder.mainLinearLayout.setOnClickListener(view -> {
            if (listener != null) {
                int pos = (int) view.getTag();
                if (!dataList.get(pos).statusShort.equalsIgnoreCase("CANC")) {
                    listener.onFixtureSelect(pos, dataList.get(pos), Actions.VIEW);
                }
            }
        });


        holder.refreshImageView.setTag(position);
        holder.refreshImageView.setOnClickListener(view -> {
            if (listener != null) {

                int pos = (int) view.getTag();
                listener.onFixtureSelect(pos, dataList.get(pos), Actions.REFRESH);
            }
        });

        if (isLiveFixtureActivity) {
            holder.notificationBellIcon.setVisibility(View.GONE);
        }
        holder.notificationBellIcon.setTag(position);

        NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
        ArrayList<Integer> integers = notificationPriorityTable.getPriorityData(dataList.get(position).getFixtureId());
        boolean isEnabled = false;
        for (int i = 1; i < integers.size(); i++) {
            if (integers.get(i) == 1) {
                isEnabled = true;
                break;
            }
        }
        if (isEnabled) {
            holder.notificationBellIcon.setBackgroundResource(R.drawable.notificications_enabled);
        } else {
            holder.notificationBellIcon.setBackgroundResource(R.drawable.notifications_disabled);
        }

//
//        holder.notificationBellIcon.setOnClickListener(v -> {
//            setPopUpWindow(position, holder);
//            mypopupWindow.showAsDropDown(v, -500, 0);
//
//
//        });

        holder.leagueTopLinearLayout.setTag(position);

        holder.leagueTopLinearLayout.setOnTouchListener((v, event) -> true);
        holder.llFixture.setTag(position);
        holder.llFixture.setOnClickListener(view -> {
            Log.i(TAG, "onBindViewHolder: Clicked");
            if (listener != null) {
                int pos = (int) holder.leagueTopLinearLayout.getTag();
                if (!dataList.get(pos).statusShort.equalsIgnoreCase("CANC")) {
                    listener.onFixtureSelect(pos, dataList.get(pos), Actions.VIEW);
                }
            }
        });
//        holder.llFixture.setOnClickListener(view -> {
//            if (listener != null) {
//                int pos = (int) holder.leagueTopLinearLayout.getTag();
//                listener.onFixtureSelect(pos, dataList.get(pos), Actions.VIEW_LEAGUE);
//            }
//        });
        if (table.getTeamStatus(dataBean.fixtureId) > 0) {
            holder.savedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star_fill));
        } else {
            holder.savedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star));
        }
        holder.savedImageView.setTag(position);
        holder.savedImageView.setOnClickListener(view -> initFav(view));

        if (dataBean.statusShort.equalsIgnoreCase("NS") ||
                dataBean.statusShort.equalsIgnoreCase("SUSP") ||
                dataBean.statusShort.equalsIgnoreCase("INT") ||
                dataBean.statusShort.equalsIgnoreCase("PST") ||
                dataBean.statusShort.equalsIgnoreCase("CANC") ||
                dataBean.statusShort.equalsIgnoreCase("ABD") ||
                dataBean.statusShort.equalsIgnoreCase("AWD") ||
                dataBean.statusShort.equalsIgnoreCase("WO") ||
                dataBean.statusShort.equalsIgnoreCase("TBD")) {
            holder.awayScoreTextView.setVisibility(View.GONE);
            holder.homeScoreTextView.setVisibility(View.GONE);

        } else {
            holder.awayScoreTextView.setVisibility(View.VISIBLE);
            holder.homeScoreTextView.setVisibility(View.VISIBLE);
            holder.middleTextView.setText("-");
        }

        if (dataBean.awayTeam != null) {
            holder.awayTeamTextView.setText(dataBean.awayTeam.teamName);
            holder.awayScoreTextView.setText(String.valueOf(dataBean.goalsAwayTeam));
            Picasso.get()
                    .load(dataBean.awayTeam.logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(holder.awayTeamImageView);
        }
        if (dataBean.homeTeam != null) {
            holder.homeTeamTextView.setText(dataBean.homeTeam.teamName);
            holder.homeScoreTextView.setText(String.valueOf(dataBean.goalsHomeTeam));
            Picasso.get()
                    .load(dataBean.homeTeam.logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(holder.homeTeamImageView);
        }


        if (dataBean.league != null) {

            holder.leagueNameTextView.setText(dataBean.league.name);

            try {
                SvgLoader.pluck()
                        .with(context)
                        .setPlaceHolder(R.drawable.img_place_holder, R.drawable.img_place_holder)
                        .load(dataBean.league.flag, holder.leagueImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
//
//            Picasso.get()
//                    .load(dataBean.league.flag)
//                    .placeholder(R.drawable.img_place_holder)
//                    .error(R.drawable.img_place_holder)
//                    .into(holder.leagueImageView);
        }

    }

    private void initFav(View view) {
        int pos = (int) view.getTag();
        FixtureItem item = dataList.get(pos);
        item.final_result_cast = NOT_SENT_RESULT_STATUS;
        if (table.getTeamStatus(item.fixtureId) > 0) {
            table.deleteFixture(item.fixtureId);
        } else {
            table.insertFixture(item);
        }
        notifyDataSetChanged();
        if (listener != null) {
            listener.onFixtureSelect(pos, dataList.get(pos), Actions.LIST_REFRESH);
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leagueNameTextView)
        TextView leagueNameTextView;
        @BindView(R.id.roundTextView)
        TextView roundTextView;
        @BindView(R.id.tv_country)
        TextView tvCountry;
        @BindView(R.id.middleTextView)
        TextView middleTextView;
        @BindView(R.id.homeTeamTextView)
        TextView homeTeamTextView;
        @BindView(R.id.homeScoreTextView)
        TextView homeScoreTextView;
        @BindView(R.id.awayTeamTextView)
        TextView awayTeamTextView;
        @BindView(R.id.awayScoreTextView)
        TextView awayScoreTextView;
        @BindView(R.id.statusTextView)
        TextView statusTextView;
        @BindView(R.id.dateTextView)
        TextView dateTextView;
        @BindView(R.id.venueTextView)
        TextView venueTextView;
        @BindView(R.id.timeTextView)
        TextView timeTextView;
        @BindView(R.id.leagueImageView)
        ImageView leagueImageView;
        @BindView(R.id.savedImageView)
        ImageView savedImageView;
        @BindView(R.id.homeTeamImageView)
        ImageView homeTeamImageView;
        @BindView(R.id.awayTeamImageView)
        ImageView awayTeamImageView;
        @BindView(R.id.refreshImageView)
        ImageView refreshImageView;
        @BindView(R.id.notification_bell_icon)
        ImageView notificationBellIcon;
        @BindView(R.id.mainLinearLayout)
        FrameLayout mainLinearLayout;
        @BindView(R.id.leagueTopLinearLayout)
        RelativeLayout leagueTopLinearLayout;
        @BindView(R.id.ll_fixture)
        LinearLayout llFixture;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
