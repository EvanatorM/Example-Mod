package com.wesuckatlife.wesuckatminecraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wesuckatlife.wesuckatminecraft.WeSuckAtMinecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ThirstHudOverlay
{
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(WeSuckAtMinecraft.MODID,
            "textures/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(WeSuckAtMinecraft.MODID,
            "textures/thirst/empty_thirst.png");

    public static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, EMPTY_THIRST);
        for (int i = 0; i < 10; i++)
        {
            // Empty Texture (9x9 texture including outline)
            // y - 49 is same height as armor bars
            // x - 91 is same x as the first heart
            // x + 10 is same x as the first hunger bar
            // x + 8 is not the same x as hunger bars but looks more centered
            // i * 8 is the same spacing as hearts and hunger bars
            GuiComponent.blit(poseStack, x + 8 + (i * 8), y - 49, 0, 0, 9, 9,
                    9, 9);
        }

        RenderSystem.setShaderTexture(0, FILLED_THIRST);
        for (int i = 0; i < 10; i++)
        {
            if (ClientThirstData.getPlayerThirst() > i)
            {
                GuiComponent.blit(poseStack, x + 8 + (i * 8), y - 49, 0, 0, 9, 9,
                        9, 9);
            }
            else
                break;
        }
    });
}
