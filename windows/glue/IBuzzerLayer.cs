using System;
namespace com.earlofmarch.reach {
	internal delegate void Callback(CallbackArgs a);
	
	internal struct CallbackArgs {
		public CallbackType eventType;
		public int handsetId;
		public int buzzerId;
		public Button button;
	}
	
	internal enum CallbackType {
		UNPLUGGED, BUTTON_PRESS
	}
	
	internal enum Button {
		RED, BLUE, ORANGE, GREEN, YELLOW
	}
	
	internal interface IBuzzerLayer {
		void setCallback(Callback c);
	}
}

