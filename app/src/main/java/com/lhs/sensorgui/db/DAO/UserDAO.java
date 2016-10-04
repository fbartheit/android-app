package com.lhs.sensorgui.db.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.TorquePreferences;
import com.lhs.sensorgui.app.model.User;
import com.lhs.sensorgui.db.DBHelper;
import com.lhs.sensorgui.db.UserContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dragan on 10/3/2016.
 */
public class UserDAO {

    public static String USER_LOGIN_SP = "com.lhs.sensorgui.db.DAO.UserDAO.USER_LOGIN_SP";

    public static long add(DBHelper dbHelper, User user){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.USER_FIRST_NAME, user.getFirstName());
        values.put(UserContract.UserEntry.USER_LAST_NAME, user.getLastName());
        values.put(UserContract.UserEntry.USER_PASSWORD, user.getPassword());
        values.put(UserContract.UserEntry.USER_EMAIL, user.getEmail());
        values.put(UserContract.UserEntry.USER_USERNAME, user.getUsername());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);

        return newRowId;
    }

//    public User read(DBHelper dbHelper, long userId){
    public static User read(DBHelper dbHelper, String username, String password){
        User user = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserContract.UserEntry.USER_FIRST_NAME,
                UserContract.UserEntry.USER_LAST_NAME,
                UserContract.UserEntry.USER_PASSWORD,
                UserContract.UserEntry.USER_EMAIL,
                UserContract.UserEntry.USER_USERNAME
        };

        Cursor c = db.query(
                UserContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                "( " + UserContract.UserEntry.USER_USERNAME + "=? AND " + UserContract.UserEntry.USER_PASSWORD + "=? )"
                + " OR ( "+ UserContract.UserEntry.USER_EMAIL + "=? AND " + UserContract.UserEntry.USER_PASSWORD +"=? ) ",
                new String[]{username, password, username, password},       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        while (c.moveToNext()){
            user = new User();
            user.setFirstName(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_FIRST_NAME)));
            user.setLastName(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_LAST_NAME)));
            user.setEmail(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_EMAIL)));
            user.setPassword(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_PASSWORD)));
            user.setUsername(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_USERNAME)));
        }
        c.close();
        return user;
    }

    public static User read(DBHelper dbHelper, String username){
        User user = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserContract.UserEntry.USER_FIRST_NAME,
                UserContract.UserEntry.USER_LAST_NAME,
                UserContract.UserEntry.USER_PASSWORD,
                UserContract.UserEntry.USER_EMAIL,
                UserContract.UserEntry.USER_USERNAME
        };

        Cursor c = db.query(
                UserContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                "" + UserContract.UserEntry.USER_USERNAME + "=? "
                        + " OR "+ UserContract.UserEntry.USER_EMAIL + "=? ",
                new String[]{username, username},       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        while (c.moveToNext()){
            user = new User();
            user.setFirstName(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_FIRST_NAME)));
            user.setLastName(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_LAST_NAME)));
            user.setEmail(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_EMAIL)));
            user.setPassword(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_PASSWORD)));
            user.setUsername(c.getString(c.getColumnIndex(UserContract.UserEntry.USER_USERNAME)));
        }
        c.close();
        return user;
    }

    public static User loadUserFromSharedPreferences(Context context) {
        // TODO dont do this all the time, but instead check if pref is already set
        PreferenceManager.setDefaultValues(context, R.xml.pref_general, false);
        //SharedPreferences sp = context.getSharedPreferences(TorquePreferences.TORQUE_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sp.getString(TorquePreferences.SP_USER_NAME, null);
        int height = Integer.parseInt(sp.getString(TorquePreferences.SP_USER_HEIGHT, "-1"));
        double weight = Double.parseDouble(sp.getString(TorquePreferences.SP_USER_WEIGHT, "-1"));
        // reading date
        String sDate = sp.getString(TorquePreferences.SP_USER_AGE, "62756640000000");
        long dob;
        if (sDate.contains("-")) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(sDate);
                dob = date.getTime();
            } catch (ParseException pe) {
                dob = 0;
                pe.printStackTrace();
            }

        } else {
            dob = Long.parseLong(sDate);
        }
        String sex = sp.getString(TorquePreferences.SP_USER_SEX, User.SEX_MALE);
        User user = null;
        if (name != null
                && height != -1
                && weight != -1
                && dob != -1
                && sex != null) {
            user = new User(name, height, weight, dob, sex);
        }
        return user;
    }
}
