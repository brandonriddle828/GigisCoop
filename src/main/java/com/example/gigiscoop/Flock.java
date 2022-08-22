package com.example.gigiscoop;

public class Flock
{
    int flockID;
    int breedID;
    String breed;
    String eggColor;
    int henNum;
    int roosterNum;
    String hatchDate;
    boolean groupEnded;

    public int getFlockID()
    {
        return flockID;
    }

    public void setFlockID(int flockID)
    {
        this.flockID = flockID;
    }

    public int getBreedID()
    {
        return breedID;
    }

    public void setBreedID(int breedID)
    {
        this.breedID = breedID;
    }

    public String getBreed()
    {
        return breed;
    }

    public void setBreed(String breed)
    {
        this.breed = breed;
    }

    public String getEggColor()
    {
        return eggColor;
    }

    public void setEggColor(String eggColor)
    {
        this.eggColor = eggColor;
    }

    public int getHenNum()
    {
        return henNum;
    }

    public void setHenNum(int henNum)
    {
        this.henNum = henNum;
    }

    public int getRoosterNum()
    {
        return roosterNum;
    }

    public void setRoosterNum(int roosterNum)
    {
        this.roosterNum = roosterNum;
    }

    public String getHatchDate()
    {
        return hatchDate;
    }

    public void setHatchDate(String hatchDate)
    {
        this.hatchDate = hatchDate;
    }

    public boolean isGroupEnded()
    {
        return groupEnded;
    }

    public void setGroupEnded(boolean groupEnded)
    {
        this.groupEnded = groupEnded;
    }

    public Flock(int flockID, int breedID, String breed, String eggColor, int henNum, int roosterNum, String hatchDate, boolean groupEnded)
    {
        this.flockID = flockID;
        this.breedID = breedID;
        this.breed = breed;
        this.eggColor = eggColor;
        this.henNum = henNum;
        this.roosterNum = roosterNum;
        this.hatchDate = hatchDate;
        this.groupEnded = groupEnded;
    }
}
