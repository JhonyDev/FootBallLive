package com.bakrin.fblive.model.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team implements Parcelable {

    @SerializedName("team_id")
    @Expose
    public int teamId;
    @SerializedName("team_name")
    @Expose
    public String teamName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("logo")
    @Expose
    public String logo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.teamId);
        dest.writeString(this.teamName);
        dest.writeString(this.name);
        dest.writeString(this.logo);

    }

    public Team() {
    }

    protected Team(Parcel in) {
        this.teamId = in.readInt();
        this.teamName = in.readString();
        this.name = in.readString();
        this.logo = in.readString();

    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

}
