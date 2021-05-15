package com.bakrin.fblive.model.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Events implements Parcelable {
    public static final Parcelable.Creator<Events> CREATOR = new Parcelable.Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel source) {
            return new Events(source);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("detail")
    @Expose
    public String detail;

    public Events() {
    }

    protected Events(Parcel in) {
        this.type = in.readString();
        this.detail = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.detail);
    }


}
