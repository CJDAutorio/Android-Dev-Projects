package edu.uncc.itcs4180.homework07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CartFragment extends Fragment {
    private final String TAG = "homework07-CartFragment";
    Button cart_logout_button;
    Button cart_addItem_button;
    Button cart_addUsers_button;
    RecyclerView cart_items_recyclerView;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize globals
        cart_logout_button = view.findViewById(R.id.cart_logout_button);
        cart_addItem_button = view.findViewById(R.id.cart_addItem_button);
        cart_addUsers_button = view.findViewById(R.id.cart_addUsers_button);
        cart_items_recyclerView = view.findViewById(R.id.cart_items_recyclerView);

        // On cart_logout_button press
        cart_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cart_logout_button pressed");
                FirebaseAuth.getInstance().signOut();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });

        return view;
    }
}