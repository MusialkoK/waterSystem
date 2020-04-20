package waterSystem.oldies;

import waterSystem.FlowPressure;

public class OldPipeline extends WaterDevice {
	double extDiameter;
	double intDiameter;
	double length;
	String material;
	double[] p=new double [2];
	double wallThickness;
	double flowSpeed;
	double hz;
	double cb;
	
	public double getFlowSpeed() {
		return flowSpeed;
	}

	private void setFlowSpeed() {
		double area=Math.PI*Math.pow((intDiameter/1000), 2)/4;
		flowSpeed = flow/3600/area;
	}

	@Override
	public void setDefault() {
		extDiameter=50;
		wallThickness=2;
		intDiameter=extDiameter-2*wallThickness;
		length=10;
		material="HDPE";
	}

	@Override
	public void setNewWorkingPointBy(FlowPressure setBy, double flow) {
		if(setBy==FlowPressure.FLOW){
			setFlow(flow);
			cb=-(8614910.22*Math.pow(getFlow(), 1.76)*Math.pow(intDiameter, -4.76)*length/1000);
			hz=-(Math.pow(10,9)* Math.pow(intDiameter, -4.87) * 1.131 * Math.pow(getFlow()/135, 1.852)*length/10);
			setPressure(Math.min(cb,hz));
			setFlowSpeed();
		}
	}

	public OldPipeline(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	public OldPipeline(String def, int i) {
		super(def, i);
		// TODO Auto-generated constructor stub
	}
}
