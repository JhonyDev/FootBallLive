package com.bakrin.fblive.db;

/**
 * this class use to create sqlite database
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "FOOTBALL24.DB";
    public static final String KEY_ID = "_id";
    public static final String TEAM_ID = "TEAM_ID";
    public static final String TEAM_NAME = "TEAM_NAME";
    public static final String TEAM_LOGO = "TEAM_LOGO";
    public static final String LEAGUE_FLAG = "LEAGUE_FLAG";

    public static final String FULL_TIME_RESULT = "FULL_TIME_RESULT";
    public static final String HALF_TIME_RESULT = "HALF_TIME_RESULT";
    public static final String KICK_OFF = "KICK_OFF";
    public static final String RED_CARDS = "RED_CARDS";
    public static final String YELLOW_CARDS = "YELLOW_CARDS";
    public static final String GOALS = "GOALS";



    public static final String fixtureId = "fixtureId";
    public static final String league_id = "league_id";
    public static final String league_country = "league_country";
    public static final String league_name = "league_name";
    public static final String league_logo = "league_logo";
    public static final String league_flag = "league_flag";
    public static final String round = "round";
    public static final String status = "status";
    public static final String eventDate = "eventDate";
    public static final String venue = "venue";
    public static final String statusShort = "statusShort";
    public static final String elapsed = "elapsed";
    public static final String home_team_id = "home_team_id";
    public static final String home_team_name = "home_team_name";
    public static final String home_team_logo = "home_team_logo";
    public static final String away_team_id = "away_team_id";
    public static final String away_team_name = "away_team_name";
    public static final String away_team_logo = "away_team_logo";
    public static final String goalsHomeTeam = "goalsHomeTeam";
    public static final String goalsAwayTeam = "goalsAwayTeam";
    public static final String event_timestamp = "event_timestamp";
    public static final String final_result_cast = "final_result_cast";


    public static final String TEAM_TABLE = "TEAM_TABLE";
    public static final String FIXTURE_TABLE = "FIXTURE_TABLE";
    public static final String NOTIFICATION_PRIORITY_TABLE = "NOTIFICATION_PRIORITY";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TEAM_TABLE = "CREATE TABLE "
                + TEAM_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + TEAM_ID + " INTEGER,"
                + TEAM_NAME + " TEXT,"
                + TEAM_LOGO + " TEXT,"
                + LEAGUE_FLAG + " TEXT"
                + ")";
        db.execSQL(CREATE_TEAM_TABLE);


        String CREATE_NOTIFICATION_PRIORITY = "CREATE TABLE "
                + NOTIFICATION_PRIORITY_TABLE + "("
                + fixtureId + " INTEGER PRIMARY KEY,"
                + FULL_TIME_RESULT + " INTEGER ,"
                + HALF_TIME_RESULT + " INTEGER,"
                + KICK_OFF + " INTEGER,"
                + RED_CARDS + " INTEGER,"
                + YELLOW_CARDS + " INTEGER,"
                + GOALS + " INTEGER"
                + ")";

        db.execSQL(CREATE_NOTIFICATION_PRIORITY);

        String CREATE_FIXTURE_TABLE = "CREATE TABLE "
                + FIXTURE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + fixtureId + " INTEGER,"
                + league_id + " INTEGER,"
                + home_team_id + " INTEGER,"
                + away_team_id + " INTEGER,"
                + goalsHomeTeam + " INTEGER,"
                + goalsAwayTeam + " INTEGER,"
                + league_country + " TEXT,"
                + league_name + " TEXT,"
                + league_logo + " TEXT,"
                + league_flag + " TEXT,"
                + round + " TEXT,"
                + status + " TEXT,"
                + eventDate + " TEXT,"
                + venue + " TEXT,"
                + statusShort + " TEXT,"
                + elapsed + " TEXT,"
                + home_team_name + " TEXT,"
                + home_team_logo + " TEXT,"
                + away_team_name + " TEXT,"
                + away_team_logo + " TEXT,"
                + event_timestamp + " TEXT,"
                + final_result_cast + " TEXT"
                + ")";
        db.execSQL(CREATE_FIXTURE_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//
//        String CREATE_CARD_STATUS_TABLE = "CREATE TABLE "
//                + CARD_STATUS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
//                + CARD_KEY + " TEXT,"
//                + TYPE + " INTEGER"
//                + ")";
//        db.execSQL(CREATE_CARD_STATUS_TABLE);

    }

}
