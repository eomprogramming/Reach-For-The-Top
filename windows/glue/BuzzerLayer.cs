using System;
using System.Collections.Generic;
using BuzzIO;
using System.Diagnostics;

namespace com.earlofmarch.reach {
	internal class BuzzerLayer : IBuzzerLayer {		
		private List<IBuzzHandsetDevice> devices;
		private ButtonStates[] states;
		private Boolean[][] lights;
		private Callback cb;
		
		public BuzzerLayer() {
			Debug.WriteLine("BuzzerLayer.BuzzerLayer()\t(constructor)\tStarting...");
			devices = BuzzHandsetDevice.FindBuzzHandsets();
			Debug.WriteLine("BuzzerLayer.BuzzerLayer()\t(constructor)\tGot devices:" + devices);
			
			states = new ButtonStates[devices.Count * 4];
			for (int i = 0; i < states.Length; i++)
				states[i] = new ButtonStates();
			
			lights = new Boolean[devices.Count][];
			for (int i = 0; i < lights.Length; i++)
				lights[i] = new Boolean[] {false, false, false, false};
			
			foreach (IBuzzHandsetDevice d in devices) {
				d.DeviceRemoved += unplugged;
				d.ButtonChanged += somethingHappens;
			}
		}
		
		public void setCallback(Callback c) {
			Debug.WriteLine("BuzzerLayer.setCallback()\t"+this+"\tSetting callback...");
			cb = c;
		}
		
		public void lightUp (int handset, int buzzer) {
			Debug.WriteLine("BuzzerLayer.lightUp()\t"+this+
			                "\tLighting buzzer ("+handset+", "+buzzer+")");
			lights[handset][buzzer] = true;
			updateLights(handset);
		}
		
		public void putOut(int handset, int buzzer) {
			Debug.WriteLine("BuzzerLayer.putOut()\t"+this+
			                "\tUnlighting buzzer ("+handset+", "+buzzer+")");
			lights[handset][buzzer] = false;
			updateLights(handset);
		}
		
		private void updateLights(int h) {
			Debug.WriteLine("BuzzerLayer.updateLights()\t"+this+
			                "\tUpdating buzzer lights on "+h);
			devices[h].SetLights(lights[h][0], lights[h][1],
			                     lights[h][2], lights[h][3]);
			Debug.WriteLine("BuzzerLayer.updateLights()\t"+this+
			                "\tNow set to "+lights[h]);
		}
		
		private void unplugged(Object sender, EventArgs e) {
			Debug.WriteLine("BuzzerLayer.unplugged()\t"+this+"\tBuzzer # "+
			                devices.IndexOf((IBuzzHandsetDevice) sender)+" unplugged");
			CallbackArgs result = new CallbackArgs();
			
			result.eventType = CallbackType.UNPLUGGED;
			result.buzzerId = -1;
			result.handsetId = devices.IndexOf((IBuzzHandsetDevice) sender);
			
			cb(result);
		}
		
		private void somethingHappens(Object sender, BuzzButtonChangedEventArgs e) {
			Debug.WriteLine("BuzzerLayer.somethingHappens()\t"+this+"\tEvent on buzzer # "+
			                devices.IndexOf((IBuzzHandsetDevice) sender)+": "+e);
			lock (states) {
				CallbackArgs result;
				result.handsetId = devices.IndexOf((IBuzzHandsetDevice) sender);
			
				ButtonStates[] status = e.Buttons;
				ButtonStates changes;
			
				for (int i = 0; i < 4; i++) {
					result.buzzerId = i;
					changes = xor(status[i], states[i]);
					if (changes.Red) { // red changed
						result.button = Button.RED;
						if (status[i].Red) // red pressed
							result.eventType = CallbackType.BUTTON_PRESS;
						else // red released
							result.eventType = CallbackType.BUTTON_RELEASE;
						cb(result);
					}
					if (changes.Blue) {
						result.button = Button.BLUE;
						if (status[i].Blue)
							result.eventType = CallbackType.BUTTON_PRESS;
						else
							result.eventType = CallbackType.BUTTON_RELEASE;
						cb(result);
					}
					if (changes.Green) {
						result.button = Button.GREEN;
						if (status[i].Green)
							result.eventType = CallbackType.BUTTON_PRESS;
						else
							result.eventType = CallbackType.BUTTON_RELEASE;
						cb(result);
					}
					if (changes.Orange) {
						result.button = Button.ORANGE;
						if (status[i].Orange)
							result.eventType = CallbackType.BUTTON_PRESS;
						else
							result.eventType = CallbackType.BUTTON_RELEASE;
						cb(result);
					}
					if (changes.Yellow) {
						result.button = Button.YELLOW;
						if (status[i].Yellow)
							result.eventType = CallbackType.BUTTON_PRESS;
						else
							result.eventType = CallbackType.BUTTON_RELEASE;
						cb(result);
					}
				}
			}
		}
		
		private static ButtonStates xor(ButtonStates a, ButtonStates b) {
			Debug.WriteLine("BuzzerLayer.xor()\t(static)\t{"+a+"} (+) {"+b+"}");
			ButtonStates result = new ButtonStates();
			if (xor(a.Red, b.Red))
				result.Red = true;
			if (xor(a.Blue, b.Blue))
				result.Blue = true;
			if (xor(a.Green, b.Green))
				result.Green = true;
			if (xor(a.Orange, b.Orange))
				result.Orange = true;
			if (xor(a.Yellow, b.Yellow))
				result.Yellow = true;
			
			Debug.WriteLine("BuzzerLayer.xor()\t(static)\treturning {"+result+"}");
			return result;
		}
		
		private static Boolean xor(Boolean a, Boolean b) {
			return !((a && b) || (!a && !b));
		}
	}
}
