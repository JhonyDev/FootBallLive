package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Country {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("flag")
    @Expose
    private String flag;

    ArrayList<LeagueListItem> leagueListItems;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ArrayList<LeagueListItem> getLeagueListItems() {
        return leagueListItems;
    }

    public void setLeagueListItems(ArrayList<LeagueListItem> leagueListItems) {
        this.leagueListItems = leagueListItems;
    }
}
