package com.example.gigiscoop;

public class FixedPrices
{
    int priceID;
    int flockID;
    double hatchingEggs;
    double pullets;
    double chicks;
    double roosters;
    double hens;
    double cockerels;
    double eatingEggs12;
    double eatingEggs6;

    public int getPriceID()
    {
        return priceID;
    }

    public void setPriceID(int priceID)
    {
        this.priceID = priceID;
    }

    public int getFlockID()
    {
        return flockID;
    }

    public void setFlockID(int flockID)
    {
        this.flockID = flockID;
    }

    public double getHatchingEggs()
    {
        return hatchingEggs;
    }

    public void setHatchingEggs(double hatchingEggs)
    {
        this.hatchingEggs = hatchingEggs;
    }

    public double getPullets()
    {
        return pullets;
    }

    public void setPullets(double pullets)
    {
        this.pullets = pullets;
    }

    public double getChicks()
    {
        return chicks;
    }

    public void setChicks(double chicks)
    {
        this.chicks = chicks;
    }

    public double getRoosters()
    {
        return roosters;
    }

    public void setRoosters(double roosters)
    {
        this.roosters = roosters;
    }

    public double getHens()
    {
        return hens;
    }

    public void setHens(double hens)
    {
        this.hens = hens;
    }

    public double getCockerels()
    {
        return cockerels;
    }

    public void setCockerels(double cockerels)
    {
        this.cockerels = cockerels;
    }

    public double getEatingEggs12()
    {
        return eatingEggs12;
    }

    public void setEatingEggs12(double eatingEggs12)
    {
        this.eatingEggs12 = eatingEggs12;
    }

    public double getEatingEggs6()
    {
        return eatingEggs6;
    }

    public void setEatingEggs6(double eatingEggs6)
    {
        this.eatingEggs6 = eatingEggs6;
    }

    public FixedPrices(int priceID, int flockID, double hatchingEggs, double pullets, double chicks, double roosters, double hens, double cockerels, double eatingEggs12, double eatingEggs6)
    {
        this.priceID = priceID;
        this.flockID = flockID;
        this.hatchingEggs = hatchingEggs;
        this.pullets = pullets;
        this.chicks = chicks;
        this.roosters = roosters;
        this.hens = hens;
        this.cockerels = cockerels;
        this.eatingEggs12 = eatingEggs12;
        this.eatingEggs6 = eatingEggs6;
    }
}
