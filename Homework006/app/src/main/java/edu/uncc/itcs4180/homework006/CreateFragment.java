package edu.uncc.itcs4180.homework006;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homework 06
 * CreateFragment.java
 * CJ D'Autorio
 */
public class CreateFragment extends Fragment {
    FirebaseAuth mAuth;
    private final String TAG = "homework006-CreateFragment";
    EditText create_title_editText;
    EditText create_desc_editText;
    Button create_submit_button;
    Button create_cancel_button;
    String userName;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
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
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        // Initialize globals
        mAuth = FirebaseAuth.getInstance();
        create_title_editText = view.findViewById(R.id.create_title_editText);
        create_desc_editText = view.findViewById(R.id.create_desc_editText);
        create_submit_button = view.findViewById(R.id.create_submit_button);
        create_cancel_button = view.findViewById(R.id.create_cancel_button);

        // on create_submit_button press
        create_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!create_title_editText.getText().toString().isEmpty() &&
                        !create_desc_editText.getText().toString().isEmpty())  {
                    setUserName();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.unfilled_create), Toast.LENGTH_SHORT);
                }
            }
        });

        // On create_cancel_button press
        create_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void createPost() {
        Log.d(TAG, "createPost function running.");

        List<Map<String, String>> comments = new ArrayList<>();
        List<String> likes = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("body", create_desc_editText.getText().toString());
        data.put("comments", comments);
        data.put("likes", likes);
        data.put("name", userName);
        data.put("timestamp", new Timestamp(Calendar.getInstance().getTime()));
        data.put("title", create_title_editText.getText().toString());
        data.put("userId", mAuth.getCurrentUser().getUid());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forum")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "createPost: successfully created post");
                        //Toast.makeText(getContext(), getActivity().getResources().getString(R.string.created_post), Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "createPost: create post failed");
                        Toast.makeText(getContext(), getActivity().getResources().getString(R.string.failure) + e.getMessage(), Toast.LENGTH_SHORT);
                    }
                });

        getParentFragmentManager().popBackStack();
    }

    public void setUserName() {
        Log.d(TAG, "setUserName function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userName = documentSnapshot.getString("name");
                createPost();
            }
        });
    }
}