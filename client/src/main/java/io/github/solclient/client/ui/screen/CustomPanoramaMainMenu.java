package io.github.solclient.client.ui.screen;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Identifier;

public class CustomPanoramaMainMenu extends TitleScreen {

    private static final Identifier[] PANORAMA = {
        new Identifier("sol_client", "textures/gui/background/meus_backgrounds/panorama_0.png"),
        new Identifier("sol_client", "textures/gui/background/meus_backgrounds/panorama_1.png"),
        new Identifier("sol_client", "textures/gui/background/meus_backgrounds/panorama_2.png"),
        new Identifier("sol_client", "textures/gui/background/meus_backgrounds/panorama_3.png"),
        new Identifier("sol_client", "textures/gui/background/meus_backgrounds/panorama_4.png"),
        new Identifier("sol_client", "textures/gui/background/meus_backgrounds/panorama_5.png")
    };

    public Identifier[] getPanoramaTextureIds() {
        return PANORAMA;
    }
}
