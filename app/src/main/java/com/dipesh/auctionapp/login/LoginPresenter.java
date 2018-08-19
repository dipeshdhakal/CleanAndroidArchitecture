package com.dipesh.auctionapp.login;

import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.ErrorMessageFactory;
import com.dipesh.auctionapp.exception.LoginErrorException;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class LoginPresenter implements LoginContract.InteractionListener{

    private Repository repository;
    private LoginContract.View view;

    public LoginPresenter(Repository repository,
                          LoginContract.View view){

        this.repository = repository;
        this.view = view;
    }

    @Override
    public void login(String userName, char[] password) {
        if(userName.isEmpty()){
            view.showUserNameEmptyErrorView();
            return;
        }

        if(password.length == 0){
            view.showPasswordEmptyErrorView();
            return;
        }

        User user = new User();
        user.setUserName(userName);
        repository.login(user, password, new Repository.LoginUserCallback() {
            @Override
            public void onLoginSuccessful(User user) {
                view.showViewOnLoginSuccessFul(user);
            }

            @Override
            public void onLoginError(LoginErrorException e) {
                view.showLoginError(ErrorMessageFactory.createMessage(e));
            }
        });
    }

    @Override
    public void checkIfSessionRunning() {
        repository.checkIfSessionIsActive(new Repository.SessionActiveCheckCallback() {
            @Override
            public void onSessionActive(User user) {
                view.onUserLoggedIn(user);
            }

            @Override
            public void onSessionInActive() {
                view.onUserNotLoggedIn();
            }
        });
    }
}
