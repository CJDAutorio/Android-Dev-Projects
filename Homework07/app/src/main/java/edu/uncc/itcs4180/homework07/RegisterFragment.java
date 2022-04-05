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

public class RegisterFragment extends Fragment {
    private final String TAG = "homework07-RegisterFragment";
    FirebaseAuth mAuth;
    EditText register_name_editText;
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize globals
        register_name_editText = view.findViewById(R.id.register_name_editText);
        register_email_editText = view.findViewById(R.id.register_email_editText);
        register_password_editText = view.findViewById(R.id.register_password_editText);
        register_cancel_button = view.findViewById(R.id.register_cancel_button);
        register_register_button = view.findViewById(R.id.register_register_button);

        getActivity().setTitle(getResources().getString(R.string.register));

        // On register_cancel_button press
        register_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "register_cancel_button pressed");
                getParentFragmentManager().popBackStack();
            }
        });

        // On register_register_button press
        register_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "register_register_button pressed");
                register();
            }
        });

        return view;
    }

    private void register() {
        Log.d(TAG, "register function running");
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
                                        .replace(R.id.fragmentContainerView, new CartFragment())
                                        .commit();

                                //addUserToCollection();
                            } else {
                                Log.d(TAG, "onComplete: Error");
                                Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}