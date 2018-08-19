package com.sushant.auctionapp.addauctionitem;

import com.dipesh.auctionapp.addauctionitem.AddAuctionItemContract;
import com.dipesh.auctionapp.addauctionitem.AddAuctionItemPresenter;
import com.dipesh.auctionapp.data.AuctionItem;
import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.AuctionItemAddException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AddAuctionItemPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private AddAuctionItemContract.View addAuctionItemView;

    @Captor
    private ArgumentCaptor<Repository.AddAuctionItemCallback> addAuctionItemCallbackArgumentCaptor;

    private AddAuctionItemPresenter addAuctionItemPresenter;

    @Before
    public void setupAddAuctionItemPresenter(){
        MockitoAnnotations.initMocks(this);

        addAuctionItemPresenter = new AddAuctionItemPresenter(addAuctionItemView, repository);
    }

    @Test
    public void invalidNameTest(){
        AuctionItem auctionItem = createDummyAuctionItem();
        auctionItem.setName("");
        addAuctionItemPresenter.add(auctionItem);

        verify(addAuctionItemView).showInvalidNameError();

    }

    @Test
    public void addAuctionItemErrorTest(){
        AuctionItem auctionItem = createDummyAuctionItem();
        addAuctionItemPresenter.add(auctionItem);
        verify(repository).addAuctionItem(eq(auctionItem), addAuctionItemCallbackArgumentCaptor.capture());
        addAuctionItemCallbackArgumentCaptor.getValue().onAuctionItemAddError(new AuctionItemAddException("Error adding auction item"));
        verify(addAuctionItemView).showAddAuctionItemError("Error adding auction item");
    }

    @Test
    public void addAuctionItemSuccessTest(){
        AuctionItem auctionItem = createDummyAuctionItem();
        addAuctionItemPresenter.add(auctionItem);
        verify(repository).addAuctionItem(eq(auctionItem), addAuctionItemCallbackArgumentCaptor.capture());
        addAuctionItemCallbackArgumentCaptor.getValue().onAuctionItemSuccessfullyAdded(any(AuctionItem.class));
        verify(addAuctionItemView).showAddAuctionItemSuccessView(any(AuctionItem.class));
    }

    private AuctionItem createDummyAuctionItem() {

        AuctionItem auctionItem = new AuctionItem();
        User postedUser = new User();
        postedUser.setFullName("Jack Black");
        postedUser.setUserName("jackblack");
        auctionItem.setName("Item 1");
        auctionItem.setDescription("This is a description of item item1");
        auctionItem.setPostedUser(postedUser);
        auctionItem.setExpiryDateTime(new Date());
        auctionItem.setUniqueID(1234567890);
        return auctionItem;
    }
}
