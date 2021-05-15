package com.bakrin.fblive.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bakrin.fblive.db.DatabaseHelper;
import com.bakrin.fblive.model.Pojo.FixtureItem;
import com.bakrin.fblive.model.Pojo.League;
import com.bakrin.fblive.model.Pojo.Team;
import com.bakrin.fblive.utils.Utils;

import java.util.ArrayList;

public class FixtureTable {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;


    public FixtureTable(Context context) {
        this.context = context;
        if (dbHelper == null)
            dbHelper = new DatabaseHelper(context);
    }

    /**
     * open db  connection
     */
    private void open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * close db connection
     */
    private void close() {
        dbHelper.close();
        database.close();
    }

    /**
     * delete all rows
     */
    public void deleteALL() throws Exception {
        try {
            open();
            database.delete(DatabaseHelper.FIXTURE_TABLE, null, null);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }


    /**
     * Delete fixture by ID
     */
    public void deleteFixture(int fixtureId) {

        try {

            open();
            database.delete(DatabaseHelper.FIXTURE_TABLE, DatabaseHelper.fixtureId + "=?",
                    new String[]{Integer.toString(fixtureId)});


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    /**
     * insert  fixture to database
     */
    public void insertFixture(FixtureItem dataBean) {
        try {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.fixtureId, dataBean.fixtureId);
            values.put(DatabaseHelper.league_id, dataBean.leagueId);
            values.put(DatabaseHelper.goalsHomeTeam, dataBean.goalsHomeTeam);
            values.put(DatabaseHelper.goalsAwayTeam, dataBean.goalsAwayTeam);
            values.put(DatabaseHelper.round, dataBean.round);
            values.put(DatabaseHelper.status, dataBean.status);
            values.put(DatabaseHelper.eventDate, dataBean.eventDate);
            values.put(DatabaseHelper.venue, dataBean.venue);
            values.put(DatabaseHelper.statusShort, dataBean.statusShort);
            values.put(DatabaseHelper.elapsed, dataBean.elapsed);
            values.put(DatabaseHelper.event_timestamp, String.valueOf(dataBean.event_timestamp));
            values.put(DatabaseHelper.final_result_cast, String.valueOf(dataBean.final_result_cast));

            if (dataBean.league != null) {
                values.put(DatabaseHelper.league_country, dataBean.league.country);
                values.put(DatabaseHelper.league_flag, dataBean.league.flag);
                values.put(DatabaseHelper.league_logo, dataBean.league.logo);
                values.put(DatabaseHelper.league_name, dataBean.league.name);
            }

            if (dataBean.homeTeam != null) {
                values.put(DatabaseHelper.home_team_id, dataBean.homeTeam.teamId);
                values.put(DatabaseHelper.home_team_logo, dataBean.homeTeam.logo);
                values.put(DatabaseHelper.home_team_name, dataBean.homeTeam.teamName);
            }
            if (dataBean.awayTeam != null) {
                values.put(DatabaseHelper.away_team_id, dataBean.awayTeam.teamId);
                values.put(DatabaseHelper.away_team_logo, dataBean.awayTeam.logo);
                values.put(DatabaseHelper.away_team_name, dataBean.awayTeam.teamName);
            }


            long i = database.insert(DatabaseHelper.FIXTURE_TABLE, null, values);

            Utils.log("i", " : " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    /**
     * update fixture to database
     */
    public void updateFixture(FixtureItem dataBean) {
        try {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.fixtureId, dataBean.fixtureId);
            values.put(DatabaseHelper.league_id, dataBean.leagueId);
            values.put(DatabaseHelper.goalsHomeTeam, dataBean.goalsHomeTeam);
            values.put(DatabaseHelper.goalsAwayTeam, dataBean.goalsAwayTeam);
            values.put(DatabaseHelper.round, dataBean.round);
            values.put(DatabaseHelper.status, dataBean.status);
            values.put(DatabaseHelper.eventDate, dataBean.eventDate);
            values.put(DatabaseHelper.venue, dataBean.venue);
            values.put(DatabaseHelper.statusShort, dataBean.statusShort);
            values.put(DatabaseHelper.elapsed, dataBean.elapsed);
            values.put(DatabaseHelper.event_timestamp, String.valueOf(dataBean.event_timestamp));

            if (dataBean.league != null) {
                values.put(DatabaseHelper.league_country, dataBean.league.country);
                values.put(DatabaseHelper.league_flag, dataBean.league.flag);
                values.put(DatabaseHelper.league_logo, dataBean.league.logo);
                values.put(DatabaseHelper.league_name, dataBean.league.name);
            }

            if (dataBean.homeTeam != null) {
                values.put(DatabaseHelper.home_team_id, dataBean.homeTeam.teamId);
                values.put(DatabaseHelper.home_team_logo, dataBean.homeTeam.logo);
                values.put(DatabaseHelper.home_team_name, dataBean.homeTeam.teamName);
            }
            if (dataBean.awayTeam != null) {
                values.put(DatabaseHelper.away_team_id, dataBean.awayTeam.teamId);
                values.put(DatabaseHelper.away_team_logo, dataBean.awayTeam.logo);
                values.put(DatabaseHelper.away_team_name, dataBean.awayTeam.teamName);
            }


            long i = database.update(DatabaseHelper.FIXTURE_TABLE, values, DatabaseHelper.fixtureId + " =? ",
                    new String[]{String.valueOf(dataBean.fixtureId)});

            Utils.log("i", " : " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    public void updateFixtureFinalResult(int targetFixtureId, String status) {
        try {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.final_result_cast, status);

            long i = database.update(DatabaseHelper.FIXTURE_TABLE, values, DatabaseHelper.fixtureId + " =? ",
                    new String[]{String.valueOf(targetFixtureId)});

            Utils.log("i", " : " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }


    /**
     *
     */
    public int getTeamStatus(int fixtureId) {

        Cursor cursor = null;
        try {
            int returnVal = -1;

            this.open();
            cursor = database.query(DatabaseHelper.FIXTURE_TABLE,
                    new String[]{DatabaseHelper.fixtureId},
                    DatabaseHelper.fixtureId + "=?",
                    new String[]{String.valueOf(fixtureId)}, null, null, null, null);


            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    returnVal = cursor.getInt(0);
                }
            }


            return returnVal;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            this.close();
            if (cursor != null) {
                cursor.close();
            }
        }


    }


    /**
     * get fixture saved list
     */
    public ArrayList<FixtureItem> getSavedFixtureList() {
        Cursor cursor = null;
        try {
            open();
            ArrayList<FixtureItem> dataList = new ArrayList<FixtureItem>();
            String query = "SELECT " +
                    DatabaseHelper.KEY_ID + " AS " + DatabaseHelper.KEY_ID + ", " +
                    DatabaseHelper.fixtureId + " AS " + DatabaseHelper.fixtureId + ", " +
                    DatabaseHelper.league_id + " AS " + DatabaseHelper.league_id + ", " +
                    DatabaseHelper.home_team_id + " AS " + DatabaseHelper.home_team_id + ", " +
                    DatabaseHelper.away_team_id + " AS " + DatabaseHelper.away_team_id + ", " +
                    DatabaseHelper.goalsHomeTeam + " AS " + DatabaseHelper.goalsHomeTeam + ", " +
                    DatabaseHelper.goalsAwayTeam + " AS " + DatabaseHelper.goalsAwayTeam + ", " +
                    DatabaseHelper.home_team_name + " AS " + DatabaseHelper.home_team_name + ", " +
                    DatabaseHelper.away_team_name + " AS " + DatabaseHelper.away_team_name + ", " +
                    DatabaseHelper.home_team_logo + " AS " + DatabaseHelper.home_team_logo + ", " +
                    DatabaseHelper.away_team_logo + " AS " + DatabaseHelper.away_team_logo + ", " +
                    DatabaseHelper.league_country + " AS " + DatabaseHelper.league_country + ", " +
                    DatabaseHelper.league_name + " AS " + DatabaseHelper.league_name + ", " +
                    DatabaseHelper.league_logo + " AS " + DatabaseHelper.league_logo + ", " +
                    DatabaseHelper.league_flag + " AS " + DatabaseHelper.league_flag + ", " +
                    DatabaseHelper.round + " AS " + DatabaseHelper.round + ", " +
                    DatabaseHelper.status + " AS " + DatabaseHelper.status + ", " +
                    DatabaseHelper.eventDate + " AS " + DatabaseHelper.eventDate + ", " +
                    DatabaseHelper.venue + " AS " + DatabaseHelper.venue + ", " +
                    DatabaseHelper.elapsed + " AS " + DatabaseHelper.elapsed + ", " +
                    DatabaseHelper.event_timestamp + " AS " + DatabaseHelper.event_timestamp + ", " +
                    DatabaseHelper.statusShort + " AS " + DatabaseHelper.statusShort + " " +

                    " FROM " + DatabaseHelper.FIXTURE_TABLE + "   order by " + DatabaseHelper.KEY_ID + " DESC";


            cursor = database.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                FixtureItem item = new FixtureItem();
                item.fixtureId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.fixtureId));
                item.leagueId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.league_id));

                Team home = new Team();
                home.teamId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.home_team_id));
                home.teamName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.home_team_name));
                home.logo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.home_team_logo));
                item.homeTeam = home;


                Team away = new Team();
                away.teamId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.away_team_id));
                away.teamName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.away_team_name));
                away.logo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.away_team_logo));
                item.awayTeam = away;

                item.goalsHomeTeam = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.goalsHomeTeam));
                item.goalsAwayTeam = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.goalsAwayTeam));
                item.round = cursor.getString(cursor.getColumnIndex(DatabaseHelper.round));
                item.status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.status));
                item.statusShort = cursor.getString(cursor.getColumnIndex(DatabaseHelper.statusShort));
                item.eventDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.eventDate));
                item.venue = cursor.getString(cursor.getColumnIndex(DatabaseHelper.venue));
                item.elapsed = cursor.getString(cursor.getColumnIndex(DatabaseHelper.elapsed));
                try {
                    item.event_timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex(DatabaseHelper.event_timestamp)));

                } catch (Exception e) {
                    item.event_timestamp = 0;
                    e.printStackTrace();
                }


                League league = new League();
                league.country = cursor.getString(cursor.getColumnIndex(DatabaseHelper.league_country));
                league.name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.league_name));
                league.logo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.league_logo));
                league.flag = cursor.getString(cursor.getColumnIndex(DatabaseHelper.league_flag));

                item.league = league;


                dataList.add(item);

            }

            return dataList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public String getFinalResultStatus(int targetFixtureId) {
        Cursor cursor = null;
        try {
            open();
            String finalResultStatus = "";
            String query = "SELECT " +
                    DatabaseHelper.final_result_cast + " AS " + DatabaseHelper.final_result_cast + " " +
                    " FROM " + DatabaseHelper.FIXTURE_TABLE +
                    " WHERE " + DatabaseHelper.fixtureId + " = " + targetFixtureId;

            cursor = database.rawQuery(query, null);
            while (cursor.moveToNext()) {

                finalResultStatus = cursor.getString(cursor.getColumnIndex(DatabaseHelper.final_result_cast));

            }

            return finalResultStatus;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
