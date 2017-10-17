package com.example.android.thecats;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by fbrsw on 16.10.2017.
 */

public class FBActivity extends FragmentActivity {
    CallbackManager callbackManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/*
        callbackManager = CallbackManager.Factory.create();

        callbackManager.onActivityResult();

        LoginButton loginButton = (LoginButton) findViewById(R.id.usersettings_fragment_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        callbackManager.onActivityResult();*/
    }

}
