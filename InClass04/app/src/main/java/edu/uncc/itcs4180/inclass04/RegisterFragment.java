package edu.uncc.itcs4180.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    EditText registerNameEditText;
    EditText registerEmailEditText;
    EditText registerPasswordEditText;
    Button registerSubmitButton;
    Button registerCancelButton;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize globals
        registerNameEditText = view.findViewById(R.id.registerNameEditText);
        registerEmailEditText = view.findViewById(R.id.registerEmailEditText);
        registerPasswordEditText = view.findViewById(R.id.registerPasswordEditText);
        registerSubmitButton = view.findViewById(R.id.registerSubmitButton);
        registerCancelButton = view.findViewById(R.id.registerCancelButton);

        // On Submit button click
        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerNameEditText.getText().toString().isEmpty()
                        || registerEmailEditText.getText().toString().isEmpty()
                        || registerPasswordEditText.getText().toString().isEmpty()
                        || !(registerEmailEditText.getText().toString().contains("@"))) {
                    Toast.makeText(getActivity(), "Error! Check inputs!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    DataServices.register(
                            registerNameEditText.getText().toString(),
                            registerEmailEditText.getText().toString(),
                            registerPasswordEditText.getText().toString()
                    );
                    Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_SHORT)
                            .show();
                    mListener.setCurrentFragment(0);
                }
            }
        });

        // On Cancel button click
        registerCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(0);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof LoginFragment.IListener) {
            mListener = (LoginFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    LoginFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
    }
}