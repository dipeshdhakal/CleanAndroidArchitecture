package com.sushant.auctionapp.wonitems;

import com.dipesh.auctionapp.wonitems.WonItemsContract;
import com.dipesh.auctionapp.wonitems.WonItemsPresenter;
import com.google.common.collect.Lists;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.WonItemFetchFailException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class WonItemsPresenterTest {

    private List<AuctionItem> WON_ITEM_LIST = Lists.newArrayList(new AuctionItem(), new AuctionItem(), new AuctionItem());

    @Mock
    private Repository repository;

    @Mock
    private WonItemsContract.View wonItemsView;

    @Captor
    private ArgumentCaptor<Repository.FetchWonItemsCallback> fetchWonItemsCallbackArgumentCaptor;

    private WonItemsPresenter wonItemsPresenter;
    @Before
    public void setupWonItemsPresenter(){
        MockitoAnnotations.initMocks(this);

        wonItemsPresenter = new WonItemsPresenter(wonItemsView, repository);
    }

    @Test
    public void fetchWonItemsFromRepositoryAndLoadIntoView(){
        wonItemsPresenter.fetchWonItemList();

        verify(repository).fetchWonItemList(fetchWonItemsCallbackArgumentCaptor.capture());

        fetchWonItemsCallbackArgumentCaptor.getValue().onWonItemSuccessfullyFetched(WON_ITEM_LIST);

        verify(wonItemsView).onWonItemListFetched(WON_ITEM_LIST);
    }

    @Test
    public void fetchWonItemsFromRepositoryError(){
        wonItemsPresenter.fetchWonItemList();

        verify(repository).fetchWonItemList(fetchWonItemsCallbackArgumentCaptor.capture());

        fetchWonItemsCallbackArgumentCaptor.getValue().onWonItemFetchFail(new WonItemFetchFailException("Won items fetch fail"));

        verify(wonItemsView).onWonItemsFetchFail("Won items fetch fail");
    }
}
