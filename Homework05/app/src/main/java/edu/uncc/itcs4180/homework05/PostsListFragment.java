package edu.uncc.itcs4180.homework05;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Homework 05
 * PostsListFragment.java
 * CJ D'Autorio
 */
public class PostsListFragment extends Fragment implements PostRecyclerViewAdapter.IPostRecycler,
        PageRecyclerViewAdapter.IPageRecycler {
    final String TAG = "homework05-PostsListFragment";
    private final OkHttpClient client = new OkHttpClient();
    private int currentPage = 1;
    private int totalCount = -1;
    ArrayList<Posts.Post> postArrayList = new ArrayList<>();
    PostRecyclerViewAdapter postRecyclerViewAdapter;
    PageRecyclerViewAdapter pageRecyclerViewAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button postList_createPost_button;
    Button postList_logout_button;
    TextView postList_welcome_label;
    RecyclerView postList_posts_recyclerView;
    TextView postList_pageCount_label;
    RecyclerView postList_pages_recyclerView;
    LinearLayoutManager postsLayoutManager;
    LinearLayoutManager pageLayoutManager;

    public PostsListFragment() {
        // Required empty public constructor
    }

    public static PostsListFragment newInstance() {
        PostsListFragment fragment = new PostsListFragment();
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
        View view = inflater.inflate(R.layout.fragment_posts_list, container, false);

        // Initialize globals
        postList_createPost_button = view.findViewById(R.id.postList_createPost_button);
        postList_logout_button = view.findViewById(R.id.postList_logout_button);
        postList_welcome_label = view.findViewById(R.id.postList_welcome_label);
        postList_posts_recyclerView = view.findViewById(R.id.postList_posts_recyclerView);
        postList_pageCount_label = view.findViewById(R.id.postList_pageCount_label);
        postList_pages_recyclerView = view.findViewById(R.id.postList_pages_recyclerView);

        pageLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        postsLayoutManager = new LinearLayoutManager(getContext());
        postList_posts_recyclerView.setLayoutManager(postsLayoutManager);
        postList_pages_recyclerView.setLayoutManager(pageLayoutManager);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        postList_welcome_label.setText(getString(R.string.welcome,
                sharedPreferences.getString(getString(R.string.auth_user_fullname),
                        "")));

        initializePosts();

        // On create post button clicked
        postList_createPost_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "create post button clicked.");
                mListener.setCreatePostFragment();
            }
        });

        // On logout button clicked
        postList_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "logout button clicked.");
                editor.clear();
                editor.apply();
                mListener.setLoginFragment();
            }
        });

        Log.d(TAG, "PostsListFragment running.");

        return view;
    }

    public void initializePosts() {
        Log.d(TAG, "initializePageCount function running.");

        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/posts").newBuilder()
                .addQueryParameter("page", String.valueOf(currentPage))
                .build();

        Log.d(TAG, "auth key: " + sharedPreferences.getString(
                getString(R.string.auth_token),""));

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "BEARER " + sharedPreferences.getString(
                        getString(R.string.auth_token),""))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //Log.d(TAG, "initializePageCount: onResponse: " + response.toString());
                String responseString = response.body().string();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        Log.d(TAG, "initializePageCount: jsonObject: " + jsonObject);
                        totalCount = jsonObject.getInt("totalCount");
                        Log.d(TAG, "initializePageCount: onResponse: total count: " + totalCount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    Posts posts = gson.fromJson(responseString, (Type) Posts.class);
                    Log.d(TAG, "initializePageCount: onResponse: posts: \n" + posts.toString());
                    Log.d(TAG, "initializePageCount: onResponse: page successfully returned");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populatePageList(totalCount);
                            populatePostList(posts);
                        }
                    });
                } else {
                    JSONObject jsonObject = null;
                    String message = "";
                    try {
                        jsonObject = new JSONObject(responseString);
                        message = jsonObject.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String finalMessage = message;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), finalMessage,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void populatePageList(int totalCount) {
        Log.d(TAG, "populatePageList: running");

        int pageCount = totalCount / 10 + 1;

        pageRecyclerViewAdapter = new PageRecyclerViewAdapter(pageCount, this);
        postList_pages_recyclerView.setAdapter(pageRecyclerViewAdapter);

        postList_pageCount_label.setText(getResources().getString(R.string.showing_page, currentPage, pageCount));

        Log.d(TAG, "populatePageList: page adapter attached");
    }

    private void populatePostList(Posts posts) {
        Log.d(TAG, "populatePostList: running");
        postArrayList.clear();
        for(int i = 0; i < posts.posts.length; i++) {
            postArrayList.add(posts.posts[i]);
        }

        postRecyclerViewAdapter = new PostRecyclerViewAdapter(postArrayList,
                sharedPreferences.getString(getString(R.string.auth_user_id),""), this);
        postList_posts_recyclerView.setAdapter(postRecyclerViewAdapter);

        Log.d(TAG, "populatePostList: posts adapter attached");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PostsListFragment.IListener) {
            mListener = (PostsListFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    @Override
    public void deletePost(String post_id) {
        Log.d(TAG, "deletePost function running.");

        FormBody formBody = new FormBody.Builder()
                .add("post_id", post_id)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/delete")
                .post(formBody)
                .addHeader("Authorization", "BEARER " + sharedPreferences.getString(
                        getString(R.string.auth_token),""))
                .build();

//        Request request = new Request.Builder()
//                .url("https://www.theappsdr.com/posts/delete")
//                .post(formBody)
//                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "deletePost: onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = null;
                            String message = "";
                            try {
                                jsonObject = new JSONObject(response.body().string());
                                message = jsonObject.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String finalMessage = message;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), finalMessage,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                            initializePosts();
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = null;
                            String message = "";
                            try {
                                jsonObject = new JSONObject(response.body().string());
                                message = jsonObject.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String finalMessage = message;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), finalMessage,
                                            Toast.LENGTH_LONG).show();
                                    initializePosts();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    PostsListFragment.IListener mListener;

    @Override
    public void changePage(int page) {
        currentPage = page;
        initializePosts();
    }

    public interface IListener {
        void setLoginFragment();
        void setCreatePostFragment();
    }
}