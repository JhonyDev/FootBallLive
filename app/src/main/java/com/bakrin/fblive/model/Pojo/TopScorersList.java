package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopScorersList {


    @SerializedName("topscorers")
    @Expose
    private ArrayList<TopScorerItem> topscorers;

    public ArrayList<TopScorerItem> getTopscorers() {
        return topscorers;
    }

    public void setTopscorers(ArrayList<TopScorerItem> topscorers) {
        this.topscorers = topscorers;
    }
}
