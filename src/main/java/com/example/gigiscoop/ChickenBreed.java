package com.example.gigiscoop;

public class ChickenBreed
{
    int breedID;
    String breedName;
    String eggColor;

    public String getBreedName()
    {
        return breedName;
    }

    public void setBreedName(String breedName)
    {
        this.breedName = breedName;
    }

    public int getBreedID()
    {
        return breedID;
    }

    public void setBreedID(int breedID)
    {
        this.breedID = breedID;
    }

    public String getEggColor()
    {
        return eggColor;
    }

    public void setEggColor(String eggColor)
    {
        this.eggColor = eggColor;
    }

    public ChickenBreed(int breedID,String breedName, String eggColor)
    {
        this.breedID = breedID;
        this.breedName = breedName;
        this.eggColor = eggColor;
    }
}
