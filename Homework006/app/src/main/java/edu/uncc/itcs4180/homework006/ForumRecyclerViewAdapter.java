package edu.uncc.itcs4180.homework006;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Homework 06
 * ForumRecyclerViewAdapter.java
 * CJ D'Autorio
 */
public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.ForumViewHolder>  {
    final private String TAG = "homework006-ForumRecyclerViewAdapter";
    Forum post;
    IForumRecycler mListener;
    FirebaseAuth mAuth;

    public ForumRecyclerViewAdapter(Forum post, IForumRecycler mListener) {
        Log.d(TAG, "ForumRecyclerViewAdapter running");
        this.post = post;
        this.mListener = mListener;
        Log.d(TAG, "ForumRecyclerViewAdapter: post: " + this.post.toString());
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row_item, parent, false);

        ForumViewHolder forumViewHolder = new ForumViewHolder(view, mListener);

        return forumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        Map<String, Object> comment = post.getComments().get(position);
        Resources resources = holder.itemView.getContext().getResources();
        Context context = holder.itemView.getContext();
        mAuth = FirebaseAuth.getInstance();

        Timestamp timestamp = (Timestamp) comment.get("time");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp.toDate());

        holder.comment = comment;
        holder.comment_name_textView.setText((String) comment.get("name"));
        holder.comment_body_textView.setText((String) comment.get("body"));
        holder.comment_date_textView.setText((calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + calendar.get(Calendar.YEAR));

        // sets time based on whether it's am or pm
        if (calendar.get(Calendar.HOUR_OF_DAY) > 12) {
            holder.comment_time_textView.setText((calendar.get(Calendar.HOUR_OF_DAY) - 12) + ":" +
                    calendar.get(Calendar.MINUTE) + " PM");
        } else if (calendar.get(Calendar.HOUR_OF_DAY) == 12) {
            holder.comment_time_textView.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                    calendar.get(Calendar.MINUTE) + " PM");
        } else {
            holder.comment_time_textView.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                    calendar.get(Calendar.MINUTE) + " AM");
        }

        // enables trash button if client userid = post userid
        if (mAuth.getCurrentUser().getUid().equals((String) comment.get("userId"))) {
            holder.comment_trash_button.setVisibility(View.VISIBLE);
            holder.comment_trash_button.setEnabled(true);
        } else {
            holder.comment_trash_button.setVisibility(View.INVISIBLE);
            holder.comment_trash_button.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "ForumRecyclerViewAdapter: getItemCount: " + this.post.getComments().size());
        return this.post.getComments().size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        final private String TAG = "homework006-ForumRecyclerViewAdapter-ForumViewHolder";
        View rootView;
        IForumRecycler mListener;
        TextView comment_name_textView;
        TextView comment_body_textView;
        TextView comment_date_textView;
        TextView comment_time_textView;
        ImageButton comment_trash_button;
        Map<String, Object> comment;

        public ForumViewHolder(@NonNull View itemView, IForumRecycler mListener) {
            super(itemView);
            rootView = itemView;
            this.mListener = mListener;
            Log.d(TAG, "ForumViewHolder running");

            comment_name_textView = itemView.findViewById(R.id.comment_name_textView);
            comment_body_textView = itemView.findViewById(R.id.comment_body_textView);
            comment_date_textView = itemView.findViewById(R.id.comment_date_textView);
            comment_time_textView = itemView.findViewById(R.id.comment_time_textView);
            comment_trash_button = itemView.findViewById(R.id.comment_trash_button);

            // On trash button press
            comment_trash_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteComment(comment);
                }
            });
        }
    }



    interface IForumRecycler {
        void deleteComment(Map<String, Object> comment);
    }
}
