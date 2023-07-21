package com.wesuckatlife.wesuckatminecraft.villager;

import com.google.common.collect.ImmutableSet;
import com.wesuckatlife.wesuckatminecraft.WeSuckAtMinecraft;
import com.wesuckatlife.wesuckatminecraft.block.ModBlocks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

// Tutorial: https://www.youtube.com/watch?v=Kuldy9sXDL8&list=PLKGarocXCE1HrC60yuTNTGRoZc6hf5Uvl&index=10

public class ModVillagers
{
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, WeSuckAtMinecraft.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, WeSuckAtMinecraft.MODID);

    public static final RegistryObject<PoiType> RUBY_BLOCK_POI = POI_TYPES.register("ruby_block_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.RUBY_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> RUBY_TRADER = VILLAGER_PROFESSIONS.register("ruby_trader",
            () -> new VillagerProfession("ruby_trader", x -> x.get() == RUBY_BLOCK_POI.get(),
                    x -> x.get() == RUBY_BLOCK_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_ARMORER));

    public static void registerPOIs()
    {
        try
        {
            ObfuscationReflectionHelper.findMethod(PoiTypes.class,
                    "registerBlockStates", PoiTypes.class).invoke(null, RUBY_BLOCK_POI.get());
        }
        catch (InvocationTargetException | IllegalAccessException exception)
        {
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus)
    {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
