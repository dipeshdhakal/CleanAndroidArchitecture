package com.dipesh.auctionapp.util;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class NumberUtil {

    public static float convertStringToFloat(String number) throws NumberFormatException {
        if(number == null){
            throw new NumberFormatException();
        }
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            throw e;
        }
    }
}
