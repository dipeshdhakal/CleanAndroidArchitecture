package com.sushant.auctionapp.auctionitems;

import com.dipesh.auctionapp.auctionitems.AuctionItemsContract;
import com.dipesh.auctionapp.auctionitems.AuctionItemsPresenter;
import com.google.common.collect.Lists;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.AuctionItemsFetchFailException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AuctionItemsPresenterTest {

    private List<AuctionItem> AUCTION_ITEM_LIST = Lists.newArrayList(new AuctionItem(), new AuctionItem(), new AuctionItem());

    @Mock
    private Repository repository;

    @Mock
    private AuctionItemsContract.View auctionItemsView;

    @Captor
    private ArgumentCaptor<Repository.FetchAuctionItemsCallback> fetchAuctionItemsCallbackArgumentCaptor;

    private AuctionItemsPresenter auctionItemsPresenter;
    @Before
    public void setupWonItemsPresenter(){
        MockitoAnnotations.initMocks(this);

        auctionItemsPresenter = new AuctionItemsPresenter(auctionItemsView, repository);
    }

    @Test
    public void fetchAuctionItemsFromRepositoryAndLoadIntoView(){
        auctionItemsPresenter.fetchAuctionItemsList();

        verify(repository).fetchAuctionItemList(any(User.class),fetchAuctionItemsCallbackArgumentCaptor.capture());

        fetchAuctionItemsCallbackArgumentCaptor.getValue().onAuctionItemSuccessfullyFetched(AUCTION_ITEM_LIST);

        verify(auctionItemsView).onAuctionItemsFetched(AUCTION_ITEM_LIST);
    }

    @Test
    public void fetchAuctionItemsFromRepositoryError(){
        auctionItemsPresenter.fetchAuctionItemsList();

        verify(repository).fetchAuctionItemList(any(User.class), fetchAuctionItemsCallbackArgumentCaptor.capture());

        fetchAuctionItemsCallbackArgumentCaptor.getValue().onAuctionItemFetchFail(new AuctionItemsFetchFailException("Auction items fetch fail"));

        verify(auctionItemsView).onAuctionItemsFetchFail("Auction items fetch fail");
    }
}
