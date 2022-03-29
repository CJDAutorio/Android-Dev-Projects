package edu.uncc.itcs4180.homework05;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Homework 05
 * CreatePostFragment.java
 * CJ D'Autorio
 */
public class CreatePostFragment extends Fragment {
    private final String TAG = "homework05-CreatePostFragment";
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText createPost_enterPost_textEdit;
    Button createPost_submit_button;
    Button createPost_cancel_button;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public static CreatePostFragment newInstance() {
        CreatePostFragment fragment = new CreatePostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        // Initialize globals
        createPost_enterPost_textEdit = view.findViewById(R.id.createPost_enterPost_textEdit);
        createPost_submit_button = view.findViewById(R.id.createPost_submit_button);
        createPost_cancel_button = view.findViewById(R.id.createPost_cancel_button);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // On submit button press
        createPost_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!createPost_enterPost_textEdit.getText().toString().isEmpty() &&
                        createPost_enterPost_textEdit.getText().toString().length() <= 100) {
                    post(createPost_enterPost_textEdit.getText().toString());
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_unfilled),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // On cancel button press
        createPost_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.popBackStack();
            }
        });

        return view;
    }

    private void post(String post_text) {
        Log.d(TAG, "post function running.");

        FormBody formBody = new FormBody.Builder()
                .add("post_text", post_text)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/create")
                .post(formBody)
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
                Log.d(TAG, "post: onResponse: " + response.toString());
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
                            mListener.popBackStack();
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
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CreatePostFragment.IListener) {
            mListener = (CreatePostFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    CreatePostFragment.IListener mListener;
    public interface IListener {
        void popBackStack();
    }
}