package com.aly.reach;

import java.io.IOException;
import java.net.UnknownHostException;

import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment that represents on of the two tabs in the app.
 */
public class TabFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	static MainActivity parent;

	public TabFragment() {
		
	}
	
	public static String ip = "127.0.0.1";
	public static SocketTask st = new SocketTask(new MainActivity(),ip);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//If it's the second page, load that layout
		if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
			View v = inflater.inflate(R.layout.stats, container, false);
			TextView text = (TextView)v.findViewById(R.id.text);
			text.setText("");
						
			Button check = (Button)v.findViewById(R.id.check);
			check.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {					
					try{
						View parent =(View)v.getParent();
						TextView text = (TextView)parent.findViewById(R.id.text);
						text.setText(""+TabFragment.parent.getBroadcastAddress());
					} catch (IOException e) {}
				}				
			});
			return v;
		}
		
		//If it's the first page, load the buzzer layout
		
		View v = inflater.inflate(R.layout.buzzer, container, false);
		EditText inputIP = (EditText)v.findViewById(R.id.ip);
		ip = inputIP.getText().toString();
					
		Button connect = (Button)v.findViewById(R.id.connect);
		connect.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {					
				if(st.getStatus()==Status.RUNNING){
					st.cancel(true);
					Toast.makeText(getActivity(), "Current connection cancelled", Toast.LENGTH_SHORT).show();
					Log.d("reach", "cancelled");
				}else{
					EditText inputIP = (EditText)((View)v.getParent()).findViewById(R.id.ip);
					ip = inputIP.getText().toString();
					try {
						st.setIP(ip);
					} catch (UnknownHostException e) {
						// TODO I have no idea what to do here.
						e.printStackTrace();
					}
					Toast.makeText(getActivity(), "Using IP: "+ip, Toast.LENGTH_SHORT).show();
					if(st.getStatus()==Status.FINISHED){
						st = new SocketTask(new MainActivity(),ip);
						st.execute();
					}else{
						st.execute();	
					}					
				}
			}				
		});
		return v;
	}
	
}