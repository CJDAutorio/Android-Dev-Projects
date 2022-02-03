package edu.uncc.itcs4180.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {
    final String TAG = "LoginFragment";
    EditText loginEmailEditText;
    EditText loginPasswordEditText;
    Button loginButton;
    Button createAccountButton;

    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(DataServices.Account account) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);
        fragment.setArguments(args);
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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize globals
        loginEmailEditText = getActivity().findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = getActivity().findViewById(R.id.loginPasswordEditText);
        loginButton = getActivity().findViewById(R.id.loginButton);
        createAccountButton = getActivity().findViewById(R.id.createAccountButton);

        // on loginButton press
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginEmailEditText.getText().toString().isEmpty()
                        || loginPasswordEditText.getText().toString().isEmpty()) {
                    // TODO: Make Toast message if email/password are empty
                } else {
                    loginAccount();
                }
            }
        });
    }

    void loginAccount() {
        DataServices.AccountRequestTask task = DataServices.login(
                loginEmailEditText.getText().toString(),
                loginPasswordEditText.getText().toString());
        if (task.isSuccessful()){ //successful
            DataServices.Account account = task.getAccount();
            mListener.setAccount(account);
            Log.d(TAG, "setAccount: name: " + account.getName());
        } else { //not successful
            String error = task.getErrorMessage();
            // TODO: Make Toast message after login error
            //Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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
    }
}