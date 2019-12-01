package com.example.hkohli.shoppingapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener, SenderInterface {


    public SignupFragment() {
        // Required empty public constructor
    }

    EditText email,password,cpassword,mobile;
    Button signupButton;

    private String REGISTER_URL;

    private String getURL()
    {
        return getResources().getString(R.string.SignupPageURL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signup, container, false);

        // Getting the url
        REGISTER_URL = getURL();

        // Assigning the id's
        email = (EditText)view.findViewById(R.id.signup_fragment_email);
        password = (EditText)view.findViewById(R.id.signup_fragment_password);
        cpassword = (EditText)view.findViewById(R.id.signup_fragment_cpassword);
        mobile = (EditText)view.findViewById(R.id.signup_fragment_mobile);
        signupButton = (Button)view.findViewById(R.id.signup_fragment_signupButton);

        signupButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v  == signupButton)
        {

            String emailString = email.getText().toString().trim();
            String passwordString = password.getText().toString().trim();
            String cpasswodeString = cpassword.getText().toString().trim();
            String mobileString = mobile.getText().toString().trim();

            if(emailString.equals("") || passwordString.equals("") || cpasswodeString.equals("")
                    || mobileString.equals(""))
            {
                Toast.makeText(getContext(), "Please Fill All the fields", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches())
                Toast.makeText(getContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
            else if(passwordString.length() <=5)
                Toast.makeText(getContext(), "Password too short", Toast.LENGTH_SHORT).show();
            else if(!passwordString.equals(cpasswodeString))
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            else if(!Patterns.PHONE.matcher(mobileString).matches() || mobileString.length() != 10)
                Toast.makeText(getContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
            else
            {
                SenderManager senderManager = new SenderManager(getContext(),REGISTER_URL);
                senderManager.DELEGATE_RESPONSE = this;
                senderManager.execute("email",emailString,
                        "password",passwordString,
                        "mobile",mobileString);
            }

        }


    }

    @Override
    public void senderInterfaceResult(String output) {

        if(output != null)
        {
            if(output.trim().equals("inserted"))
            {
                Toast.makeText(getContext(), "SignUp Successfull", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), ""+output, Toast.LENGTH_SHORT).show();
        }

    }
}
