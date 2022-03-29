
package edu.uncc.itcs4180.homework006;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Homework 06
 * RegisterFragment.java
 * CJ D'Autorio
 */
public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private final String TAG = "homework006-RegisterFragment";
    EditText register_name_editText;
    EditText register_email_editText;
    EditText register_password_editText;
    Button register_submit_button;
    Button register_cancel_button;

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        register_name_editText = view.findViewById(R.id.register_name_editText);
        register_email_editText = view.findViewById(R.id.register_email_editText);
        register_password_editText = view.findViewById(R.id.register_password_editText);
        register_submit_button = view.findViewById(R.id.register_submit_button);
        register_cancel_button = view.findViewById(R.id.register_cancel_button);

        getActivity().setTitle(getResources().getString(R.string.create_new_account));

        // on submit button press
        register_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "register_submit_button pressed");
                register();
            }
        });

        // on cancel button press
        register_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "register_cancel_button pressed");
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });

        return view;
    }

    private void register() {
        String name = register_name_editText.getText().toString();
        String email = register_email_editText.getText().toString();
        String password = register_password_editText.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.register_unfilled), Toast.LENGTH_SHORT).show();
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
                                        .replace(R.id.fragmentContainerView, new ForumsFragment())
                                        .commit();

                                addUserToCollection();
                            } else {
                                Log.d(TAG, "onComplete: Error");
                                Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void addUserToCollection() {
        Log.d(TAG, "addUserToCollection function running.");

        Map<String, Object> data = new HashMap<>();
        data.put("name", register_name_editText.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(mAuth.getCurrentUser().getUid()).set(data);

        Log.d(TAG, "addUserToCollection successfully ran.");
    }
}