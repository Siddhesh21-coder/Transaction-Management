package com.codersarena.transactionmanagement;

import android.app.Application;
import android.os.Looper;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Repository {
    private final TransactionDAO transactionDAO;
    Handler handler;
    ExecutorService executorService;

    public Repository(Application application)
    {
        TransactionDatabase transactionDatabase = TransactionDatabase.getInstance(application);
        this.transactionDAO = transactionDatabase.getTransactionDAO();
        executorService = Executors.newSingleThreadExecutor();
        handler = new android.os.Handler(Looper.getMainLooper());
    }
    public void addTransaction(Transactions transactions)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionDAO.insert(transactions);
            }
        }
        );
    }


    public void deleteTransaction(Transactions transactions)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionDAO.delete(transactions);
            }
        });
    }

    public LiveData<List<Transactions>> getAllTransactions(){
        return transactionDAO.getAllTransactions();
    }
}
