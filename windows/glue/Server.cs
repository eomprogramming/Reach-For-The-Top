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
		private BuzzerLayerBuilder sourceSource;
		private IBuzzerLayer source;
		
		/**
		 * Create a new Server to communicate on stream s with buzzers
		 * wrapped by b.
		 * @param s the stream to work on
		 * @param b the interface to the buzzers
		 */
		public Server(Stream s, BuzzerLayerBuilder b) {
			r = new StreamReader(s);
			w = new StreamWriter(s);
			sourceSource = b;
			source = sourceSource();
			source.setCallback(new Callback(buzzerInput));
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
				} else if (parts[0].Equals("reload")) {
					source = sourceSource();
					source.setCallback(new Callback(buzzerInput));
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
					break;
			}
		}
		
		public static string ButtonToString(Button b) {
			switch (b) {
				case Button.RED:
					return "red";
				case Button.BLUE:
					return "blue";
				case Button.ORANGE:
					return "orange";
				case Button.GREEN:
					return "green";
				case Button.YELLOW:
					return "yellow";
				default:
					return "invalid-button";
			}
		}
	}
	
	internal delegate IBuzzerLayer BuzzerLayerBuilder();
}

