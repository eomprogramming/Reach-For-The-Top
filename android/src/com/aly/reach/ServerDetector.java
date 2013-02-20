package com.aly.reach;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import com.solers.slp.*;

/**
 * Use SLP to detect Reach servers on the local network.
 * @author Ian Dewan
 */
public class ServerDetector {
	/**
	 * The service URL prefix for Reach servers.
	 */
	private static final String URLPREFIX = "service:game:eomreach";
	/**
	 * The scopes needed by the SLP API.
	 */
	private static final Vector<String> SCOPES = new Vector<String>();
	/**
	 * The names of our custom SLP attributes.
	 */
	private static final Vector<String> ATTRS = new Vector<String>();
	static {
		SCOPES.add("DEFAULT");
		ATTRS.add("gameName");
		ATTRS.add("teamSlots");
	}
	
	private Locator loc;
	private ServiceType lookingFor;
	
	/**
	 * Create a new ServerDetector.
	 * @throws ServiceLocationException I have no idea what might cause this.
	 */
	public ServerDetector() throws ServiceLocationException {
		loc = ServiceLocationManager.getLocator(Locale.CANADA);
		lookingFor = new ServiceType(URLPREFIX);
	}
	
	/**
	 * Generate a list of available games from the network.
	 * @return A Map containing all available games and their IP addresses.
	 * @throws ServiceLocationException I have no idea what might cause this.
	 */
	public Map<GameDescriptor,InetAddress> getGames() throws ServiceLocationException {
		Map<GameDescriptor,InetAddress> ret = new HashMap<GameDescriptor,InetAddress>();
		ServiceLocationEnumeration results = loc.findServices(lookingFor, SCOPES, "");
		ServiceLocationEnumeration attribs;
		Object o, p;
		ServiceURL curr;
		ServiceLocationAttribute a;
		String gName = null;
		int[] teams = null;
		byte[] tmp = null;
		
		while ((o = results.nextElement()) != null) {
			curr = (ServiceURL) o;
			attribs = loc.findAttributes(curr, SCOPES, ATTRS);
			while ((p = attribs.nextElement()) != null) {
				a = (ServiceLocationAttribute) p;
				if (a.getId().equals("gameName"))
					gName = (String) a.getValues().get(0);
				else if (a.getId().equals("teamSlots")) {
					tmp = (byte[]) a.getValues().get(0);
					teams = new int[tmp.length];
					for (int i = 0; i < tmp.length; i++)
						teams[i] = tmp[i];
				}
			}
			try {
				ret.put(new GameDescriptor(gName,teams), InetAddress.getAllByName(curr.getHost())[0]);
			} catch (UnknownHostException e) {
				// Can't find server on network, so skip it.
				continue;
			}
		}
		
		return ret;
	}
}
