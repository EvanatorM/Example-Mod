package com.wesuckatlife.wesuckatminecraft.event;

import com.wesuckatlife.wesuckatminecraft.WeSuckAtMinecraft;
import com.wesuckatlife.wesuckatminecraft.item.ModItems;
import com.wesuckatlife.wesuckatminecraft.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

// Tutorial: https://www.youtube.com/watch?v=Kuldy9sXDL8&list=PLKGarocXCE1HrC60yuTNTGRoZc6hf5Uvl&index=10

@Mod.EventBusSubscriber(modid = WeSuckAtMinecraft.MODID)
public class ModEvents
{
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event)
    {
        // Add custom trades to vanilla villagers
        if (event.getType() == VillagerProfession.TOOLSMITH)
        {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EIGHT_BALL.get(), 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    stack, 10, 8, 0.02f));

            // There is also a remove method for trades
        }
        // Add custom trades to modded villagers
        if (event.getType() == ModVillagers.RUBY_TRADER.get())
        {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.BLUEBERRY.get(), 15);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    stack, 10, 8, 0.02f));
        }
    }
}
