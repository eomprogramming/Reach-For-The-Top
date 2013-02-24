package com.aly.reach;

import android.annotation.SuppressLint;
import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The networking code.
 * @author Ian Dewan
 */
public class NetworkAccess implements Closeable {
	public static final int LIGHT_STATUS = 0;
	public static final int BUZZ_STATUS = 1;
	
    private static final int PORT = 6430;
    private static final int TIMEOUT = 75;
    private static final int RETRIES = 3;
    
    // TYPE codes
    private static final byte CONFIRMATION = (byte) 0xC0;
    private static final byte JOIN = (byte) 0x07;
    private static final byte JOIN_RESPONSE = (byte) 0x97;
    private static final byte BUZZ = (byte) 0xB2;
    private static final byte STATE = (byte) 0x5A;
    
	private DatagramSocket connection;
	private Receiver r;
	private AtomicInteger nextPacketID = new AtomicInteger(1);
	private boolean[] statusFlags = new boolean[] {false, true};
	
	/**
	 * Create a new NetworkAccess object connecting to the given IP address.
	 * @param ip The IP address to connect to.
	 * @throws SocketException If an error occurs connecting the internal socket.
	 */
	public NetworkAccess(InetAddress ip) throws SocketException {
		connection = new DatagramSocket();
		connection.connect(new InetSocketAddress(ip, PORT));
		connection.setSoTimeout(0);
		r = new Receiver();
		new Thread(r).start();
	}

	/**
	 * Get the IP address the system is connecting to.
	 * @return The IP address of the server.
	 */
	public InetAddress getIPAddress() {
		return connection.getInetAddress();
	}
	
	/**
	 * Send a buzz to the server.
	 * @throws IOException Something goes wrong.
	 */
	public void buzz() throws IOException {
		if (!statusFlags[BUZZ_STATUS])
			return;
		byte[] data = new byte[12];
		data[0] = BUZZ;
		data[1] = (byte) 0x80; // No confirm bit.
		send(data);
	}
	
	/**
	 * Get the status flags. Any threads waiting on the returned object will be notified of changes.
	 * @return The status flags, indexed by the <code>_STATUS</code> constants.
	 */
	public boolean[] getStatusFlags() {
		return statusFlags;
	}
	
	/**
	 * Join the given team.
	 * @param team The team index
	 * @throws IOException Communication error.
	 * @throws ReachException Unable to join specified team.
	 */
	public void join(int team) throws IOException, ReachException {
		if (team > 255)
			throw new IllegalArgumentException("Team index to large.");
		byte[] data = new byte[12];
		data[0] = JOIN;
		data[4] = (byte) team;
		if (!send(data))
			throw new IOException("No confirmation from server.");
		int pID = (data[2] << 8) | (data[3]);
		int error = r.awaitJoinResponse(pID);
		if (error != 0)
			throw new ReachException(ReachException.Situation.JOIN, error);
	}
	
	/**
	 * Send the given data to the server and await confirmation.
	 * @param packet The data to send; the two packetID bytes will be filled by this method, and can be set to anything.
	 * @return Whether or not confirmation was received in time.
	 * @throws IOException Something goes wrong.
	 */
	private boolean send(byte[] packet) throws IOException {
		if (packet.length != 12)
			throw new IllegalArgumentException("Packet must be 12 bytes");
		int packetID = nextPacketID.addAndGet(2);
		packet[3] = (byte) (packetID & 0xFF);
		packet[2] = (byte) ((packetID & 0xFF00) >> 8);
		DatagramPacket out = new DatagramPacket(packet, 12, connection.getInetAddress(), connection.getPort());
		connection.send(out);
		if ((packet[1] & 0x80) != 0)
			return true;
		return r.awaitConfirmation(packetID);
	}

	@Override
	public void close() throws IOException {
		r.stop();
		connection.close();
		connection.disconnect();
	}
	
	/**
	 * A Runnable that manages the input from the network.
	 */
	private class Receiver implements Runnable {
		private volatile boolean redFlag = false;
		private BitSet awaitingConf = new BitSet();
		private BitSet received = new BitSet();
		private BitSet awaitingResponse = new BitSet();
		private List<Integer> joinErrors = new ArrayList<Integer>(5);
		
