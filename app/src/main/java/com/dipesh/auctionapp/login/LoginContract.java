package com.dipesh.auctionapp.login;

import com.dipesh.auctionapp.data.User;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public interface LoginContract {

    interface View{
        void showLoginError(String message);

        void showViewOnLoginSuccessFul(User user);

        void showUserNameEmptyErrorView();

        void showPasswordEmptyErrorView();

        void onUserLoggedIn(User user);

        void onUserNotLoggedIn();
    }

    interface InteractionListener{
        void login(String userName, char[] password);

        void checkIfSessionRunning();
    }
}
