package com.dipesh.auctionapp.addauctionitem;

import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.AuctionItemAddException;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.exception.ErrorMessageFactory;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AddAuctionItemPresenter implements AddAuctionItemContract.InteractionListener{

    private AddAuctionItemContract.View view;
    private Repository repository;

    public AddAuctionItemPresenter(AddAuctionItemContract.View view, Repository repository){

        this.view = view;
        this.repository = repository;
    }

    @Override
    public void add(final AuctionItem auctionItem) {
        if(auctionItem.getName() == null || auctionItem.getName().isEmpty()){
            view.showInvalidNameError();
            return;
        }

        if(auctionItem.getExpiryDateTime() == null){
            view.showNotAValidDateTimeError();
            return;
        }

        repository.addAuctionItem(auctionItem, new Repository.AddAuctionItemCallback(){

            @Override
            public void onAuctionItemSuccessfullyAdded(AuctionItem item) {
                view.showAddAuctionItemSuccessView(auctionItem);
            }

            @Override
            public void onAuctionItemAddError(AuctionItemAddException e) {
                view.showAddAuctionItemError(ErrorMessageFactory.createMessage(e));
            }
        });
    }
}
