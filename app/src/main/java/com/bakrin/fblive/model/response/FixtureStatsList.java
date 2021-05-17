package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FixtureStatsList  implements Parcelable {

    @SerializedName("fixtures")
    @Expose
    public ArrayList<FixtureStats> fixtures;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.fixtures);

    }

    public FixtureStatsList() {
    }

    protected FixtureStatsList(Parcel in) {
        fixtures = in.createTypedArrayList(FixtureStats.CREATOR);

    }

    public static final Parcelable.Creator<FixtureStatsList> CREATOR = new Parcelable.Creator<FixtureStatsList>() {
        @Override
        public FixtureStatsList createFromParcel(Parcel source) {
            return new FixtureStatsList(source);
        }

        @Override
        public FixtureStatsList[] newArray(int size) {
            return new FixtureStatsList[size];
        }
    };

}
