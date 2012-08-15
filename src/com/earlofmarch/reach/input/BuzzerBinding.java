package com.earlofmarch.reach.input;

/**
 * A class that processes input from Buzz buzzers.
 * @author Ian Dewan
 *
 */
public interface BuzzerBinding {
	/**
	 * Set which buttons will trigger a buzz.
	 * If this method is not called with some values before the first buzz, the buzz
	 * will be ignored.
	 * @param red Whether Red will trigger.
	 * @param blue Whether Blue will trigger.
	 * @param green Whether Green will trigger.
	 * @param orange Whether Orange will trigger.
	 * @param yellow Whether Yellow will trigger.
	 */
	public void setButtonSensitivity(boolean red, boolean blue, boolean green,
			boolean orange, boolean yellow);
	
	/**
	 * Clear the current buzzed status.
	 */
	public void clear();
	
	/**
	 * Return the current buzzed buzzer.
	 * @return A ordered pair of (handset #, buzzer #), or null if no buzzer is buzzed.
	 */
	public Pair<Integer, Integer> getCurrentBuzzed();
	
	/**
	 * Register a handler to be called if a buzz occurs.
	 * If the same handler is added twice the result is undefined.
	 * Handlers may be run in separate threads: they should be thread safe.
	 * @param r The runnable to be run.
	 */
	public void registerBuzzHandler(Runnable r);
	
	/**
	 * Register a handler to be if a handset is unplugged.
	 * If the same handler is added twice the result is undefined.
	 * Handlers may be run in separate threads: they should be thread safe.
	 * @param r The runnable to be run.
	 * @param r The runnable to be run.
	 */
	public void registerUnplugHandler(Runnable r);
}
