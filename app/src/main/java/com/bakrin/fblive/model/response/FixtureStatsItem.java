package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FixtureStatsItem  implements Parcelable {

    @SerializedName("Shots on Goal")
    @Expose
    public StatsItem Shots_on_Goal;
    @SerializedName("Shots off Goal")
    @Expose
    public StatsItem Shots_off_Goal;
    @SerializedName("Total Shots")
    @Expose
    public StatsItem Total_Shots;
    @SerializedName("Blocked Shots")
    @Expose
    public StatsItem Blocked_Shots;
    @SerializedName("Shots insidebox")
    @Expose
    public StatsItem Shots_insidebox;
    @SerializedName("Shots outsidebox")
    @Expose
    public StatsItem Shots_outsidebox;
    @SerializedName("Fouls")
    @Expose
    public StatsItem Fouls;
    @SerializedName("Corner Kicks")
    @Expose
    public StatsItem Corner_Kicks;
    @SerializedName("Offsides")
    @Expose
    public StatsItem Offsides;
    @SerializedName("Ball Possession")
    @Expose
    public StatsItem BallPossession;
    @SerializedName("Yellow Cards")
    @Expose
    public StatsItem YellowCards;
    @SerializedName("Red Cards")
    @Expose
    public StatsItem RedCards;
    @SerializedName("Goalkeeper Saves")
    @Expose
    public StatsItem GoalkeeperSaves;
    @SerializedName("Total passes")
    @Expose
    public StatsItem TotalPasses;
    @SerializedName("Passes accurate")
    @Expose
    public StatsItem PassesAccurate;
    @SerializedName("Passes %")
    @Expose
    public StatsItem Passes;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(Shots_on_Goal, flags);
        dest.writeParcelable(Shots_off_Goal, flags);
        dest.writeParcelable(Total_Shots, flags);
        dest.writeParcelable(Shots_insidebox, flags);
        dest.writeParcelable(Shots_outsidebox, flags);
        dest.writeParcelable(Fouls, flags);
        dest.writeParcelable(Corner_Kicks, flags);
        dest.writeParcelable(Offsides, flags);
        dest.writeParcelable(BallPossession, flags);
        dest.writeParcelable(YellowCards, flags);
        dest.writeParcelable(RedCards, flags);
        dest.writeParcelable(GoalkeeperSaves, flags);
        dest.writeParcelable(TotalPasses, flags);
        dest.writeParcelable(PassesAccurate, flags);
        dest.writeParcelable(Passes, flags);

    }

    public FixtureStatsItem() {
    }

    protected FixtureStatsItem(Parcel in) {

        this.Shots_on_Goal = in.readParcelable(StatsItem.class.getClassLoader());
        this.Shots_off_Goal = in.readParcelable(StatsItem.class.getClassLoader());
        this.Total_Shots = in.readParcelable(StatsItem.class.getClassLoader());
        this.Shots_insidebox = in.readParcelable(StatsItem.class.getClassLoader());
        this.Shots_outsidebox = in.readParcelable(StatsItem.class.getClassLoader());
        this.Fouls = in.readParcelable(StatsItem.class.getClassLoader());
        this.Corner_Kicks = in.readParcelable(StatsItem.class.getClassLoader());
        this.Offsides = in.readParcelable(StatsItem.class.getClassLoader());
        this.BallPossession = in.readParcelable(StatsItem.class.getClassLoader());
        this.YellowCards = in.readParcelable(StatsItem.class.getClassLoader());
        this.RedCards = in.readParcelable(StatsItem.class.getClassLoader());
        this.GoalkeeperSaves = in.readParcelable(StatsItem.class.getClassLoader());
        this.TotalPasses = in.readParcelable(StatsItem.class.getClassLoader());
        this.PassesAccurate = in.readParcelable(StatsItem.class.getClassLoader());
        this.Passes = in.readParcelable(StatsItem.class.getClassLoader());

    }

    public static final Parcelable.Creator<FixtureStatsItem> CREATOR = new Parcelable.Creator<FixtureStatsItem>() {
        @Override
        public FixtureStatsItem createFromParcel(Parcel source) {
            return new FixtureStatsItem(source);
        }

        @Override
        public FixtureStatsItem[] newArray(int size) {
            return new FixtureStatsItem[size];
        }
    };
}
