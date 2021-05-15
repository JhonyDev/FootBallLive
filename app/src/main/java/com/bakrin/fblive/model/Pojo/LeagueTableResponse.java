package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueTableResponse {

    @SerializedName("api")
    @Expose
    private PointTableItems api;

    public PointTableItems getApi() {
        return api;
    }

    public void setApi(PointTableItems api) {
        this.api = api;
    }
}
