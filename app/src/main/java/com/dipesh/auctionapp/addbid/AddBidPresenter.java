package com.dipesh.auctionapp.addbid;

import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.BidAddException;
import com.dipesh.auctionapp.util.NumberUtil;
import com.dipesh.auctionapp.data.Bid;
import com.dipesh.auctionapp.exception.ErrorMessageFactory;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AddBidPresenter implements AddBidContract.InteractionListener {

    private AddBidContract.View view;
    private Repository repository;

    public AddBidPresenter(AddBidContract.View view, Repository repository){
        this.view = view;

        this.repository = repository;
    }

    @Override
    public void bid(AuctionItem auctionItem, String bidAmount){
        float bidAmountInFloat;
        try{
            bidAmountInFloat = NumberUtil.convertStringToFloat(bidAmount);
        } catch (NumberFormatException e){
            view.showNotAValidNumberView();
            return;
        }
        repository.bid(auctionItem, bidAmountInFloat, new Repository.AddBidCallback() {
            @Override
            public void onBidSuccessfullyAdded(Bid bid) {
                view.showBidSuccessfullyAddedView(bid);
            }

            @Override
            public void onBidAddError(BidAddException e) {
                view.showBidAddError(ErrorMessageFactory.createMessage(e));
            }
        });
    }
}
