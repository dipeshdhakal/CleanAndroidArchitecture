package com.dipesh.auctionapp.exception;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AuctionItemsFetchFailException extends Exception {
    public AuctionItemsFetchFailException(String detailMessage) {
        super(detailMessage);
    }
}