		@Override
		public void run() {
			byte[] buffer = new byte[12];
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
			ReachDatagram data;
			
			while (!redFlag) {
				try {
					connection.receive(pack);
				} catch (IOException e) {
					continue; // Is this the right thing to do?
				}
				data = new ReachDatagram(buffer);
				if (received.get(data.getPacketID()))
					continue;
				else
					received.set(data.getPacketID());
				switch (data.getType()) {
				case CONFIRMATION:
					synchronized(awaitingConf) {
						awaitingConf.clear(data.getPacketID());
						awaitingConf.notifyAll();
					}
					break;
				case JOIN:
					// Should never happen; JOIN can only be sent by client.
					break;
				case JOIN_RESPONSE:
					try {
						confirm(Arrays.copyOfRange(buffer, 2, 4));
					} catch (IOException e) {
						// Nothing one can do.
					}
					synchronized (joinErrors) {
						joinErrors.add(data.getResponseTo(), Integer.valueOf(data.getJoinError()));
					}
					synchronized (awaitingResponse) {
						awaitingResponse.clear(data.getResponseTo());
						awaitingResponse.notifyAll();
					}
					break;
				case BUZZ:
					// Should never happen; BUZZ can only be sent by client.
					break;
				case STATE:
					try {
						confirm(Arrays.copyOfRange(buffer, 2, 4));
					} catch (IOException e) {
						// Nothing one can do.
					}
					synchronized (statusFlags) {
						statusFlags[LIGHT_STATUS] = data.getLightBit();
						statusFlags[BUZZ_STATUS] = data.getBuzzBit();
						statusFlags.notifyAll();
					}
					break;
				default:
					// Invalid datagram type.
					break;
				}
				
				Arrays.fill(buffer, (byte) 0);
			}
		}
		
		/**
		 * Stop the train!
		 */
		public void stop() {
			redFlag = true;
		}
		
		public boolean awaitConfirmation(int packetID) {
			if ((packetID > 65535) || (packetID < 0))
				throw new IllegalArgumentException("Packet ID out of range: "+packetID);
			synchronized (awaitingConf) {
				awaitingConf.set(packetID);
				for (int i = 0; i < RETRIES; i++) {
					try {
						awaitingConf.wait(TIMEOUT);
					} catch (InterruptedException e) {
						i--;
						continue;
					}
					if (awaitingConf.get(packetID)) {
						i--;
						continue;
					} else {
						return true;
					}
				}
			}
			return false;
		}
		
		public int awaitJoinResponse(int pID) {
			synchronized (awaitingResponse) {
				awaitingResponse.set(pID);
				do {
					try {
						awaitingResponse.wait();
					} catch (InterruptedException e) {
						continue;
					}
				} while (awaitingResponse.get(pID));
			}
			synchronized (joinErrors) {
				return joinErrors.get(pID).intValue();
			}
		}
		
		private void confirm(byte[] packetID) throws IOException {
			if (packetID.length != 2)
				throw new IllegalArgumentException("Invalid packetID in confirm.");
			byte[] data = new byte[12];
			data[0] = CONFIRMATION;
			data[2] = packetID[0];
			data[3] = packetID[1];
			DatagramPacket out = new DatagramPacket(data, 12, connection.getInetAddress(), connection.getPort());
			connection.send(out);
		}
	}
	
	/**
	 * Represents a datagram to be sent or received in memory.
	 */
	@SuppressLint("UseValueOf")
	private static class ReachDatagram {
		private byte[] data = null;
		private int packetID = -1;
		private byte type = 0;
		private Boolean lightBit = null;
		private Boolean buzzBit = null;
		private int joinError = -1;
		private int responseTo = -1;
		
		/**
		 * Create a new ReachDatagram from the given raw data.
		 * @param data The raw datagram data.
		 */
		public ReachDatagram(byte[] data) {
			if (data.length != 12)
				throw new IllegalArgumentException("Datagram must be 12 bytes long.");
			this.data = data;
		}
		
		/**
		 * Get the packet ID of the datagram.
		 * @return The packet ID.
		 */
		public int getPacketID() {
			if (packetID < 0)
				if (data != null)
					return (packetID = ((data[2] << 8) | data[3]));
				else
					throw new IllegalStateException("No raw data and no value for packetID.");
			else
				return packetID;
		}
		
		/**
		 * Get the type byte of a datagram.
		 * @return The type.
		 */
		public byte getType() {
			if (type == 0)
				if (data != null)
					return (type = data[0]);
				else
					throw new IllegalStateException("No raw data and no value for type.");
			else
				return type;
		}
		
		public boolean getLightBit() {
			if (lightBit == null)
				if (data != null)
					return (lightBit = new Boolean((data[4] & 0x80) != 0)).booleanValue();
				else
					throw new IllegalStateException("No raw data and no value for lightBit.");
			else
				return lightBit.booleanValue();
		}
		
		public boolean getBuzzBit() {
			if (buzzBit == null)
				if (data != null)
					return (buzzBit = new Boolean((data[4] & 0x40) != 0)).booleanValue();
				else
					throw new IllegalStateException("No raw data and no value for buzzBit.");
			else
				return buzzBit.booleanValue();
		}
		
		public int getJoinError() {
			if (joinError < 0)
				if (data != null)
					return (joinError = data[6]);
				else
					throw new IllegalStateException("No raw data and no value for joinError.");
			else
				return joinError;
		}
		
		public int getResponseTo() {
			if (responseTo < 0)
				if (data != null)
					return (responseTo = ((data[4] << 8) | data[5]));
				else
					throw new IllegalStateException("No raw data and no value for responseTo.");
			else
				return responseTo;
		}
	}
}
