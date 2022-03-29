package edu.uncc.itcs4180.homework006;

import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Homework 06
 * ForumsFragment.java
 * CJ D'Autorio
 */
public class ForumsFragment extends Fragment implements ForumsRecyclerViewAdapter.IForumsRecycler {
    private FirebaseAuth mAuth;
    private final String TAG = "homework006-ForumsFragment";
    ArrayList<Forum> posts = new ArrayList<>();
    Button forums_logout_button;
    Button forums_newForum_button;
    RecyclerView forums_posts_recyclerView;
    ForumsRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    public ForumsFragment() {
        // Required empty public constructor
    }

    public static ForumsFragment newInstance() {
        ForumsFragment fragment = new ForumsFragment();
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
        View view = inflater.inflate(R.layout.fragment_forums, container, false);

        mAuth = FirebaseAuth.getInstance();
        forums_logout_button = view.findViewById(R.id.forums_logout_button);
        forums_newForum_button = view.findViewById(R.id.forums_newForum_button);
        forums_posts_recyclerView = view.findViewById(R.id.forums_posts_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        forums_posts_recyclerView.setLayoutManager(layoutManager);
        adapter = new ForumsRecyclerViewAdapter(posts, this);
        forums_posts_recyclerView.setAdapter(adapter);
        Log.d(TAG, "onCreateView: adapter attached");

        getActivity().setTitle(getResources().getString(R.string.forums));

        getData();

        // on forums_logout_button press
        forums_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });

        // on forums_newForum_button press
        forums_newForum_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new CreateFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void getData() {
        Log.d(TAG, "getData function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("forum")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d(TAG, "getData: onEvent");

                        posts.clear();
                        for (QueryDocumentSnapshot document: value) {
                            Log.d(TAG, "getData: onEvent: " + document.getId());
                            Log.d(TAG, "getData: onEvent: " + document.getData());
                            Log.d(TAG, "getData: onEvent: name: " + document.getString("name"));

                            List<String> likesList = (List<String>) document.get("likes");
                            List<Map<String, Object>> commentsList = (List<Map<String, Object>>) document.get("comments");

                            posts.add(new Forum(
                                    document.getId(),
                                    document.getString("title"),
                                    document.getString("body"),
                                    document.getTimestamp("timestamp"),
                                    new ArrayList<>(likesList),
                                    document.getString("userId"),
                                    new ArrayList<>(commentsList),
                                    document.getString("name")
                            ));

                            Log.d(TAG, "getData: onEvent: posts: " + posts.toString());
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void toggleLikePost(String postId, boolean isLiked) {
        Log.d(TAG, "toggleLikePost function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("forum").document(postId);

        if (isLiked) {
            documentReference.update("likes", FieldValue.arrayRemove(mAuth.getCurrentUser().getUid()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "toggleLikePost: successfully unliked post");
                            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.unliked_post), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            documentReference.update("likes", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "toggleLikePost: successfully liked post");
                            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.liked_post), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        getData();
    }

    @Override
    public void deletePost(String postId) {
        Log.d(TAG, "deletePost function running.");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("forum").document(postId);

        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "deletePost: successfully deleted post");
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.delete_post), Toast.LENGTH_SHORT).show();
            }
        });

        getData();
    }

    @Override
    public void openPost(String postId) {
        Log.d(TAG, "openPost function running.");

        new ForumFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ForumFragment.newInstance(postId))
                .addToBackStack(null)
                .commit();
    }
}