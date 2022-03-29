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
 * RegisterFragment.java
 * CJ D'Autorio
 */
public class RegisterFragment extends Fragment {
    final String TAG = "homework05-RegisterFragment";
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText register_fullName_editText;
    EditText register_email_editText;
    EditText register_password_editText;
    Button register_submit_button;
    Button register_cancel_button;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        register_fullName_editText = view.findViewById(R.id.register_fullName_editText);
        register_email_editText = view.findViewById(R.id.register_email_editText);
        register_password_editText = view.findViewById(R.id.register_password_editText);
        register_submit_button = view.findViewById(R.id.register_submit_button);
        register_cancel_button = view.findViewById(R.id.register_cancel_button);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // on submit button press
        register_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!register_fullName_editText.getText().toString().isEmpty() &&
                        !register_email_editText.getText().toString().isEmpty() &&
                        !register_password_editText.getText().toString().isEmpty() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(register_email_editText.getText().toString()).matches()) {
                    register(register_fullName_editText.getText().toString(),
                            register_email_editText.getText().toString(),
                            register_password_editText.getText().toString());
                    Log.d(TAG, "register_submit_button pressed");
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.register_unfilled),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // on cancel button press
        register_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "register_cancel_button pressed");
                mListener.setLoginFragment();
            }
        });

        return view;
    }

    private void register(String fullName, String email, String password) {
        Log.d(TAG, "register function running.");
        FormBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("name", fullName)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/signup")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "register: onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        editor.putString(getString(R.string.auth_token), jsonObject.getString("token"));
                        editor.putString(getString(R.string.auth_user_id), jsonObject.getString("user_id"));
                        editor.putString(getString(R.string.auth_user_fullname), jsonObject.getString("user_fullname"));
                        editor.apply();
                        Log.d(TAG, "register: onResponse: information saved. name: " +
                                sharedPreferences.getString(getString(R.string.auth_user_fullname),
                                        "") +
                                ", user_id: " +
                                sharedPreferences.getString(getString(R.string.auth_user_id),
                                        "") +
                                ", auth_token: " +
                                sharedPreferences.getString(getString(R.string.auth_token),
                                        ""));
                        mListener.setPostsListFragment();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
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
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof RegisterFragment.IListener) {
            mListener = (RegisterFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    RegisterFragment.IListener mListener;
    public interface IListener {
        void setPostsListFragment();
        void setLoginFragment();
    }
}