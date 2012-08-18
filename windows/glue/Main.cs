using System;
using System.IO.Pipes;

namespace com.earlofmarch.reach {
	static class MainClass {
		public static void Main(string[] args) {
			String pname;
			if (args.Length < 1)
				pname = "eomreachpipe";
			else
				pname = args[0];
			
			Server serv = new Server(new NamedPipeServerStream(pname, PipeDirection.InOut), builder);

			serv.listen();
		}
		
		private static IBuzzerLayer builder() {
			return new BuzzerLayer();
		}
	}
}

