package com.bakrin.fblive.model.response;

import com.google.gson.annotations.SerializedName;

public class NotificationPriority {
    @SerializedName("user_id")
    String user;
    @SerializedName("fixture_id")
    int fixtureId;
    @SerializedName("full_time_result")
    int fullTimeResult;
    @SerializedName("half_time_result")
    int halfTimeResult;
    @SerializedName("kick_off")
    int kickOff;
    @SerializedName("red_cards")
    int redCard;
    @SerializedName("yellow_cards")
    int yellowCard;
    @SerializedName("goals")
    int goal;


    public NotificationPriority(String user, int fixtureId, int fullTimeResult, int halfTimeResult, int kickOff, int redCard, int yellowCard, int goal) {
        this.user = user;
        this.fixtureId = fixtureId;
        this.fullTimeResult = fullTimeResult;
        this.halfTimeResult = halfTimeResult;
        this.kickOff = kickOff;
        this.redCard = redCard;
        this.yellowCard = yellowCard;
        this.goal = goal;
    }

    public NotificationPriority(int fixtureId, int fullTimeResult, int halfTimeResult, int kickOff, int redCard, int yellowCard, int goal) {
        this.fixtureId = fixtureId;
        this.fullTimeResult = fullTimeResult;
        this.halfTimeResult = halfTimeResult;
        this.kickOff = kickOff;
        this.redCard = redCard;
        this.yellowCard = yellowCard;
        this.goal = goal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getFixtureId() {
        return fixtureId;
    }

    public int getFullTimeResult() {
        return fullTimeResult;
    }

    public int getHalfTimeResult() {
        return halfTimeResult;
    }

    public int getKickOff() {
        return kickOff;
    }

    public int getRedCard() {
        return redCard;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public int getGoal() {
        return goal;
    }
}
