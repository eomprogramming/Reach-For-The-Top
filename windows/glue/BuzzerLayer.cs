using System;
using System.Collections.Generic;
using BuzzIO;

namespace com.earlofmarch.reach {
	internal class BuzzerLayer : IBuzzerLayer {
		private IEnumerable<IBuzzHandsetDevice> devices;
		private Callback cb;
		
		public BuzzerLayer() {
			devices = new BuzzHandsetFinder().FindHandsets();
			
			foreach (IBuzzHandsetDevice d in devices) {
				d.DeviceRemoved += unplugged;
				d.ButtonChanged += somethingHappens;
			}
		}
		
		override void setCallback(Callback c) {
			cb = c;
		}
		
		private void unplugged(Object sender, EventArgs e) {
			int i = 0;
			CallbackArgs result;
			
			result.eventType = CallbackType.UNPLUGGED;
			result.buzzerId = -1;
			foreach (IBuzzHandsetDevice d in devices) {
				if (sender == d)
					break;
				i++;
			}
			result.handsetId = i;
			
			cb(result);
		}
		
		private void somethingHappens(Object sender, BuzzButtonChangedEventArgs e) {
			;
		}
	}
}

