package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FixtureStats implements Parcelable {

    @SerializedName("fixture_id")
    @Expose
    public int fixtureId;
    @SerializedName("league_id")
    @Expose
    public int leagueId;
    @SerializedName("league")
    @Expose
    public League league;
    @SerializedName("round")
    @Expose
    public String round;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("event_date")
    @Expose
    public String eventDate;
    @SerializedName("venue")
    @Expose
    public String venue;
    @SerializedName("statusShort")
    @Expose
    public String statusShort;
    @SerializedName("homeTeam")
    @Expose
    public Team homeTeam;
    @SerializedName("awayTeam")
    @Expose
    public Team awayTeam;
    @SerializedName("goalsHomeTeam")
    @Expose
    public int goalsHomeTeam;
    @SerializedName("goalsAwayTeam")
    @Expose
    public int goalsAwayTeam;
    @SerializedName("event_timestamp")
    @Expose
    public long event_timestamp;
    @SerializedName("events")
    @Expose
    public ArrayList<FixtureStatEvent> events;
    @SerializedName("statistics")
    @Expose
    public FixtureStatsItem statistics;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fixtureId);
        dest.writeInt(this.leagueId);
        dest.writeParcelable(league, flags);
        dest.writeString(this.round);
        dest.writeString(this.status);
        dest.writeString(this.eventDate);
        dest.writeString(this.venue);
        dest.writeString(this.statusShort);
        dest.writeParcelable(homeTeam, flags);
        dest.writeParcelable(awayTeam, flags);
        dest.writeInt(this.goalsHomeTeam);
        dest.writeInt(this.goalsAwayTeam);
        dest.writeLong(this.event_timestamp);
//        dest.writeParcelable(events, flags);
        dest.writeTypedList(this.events);
        dest.writeParcelable(statistics, flags);

    }

    public FixtureStats() {
    }

    protected FixtureStats(Parcel in) {
        this.fixtureId = in.readInt();
        this.leagueId = in.readInt();
        this.league = in.readParcelable(League.class.getClassLoader());
        this.round = in.readString();
        this.status = in.readString();
        this.eventDate = in.readString();
        this.venue = in.readString();
        this.statusShort = in.readString();
        this.homeTeam = in.readParcelable(Team.class.getClassLoader());
        this.awayTeam = in.readParcelable(Team.class.getClassLoader());
        this.goalsHomeTeam = in.readInt();
        this.goalsAwayTeam = in.readInt();
        this.event_timestamp = in.readLong();
        this.events = in.createTypedArrayList(FixtureStatEvent.CREATOR);
        this.statistics = in.readParcelable(FixtureStatsItem.class.getClassLoader());

    }

    public static final Parcelable.Creator<FixtureStats> CREATOR = new Parcelable.Creator<FixtureStats>() {
        @Override
        public FixtureStats createFromParcel(Parcel source) {
            return new FixtureStats(source);
        }

        @Override
        public FixtureStats[] newArray(int size) {
            return new FixtureStats[size];
        }
    };
}
