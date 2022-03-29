package edu.uncc.itcs4180.homework006;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Homework 06
 * ForumsRecyclerViewAdapter.java
 * CJ D'Autorio
 */
public class ForumsRecyclerViewAdapter extends RecyclerView.Adapter<ForumsRecyclerViewAdapter.ForumsViewHolder>  {
    final private String TAG = "homework006-ForumsRecyclerViewAdapter";
    ArrayList<Forum> forumArrayList;
    IForumsRecycler mListener;
    FirebaseAuth mAuth;

    public ForumsRecyclerViewAdapter(ArrayList<Forum> forumArrayList, IForumsRecycler mListener) {
        Log.d(TAG, "ForumsRecyclerViewAdapter running");
        this.forumArrayList = forumArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forums_row_item, parent, false);

        ForumsViewHolder forumsViewHolder = new ForumsViewHolder(view, mListener);

        return forumsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumsViewHolder holder, int position) {
        Forum forum = forumArrayList.get(position);
        Resources resources = holder.itemView.getContext().getResources();
        Context context = holder.itemView.getContext();
        mAuth = FirebaseAuth.getInstance();

        holder.post = forum;
        holder.post_title_textView.setText(forum.getTitle());
        holder.post_name_textView.setText(forum.getName());
        holder.post_likes_textView.setText(resources.getString(R.string.num_likes, forum.getLikes().size()));
        holder.post_date_textView.setText((forum.getDate().get(Calendar.MONTH) + 1) + "/" +
                forum.getDate().get(Calendar.DAY_OF_MONTH) + "/"
                + forum.getDate().get(Calendar.YEAR));

        // sets time based on whether it's am or pm
        if (forum.getDate().get(Calendar.HOUR_OF_DAY) > 12) {
            holder.post_time_textView.setText((forum.getDate().get(Calendar.HOUR_OF_DAY) - 12) + ":" +
                    forum.getDate().get(Calendar.MINUTE) + " PM");
        } else if (forum.getDate().get(Calendar.HOUR_OF_DAY) == 12) {
            holder.post_time_textView.setText(forum.getDate().get(Calendar.HOUR_OF_DAY) + ":" +
                    forum.getDate().get(Calendar.MINUTE) + " PM");
        } else {
            holder.post_time_textView.setText(forum.getDate().get(Calendar.HOUR_OF_DAY) + ":" +
                    forum.getDate().get(Calendar.MINUTE) + " AM");
        }

        // trims body if it is too long (200 characters)
        if (forum.getBody().length() > 200) {
            String bodyTrimmed = forum.getBody().substring(0, 200) + "...";
            holder.post_body_textView.setText(bodyTrimmed);
        } else {
            holder.post_body_textView.setText(forum.getBody());
        }

        // enables trash button if client userid = post userid
        if (mAuth.getCurrentUser().getUid().equals(forum.getUserId())) {
            holder.post_trash_button.setVisibility(View.VISIBLE);
            holder.post_trash_button.setEnabled(true);
        } else {
            holder.post_trash_button.setVisibility(View.INVISIBLE);
            holder.post_trash_button.setEnabled(false);
        }

        // sets like button status if client's uID is in the post's like list
        if (forum.getLikes().contains(mAuth.getCurrentUser().getUid())) {
            holder.post_like_button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like_favorite));
            holder.isLiked = true;
        } else {
            holder.post_like_button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like_not_favorite));
            holder.isLiked = false;
        }
    }

    @Override
    public int getItemCount() {
        return this.forumArrayList.size();
    }

    public static class ForumsViewHolder extends RecyclerView.ViewHolder {
        final private String TAG = "homework006-ForumsRecyclerViewAdapter-ForumsViewHolder";
        TextView post_title_textView;
        TextView post_name_textView;
        TextView post_body_textView;
        TextView post_likes_textView;
        TextView post_date_textView;
        TextView post_time_textView;
        ImageButton post_trash_button;
        ImageButton post_like_button;
        View rootView;
        IForumsRecycler mListener;
        Forum post;
        boolean isLiked;

        public ForumsViewHolder(@NonNull View itemView, IForumsRecycler mListener) {
            super(itemView);
            post_title_textView = itemView.findViewById(R.id.post_title_textView);
            post_name_textView = itemView.findViewById(R.id.post_name_textView);
            post_body_textView = itemView.findViewById(R.id.post_body_textView);
            post_likes_textView = itemView.findViewById(R.id.post_likes_textView);
            post_date_textView = itemView.findViewById(R.id.post_date_textView);
            post_time_textView = itemView.findViewById(R.id.post_time_textView);
            post_trash_button = itemView.findViewById(R.id.post_trash_button);
            post_like_button = itemView.findViewById(R.id.post_like_button);
            rootView = itemView;
            this.mListener = mListener;

            Log.d(TAG, "ForumsViewHolder running");

            // On like button press
            post_like_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.toggleLikePost(post.getPostId(), isLiked);
                }
            });

            // On trash button press
            post_trash_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deletePost(post.getPostId());
                }
            });


            // on rootView press
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.openPost(post.getPostId());
                }
            });
        }
    }

    interface IForumsRecycler {
        void toggleLikePost(String postId, boolean isLiked);
        void deletePost(String postId);
        void openPost(String postId);
    }
}
