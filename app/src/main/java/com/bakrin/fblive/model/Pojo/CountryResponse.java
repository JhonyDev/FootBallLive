package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryResponse {

    @SerializedName("api")
    @Expose
    private Countries api;

    public Countries getApi() {
        return api;
    }

    public void setApi(Countries api) {
        this.api = api;
    }
}
