package waterSystem;

public class Pump extends WaterDevice{
	
	public void setDefault() {
		curvePoints=new WorkingPoint[6];
		for (int j = 0; j < curvePoints.length; j++) {
			WorkingPoint wp=new WorkingPoint();
			curvePoints[j]=wp;
		}
		curvePoints[0].setWorkingPoint(18, 5.51);
		curvePoints[1].setWorkingPoint(24, 5.23);
		curvePoints[2].setWorkingPoint(27, 5.08);
		curvePoints[3].setWorkingPoint(36, 4.5);
		curvePoints[4].setWorkingPoint(42, 4);
		curvePoints[5].setWorkingPoint(48, 3.45);
		setName("SHE 40-200/75");
	}
	
	public void startPump(CalculationDirection direction) {
		double midFlow =(curvePoints[0].getFlow()+curvePoints[curvePoints.length-1].getFlow())/2;
		double startFlow = 39;
		setNewWorkingPointBy(FlowPressure.FLOW, startFlow);
		calculateWPtoGates(direction);
		setAllowTransfer(true);
	}

	public Pump(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	public Pump(String def, int i) {
		super(def, i);
		// TODO Auto-generated constructor stub
	}

	
	protected void transfer() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void distributeWater(CalculationDirection direction) {
		CalculationDirection aDirection;
		
		if(direction.equals(CalculationDirection.DIRECT)) {
			aDirection=CalculationDirection.REVERSE;
		}else {
			aDirection=CalculationDirection.DIRECT;
		}
		
		if(allowDistribution) {
			calculateGatesToWP(direction);
			setNewWorkingPointBy(FlowPressure.PRESSURE, getPressure());
			calculateWPtoGates(aDirection);
			setAllowDistribution(false);
		}
	}

}
