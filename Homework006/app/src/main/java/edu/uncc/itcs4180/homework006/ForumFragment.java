package edu.uncc.itcs4180.homework006;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homework 06
 * ForumFragment.java
 * CJ D'Autorio
 */
public class ForumFragment extends Fragment implements ForumRecyclerViewAdapter.IForumRecycler {
    private FirebaseAuth mAuth;
    private final String TAG = "homework006-ForumFragment";
    private final static String ARG_POSTID = "ARG_POSTID";
    String postId;
    Forum post;
    String userName;
    TextView forum_title_textView;
    TextView forum_name_textView;
    TextView forum_body_textView;
    TextView forum_comments_textView;
    EditText forum_comment_editText;
    Button forum_post_button;
    RecyclerView forum_comments_recyclerView;
    ForumRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(String postId) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POSTID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = getArguments().getString(ARG_POSTID);
            Log.d(TAG, "onCreate: postId: " + postId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        // Initialize globals
        mAuth = FirebaseAuth.getInstance();
        forum_title_textView = view.findViewById(R.id.forum_title_textView);
        forum_name_textView = view.findViewById(R.id.forum_name_textView);
        forum_body_textView = view.findViewById(R.id.forum_body_textView);
        forum_comments_textView = view.findViewById(R.id.forum_comments_textView);
        forum_comment_editText = view.findViewById(R.id.forum_comment_editText);
        forum_post_button = view.findViewById(R.id.forum_post_button);
        forum_comments_recyclerView = view.findViewById(R.id.forum_comments_recyclerView);

        layoutManager = new LinearLayoutManager(getContext());
        forum_comments_recyclerView.setLayoutManager(layoutManager);

        getPost();

        // on forum_post_button press
        forum_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "forum_post_button press");
                if (!forum_comment_editText.getText().toString().isEmpty()) {
                    setUserName();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.unfilled_comment), Toast.LENGTH_SHORT);
                }
            }
        });

        return view;
    }

    private void fillText() {
        forum_title_textView.setText(post.getTitle());
        forum_name_textView.setText(post.getName());
        forum_body_textView.setText(post.getBody());
        forum_comments_textView.setText(getContext().getString(R.string.num_comments, post.getComments().size()));
    }

    private void getPost() {
        Log.d(TAG, "getPost function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection("forum").document(postId);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "getPost: onEvent");

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Log.d(TAG, "Current data: " + documentSnapshot.getData());
                        } else {
                            Log.d(TAG, "Current data: null");
                        }

                        Log.d(TAG, "getPost: onEvent: " + documentSnapshot.getId());
                        Log.d(TAG, "getPost: onEvent: " + documentSnapshot.getData());
                        Log.d(TAG, "getPost: onEvent: name: " + documentSnapshot.getString("name"));

                        List<String> likesList = (List<String>) documentSnapshot.get("likes");
                        List<Map<String, Object>> commentsList = (List<Map<String, Object>>) documentSnapshot.get("comments");

                        post = new Forum(
                                documentSnapshot.getId(),
                                documentSnapshot.getString("title"),
                                documentSnapshot.getString("body"),
                                documentSnapshot.getTimestamp("timestamp"),
                                new ArrayList<>(likesList),
                                documentSnapshot.getString("userId"),
                                new ArrayList<>(commentsList),
                                documentSnapshot.getString("name")
                        );

                        fillText();
                        attachAdapter();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
            }
        });

        // Snapshot listener doesn't close when back button is pressed, causing a crash if the post
        // is interacted with, so I had to replace it with a one time documentSnapshot
        //        documentReference
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                        Log.d(TAG, "getPost: onEvent");
//
//                        if (error != null) {
//                            Log.w(TAG, "Listen failed.", error);
//                            return;
//                        }
//
//                        if (value != null && value.exists()) {
//                            Log.d(TAG, "Current data: " + value.getData());
//                        } else {
//                            Log.d(TAG, "Current data: null");
//                        }
//
//                        Log.d(TAG, "getPost: onEvent: " + value.getId());
//                        Log.d(TAG, "getPost: onEvent: " + value.getData());
//                        Log.d(TAG, "getPost: onEvent: name: " + value.getString("name"));
//
//                        List<String> likesList = (List<String>) value.get("likes");
//                        List<Map<String, Object>> commentsList = (List<Map<String, Object>>) value.get("comments");
//
//                        post = new Forum(
//                                value.getId(),
//                                value.getString("title"),
//                                value.getString("body"),
//                                value.getTimestamp("timestamp"),
//                                new ArrayList<>(likesList),
//                                value.getString("userId"),
//                                new ArrayList<>(commentsList),
//                                value.getString("name")
//                        );
//
//                        fillText();
//                        attachAdapter();
//                    }
//                });
    }

    private void attachAdapter() {
        adapter = new ForumRecyclerViewAdapter(post, this);
        forum_comments_recyclerView.setAdapter(adapter);
        Log.d(TAG, "onCreateView: adapter attached");
    }

    private void postComment() {
        Log.d(TAG, "postComment function running");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("forum").document(postId);
        Map<String, Object> comment = new HashMap<>();
        comment.put("body", forum_comment_editText.getText().toString());
        comment.put("name", userName);
        comment.put("time", new Timestamp(Calendar.getInstance().getTime()));
        comment.put("userId", mAuth.getCurrentUser().getUid());

        documentReference.update("comments", FieldValue.arrayUnion(comment))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "postComment: onSuccess.");
                        Toast.makeText(getContext(), getResources().getString(R.string.created_comment), Toast.LENGTH_SHORT);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "postComment: onFailure.");
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
            }
        });

        getPost();
    }

    public void setUserName() {
        Log.d(TAG, "setUserName function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userName = documentSnapshot.getString("name");
                postComment();
            }
        });
    }

    @Override
    public void deleteComment(Map<String, Object> comment) {
        Log.d(TAG, "deleteComment function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("forum").document(postId);

        documentReference.update("comments", FieldValue.arrayRemove(comment))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "deleteComment: onSuccess.");
                Toast.makeText(getContext(), getResources().getString(R.string.delete_comment), Toast.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "deleteComment: onFailure.");
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
            }
        });

        getPost();
    }
}