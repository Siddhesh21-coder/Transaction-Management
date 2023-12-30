package com.codersarena.transactionmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codersarena.transactionmanagement.databinding.TransactionListItemBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TransactionViewHolder> {
    private ArrayList<Transactions> transactions;

    public MyAdapter(ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TransactionListItemBinding transactionListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.transaction_list_item,parent,false);
        return new TransactionViewHolder(transactionListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transactions currentTransactions = transactions.get(position);
        holder.transactionListItemBinding.setTransaction(currentTransactions);
    }

    @Override
    public int getItemCount() {
        if (transactions != null)
        {
            return transactions.size();
        }
        return 0;
    }

    public void setTransactions(ArrayList<Transactions> transactions)
    {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TransactionListItemBinding transactionListItemBinding;

        public TransactionViewHolder(TransactionListItemBinding transactionListItemBinding) {
            super(transactionListItemBinding.getRoot());
            this.transactionListItemBinding = transactionListItemBinding;
        }
    }
}
