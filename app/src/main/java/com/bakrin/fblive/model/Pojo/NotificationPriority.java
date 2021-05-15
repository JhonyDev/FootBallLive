package com.bakrin.fblive.model.Pojo;

public class NotificationPriority {
    int fixtureId;
    int fullTimeResult;
    int halfTimeResult;
    int kickOff;
    int redCard;
    int yellowCard;
    int goal;

    public NotificationPriority(int fixtureId, int fullTimeResult, int halfTimeResult, int kickOff, int redCard, int yellowCard, int goal) {
        this.fixtureId = fixtureId;
        this.fullTimeResult = fullTimeResult;
        this.halfTimeResult = halfTimeResult;
        this.kickOff = kickOff;
        this.redCard = redCard;
        this.yellowCard = yellowCard;
        this.goal = goal;
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
