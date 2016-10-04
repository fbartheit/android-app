package com.lhs.sensorgui.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.TorqueApp;
import com.lhs.sensorgui.app.model.User;
import com.lhs.sensorgui.view.MainActivity;

/**
 * Created by Dragan on 9/29/2016.
 */
public class LoginFragment extends Fragment {

    private Button btnLogin;
    private EditText etLoginUsername;
    private EditText etLoginPassword;

    private ProgressDialog progressDialog;

    public LoginFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LoginFragment newInstance(int sectionNumber) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Logging in...");
        progressDialog.hide();
        
        etLoginUsername = (EditText) rootView.findViewById(R.id.etLoginUsername);
        etLoginPassword = (EditText) rootView.findViewById(R.id.etLoginPassword);

        btnLogin = (Button) rootView.findViewById(R.id.btnLoginLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                if(!("".equals(username)) && !("".equals(password))){
                    LoginAsyncTask lat = new LoginAsyncTask();
                    lat.execute(username, password);
                }else{
                    Snackbar.make(etLoginUsername,
                            "Username and password must not be empty!",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        return rootView;
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, User>{

        private String url = "www.probiker.com/api/login/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected User doInBackground(String params[]) {
            String username = params[0];
            String password = params[1];
            // check login in database, if false, then check on server
            User user = app().checkUserLogin(username, password);
            return user;
        }
        @Override
        protected void onPostExecute(User user) {
            //app().setForecast(result);
            //progressDialog.hide();
            if(user != null) {
                // update user in application
                app().loginUser(user);

                // start main activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }else{
                Snackbar.make(etLoginUsername,
                        "Wrong username or password!",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

    }
    private TorqueApp app(){
        return TorqueApp.getInstance(getContext());
    }
}
