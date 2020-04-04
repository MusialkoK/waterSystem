package waterSystem;

public class WaterConditions implements Comparable<WaterConditions>{
    double flow;
    double pressure;


    public WaterConditions(double flow, double pressure) {
        setFlowAndPressure(flow, pressure);
    }

    public WaterConditions(){
        setFlowAndPressure(0,0);
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setFlowAndPressure(double flow, double pressure) {
        setFlow(flow);
        setPressure(pressure);
    }

    @Override
    public int compareTo(WaterConditions waterConditions) {
        return Double.compare(this.flow,waterConditions.flow);
    }
}
