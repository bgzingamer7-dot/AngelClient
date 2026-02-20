package io.github.solclient.client.ui.screen;

import io.github.solclient.client.ui.component.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

public class SolClientMainMenu extends PanoramaBackgroundScreen {

        private static final ResourceLocation LOGO = new ResourceLocation("sol_client", "textures/angelclient/logo.png");
    private static final ResourceLocation ICON_CONFIG = new ResourceLocation("sol_client", "textures/angelclient/settings.png");
    private static final ResourceLocation ICON_COSMETICS = new ResourceLocation("sol_client", "textures/angelclient/cosmetics.png");
    private static final ResourceLocation ICON_REPLAY = new ResourceLocation("sol_client", "textures/angelclient/replay.png");
    private static final ResourceLocation BACKGROUND = new ResourceLocation("sol_client", "textures/angelclient/background.png");
    
    private static final ResourceLocation[] SLIDES = {
        new ResourceLocation("sol_client", "textures/angelclient/slide_1.png"),
        new ResourceLocation("sol_client", "textures/angelclient/slide_2.png"),
        new ResourceLocation("sol_client", "textures/angelclient/slide_3.png"),
    };
    private static final String[] SLIDE_TEXTS = { "Compre Beta em nosso Discord!", "100 Membros no Discord!", "Bem-Vindo! Player!" };

    public SolClientMainMenu() {
        super(new AngelMenuComponent());
    }

    public static class AngelMenuComponent extends Component { 
        private int currentSlide = 0;
        private long lastSlideSwitch = System.currentTimeMillis();

        @Override
        public void render(ComponentRenderInfo info) {
            Minecraft mc = Minecraft.getMinecraft();
            int w = screen.width;
            int h = screen.height;

            // Desenhar Fundo Principal
            mc.getTextureManager().bindTexture(BACKGROUND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            screen.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, w, h, (float)w, (float)h);

            int mx = Mouse.getX() * w / mc.displayWidth;
            int my = h - Mouse.getY() * h / mc.displayHeight - 1;

            int splitX = (int) (w * 0.30);

            drawRect(0, 0, splitX, h, 0xFF0A0A0A);

            // 2. Logo
            mc.getTextureManager().bindTexture(LOGO);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            screen.drawModalRectWithCustomSizedTexture(splitX / 2 - 40, 30, 0, 0, 80, 30, 80.0F, 30.0F);
            GL11.glDisable(GL11.GL_BLEND);

            // 3. Botões
            drawTextBtn(mc, "Singleplayer", splitX / 2, h / 2 - 20, mx, my);
            drawTextBtn(mc, "Multiplayer", splitX / 2, h / 2 + 10, mx, my);

            // 4. Ícones
            int iconSize = 24;
            int iconY = h - 40;
            int gap = 15;
            int startX = (splitX - (iconSize * 3 + gap * 2)) / 2;

            drawIcon(mc, ICON_CONFIG, startX, iconY, iconSize, mx, my);
            drawIcon(mc, ICON_COSMETICS, startX + iconSize + gap, iconY, iconSize, mx, my);
            drawIcon(mc, ICON_REPLAY, startX + (iconSize + gap) * 2, iconY, iconSize, mx, my);

            // 5. Slider
            drawSlider(mc, splitX, w, h, mx, my);

            // 6. Botão Fechar
            boolean hoverX = mx >= w - 25 && mx <= w - 5 && my >= 5 && my <= 25;
            drawRect(w - 25, 5, w - 5, 25, hoverX ? 0xFFFF0000 : 0xAAFF0000);
            mc.fontRendererObj.drawString("x", w - 17, 9, 0xFFFFFFFF);

            super.render(info);
        }

     private void drawSlider(Minecraft mc, int splitX, int w, int h, int mx, int my) {
    int centerX = splitX + (w - splitX) / 2;
    int centerY = h / 2;
    int sw = 280;
    int sh = 150;

    if (currentSlide >= SLIDES.length) {
        currentSlide = 0;
    }

    // Auto-troca 3s
    if (System.currentTimeMillis() - lastSlideSwitch > 3000) {
        currentSlide = (currentSlide + 1) % SLIDES.length;
        lastSlideSwitch = System.currentTimeMillis();
    }

    // Borda
    drawRect(centerX - sw/2 - 2, centerY - sh/2 - 2, centerX + sw/2 + 2, centerY + sh/2 + 2, 0x50FFFFFF);
    
    if (SLIDES.length > 0) {
        mc.getTextureManager().bindTexture(SLIDES[currentSlide]);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        screen.drawModalRectWithCustomSizedTexture(centerX - sw/2, centerY - sh/2, 0, 0, sw, sh, (float)sw, (float)sh);

        if (currentSlide < SLIDE_TEXTS.length) {
            String txt = SLIDE_TEXTS[currentSlide];
            mc.fontRendererObj.drawString(txt, centerX - mc.fontRendererObj.getStringWidth(txt)/2, centerY + sh/2 + 5, 0xFFFFFFFF);
        }
    }
}
        private void drawTextBtn(Minecraft mc, String txt, int x, int y, int mx, int my) {
            int tw = mc.fontRendererObj.getStringWidth(txt);
            boolean hov = mx >= x - tw/2 && mx <= x + tw/2 && my >= y && my <= y + 10;
            mc.fontRendererObj.drawString(txt, x - tw/2, y, hov ? 0xFF55FFFF : 0xFFFFFFFF);
        }

        private void drawIcon(Minecraft mc, ResourceLocation res, int x, int y, int size, int mx, int my) {
            boolean hov = mx >= x && mx <= x + size && my >= y && my <= y + size;
            mc.getTextureManager().bindTexture(res);
            GL11.glColor4f(1f, 1f, 1f, hov ? 1f : 0.6f);
            screen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, size, size, (float)size, (float)size);
        }

        @Override
        public boolean mouseClicked(ComponentRenderInfo info, int button) {
            if (button == 0) {
                Minecraft mc = Minecraft.getMinecraft();
                int w = screen.width;
                int h = screen.height;
                int mx = Mouse.getX() * w / mc.displayWidth;
                int my = h - Mouse.getY() * h / mc.displayHeight - 1;
                int splitX = (int) (w * 0.30);

                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));

                if (mx < splitX) {
                    if (my > h/2 - 25 && my < h/2 - 5) mc.displayGuiScreen(new GuiSelectWorld(screen));
                    if (my > h/2 + 5 && my < h/2 + 25) mc.displayGuiScreen(new GuiMultiplayer(screen));
                    if (my > h - 45 && mx > (splitX/2 - 50) && mx < splitX/2) mc.displayGuiScreen(new GuiOptions(screen, mc.gameSettings));
                }
                
                if (mx > w - 30 && my < 30) mc.shutdown();
            }
            return super.mouseClicked(info, button);
        }
    }
}