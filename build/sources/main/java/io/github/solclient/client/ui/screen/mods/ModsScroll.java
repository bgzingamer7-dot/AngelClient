package io.github.solclient.client.ui.screen.mods;

import io.github.solclient.client.Client;
import io.github.solclient.client.mod.Mod;
import io.github.solclient.client.mod.ModOption;
import io.github.solclient.client.ui.component.impl.ScrollListComponent;
import io.github.solclient.client.util.data.Rectangle;

public class ModsScroll extends ScrollListComponent {

    private ModsScreen.ModsScreenComponent screen;

    public ModsScroll(ModsScreen.ModsScreenComponent screen) {
        this.screen = screen;
    }

    @Override
    public int getSpacing() {
        return 10;
    }

    @Override
    protected int getScrollStep() {
        return 40;
    }

    public void load() {
        clear();
        
        if(screen.getMod() == null) {
            int x = 0;
            int y = 0;
            int columnCount = 4;
            
            for(Mod mod : Client.INSTANCE.getMods()) {
                if(!mod.getName().toLowerCase().contains(screen.getQuery().toLowerCase())) continue;

                // Cópias finais para o Lambda não reclamar
                final int finalX = x;
                final int finalY = y;

                add(new ModListing(mod, screen), (c, b) -> new Rectangle(finalX * 100, finalY * 85, 90, 75));

                x++;
                if(x >= columnCount) {
                    x = 0;
                    y++;
                }
            }
        } else {
            // Carrega as configurações do Mod quando você clica com o botão direito
            for(ModOption option : screen.getMod().getOptions()) {
                add(new ModOptionComponent(option));
            }
        }
    }
}