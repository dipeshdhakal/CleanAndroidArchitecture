package com.dipesh.auctionapp.data;

import com.dipesh.auctionapp.exception.AuctionItemAddException;
import com.dipesh.auctionapp.exception.AuctionItemsFetchFailException;
import com.dipesh.auctionapp.exception.RegistrationFailedException;
import com.dipesh.auctionapp.exception.WonItemFetchFailException;
import com.dipesh.auctionapp.exception.BidAddException;
import com.dipesh.auctionapp.exception.LoginErrorException;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class RepositoryImpl implements Repository {


    private AuctionService auctionService;

    private RepositoryImpl(){

    }

    private static RepositoryImpl repository = null;

    public static Repository getInstance(){
        if(repository == null){
            repository = new RepositoryImpl();
            repository.auctionService = DAO.getInstance();
        }

        return repository;
    }

    @Override
    public void login(User user, char[] password, LoginUserCallback loginUserCallback) {
        boolean isValid = auctionService.validateLoginInfo(user.getUserName(), password);
        if(isValid){
            User authenticatedUser = auctionService.generateAuthTokenForUser(user);
            auctionService.saveAuthTokenToSharedPref(authenticatedUser.getAuthToken());
            loginUserCallback.onLoginSuccessful(authenticatedUser);
        } else {
            loginUserCallback.onLoginError(new LoginErrorException("User not valid"));
        }
    }

    @Override
    public void register(User user, char[] password, RegisterUserCallback registerUserCallback) {
        if(auctionService.registerUser(user, password) != -1){
            User authenticatedUser = auctionService.generateAuthTokenForUser(user);
            auctionService.saveAuthTokenToSharedPref(authenticatedUser.getAuthToken());
            registerUserCallback.onRegistrationSuccessful(authenticatedUser);
        } else {
            registerUserCallback.onRegistrationError(new RegistrationFailedException("Registration error"));
        }

    }


    @Override
    public void fetchWonItemList(FetchWonItemsCallback fetchWonItemsCallback) {
        List<AuctionItem> wonItemList = auctionService.getAllOwnedItemsByUser(auctionService.getActiveUser());
        if(wonItemList != null && wonItemList.size() > 0){
            fetchWonItemsCallback.onWonItemSuccessfullyFetched(wonItemList);
        } else {
            fetchWonItemsCallback.onWonItemFetchFail(new WonItemFetchFailException("No won items exist"));
        }
    }

    @Override
    public void fetchAuctionItemList(FetchAuctionItemsCallback fetchAuctionItemsCallback) {
        List<AuctionItem> auctionItemList = auctionService.getAllAuctionItems();
        if(auctionItemList == null || auctionItemList.size() == 0){
            fetchAuctionItemsCallback.onAuctionItemFetchFail(new AuctionItemsFetchFailException("Auction items not found"));
        } else {
            fetchAuctionItemsCallback.onAuctionItemSuccessfullyFetched(auctionItemList);
        }
    }

    @Override
    public void fetchAuctionItemList(User user, FetchAuctionItemsCallback fetchAuctionItemsCallback) {
        List<AuctionItem> auctionItemList = auctionService.getAllAuctionItems(user);
        if(auctionItemList == null || auctionItemList.size() == 0){
            fetchAuctionItemsCallback.onAuctionItemFetchFail(new AuctionItemsFetchFailException("Auction items not found"));
        } else {
            fetchAuctionItemsCallback.onAuctionItemSuccessfullyFetched(auctionItemList);
        }
    }

    @Override
    public void bid(AuctionItem auctionItem, float amount, AddBidCallback addBidCallback) {
        if(auctionService.bidForAuctionItem(auctionItem, amount, auctionService.getActiveUser()) == -1){
            addBidCallback.onBidAddError(new BidAddException("Bid cannot be added"));
        } else {
            addBidCallback.onBidSuccessfullyAdded(null);
        }
    }

    @Override
    public void bid(AuctionItem auctionItem, float amount, User user, AddBidCallback addBidCallback) {
        if(auctionService.bidForAuctionItem(auctionItem, amount, user) == -1){
            addBidCallback.onBidAddError(new BidAddException("Bid cannot be added"));
        } else {
            addBidCallback.onBidSuccessfullyAdded(null);
        }
    }

    @Override
    public void addAuctionItem(AuctionItem auctionItem, AddAuctionItemCallback addAuctionItemCallback) {
        if(auctionItem.getPostedUser() == null) {
            auctionItem.setPostedUser(auctionService.getActiveUser());
        }
        if(auctionService.addAuctionItem(auctionItem) != -1){
            addAuctionItemCallback.onAuctionItemSuccessfullyAdded(auctionItem);
        } else {
            addAuctionItemCallback.onAuctionItemAddError(new AuctionItemAddException("Auction item cannot be added"));
        }
    }

    @Override
    public void checkIfSessionIsActive(SessionActiveCheckCallback checkCallback) {
        User authenticatedUser = auctionService.getActiveUser();
        if(authenticatedUser != null){
            checkCallback.onSessionActive(authenticatedUser);
        } else {
            checkCallback.onSessionInActive();
        }
    }

    @Override
    public User getActiveUser() {
        return auctionService.getActiveUser();
    }

    @Override
    public User getBot() {
        return auctionService.getBot();
    }

    @Override
    public void clearSession() {
        auctionService.clearSession();
    }
}
