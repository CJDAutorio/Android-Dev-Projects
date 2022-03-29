package edu.uncc.itcs4180.midtermexam;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenceRecyclerViewAdapter extends RecyclerView.Adapter<ExpenceRecyclerViewAdapter.ExpenseViewHolder> {
    ArrayList<Expense> expenseArrayList;
    IExpenseRecycler mListener;

    public ExpenceRecyclerViewAdapter(ArrayList<Expense> expenseArrayList, IExpenseRecycler mListener) {
        this.mListener = mListener;
        this.expenseArrayList = expenseArrayList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.expense_row_item,
                parent,
                false);

        ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(view, mListener);


        return expenseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseArrayList.get(position);
        holder.expenseItem_name_TextView.setText(expense.getName());
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        holder.expenseItem_price_TextView.setText("$" + decimalFormat.format(expense.getAmount()));
        holder.expenseItem_date_TextView.setText(expense.getDateToString());
        holder.expenseItem_cat_TextView.setText(expense.getCategory());
        holder.expense = expense;
    }

    @Override
    public int getItemCount() {
        return this.expenseArrayList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView expenseItem_name_TextView;
        TextView expenseItem_price_TextView;
        TextView expenseItem_date_TextView;
        TextView expenseItem_cat_TextView;
        ImageButton expenseItem_ImageButton;
        IExpenseRecycler mListener;
        Expense expense;
        private final String TAG = "midterm-ExpenceRecyclerViewAdapter-ExpenseViewHolder";

        public ExpenseViewHolder(@NonNull View itemView, IExpenseRecycler mListener) {
            super(itemView);

            rootView = itemView;
            this.mListener = mListener;
            expenseItem_name_TextView = itemView.findViewById(R.id.expenseItem_name_TextView);
            expenseItem_price_TextView = itemView.findViewById(R.id.expenseItem_price_TextView);
            expenseItem_date_TextView = itemView.findViewById(R.id.expenseItem_date_TextView);
            expenseItem_cat_TextView = itemView.findViewById(R.id.expenseItem_cat_TextView);
            expenseItem_ImageButton = itemView.findViewById(R.id.expenseItem_ImageButton);

            expenseItem_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "expenseItem_ImageButton.setOnClickListener: " +
                            "Delete button pressed");
                    mListener.deleteExpense(expense);
                }
            });
        }
    }

    interface IExpenseRecycler {
        void deleteExpense(Expense expense);
    }
}
