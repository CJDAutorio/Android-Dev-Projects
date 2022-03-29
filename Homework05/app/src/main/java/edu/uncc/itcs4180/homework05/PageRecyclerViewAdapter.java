package edu.uncc.itcs4180.homework05;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Homework 05
 * PageRecyclerViewAdapter.java
 * CJ D'Autorio
 */
public class PageRecyclerViewAdapter extends RecyclerView.Adapter<PageRecyclerViewAdapter.PageViewHolder> {
    int pageCount;
    IPageRecycler mListener;

    public PageRecyclerViewAdapter(int pageCount, IPageRecycler mListener) {
        this.pageCount = pageCount;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_row_item, parent, false);

        PageViewHolder pageViewHolder = new PageViewHolder(view, mListener);

        return pageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        Resources resources = holder.itemView.getContext().getResources();
        holder.pageRow_number_button.setText(position + 1 + "");
        holder.pageNumber = position + 1;
    }

    @Override
    public int getItemCount() {
        return pageCount;
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        Button pageRow_number_button;
        View rootView;
        IPageRecycler mListener;
        int pageNumber;

        public PageViewHolder(@NonNull View itemView, IPageRecycler mListener) {
            super(itemView);
            pageRow_number_button = itemView.findViewById(R.id.pageRow_number_button);

            pageRow_number_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.changePage(pageNumber);
                }
            });
        }
    }

    interface IPageRecycler {
        void changePage(int page);
    }
}
