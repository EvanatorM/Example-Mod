package com.wesuckatlife.wesuckatminecraft.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab
{
    public static final CreativeModeTab WSAM_TAB = new CreativeModeTab("wsamtab")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.RUBY.get());
        }
    };
}
