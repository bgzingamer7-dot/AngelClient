package io.github.solclient.client.mod.impl.hud;

import com.google.gson.annotations.Expose;
import io.github.solclient.client.mod.impl.SolClientSimpleHudMod;
import io.github.solclient.client.mod.impl.core.CpsMonitor;
import io.github.solclient.client.mod.option.annotation.Option;
import io.github.solclient.client.util.MinecraftUtils;
import io.github.solclient.client.util.data.*;

public class CpsMod extends SolClientSimpleHudMod {

	@Expose
	@Option
	private boolean rmb;

	@Expose
	@Option
	private boolean showLabel = true;

	@Expose
	@Option
	private Colour separatorColour = new Colour(64, 64, 64);

	@Override
	public void render(Position position, boolean editMode) {
		super.render(position, editMode);
		
		if (rmb) {
			String prefix = background ? "" : "[";
			String suffix = background ? "" : "]";
			String label = showLabel ? " CPS" : "";

			int width = font.getStringWidth(prefix + CpsMonitor.LMB.getCps() + " | " + CpsMonitor.RMB.getCps() + label + suffix) - 2;

			int xPos = position.getX() + (53 / 2) - (width / 2);
			int yPos = position.getY() + 4;

			xPos = font.draw(prefix + CpsMonitor.LMB.getCps(), xPos, yPos, textColour.getValue(), shadow);

			xPos--;
			if (shadow) {
				xPos--;
			}

			xPos += font.getCharWidth(' ');

			MinecraftUtils.drawVerticalLine(xPos, yPos - 1, yPos + 7, separatorColour.getValue());

			if (shadow) {
				MinecraftUtils.drawVerticalLine(xPos + 1, yPos, yPos + 8, separatorColour.getShadowValue());
			}

			xPos += 1;
			xPos += font.getCharWidth(' ');

			font.draw(CpsMonitor.RMB.getCps() + label + suffix, xPos, yPos, textColour.getValue(), shadow);
		}
	}

	@Override
	public String getText(boolean editMode) {
		String label = showLabel ? " CPS" : "";
		return rmb ? "" : CpsMonitor.LMB.getCps() + label;
	}

}