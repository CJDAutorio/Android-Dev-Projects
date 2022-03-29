package edu.uncc.itcs4180.homework04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class UsersFragment extends Fragment {
    private final static String TAG = "homework04-UsersFragment";
    private final static String ARG_STATE = "ARG_STATE";
    private String currentState = "";
    private Button users_filterButton;
    private Button users_sortButton;
    private ListView users_listView;
    private UserAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance(String state) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentState = (String) getArguments().get(ARG_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        // Initialize globals
        users_filterButton = view.findViewById(R.id.users_filterButton);
        users_sortButton = view.findViewById(R.id.users_sortButton);
        users_listView = view.findViewById(R.id.users_listView);

        // Populate listView
        populateListView();

        // On filterButton press
        users_filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(1);
            }
        });

        // On sortButton press
        users_sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(2);
            }
        });

        return view;
    }

    private void populateListView() {
        if (currentState.equals("")) {
            adapter = new UserAdapter(getContext(), R.layout.users_row_item,
                    DataServices.getAllUsers());
        } else {
            ArrayList<DataServices.User> usersList = new ArrayList<>();
            for (int i = 0; i < DataServices.getAllUsers().size(); i++) {
                if (DataServices.getAllUsers().get(i).state.equals(currentState)) {
                    usersList.add(DataServices.getAllUsers().get(i));
                }
            }

            adapter = new UserAdapter(getContext(), R.layout.users_row_item, usersList);
        }

        users_listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof UsersFragment.IListener) {
            mListener = (UsersFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    UsersFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
    }
}