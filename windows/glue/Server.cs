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
			
			listen();
		}
		
		public void listen() {
			String input;
			String[] parts;
			String[] subparts;
			while (true) {
				input = r.ReadLine();
				parts = input.Split(' ', '\t');
				
				if (parts[0].Equals("light")) {
					subparts = parts[1].Split(':');
					source.lightUp(Int32.Parse(subparts[0]), Int32.Parse(subparts[1]));
				} else if (parts[0].Equals("unlight")) {
					subparts = parts[1].Split(':');
					source.putOut(Int32.Parse(subparts[0]), Int32.Parse(subparts[1]));
				}
			}
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

