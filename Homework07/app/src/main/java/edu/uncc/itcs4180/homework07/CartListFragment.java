package edu.uncc.itcs4180.homework07;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CartListFragment extends Fragment {
    private final String TAG = "homework07-CartListFragment";
    Button cartList_logout_button;
    Button cartList_create_button;
    RecyclerView cartList_carts_recyclerView;

    public CartListFragment() {
        // Required empty public constructor
    }

    public static CartListFragment newInstance() {
        CartListFragment fragment = new CartListFragment();
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
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        // Initialize globals
        cartList_logout_button = view.findViewById(R.id.cartList_logout_button);
        cartList_create_button = view.findViewById(R.id.cartList_create_button);
        cartList_carts_recyclerView = view.findViewById(R.id.cartList_carts_recyclerView);

        getActivity().setTitle(getResources().getString(R.string.cart_list));

        // On cartList_logout_button press
        cartList_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cartList_logout_button pressed");
                FirebaseAuth.getInstance().signOut();

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new LoginFragment())
                        .commit();
            }
        });

        // On cartList_create_button press
        cartList_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cartList_create_button pressed");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton(getResources().getString(R.string.create), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "create dialogue button pressed");
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "cancel dialogue button pressed");
                    }
                });
            }
        });

        return view;
    }
}