package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointTableAllItem {

    @SerializedName("matchsPlayed")
    @Expose
    private int matchsPlayed;
    @SerializedName("win")
    @Expose
    private int win;
    @SerializedName("draw")
    @Expose
    private int draw;
    @SerializedName("lose")
    @Expose
    private int lose;
    @SerializedName("goalsFor")
    @Expose
    private int goalsFor;
    @SerializedName("goalsAgainst")
    @Expose
    private int goalsAgainst;

    public int getMatchsPlayed() {
        return matchsPlayed;
    }

    public void setMatchsPlayed(int matchsPlayed) {
        this.matchsPlayed = matchsPlayed;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }
}
