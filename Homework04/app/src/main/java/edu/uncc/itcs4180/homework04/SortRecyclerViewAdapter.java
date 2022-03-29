package edu.uncc.itcs4180.homework04;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SortRecyclerViewAdapter extends RecyclerView.Adapter<SortRecyclerViewAdapter.SortViewHolder>{
    ArrayList<String> sortModes;

    public SortRecyclerViewAdapter(ArrayList<String> sortModes) {
        this.sortModes = sortModes;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_row_item,
                parent, false);

        SortViewHolder sortViewHolder = new SortViewHolder(view);

        return sortViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        holder.sortRow_textView.setText(sortModes.get(position));
        holder.sortMode = sortModes.get(position);
    }

    @Override
    public int getItemCount() {
        return this.sortModes.size();
    }

    public static class SortViewHolder extends RecyclerView.ViewHolder {
        TextView sortRow_textView;
        ImageButton sortRow_ascendingImageButton;
        ImageButton sortRow_descendingImageButton;
        String sortMode;

        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            sortRow_textView = itemView.findViewById(R.id.sortRow_textView);
            sortRow_ascendingImageButton = itemView.findViewById(R.id.sortRow_ascendingImageButton);
            sortRow_descendingImageButton = itemView.findViewById(R.id.sortRow_descendingImageButton);
            Log.d("homework04-SortRecyclerViewAdapter-SortViewHolder", "sortMode: " + sortMode);

            sortRow_ascendingImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onButtonClick(sortMode, true);
                }
            });

            sortRow_descendingImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onButtonClick(sortMode, false);
                }
            });
        }

        SortRecyclerViewAdapter.SortViewHolder.OnButtonClickListener mListener;
        public interface OnButtonClickListener {
            void onButtonClick(String sortMode, boolean isAscending);
        }
    }
}
