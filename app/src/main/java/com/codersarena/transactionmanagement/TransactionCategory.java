package com.codersarena.transactionmanagement;

public class TransactionCategory {
    private String category;
    private double  amountTotal;

    public TransactionCategory() {
    }

    public TransactionCategory(String category, double amountTotal) {
        this.category = category;
        this.amountTotal = amountTotal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(double amountTotal) {
        this.amountTotal = amountTotal;
    }
}
