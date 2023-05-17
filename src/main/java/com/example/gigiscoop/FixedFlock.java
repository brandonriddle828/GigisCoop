package com.example.gigiscoop;

public class FixedFlock
{
    int flockID;
    int breedID;
    String breed;
    String eggColor;
    int henNum;
    int roosterNum;
    int pulletNum;
    int cockerelNum;
    int chickNum;
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

    public int getPulletNum()
    {
        return pulletNum;
    }

    public void setPulletNum(int pulletNum)
    {
        this.pulletNum = pulletNum;
    }

    public int getCockerelNum()
    {
        return cockerelNum;
    }

    public void setCockerelNum(int cockerelNum)
    {
        this.cockerelNum = cockerelNum;
    }

    public int getChickNum()
    {
        return chickNum;
    }

    public void setChickNum(int chickNum)
    {
        this.chickNum = chickNum;
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


    public FixedFlock(int flockID, int breedID, String breed, String eggColor, int henNum, int roosterNum, int pulletNum, int cockerelNum, int chickNum, String hatchDate, boolean groupEnded)
    {
        this.flockID = flockID;
        this.breedID = breedID;
        this.breed = breed;
        this.eggColor = eggColor;
        this.henNum = henNum;
        this.roosterNum = roosterNum;
        this.pulletNum = pulletNum;
        this.cockerelNum = cockerelNum;
        this.chickNum = chickNum;
        this.hatchDate = hatchDate;
        this.groupEnded = groupEnded;
    }
}
