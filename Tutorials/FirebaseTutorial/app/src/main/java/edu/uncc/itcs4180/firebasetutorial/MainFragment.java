package edu.uncc.itcs4180.firebasetutorial;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragment extends Fragment {
    private final String TAG = "FirebaseTutorial-MainFragment";
    Button main_logoutButton;
    ListView main_listView;
    ArrayAdapter<User> adapter;
    ArrayList<User> users = new ArrayList<>();

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        main_logoutButton = view.findViewById(R.id.main_logoutButton);
        main_listView = view.findViewById(R.id.main_listView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, users);
        main_listView.setAdapter(adapter);

        getActivity().setTitle("Main Fragment");

        main_logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });

        getData();

        return view;
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("contacts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot document: value) {
                            users.clear();
                            //users.add(new User(document.getString("name"), document.getString("cell")));

                            // NEED TO MATCH CLASS PARAMETERS EXACTLY, NEED GET/SET METHODS
                            document.toObject(User.class);

                            Log.d(TAG, "onEvent: " + document.getId());
                            Log.d(TAG, "onEvent: " + document.getData());
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> user = new HashMap<>();
        user.put("name", "Test User");
        user.put("cell", "444-444-4444");

        // random id
        db.collection("contacts")
                .add(user)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                })
                .addOnSuccessListener(getActivity(), new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });

        // defined id
        db.collection("contacts")
                .document("testId")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

        // update
        db.collection("contacts")
                .document("testId")
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
    }
}