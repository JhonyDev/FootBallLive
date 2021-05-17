package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FixtureStatEvent implements Parcelable {

    @SerializedName("elapsed")
    @Expose
    public int elapsed;
    @SerializedName("team_id")
    @Expose
    public int team_id;
    @SerializedName("player_id")
    @Expose
    public int player_id;
    @SerializedName("assist_id")
    @Expose
    public int assist_id;
    @SerializedName("elapsed_plus")
    @Expose
    public String elapsed_plus;
    @SerializedName("teamName")
    @Expose
    public String teamName;
    @SerializedName("player")
    @Expose
    public String player;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("detail")
    @Expose
    public String detail;
    @SerializedName("comments")
    @Expose
    public String comments;
    @SerializedName("assist")
    @Expose
    public String assist;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.elapsed);
        dest.writeInt(this.team_id);
        dest.writeInt(this.player_id);
        dest.writeInt(this.assist_id);
        dest.writeString(this.elapsed_plus);
        dest.writeString(this.teamName);
        dest.writeString(this.player);
        dest.writeString(this.type);
        dest.writeString(this.detail);
        dest.writeString(this.comments);
        dest.writeString(this.assist);

    }

    public FixtureStatEvent() {
    }

    protected FixtureStatEvent(Parcel in) {
        this.elapsed = in.readInt();
        this.team_id = in.readInt();
        this.player_id = in.readInt();
        this.assist_id = in.readInt();
        this.elapsed_plus = in.readString();
        this.teamName = in.readString();
        this.player = in.readString();
        this.type = in.readString();
        this.detail = in.readString();
        this.comments = in.readString();
        this.assist = in.readString();


    }

    public static final Parcelable.Creator<FixtureStatEvent> CREATOR = new Parcelable.Creator<FixtureStatEvent>() {
        @Override
        public FixtureStatEvent createFromParcel(Parcel source) {
            return new FixtureStatEvent(source);
        }

        @Override
        public FixtureStatEvent[] newArray(int size) {
            return new FixtureStatEvent[size];
        }
    };
}
