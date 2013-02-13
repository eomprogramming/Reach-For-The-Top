package com.aly.reach;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SocketTask extends AsyncTask<String, Void, String> {

    Activity activity;
    InetAddress ip;
    private static final int PORT = 6430;
    private static final int TIMEOUT = 250;
    private static final int RETRIES = 3;
    private static final int CONFIRMATION = (byte) 57;
    
    public SocketTask(Activity a, String ipaddress){
    	super();
    	try {
			this.ip = InetAddress.getAllByName(ipaddress)[0];
		} catch (UnknownHostException e) {
			
		}
    	activity = a;
    }
    
    public void setIP(String newIP) throws UnknownHostException {
    	ip = InetAddress.getAllByName(newIP)[0];
    }

    protected String doInBackground(String... unused) {
    	Log.d("reach", "Connecting to "+ip);
		DatagramSocket mySocket;
		while(!this.isCancelled()){
			try {
				mySocket = new DatagramSocket();
				mySocket.connect(ip, PORT);
				send("Connection Established!", mySocket);
				mySocket.close();
				return "Success";
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("reach", "IOException!");
				return "Error";
			}
		}
		return "Cancelled";
		
    }

    protected void onPostExecute(String s) {
    	Log.d("reach", "Finished normally");			
    }
    
    /**
     * Pack data into a datagram packet to be sent over the network.
     * Uses utf8 as a string encoding format.
     * @param data
     * @return
     */
    private DatagramPacket packetize(String data) {
    	byte[] encodedData = null;
    	try {
			encodedData = data.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// This can never happen.
		}
    	return new DatagramPacket(encodedData, encodedData.length, ip, PORT);
    }
    
    private boolean send(String data, DatagramSocket sock) throws IOException {
    	byte[] returnData = {(byte) 0};
    	DatagramPacket out = packetize(data);
    	DatagramPacket in = new DatagramPacket(returnData, 1);
    	boolean success = false;
    	
    	for (int i = 0; i < RETRIES; i++) {
    		sock.send(out);
    		sock.setSoTimeout(TIMEOUT);
    		try {
    			sock.receive(in);
    		} catch (InterruptedIOException e) {
    			continue;
    		}
    		if (returnData[0] == CONFIRMATION) {
    			success = true;
    			break;
    		}
    	}
    	
    	return success;
    }
 }
