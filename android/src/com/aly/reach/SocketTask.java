package com.aly.reach;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SocketTask extends AsyncTask<String, Void, String> {

    Activity activity;
    String ip;
    
    public SocketTask(Activity a, String ipaddress){
    	super();
    	this.ip = ipaddress;
    	activity = a;
    }
    
    public void setIP(String newIP){
    	ip = newIP;
    }

    protected String doInBackground(String... unused) {
    	Log.d("reach", "Connecting to "+ip);
		Socket mySocket;
		while(!this.isCancelled()){
			try {
				
				if(ip.contains(":"))
					mySocket = new Socket(ip.substring(0, ip.indexOf(":")),Integer.parseInt(ip.substring(ip.indexOf(":")+1, ip.length())));
				else
					mySocket = new Socket(ip, 6430);					
				PrintWriter p = new PrintWriter (mySocket.getOutputStream(),true);
				p.write("Connection Established!");
				p.close();
				mySocket.close();
				return "Success";
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return "Error";
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
 }
