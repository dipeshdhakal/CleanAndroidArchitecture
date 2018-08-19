package com.sushant.auctionapp.login;

import com.dipesh.auctionapp.data.Repository;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.exception.LoginErrorException;
import com.dipesh.auctionapp.login.LoginContract;
import com.dipesh.auctionapp.login.LoginPresenter;

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
public class LoginPresenterTest {

    @Mock
    private Repository repository;

    @Mock
    private LoginContract.View loginView;

    private LoginPresenter loginPresenter;

    @Captor
    private ArgumentCaptor<Repository.LoginUserCallback> loginUserCallbackArgumentCaptor;

    @Before
    public void setupLoginPresenter(){
        MockitoAnnotations.initMocks(this);

        loginPresenter = new LoginPresenter(repository, loginView);
    }

    @Test
    public void login_emptyUserNameShowsErrorUI(){
        loginPresenter.login("",new char[0]);

        verify(loginView).showUserNameEmptyErrorView();
    }

    @Test
    public void login_emptyPasswordShowsErrorUI(){
        loginPresenter.login("jackblack", new char[0]);

        verify(loginView).showPasswordEmptyErrorView();
    }

    @Test
    public void login_InvalidLoginShowErrorUI(){

        String userName = "jackblack";
        char[] password = "test".toCharArray();

        loginPresenter.login(userName, password);
        verify(repository).login(any(User.class), any(char[].class), loginUserCallbackArgumentCaptor.capture());

        loginUserCallbackArgumentCaptor.getValue().onLoginError(new LoginErrorException("Login failed"));

        verify(loginView).showLoginError("Login failed");
    }

    @Test
    public void login_ValidLoginShowSuccessUI(){
        String userName = "jackblack";
        char[] password = "test".toCharArray();

        loginPresenter.login(userName, password);

        verify(repository).login(any(User.class), any(char[].class), loginUserCallbackArgumentCaptor.capture());

        User user = new User();
        loginUserCallbackArgumentCaptor.getValue().onLoginSuccessful(user);

        verify(loginView).showViewOnLoginSuccessFul(user);
    }
}
