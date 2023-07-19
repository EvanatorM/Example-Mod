package com.wesuckatlife.wesuckatminecraft.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

public class SnowballCannon extends Item
{
    public SnowballCannon(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND)
        {
            Snowball snowball = new Snowball(level, player);
            snowball.setItem(new ItemStack(Items.SNOWBALL));
            snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 2.5f, 1.0f);
            level.addFreshEntity(snowball);
        }

        return super.use(level, player, hand);
    }
}
