package com.aly.reach;

import java.util.HashMap;
import java.util.Map;

/**
 * An exception specific to the Reach for the Top code.
 * @author Ian Dewan
 */
public class ReachException extends Exception {
	private static final Map<Situation,String[]> messages = new HashMap<Situation,String[]>();
	private static final long serialVersionUID = 1L;
	
	static {
		messages.put(Situation.JOIN, new String[] {"No such team.", "Team already full."});
	}

	/**
	 * Create a new ReachException with the given message.
	 * @param message the message
	 */
	public ReachException(String message) {
		super(message);
	}
	
	/**
	 * Create a new ReachException with a default message.
	 * @param s The situation in which the error occurred.
	 * @param code The error code.
	 */
	public ReachException(Situation s, int code) {
		super(messages.get(s)[code-1]);
	}
	
	/**
	 * A situation in which a ReachException could occur; a namespace for error codes.
	 */
	public static enum Situation {
		JOIN
	}
}
