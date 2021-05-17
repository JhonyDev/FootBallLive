package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class H2hData  implements Parcelable {

    @SerializedName("fixtures")
    @Expose
    public ArrayList<FixtureItem> fixtures;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.fixtures);

    }

    public H2hData() {
    }

    protected H2hData(Parcel in) {
        fixtures = in.createTypedArrayList(FixtureItem.CREATOR);

    }

    public static final Parcelable.Creator<H2hData> CREATOR = new Parcelable.Creator<H2hData>() {
        @Override
        public H2hData createFromParcel(Parcel source) {
            return new H2hData(source);
        }

        @Override
        public H2hData[] newArray(int size) {
            return new H2hData[size];
        }
    };
}
