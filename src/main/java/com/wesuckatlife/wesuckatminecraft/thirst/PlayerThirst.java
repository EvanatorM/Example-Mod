package com.wesuckatlife.wesuckatminecraft.thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst
{
    private int thirst;
    private final int MIN_THIRST = 0;
    private final int MAX_THIRST = 10;

    public int getThirst()
    {
        return thirst;
    }

    // It would probably be better to sync between server and client here. It's more organized that way.
    public void addThirst(int add)
    {
        this.thirst = Math.min(thirst + add, MAX_THIRST);
    }
    public void subThirst(int sub)
    {
        this.thirst = Math.max(thirst - sub, MIN_THIRST);
    }

    public void copyFrom(PlayerThirst source)
    {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putInt("thirst", thirst);
    }

    public void loadNBTData(CompoundTag nbt)
    {
        thirst = nbt.getInt("thirst");
    }
}
