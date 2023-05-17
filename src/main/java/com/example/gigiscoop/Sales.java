package com.example.gigiscoop;

public class Sales
{
    int saleID;
    int priceID;
    int customerID;
    int hatchingEggs;
    int pullets;
    int chicks;
    int eatingEggs12;
    int eatingEggs6;
    String dateSold;

    public int getSaleID()
    {
        return saleID;
    }

    public void setSaleID(int saleID)
    {
        this.saleID = saleID;
    }

    public int getPriceID()
    {
        return priceID;
    }

    public void setPriceID(int priceID)
    {
        this.priceID = priceID;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    public int getHatchingEggs()
    {
        return hatchingEggs;
    }

    public void setHatchingEggs(int hatchingEggs)
    {
        this.hatchingEggs = hatchingEggs;
    }

    public int getPullets()
    {
        return pullets;
    }

    public void setPullets(int pullets)
    {
        this.pullets = pullets;
    }

    public int getChicks()
    {
        return chicks;
    }

    public void setChicks(int chicks)
    {
        this.chicks = chicks;
    }


    public int getEatingEggs12()
    {
        return eatingEggs12;
    }

    public void setEatingEggs12(int eatingEggs12)
    {
        this.eatingEggs12 = eatingEggs12;
    }

    public int getEatingEggs6()
    {
        return eatingEggs6;
    }

    public void setEatingEggs6(int eatingEggs6)
    {
        this.eatingEggs6 = eatingEggs6;
    }

    public String getDateSold()
    {
        return dateSold;
    }

    public void setDateSold(String dateSold)
    {
        this.dateSold = dateSold;
    }

    public Sales(int saleID, int priceID, int customerID, int hatchingEggs, int pullets, int chicks, int eatingEggs12, int eatingEggs6, String dateSold)
    {
        this.saleID = saleID;
        this.priceID = priceID;
        this.customerID = customerID;
        this.hatchingEggs = hatchingEggs;
        this.pullets = pullets;
        this.chicks = chicks;
        this.eatingEggs12 = eatingEggs12;
        this.eatingEggs6 = eatingEggs6;
        this.dateSold = dateSold;
    }
}
