package com.codersarena.transactionmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codersarena.transactionmanagement.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TransactionDatabase transactionDatabase;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private MyAdapter myAdapter;
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandler handlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandler(this);
        mainBinding.setClickHandler(handlers);
        RecyclerView recyclerView = mainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        transactionDatabase = TransactionDatabase.getInstance(this);
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
//        Transactions t1 = new Transactions("100","cash","Food");
//        viewModel.addNewTransaction(t1);
        viewModel.getAllTransactions().observe(this,
                new Observer<List<Transactions>>() {
                    @Override
                    public void onChanged(List<Transactions> transactions1) {
                        transactions.clear();
                        double sum = 0;
                        for(Transactions t: transactions1)
                        {
                            transactions.add(t);
                            if (t.getType().equalsIgnoreCase("debit"))
                            {
                                sum-=Double.parseDouble(t.getAmount());
                            }
                            else {
                                sum+=Double.parseDouble(t.getAmount());

                            }
                            Log.v("TAGO",t.getType());
                        }
                        TextView txtView = findViewById(R.id.t2View);
                        String s = "Balance: \t\t\u20B9";
                        txtView.setText(s+String.valueOf(sum));

                        myAdapter.notifyDataSetChanged();
                    }
                });
        myAdapter = new MyAdapter(transactions);
        recyclerView.setAdapter(myAdapter);
    }
}