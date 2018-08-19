package com.dipesh.auctionapp.auctionitems;

import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.AuctionItemsFetchFailException;
import com.dipesh.auctionapp.exception.ErrorMessageFactory;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AuctionItemsPresenter implements AuctionItemsContract.InteractionListener {

    private AuctionItemsContract.View view;
    private Repository repository;

    public AuctionItemsPresenter(AuctionItemsContract.View view, Repository repository){

        this.view = view;
        this.repository = repository;
    }


    @Override
    public void fetchAuctionItemsList() {
        repository.fetchAuctionItemList(repository.getActiveUser(), new Repository.FetchAuctionItemsCallback(){

            @Override
            public void onAuctionItemSuccessfullyFetched(List<AuctionItem> auctionItemList) {
                view.onAuctionItemsFetched(auctionItemList);
            }

            @Override
            public void onAuctionItemFetchFail(AuctionItemsFetchFailException e) {
                view.onAuctionItemsFetchFail(ErrorMessageFactory.createMessage(e));
            }
        });
    }
}
