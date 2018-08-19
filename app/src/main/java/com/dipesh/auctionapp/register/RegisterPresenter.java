package com.dipesh.auctionapp.register;

import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.exception.RegistrationFailedException;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.ErrorMessageFactory;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class RegisterPresenter implements RegisterContract.InteractionListener{

    private RegisterContract.View view;
    private Repository repository;

    public RegisterPresenter(RegisterContract.View view,
                             Repository repository){

        this.view = view;
        this.repository = repository;
    }


    @Override
    public void register(User user, char[] password) {
        if(user.getUserName().isEmpty()){
            view.showViewOnUserNameEmpty();
            return;
        }

        if(user.getFullName().isEmpty()){
            view.showViewOnFullNameEmpty();
            return;
        }

        if(password.length == 0){
            view.showViewOnPasswordEmpty();
            return;
        }

        repository.register(user, password, new Repository.RegisterUserCallback() {
            @Override
            public void onRegistrationSuccessful(User user) {
                view.showRegistrationSuccessView(user);
            }

            @Override
            public void onRegistrationError(RegistrationFailedException e) {
                view.showRegistrationErrorView(ErrorMessageFactory.createMessage(e));
            }
        });
    }
}
