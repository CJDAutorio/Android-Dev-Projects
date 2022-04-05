package edu.uncc.itcs4180.homework07;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private final String TAG = "homework07-LoginFragment";
    private FirebaseAuth mAuth;
    EditText login_email_editText;
    EditText login_password_editText;
    Button login_register_button;
    Button login_login_button;

    public LoginFragment() {
        // Required empty public constructor
    }

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
        Log.d(TAG, "fragment running");

        getActivity().setTitle(getResources().getString(R.string.login));

        // Initialize globals
        login_email_editText = view.findViewById(R.id.login_email_editText);
        login_password_editText = view.findViewById(R.id.login_password_editText);
        login_register_button = view.findViewById(R.id.login_register_button);
        login_login_button = view.findViewById(R.id.login_login_button);

        // on login_register_button press
        login_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainerView, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // on login_login_button press
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "login button pressed");
                if (!login_email_editText.getText().toString().isEmpty() &&
                        !login_password_editText.getText().toString().isEmpty() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(login_email_editText.getText().toString()).matches()) {
                    login();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_unfilled),
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "login unfilled");
                }
            }
        });

        return view;
    }

    private void login() {
        Log.d(TAG, "login: running");
        String email = login_email_editText.getText().toString();
        String password = login_password_editText.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "login-onComplete: Logged in successfully");
                            Log.d(TAG, "login-onComplete: " + mAuth.getCurrentUser().getUid());

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, new CartFragment())
                                    .commit();
                        } else {
                            Log.d(TAG, "login-onComplete: Error");
                            Log.d(TAG, "login-onComplete: " + task.getException().getMessage());
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}