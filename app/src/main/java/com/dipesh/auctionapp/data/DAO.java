package com.dipesh.auctionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class DAO implements AuctionService {

    private final String BOT_USER_NAME = "bot";

    private static final String SP_AUTH_TOKEN = "sp_auth_token";
    private static DAO instance = null;
    private Context context;
    private SQLiteDatabase database;

    public static synchronized void instantiateDAO(Context context) {
        if (instance == null) {
            instance = new DAO();
            instance.context = context;
            instance.database = DBHelper.getHelper(context).getWritableDatabase();
            instance.createBot();
        }
    }

    private void createBot() {
        User bot = new User();
        bot.setFullName("Bot lot");
        bot.setUserName(BOT_USER_NAME);
        registerUser(bot, "botpass".toCharArray());
    }

    public static DAO getInstance() {
        return instance;
    }

    @Override
    public User getBot() {
        return getUserByUserName(BOT_USER_NAME);
    }

    @Override
    public synchronized List<AuctionItem> getAllAuctionItems() {
        List<AuctionItem> auctionItems = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.AUCTION_ITEM_TABLE,
                new String[]{DBHelper._ID,
                        DBHelper.NAME,
                        DBHelper.DESCRIPTION,
                        DBHelper.EXPIRY_DATE_TIME,
                        DBHelper.POSTED_USER_ID}, null, null, null,
                null, DBHelper.EXPIRY_DATE_TIME);

        while (cursor.moveToNext()) {

            AuctionItem auctionItem = new AuctionItem();
            auctionItem.setUniqueID(cursor.getInt(0));
            auctionItem.setName(cursor.getString(1));
            auctionItem.setDescription(cursor.getString(2));
            auctionItem.setExpiryDateTime(new Date(cursor.getLong(3)));
            auctionItem.setPostedUser(getUserByID(cursor.getInt(4)));

            auctionItems.add(auctionItem);
        }

        cursor.close();
        return auctionItems;
    }

    @Override
    public synchronized List<AuctionItem> getAllAuctionItems(User user) {
        List<AuctionItem> auctionItems = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.AUCTION_ITEM_TABLE,
                new String[]{DBHelper._ID,
                        DBHelper.NAME,
                        DBHelper.DESCRIPTION,
                        DBHelper.EXPIRY_DATE_TIME,
                        DBHelper.POSTED_USER_ID}, DBHelper.POSTED_USER_ID + " != '" + user.getId() +"'", null, null,
                null, DBHelper.EXPIRY_DATE_TIME);

        while (cursor.moveToNext()) {

            AuctionItem auctionItem = new AuctionItem();
            auctionItem.setUniqueID(cursor.getInt(0));
            auctionItem.setName(cursor.getString(1));
            auctionItem.setDescription(cursor.getString(2));
            auctionItem.setExpiryDateTime(new Date(cursor.getLong(3)));
            auctionItem.setPostedUser(getUserByID(cursor.getInt(4)));

            auctionItems.add(auctionItem);
        }

        cursor.close();
        return auctionItems;
    }

    @Override
    public synchronized List<AuctionItem> getAllOwnedItemsByUser(User user) {

        List<AuctionItem> ownedAuctionItems = new ArrayList<>();

        String rawQuery = "SELECT " +
                DBHelper.USER_TABLE + "." + DBHelper._ID + " AS user_id ," +
                DBHelper.USER_TABLE + "." + DBHelper.NAME + " AS user_full_name, " +
                DBHelper.USER_TABLE + "." + DBHelper.USER_NAME + " AS user_name, " +
                DBHelper.AUCTION_ITEM_TABLE + "." + DBHelper._ID + " AS "+DBHelper.AUCTION_ITEM_ID+", " +
                DBHelper.AUCTION_ITEM_TABLE + "." + DBHelper.NAME + " AS auction_name, " +
                DBHelper.AUCTION_ITEM_TABLE + "." + DBHelper.DESCRIPTION + " AS auction_item_desc, " +
                DBHelper.AUCTION_ITEM_TABLE + "." + DBHelper.POSTED_USER_ID + " AS posted_user_id, " +
                DBHelper.AUCTION_ITEM_TABLE + "." + DBHelper.EXPIRY_DATE_TIME + " AS auction_item_expiry_date_time, " +
                "MAX("+ DBHelper.BID_AMOUNT+") AS bid_amount " +
                "FROM " +
                DBHelper.USER_TABLE + " " +
                "INNER JOIN " +
                DBHelper.BID_TABLE + " " +
                "ON " +
                DBHelper.USER_TABLE + "." + DBHelper._ID + " = " + DBHelper.BID_TABLE + "." + DBHelper.USER_ID + " " +
                "INNER JOIN " +
                DBHelper.AUCTION_ITEM_TABLE + " " +
                "ON " +
                DBHelper.BID_TABLE + "." + DBHelper.AUCTION_ITEM_ID + " = " + DBHelper.AUCTION_ITEM_TABLE + "." + DBHelper._ID + " " +
                "GROUP BY " +
                DBHelper.AUCTION_ITEM_ID + " " +
                "HAVING " + DBHelper.USER_TABLE + "." + DBHelper._ID + " = " + user.getId();

        Cursor cursor = database.rawQuery(rawQuery, new String []{});

        while (cursor.moveToNext()){

            AuctionItem auctionItem = new AuctionItem();
            auctionItem.setUniqueID(cursor.getInt(2));
            auctionItem.setName(cursor.getString(4));
            auctionItem.setDescription(cursor.getString(5));
            auctionItem.setExpiryDateTime(new Date(cursor.getLong(7)));

            long postedUserID = cursor.getInt(6);

            User postedUser = getUserByID(postedUserID);

            auctionItem.setPostedUser(postedUser);

            ownedAuctionItems.add(auctionItem);
        }

        cursor.close();

        return ownedAuctionItems;
    }



    @Override
    public synchronized long addAuctionItem(AuctionItem auctionItem) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, auctionItem.getName());
        values.put(DBHelper.DESCRIPTION, auctionItem.getDescription());
        values.put(DBHelper.POSTED_USER_ID, auctionItem.getPostedUser().getId());
        values.put(DBHelper.EXPIRY_DATE_TIME, auctionItem.getExpiryDateTime().getTime());

        return database.insert(DBHelper.AUCTION_ITEM_TABLE, null, values);
    }

    @Override
    public synchronized long bidForAuctionItem(AuctionItem auctionItem, float bid, User bidBy) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.BID_AMOUNT, bid);
        values.put(DBHelper.USER_ID, bidBy.getId());
        values.put(DBHelper.AUCTION_ITEM_ID, auctionItem.getUniqueID());

        return database.insert(DBHelper.BID_TABLE, null, values);
    }

    @Override
    public synchronized long registerUser(User user, char[] password) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, user.getFullName());
        values.put(DBHelper.USER_NAME, user.getUserName());
        values.put(DBHelper.PASSWORD, String.valueOf(password));
        return database.insert(DBHelper.USER_TABLE, null, values);
    }

    @Override
    public synchronized boolean validateLoginInfo(String userName, char[] password) {
        Cursor cursor = database.query(DBHelper.USER_TABLE, new String[]{DBHelper._ID, DBHelper.NAME, DBHelper.USER_NAME},
                DBHelper.USER_NAME + " = '" + userName +"'", null, null, null, null);

        if (cursor.getCount() >= 1) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }

    @Override
    public User getUserByID(long id) {
        Cursor cursor = database.query(DBHelper.USER_TABLE, new String[]{DBHelper._ID, DBHelper.NAME, DBHelper.USER_NAME},
                DBHelper._ID + " = " + id, null, null, null, null);

        int count = cursor.getCount();
        User user = null;
        if (count == 1) {
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUserName(cursor.getString(2));

        }

        cursor.close();
        return user;
    }

    public User getUserByUserName(String userName) {
        Cursor cursor = database.query(DBHelper.USER_TABLE, new String[]{DBHelper._ID, DBHelper.NAME, DBHelper.USER_NAME, DBHelper.AUTH_TOKEN},
                DBHelper.USER_NAME + " = '" + userName +"'", null, null, null, null);

        int count = cursor.getCount();
        User user = null;
        if (count == 1) {
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUserName(cursor.getString(2));
            user.setAuthToken(cursor.getString(3));

        }

        cursor.close();
        return user;
    }

    @Override
    public User generateAuthTokenForUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.AUTH_TOKEN, UUID.randomUUID().toString());
        database.update(DBHelper.USER_TABLE, contentValues, DBHelper.USER_NAME + " = ?", new String[] { user.getUserName() });
        return getUserByUserName(user.getUserName());
    }

    @Override
    public void saveAuthTokenToSharedPref(String authToken) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(SP_AUTH_TOKEN, authToken);
        editor.apply();

    }

    @Override
    public String getAuthTokenFromSharedPref() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SP_AUTH_TOKEN, "");
    }

    @Override
    public User getActiveUser() {
        String spAuthToken = getAuthTokenFromSharedPref();
        if(spAuthToken.isEmpty()){
            return null;
        }

        return getUserByAuthToken(spAuthToken);
    }

    private User getUserByAuthToken(String authToken) {
        Cursor cursor = database.query(DBHelper.USER_TABLE, new String[]{DBHelper._ID, DBHelper.NAME, DBHelper.USER_NAME, DBHelper.AUTH_TOKEN},
                DBHelper.AUTH_TOKEN + " = '" + authToken +"'", null, null, null, null);

        int count = cursor.getCount();
        User user = null;
        if (count >= 1) {
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUserName(cursor.getString(2));
            user.setAuthToken(cursor.getString(3));

        }

        cursor.close();
        return user;
    }

    @Override
    public void clearSession() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(SP_AUTH_TOKEN, "");
        editor.commit();
    }
}
