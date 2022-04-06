package edu.uncc.itcs4180.homework07;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>{
    final private String TAG = "homework07-ItemRecyclerViewAdapter";
    ArrayList<Map<String, Object>> itemsArrayList;
    FirebaseAuth mAuth;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        final private String TAG = "homework07-ItemViewHolder";

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
