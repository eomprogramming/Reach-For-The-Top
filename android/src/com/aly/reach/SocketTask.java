package com.aly.reach;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SocketTask extends AsyncTask<String, Void, String> {

    Activity activity;
    NetworkAccess net;
    
    public SocketTask(Activity a, String ipaddress) {
    	super();
    	try {
			this.net = new NetworkAccess(InetAddress.getAllByName(ipaddress)[0]);
		} catch (Exception e) {
			// TODO: Exception handling.
		}
    	activity = a;
    }
    
    public void setIP(String newIP) throws UnknownHostException, SocketException {
    	try {
			net.close();
		} catch (IOException e) {
			// Never happens.
		}
    	net = new NetworkAccess(InetAddress.getAllByName(newIP)[0]);
    }

    protected String doInBackground(String... unused) {
    	Log.d("reach", "Connecting to "+net.getIPAddress());
		/*DatagramSocket mySocket;
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
		return "Cancelled";*/
    	return "Unimplemented.";
		
    }

    protected void onPostExecute(String s) {
    	Log.d("reach", "Finished normally");			
    }
    
   
 }
