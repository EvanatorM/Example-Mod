package com.wesuckatlife.wesuckatminecraft.item;

import com.wesuckatlife.wesuckatminecraft.WeSuckAtMinecraft;
import com.wesuckatlife.wesuckatminecraft.item.custom.EightBallItem;
import com.wesuckatlife.wesuckatminecraft.item.custom.SnowballCannon;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WeSuckAtMinecraft.MODID);

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.WSAM_TAB)));

    public static final RegistryObject<Item> EIGHT_BALL = ITEMS.register("eight_ball",
            () -> new EightBallItem(new Item.Properties().tab(ModCreativeModeTab.WSAM_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SNOWBALL_CANNON = ITEMS.register("snowball_cannon",
            () -> new SnowballCannon(new Item.Properties().tab(ModCreativeModeTab.WSAM_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
