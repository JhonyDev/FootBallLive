package com.bakrin.fblive.model.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Fixtures implements Parcelable {

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

    public Fixtures() {
    }

    protected Fixtures(Parcel in) {
        fixtures = in.createTypedArrayList(FixtureItem.CREATOR);

    }

    public static final Parcelable.Creator<Fixtures> CREATOR = new Parcelable.Creator<Fixtures>() {
        @Override
        public Fixtures createFromParcel(Parcel source) {
            return new Fixtures(source);
        }

        @Override
        public Fixtures[] newArray(int size) {
            return new Fixtures[size];
        }
    };


//    public ArrayList<FixtureItem> getFixtures() {
//        return fixtures;
//    }
//
//    public void setFixtures(ArrayList<FixtureItem> fixtures) {
//        this.fixtures = fixtures;
//    }
}
