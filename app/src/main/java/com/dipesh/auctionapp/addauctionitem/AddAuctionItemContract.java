package com.dipesh.auctionapp.addauctionitem;

import com.dipesh.auctionapp.data.AuctionItem;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface AddAuctionItemContract {
    interface View{

        void showInvalidNameError();

        void showAddAuctionItemSuccessView(AuctionItem auctionItem);

        void showAddAuctionItemError(String message);

        void showNotAValidDateTimeError();
    }

    interface InteractionListener{

        void add(AuctionItem auctionItem);
    }
}
