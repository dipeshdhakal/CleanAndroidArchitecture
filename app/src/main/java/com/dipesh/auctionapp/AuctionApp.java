package com.dipesh.auctionapp;

import android.app.Application;

import com.dipesh.auctionapp.data.DAO;

/**
 * Created by Dipeshdhakal on 5/17/16. test
 */
public class AuctionApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DAO.instantiateDAO(getApplicationContext());
    }
}
