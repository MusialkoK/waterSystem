package waterSystem;


public class Gate extends WorkingPoint {

		Pipeline withPipeline; 
		WaterDevice toDevice;
		boolean GateOpen=true;
		// true - waiting for data - do not calculate of transfer
		// false - data received - ready to calculate or transfer
		
		public Pipeline getWithPipeline() {
			return withPipeline;
		}

		public void setWithPipeline(Pipeline pipe) {
			this.withPipeline = pipe;
		}

		public WaterDevice getToDevice() {
			return toDevice;
		}

		public void setToDevice(WaterDevice device) {
			this.toDevice = device;
		}

		public boolean getGateState(){
			return GateOpen;
		}

		public void changeGateState(){
			GateOpen=!GateOpen;
		}

		public void setGateOpen(boolean gateOpen) {
			GateOpen = gateOpen;
		}
		

}
