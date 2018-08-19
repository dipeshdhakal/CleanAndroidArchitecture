package com.dipesh.auctionapp.wonitems;

import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.WonItemFetchFailException;
import com.dipesh.auctionapp.exception.ErrorMessageFactory;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class WonItemsPresenter implements WonItemsContract.InteractionListener {

    private WonItemsContract.View view;
    private Repository repository;

    public WonItemsPresenter(WonItemsContract.View view, Repository repository){

        this.view = view;
        this.repository = repository;
    }


    @Override
    public void fetchWonItemList() {
        repository.fetchWonItemList(new Repository.FetchWonItemsCallback(){

            @Override
            public void onWonItemSuccessfullyFetched(List<AuctionItem> wonItemList) {
                view.onWonItemListFetched(wonItemList);
            }

            @Override
            public void onWonItemFetchFail(WonItemFetchFailException e) {
                view.onWonItemsFetchFail(ErrorMessageFactory.createMessage(e));
            }
        });
    }
}
