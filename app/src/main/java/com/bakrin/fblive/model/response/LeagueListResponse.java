package com.bakrin.fblive.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueListResponse {

    @SerializedName("api")
    @Expose
    private Leagues api;

    public Leagues getApi() {
        return api;
    }

    public void setApi(Leagues api) {
        this.api = api;
    }
}
