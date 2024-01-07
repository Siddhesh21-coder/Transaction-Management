package com.codersarena.transactionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codersarena.transactionmanagement.databinding.ActivityMainBinding;
import java.util.stream.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private TransactionDatabase transactionDatabase;
    private ArrayList<Transactions> transactions = new ArrayList<>();
    private ArrayList<TransactionCategory> transactionCategories = new ArrayList<>();
    private MyAdapter myAdapter;
    private MyNewAdapter myNewAdapter;
    private HashMap<String, Double> categorySumMap = new HashMap<>();;
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
        RecyclerView recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);

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
                        double cashBalance = 0;
                        double upiBalance = 0;
                        double expenses = 0;
                        for(Transactions t: transactions1)
                        {




                            transactions.add(t);
                            if (t.getType().equalsIgnoreCase("debit"))
                            {
                                expenses+=Double.parseDouble(t.getAmount());
                                if(t.getMethod().equalsIgnoreCase("cash"))
                                {
                                    cashBalance-=Double.parseDouble(t.getAmount());
                                }
                                else {
                                    upiBalance -= Double.parseDouble(t.getAmount());
                                }
                                sum-=Double.parseDouble(t.getAmount());
                                if (categorySumMap.containsKey(t.getCategory())) {
                                    // If yes, add the value to the existing sum
                                    double currentSum = categorySumMap.get(t.getCategory());
                                    categorySumMap.put(t.getCategory(), currentSum + Double.parseDouble(t.getAmount()));
                                } else {
                                    // If no, add a new entry for the category with the initial value
                                    categorySumMap.put(t.getCategory(), Double.parseDouble(t.getAmount()));
                                }
                            }
                            else {
                                if(t.getMethod().equalsIgnoreCase("cash"))
                                {
                                    cashBalance+=Double.parseDouble(t.getAmount());
                                }
                                else {
                                    upiBalance += Double.parseDouble(t.getAmount());
                                }
                                sum+=Double.parseDouble(t.getAmount());

                            }
                            Log.v("TAGO",t.getType());
                        }

                        List<Map.Entry<String, Double>> entryList = new ArrayList<>(categorySumMap.entrySet());
                        Collections.sort(entryList, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
                        categorySumMap.clear(); // Clear the existing map
                        for (Map.Entry<String, Double> entry : entryList) {
                            categorySumMap.put(entry.getKey(), entry.getValue());
                        }
                        transactionCategories.clear();
                        //Sort the keys according to the values
                        Map<String, Double> sortedHashMap = categorySumMap.entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1, e2) -> e1,
                                        LinkedHashMap::new
                                ));
                        for (Map.Entry<String, Double> entry : sortedHashMap.entrySet()) {
                            String category = entry.getKey();
                            double sumCategory = entry.getValue();
                            TransactionCategory tcs = new TransactionCategory(category,sumCategory);
                            Log.v("TAGP", "Added TransactionCategory - Category: " + transactionCategories.size());
                            transactionCategories.add(tcs);

                        }


                        Collections.reverse(transactions);
                        TextView txtView = findViewById(R.id.t2View);
                        TextView cashView = findViewById(R.id.cashView);
                        String cashBalancer = "Cash Balance: \t\t\u20B9";
                        String cashint = String.format("%.2f",cashBalance);

                        cashView.setText(cashBalancer+cashint);

                        TextView upiView = findViewById(R.id.upiView);
                        String upiBalancer = "UPI Balance: \t\t\u20B9";
                        String upiint = String.format("%.2f",upiBalance);
                        upiView.setText(upiBalancer+upiint);
                        String s = "Expenses: \t\t\u20B9";
                        String bint = String.format("%.2f",expenses);
                        txtView.setText(s+bint);
                        myNewAdapter = new MyNewAdapter(transactionCategories);
                        recyclerView1.setAdapter(myNewAdapter);
                        myAdapter.notifyDataSetChanged();
                    }
                });
        myAdapter = new MyAdapter(transactions);
        Log.v("TAGS","Transaction Categories size: " + transactionCategories.size());

        myNewAdapter = new MyNewAdapter(transactionCategories);
        recyclerView1.setAdapter(myNewAdapter);
        recyclerView.setAdapter(myAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Transactions t = transactions.get(viewHolder.getAdapterPosition());
                viewModel.deleteNewTransaction(t);
                Toast.makeText(MainActivity.this, "Deleted Contact Successfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}