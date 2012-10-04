package com.earlofmarch.reach.input;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.logging.*;

/**
 * Windows access to buzzers through BuzzIO.
 * @author Ian Dewan
 *
 */
class WindowsBuzzerBinding extends AbstractBuzzerBinding {
	private static final String PIPENAME = "eomreachpipe";
	
	private HashMap<String, Boolean> sensitivities = new HashMap<String, Boolean>();
	private volatile Pair<Integer, Integer> currBuzz = null;
	private RandomAccessFile pipe;
	private PipeReader pr;
	
	private Object stateLock = new Object();
	
	public WindowsBuzzerBinding() throws IOException {
		pipe = new RandomAccessFile("\\\\.\\pipe\\" + PIPENAME, "rw");
		try {
			pipe.writeChars("reload");
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.WARNING, "Unable to reload buzzer",
					e);
		}
		
		pr = new PipeReader();
		pr.start();
	}
	
	@Override
	public void setButtonSensitivity(boolean red, boolean blue, boolean green,
			boolean orange, boolean yellow) {
		synchronized(sensitivities) {
			sensitivities.put("red", red);
			sensitivities.put("blue", blue);
			sensitivities.put("green", green);
			sensitivities.put("orange", orange);
			sensitivities.put("yellow", yellow);
		}
	}
	
	@Override
	public void clear() {
		synchronized (stateLock) {
			pr.unlight(currBuzz.getFirst(), currBuzz.getSecond());
			currBuzz = null;
		}
	}
	
	@Override
	public Pair<Integer, Integer> getCurrentBuzzed() {
		synchronized (stateLock) {
			return currBuzz;
		}
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
			synchronized (sensitivities) {
			synchronized (stateLock) {
				if (sensitivities.containsKey(button) &&
						sensitivities.get(button).equals(true) &&
						currBuzz == null) {
					try {
						pipe.writeChars("light " + h + ":" + b);
					} catch (IOException e) {
						Logger.getAnonymousLogger().log(Level.WARNING,
								"Unable to light buzzer light", e);
					}
					currBuzz = new Pair<Integer, Integer>(h, b);
					WindowsBuzzerBinding.this.buzz();
				}
			}
			}
		}
		
		public void unlight(Integer h, Integer b) {
			try {
				pipe.writeChars("unlight " + h + ":" + b);
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.WARNING,
						"Unable to unlight buzzer light", e);
			}
		}
	}
	
	public static boolean serverIsRunning() {
		return new File("\\\\.\\pipe\\" + PIPENAME).exists();
	}
}
