package com.earlofmarch.reach.input;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * Implements handler registering portions of BuzzerBinding.
 * @author Ian Dewan
 */
abstract class AbstractBuzzerBinding implements BuzzerBinding {
	private LinkedList<Runnable> buzzHandlers = new LinkedList<Runnable>();
	private LinkedList<Runnable> unplugHandlers = new LinkedList<Runnable>();
	private Executor runStuff = new ThreadPoolExecutor(1, 10, 10,
			TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	@Override
	public void registerBuzzHandler(Runnable r) {
		synchronized (buzzHandlers) {
			buzzHandlers.add(r);
		}
	}
	
	@Override
	public void registerUnplugHandler(Runnable r) {
		synchronized (unplugHandlers) {
			unplugHandlers.add(r);
		}
	}
	
	/**
	 * Indicate a buzz.
	 */
	protected void buzz() {
		synchronized (buzzHandlers) {
			for (Runnable r : buzzHandlers)
				runStuff.execute(r);
		}
	}
	
	/**
	 * Indicate an unplugging.
	 */
	protected void unplug() {
		synchronized (unplugHandlers) {
			for (Runnable r: unplugHandlers)
				runStuff.execute(r);
		}
	}
	
}
