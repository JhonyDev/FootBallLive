package com.bakrin.fblive.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopScorerCard {


    @SerializedName("yellow")
    @Expose
    private int yellow;
    @SerializedName("second_yellow")
    @Expose
    private int secondYellow;
    @SerializedName("red")
    @Expose
    private int red;

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getSecondYellow() {
        return secondYellow;
    }

    public void setSecondYellow(int secondYellow) {
        this.secondYellow = secondYellow;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }
}
