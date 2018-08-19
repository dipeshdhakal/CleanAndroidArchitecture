package com.dipesh.auctionapp.data;

import com.dipesh.auctionapp.exception.RegistrationFailedException;
import com.dipesh.auctionapp.exception.AuctionItemAddException;
import com.dipesh.auctionapp.exception.AuctionItemsFetchFailException;
import com.dipesh.auctionapp.exception.BidAddException;
import com.dipesh.auctionapp.exception.LoginErrorException;
import com.dipesh.auctionapp.exception.WonItemFetchFailException;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface Repository {

    interface RegisterUserCallback{

        void onRegistrationSuccessful(User user);

        void onRegistrationError(RegistrationFailedException e);

    }

    interface LoginUserCallback{

        void onLoginSuccessful(User user);
        void onLoginError(LoginErrorException e);
    }
    interface FetchAuctionItemsCallback{

        void onAuctionItemSuccessfullyFetched(List<AuctionItem> auctionItemList);
        void onAuctionItemFetchFail(AuctionItemsFetchFailException e);
    }
    interface FetchWonItemsCallback{

        void onWonItemSuccessfullyFetched(List<AuctionItem> wonItemList);
        void onWonItemFetchFail(WonItemFetchFailException e);
    }
    interface AddBidCallback{

        void onBidSuccessfullyAdded(Bid bid);
        void onBidAddError(BidAddException e);
    }

    interface AddAuctionItemCallback{
        void onAuctionItemSuccessfullyAdded(AuctionItem item);
        void onAuctionItemAddError(AuctionItemAddException e);
    }

    interface SessionActiveCheckCallback{
        void onSessionActive(User user);
        void onSessionInActive();

    }
    void login(User user, char[] password, LoginUserCallback loginUserCallback);
    void register(User user, char[] password, RegisterUserCallback registerUserCallback);
    void fetchWonItemList(FetchWonItemsCallback fetchWonItemsCallback);
    void fetchAuctionItemList(FetchAuctionItemsCallback fetchAuctionItemsCallback);
    void fetchAuctionItemList(User user, FetchAuctionItemsCallback fetchAuctionItemsCallback);

    void bid(AuctionItem auctionItem, float amount, AddBidCallback addBidCallback);

    void bid(AuctionItem auctionItem, float amount, User user, AddBidCallback addBidCallback);

    void addAuctionItem(AuctionItem auctionItem, AddAuctionItemCallback addAuctionItemCallback);

    void checkIfSessionIsActive(SessionActiveCheckCallback checkCallback);

    User getActiveUser();

    User getBot();

    void clearSession();

}

