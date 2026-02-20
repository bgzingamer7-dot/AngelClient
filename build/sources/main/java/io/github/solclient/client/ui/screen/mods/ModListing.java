package io.github.solclient.client.ui.screen.mods;

import io.github.solclient.client.mod.Mod;
import io.github.solclient.client.ui.component.ComponentRenderInfo;
import io.github.solclient.client.ui.component.impl.ColouredComponent;
import io.github.solclient.client.ui.component.impl.LabelComponent;
import io.github.solclient.client.ui.component.impl.ScaledIconComponent;
import io.github.solclient.client.util.Utils;
import io.github.solclient.client.util.data.Colour;
import io.github.solclient.client.util.data.Rectangle;

public class ModListing extends ColouredComponent {

    private Mod mod;
    private ModsScreen.ModsScreenComponent screen;

    public ModListing(Mod mod, ModsScreen.ModsScreenComponent screen) {
        super((c, col) -> Colour.WHITE);
        this.mod = mod;
        this.screen = screen;

        // ÍCONE DO MOD - Usando o sistema do Sol Client original
        // Usa "sol_client_" + ID do mod (ex: "sol_client_fps", "sol_client_keystrokes")
        add(new ScaledIconComponent("sol_client_" + mod.getId(), 32, 32), 
            (c, b) -> new Rectangle(29, 10, 32, 32));

        // NOME DO MOD
        add(new LabelComponent(mod.getName()), 
            (c, b) -> new Rectangle(0, 50, 90, 10));
    }

    @Override
    public void render(ComponentRenderInfo info) {
        Colour bg = isHovered() ? new Colour(255, 255, 255, 40) : new Colour(0, 0, 0, 100);
        Utils.drawRectangle(getRelativeBounds(), bg);

        if(mod.isEnabled()) {
            Rectangle b = getRelativeBounds();
            Utils.drawRectangle(new Rectangle(b.getX(), b.getY() + b.getHeight() - 2, b.getWidth(), 2), 
                new Colour(255, 255, 255, 255));
        }
        super.render(info);
    }

    @Override
    public boolean mouseClicked(ComponentRenderInfo info, int button) {
        if (button == 0) {
            Utils.playClickSound(true);
            mod.setEnabled(!mod.isEnabled());
            return true;
        } else if (button == 1) { 
            Utils.playClickSound(true);
            screen.switchMod(mod, false); 
            return true;
        }
        return false;
    }
}