using System;
namespace com.earlofmarch.reach {
	internal delegate void Callback(CallbackArgs a);
	
	internal struct CallbackArgs {
		public CallbackType eventType;
		public int handsetId;
		public int buzzerId;
	}
	
	internal enum CallbackType {
		UNPLUGGED
	}
	
	internal interface IBuzzerLayer {
		void setCallback(Callback c);
	}
}

