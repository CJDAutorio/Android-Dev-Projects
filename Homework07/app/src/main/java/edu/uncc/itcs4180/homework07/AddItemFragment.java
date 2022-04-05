package edu.uncc.itcs4180.homework07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddItemFragment extends Fragment {
    EditText addItem_name_editText;
    EditText addItem_price_editText;
    Button addItem_back_button;
    Button addItem_add_button;

    public AddItemFragment() {
        // Required empty public constructor
    }

    public static AddItemFragment newInstance() {
        AddItemFragment fragment = new AddItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        addItem_name_editText = view.findViewById(R.id.addItem_name_editText);
        addItem_price_editText = view.findViewById(R.id.addItem_price_editText);
        addItem_back_button = view.findViewById(R.id.addItem_back_button);
        addItem_add_button = view.findViewById(R.id.addItem_add_button);

        return view;
    }
}