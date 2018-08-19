package com.dipesh.auctionapp.addbid;

import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Bid;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface AddBidContract {

    interface View{

        void showNotAValidNumberView();

        void showBidAddError(String message);

        void showBidSuccessfullyAddedView(Bid bid);
    }

    interface InteractionListener{
         void bid(AuctionItem auctionItem, String bidAmount);
    }
}
