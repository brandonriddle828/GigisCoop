package com.example.gigiscoop;

public class FixedCart
{
    String customer;
    String breed;
    int hatchingEggs;
    int pullets;
    int chicks;
    int eatingEggs12;
    int eatingEggs6;
    int roosters;
    int hens;
    int cockerels;
    double cost;


    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer(String customer)
    {
        this.customer = customer;
    }

    public String getBreed()
    {
        return breed;
    }

    public void setBreed(String breed)
    {
        this.breed = breed;
    }

    public int getHatchingEggs()
    {
        return hatchingEggs;
    }

    public void setHatchingEggs(int hatchingEggs)
    {
        this.hatchingEggs = hatchingEggs;
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

    public int getPullets()
    {
        return pullets;
    }

    public void setPullets(int pullets)
    {
        this.pullets = pullets;
    }

    public int getRoosters()
    {
        return roosters;
    }

    public void setRoosters(int roosters)
    {
        this.roosters = roosters;
    }

    public int getHens()
    {
        return hens;
    }

    public void setHens(int hens)
    {
        this.hens = hens;
    }

    public int getCockerels()
    {
        return cockerels;
    }

    public void setCockerels(int cockerels)
    {
        this.cockerels = cockerels;
    }

    public FixedCart(String customer, String breed, int roosters, int hens, int pullets, int cockerels,  int chicks, int hatchingEggs, int eatingEggs12, int eatingEggs6,  double cost)
    {
        this.customer = customer;
        this.breed = breed;
        this.hatchingEggs = hatchingEggs;
        this.pullets = pullets;
        this.chicks = chicks;
        this.eatingEggs12 = eatingEggs12;
        this.eatingEggs6 = eatingEggs6;
        this.roosters = roosters;
        this.hens = hens;
        this.cockerels = cockerels;
        this.cost = cost;
    }
}
