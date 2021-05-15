package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopScorerGame {


    @SerializedName("appearences")
    @Expose
    private int appearences;
    @SerializedName("minutes_played")
    @Expose
    private int minutesPlayed;

    public int getAppearences() {
        return appearences;
    }

    public void setAppearences(int appearences) {
        this.appearences = appearences;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }
}
