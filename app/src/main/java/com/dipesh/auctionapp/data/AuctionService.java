package com.dipesh.auctionapp.data;

import java.util.List;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface AuctionService {

    List<AuctionItem> getAllAuctionItems();

    List<AuctionItem> getAllAuctionItems(User user);

    List<AuctionItem> getAllOwnedItemsByUser(User user);

    long addAuctionItem(AuctionItem auctionItem);

    long bidForAuctionItem(AuctionItem auctionItem, float bid, User bidBy);

    long registerUser(User user, char[] password);

    boolean validateLoginInfo(String userName, char[] password);

    User getUserByID(long id);

    User generateAuthTokenForUser(User user);

    void saveAuthTokenToSharedPref(String authToken);

    String getAuthTokenFromSharedPref();

    User getActiveUser();

    User getBot();

    void clearSession();
}
