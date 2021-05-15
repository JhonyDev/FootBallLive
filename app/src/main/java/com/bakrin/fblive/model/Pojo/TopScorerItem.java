package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopScorerItem {
    @SerializedName("player_id")
    @Expose
    private int playerId;
    @SerializedName("player_name")
    @Expose
    private String playerName;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("team_id")
    @Expose
    private int teamId;
    @SerializedName("games")
    @Expose
    private TopScorerGame games;
    @SerializedName("goals")
    @Expose
    private TopScorerGoal goals;
    @SerializedName("cards")
    @Expose
    private TopScorerCard cards;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public TopScorerGame getGames() {
        return games;
    }

    public void setGames(TopScorerGame games) {
        this.games = games;
    }

    public TopScorerGoal getGoals() {
        return goals;
    }

    public void setGoals(TopScorerGoal goals) {
        this.goals = goals;
    }

    public TopScorerCard getCards() {
        return cards;
    }

    public void setCards(TopScorerCard cards) {
        this.cards = cards;
    }
}
