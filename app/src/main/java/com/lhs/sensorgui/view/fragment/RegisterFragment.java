package com.lhs.sensorgui.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.TorqueApp;
import com.lhs.sensorgui.app.model.User;
import com.lhs.sensorgui.view.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Dragan on 9/29/2016.
 */
public class RegisterFragment extends Fragment {

    private Button btnRegister;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private ProgressDialog progressDialog;

    public RegisterFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RegisterFragment newInstance(int sectionNumber) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        etFirstName = (EditText) rootView.findViewById(R.id.etRegisterFirstName);
        etLastName = (EditText) rootView.findViewById(R.id.etRegisterLastName);
        etUsername = (EditText) rootView.findViewById(R.id.etRegisterUsername);
        etEmail = (EditText) rootView.findViewById(R.id.etRegisteEmail);
        etPassword = (EditText) rootView.findViewById(R.id.etRegisterPassword);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etRegisterConfirmPassword);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegisterRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmedPassword = etConfirmPassword.getText().toString();

                // check input data, if ok, register user
                if((!"".equals(username)
                            && !"".equals(firstName)
                            && !"".equals(lastName)
                            && !"".equals(email)
                            && !"".equals(password)
                            && !"".equals(confirmedPassword))
                        && password.equals(confirmedPassword)){
                    RegisterAsyncTask rat = new RegisterAsyncTask();
                    rat.execute(username, firstName, lastName, email, password);
                }

                User user = new User();
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Registering...");
        progressDialog.hide();

        return rootView;
    }

    private class RegisterAsyncTask extends AsyncTask<String, Void, User> {

        private String url = "www.probiker.com/api/register/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected User doInBackground(String params[]) {
            String username = params[0];
            String firstName = params[1];
            String lastName = params[2];
            String email = params[3];
            String password = params[4];
            // attempt registration

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);

            try {
                String result = registerUser(user);
                JSONObject json = new JSONObject(result);
                String status = json.getString("result");
                if ("OK".equals(status)) {
                    return user;
                } else {
                    return null;
                }
            }catch(Exception e){
                Log.d("EXCEPTION", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(User user) {
            //app().setForecast(result);
            progressDialog.hide();
            if(user != null) {
                // update user in application
                app().registerUser(user);
                app().loginUser(user);
                // TODO check if user entered data, if not, redirect to data entry activity

                // else
                // start main activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }else{
                Snackbar.make(etUsername,
                        "Registration failed!",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

        // Given a URL, establishes an HttpUrlConnection and retrieves
        // the web page content as a InputStream, which it returns as
        // a string.
        private String registerUser(User user) throws IOException {
            InputStream is = null;

            try {
                /* URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);    // miliseconds
                conn.setConnectTimeout(15000); // miliseconds
                conn.setRequestMethod(REQUEST_METHOD);
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                return contentAsString; */
                //if(user.getUsername().equals("zile92")) {
                    return "{\"result\":\"OK\"}";
                //}
                //else{
                 //   return "{\"result\":\"NOK\"}";
                //}
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(stream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            stream.close();
            return result;
        }
    }

    private TorqueApp app(){
        return TorqueApp.getInstance(getContext());
    }
}
