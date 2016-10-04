package com.lhs.sensorgui.db;

import android.provider.BaseColumns;

/**
 * Created by Dragan on 10/3/2016.
 */
public class UserContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public UserContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String USER_USERNAME = "username";
        public static final String USER_EMAIL = "email";
        public static final String USER_FIRST_NAME = "first_name";
        public static final String USER_LAST_NAME = "last_name";
        public static final String USER_PASSWORD = "password";
        public static final String USER_SEX = "sex";
        public static final String USER_BIRTHDAY = "birthday";
        public static final String USER_HEIGHT = "height";
        public static final String USER_WEIGHT = "weight";

        private static final String TEXT_TYPE = " TEXT";
        private static final String DATE_TYPE = " DATE";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                        UserEntry._ID + " INTEGER PRIMARY KEY," +
                        UserEntry.USER_USERNAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.USER_EMAIL + TEXT_TYPE + COMMA_SEP +
                        UserEntry.USER_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.USER_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.USER_PASSWORD + TEXT_TYPE +
                        " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
    }
}
