package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamSearchResponse {

    @SerializedName("api")
    @Expose
    private SearchTeams api;

    public SearchTeams getApi() {
        return api;
    }

    public void setApi(SearchTeams api) {
        this.api = api;
    }
}
