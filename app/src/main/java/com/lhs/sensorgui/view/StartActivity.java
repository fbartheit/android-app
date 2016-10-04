package com.lhs.sensorgui.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.TorqueApp;
import com.lhs.sensorgui.db.DAO.UserDAO;
import com.lhs.sensorgui.db.UserContract;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sp = getSharedPreferences(UserDAO.USER_LOGIN_SP, MODE_PRIVATE);
        String username = sp.getString(UserContract.UserEntry.USER_USERNAME, null);

        if(username == null){ // start login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{ // start main activity for the user
            app().setUsername(username);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }
    private TorqueApp app(){
        return TorqueApp.getInstance(getApplicationContext());
    }

}
