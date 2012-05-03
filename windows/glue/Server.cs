using System;
namespace com.earlofmarch.reach {
	public class Server {
		private Stream io;
		private BuzzerLayer source;
		
		public Server (Stream s, BuzzerLayer b) {
			io = s;
			source = b;
		}
	}
}

