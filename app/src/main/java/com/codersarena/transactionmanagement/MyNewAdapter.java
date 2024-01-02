package com.codersarena.transactionmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyNewAdapter extends RecyclerView.Adapter<MyNewAdapter.TransactionCategoryViewHolder> {
    private ArrayList<TransactionCategory> transactionCategories;

    public MyNewAdapter(ArrayList<TransactionCategory> transactionCategories) {
        this.transactionCategories = transactionCategories;
    }

    @NonNull
    @Override
    public TransactionCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_category_list, parent, false);
        TextView categoryTextView = itemView.findViewById(R.id.contentView);
        TextView sumTextView = itemView.findViewById(R.id.priceView);

        return new TransactionCategoryViewHolder(itemView, categoryTextView, sumTextView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionCategoryViewHolder holder, int position) {
        TransactionCategory currentCategory = transactionCategories.get(position);

        holder.categoryTextView.setText(currentCategory.getCategory());
        holder.sumTextView.setText("\u20B9 "+String.valueOf(currentCategory.getAmountTotal()));
    }

    @Override
    public int getItemCount() {
        return transactionCategories.size();
    }

    public class TransactionCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView sumTextView;

        public TransactionCategoryViewHolder(@NonNull View itemView, TextView categoryTextView, TextView sumTextView) {
            super(itemView);
            this.categoryTextView = categoryTextView;
            this.sumTextView = sumTextView;
        }
    }
}
