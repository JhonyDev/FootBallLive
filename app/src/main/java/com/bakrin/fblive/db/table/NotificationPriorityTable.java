package com.bakrin.fblive.db.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bakrin.fblive.db.DatabaseHelper;
import com.bakrin.fblive.info.Info;
import com.bakrin.fblive.model.response.NotificationPriority;
import com.bakrin.fblive.utils.Utils;

import java.util.ArrayList;

public class NotificationPriorityTable implements Info {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;


    public NotificationPriorityTable(Context context) {
        this.context = context;
        if (dbHelper == null)
            dbHelper = new DatabaseHelper(context);
    }

    private void open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
            database = dbHelper.getReadableDatabase();
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
            database.delete(DatabaseHelper.NOTIFICATION_PRIORITY_TABLE, null, null);
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
            database.delete(DatabaseHelper.NOTIFICATION_PRIORITY_TABLE, DatabaseHelper.fixtureId + "=?",
                    new String[]{Integer.toString(fixtureId)});

            Log.i(TAG, "deleteFixture: Fixture deleted");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    /**
     * insert  fixture to database
     */
    public void insertFixture(int fixtureId,
                              int FULL_TIME_RESULT,
                              int HALF_TIME_RESULT,
                              int KICK_OFF,
                              int RED_CARDS,
                              int YELLOW_CARDS,
                              int GOALS) throws Exception {
        try {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.fixtureId, fixtureId);
            values.put(DatabaseHelper.FULL_TIME_RESULT, FULL_TIME_RESULT);
            values.put(DatabaseHelper.HALF_TIME_RESULT, HALF_TIME_RESULT);
            values.put(DatabaseHelper.KICK_OFF, KICK_OFF);
            values.put(DatabaseHelper.RED_CARDS, RED_CARDS);
            values.put(DatabaseHelper.YELLOW_CARDS, YELLOW_CARDS);
            values.put(DatabaseHelper.GOALS, GOALS);

            long i = database.insert(DatabaseHelper.NOTIFICATION_PRIORITY_TABLE, null, values);

            Utils.log("i", " : " + i);

        } finally {
            close();
        }
    }


    /**
     * update fixture to database
     */
    public void updateFixture(int fixtureId,
                              int FULL_TIME_RESULT,
                              int HALF_TIME_RESULT,
                              int KICK_OFF,
                              int RED_CARDS,
                              int YELLOW_CARDS,
                              int GOALS
    ) {


        Log.i(TAG, "updateFixture: " + fixtureId + " " + FULL_TIME_RESULT + " " +
                HALF_TIME_RESULT + " " + KICK_OFF + " " + RED_CARDS + " " + YELLOW_CARDS + " " + GOALS);



        try {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.fixtureId, fixtureId);
            values.put(DatabaseHelper.FULL_TIME_RESULT, FULL_TIME_RESULT);
            values.put(DatabaseHelper.HALF_TIME_RESULT, HALF_TIME_RESULT);
            values.put(DatabaseHelper.KICK_OFF, KICK_OFF);
            values.put(DatabaseHelper.RED_CARDS, RED_CARDS);
            values.put(DatabaseHelper.YELLOW_CARDS, YELLOW_CARDS);
            values.put(DatabaseHelper.GOALS, GOALS);

            long i = database.update(DatabaseHelper.NOTIFICATION_PRIORITY_TABLE, values, DatabaseHelper.fixtureId + " =? ",
                    new String[]{String.valueOf(fixtureId)});

            Utils.log("i", " : " + i);

            Log.i(TAG, "updateFixture: Values Updated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public ArrayList<NotificationPriority> getNotificationPriorityList(){
        Cursor cursor = null;
        try {
            open();
            String query = "SELECT " +
                    DatabaseHelper.fixtureId + " AS " + DatabaseHelper.fixtureId + ", " +
                    DatabaseHelper.FULL_TIME_RESULT + " AS " + DatabaseHelper.FULL_TIME_RESULT + ", " +
                    DatabaseHelper.HALF_TIME_RESULT + " AS " + DatabaseHelper.HALF_TIME_RESULT + ", " +
                    DatabaseHelper.KICK_OFF + " AS " + DatabaseHelper.KICK_OFF + ", " +
                    DatabaseHelper.RED_CARDS + " AS " + DatabaseHelper.RED_CARDS + ", " +
                    DatabaseHelper.YELLOW_CARDS + " AS " + DatabaseHelper.YELLOW_CARDS + ", " +
                    DatabaseHelper.GOALS + " AS " + DatabaseHelper.GOALS +

                    " FROM " + DatabaseHelper.NOTIFICATION_PRIORITY_TABLE;


            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);


            ArrayList<NotificationPriority> notificationPriorityArrayList = new ArrayList<>();
           while (cursor.moveToNext()) {
                NotificationPriority notificationPriority = new NotificationPriority(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.fixtureId)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FULL_TIME_RESULT)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HALF_TIME_RESULT)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KICK_OFF)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.RED_CARDS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.YELLOW_CARDS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GOALS))
                );
                notificationPriorityArrayList.add(notificationPriority);
            }

            return notificationPriorityArrayList;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getPriorityData: Get Data Exception: " + e);
            return null;
        } finally {
            close();
            if (cursor != null) {
                cursor.close();
            }
        }
    }





    public ArrayList<Integer> getPriorityData(int targetFixtureID) {
        Cursor cursor = null;
        try {
            open();
            String query = "SELECT " +
                    DatabaseHelper.fixtureId + " AS " + DatabaseHelper.fixtureId + ", " +
                    DatabaseHelper.FULL_TIME_RESULT + " AS " + DatabaseHelper.FULL_TIME_RESULT + ", " +
                    DatabaseHelper.HALF_TIME_RESULT + " AS " + DatabaseHelper.HALF_TIME_RESULT + ", " +
                    DatabaseHelper.KICK_OFF + " AS " + DatabaseHelper.KICK_OFF + ", " +
                    DatabaseHelper.RED_CARDS + " AS " + DatabaseHelper.RED_CARDS + ", " +
                    DatabaseHelper.YELLOW_CARDS + " AS " + DatabaseHelper.YELLOW_CARDS + ", " +
                    DatabaseHelper.GOALS + " AS " + DatabaseHelper.GOALS +

                    " FROM " + DatabaseHelper.NOTIFICATION_PRIORITY_TABLE + " WHERE "
                    + DatabaseHelper.fixtureId + " = " + targetFixtureID;


            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);


            ArrayList<Integer> integers = new ArrayList<>();
            while (cursor.moveToNext()) {
                Log.i(TAG, "getPriorityData: index check");
                Log.i(TAG, "getPriorityData: index check 2 + " + cursor.getInt(cursor.getColumnIndex(DatabaseHelper.fixtureId)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.fixtureId)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FULL_TIME_RESULT)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HALF_TIME_RESULT)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KICK_OFF)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.RED_CARDS)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.YELLOW_CARDS)));
                integers.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GOALS)));

            }


            Log.e(TAG, "getPriorityData: " + integers);

            return integers;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getPriorityData: Get Data Exception: " + e);
            return null;
        } finally {
            close();
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
