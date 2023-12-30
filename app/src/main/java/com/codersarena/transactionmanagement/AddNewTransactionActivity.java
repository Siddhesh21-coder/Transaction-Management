package com.codersarena.transactionmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.codersarena.transactionmanagement.databinding.ActivityAddNewTransactionBinding;

public class AddNewTransactionActivity extends AppCompatActivity {
    private ActivityAddNewTransactionBinding addNewTransactionBinding;
    private AddNewTransactionClickHandler clickHandler;
    private Transactions transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_transaction);
        transactions = new Transactions();
        addNewTransactionBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_add_new_transaction);
        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        clickHandler = new AddNewTransactionClickHandler(transactions,this, myViewModel);
        addNewTransactionBinding.setTransaction(transactions);
        addNewTransactionBinding.setClickHandler(clickHandler);
    }
}