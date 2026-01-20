package io.github.solclient.client.ui.screen.cosmetics;

import io.github.solclient.client.ui.Theme;
import io.github.solclient.client.ui.component.Component;
import io.github.solclient.client.ui.component.impl.ButtonComponent;
import io.github.solclient.client.ui.screen.PanoramaBackgroundScreen;
import io.github.solclient.client.ui.screen.SolClientMainMenu;
import io.github.solclient.client.util.MinecraftUtils;

public class CosmeticsScreen extends PanoramaBackgroundScreen {

    public CosmeticsScreen() {
        super(new CosmeticsComponent());
    }

    private static class CosmeticsComponent extends Component {

        public CosmeticsComponent() {

            add(
                new ButtonComponent(
                    (component, defaultText) -> "Voltar",
                    Theme.button(),
                    Theme.fg()
                )
                .width(200)
                .onClick((info, button) -> {
                    if (button == 0) {
                        MinecraftUtils.playClickSound(true);
                        mc.setScreen(new SolClientMainMenu());
                        return true;
                    }
                    return false;
                }),
                // 👇 USA O LAYOUT PADRÃO DO CLIENT
                (component, defaultBounds) -> defaultBounds
            );
        }
    }
}
