package com.codersarena.transactionmanagement;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transactions {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @ColumnInfo(name = "transaction_id")
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "transaction_amount")
    private String amount;
    @ColumnInfo(name ="transaction_type")
    private String type;
    @ColumnInfo(name = "transaction_category")
    private String category;
    @ColumnInfo(name= "transaction_method")
    private String method;
    @ColumnInfo(name = "transaction_description")
    private String description;
    @ColumnInfo(name = "transaction_date")
    private String tdate;

    public Transactions(String amount, String type, String category, String method, String description, String tdate) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.method = method;
        this.description = description;
        this.tdate = tdate;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Transactions() {
    }
}
