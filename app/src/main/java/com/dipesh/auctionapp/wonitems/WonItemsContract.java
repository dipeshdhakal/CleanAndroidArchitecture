package com.dipesh.auctionapp.wonitems;

import com.dipesh.auctionapp.data.AuctionItem;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface WonItemsContract {

    interface View{

        void onWonItemListFetched(List<AuctionItem> wonItemList);

        void onWonItemsFetchFail(String message);
    }

    interface InteractionListener{
        void fetchWonItemList();
    }
}
