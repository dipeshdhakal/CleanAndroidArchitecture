package com.dipesh.auctionapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "auctiondb";
    private static final int DATABASE_VERSION = 1;


    public static final String AUCTION_ITEM_TABLE = "auction_table";
    public static final String USER_TABLE = "user_table";
    public static final String BID_TABLE = "bid_table";

    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "desc";
    public static final String EXPIRY_DATE_TIME = "expiry_date_time";
    public static final String POSTED_USER_ID = "posted_user_id";
    public static final String USER_NAME = "user_name";
    public static final String AUTH_TOKEN = "auth_token";
    public static final String BID_AMOUNT = "bid_amount";
    public static final String USER_ID = "user_id";
    public static final String AUCTION_ITEM_ID = "auction_item_id";
    public static final String PASSWORD = "password";

    public static final String CREATE_TABLE_USER = "CREATE TABLE "
            + USER_TABLE
            + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + USER_NAME + " TEXT NOT NULL UNIQUE, "
            + PASSWORD + " TEXT, "
            + AUTH_TOKEN + " TEXT" + ")";

    public static final String CREATE_TABLE_AUCTION_ITEM = "CREATE TABLE "
            + AUCTION_ITEM_TABLE
            + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + EXPIRY_DATE_TIME + " INTEGER, "
            + POSTED_USER_ID + " INTEGER, "
            + " FOREIGN KEY ("+ POSTED_USER_ID +") REFERENCES "+ USER_TABLE +"("+ _ID +"));";

    public static final String CREATE_TABLE_BID = "CREATE TABLE "
            + BID_TABLE
            + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BID_AMOUNT + " DOUBLE, "
            + USER_ID + " INTEGER, "
            + AUCTION_ITEM_ID + " INTEGER, "
            + " FOREIGN KEY ("+ USER_ID +") REFERENCES "+ USER_TABLE +"("+ _ID +"),"
            + " FOREIGN KEY ("+ AUCTION_ITEM_ID +") REFERENCES "+ AUCTION_ITEM_ID +"("+ _ID +"));";

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static DBHelper instance = null;

    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_AUCTION_ITEM);
        db.execSQL(CREATE_TABLE_BID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
