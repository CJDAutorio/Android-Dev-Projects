package edu.uncc.itcs4180.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private static final String ARG_ACCOUNT = "ARG_ACCOUNT";
    DataServices.Account currentAccount;
    TextView accountNameText;
    Button accountEditButton;
    Button accountLogOutButton;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(DataServices.Account account) {
        AccountFragment fragment = new AccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize globals
        accountNameText = view.findViewById(R.id.accountNameText);
        accountNameText.setText(currentAccount.getName());
        accountEditButton = view.findViewById(R.id.accountEditButton);
        accountLogOutButton = view.findViewById(R.id.accountLogOutButton);

        accountEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(2);
            }
        });

        accountLogOutButton.setOnClickListener(new View.OnClickListener() {
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