package uk.ac.tees.b1241570.travelexplorer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import studio.knowhere.travelappg.ui.Class.TripPlacePoJO;

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "loopwiki.com";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TRIP_PLACE = "tripplace";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //TABLE TABLE_TRIP_PLACE COLUMNS
    public static final String KEY_TRIPID = "idt";
    public static final String KEY_USERID = "idu";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_LATTITUDE = "lattitude";
    public static final String KEY_LOGGITUDE = "longitude";
    public static final String KEY_PURPOSE = "purpose";
    public static final String KEY_PLACENAME = "placename";


    //SQL for creating users table


    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_TRIP_PLACE = " CREATE TABLE " + TABLE_TRIP_PLACE
            + " ( "
            + KEY_TRIPID + " INTEGER PRIMARY KEY, "
            + KEY_USERID + " TEXT, "
            + KEY_DATE + " TEXT, "
            + KEY_TIME + " TEXT,"
            + KEY_LATTITUDE + " TEXT, "
            + KEY_LOGGITUDE + " TEXT, "
            + KEY_PURPOSE + " TEXT,"
            + KEY_PLACENAME + " TEXT"
            + " ) ";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_TRIP_PLACE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_TRIP_PLACE);
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.userName);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public void addTripPlace(studio.knowhere.travelappg.Database.TripPlace tripPlace) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values to insert
        ContentValues values = new ContentValues();
        values.put(KEY_USERID, tripPlace.idu);
        values.put(KEY_DATE, tripPlace.date);
        values.put(KEY_TIME, tripPlace.time);
        values.put(KEY_LATTITUDE, tripPlace.lattitude);
        values.put(KEY_LOGGITUDE, tripPlace.longitude);
        values.put(KEY_PURPOSE, tripPlace.purpose);
        values.put(KEY_PLACENAME, tripPlace.placename);

        // insert row
        long todo_trip_id = db.insert(TABLE_TRIP_PLACE, null, values);
        Log.v("TAG","INTERTED"+todo_trip_id);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                //Log.v("TAG","FROM DB"+user1);
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

    public boolean isDateExists(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase dbs = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRIP_PLACE,// Selecting Table
                new String[]{KEY_TRIPID, KEY_USERID, KEY_DATE, KEY_PURPOSE},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{date},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true

            return true;
        }

        //if email does not exist return false
        return false;
    }

    public boolean deleteAllTrip() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIP_PLACE,null,null);
        return true;
    }

    public List<TripPlacePoJO> getAllTripPlans() {
        List<TripPlacePoJO> tripPlacePoJOARRAYS = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRIP_PLACE + " ORDER BY " +
                KEY_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TripPlacePoJO tripPlacePoJO = new TripPlacePoJO();
               // tripPlacePoJO.setIdt(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
               // tripPlacePoJO.setIdu(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                tripPlacePoJO.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                tripPlacePoJO.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
                tripPlacePoJO.setPurpose(cursor.getString(cursor.getColumnIndex(KEY_PURPOSE)));
                tripPlacePoJO.setPlacename(cursor.getString(cursor.getColumnIndex(KEY_PLACENAME)));
                tripPlacePoJO.setLattitude(cursor.getString(cursor.getColumnIndex(KEY_LATTITUDE)));
                tripPlacePoJO.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_LOGGITUDE)));

                tripPlacePoJOARRAYS.add(tripPlacePoJO);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return tripPlacePoJOARRAYS;
    }
}
