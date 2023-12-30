package com.codersarena.transactionmanagement;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AddNewTransactionClickHandler {
    Transactions transactions;
    Context context;
    MyViewModel myViewModel;

    public AddNewTransactionClickHandler(Transactions transactions, Context context, MyViewModel myViewModel) {
        this.transactions = transactions;
        this.context = context;
        this.myViewModel = myViewModel;
    }

    public void onSubmitBtnClicked(View view)
    {
        if(transactions.getAmount() == null || transactions.getCategory()==null || transactions.getType() == null)
        {
            Toast.makeText(context, "Fields cannot be blank", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("Amount",transactions.getAmount());
            i.putExtra("Type",transactions.getType());
            i.putExtra("Category",transactions.getCategory());
            Transactions t = new Transactions(
                    transactions.getAmount(),
                    transactions.getType(),
                    transactions.getCategory(),
                    "Cash",
                    "For desription purpose",
                    "29 Dec"
            );
            Log.v("TAGY",t.getAmount()+t.getType()+t.getCategory());
            myViewModel.addNewTransaction(t);
            context.startActivity(i);
        }
    }
}
