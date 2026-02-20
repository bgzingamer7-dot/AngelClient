package io.github.solclient.client.ui.screen.mods;

import io.github.solclient.client.mod.Mod;
import io.github.solclient.client.ui.component.Component;
import io.github.solclient.client.ui.component.impl.ButtonComponent;
import io.github.solclient.client.ui.component.impl.ScaledIconComponent;
import io.github.solclient.client.ui.component.impl.TextFieldComponent;
import io.github.solclient.client.ui.screen.PanoramaBackgroundScreen;
import io.github.solclient.client.util.data.Colour;
import io.github.solclient.client.util.data.Rectangle;
import io.github.solclient.client.ui.component.ComponentRenderInfo;
import io.github.solclient.client.ui.component.controller.AnimatedColourController;
import io.github.solclient.client.util.Utils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;


public class ModsScreen extends PanoramaBackgroundScreen {

    private ModsScreenComponent component;

    public ModsScreen() { this(null); }

    public ModsScreen(Mod mod) {
        super(new ModsScreenComponent(mod));
        this.component = (ModsScreenComponent) root;
    }

    public void switchMod(Mod mod) {
        component.switchMod(mod, false);
    }

    public static class ModsScreenComponent extends Component {
        private Mod mod;
        private ModsScroll scroll;
        private TextFieldComponent search;
        private final int paneW = 420; 
        private final int paneH = 280;

        public ModsScreenComponent(Mod startingMod) {
            this.mod = startingMod;

            scroll = new ModsScroll(this);
            add(scroll, (c, b) -> {
                int px = (getScreen().width - paneW) / 2;
                int py = (getScreen().height - paneH) / 2;
                return new Rectangle(px + 10, py + 40, paneW - 20, paneH - 75);
            });

            search = new TextFieldComponent(100, false).placeholder("Search");
            
            add(new Component() {
                @Override
                public void render(ComponentRenderInfo info) {

                    drawRect(0, 0, 45, 20, 0xCCCCCC);
                    
                    drawRect(0, 0, 45, 1, 0xFFFFFFFF); 
                    drawRect(0, 19, 45, 20, 0xFFFFFFFF); 
                    drawRect(0, 0, 1, 20, 0xFFFFFFFF); 
                    drawRect(44, 0, 45, 20, 0xFFFFFFFF);

                    Minecraft mc = Minecraft.getMinecraft();
                    String txt = "Done";
                    mc.fontRendererObj.drawStringWithShadow(txt, (45 - mc.fontRendererObj.getStringWidth(txt)) / 2, 6, 0xFFFFFFFF);
                }

                @Override
                public boolean mouseClickedAnywhere(ComponentRenderInfo info, int button, boolean inside, boolean windowNotFocused) {
                    if (inside && button == 0) {
                        Utils.playClickSound(true);
                        if (mod == null) getScreen().close();
                        else switchMod(null, false);
                        return true;
                    }
                    return super.mouseClickedAnywhere(info, button, inside, windowNotFocused);
                }
            }, (c, b) -> {
                int px = (getScreen().width - paneW) / 2;
                int py = (getScreen().height - paneH) / 2;
                
                int x = px + paneW - 45 - 10; 
                
                int y = py + paneH - 20 - 10;
                
                return new Rectangle(x, y, 45, 20);
            });

            switchMod(startingMod, true);
        }

        public Mod getMod() { return mod; }
        public String getQuery() { return search.getText(); }

        @Override
        public boolean keyPressed(ComponentRenderInfo info, int keyCode, char character) {
            boolean result = super.keyPressed(info, keyCode, character);
            if(search.isFocused()) {
                scroll.load();
            }
            return result;
        }

        public void switchMod(Mod mod, boolean first) {
            this.mod = mod;
            scroll.load();
            if(mod == null) {
                add(search, (c, b) -> {
                    int px = (getScreen().width - paneW) / 2;
                    int py = (getScreen().height - paneH) / 2;
                    return new Rectangle(px + paneW - 110, py + 15, 100, 15);
                });
            } else if(!first) {
                remove(search);
            }
        }

        @Override
        public void render(ComponentRenderInfo info) {
            int px = (getScreen().width - paneW) / 2;
            int py = (getScreen().height - paneH) / 2;
            drawRect(px, py, px + paneW, py + paneH, 0xCC000000); 
            super.render(info);
        }
    }
}