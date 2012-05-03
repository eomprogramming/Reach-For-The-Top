using System;
using BuzzIO;

namespace com.earlofmarch.reach {
	public class BuzzerLayer {
		private IEnumerable<IBuzzHandsetDevice> devices;
		
		public BuzzerLayer () {
			devices = new BuzzHandsetFinder().FindHandsets();
		}
	}
}

