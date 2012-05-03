using System;

namespace com.earlofmarch.reach {
	static class MainClass {
		public static void Main (string[] args) {
			String pname;
			if (args.GetLength < 1)
				pname = "eomreachpipe";
			else
				pname = args[0];
			
			// Dammit: no Mono NamedPipe support.
			//Server serv = new Server(new NamedPipeServerStream(
		}
	}
}

