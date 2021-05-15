package com.bakrin.fblive.model.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class H2HResponse implements Parcelable {

    @SerializedName("api")
    @Expose
    public H2hData api;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(api, flags);

    }

    public H2HResponse() {
    }

    protected H2HResponse(Parcel in) {
        this.api = in.readParcelable(H2hData.class.getClassLoader());

    }

    public static final Parcelable.Creator<H2HResponse> CREATOR = new Parcelable.Creator<H2HResponse>() {
        @Override
        public H2HResponse createFromParcel(Parcel source) {
            return new H2HResponse(source);
        }

        @Override
        public H2HResponse[] newArray(int size) {
            return new H2HResponse[size];
        }
    };
}
