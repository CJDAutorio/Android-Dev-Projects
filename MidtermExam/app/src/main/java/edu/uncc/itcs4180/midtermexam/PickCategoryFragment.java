package edu.uncc.itcs4180.midtermexam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PickCategoryFragment extends Fragment {
    ListView pickCategoryFragment_ListView;
    ArrayAdapter<String> adapter;
    private String category = "";

    private static final String TAG = "midterm-PickCategoryFragment";

    public PickCategoryFragment() {
        // Required empty public constructor
    }

    public static PickCategoryFragment newInstance() {
        PickCategoryFragment fragment = new PickCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pick_category, container, false);

        pickCategoryFragment_ListView = view.findViewById(R.id.pickCategoryFragment_ListView);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, DataServices.getCategories());
        pickCategoryFragment_ListView.setAdapter(adapter);

        pickCategoryFragment_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onCreateView: pickCategoryFragment_ListView.setOnItemClickListener: Selected category: " + adapter.getItem(i));
                mListener.setCurrentCategory(adapter.getItem(i));
                mListener.setCurrentFragment(2);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PickCategoryFragment.IPickCategoryFragment) {
            mListener = (PickCategoryFragment.IPickCategoryFragment) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    IPickCategoryFragment mListener;
    interface IPickCategoryFragment {
        void setCurrentFragment(int fragment);
        void setCurrentCategory(String category);
    }
}