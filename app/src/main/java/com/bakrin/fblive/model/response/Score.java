package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score implements Parcelable {
    @SerializedName("halftime")
    @Expose
    public String firstHalfResult;
    @SerializedName("fulltime")
    @Expose
    public String secondHalfResult;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstHalfResult);
        dest.writeString(this.secondHalfResult);
    }

    public Score() {
    }

    protected Score(Parcel in) {
        this.firstHalfResult = in.readString();
        this.secondHalfResult = in.readString();

    }

    public static final Parcelable.Creator<League> CREATOR = new Parcelable.Creator<League>() {
        @Override
        public League createFromParcel(Parcel source) {
            return new League(source);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };




}
