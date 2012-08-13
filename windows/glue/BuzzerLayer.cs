using System;
using System.Collections.Generic;
using BuzzIO;

namespace com.earlofmarch.reach {
	internal class BuzzerLayer : IBuzzerLayer {		
		private List<IBuzzHandsetDevice> devices;
		private ButtonStates[] states;
		private Boolean[][] lights;
		private Callback cb;
		
		public BuzzerLayer() {
			devices = BuzzHandsetDevice.FindBuzzHandsets();
			
			states = new ButtonStates[devices.Count * 4];
			
			lights = new Boolean[devices.Count][];
			for (int i = 0; i < lights.Length; i++)
				lights[i] = new Boolean[] {false, false, false, false};
			
			foreach (IBuzzHandsetDevice d in devices) {
				d.DeviceRemoved += unplugged;
				d.ButtonChanged += somethingHappens;
			}
		}
		
		public void setCallback(Callback c) {
			cb = c;
		}
		
		public void lightUp (int handset, int buzzer) {
			lights[handset][buzzer] = true;
			updateLights(handset);
		}
		
		public void putOut(int handset, int buzzer) {
			lights[handset][buzzer] = false;
			updateLights(handset);
		}
		
		private void updateLights(int h) {
			devices[h].SetLights(lights[h][0], lights[h][1],
			                     lights[h][2], lights[h][3]);
		}
		
		private void unplugged(Object sender, EventArgs e) {
			CallbackArgs result = new CallbackArgs();
			
			result.eventType = CallbackType.UNPLUGGED;
			result.buzzerId = -1;
			result.handsetId = devices.IndexOf((IBuzzHandsetDevice) sender);
			
			cb(result);
		}
		
		private void somethingHappens(Object sender, BuzzButtonChangedEventArgs e) {
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
		
		private ButtonStates xor(ButtonStates a, ButtonStates b) {
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
			
			return result;
		}
		
		private Boolean xor(Boolean a, Boolean b) {
			return !((a && b) || (!a && !b));
		}
	}
}
