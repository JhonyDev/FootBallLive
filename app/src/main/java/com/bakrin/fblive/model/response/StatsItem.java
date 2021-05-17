package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatsItem implements Parcelable {


    public static final Parcelable.Creator<StatsItem> CREATOR = new Parcelable.Creator<StatsItem>() {
        @Override
        public StatsItem createFromParcel(Parcel source) {
            return new StatsItem(source);
        }

        @Override
        public StatsItem[] newArray(int size) {
            return new StatsItem[size];
        }
    };
    @SerializedName("home")
    @Expose
    public String home;
    @SerializedName("away")
    @Expose
    public String away;

    public StatsItem() {
    }

    protected StatsItem(Parcel in) {
        this.home = in.readString();
        this.away = in.readString();


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.home);
        dest.writeString(this.away);


    }
}
