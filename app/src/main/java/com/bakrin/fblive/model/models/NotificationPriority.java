package com.bakrin.fblive.model.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class NotificationPriority {

    @SerializedName("user_id")
    String userId;
    @SerializedName("fixture_id")
    int fixtureId;
    @SerializedName("full_time_result")
    int fullTimeResultInt;
    @SerializedName("half_time_result")
    int halfTimeResultInt;
    @SerializedName("kick_off")
    int kickOffInt;
    @SerializedName("red_cards")
    int redCardsInt;
    @SerializedName("yellow_cards")
    int yellowCardsInt;
    @SerializedName("goals")
    int goalsInt;
    @SerializedName("notification_id")
    String notificationId;

    public NotificationPriority(String userId, int fixtureId, int fullTimeResultInt,
                                int halfTimeResultInt, int kickOffInt, int redCardsInt,
                                int yellowCardsInt, int goalsInt) {
        this.userId = userId;
        this.fixtureId = fixtureId;
        this.fullTimeResultInt = fullTimeResultInt;
        this.halfTimeResultInt = halfTimeResultInt;
        this.kickOffInt = kickOffInt;
        this.redCardsInt = redCardsInt;
        this.yellowCardsInt = yellowCardsInt;
        this.goalsInt = goalsInt;
        notificationId = UUID.randomUUID().toString();
    }

    public NotificationPriority(int fixtureId, int fullTimeResultInt,
                                int halfTimeResultInt, int kickOffInt, int redCardsInt,
                                int yellowCardsInt, int goalsInt) {
        this.fixtureId = fixtureId;
        this.fullTimeResultInt = fullTimeResultInt;
        this.halfTimeResultInt = halfTimeResultInt;
        this.kickOffInt = kickOffInt;
        this.redCardsInt = redCardsInt;
        this.yellowCardsInt = yellowCardsInt;
        this.goalsInt = goalsInt;
        notificationId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(int fixtureId) {
        this.fixtureId = fixtureId;
    }

    public int getFullTimeResultInt() {
        return fullTimeResultInt;
    }

    public void setFullTimeResultInt(int fullTimeResultInt) {
        this.fullTimeResultInt = fullTimeResultInt;
    }

    public int getHalfTimeResultInt() {
        return halfTimeResultInt;
    }

    public void setHalfTimeResultInt(int halfTimeResultInt) {
        this.halfTimeResultInt = halfTimeResultInt;
    }

    public int getKickOffInt() {
        return kickOffInt;
    }

    public void setKickOffInt(int kickOffInt) {
        this.kickOffInt = kickOffInt;
    }

    public int getRedCardsInt() {
        return redCardsInt;
    }

    public void setRedCardsInt(int redCardsInt) {
        this.redCardsInt = redCardsInt;
    }

    public int getYellowCardsInt() {
        return yellowCardsInt;
    }

    public void setYellowCardsInt(int yellowCardsInt) {
        this.yellowCardsInt = yellowCardsInt;
    }

    public int getGoalsInt() {
        return goalsInt;
    }

    public void setGoalsInt(int goalsInt) {
        this.goalsInt = goalsInt;
    }
}
