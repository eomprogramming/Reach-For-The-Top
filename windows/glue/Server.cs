using System;
using System.IO;
using System.IO.Pipes;
using System.Threading;
using System.Diagnostics;

namespace com.earlofmarch.reach {
	/**
	 * A class that listens to input on a stream and acts as a
	 * liason between a program at the other end of said stream
	 * and a Buzz buzzer.
	 */
	internal class Server {
		private Stream pipe;
		private TextReader r;
		private TextWriter w;
		private BuzzerLayerBuilder sourceSource;
		private IBuzzerLayer source;
		
		/**
		 * Create a new Server to communicate on stream s with buzzers
		 * wrapped by b.
		 * @param s the stream to work on
		 * @param b the interface to the buzzers
		 */
		public Server(Stream s, BuzzerLayerBuilder b) {
			Debug.WriteLine("Server.Server()\t(constructor)\tCalled with" + s + " & " + b);
			pipe = s;
			r = new StreamReader(s);
			w = new StreamWriter(s);
			sourceSource = b;
			source = sourceSource();
			source.setCallback(new Callback(buzzerInput));
		}
		
		public Server(TextReader tr, TextWriter tw, BuzzerLayerBuilder b) {
			Debug.WriteLine("Server.Server()\t(constructor)\tCalled with" + tr + " & " + tw + " & " + b);
			pipe = null;
			r = tr;
			w = tw;
			sourceSource = b;
			source = sourceSource();
			source.setCallback(new Callback(buzzerInput));
		}
		
		public void start() {
			while (true) {
				if (pipe is NamedPipeServerStream) {
					((NamedPipeServerStream) pipe).WaitForConnection();
				}
				listen();
			}
		}
		
		public void listen() {
			Debug.WriteLine("Server.listen()\t"+this+"\tListening...");
			String input;
			String[] parts;
			String[] subparts;
			while ((input = r.ReadLine()) != null) {
				Debug.WriteLine("Server.listen()\t"+this+"\tGot line:" + input);
				parts = input.Split(' ', '\t');
				Debug.WriteLine("Server.listen()\t"+this+"\tSplit into" + parts);
				
				if (parts[0].Equals("light")) {
					subparts = parts[1].Split(':');
					source.lightUp(Int32.Parse(subparts[0]), Int32.Parse(subparts[1]));
				} else if (parts[0].Equals("unlight")) {
					subparts = parts[1].Split(':');
					source.putOut(Int32.Parse(subparts[0]), Int32.Parse(subparts[1]));
				}
			}
			Debug.WriteLine("Server.listen()\t"+this+"\tDone listening...");
		}
		
		private void buzzerInput(CallbackArgs a) {
			Debug.WriteLine("Server.buzzerInput(CallbackArgs)\t"+this+"\tCalled with" + a);
			if ((pipe is NamedPipeServerStream) && (!((NamedPipeServerStream)pipe).IsConnected)) {
				Debug.WriteLine("Server.buzzerInput()\t"+this+"\tNo pipe connection.");
				return;
			}
			
			switch (a.eventType) {
				case CallbackType.UNPLUGGED:
					Debug.WriteLine("Server.buzzerInput(CallbackArgs)\t"+this+"\tWriting unplugged");
					w.WriteLine("unplugged " + a.handsetId);
					break;
				case CallbackType.BUTTON_PRESS:
					Debug.WriteLine("Server.buzzerInput(CallbackArgs)\t"+this+"\tWriting press");
					w.WriteLine("press " + a.handsetId + ":" + a.buzzerId + ":" + ButtonToString(a.button));
					break;
				case CallbackType.BUTTON_RELEASE:
					Debug.WriteLine("Server.buzzerInput(CallbackArgs)\t"+this+"\tWriting release");
					w.WriteLine("release " + a.handsetId + ":" + a.buzzerId + ":" + ButtonToString(a.button));
					break;
				default:
					Debug.WriteLine("Server.buzzerInput(CallbackArgs)\t"+this+"\tInvalid callback type:"+a.eventType);
					w.WriteLine("error internal \"Unknown CallbackType\"");
					break;
			}
		}
		
		public static string ButtonToString(Button b) {
			Debug.WriteLine("Server.ButtonToString(Button)\t(static)\tCalled with" + b);
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

