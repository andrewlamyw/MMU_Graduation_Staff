package com.lamyatweng.mmugraduationstaff;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_register);
        final TextInputLayout emailWrapper = (TextInputLayout) rootView.findViewById(R.id.wrapper_email);
        final TextInputLayout passwordWrapper = (TextInputLayout) rootView.findViewById(R.id.wrapper_password);
        final TextInputLayout verifyPasswordWrapper = (TextInputLayout) rootView.findViewById(R.id.wrapper_verify_password);
        Button button = (Button) rootView.findViewById(R.id.button_create);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String email = emailWrapper.getEditText().getText().toString();
                final String password = passwordWrapper.getEditText().getText().toString();
                String verifyPassword = verifyPasswordWrapper.getEditText().getText().toString();

                if (!validateEmail(email)) {
                    emailWrapper.setError("Not a valid email address!");
                    passwordWrapper.setErrorEnabled(false);
                    verifyPasswordWrapper.setErrorEnabled(false);
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                    emailWrapper.setErrorEnabled(false);
                    verifyPasswordWrapper.setErrorEnabled(false);
                } else if (!verifyPassword.equals(password)) {
                    verifyPasswordWrapper.setError("Password does not match!");
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                } else {
                    // Email & password is in proper format
                    progressBar.setVisibility(View.VISIBLE);
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    verifyPasswordWrapper.setErrorEnabled(false);

                    Constants.FIREBASE_REF_STAFF.createUser(email, password, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Successfully created user account", Toast.LENGTH_LONG).show();

                            emailWrapper.getEditText().setText("");
                            passwordWrapper.getEditText().setText("");
                            verifyPasswordWrapper.getEditText().setText("");
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            progressBar.setVisibility(View.GONE);
                            switch (firebaseError.getCode()) {
                                case FirebaseError.EMAIL_TAKEN:
                                    Toast.makeText(getActivity(), "The new user account cannot be created because the specified email address is already in use.", Toast.LENGTH_LONG).show();
                                    break;
                                case FirebaseError.INVALID_EMAIL:
                                    Toast.makeText(getActivity(), "The specified email is not a valid email.", Toast.LENGTH_LONG).show();
                                    break;
                                case FirebaseError.NETWORK_ERROR:
                                    Toast.makeText(getActivity(), "An error occurred while attempting to contact the authentication server.", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "An unknown error occurred. Please refer to the error message and error details for more information.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });

        return rootView;
    }

    public boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 4;
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("New Staff " + Constants.TITLE_ACCOUNT);
    }
}