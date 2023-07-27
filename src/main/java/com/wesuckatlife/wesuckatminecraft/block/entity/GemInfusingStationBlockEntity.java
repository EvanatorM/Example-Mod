package com.wesuckatlife.wesuckatminecraft.block.entity;

import com.wesuckatlife.wesuckatminecraft.block.ModBlocks;
import com.wesuckatlife.wesuckatminecraft.item.ModItems;
import com.wesuckatlife.wesuckatminecraft.screen.GemInfusingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemInfusingStationBlockEntity extends BlockEntity implements MenuProvider
{
    private final ItemStackHandler itemHandler = new ItemStackHandler(3)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public GemInfusingStationBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.GEM_INFUSING_STATION.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex)
            {
                return switch (pIndex) {
                    case 0 -> GemInfusingStationBlockEntity.this.progress;
                    case 1 -> GemInfusingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue)
            {
                switch (pIndex) {
                    case 0 -> GemInfusingStationBlockEntity.this.progress = pValue;
                    case 1 -> GemInfusingStationBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount()
            {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Gem Infusing Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
    {
        return new GemInfusingStationMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("gem_infusing_station.progress", this.progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag)
    {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_infusing_station.progress");
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, GemInfusingStationBlockEntity pEntity)
    {
        if (level.isClientSide())
            return;

        if (hasRecipe(pEntity))
        {
            pEntity.progress++;
            setChanged(level, blockPos, blockState);

            if (pEntity.progress >= pEntity.maxProgress)
            {
                craftItem(pEntity);
            }
        }
        else
        {
            pEntity.resetProgress();
            setChanged(level, blockPos, blockState);
        }
    }


    private void resetProgress()
    {
        this.progress = 0;
    }

    private static void craftItem(GemInfusingStationBlockEntity pEntity)
    {
        if (hasRecipe(pEntity))
        {
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(ModBlocks.ASPHALT_BLOCK.get(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(GemInfusingStationBlockEntity pEntity)
    {
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        boolean hasRawGemInFirstSlot = pEntity.itemHandler.getStackInSlot(1).getItem() == ModItems.RUBY.get();

        return hasRawGemInFirstSlot && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(ModBlocks.ASPHALT_BLOCK.get(), 1));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack)
    {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory)
    {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
