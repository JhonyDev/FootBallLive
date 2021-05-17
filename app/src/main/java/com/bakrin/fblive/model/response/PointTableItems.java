package com.bakrin.fblive.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PointTableItems {

    @SerializedName("standings")
    @Expose
    private ArrayList<ArrayList<PointTableItem>> standings;

    public ArrayList<ArrayList<PointTableItem>> getStandings() {
        return standings;
    }

    public void setStandings(ArrayList<ArrayList<PointTableItem>> standings) {
        this.standings = standings;
    }
}
