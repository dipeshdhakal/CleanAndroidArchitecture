package com.sushant.auctionapp.addbid;

import com.dipesh.auctionapp.addbid.AddBidContract;
import com.dipesh.auctionapp.addbid.AddBidPresenter;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Bid;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.BidAddException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;


/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AddBidPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private AddBidContract.View addBidView;

    @Captor
    private ArgumentCaptor<Repository.AddBidCallback> addBidCallbackArgumentCaptor;
    private AddBidPresenter addBidPresenter;

    @Before
    public void setupAddBidPresenter(){
        MockitoAnnotations.initMocks(this);

        addBidPresenter = new AddBidPresenter(addBidView, repository);
    }

    @Test
    public void invalidBidAmountTest(){
        addBidPresenter.bid(new AuctionItem(), "");

        verify(addBidView).showNotAValidNumberView();
    }

    @Test
    public void addBidErrorTest(){
        AuctionItem auctionItem = new AuctionItem();
        addBidPresenter.bid(auctionItem, "1234");

        verify(repository).bid(eq(auctionItem), eq(1234f), addBidCallbackArgumentCaptor.capture());

        addBidCallbackArgumentCaptor.getValue().onBidAddError(new BidAddException("Bid add error"));

        verify(addBidView).showBidAddError("Bid add error");
    }

    @Test
    public void addBidSuccessfulTest(){
        AuctionItem auctionItem = new AuctionItem();
        addBidPresenter.bid(auctionItem, "1234");

        verify(repository).bid(eq(auctionItem), eq(1234f), addBidCallbackArgumentCaptor.capture());

        addBidCallbackArgumentCaptor.getValue().onBidSuccessfullyAdded(any(Bid.class));

        verify(addBidView).showBidSuccessfullyAddedView(any(Bid.class));

    }
}
