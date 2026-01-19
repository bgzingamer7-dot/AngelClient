/*
 * Sol Client - an open source Minecraft client
 * Copyright (C) 2021-2023  TheKodeToad and Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.solclient.client.ui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;

public class SplashScreen {

	private static final int FG = 0xFFFFFFFF;
	private static final int BG = 0xFF000000;

	public static final SplashScreen INSTANCE = new SplashScreen();

	private static final int STAGES = 18;

	private final MinecraftClient mc = MinecraftClient.getInstance();
	private int stage;

	public void reset() {
		stage = 0;
	}

	public void draw() {
		if (stage > STAGES) {
			stage = STAGES;
		}

		Window window = new Window(mc);
		int scale = window.getScaleFactor();

		int screenWidth = window.getWidth() * scale;
		int screenHeight = window.getHeight() * scale;

		// ===== CONFIGURAÇÃO DA BARRA =====
		int barWidth = screenWidth * 2 / 3; // larga
		int barHeight = 22;                 // grossa
		int radius = barHeight / 2;         // arredondamento perfeito

		// ===== POSIÇÃO (ABAIXO DA LOGO) =====
		int x = (screenWidth - barWidth) / 2;
		int y = (screenHeight / 2) + 170;

		// ===== FUNDO DA BARRA (ARREDONDADO) =====
		drawRoundedBar(x, y, barWidth, barHeight, radius, BG);

		// ===== PROGRESSO =====
		float progress = (float) stage / STAGES;
		int progressWidth = (int) (barWidth * progress);

		if (progressWidth > 0) {
			drawRoundedProgress(x, y, progressWidth, barHeight, radius, FG);
		}

		stage++;
	}

	// ===============================
	// ===== FUNÇÕES AUXILIARES =====
	// ===============================

	private void drawRoundedBar(int x, int y, int w, int h, int r, int color) {
		// centro
		DrawableHelper.fill(x + r, y, x + w - r, y + h, color);
		// esquerda
		DrawableHelper.fill(x, y + r, x + r, y + h - r, color);
		// direita
		DrawableHelper.fill(x + w - r, y + r, x + w, y + h - r, color);
	}

	private void drawRoundedProgress(int x, int y, int w, int h, int r, int color) {

		// progresso pequeno → SEM arredondar (evita ponta)
		if (w <= r) {
			DrawableHelper.fill(x, y, x + w, y + h, color);
			return;
		}

		// centro
		DrawableHelper.fill(x + r, y, x + w, y + h, color);
		// lado esquerdo arredondado
		DrawableHelper.fill(x, y + r, x + r, y + h - r, color);
	}
}