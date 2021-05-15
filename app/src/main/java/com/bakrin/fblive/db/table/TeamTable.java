package com.bakrin.fblive.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bakrin.fblive.db.DatabaseHelper;
import com.bakrin.fblive.model.Pojo.Team;
import com.bakrin.fblive.utils.Utils;

import java.util.ArrayList;


public class TeamTable {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;


    public TeamTable(Context context) {
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
            database.delete(DatabaseHelper.TEAM_TABLE, null, null);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }


    /**
     * Delete Team by ID
     */
    public void deleteTeam(int teamID) {

        try {

            open();
            database.delete(DatabaseHelper.TEAM_TABLE, DatabaseHelper.TEAM_ID + "=?",
                    new String[]{Integer.toString(teamID)});


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    /**
     * insert  Team to database
     */
    public void insertTeam(Team dataBean) {
        try {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.TEAM_ID, dataBean.teamId);
            values.put(DatabaseHelper.TEAM_LOGO, dataBean.logo);
            values.put(DatabaseHelper.TEAM_NAME, dataBean.teamName);
            long i = database.insert(DatabaseHelper.TEAM_TABLE, null, values);
            Utils.log("i", " : " + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
//    /**
//     * insert  contact to database
//     */
//    public void insertContact(TrendChatItem dataBean, int hotelId) throws Exception {
//        try {
//            open();
//            ContentValues values = new ContentValues();
//            values.put(DatabaseHelper.HOTEL_ID, hotelId);
//            values.put(DatabaseHelper.CARD_KEY, dataBean.kpiKey);
//            database.insert(DatabaseHelper.HOTEL_ORDER, null, values);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            close();
//        }
//    }
//

    /**
     *
     */
    public int getTeamStatus(int teamID) {

        Cursor cursor = null;
        try {
            int returnVal = -1;

            this.open();
            cursor = database.query(DatabaseHelper.TEAM_TABLE,
                    new String[]{DatabaseHelper.TEAM_ID},
                    DatabaseHelper.TEAM_ID + "=?",
                    new String[]{String.valueOf(teamID)}, null, null, null, null);


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
     * get team saved list
     */
    public ArrayList<Team> getSavedTeams() {
        Cursor cursor = null;
        try {
            open();
            ArrayList<Team> dataList = new ArrayList<Team>();
            String query = "SELECT " +
                    DatabaseHelper.KEY_ID + " AS " + DatabaseHelper.KEY_ID + ", " +
                    DatabaseHelper.TEAM_ID + " AS " + DatabaseHelper.TEAM_ID + ", " +
                    DatabaseHelper.TEAM_LOGO + " AS " + DatabaseHelper.TEAM_LOGO + ", " +
                    DatabaseHelper.TEAM_NAME + " AS " + DatabaseHelper.TEAM_NAME + " " +

                    " FROM " + DatabaseHelper.TEAM_TABLE;//+" WHERE "+DatabaseHelper.HOTEL_ID+" =?";


            cursor = database.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Team team = new Team();
                team.logo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TEAM_LOGO));
                team.teamName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TEAM_NAME));
                team.teamId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TEAM_ID));
                dataList.add(team);

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


}
