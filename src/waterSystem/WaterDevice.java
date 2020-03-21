package waterSystem;

import java.util.Scanner;

public abstract class WaterDevice extends WorkingPoint{
	String name;
	WorkingPoint[] curvePoints;
	int number;
	Gate[] inGates = new Gate[6];
	Gate[] outGates = new Gate[6];
	int activeInGates;
	int activeOutGates;
	boolean allowDistribution = false;
	boolean allowTransfer = false;
	
	public abstract void setDefault();
	
	public void setName(String name) {
		this.name=name;
	}
	public void setCurvePoints() {
		Scanner inScanner=new Scanner(System.in);
		System.out.print("Device name: ");	
		setName(inScanner.next());
		System.out.print("Number of working points:");	
		curvePoints=new WorkingPoint[inScanner.nextInt()];
		for (int j = 0; j < curvePoints.length; j++) {
			WorkingPoint wp=new WorkingPoint();
			curvePoints[j]=wp;
		}
		for (int j = 0; j < curvePoints.length; j++) {
			System.out.print("No."+(j+1)+" working point's flow [m3/h]: ");
			curvePoints[j].setFlow(inScanner.nextDouble());
			System.out.print("No."+(j+1)+". working point's pressue [atm]: ");
			curvePoints[j].setPressure(inScanner.nextDouble());
		}
		inScanner.close();
	}
	
	public void getCurvePoints() {
		for (WorkingPoint wp : curvePoints) {
			System.out.println(wp.getFlow()+" m3/h   "+ wp.getPressure()+" atm");
		}
	}
	public void getInfo() {
		System.out.println("Name: "+name);
		System.out.println("Working points:");
		getCurvePoints();
	}
	public void getRWP() {
		System.out.println("RWP flow: "+getFlow()+" m3/h");
		System.out.println("RWP pressure: "+getPressure()+" atm");
	}
	
	public double interpolate(double x, double x0, double x1, double y0, double y1) {
		return y0+(y1-y0)/(x1-x0)*(x-x0);
	}
	
	public void setNewWorkingPointBy(FlowPressure setBy, double value) {
		int i=0;
		Range range;
		switch (setBy){
			case PRESSURE:
				
				if(value>=curvePoints[0].getPressure()){
					range = Range.UPRANGE;
				}else if(value<=curvePoints[curvePoints.length-1].getPressure()) {
					range = Range.DOWNRANGE;
				}else {
					range = Range.INRANGE;
				}
				
				switch(range) {
					case UPRANGE:
						setFlow(curvePoints[0].getFlow());
						setPressure(curvePoints[0].getPressure());
						break;
					case INRANGE:
						do {
							if(curvePoints[i].getPressure()>=value && curvePoints[i+1].getPressure()<=value) {
								setFlow(interpolate(value, curvePoints[i].getPressure(), curvePoints[i+1].getPressure(), curvePoints[i].getFlow(), curvePoints[i+1].getFlow()));
								setPressure(value);
								i=curvePoints.length;
							}else {
								i++;
							}
						}while(i<curvePoints.length);
						break;
					case DOWNRANGE:
						setFlow(curvePoints[curvePoints.length-1].getFlow());
						setPressure(curvePoints[curvePoints.length-1].getPressure());
						break;
				}	
				break;
			case FLOW:
				
				if(value<=curvePoints[0].getFlow()){
					range = Range.DOWNRANGE;
				}else if(value>=curvePoints[curvePoints.length-1].getFlow()) {
					range = Range.UPRANGE;
				}else {
					range = Range.INRANGE;
				}
				
				switch(range) {
				case UPRANGE:
					setFlow(curvePoints[curvePoints.length-1].getFlow());
					setPressure(curvePoints[curvePoints.length-1].getPressure());
					break;
				case INRANGE:
					do {
						if(value>=curvePoints[i].getFlow() && value<=curvePoints[i+1].getFlow()) {
							setFlow(value);
							setPressure(interpolate(value, curvePoints[i].getFlow(), curvePoints[i+1].getFlow(), curvePoints[i].getPressure(), curvePoints[i+1].getPressure()));
							i=curvePoints.length;
						}else {
							i++;
						}
					}while(i<curvePoints.length-1);
					break;
				case DOWNRANGE:
					setFlow(curvePoints[0].getFlow());
					setPressure(curvePoints[0].getPressure());
					break;
			}
				break;
			}
		}
	
	public void changeDistributionState() {
		allowDistribution=!allowDistribution;
	}
	
	public boolean getDistrubutionState() {
		return allowDistribution;
	}
	
	public void changeTransferState() {
		allowTransfer=!allowTransfer;
	}
	
	public boolean getTransferState() {
		return allowTransfer;
	}
	
