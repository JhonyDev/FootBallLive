package com.bakrin.fblive.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FixtureItem implements Parcelable {

    public static final Parcelable.Creator<FixtureItem> CREATOR = new Parcelable.Creator<FixtureItem>() {
        @Override
        public FixtureItem createFromParcel(Parcel source) {
            return new FixtureItem(source);
        }

        @Override
        public FixtureItem[] newArray(int size) {
            return new FixtureItem[size];
        }
    };
    @SerializedName("fixture_id")
    @Expose
    public int fixtureId;
    @SerializedName("league_id")
    @Expose
    public int leagueId;
    @SerializedName("league")
    @Expose
    public League league;
    @SerializedName("round")
    @Expose
    public String round;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("event_date")
    @Expose
    public String eventDate;
    @SerializedName("venue")
    @Expose
    public String venue;
    @SerializedName("score")
    @Expose
    public Score score;
    @SerializedName("events")
    @Expose
    public ArrayList<Events> eventsArray;
    @SerializedName("statusShort")
    @Expose
    public String statusShort;
    @SerializedName("elapsed")
    @Expose
    public String elapsed;
    @SerializedName("homeTeam")
    @Expose
    public Team homeTeam;
    @SerializedName("awayTeam")
    @Expose
    public Team awayTeam;
    @SerializedName("goalsHomeTeam")
    @Expose
    public int goalsHomeTeam;
    @SerializedName("goalsAwayTeam")
    @Expose
    public int goalsAwayTeam;
    @SerializedName("event_timestamp")
    @Expose
    public long event_timestamp;
    public String final_result_cast;

    public String numberOfYellowCards;
    public String numberOfRedCards;
    public int numberYellow;
    public int numberRed;


    public FixtureItem() {
    }

    public FixtureItem(int fixtureId, int leagueId, League league, String round, String status,
                       String eventDate, String venue, Score score, ArrayList<Events> eventsArray,
                       String statusShort, String elapsed, Team homeTeam, Team awayTeam,
                       int goalsHomeTeam, int goalsAwayTeam, long event_timestamp, String final_result_cast,
                       String numberOfYellowCards, String numberOfRedCards, int numberYellow, int numberRed) {
        this.fixtureId = fixtureId;
        this.leagueId = leagueId;
        this.league = league;
        this.round = round;
        this.status = status;
        this.eventDate = eventDate;
        this.venue = venue;
        this.score = score;
        this.eventsArray = eventsArray;
        this.statusShort = statusShort;
        this.elapsed = elapsed;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.goalsHomeTeam = goalsHomeTeam;
        this.goalsAwayTeam = goalsAwayTeam;
        this.event_timestamp = event_timestamp;
        this.final_result_cast = final_result_cast;
        this.numberOfYellowCards = numberOfYellowCards;
        this.numberOfRedCards = numberOfRedCards;
        this.numberYellow = numberYellow;
        this.numberRed = numberRed;
    }

    protected FixtureItem(Parcel in) {
        this.fixtureId = in.readInt();
        this.leagueId = in.readInt();
        this.league = in.readParcelable(League.class.getClassLoader());
        this.round = in.readString();
        this.status = in.readString();
        this.eventDate = in.readString();
        this.venue = in.readString();
        this.statusShort = in.readString();
        this.elapsed = in.readString();
        this.homeTeam = in.readParcelable(Team.class.getClassLoader());
        this.awayTeam = in.readParcelable(Team.class.getClassLoader());
        this.goalsHomeTeam = in.readInt();
        this.goalsAwayTeam = in.readInt();
        this.event_timestamp = in.readLong();
        eventsArray = in.createTypedArrayList(Events.CREATOR);
    }

    public String getNumberOfYellowCards() {
        return numberOfYellowCards;
    }

    public void setNumberOfYellowCards(int numberOfYellowCards) {
        String number;
        switch (numberOfYellowCards) {
            case 1:
                number = "1st";
                break;
            case 2:
                number = "2nd";
                break;
            case 3:
                number = "3rd";
                break;
            default:
                number = numberOfYellowCards + "th";
        }
        this.numberOfYellowCards = number;
    }

    public String getNumberOfRedCards() {
        return numberOfRedCards;
    }

    public void setNumberOfRedCards(int numberOfRedCards) {
        String number;
        switch (numberOfRedCards) {
            case 1:
                number = "1st";
                break;
            case 2:
                number = "2nd";
                break;
            case 3:
                number = "3rd";
                break;
            default:
                number = numberOfRedCards + "th";
        }
        this.numberOfRedCards = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fixtureId);
        dest.writeInt(this.leagueId);
        dest.writeParcelable(league, flags);
        dest.writeString(this.round);
        dest.writeString(this.status);
        dest.writeString(this.eventDate);
        dest.writeString(this.venue);
        dest.writeString(this.statusShort);
        dest.writeString(this.elapsed);
        dest.writeParcelable(homeTeam, flags);
        dest.writeParcelable(awayTeam, flags);
        dest.writeInt(this.goalsHomeTeam);
        dest.writeInt(this.goalsAwayTeam);
        dest.writeLong(this.event_timestamp);
        dest.writeParcelable(score, flags);
        dest.writeTypedList(eventsArray);

    }


//

    public int getFixtureId() {
        return fixtureId;
    }
//
//    public void setFixtureId(int fixtureId) {
//        this.fixtureId = fixtureId;
//    }
//
//    public int getLeagueId() {
//        return leagueId;
//    }
//
//    public void setLeagueId(int leagueId) {
//        this.leagueId = leagueId;
//    }
//
//    public League getLeague() {
//        return league;
//    }
//
//    public void setLeague(League league) {
//        this.league = league;
//    }
//
//    public String getRound() {
//        return round;
//    }
//
//    public void setRound(String round) {
//        this.round = round;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getVenue() {
//        return venue;
//    }
//
//    public void setVenue(String venue) {
//        this.venue = venue;
//    }
//
//    public Team getHomeTeam() {
//        return homeTeam;
//    }
//
//    public void setHomeTeam(Team homeTeam) {
//        this.homeTeam = homeTeam;
//    }
//
//    public Team getAwayTeam() {
//        return awayTeam;
//    }
//
//    public void setAwayTeam(Team awayTeam) {
//        this.awayTeam = awayTeam;
//    }
//
//    public int getGoalsHomeTeam() {
//        return goalsHomeTeam;
//    }
//
//    public void setGoalsHomeTeam(int goalsHomeTeam) {
//        this.goalsHomeTeam = goalsHomeTeam;
//    }
//
//    public int getGoalsAwayTeam() {
//        return goalsAwayTeam;
//    }
//
//    public void setGoalsAwayTeam(int goalsAwayTeam) {
//        this.goalsAwayTeam = goalsAwayTeam;
//    }
//
//    public String getEventDate() {
//        return eventDate;
//    }
//
//    public void setEventDate(String eventDate) {
//        this.eventDate = eventDate;
//    }
//
//    public long getEventTimestamp() {
//        return event_timestamp;
//    }
//
//    public void setEventTimestamp(long event_timestamp) {
//        this.event_timestamp = event_timestamp;
//    }
//
//    public String getStatusShort() {
//        return statusShort;
//    }
//
//    public void setStatusShort(String statusShort) {
//        this.statusShort = statusShort;
//    }
}
