package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveFixtureResponse implements Parcelable {

    @SerializedName("api")
    @Expose
    public Fixtures api;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(api, flags);

    }

    public LiveFixtureResponse() {
    }

    protected LiveFixtureResponse(Parcel in) {
        this.api = in.readParcelable(Fixtures.class.getClassLoader());

    }

    public static final Parcelable.Creator<LiveFixtureResponse> CREATOR = new Parcelable.Creator<LiveFixtureResponse>() {
        @Override
        public LiveFixtureResponse createFromParcel(Parcel source) {
            return new LiveFixtureResponse(source);
        }

        @Override
        public LiveFixtureResponse[] newArray(int size) {
            return new LiveFixtureResponse[size];
        }
    };

//    public Fixtures getApi() {
//        return api;
//    }
//
//    public void setApi(Fixtures api) {
//        this.api = api;
//    }
}
