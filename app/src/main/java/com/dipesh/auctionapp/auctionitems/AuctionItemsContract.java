package com.dipesh.auctionapp.auctionitems;

import com.dipesh.auctionapp.data.AuctionItem;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface AuctionItemsContract {

    interface View{

        void onAuctionItemsFetched(List<AuctionItem> auctionItemList);

        void onAuctionItemsFetchFail(String message);
    }

    interface InteractionListener{
        void fetchAuctionItemsList();
    }
}
