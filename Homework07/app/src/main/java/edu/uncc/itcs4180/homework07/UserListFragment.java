package edu.uncc.itcs4180.homework07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserListFragment extends Fragment {
    private final String TAG = "homework07-UserListFragment";
    FirebaseAuth mAuth;
    Button userList_back_button;
    RecyclerView userList_addedUsers_recylerView;
    RecyclerView userList_allUsers_recylerView;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        userList_back_button = view.findViewById(R.id.userList_back_button);
        userList_addedUsers_recylerView = view.findViewById(R.id.userList_addedUsers_recylerView);
        userList_allUsers_recylerView = view.findViewById(R.id.userList_allUsers_recylerView);

        // On userList_back_button press
        userList_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "userList_back_button pressed");
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }
}