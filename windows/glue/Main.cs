using System;
using System.IO.Pipes;
using System.Diagnostics;

namespace com.earlofmarch.reach {
	static class MainClass {
		public static void Main(string[] args) {
			Debug.Listeners.Add(new TextWriterTraceListener(Console.Error));
			Debug.AutoFlush = true;
			
			Debug.WriteLine("MainClass.main()\t(static)\tStarting...");
			//String pname;
			//if (args.Length < 1)
				//pname = "eomreachpipe";
			//else
				//pname = args[0];
			//Debug.WriteLine("MainClass.main()\t(static)\tListening on pipe " + pname);
			
			Server serv = new Server(Console.In, Console.Out, builder);
			serv.start();
		}
		
		private static IBuzzerLayer builder() {
			Debug.WriteLine("MainClass.builder()\t(static)\tCalled!");
			return new BuzzerLayer();
		}
	}
}

