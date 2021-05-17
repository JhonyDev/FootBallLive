package com.bakrin.fblive.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopScorerResponse {


    @SerializedName("api")
    @Expose
    private TopScorersList api;

    public TopScorersList getApi() {
        return api;
    }

    public void setApi(TopScorersList api) {
        this.api = api;
    }
}
