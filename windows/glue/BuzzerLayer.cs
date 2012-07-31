using System;
using System.Collections.Generic;
using BuzzIO;

namespace com.earlofmarch.reach {
	internal class BuzzerLayer : IBuzzerLayer {
		private List<IBuzzHandsetDevice> devices;
		private Callback cb;
		
		public BuzzerLayer() {
			devices = BuzzHandsetDevice.FindBuzzHandset();
			
			foreach (IBuzzHandsetDevice d in devices) {
				d.DeviceRemoved += unplugged;
				d.ButtonChanged += somethingHappens;
			}
		}
		
		override void setCallback(Callback c) {
			cb = c;
		}
		
		private void unplugged(Object sender, EventArgs e) {
			CallbackArgs result;
			
			result.eventType = CallbackType.UNPLUGGED;
			result.buzzerId = -1;
			result.handsetId = devices.IndexOf((IBuzzHandsetDevice) sender);
			
			cb(result);
		}
		
		private void somethingHappens(Object sender, BuzzButtonChangedEventArgs e) {
			CallbackArgs result;
			result.handsetId = devices.IndexOf((IBuzzHandsetDevice) sender);
			result.eventType = CallbackType.BUTTON_PRESS;
			
			for (int i = 0; i < e.Buttons.Length; i++) {
				result.buzzer = i;
				result.button = e.Buttons[i].Red ? Button.RED : (e.Buttons[i].Blue ? Button.BLUE : (e.Buttons[i].Green ? Button.GREEN :
								(e.Buttons[i].Orange ? Button.ORANGE : (e.Buttons[i].Yellow ? Button.YELLOW : 0))));
				if (result.button != 0)
					cb(result);
			}
		}
	}
}