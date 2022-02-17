package edu.uncc.itcs4180.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    final String TAG = "LoginFragment";
    EditText loginEmailEditText;
    EditText loginPasswordEditText;
    Button loginButton;
    Button createAccountButton;
    String email = "";
    String password = "";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize globals
        loginEmailEditText = view.findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = view.findViewById(R.id.loginPasswordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        createAccountButton = view.findViewById(R.id.createAccountButton);

        loginEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                email = loginEmailEditText.getText().toString();
                Log.d(TAG, "Email changed. Email: "
                        + email
                        + ", Pass: " + password);
            }
        });

        loginPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                password = loginPasswordEditText.getText().toString();
                Log.d(TAG, "Password changed. Email: "
                        + email
                        + ", Pass: " + password);
            }
        });

        // on loginButton press
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.isEmpty()
                        || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid credentials!", Toast.LENGTH_SHORT)
                            .show();
                    Log.d(TAG, "loginButton clicked, inputs invalid.");
                } else {
                    Log.d(TAG, "loginButton clicked. Email: "
                            + email
                            + ", Pass: " + password);
                    if (loginAccount()) {
                        mListener.setCurrentFragment(3);
                        Log.d(TAG, "loginButton.setOnClickListener: Set fragment, fragnum: "
                                + 3);
                    }
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(1);
            }
        });

        Log.d(TAG, "onViewCreated");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    boolean loginAccount() {
        DataServices.AccountRequestTask task = DataServices.login(
                loginEmailEditText.getText().toString(),
                loginPasswordEditText.getText().toString());
        if (task.isSuccessful()) { //successful
            DataServices.Account account = task.getAccount();
            mListener.setAccount(account);
            Log.d(TAG, "loginAccount: Login Successful, name: " + account.getName());
            return true;
        } else { //not successful
            String error = task.getErrorMessage();
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IListener) {
            mListener = (IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    IListener mListener;
    public interface IListener {
        void setAccount(DataServices.Account account);
        void setCurrentFragment(int fragment);
    }
}