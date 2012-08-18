package com.earlofmarch.reach.input;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ian Dewan
 *
 */
public class BuzzerBindingFactory {
	
	public static BuzzerBinding getBinding() throws IOException {
		if (System.getProperty("os.name").contains("Windows")) {
			return windows();
		} else { // assume Linux
			//TODO
			return null;
		}
	}
	
	private static WindowsBuzzerBinding windows() throws IOException {
		if (!WindowsBuzzerBinding.serverIsRunning()) {
			try {
				Runtime.getRuntime().exec("./glue.exe");
			} catch (IOException e) {
				//TODO: logging policy
				Logger.getAnonymousLogger().log(Level.SEVERE,
						"Unable to start glue.exe", e);
				throw e;
			}
		}
		return new WindowsBuzzerBinding();
	}
	
}
