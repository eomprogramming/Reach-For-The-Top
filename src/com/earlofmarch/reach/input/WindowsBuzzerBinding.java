package com.earlofmarch.reach.input;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.logging.*;

/**
 * @author Ian Dewan
 *
 */
class WindowsBuzzerBinding extends AbstractBuzzerBinding {
	private static final String PIPENAME = "eomreachpipe";
	
	private HashMap<String, Boolean> sensitivities = new HashMap<String, Boolean>();
	private Pair<Integer, Integer> currBuzz = null;
	private Process glue;
	private RandomAccessFile pipe;
	
	public WindowsBuzzerBinding() throws IOException {
		glue = Runtime.getRuntime().exec("./glue.exe");
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable () {
			public void run() { glue.destroy(); }
		}));
		
		pipe = new RandomAccessFile("\\\\.\\pipe\\" + PIPENAME, "rw");
		
		new PipeReader().start();
	}
	
	@Override
	public void setButtonSensitivity(boolean red, boolean blue, boolean green,
			boolean orange, boolean yellow) {
		sensitivities.put("red", red);
		sensitivities.put("blue", blue);
		sensitivities.put("green", green);
		sensitivities.put("orange", orange);
		sensitivities.put("yellow", yellow);
	}
	
	@Override
	public void clear() {
		currBuzz = null;
	}
	
	@Override
	public Pair<Integer, Integer> getCurrentBuzzed() {
		return currBuzz;
	}
	
	private class PipeReader extends Thread {
		
		public void run() {
			String input;
			String[] parts;
			String[] subparts;
			try {
				input = pipe.readLine();
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE,
						"IO error getting data from glue.exe.", e);
				return;
			}
			while(input != null) {
				try {
					parts = input.split(" |\t");
					if (parts[0].equals("press")) {
						subparts = parts[1].split(":");
						buzz(Integer.parseInt(subparts[0]),
								Integer.parseInt(subparts[1]), subparts[2]);
					} else if (parts[0].equals("unplugged")) {
						unplug();
					} else if (parts[0].equals("error")) {
						Logger.getAnonymousLogger().log(Level.WARNING,
							"Error occured in glue.exe:" + input);
					}
				} catch (Exception e) {
					//TODO: Global error logging policy.
					Logger.getAnonymousLogger().log(Level.WARNING,
							"Error occurred parsing glue.exe input.", e);
				}
				try {
					input = pipe.readLine();
				} catch (IOException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE,
						"IO error getting data from glue.exe.", e);
					return;
				}
			}
		}
		
		private synchronized void buzz(int h, int b, String button) {
			if (sensitivities.containsKey(button) &&
					sensitivities.get(button).equals(true)) {
				currBuzz = new Pair<Integer, Integer>(h, b);
				WindowsBuzzerBinding.this.buzz();
			}
		}
		
	}
	
}
