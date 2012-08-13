using System;
namespace com.earlofmarch.reach {
	internal delegate void Callback(CallbackArgs a);
	
	internal struct CallbackArgs {
		public CallbackType eventType;
		public int handsetId;
		public int buzzerId;
		public Button button;
	}
	
	internal struct Pair<T> {
		public T First;
		public T Second;
	}
	
	internal enum CallbackType {
		UNPLUGGED, BUTTON_PRESS, BUTTON_RELEASE
	}
	
	internal enum Button {
		RED, BLUE, ORANGE, GREEN, YELLOW
	}
	
	internal interface IBuzzerLayer {
		public void setCallback(Callback c);
		public void lightUp(int handset, int buzzer);
		public void putOut(int handset, int buzzer);
	}
}

