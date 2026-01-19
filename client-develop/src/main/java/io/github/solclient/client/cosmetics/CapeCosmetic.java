package io.github.solclient.client.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public class CapeCosmetic {

    private static final ResourceLocation CAPE =
        new ResourceLocation("sol_client", "textures/capes/gui/cosmetics/capes/Tyrone/1.png");

    public static boolean shouldRender(AbstractClientPlayer player) {
        return CosmeticsManager.hasCape();
    }

    public static ResourceLocation getCape() {
        return CAPE;
    }
}
