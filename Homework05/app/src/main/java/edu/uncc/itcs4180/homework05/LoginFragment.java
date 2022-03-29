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
 * LoginFragment.java
 * CJ D'Autorio
 */
public class LoginFragment extends Fragment {
    private final String TAG = "homework05-LoginFragment";
    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText login_email_editText;
    EditText login_password_editText;
    Button login_login_button;
    Button login_createAccount_button;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize globals
        login_email_editText = view.findViewById(R.id.login_email_editText);
        login_password_editText = view.findViewById(R.id.login_password_editText);
        login_login_button = view.findViewById(R.id.login_login_button);
        login_createAccount_button = view.findViewById(R.id.login_createAccount_button);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // On login button press
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login_email_editText.getText().toString().isEmpty() &&
                        !login_password_editText.getText().toString().isEmpty() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(login_email_editText.getText().toString()).matches()) {
                    login(login_email_editText.getText().toString(), login_password_editText.getText().toString());
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_unfilled),
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "login error.");
                }

                Log.d(TAG, "login button pressed.");
            }
        });

        // On create new account button press
        login_createAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "createAccount button pressed.");
                mListener.setRegisterFragment();
            }
        });

        return view;
    }

    private void login(String email, String password) {
        Log.d(TAG, "login function running.");
        FormBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "login: onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        editor.putString(getString(R.string.auth_token), jsonObject.getString("token"));
                        editor.putString(getString(R.string.auth_user_id), jsonObject.getString("user_id"));
                        editor.putString(getString(R.string.auth_user_fullname), jsonObject.getString("user_fullname"));
                        editor.commit();
                        Log.d(TAG, "json token: " + jsonObject.getString("token"));
                        Log.d(TAG, "json user_id: " + jsonObject.getString("user_id"));
                        Log.d(TAG, "json user_fullname: " + jsonObject.getString("user_fullname"));
                        Log.d(TAG, "login: onResponse: information saved. name: " +
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

        if (context instanceof LoginFragment.IListener) {
            mListener = (LoginFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    LoginFragment.IListener mListener;
    public interface IListener {
        void setPostsListFragment();
        void setRegisterFragment();
    }
}