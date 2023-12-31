package com.wesuckatlife.wesuckatminecraft;

import com.mojang.logging.LogUtils;
import com.wesuckatlife.wesuckatminecraft.block.ModBlocks;
import com.wesuckatlife.wesuckatminecraft.block.entity.ModBlockEntities;
import com.wesuckatlife.wesuckatminecraft.item.ModItems;
import com.wesuckatlife.wesuckatminecraft.networking.ModMessages;
import com.wesuckatlife.wesuckatminecraft.painting.ModPaintings;
import com.wesuckatlife.wesuckatminecraft.recipe.ModRecipes;
import com.wesuckatlife.wesuckatminecraft.screen.GemInfusingStationScreen;
import com.wesuckatlife.wesuckatminecraft.screen.ModMenuTypes;
import com.wesuckatlife.wesuckatminecraft.villager.ModVillagers;
import com.wesuckatlife.wesuckatminecraft.world.feature.ModConfiguredFeatures;
import com.wesuckatlife.wesuckatminecraft.world.feature.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeSuckAtMinecraft.MODID)
public class WeSuckAtMinecraft
{
    public static final String MODID = "wesuckatminecraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WeSuckAtMinecraft()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModPaintings.register(modEventBus);

        ModVillagers.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();
            ModVillagers.registerPOIs();
        });

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.GEM_INFUSING_STATOIN_MENU.get(), GemInfusingStationScreen::new);
        }
    }
}
