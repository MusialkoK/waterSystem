package waterSystem;

public class Sprinkler extends WaterDevice{

	public void setDefault() {
		int quantity=190;
		curvePoints=new WorkingPoint[6];
		for (int j = 0; j < curvePoints.length; j++) {
			WorkingPoint wp=new WorkingPoint();
			curvePoints[j]=wp;
		}
		curvePoints[0].setWorkingPoint(quantity*0.082, 2.1);
		curvePoints[1].setWorkingPoint(quantity*0.100, 2.4);
		curvePoints[2].setWorkingPoint(quantity*0.104, 2.8);
		curvePoints[3].setWorkingPoint(quantity*0.109, 3.1);
		curvePoints[4].setWorkingPoint(quantity*0.113, 3.4);
		curvePoints[5].setWorkingPoint(quantity*0.127, 3.8);
		setName("R-VAN-SST 1,5x9,1m");	
	}

	public void setDefault(int quantity) {
		curvePoints=new WorkingPoint[6];
		for (int j = 0; j < curvePoints.length; j++) {
			WorkingPoint wp=new WorkingPoint();
			curvePoints[j]=wp;
		}
		curvePoints[0].setWorkingPoint(quantity*0.082, 2.1);
		curvePoints[1].setWorkingPoint(quantity*0.100, 2.4);
		curvePoints[2].setWorkingPoint(quantity*0.104, 2.8);
		curvePoints[3].setWorkingPoint(quantity*0.109, 3.1);
		curvePoints[4].setWorkingPoint(quantity*0.113, 3.4);
		curvePoints[5].setWorkingPoint(quantity*0.127, 3.8);
		setName("R-VAN-SST 1,5x9,1m");	
	}

	public Sprinkler(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}


	public Sprinkler(String def, int i) {
		super(def, i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void distributeWater(FlowDirection direction) {
		FlowDirection aDirection;
		
		if(direction.equals(FlowDirection.DIRECT)) {
			aDirection= FlowDirection.REVERSE;
		}else {
			aDirection= FlowDirection.DIRECT;
		}
		
		if(allowDistribution) {
			calculateGatesToWP(direction);
			setNewWorkingPointBy(FlowPressure.FLOW, getFlow());
			calculateWPtoGates(aDirection);
			setAllowDistribution(false);
		}
	}




}
