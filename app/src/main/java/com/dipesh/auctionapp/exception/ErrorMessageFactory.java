package com.dipesh.auctionapp.exception;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class ErrorMessageFactory {

    public static String createMessage(Exception e){
        return e.getMessage();
    }
}
