package com.bakrin.fblive.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.api.APIManager;
import com.bakrin.fblive.api.APIService;
import com.bakrin.fblive.db.table.FixtureTable;
import com.bakrin.fblive.db.table.NotificationPriorityTable;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.listener.FixtureItemSelectListener;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.NotificationPriority;
import com.bakrin.fblive.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.venueTextView.setText(dataBean.venue);
        if (isRefresh) {
            holder.refreshImageView.setVisibility(View.VISIBLE);
        } else {
            holder.refreshImageView.setVisibility(View.GONE);
        }

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
                    if(!dataBean.status.equals("Halftime"))
                        elapsed = (Integer.parseInt(dataBean.elapsed) + 2) + "'";

                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.statusTextView.setText(elapsed);
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
                        holder.dateTextView.setTextColor(context.getResources().getColor(R.color.text_gray));
                    } else {
                        holder.dateTextView.setText(fmtShow.format(api));
                        holder.dateTextView.setTextColor(context.getResources().getColor(R.color.text_gray));
                    }
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
                        holder.dateTextView.setTextColor(context.getResources().getColor(R.color.text_green));
                    } else {
                        holder.dateTextView.setText(fmtShow.format(api));
                        holder.dateTextView.setTextColor(context.getResources().getColor(R.color.text_green));
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mainLinearLayout.setTag(position);
        holder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {

                    int pos = (int) view.getTag();
                    if (!dataList.get(pos).statusShort.equalsIgnoreCase("CANC")) {
                        listener.onFixtureSelect(pos, dataList.get(pos), Actions.VIEW);
                    }
                }
            }
        });


        holder.refreshImageView.setTag(position);
        holder.refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {

                    int pos = (int) view.getTag();
                    listener.onFixtureSelect(pos, dataList.get(pos), Actions.REFRESH);
                }
            }
        });

        if (isLiveFixtureActivity) {
            holder.notificationBellIcon.setVisibility(View.VISIBLE);
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


        holder.notificationBellIcon.setOnClickListener(v -> {
            setPopUpWindow(position, holder);
            mypopupWindow.showAsDropDown(v, -500, 0);


        });

        holder.leagueTopLinearLayout.setTag(position);
        holder.leagueTopLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int pos = (int) view.getTag();
                    listener.onFixtureSelect(pos, dataList.get(pos), Actions.VIEW_LEAGUE);
                }
            }
        });
        if (table.getTeamStatus(dataBean.fixtureId) > 0) {
            holder.savedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star_fill));
        } else {
            holder.savedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.vec_star));
        }
        holder.savedImageView.setTag(position);
        holder.savedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

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
//            SvgLoader.pluck()
//                    .with(context)
//                    .setPlaceHolder(R.drawable.img_place_holder, R.drawable.img_place_holder)
//                    .load(dataBean.getLeague().getLogo(), holder.leagueImageView);

            Picasso.get()
                    .load(dataBean.league.logo)
                    .placeholder(R.drawable.img_place_holder)
                    .error(R.drawable.img_place_holder)
                    .into(holder.leagueImageView);
        }
    }

    private void setPopUpWindow(int position, ViewHolder holder) {
        fullTimeResultInt = 0;
        halfTimeResultInt = 0;
        kickOffInt = 0;
        redCardsInt = 0;
        yellowCardsInt = 0;
        goalsInt = 0;

        Log.i(TAG, "setPopUpWindow: Setting up popup Window");
        LayoutInflater inflater = (LayoutInflater)
                context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup, null);

        NotificationPriorityTable notificationPriorityTable = new NotificationPriorityTable(context);
        Log.i(TAG, "setPopUpWindow: " + dataList.get(position).getFixtureId());

        ArrayList<Integer> arrayList = notificationPriorityTable.getPriorityData(dataList.get(position).getFixtureId());

        mypopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        CheckBox fullTimeResult = view.findViewById(R.id.full_time_result);
        CheckBox halfTimeResult = view.findViewById(R.id.half_time_result);
        CheckBox kickOff = view.findViewById(R.id.kick_off);
        CheckBox redCards = view.findViewById(R.id.red_cards);
        CheckBox yellowCards = view.findViewById(R.id.yellow_cards);
        CheckBox goals = view.findViewById(R.id.goals);
        CheckBox notifications = view.findViewById(R.id.notification);

        fullTimeResult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fullTimeResultInt = 1;
            } else {
                fullTimeResultInt = 0;
            }
        });
        halfTimeResult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                halfTimeResultInt = 1;
            } else {
                halfTimeResultInt = 0;
            }
        });
        kickOff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                kickOffInt = 1;
            } else {
                kickOffInt = 0;
            }
        });
        redCards.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                redCardsInt = 1;
            } else {
                redCardsInt = 0;
            }
        });
        yellowCards.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                yellowCardsInt = 1;
            } else {
                yellowCardsInt = 0;
            }
        });
        goals.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                goalsInt = 1;
            } else {
                goalsInt = 0;
            }
        });
        mypopupWindow.setOnDismissListener(() -> {

            if (fullTimeResultInt == 0
                    && halfTimeResultInt == 0
                    && kickOffInt == 0
                    && redCardsInt == 0
                    && yellowCardsInt == 0
                    && goalsInt == 0) {
                holder.notificationBellIcon.setBackgroundResource(R.drawable.notifications_disabled);
                notificationPriorityTable.deleteFixture(dataList.get(position).getFixtureId());

            } else {
                try {
                    notificationPriorityTable.deleteFixture(dataList.get(position).getFixtureId());
                    notificationPriorityTable.insertFixture(dataList.get(position).getFixtureId(),
                            fullTimeResultInt, halfTimeResultInt, kickOffInt, redCardsInt, yellowCardsInt, goalsInt);
                    Log.i(TAG, "setPopUpWindow: inserted");
                } catch (Exception e) {
                    Toast.makeText(context, "Error updating", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "setPopUpWindow: updated");
                }


                holder.notificationBellIcon.setBackgroundResource(R.drawable.notificications_enabled);
            }
            NotificationPriority notificationPriority = new NotificationPriority(
                    Utils.getIdInSharedPrefs(context),
                    dataList.get(position).getFixtureId(),
                    fullTimeResultInt,
                    halfTimeResultInt,
                    kickOffInt,
                    redCardsInt,
                    yellowCardsInt,
                    goalsInt

            );
            if (!Utils.getIdInSharedPrefs(context).equals(NO_ID)) {
                Toast.makeText(context, "Posting user Id", Toast.LENGTH_SHORT).show();
                APIManager.getRetrofit()
                        .create(APIService.class)
                        .postFixtureItem(notificationPriority)
                        .enqueue(new Callback<NotificationPriority>() {
                            @Override
                            public void onResponse(Call<NotificationPriority> call, Response<NotificationPriority> response) {
                                Log.i(TAG, "onResponse: " + response.body());
                                Log.i(TAG, "onResponse: " + response.message());
                                Log.i(TAG, "onResponse: " + response.errorBody());
                                Log.i(TAG, "onResponse: " + response.code());
                                Log.i(TAG, "onResponse: " + response.raw());
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                    Log.i(TAG, "onResponse: " + response.message());
                                }

                            }

                            @Override
                            public void onFailure(Call<NotificationPriority> call, Throwable t) {
                                Toast.makeText(context, "Error communicating server", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onFailure: " + t.getMessage());
                                Log.i(TAG, "onFailure: " + t.getCause());
                            }
                        });
            }
            Log.i(TAG, "setPopUpWindow: Data Should be written");
        });

        if (arrayList.size() < 1) {
            return;
        }

        boolean isNotificationEnabled = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(1)) {
                isNotificationEnabled = true;
                break;
            }
        }

        notifications.setChecked(isNotificationEnabled);

        fullTimeResult.setChecked(arrayList.get(1).equals(1));
        if (arrayList.get(1).equals(1)) {
            fullTimeResultInt = 1;
        } else {
            fullTimeResultInt = 0;
        }
        halfTimeResult.setChecked(arrayList.get(2).equals(1));
        if (arrayList.get(2).equals(1)) {
            halfTimeResultInt = 1;
        } else {
            halfTimeResultInt = 0;
        }
        kickOff.setChecked(arrayList.get(3).equals(1));
        if (arrayList.get(3).equals(1)) {
            kickOffInt = 1;
        } else {
            kickOffInt = 0;
        }
        redCards.setChecked(arrayList.get(4).equals(1));
        if (arrayList.get(4).equals(1)) {
            redCardsInt = 1;
        } else {
            redCardsInt = 0;
        }
        yellowCards.setChecked(arrayList.get(5).equals(1));
        if (arrayList.get(5).equals(1)) {
            yellowCardsInt = 1;
        } else {
            yellowCardsInt = 0;
        }
        goals.setChecked(arrayList.get(6).equals(1));
        if (arrayList.get(6).equals(1)) {
            goalsInt = 1;
        } else {
            goalsInt = 0;
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


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
