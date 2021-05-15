package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointTableItem {

    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("team_id")
    @Expose
    private int teamId;
    @SerializedName("goalsDiff")
    @Expose
    private int goalsDiff;
    @SerializedName("points")
    @Expose
    private int points;
    @SerializedName("teamName")
    @Expose
    private String teamName;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("forme")
    @Expose
    private String forme;
    @SerializedName("all")
    @Expose
    private PointTableAllItem all;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getGoalsDiff() {
        return goalsDiff;
    }

    public void setGoalsDiff(int goalsDiff) {
        this.goalsDiff = goalsDiff;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getForme() {
        return forme;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public PointTableAllItem getAll() {
        return all;
    }

    public void setAll(PointTableAllItem all) {
        this.all = all;
    }
}