	public void calculateGatesToWP(CalculationDirection direction) {
			double sumFlow=0;
			double avgPressure=0;
			switch(direction) {
			case DIRECT:
				if(activeInGates>0) {
					for(int i=0; i<activeInGates; i++) {
						sumFlow=sumFlow+inGates[i].getFlow();
						avgPressure=avgPressure+inGates[i].getPressure();
						inGates[i].changeGateState();
					}
					avgPressure=avgPressure/activeInGates;
					setWorkingPoint(sumFlow,  avgPressure);
				}
				break;
			case REVERSE:
				if(activeOutGates>0) {
					for(int i=0; i<activeOutGates; i++) {
						sumFlow=sumFlow+outGates[i].getFlow();
						avgPressure=avgPressure+outGates[i].getPressure();
						outGates[i].changeGateState();
					}
					avgPressure=avgPressure/activeOutGates;
					setWorkingPoint(sumFlow,  avgPressure);
				}
				break;
			}
	}
	
	public void calculateWPtoGates(CalculationDirection direction) {
		double sumFlow=0;
		
		switch(direction) {
		case DIRECT:
			if(activeOutGates>0) {
				for(int i=0; i<activeOutGates; i++) {
					outGates[i].setPressure(getPressure());
					sumFlow=sumFlow+outGates[i].getFlow();
				}
				if(sumFlow!=0) {
					for(int i=0; i<activeOutGates; i++) {
					outGates[i].setFlow(outGates[i].getFlow()/sumFlow*getFlow());
					outGates[i].changeGateState();
					}
				}else {
					for(int i=0; i<activeOutGates; i++) {
						outGates[i].setFlow(getFlow()/activeOutGates);
						outGates[i].changeGateState();
					}
				}
			}
			break;
		case REVERSE:
			if(activeInGates>0) {
				for(int i=0; i<activeInGates; i++) {
					inGates[i].setPressure(getPressure());
					sumFlow=sumFlow+inGates[i].getFlow();
				}
				if(sumFlow!=0) {
					for(int i=0; i<activeInGates; i++) {
					inGates[i].setFlow(inGates[i].getFlow()/sumFlow*getFlow());
					inGates[i].changeGateState();
					}
				}else {
					for(int i=0; i<activeInGates; i++) {
						inGates[i].setFlow(getFlow()/activeInGates);
						inGates[i].changeGateState();
					}
				}
			}		
			break;
		}
	}
	public void distributeWater(CalculationDirection direction) {
		if(allowDistribution) {
			calculateGatesToWP(direction);
			calculateWPtoGates(direction);
			setAllowDistribution(false);
		}
	}
	
	public WaterDevice(int i) {
		number=i;
	}
	
	public WaterDevice(String def, int i) {
		if(def.equals("default")) {setDefault();}
		number=i;
	}

	public void setInGate(WaterDevice wd, Pipeline p) {
		Gate gt = new Gate();
		gt.setToDevice(wd);
		gt.setWithPipeline(p);
		inGates[activeInGates]=gt;
		activeInGates++;
	}
	
	public void setOutGate(WaterDevice wd, Pipeline p) {
		Gate gt = new Gate();
		gt.setToDevice(wd);
		gt.setWithPipeline(p);
		outGates[activeOutGates]=gt;
		activeOutGates++;	
	}
	
	public boolean checkGates(Gate[] g, int range){
		boolean isOK=false;
		boolean stop=false;
		int i=0;
			while(!stop && i<range){
				if(!g[i].getGateState()){
					isOK=true;
					i++;
					}else{
						isOK=false; 
						stop=true;
						}		
			}
		return isOK;
	}
	
	public void setDeviceStatus (CalculationDirection direction){
		switch(direction) {
		
		case DIRECT:
			if(activeInGates>0 && activeOutGates>0) {
				if(checkGates(inGates, activeInGates) && !checkGates(outGates, activeOutGates)){
					setAllowDistribution(true);
					}else if(!checkGates(inGates, activeInGates)&& checkGates(outGates,activeOutGates)){
						setAllowTransfer(true);
					}
			}else if(activeInGates==0) {
				if(!checkGates(outGates, activeOutGates)){
					setAllowDistribution(true);
					}else if(checkGates(outGates,activeOutGates)){
						setAllowTransfer(true);
					}
			}else {
				if(checkGates(inGates, activeInGates)){
					setAllowDistribution(true);
					}else if(!checkGates(inGates, activeInGates)){
						setAllowTransfer(true);
					}
			}
			
			break;
			
		case REVERSE:
			if(checkGates(inGates, activeInGates) && !checkGates(outGates,activeOutGates)){
				setAllowTransfer(true);
				}else if(!checkGates(inGates, activeInGates) && checkGates(outGates,activeOutGates)){
					setAllowDistribution(true);
				}
			break;
		}
	}

	public int findGate(CalculationDirection direction, WaterDevice source) {
		int ret=-1;
		int i=0;
		switch(direction) {
		case DIRECT:
			do {
				if(inGates[i].toDevice==source) {
					ret=i;
				}else {
					i++;
				}
			}while(ret==-1);
			break;
		case REVERSE:
			do {
				if(outGates[i].toDevice==source) {
					ret=i;
				}else {
					i++;
				}
			}while(ret==-1);
			break;
		}
		return ret;
	}

	public void setAllowTransfer(boolean allowTransfer) {
		this.allowTransfer = allowTransfer;
	}

	public void setAllowDistribution(boolean allowDistribution) {
		this.allowDistribution = allowDistribution;
	}
	
}