package com.bakrin.fblive.model.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatsResponse implements Parcelable {

    @SerializedName("api")
    @Expose
    public FixtureStatsList api;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(api, flags);

    }

    public StatsResponse() {
    }

    protected StatsResponse(Parcel in) {
        this.api = in.readParcelable(FixtureStatsList.class.getClassLoader());

    }

    public static final Parcelable.Creator<StatsResponse> CREATOR = new Parcelable.Creator<StatsResponse>() {
        @Override
        public StatsResponse createFromParcel(Parcel source) {
            return new StatsResponse(source);
        }

        @Override
        public StatsResponse[] newArray(int size) {
            return new StatsResponse[size];
        }
    };
}
