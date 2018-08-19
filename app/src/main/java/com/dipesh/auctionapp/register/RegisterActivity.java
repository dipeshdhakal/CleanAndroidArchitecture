package com.dipesh.auctionapp.register;

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
import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.data.RepositoryImpl;
import com.dipesh.auctionapp.data.User;
import com.dipesh.auctionapp.login.LoginActivity;


/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterContract.View {
    private EditText etRegisterUserName;
    private EditText etRegisterFullName;
    private EditText etRegisterPassword;
    private Button btnRegister;
    private RegisterContract.InteractionListener interactionListener;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactionListener = new RegisterPresenter(this, RepositoryImpl.getInstance());
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI() {
        etRegisterUserName = (EditText) findViewById(R.id.etRegisterUserName);
        etRegisterFullName = (EditText) findViewById(R.id.etRegisterFullName);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                interactionListener.register(createUserFromForm(), etRegisterPassword.getText().toString().toCharArray());
                break;
            case R.id.btnLogin:
                LoginActivity.start(this);
                finish();
                break;
        }
    }

    private User createUserFromForm() {
        User user = new User();
        user.setUserName(etRegisterUserName.getText().toString().trim());
        user.setFullName(etRegisterFullName.getText().toString().trim());
        return user;
    }

    @Override
    public void showViewOnUserNameEmpty() {
        etRegisterUserName.setError(getString(R.string.text_username_empty_error));
    }

    @Override
    public void showViewOnPasswordEmpty() {
        etRegisterPassword.setError(getString(R.string.text_password_empty_error));
    }

    @Override
    public void showViewOnFullNameEmpty() {
        etRegisterFullName.setError(getString(R.string.text_full_name_empty_error));
    }

    @Override
    public void showRegistrationErrorView(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegistrationSuccessView(User user) {
        Toast.makeText(this, getString(R.string.text_registration_successful_message), Toast.LENGTH_LONG).show();
        finish();
        MainActivity.start(this);
    }

    public static void start(Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }
}
