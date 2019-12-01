package com.example.hkohli.shoppingapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, SenderInterface {


    public LoginFragment() {
        // Required empty public constructor
    }

    private EditText email, password;
    private Button loginButton;
    private String URL;

    private String getURL()
    {
        return getResources().getString(R.string.LoginPageURL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // Assigning the url
        URL = getURL();

        // Getting the id's
        email = (EditText)view.findViewById(R.id.login_fragment_email);
        password = (EditText)view.findViewById(R.id.login_fragment_password);
        loginButton = (Button)view.findViewById(R.id.login_fragment_loginbutton);

        loginButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == loginButton)
        {
            String emailString = email.getText().toString().trim();
            String passwodString = password.getText().toString().trim();

            if(emailString.equals("") || passwodString.equals(""))
            {
                Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches())
            {
                Toast.makeText(getContext(), "Enter a Valid Email", Toast.LENGTH_SHORT).show();
            }
            else if(password.length() <=5)
            {
                Toast.makeText(getContext(), "Password is too short", Toast.LENGTH_SHORT).show();
            }
            else
            {
                SenderManager senderManager = new SenderManager(getContext(),URL);
                senderManager.DELEGATE_RESPONSE = this;
                senderManager.execute("email",emailString,"password",passwodString);
            }
        }

    }

    @Override
    public void senderInterfaceResult(String output) {

        if(output != null){
            Toast.makeText(getContext(), ""+output, Toast.LENGTH_SHORT).show();
        }
    }
}
