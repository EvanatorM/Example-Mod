package com.wesuckatlife.wesuckatminecraft.event;

import com.wesuckatlife.wesuckatminecraft.WeSuckAtMinecraft;
import com.wesuckatlife.wesuckatminecraft.client.ThirstHudOverlay;
import com.wesuckatlife.wesuckatminecraft.networking.ModMessages;
import com.wesuckatlife.wesuckatminecraft.networking.packet.DrinkWaterC2SPacket;
import com.wesuckatlife.wesuckatminecraft.networking.packet.ExampleC2SPacket;
import com.wesuckatlife.wesuckatminecraft.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents
{
    @Mod.EventBusSubscriber(modid = WeSuckAtMinecraft.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event)
        {
            if (KeyBinding.DRINKING_KEY.consumeClick())
            {
                //Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed a Key!"));
                ModMessages.sendToServer(new DrinkWaterC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = WeSuckAtMinecraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.DRINKING_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event)
        {
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }
    }

}
