package io.github.solclient.client.util;

public final class OptiFineCompat {

	private static Boolean loaded;

	public static boolean isLoaded() {
		if (loaded != null) {
			return loaded;
		}

		try {
			Class.forName("optifine.Installer");
			loaded = true;
		} catch (Throwable t) {
			loaded = false;
		}

		return loaded;
	}
}
