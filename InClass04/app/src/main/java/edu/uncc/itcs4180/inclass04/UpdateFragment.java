package edu.uncc.itcs4180.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateFragment extends Fragment {
    final static String ARG_ACCOUNT = "ARG_ACCOUNT";
    DataServices.Account currentAccount;
    TextView updateEmailLabel;
    EditText updateNameEditText;
    EditText updatePasswordEditText;
    Button updateSubmitButton;
    Button updateCancelButton;

    public UpdateFragment() {
        // Required empty public constructor
    }

    public static UpdateFragment newInstance(DataServices.Account account) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentAccount = (DataServices.Account) getArguments().getSerializable(ARG_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        // Initialize globals
        updateEmailLabel = view.findViewById(R.id.updateEmailLabel);
        updateNameEditText = view.findViewById(R.id.updateNameEditText);
        updatePasswordEditText = view.findViewById(R.id.updatePasswordEditText);
        updateSubmitButton = view.findViewById(R.id.updateSubmitButton);
        updateCancelButton = view.findViewById(R.id.updateCancelButton);

        updateEmailLabel.setText(currentAccount.getEmail());

        updateSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateNameEditText.getText().toString().isEmpty() ||
                updatePasswordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            "Please enter information, or click the cancel button",
                            Toast.LENGTH_SHORT).show();
                } else {
                    DataServices.update(currentAccount,
                            updateNameEditText.getText().toString(),
                            updatePasswordEditText.getText().toString());
                    Toast.makeText(getActivity(), "Account updated!", Toast.LENGTH_SHORT)
                            .show();
                    mListener.setCurrentFragment(3);
                }
            }
        });

        updateCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(3);
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