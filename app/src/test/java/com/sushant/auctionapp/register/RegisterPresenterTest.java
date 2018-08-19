package com.sushant.auctionapp.register;

import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.RegistrationFailedException;
import com.dipesh.auctionapp.register.RegisterContract;
import com.dipesh.auctionapp.register.RegisterPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class RegisterPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private RegisterContract.View registerView;

    private RegisterPresenter registerPresenter;

    @Captor
    private ArgumentCaptor<Repository.RegisterUserCallback> registerUserCallbackArgumentCaptor;

    @Before
    public void setupRegisterPresenter(){
        MockitoAnnotations.initMocks(this);

        registerPresenter = new RegisterPresenter(registerView, repository);
    }

    @Test
    public void register_showErrorViewOnEmptyUserName(){
        String userName = "";
        char[] password = "test".toCharArray();
        String fullName = "Jack Black";

        User user = new User();
        user.setUserName(userName);
        user.setFullName(fullName);

        registerPresenter.register(user, password);

        verify(registerView).showViewOnUserNameEmpty();
    }

    @Test
    public void register_showErrorViewOnEmptyFullName(){
        String userName = "jackblack";
        char[] password = "test".toCharArray();
        String fullName = "";

        User user = new User();
        user.setUserName(userName);
        user.setFullName(fullName);

        registerPresenter.register(user, password);

        verify(registerView).showViewOnFullNameEmpty();
    }

    @Test
    public void register_showErrorViewOnEmptyPassword(){
        User user = createDummyUser();

        registerPresenter.register(user, "".toCharArray());
        verify(registerView).showViewOnPasswordEmpty();
    }

    @Test
    public void register_invalidRegistrationShowError(){
        User user = createDummyUser();
        char[] password = "test".toCharArray();

        registerPresenter.register(user, password);
        verify(repository).register(any(User.class), any(char[].class), registerUserCallbackArgumentCaptor.capture());

        registerUserCallbackArgumentCaptor.getValue().onRegistrationError(new RegistrationFailedException("Registration failed"));

        verify(registerView).showRegistrationErrorView("Registration failed");
    }

    @Test
    public void register_ValidRegistrationShowSuccess(){
        User user = createDummyUser();
        char[] password = "test".toCharArray();

        registerPresenter.register(user, password);
        verify(repository).register(any(User.class), any(char[].class), registerUserCallbackArgumentCaptor.capture());

        registerUserCallbackArgumentCaptor.getValue().onRegistrationSuccessful(user);

        verify(registerView).showRegistrationSuccessView(user);
    }

    private User createDummyUser(){
        User user = new User();
        user.setFullName("Jack Black");
        user.setUserName("jackblack");

        return user;
    }
}
