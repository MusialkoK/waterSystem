package waterSystem.oldies;


public class Gate extends WorkingPoint {

		OldPipeline withOldPipeline;
		WaterDevice toDevice;
		boolean GateOpen=true;
		// true - waiting for data - do not calculate of transfer
		// false - data received - ready to calculate or transfer
		
		public OldPipeline getWithOldPipeline() {
			return withOldPipeline;
		}

		public void setWithOldPipeline(OldPipeline pipe) {
			this.withOldPipeline = pipe;
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
