package com.bakrin.fblive.model.Pojo;

public class NotificationPriorityString {
    int fixtureId;
    String fullTimeResult;
    String halfTimeResult;
    String kickOff;
    String redCard;
    String yellowCard;
    String goal;

    public NotificationPriorityString(int fixtureId, String fullTimeResult, String halfTimeResult, String kickOff,
                                      String redCard, String yellowCard, String goal) {
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

    public String getFullTimeResult() {
        return fullTimeResult;
    }

    public String getHalfTimeResult() {
        return halfTimeResult;
    }

    public String getKickOff() {
        return kickOff;
    }

    public String getRedCard() {
        return redCard;
    }

    public String getYellowCard() {
        return yellowCard;
    }

    public String getGoal() {
        return goal;
    }
}
