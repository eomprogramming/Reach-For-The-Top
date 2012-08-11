using System;
using System.IO;

namespace com.earlofmarch.reach {
	/**
	 * A class that listens to input on a stream and acts as a
	 * liason between a program at the other end of said stream
	 * and a Buzz buzzer.
	 */
	internal class Server {
		private StreamReader r;
		private StreamWriter w;
		private IBuzzerLayer source;
		
		/**
		 * Create a new Server to communicate on stream s with buzzers
		 * wrapped by b.
		 * @param s the stream to work on
		 * @param b the interface to the buzzers
		 */
		public Server(Stream s, IBuzzerLayer b) {
			r = new StreamReader(s);
			w = new StreamWriter(s);
			source = b;
			source.setCallback(new Callback(buzzerInput));
		}
		
		public void listen() {
			;
		}
		
		private void buzzerInput(CallbackArgs a) {
			switch (a.eventType) {
				case CallbackType.UNPLUGGED:
					w.WriteLine("unplugged " + a.handsetId);
					break;
				case CallbackType.BUTTON_PRESS:
					w.WriteLine("press " + a.handsetId + ":" + a.buzzerId + ":" + ButtonToString(a.button));
					break;
				case CallbackType.BUTTON_RELEASE:
					w.WriteLine("release " + a.handsetId + ":" + a.buzzerId + ":" + ButtonToString(a.button));
					break;
				default:
					w.WriteLine("error internal \"Unknown CallbackType\"");
			}
		}
		
		public static string ButtonToString(Button b) {
			switch (b) {
			case RED:
				return "red";
			case BLUE:
				return "blue";
			case ORANGE:
				return "orange";
			case GREEN:
				return "green";
			case YELLOW:
				return "yellow";
			default:
				return "invalid-button";
		}
	}
}

