package edu.uncc.itcs4180.homework07;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>{
    final private String TAG = "homework07-CartRecyclerViewAdapter";
    ArrayList<Cart> cartArrayList;
    ICartRecycler mListener;
    FirebaseAuth mAuth;

    public CartRecyclerViewAdapter(ArrayList<Cart> cartArrayList, ICartRecycler mListener) {
        Log.d(TAG, "ForumsRecyclerViewAdapter running");
        this.cartArrayList = cartArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder running");
        Cart cart = cartArrayList.get(position);
        Resources resources = holder.itemView.getContext().getResources();
        Context context = holder.itemView.getContext();
        mAuth = FirebaseAuth.getInstance();

        holder.cartItem_title_textView.setText(cart.title);
        holder.cartItem_owner_textView.setText(resources.getString(R.string.owner_name, cart.ownerName));
        holder.cartItem_numItems_textView.setText(resources.getString(R.string.num_items, cart.items.size()));

        double totalCost = 0;
        for (int i = 0; i < cart.items.size(); i++) {
            totalCost += (double) cart.items.get(i).get("price");
        }
        holder.cartItem_totalPrice_textView.setText(resources.getString(R.string.total_price, String.format("%.2f", totalCost)));
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        final private String TAG = "homework07-CartRecyclerViewAdapter-CartViewHolder";
        TextView cartItem_title_textView;
        TextView cartItem_owner_textView;
        TextView cartItem_numItems_textView;
        TextView cartItem_totalPrice_textView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "CartViewHolder running");
            cartItem_title_textView = itemView.findViewById(R.id.cartItem_title_textView);
            cartItem_owner_textView = itemView.findViewById(R.id.cartItem_owner_textView);
            cartItem_numItems_textView = itemView.findViewById(R.id.cartItem_numItems_textView);
            cartItem_totalPrice_textView = itemView.findViewById(R.id.cartItem_totalPrice_textView);
        }
    }

    interface ICartRecycler {
    }
}
