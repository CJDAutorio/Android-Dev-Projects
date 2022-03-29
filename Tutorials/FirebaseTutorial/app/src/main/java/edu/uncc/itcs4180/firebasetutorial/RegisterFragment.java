package edu.uncc.itcs4180.firebasetutorial;

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

public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private final String TAG = "firebasetutorial-RegisterFragment";
    EditText register_email_editText;
    EditText register_password_editText;
    Button register_cancel_button;
    Button register_register_button;

    public RegisterFragment() {
        // Required empty public constructor
    }

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
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        register_email_editText = view.findViewById(R.id.register_email_editText);
        register_password_editText = view.findViewById(R.id.register_password_editText);
        register_cancel_button = view.findViewById(R.id.register_cancel_button);
        register_register_button = view.findViewById(R.id.register_register_button);

        getActivity().setTitle("Register New User");

        register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = register_email_editText.getText().toString();
                String password = register_password_editText.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter password", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: Registered successfully");

                                Log.d(TAG, "onComplete: " + mAuth.getCurrentUser().getUid());

                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentContainerView, new MainFragment())
                                        .commit();
                            } else {
                                Log.d(TAG, "onComplete: Error");
                                Log.d(TAG, "onComplete: " + task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        register_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });

        return view;
    }
}