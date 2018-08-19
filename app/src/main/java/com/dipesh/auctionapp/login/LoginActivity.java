package com.dipesh.auctionapp.login;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dipesh.auctionapp.MainActivity;
import com.dipesh.auctionapp.bot.BotService;
import com.dipesh.auctionapp.data.RepositoryImpl;
import com.dipesh.auctionapp.register.RegisterActivity;
import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.User;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {

    private EditText etLoginUserName;
    private EditText etLoginPassword;
    private Button btnLogin;
    private Button btnRegister;
    private LoginContract.InteractionListener interactionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isBotServiceRunning())
            startService(new Intent(this, BotService.class));
        interactionListener = new LoginPresenter(RepositoryImpl.getInstance(), this);
        interactionListener.checkIfSessionRunning();
    }


    private void initUI() {
        etLoginUserName = (EditText) findViewById(R.id.etLoginUserName);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showViewOnLoginSuccessFul(User user) {
        Toast.makeText(LoginActivity.this, getString(R.string.text_succesfully_logged_in), Toast.LENGTH_SHORT).show();
        finish();
        MainActivity.start(this);
    }

    @Override
    public void showUserNameEmptyErrorView() {
        etLoginUserName.setError(getString(R.string.text_username_empty_error));
    }

    @Override
    public void showPasswordEmptyErrorView() {
        etLoginPassword.setError(getString(R.string.text_password_empty_error));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                interactionListener.login(etLoginUserName.getText().toString().trim(), etLoginPassword.getText().toString().toCharArray());
                break;
            case R.id.btnRegister:
                RegisterActivity.start(this);
                finish();
                break;
        }
    }

    @Override
    public void onUserLoggedIn(User user) {
        MainActivity.start(this);
        finish();
    }

    @Override
    public void onUserNotLoggedIn() {
        setContentView(R.layout.activity_login);
        initUI();
    }

    private boolean isBotServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (BotService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
