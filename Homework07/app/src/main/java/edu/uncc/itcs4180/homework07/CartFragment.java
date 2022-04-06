package edu.uncc.itcs4180.homework07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartFragment extends Fragment {
    private final String TAG = "homework07-CartFragment";
    private final static String ARG_CARTID = "ARG_CARTID";
    String cartId;
    Button cart_logout_button;
    Button cart_delete_button;
    Button cart_addItem_button;
    Button cart_addUsers_button;
    RecyclerView cart_items_recyclerView;
    CartRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String cartId) {
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
        cart_delete_button = view.findViewById(R.id.cart_delete_button);
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

        // On cart_delete_button press
        cart_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cart_delete_button pressed");
                deleteCart();
            }
        });

        // On cart_addItem_button press
        cart_addItem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cart_addItem_button pressed");
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new AddItemFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // On cart_addUsers_button press
        cart_addUsers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cart_addUsers_button pressed");
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new UserListFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void populateList() {
        Log.d(TAG, "populateList function running");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference documentReference = db.collection()
    }

    private void deleteCart() {
        Log.d(TAG, "deleteCart function running");

    }
}