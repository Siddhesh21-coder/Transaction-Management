package com.codersarena.transactionmanagement;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddNewTransactionClickHandler {
    Transactions transactions;
    Context context;
    MyViewModel myViewModel;

    public AddNewTransactionClickHandler(Transactions transactions, Context context, MyViewModel myViewModel) {
        this.transactions = transactions;
        this.context = context;
        this.myViewModel = myViewModel;
    }
    public void onSplitTypeChanged(RadioGroup radioGroup, int checkedId) {
        RadioButton radioButton = radioGroup.findViewById(checkedId);

        if (radioButton != null) {
            String selectedType = radioButton.getText().toString();
            transactions.setType(selectedType.toLowerCase()); // Assuming you want "debit" or "credit"
        }
    }


    public void onSplitTypeChanged1(RadioGroup radioGroup, int checkedId) {
        RadioButton radioButton = radioGroup.findViewById(checkedId);

        if (radioButton != null) {
            String selectedType = radioButton.getText().toString().split(" ")[1];
            Log.v("Tagy",selectedType);
            transactions.setMethod(selectedType.toLowerCase()); // Assuming you want "debit" or "credit"
        }
    }
    public void onSubmitBtnClicked(View view)
    {
        if(transactions.getAmount() == null || transactions.getCategory()==null || transactions.getType() == null)
        {
            Toast.makeText(context, "Fields cannot be blank", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(context, MainActivity.class);
//            Log.v("TAGY",transactions.getType());

            i.putExtra("Amount",transactions.getAmount());
            i.putExtra("Type",transactions.getType());
            i.putExtra("Category",transactions.getCategory());
            String formattedDateTime1="30 Dec";
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm");
            String formattedDateTime = date.format(formatter);
//            Log.v("TAGY",formattedDateTime);
            Transactions t = new Transactions(
                    transactions.getAmount(),
                    transactions.getType(),
                    transactions.getCategory(),
                    transactions.getMethod(),
                    transactions.getDescription(),
                    formattedDateTime
            );
            Log.v("TAGY",t.getMethod());
            myViewModel.addNewTransaction(t);
            context.startActivity(i);
        }
    }
}
