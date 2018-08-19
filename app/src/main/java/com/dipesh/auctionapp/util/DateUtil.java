package com.dipesh.auctionapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    public static String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return simpleDateFormat.format(date);
    }

    public static Date convertDateFromString(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
