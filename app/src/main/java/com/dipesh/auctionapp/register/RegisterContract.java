package com.dipesh.auctionapp.register;

import com.dipesh.auctionapp.data.User;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface RegisterContract {
    interface View{
        void showViewOnUserNameEmpty();

        void showViewOnPasswordEmpty();

        void showViewOnFullNameEmpty();

        void showRegistrationErrorView(String message);

        void showRegistrationSuccessView(User user);
    }

    interface InteractionListener{
        void register(User user, char[] password);

    }
}
