package com.example.gigiscoop;

public class Expense
{
    int expenseID;
    String type;
    String itemName;
    double cost;
    String datePurchased;

    public int getExpenseID()
    {
        return expenseID;
    }

    public void setExpenseID(int expenseID)
    {
        this.expenseID = expenseID;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public String getDatePurchased()
    {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased)
    {
        this.datePurchased = datePurchased;
    }

    public Expense(int expenseID, String type, String itemName, double cost, String datePurchased)
    {
        this.expenseID = expenseID;
        this.type = type;
        this.itemName = itemName;
        this.cost = cost;
        this.datePurchased = datePurchased;
    }
}
