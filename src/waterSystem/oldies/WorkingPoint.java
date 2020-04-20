package waterSystem.oldies;

public class WorkingPoint {
	double pressure;
	double flow;
	
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getFlow() {
		return flow;
	}
	public void setFlow(double flow) {
		this.flow = flow;
	}
	
	public void setWorkingPoint(double flow, double pressure) {
		setPressure(pressure);
		setFlow(flow);
	}
	
	public void setWorkingPoint(WorkingPoint w) {
		setWorkingPoint(w.getFlow(), w.getPressure());
	}
}
