package edu.uncc.itcs4180.homework05;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Homework 05
 * PostRecyclerViewAdapter.java
 * CJ D'Autorio
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolder> {
    ArrayList<Posts.Post> postArrayList;
    String clientUserId;
    IPostRecycler mListener;

    public PostRecyclerViewAdapter(ArrayList<Posts.Post> postArrayList, String clientUserId, IPostRecycler mListener) {
        this.postArrayList = postArrayList;
        this.clientUserId = clientUserId;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row_item, parent, false);

        PostViewHolder postViewHolder = new PostViewHolder(view, mListener);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Posts.Post post = postArrayList.get(position);
        Resources resources = holder.itemView.getContext().getResources();
        holder.postRowItem_text_textView.setText(post.post_text);
        holder.postRowItem_name_textView.setText(post.created_by_name);
        holder.postRowItem_dateTime_textView.setText(resources.getString(
                R.string.date_at_time, post.getDate(), post.getTime()));
        holder.postRowItem_trash_button.setVisibility(View.INVISIBLE);
        holder.postRowItem_trash_button.setEnabled(false);
        holder.post_id = post.post_id;

        // enables trash button if client userid = post userid
        if (this.clientUserId.equals(post.created_by_uid)) {
            holder.postRowItem_trash_button.setVisibility(View.VISIBLE);
            holder.postRowItem_trash_button.setEnabled(true);
        }

    }

    @Override
    public int getItemCount() {
        return this.postArrayList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postRowItem_text_textView;
        TextView postRowItem_name_textView;
        TextView postRowItem_dateTime_textView;
        ImageButton postRowItem_trash_button;
        String post_id;
        View rootView;
        IPostRecycler mListener;

        public PostViewHolder(@NonNull View itemView, IPostRecycler mListener) {
            super(itemView);
            postRowItem_text_textView = itemView.findViewById(R.id.postRowItem_text_textView);
            postRowItem_name_textView = itemView.findViewById(R.id.postRowItem_name_textView);
            postRowItem_dateTime_textView = itemView.findViewById(R.id.postRowItem_dateTime_textView);
            postRowItem_trash_button = itemView.findViewById(R.id.postRowItem_trash_button);
            rootView = itemView;
            this.mListener = mListener;

            // on trash button press
            postRowItem_trash_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deletePost(post_id);
                }
            });
        }
    }

    interface IPostRecycler {
        void deletePost(String post_id);
    }
}
